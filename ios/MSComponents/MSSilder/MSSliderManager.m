//
//  RCTSliderManager.m
//  MishiBuyer
//
//  Created by sophia on 16/4/6.
//  Copyright © 2016年 Mishi Technology. All rights reserved.
//

#import "MSSliderManager.h"

#import "RCTBridge.h"
#import "RCTEventDispatcher.h"
#import "MSSilder.h"
#import "UIView+React.h"

@implementation MSSliderManager

RCT_EXPORT_MODULE()

- (UIView *)view
{
  MSSilder *slider = [MSSilder new];
  [slider addTarget:self action:@selector(sliderValueChanged:)
   forControlEvents:UIControlEventValueChanged];
  [slider addTarget:self action:@selector(sliderTouchEnd:)
   forControlEvents:(UIControlEventTouchUpInside |
                     UIControlEventTouchUpOutside |
                     UIControlEventTouchCancel)];
  return slider;
}

static void RCTSendSliderEvent(MSSilder *sender, BOOL continuous)
{
  float value = sender.value;
  
  if (sender.step > 0 &&
      sender.step <= (sender.maximumValue - sender.minimumValue)) {
    
    value =
    MAX(sender.minimumValue,
        MIN(sender.maximumValue,
            sender.minimumValue + round((sender.value - sender.minimumValue) / sender.step) * sender.step
            )
        );
    
    float min = value;
    if (sender.minValue && value < sender.minValue) {
      min = sender.minValue;
    }
    [sender setValue:min animated:YES];
  }
  
  if (continuous) {
    if (sender.onValueChange && sender.lastValue != value) {
      sender.onValueChange(@{
                             @"value": @(value),
                             });
    }
  } else {
    if (sender.onSlidingComplete) {
      sender.onSlidingComplete(@{
                                 @"value": @(value),
                                 });
    }
  }
  
  sender.lastValue = value;
}

- (void)sliderValueChanged:(RCTSlider *)sender
{
  RCTSendSliderEvent(sender, YES);
}

- (void)sliderTouchEnd:(RCTSlider *)sender
{
  RCTSendSliderEvent(sender, NO);
}

RCT_EXPORT_VIEW_PROPERTY(value, float);
RCT_EXPORT_VIEW_PROPERTY(step, float);
RCT_EXPORT_VIEW_PROPERTY(trackImage, UIImage);
RCT_EXPORT_VIEW_PROPERTY(minimumTrackImage, UIImage);
RCT_EXPORT_VIEW_PROPERTY(maximumTrackImage, UIImage);
RCT_EXPORT_VIEW_PROPERTY(minimumValue, float);
RCT_EXPORT_VIEW_PROPERTY(maximumValue, float);
RCT_EXPORT_VIEW_PROPERTY(minimumTrackTintColor, UIColor);
RCT_EXPORT_VIEW_PROPERTY(maximumTrackTintColor, UIColor);
RCT_EXPORT_VIEW_PROPERTY(onValueChange, RCTBubblingEventBlock);
RCT_EXPORT_VIEW_PROPERTY(onSlidingComplete, RCTBubblingEventBlock);
RCT_EXPORT_VIEW_PROPERTY(thumbImage, UIImage);
RCT_EXPORT_VIEW_PROPERTY(minValue, float);
RCT_CUSTOM_VIEW_PROPERTY(disabled, BOOL, RCTSlider)
{
  if (json) {
    view.enabled = !([RCTConvert BOOL:json]);
  } else {
    view.enabled = defaultView.enabled;
  }
}

@end
