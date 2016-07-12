//
//  MSSearchMapController.m
//  MishiBuyer
//
//  Created by sophia on 15/12/28.
//  Copyright © 2015年 Mishi Technology. All rights reserved.
//

#import "MSSearchMapController.h"

#import "MSMapController.h"
#import <MAMapKit/MAMapKit.h>
#import <AMapSearchKit/AMapSearchAPI.h>
#import <AMapSearchKit/AMapSearchServices.h>
#import "MSConfig.h"
#import "MSColor.h"
#import "MSFont.h"
#import "Masonry.h"


#define MSdistanceFromEdge 15

@interface MSSearchMapController ()<MAMapViewDelegate,UIActionSheetDelegate>
{
  MAMapView *myMapView;
  NSMutableArray *annotations;
  UIButton *backButton;
  AMapSearchAPI *search;
  NSMutableDictionary *searchResult;
  MAPointAnnotation *addressAnnotation;
}
@end

@implementation MSSearchMapController

- (void)viewDidLoad {
  [super viewDidLoad];
  
  searchResult = [[NSMutableDictionary alloc] init];
  
  [self initMapUI];
  [self initUI];
  [self geoSearch: [self.address city] poiName:[self.address poiName] address:self.address.addressDesc ];

}

- (void) initMapUI {
  [AMapSearchServices sharedServices].apiKey = [[MSConfig globalConfig] getMapKey];
  search =  [[AMapSearchAPI alloc] init];
  [search setDelegate: self];
  
  myMapView = [[MAMapView alloc] initWithFrame:CGRectMake(0, 0, CGRectGetWidth(self.view.bounds), CGRectGetHeight(self.view.bounds))];
  myMapView.showsCompass = NO;
  myMapView.showsScale = NO;
  myMapView.showsUserLocation = YES;
  myMapView.zoomEnabled = YES;
  myMapView.scrollEnabled = YES;
  myMapView.userTrackingMode = MAUserTrackingModeFollow;
  
  [myMapView setDelegate:self];
  [myMapView setZoomLevel:16 atPivot:CGPointMake(self.view.bounds.size.width/2, self.view.bounds.size.height/2) animated:YES];
  
  [self.view addSubview:myMapView];

}

//根据地址解析经纬度
- (void)geoSearch: (NSString*) city poiName:(NSString*) poiName address:(NSString*) address {

  //构造AMapGeocodeSearchRequest对象，address为必选项，city为可选项
  AMapGeocodeSearchRequest *geoRequest = [[AMapGeocodeSearchRequest alloc] init];
  
  geoRequest.city = city;
  
  NSString *other = poiName;
  if (poiName != address) {
    other = [address stringByAppendingString:poiName];
  }
  
  geoRequest.address = other;
  
  //发起正向地理编码
  [search AMapGeocodeSearch: geoRequest];

}

//实现正向地理编码的回调函数
- (void)onGeocodeSearchDone:(AMapGeocodeSearchRequest *)request response:(AMapGeocodeSearchResponse *)response {
  if (response.geocodes.count > 0) {
    AMapGeocode *geocode = [response.geocodes objectAtIndex:0];
    
    if (geocode && geocode.province && geocode.district && geocode.citycode && geocode.city) {
      [searchResult removeAllObjects];
      [searchResult setObject:_address.poiName forKey:@"poiName"];
      [searchResult setObject:_address.addressDesc forKey:@"address"];
      
      if(geocode.province) [searchResult setObject:geocode.province forKey:@"province"];
      if(geocode.district) [searchResult setObject:geocode.district forKey:@"district"];
      if(geocode.citycode) [searchResult setObject:geocode.citycode forKey:@"cityCode"];
      if(geocode.adcode) [searchResult setObject:geocode.adcode forKey:@"districtCode"];
      if(geocode.city) [searchResult setObject:geocode.city forKey:@"city"];
      
      [searchResult setObject:[NSString stringWithFormat:@"%f",geocode.location.latitude] forKey:@"latitude"];
      [searchResult setObject:[NSString stringWithFormat:@"%f",geocode.location.longitude] forKey:@"longitude"];
      
      [myMapView setCenterCoordinate:CLLocationCoordinate2DMake(geocode.location.latitude, geocode.location.longitude)];
      addressAnnotation.coordinate = CLLocationCoordinate2DMake(geocode.location.latitude, geocode.location.longitude);
      return;
    }
  }
  
  if (![[searchResult objectForKey:@"searchTimes"]  isEqual: @"1"]) {
      [searchResult removeAllObjects];
      [searchResult setObject:@"1" forKey:@"searchTimes"];
      [self geoSearch: [self.address city] poiName:[self.address city] address:[self.address city]];
  }
}

