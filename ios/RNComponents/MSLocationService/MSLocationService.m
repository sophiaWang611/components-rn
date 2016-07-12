//
//  MSLocationService.m
//  MishiBuyer
//
//  Created by mars.tsang on 15/11/18.
//  Copyright © 2015年 Mishi Technology. All rights reserved.
//

#import "MSLocationService.h"

#import <CoreLocation/CLLocation.h>
#import <AMapLocationKit/AMapLocationkit.h>
#import <AMapSearchKit/AMapSearchKit.h>
#import "MSSdkConfig.h"

//static NSString *AMAP_API_KEY = @"777f3f64a01b65c929664347c337fd6b";

@interface MSLocationService()

@property (nonatomic,strong) AMapLocationManager *locationManager;
@property (nonatomic,strong) NSString *city;
@property (nonatomic,strong) NSCondition *locationCondition;

@property (nonatomic,strong) AMapSearchAPI *searchApi;

@property (nonatomic,strong) RCTResponseSenderBlock nearbyPOISearchCallback;
@property (nonatomic,strong) RCTResponseSenderBlock keywordPOISearchCallback;

- (void)startLocate;
- (void)waitingForLocationDone;

- (NSString *)fetechUserCity;
- (void)fetchNearbyPOIs;
- (void)fetchPOIwithCity:(NSString *)city keyword:(NSString *)keyword;

- (NSDictionary *)AMapPOIToDictionary:(AMapPOI *)poi;

@end


@implementation MSLocationService

+ (MSLocationService *)locationService {
  static MSLocationService *sharedInstance;
  static dispatch_once_t token;
  dispatch_once(&token,^{
    sharedInstance = [[MSLocationService alloc] init];
    
  });
  return sharedInstance;
}

RCT_EXPORT_MODULE()


RCT_EXPORT_METHOD(userCity:(RCTResponseSenderBlock)callback){
  NSString *city = [self fetechUserCity];
  if(!city){
    city = @"";
  }
  callback([self getReturnMap:@"data" value:city]);
}

RCT_EXPORT_METHOD(userNearbyPOIs:(RCTResponseSenderBlock)callback){
  //callback(@[[NSNull null]]);
  self.nearbyPOISearchCallback = callback;
  [self fetchNearbyPOIs];
}


RCT_EXPORT_METHOD(cityKeywordSearchPOIs:(NSString *)city keyword:(NSString *)keyword callback:(RCTResponseSenderBlock)callback){
  //callback(@[[NSNull null]]);
  self.keywordPOISearchCallback = callback;
  [self fetchPOIwithCity:city keyword:keyword];
}

RCT_EXPORT_METHOD(userLocation: (RCTResponseSenderBlock)callback) {
  NSMutableDictionary *searchResult = [[NSMutableDictionary alloc] init];
  NSMutableArray *results = [[NSMutableArray alloc] init];
  if (self.isLocationDone) {
    [searchResult setObject:[NSString stringWithFormat:@"%f", self.userLocation.coordinate.latitude] forKey:@"latitude"];
    [searchResult setObject:[NSString stringWithFormat:@"%f", self.userLocation.coordinate.longitude] forKey:@"longitude"];
  }
  [results addObject:searchResult];
  callback(results);
}

- (instancetype)init{
  self = [super init];
  if (self) {
    //
    [[AMapLocationServices sharedServices] setApiKey:[[MSSdkConfig globalConfig] amapAPIKey]];
    self.locationManager = [[AMapLocationManager alloc] init];
    self.locationCondition = [[NSCondition alloc] init];
    self.isLocationDone = NO;
    
    [self startLocate];
    
    [[AMapSearchServices sharedServices] setApiKey:[[MSSdkConfig globalConfig] amapAPIKey]];
    self.searchApi = [[AMapSearchAPI alloc] init];
    [self.searchApi setDelegate:self];
    self.nearbyPOISearchCallback = nil;
    self.keywordPOISearchCallback = nil;

  }
  return self;
}


- (NSString *)fetechUserCity{
  [self waitingForLocationDone];
  return self.city;
  
}

