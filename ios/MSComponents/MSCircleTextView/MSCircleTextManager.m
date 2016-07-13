//
//  MSCircleTextManager.m
//  MishiBuyer
//
//  Created by sophia on 16/1/13.
//  Copyright © 2016年 Mishi Technology. All rights reserved.
//

#import "MSCircleTextManager.h"
#import "MSCircleTextView.h"
#import "RCTTextView.h"

@implementation MSCircleTextManager

RCT_EXPORT_MODULE();

- (UIView *)view
{
  return [[MSCircleTextView alloc] init];
}

RCT_EXPORT_VIEW_PROPERTY(text, NSString)
RCT_EXPORT_VIEW_PROPERTY(color, UIColor)
RCT_EXPORT_VIEW_PROPERTY(width, float)
RCT_EXPORT_VIEW_PROPERTY(fontSize, float)


@end