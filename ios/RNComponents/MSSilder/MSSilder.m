//
//  MSSilder.m
//  MishiBuyer
//
//  Created by sophia on 16/4/6.
//  Copyright © 2016年 Mishi Technology. All rights reserved.
//

#import "MSSilder.h"

@implementation MSSilder {
  float _minValue;
}

-(void) setMinValue:(float)minValue {
  _minValue = minValue;
}

- (void)setValue:(float)value
{
  if (_minValue && _minValue > 0 && value < _minValue) {
    super.value = _minValue;
  }
  super.value = value;
}

@end
