//
//  MSConfig.h
//  RNComponents
//
//  Created by sophia on 16/7/11.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface MSConfig : NSObject

+ (MSConfig *) globalConfig;

- (NSString*) getMapKey;

- (void) setMapKey:(NSString*) mapKey;

@end