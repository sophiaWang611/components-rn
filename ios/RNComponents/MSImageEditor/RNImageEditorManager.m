//
//  RNImageEditorManager.m
//  MishiBuyer
//
//  Created by sophia on 16/1/12.
//  Copyright © 2015年 Mishi Technology. All rights reserved.
//

#import "RNImageEditorManager.h"
#import "RCTConvert.h"
#import "RNImageEditor.h"


@implementation RNImageEditorManager

RCT_EXPORT_MODULE();

- (UIView *)view
{
  return [[RNImageEditor alloc] init];
}

RCT_EXPORT_VIEW_PROPERTY(imageSourceUri, NSString);
RCT_EXPORT_VIEW_PROPERTY(width, NSNumber);
RCT_EXPORT_VIEW_PROPERTY(onChange, RCTBubblingEventBlock)



@end
