//
//  MSMapViewManager.m
//  MishiBuyer
//
//  Created by mars.tsang on 15/11/19.
//  Copyright © 2015年 Mishi Technology. All rights reserved.
//

#import "MSMapViewManager.h"

#import "MSMapController.h"
#import "MSAddress.h"
#import "MSSearchMapController.h"

@implementation MSMapViewManager

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(presentMapViewWithPOIName:(NSString *)province city:(NSString *)city
                  district:(NSString *)district poiName:(NSString *)poiName lat:(double)lat lng:(double)lng){
//RCT_EXPORT_METHOD(presentMapViewWithPOIName){
  dispatch_async(dispatch_get_main_queue(), ^{
    UIViewController *root = [[[[UIApplication sharedApplication] delegate] window] rootViewController];
    MSAddress *address = [[MSAddress alloc] init];
    
    [address setProvince:province];
    [address setCity:city];
    [address setDistrict:district];
    [address setPoiName:poiName];
    [address setLat:lat];
    [address setLng:lng];
    NSString *desc = [NSString stringWithFormat:@"%@%@", province, city];
    desc = [NSString stringWithFormat:@"%@%@", desc, district];
    [address setAddressDesc: desc];
    MSMapController *mapViewController = [[MSMapController alloc] init];
    [mapViewController setAddress: address];
    [root presentViewController:mapViewController animated:YES completion:nil];
  });
}

RCT_EXPORT_METHOD(presentSearchMapView:(NSString *) cityName poiName:(NSString *) poiName
                  addressDesc:(NSString *) addressDesc callback:(RCTResponseSenderBlock)callback) {
  dispatch_async(dispatch_get_main_queue(), ^{
    UIViewController *root = [[[[UIApplication sharedApplication] delegate] window] rootViewController];
    MSAddress *address = [[MSAddress alloc] init];
    [address setCity: cityName];
    [address setAddressDesc: addressDesc];
    [address setPoiName: poiName];
    
    MSSearchMapController *map = [[MSSearchMapController alloc] init];
    [map setAddress:address];
    [map setCallback:callback];
    [root presentViewController:map animated:YES completion:nil];
  });
}


@end
