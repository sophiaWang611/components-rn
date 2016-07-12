//
//  MSHtmlTxtView.m
//  MishiBuyer
//
//  Created by sophia on 16/3/18.
//  Copyright © 2016年 Mishi Technology. All rights reserved.
//

#import "MSHtmlTxtView.h"

@interface MSHtmlTxtView()

@property (nonatomic, strong) UILabel *htmlLabel;

@end

@implementation MSHtmlTxtView {
  CGSize _size;
}

- (instancetype)initWithFrame:(CGRect)frame
{
  if ((self = [super initWithFrame:frame])) {
    _size = CGSizeMake(0.0f, 0.0f);
    self.htmlLabel = [[UILabel alloc] initWithFrame:self.bounds];
    [self addSubview:self.htmlLabel];
  }
  return self;
}

- (void) setText:(NSString *)text {
  self.htmlLabel.attributedText = [[NSAttributedString alloc] initWithData:[text dataUsingEncoding:NSUnicodeStringEncoding]
                                                              options:@{NSDocumentTypeDocumentAttribute: NSHTMLTextDocumentType}
                                                   documentAttributes:nil
                                                                error:nil];

  CGSize titleSize = [text sizeWithFont:self.htmlLabel.font
                      constrainedToSize:CGSizeMake(MAXFLOAT, MAXFLOAT)
                          lineBreakMode:NSLineBreakByWordWrapping];
  
  if (titleSize.height > _size.height) {
    _size = CGSizeMake(_size.width + titleSize.width, _size.height + titleSize.height);
    self.htmlLabel.frame = (CGRect){.size = _size};
  }
}

- (void) setPaddingTop:(NSNumber *)paddingTop {
  if (!paddingTop) return;
  _size = CGSizeMake(_size.width, _size.height + [paddingTop floatValue]);
  self.htmlLabel.frame = (CGRect){.size = _size};
}

- (void) setPaddingBottom:(NSNumber *)paddingBottom {
  if (!paddingBottom) return;
  _size = CGSizeMake(_size.width, _size.height + [paddingBottom floatValue]);
  self.htmlLabel.frame = (CGRect){.size = _size};
}

- (void) setPaddingLeft:(NSNumber *)paddingLeft {
  if (!paddingLeft) return;
  _size = CGSizeMake(_size.width + [paddingLeft floatValue], _size.height);
  self.htmlLabel.frame = (CGRect){.size = _size};
}

- (void) setPaddingRight:(NSNumber *)paddingRight {
  if (!paddingRight) return;
  _size = CGSizeMake(_size.width + [paddingRight floatValue], _size.height);
  self.htmlLabel.frame = (CGRect){.size = _size};
}

@end