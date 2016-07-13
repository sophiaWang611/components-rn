//
//  MSLocationService.h
//  MishiBuyer
//
//  Created by mars.tsang on 15/11/18.
//  Copyright © 2015年 Mishi Technology. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <CoreLocation/CLLocation.h>
#import "RCTBridgeModule.h"


@interface MSLocationService : NSObject <RCTBridgeModule>

@property (nonatomic,strong) CLLocation *userLocation;

@property  BOOL isLocationDone;

- (void)startLocate;
- (void)waitingForLocationDone;
+ (MSLocationService *)locationService;

@end