- (void)initUI
{
  UIView *noticeView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 222, 30)];
  noticeView.translatesAutoresizingMaskIntoConstraints = NO;
  noticeView.layer.cornerRadius = 15;
  noticeView.backgroundColor = [MSColor black8Alpha];
  [self.view addSubview:noticeView];
  [noticeView mas_makeConstraints:^(MASConstraintMaker *make) {
    make.top.equalTo(self.view).with.offset(30);
    make.centerX.mas_equalTo(self.view.mas_centerX);
    make.width.equalTo(@180);
    make.height.equalTo(@30);
  }];
  
  UIImageView *bLineLocImage = [[UIImageView alloc] init];
  bLineLocImage.translatesAutoresizingMaskIntoConstraints = NO;
  bLineLocImage.image = [UIImage imageNamed:@"bLineLocImage"];
  [noticeView addSubview:bLineLocImage];
  [bLineLocImage mas_makeConstraints:^(MASConstraintMaker *make) {
    make.left.equalTo(noticeView).with.offset(10);
    make.centerY.mas_equalTo(noticeView.mas_centerY);
    make.width.equalTo(@12);
    make.height.equalTo(@12);
  }];
  
  UILabel *noticeLabel = [[UILabel alloc] init];
  noticeLabel.translatesAutoresizingMaskIntoConstraints = NO;
  noticeLabel.textColor = [MSColor white];
  noticeLabel.font = [MSFont small];
  noticeLabel.text = @"拖动或缩放地图来确定位置";
  [noticeView addSubview:noticeLabel];
  [noticeLabel mas_makeConstraints:^(MASConstraintMaker *make) {
    make.left.equalTo(bLineLocImage).with.offset(15);
    make.centerY.mas_equalTo(noticeView.mas_centerY);
  }];
  
  //图钉
  UIView *locationView = [[UIView alloc] initWithFrame:CGRectMake(0, 0, 80, 30)];
  locationView.translatesAutoresizingMaskIntoConstraints = NO;
  locationView.layer.cornerRadius = 5;
  [locationView.layer setBorderColor: [[MSColor lightEBGray] CGColor]];
  [locationView.layer setBorderWidth: 1];
  locationView.backgroundColor = [MSColor whiteColor];
  [self.view addSubview:locationView];
  [locationView mas_makeConstraints:^(MASConstraintMaker *make) {
    make.bottom.mas_equalTo(self.view.mas_centerY).with.offset(-30);
    make.centerX.mas_equalTo(self.view.mas_centerX);
  }];
  //图钉文本，显示poiName
  UILabel *locationLabel = [[UILabel alloc] init];
  locationLabel.translatesAutoresizingMaskIntoConstraints = NO;
  locationLabel.textColor = [MSColor black];
  locationLabel.font = [MSFont small];
  locationLabel.text = self.address.poiName;
  locationLabel.numberOfLines = 2;
  [locationView addSubview:locationLabel];
  //计算文本高度和宽度
  NSAttributedString *attrStr = [[NSAttributedString alloc] initWithString:locationLabel.text];
  locationLabel.attributedText = attrStr;
  NSRange range = NSMakeRange(0, attrStr.length);
  NSDictionary *dic = [attrStr attributesAtIndex:0 effectiveRange:&range];
  CGRect rect = [locationLabel.text boundingRectWithSize:locationLabel.bounds.size
                                                 options:NSStringDrawingUsesLineFragmentOrigin | NSStringDrawingUsesFontLeading
                                              attributes:dic
                                                 context:nil];
  CGFloat width = rect.size.width > 250 ? 250 : rect.size.width;
  CGFloat height = rect.size.height;
  //设置文本容器自适应文字内容
  [locationLabel mas_makeConstraints:^(MASConstraintMaker *make) {
    make.centerY.mas_equalTo(locationView.mas_centerY);
    make.centerX.mas_equalTo(locationView.mas_centerX);
    make.width.equalTo(@(width));
    make.height.equalTo(@(height));
  }];
  [locationView mas_makeConstraints:^(MASConstraintMaker *make) {
    make.width.equalTo(@(width + 20));
    make.height.equalTo(@(height + 20));
  }];
  //图钉的图片
  UIImageView *locationImg = [[UIImageView alloc] initWithImage:[UIImage imageNamed:@"maplocation"]];
  locationImg.translatesAutoresizingMaskIntoConstraints = NO;
  locationImg.center = self.view.center;
  [self.view addSubview:locationImg];
  [self.view bringSubviewToFront:locationImg];
  [locationImg mas_makeConstraints:^(MASConstraintMaker *make) {
    make.bottom.mas_equalTo(self.view.mas_centerY);
    make.centerX.mas_equalTo(self.view.mas_centerX);
  }];
  
  //后退按钮
  backButton = [UIButton buttonWithType:UIButtonTypeCustom];
  backButton.frame = CGRectMake(MSdistanceFromEdge, 30, 34, 34);
  [backButton setBackgroundImage:[UIImage imageNamed:@"btn_map_back"] forState:UIControlStateNormal];
  [backButton addTarget:self action:@selector(backAction) forControlEvents:UIControlEventTouchUpInside];
  backButton.backgroundColor = [MSColor clearColor];
  [backButton.layer setMasksToBounds:YES];
  [backButton.layer setCornerRadius:17];
  [self.view addSubview:backButton];
  
  //完成按钮
  UIButton *finished = [UIButton buttonWithType:UIButtonTypeCustom];
  finished.frame = CGRectMake(self.view.frame.size.width - 50, 30, 35, 35);
  [finished addTarget:self action:@selector(finishedAction) forControlEvents:UIControlEventTouchUpInside];
  finished.backgroundColor = [MSColor c8Black];
  [finished setTitle: @"完成" forState:UIControlStateNormal];
  [finished setFont: [MSFont small]];
  [finished setTitleColor:[MSColor white] forState:UIControlStateNormal];
  [finished.layer setMasksToBounds:YES];
  [finished.layer setCornerRadius:5];
  [self.view addSubview:finished];
  
}

