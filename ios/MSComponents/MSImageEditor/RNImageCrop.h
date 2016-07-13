//
//  RNImageCrop.h
//  MishiBuyer
//
//  Created by sophia on 16/1/15.
//  Copyright © 2016年 Mishi Technology. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
#import "RCTBridgeModule.h"

@interface RNImageCrop : NSObject<RCTBridgeModule>

-(UIImage*) cropByPathRate: (float)rate path:(NSString *)path;

@end