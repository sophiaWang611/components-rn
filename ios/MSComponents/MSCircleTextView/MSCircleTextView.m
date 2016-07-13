//
//  MSCircleTextView.m
//  MishiBuyer
//
//  Created by sophia on 16/1/13.
//  Copyright © 2016年 Mishi Technology. All rights reserved.
//

#import "MSCircleTextView.h"
#import "MSFont.h"

@implementation MSCircleTextView
{
  UILabel *_textView;
}

- (instancetype)initWithFrame:(CGRect)frame
{
  if ((self = [super initWithFrame:CGRectMake(0.0f, 0.0f, 16, 16)])) {
    
    _textView = [[UILabel alloc] initWithFrame:self.bounds];
    _textView.backgroundColor = [UIColor colorWithRed:247/255.0 green:65/255.0 blue:99/255.0 alpha:1];//f74163
    _textView.textColor = [UIColor whiteColor];
    _textView.font = [MSFont xboldSmall];
    _textView.layer.masksToBounds = YES;
    _textView.layer.cornerRadius = 8;
    _textView.textAlignment = NSTextAlignmentCenter;
  }
  return self;
}

- (void) setText:(NSString *)text {
  [_textView setText:text];
  [self addSubview:_textView];
}

- (void) setColor:(UIColor *)color {
  _textView.backgroundColor = color;
}

- (void) setWidth:(float)width {
  _textView.frame = CGRectMake(0.0f, 0.0f, width, width);
  _textView.layer.cornerRadius = width/2;
}

- (void) setFontSize:(float)fontSize {
  _textView.font = [UIFont systemFontOfSize:fontSize];
}

@end

