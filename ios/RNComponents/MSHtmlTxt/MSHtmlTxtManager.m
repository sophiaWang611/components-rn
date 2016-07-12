//
//  MSHtmlTxtManager.m
//  MishiBuyer
//
//  Created by sophia on 16/3/18.
//  Copyright © 2016年 Mishi Technology. All rights reserved.
//

#import "MSHtmlTxtManager.h"
#import "MSHtmlTxtView.h"

@implementation MSHtmlTxtManager

RCT_EXPORT_MODULE();

- (UIView *)view
{
  return [[MSHtmlTxtView alloc] init];
}

RCT_EXPORT_VIEW_PROPERTY(text, NSString)
RCT_EXPORT_VIEW_PROPERTY(paddingTop, NSNumber)
RCT_EXPORT_VIEW_PROPERTY(paddingBottom, NSNumber)
RCT_EXPORT_VIEW_PROPERTY(paddingLeft, NSNumber)
RCT_EXPORT_VIEW_PROPERTY(paddingRight, NSNumber)


@end

