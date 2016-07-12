//
//  MSMaskInput.m
//  MishiBuyer
//
//  Created by sophia on 15/11/26.
//  Copyright © 2015年 Mishi Technology. All rights reserved.
//

#import "MSMaskInputManager.h"
//#import "MSMaskInput.h"
#import "RCTBridge.h"
#import "RCTConvert.h"
#import "RCTShadowView.h"
#import "MSMaskInput.h"

@interface MSMaskInputManager() <UITextFieldDelegate>

@end

@implementation MSMaskInputManager

RCT_EXPORT_MODULE()

- (UIView *)view
{
  MSMaskInput *textField = [[MSMaskInput alloc] initWithEventDispatcher:self.bridge.eventDispatcher];
  textField.delegate = self;
  return textField;
}

- (BOOL)textField:(MSMaskInput *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string
{
  if (textField.mask == nil && textField.regular == nil) {
    if (textField.maxLength == nil || [string isEqualToString:@"\n"]) {  // Make sure forms can be submitted via return
      return YES;
    }
    NSUInteger allowedLength = textField.maxLength.integerValue - textField.text.length + range.length;
    if (string.length > allowedLength) {
      if (string.length > 1) {
        // Truncate the input string so the result is exactly maxLength
        NSString *limitedString = [string substringToIndex:allowedLength];
        NSMutableString *newString = textField.text.mutableCopy;
        [newString replaceCharactersInRange:range withString:limitedString];
        textField.text = newString;
        // Collapse selection at end of insert to match normal paste behavior
        UITextPosition *insertEnd = [textField positionFromPosition:textField.beginningOfDocument
                                                             offset:(range.location + allowedLength)];
        textField.selectedTextRange = [textField textRangeFromPosition:insertEnd toPosition:insertEnd];
        [textField textFieldDidChange];
      }
      return NO;
    } else {
      return YES;
    }
  }
  
  NSString * currentTextDigited = [textField.text stringByReplacingCharactersInRange:range withString:string];
  if (string.length == 0) {
    unichar lastCharDeleted = 0;
    while (currentTextDigited.length > 0 && !isnumber([currentTextDigited characterAtIndex:currentTextDigited.length-1])) {
      lastCharDeleted = [currentTextDigited characterAtIndex:[currentTextDigited length] - 1];
      currentTextDigited = [currentTextDigited substringToIndex:[currentTextDigited length] - 1];
    }
    textField.text = currentTextDigited;
    [textField textFieldDidChange];
    return NO;
  }
  
  if (textField.regular != nil && textField.regular.length > 0) {
    NSPredicate *predicate = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", textField.regular];
    return [predicate evaluateWithObject: currentTextDigited];
  }
  
  NSMutableString * returnText = [[NSMutableString alloc] init];
  if (currentTextDigited.length > textField.mask.length) {
    return NO;
  }
  
  int last = 0;
  BOOL needAppend = NO;
  for (int i = 0; i < currentTextDigited.length; i++) {
    unichar  currentCharMask = [textField.mask characterAtIndex:i];
    unichar  currentChar = [currentTextDigited characterAtIndex:i];
    if (isnumber(currentChar) && currentCharMask == '#') {
      [returnText appendString:[NSString stringWithFormat:@"%c",currentChar]];
    }else{
      if (currentCharMask == '#') {
        break;
      }
      if (isnumber(currentChar) && currentCharMask!= currentChar) {
        needAppend = YES;
      }
      [returnText appendString:[NSString stringWithFormat:@"%c",currentCharMask]];
    }
    last = i;
  }
  
  for (int i = last+1; i < textField.mask.length; i++) {
    unichar currentCharMask = [textField.mask characterAtIndex:i];
    if (currentCharMask != '#') {
      [returnText appendString:[NSString stringWithFormat:@"%c",currentCharMask]];
    }
    if (currentCharMask == '#') {
      break;
    }
  }
  if (needAppend) {
    [returnText appendString:string];
  }
  textField.text = returnText;
  
  // Collapse selection at end of insert to match normal paste behavior
  UITextPosition *insertEnd = [textField positionFromPosition:textField.beginningOfDocument
                                                       offset:(returnText.length)];
  textField.selectedTextRange = [textField textRangeFromPosition:insertEnd toPosition:insertEnd];
  [textField textFieldDidChange];
  
  return NO;
}

RCT_EXPORT_VIEW_PROPERTY(regular, NSString)
RCT_EXPORT_VIEW_PROPERTY(mask, NSString)
RCT_EXPORT_VIEW_PROPERTY(caretHidden, BOOL)
RCT_EXPORT_VIEW_PROPERTY(autoCorrect, BOOL)
RCT_REMAP_VIEW_PROPERTY(editable, enabled, BOOL)
RCT_EXPORT_VIEW_PROPERTY(placeholder, NSString)
RCT_EXPORT_VIEW_PROPERTY(placeholderTextColor, UIColor)
RCT_EXPORT_VIEW_PROPERTY(text, NSString)
RCT_EXPORT_VIEW_PROPERTY(maxLength, NSNumber)
RCT_EXPORT_VIEW_PROPERTY(clearButtonMode, UITextFieldViewMode)
RCT_REMAP_VIEW_PROPERTY(clearTextOnFocus, clearsOnBeginEditing, BOOL)
RCT_EXPORT_VIEW_PROPERTY(selectTextOnFocus, BOOL)
RCT_EXPORT_VIEW_PROPERTY(keyboardType, UIKeyboardType)
RCT_EXPORT_VIEW_PROPERTY(returnKeyType, UIReturnKeyType)
RCT_EXPORT_VIEW_PROPERTY(enablesReturnKeyAutomatically, BOOL)
RCT_EXPORT_VIEW_PROPERTY(secureTextEntry, BOOL)
RCT_REMAP_VIEW_PROPERTY(password, secureTextEntry, BOOL) // backwards compatibility
RCT_REMAP_VIEW_PROPERTY(color, textColor, UIColor)
RCT_REMAP_VIEW_PROPERTY(autoCapitalize, autocapitalizationType, UITextAutocapitalizationType)
RCT_REMAP_VIEW_PROPERTY(textAlign, textAlignment, NSTextAlignment)
RCT_CUSTOM_VIEW_PROPERTY(fontSize, CGFloat, RCTTextField)
{
  view.font = [RCTConvert UIFont:view.font withSize:json ?: @(defaultView.font.pointSize)];
}
RCT_CUSTOM_VIEW_PROPERTY(fontWeight, NSString, __unused RCTTextField)
{
  view.font = [RCTConvert UIFont:view.font withWeight:json]; // defaults to normal
}
RCT_CUSTOM_VIEW_PROPERTY(fontStyle, NSString, __unused RCTTextField)
{
  view.font = [RCTConvert UIFont:view.font withStyle:json]; // defaults to normal
}
RCT_CUSTOM_VIEW_PROPERTY(fontFamily, NSString, RCTTextField)
{
  view.font = [RCTConvert UIFont:view.font withFamily:json ?: defaultView.font.familyName];
}
RCT_EXPORT_VIEW_PROPERTY(mostRecentEventCount, NSInteger)

- (RCTViewManagerUIBlock)uiBlockToAmendWithShadowView:(RCTShadowView *)shadowView
{
  NSNumber *reactTag = shadowView.reactTag;
  UIEdgeInsets padding = shadowView.paddingAsInsets;
  return ^(__unused RCTUIManager *uiManager, NSDictionary<NSNumber *, RCTSparseArray *> *viewRegistry) {
    ((RCTTextField *)viewRegistry[reactTag]).contentInset = padding;
  };
}

@end