- (void)fetchNearbyPOIs{
  if (!self.isLocationDone) {
    [self waitingForLocationDone];
  }
  
  AMapPOIAroundSearchRequest  *request = [[AMapPOIAroundSearchRequest alloc] init];
  request.types = @"";
  request.radius = 1000;
  //request.city = @[self.city];
  request.requireExtension = YES;
  request.location = [AMapGeoPoint locationWithLatitude:self.userLocation.coordinate.latitude longitude:self.userLocation.coordinate.longitude];
  [self.searchApi AMapPOIAroundSearch:request];
  
}

- (void)fetchPOIwithCity:(NSString *)city keyword:(NSString *)keyword{
  if (!self.isLocationDone) {
    [self waitingForLocationDone];
  }
  AMapPOIKeywordsSearchRequest *request = [[AMapPOIKeywordsSearchRequest alloc] init];
  request.requireExtension = YES;
  request.city = city;
  request.keywords = keyword;
  [self.searchApi AMapPOIKeywordsSearch:request];
  
}




#pragma --- poi search delegate --

- (void)searchRequest:(id)request didFailWithError:(NSError *)error{
  if ([request isKindOfClass:[AMapPOIAroundSearchRequest class]]) {
    self.nearbyPOISearchCallback([self getReturnMap:@"err" value:[error description]]);
  }else{
    self.keywordPOISearchCallback([self getReturnMap:@"err" value:[error description]]);
  }
 
}

- (void)onPOISearchDone:(AMapPOISearchBaseRequest *)request response:(AMapPOISearchResponse *)response{
  NSMutableArray *results = [[NSMutableArray alloc] init];
  for (AMapPOI *aPoi in response.pois) {
    //
    NSDictionary *dic = [self AMapPOIToDictionary:aPoi];
    if (dic) {
      [results addObject:dic];
    }
  }
  if ([request isKindOfClass:[AMapPOIAroundSearchRequest class]]) {
    self.nearbyPOISearchCallback([self getReturnMap:@"data" value:results]);
  }else{
    self.keywordPOISearchCallback([self getReturnMap:@"data" value:results]);
  }
}


#pragma --- Private Methods ---

- (void)startLocate{
  __weak typeof(self)  weakSelf = self;
  [self.locationCondition lock];
  [self.locationManager requestLocationWithReGeocode:YES completionBlock:^(CLLocation *location, AMapLocationReGeocode *regeocode, NSError *error) {
    if (error) {
      weakSelf.city = @"";
    }else{
      weakSelf.userLocation = location;
      weakSelf.city = regeocode.city;
    }
    self.isLocationDone = YES;
    [self.locationCondition signal];
    [self.locationCondition unlock];
  }];
}

- (void)waitingForLocationDone{
  [self.locationCondition lock];
  while (!self.isLocationDone ) {
    [self.locationCondition wait];
  }
  [self.locationCondition unlock];
}


- (NSDictionary *)AMapPOIToDictionary:(AMapPOI *)poi{
  if (poi == nil) {
    return [NSDictionary dictionary];
  }
  NSMutableDictionary *dictionary = [[NSMutableDictionary alloc] init];
  [dictionary setObject:poi.name forKey:@"name"];
  if(poi.address) [dictionary setObject:poi.address forKey:@"address"];
  if(poi.province) [dictionary setObject:poi.province forKey:@"province"];
  if(poi.city) [dictionary setObject:poi.city forKey:@"city"];
  if(poi.district) [dictionary setObject:poi.district forKey:@"district"];
  if(poi.adcode) [dictionary setObject:poi.adcode forKey:@"districtCode"];
  if(poi.citycode) [dictionary setObject:poi.citycode forKey:@"cityCode"];
  [dictionary setObject:[NSString stringWithFormat:@"%f",poi.location.latitude] forKey:@"latitude"];
  [dictionary setObject:[NSString stringWithFormat:@"%f",poi.location.longitude] forKey:@"longitude"];
  return dictionary;
}

- (NSMutableArray *)getReturnMap:(NSString*) key value: (NSObject *) value{
  NSMutableDictionary *searchResult = [[NSMutableDictionary alloc] init];
  [searchResult setObject:value forKey:key];
  NSMutableArray *results = [[NSMutableArray alloc] init];
  [results addObject:searchResult];
  return results;
}

@end
