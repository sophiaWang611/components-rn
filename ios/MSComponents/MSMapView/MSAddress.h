//
//  MSAddress.h
//  MishiBuyer
//
//  Created by mars.tsang on 15/11/19.
//  Copyright © 2015年 Mishi Technology. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface MSAddress : NSObject

@property(nonatomic,strong) NSString *province;
@property(nonatomic,strong) NSString *provinceCode;
@property(nonatomic,strong) NSString *city;
@property(nonatomic,strong) NSString *cityCode;
@property(nonatomic,strong) NSString *district;
@property(nonatomic,strong) NSString *districtCode;
@property(nonatomic,strong) NSString *poiName;
@property(nonatomic,strong) NSString *otherPoiInfo;
@property(nonatomic,strong) NSString *addressDesc;
@property(nonatomic,strong) NSString *keyWords;
@property double lat;
@property double lng;

@end
