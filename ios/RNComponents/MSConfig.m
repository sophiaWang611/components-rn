//
//  MSConfig.m
//  RNComponents
//
//  Created by sophia on 16/7/11.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#import "MSConfig.h"

@implementation MSConfig {
  NSString *_mapKey;
}

+ (MSConfig *)globalConfig{
  static MSConfig *sharedInstance;
  static dispatch_once_t token;
  dispatch_once(&token,^{
    sharedInstance = [[MSConfig alloc] init];
    
  });
  return sharedInstance;
}

- (instancetype)init{
  _mapKey = @"";
  return self;
}

- (void) setMapKey:(NSString*) mapKey {
  _mapKey = mapKey;
}

- (NSString*) getMapKey {
  if (!_mapKey || [_mapKey length] == 0) {
    @throw [NSException exceptionWithName:@"MapException" reason:@"Map key did not set!" userInfo:nil];
  }
  return _mapKey;
}

@end

