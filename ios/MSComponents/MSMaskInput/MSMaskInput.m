//
//  MSMaskInput.m
//  MishiBuyer
//
//  Created by sophia on 15/11/26.
//  Copyright © 2015年 Mishi Technology. All rights reserved.
//

#import "MSMaskInput.h"
#import "RCTTextField.h"
#import "RCTConvert.h"

@implementation MSMaskInput : RCTTextField

//- (void)setSelectionRange:(NSDictionary *)selectionRange
//{
//  if (selectionRange == nil) {
//    return;
//  }
//  NSInteger selectionStart = [RCTConvert NSInteger:selectionRange[@"start"]];
//  NSInteger selectionEnd = [RCTConvert NSInteger:selectionRange[@"end"]];
//  UITextPosition *start = [super positionFromPosition:[super beginningOfDocument]
//                                                   offset:selectionStart];
//  UITextPosition *end = [super positionFromPosition:[super beginningOfDocument]
//                                                 offset:selectionEnd];
//  UITextRange *selection = [super textRangeFromPosition:start toPosition:end];
//  super.selectedTextRange = selection;
//}

@end