- (void)mapView:(MAMapView *)mapView didAddAnnotationViews:(NSArray *)views
{
  MAAnnotationView *view = views[0];
  // 放到该方法中用以保证userlocation的annotationView已经添加到地图上了。
  if ([view.annotation isKindOfClass:[MAUserLocation class]])
  {
    MAUserLocationRepresentation *pre = [[MAUserLocationRepresentation alloc] init];
    pre.showsAccuracyRing = YES;
    pre.fillColor = [MSColor alpha4blue];
    pre.image = [UIImage imageNamed:@"currentlocation"];
    pre.lineWidth = 0;
    
    [myMapView updateUserLocationRepresentation:pre];
    
    view.calloutOffset = CGPointMake(0, 0);
  }
  
}

- (NSArray *)Annotations
{
  if (annotations == nil) {
    annotations = [[NSMutableArray alloc] init];
  }
  return annotations;
}

#pragma mark -- Actions
-(void)backAction
{
  [self dismissViewControllerAnimated:YES completion:nil];
}

-(void)finishedAction {
  UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@""
                                                  message:@"配送员将会按照地图定位上门取餐，请务必确认地图定位正确！"
                                                 delegate:self
                                        cancelButtonTitle:@"返回调整定位"
                                        otherButtonTitles:@"定位正确", nil];
  dispatch_async(dispatch_get_main_queue(), ^{
    [alert show];
  });
}

- (void)alertView:(UIAlertView *)alertView clickedButtonAtIndex:(NSInteger)buttonIndex
{
  if (buttonIndex == 1) {
    AMapReGeocodeSearchRequest *regeo = [[AMapReGeocodeSearchRequest alloc] init];
    regeo.location = [AMapGeoPoint locationWithLatitude: myMapView.centerCoordinate.latitude
                                             longitude: myMapView.centerCoordinate.longitude];
    regeo.radius = 0;
    [search AMapReGoecodeSearch: regeo];
  }
}

- (void)onReGeocodeSearchDone:(AMapReGeocodeSearchRequest *)request response:(AMapReGeocodeSearchResponse *)response {
  if (response.regeocode) {
    AMapReGeocode *geocode = response.regeocode;
    AMapAddressComponent *addressComponent = geocode.addressComponent;
    
    [searchResult removeAllObjects];
    [searchResult setObject:_address.poiName forKey:@"poiName"];
    [searchResult setObject:_address.addressDesc forKey:@"address"];
    [searchResult setObject:[NSString stringWithFormat:@"%f",myMapView.centerCoordinate.latitude] forKey:@"latitude"];
    [searchResult setObject:[NSString stringWithFormat:@"%f",myMapView.centerCoordinate.longitude] forKey:@"longitude"];
    
    if(addressComponent.province) [searchResult setObject:addressComponent.province forKey:@"province"];
    if(addressComponent.district) [searchResult setObject:addressComponent.district forKey:@"district"];
    if(addressComponent.citycode) [searchResult setObject:addressComponent.citycode forKey:@"cityCode"];
    if(addressComponent.adcode) [searchResult setObject:addressComponent.adcode forKey:@"districtCode"];
    if(!addressComponent.city || addressComponent.city.length == 0) {
      [searchResult setObject:addressComponent.province forKey:@"city"];
    } else {
      [searchResult setObject:addressComponent.city forKey:@"city"];
    }
  }
  
  NSMutableDictionary *result = [[NSMutableDictionary alloc] init];
  [result setObject:searchResult forKey:@"data"];
  NSMutableArray *results = [[NSMutableArray alloc] init];
  [results addObject:result];
  _callback(results);
  [self dismissViewControllerAnimated:YES completion:nil];

}

//定位当前位置
-(void)locationAction:(id)sender
{
  [myMapView setCenterCoordinate:myMapView.userLocation.location.coordinate animated:YES];
}

@end