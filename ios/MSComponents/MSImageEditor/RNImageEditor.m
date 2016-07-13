//
//  RNImageEditor.m
//  MishiBuyer
//
//  Created by sophia on 16/1/12.
//  Copyright © 2015年 Mishi Technology. All rights reserved.
//

#import "RNImageEditor.h"
#import "RCTUIManager.h"
#import "RCTView.h"
#import "RCTTouchHandler.h"
#import "UIView+React.h"
#import "RCTImageLoader.h"
#import <AssetsLibrary/ALAssetsLibrary.h>
#import "RCTUtils.h"
#import "UIImage+Resize.h"

@interface RNImageEditor()

@property (nonatomic, copy) RCTBubblingEventBlock onChange;

@end

@implementation RNImageEditor {
  UIScrollView *_imageEditorBaseScrollView;
  UIImageView *_imageEditorImageView;
  NSString *_imageSourceUri;
  NSNumber *_width;
  bool _hasRecord;
  bool _hasSetUrl;
}

- (instancetype)initWithFrame:(CGRect)frame
{
  if (([super initWithFrame:frame])) {
      _hasRecord = false;
      _hasSetUrl = false;
  }

  return self;
}

- (void)setImageSourceUri: (NSString*)imageSourceUri {
  _imageSourceUri = imageSourceUri;
  _hasSetUrl = true;
  
  NSData *data = [NSData dataWithContentsOfURL:[NSURL URLWithString:imageSourceUri]];
  UIImage *image = [UIImage imageWithData:data];
  
  CGFloat scrollWidth = _width == nil ? ([[UIScreen mainScreen] bounds].size.width) : [_width floatValue];
  CGFloat scrollHeight = scrollWidth * 3 / 4;
  CGFloat srcWidth = image.size.width;
  CGFloat srcHeight = image.size.height;
  CGFloat width = 1080;
  CGFloat height = width * 3 / 4;
  float widthRate = width*1.0/srcWidth;
  float heightRate = height*1.0/srcHeight;
  float rate = MAX(widthRate, heightRate);

  CGFloat targetWidth = srcWidth * rate;
  CGFloat targetHeight = srcHeight * rate;
  
  //redraw with new size
  UIImage *scaledImage = [image resizedImage:CGSizeMake(targetWidth, targetHeight)
                        interpolationQuality:kCGInterpolationHigh];
  
  widthRate = scrollWidth / targetWidth;
  heightRate = scrollHeight / targetHeight;
  rate = MAX(widthRate, heightRate);
  
  CGFloat imageViewWidth = targetWidth*rate;
  CGFloat imageViewHeight = targetHeight*rate;
  
  // Set image
  _imageEditorImageView = [[UIImageView alloc] initWithImage:scaledImage];
  _imageEditorImageView.frame = (CGRect){.origin=CGPointMake(0.0f, 0.0f), .size=CGSizeMake(imageViewWidth, imageViewHeight)};
  _imageEditorImageView.contentMode = UIViewContentModeScaleAspectFit;
  
  //init scroll view
  _imageEditorBaseScrollView = [[UIScrollView alloc] initWithFrame:[UIScreen mainScreen].bounds];
  _imageEditorBaseScrollView.delegate = self;
  _imageEditorBaseScrollView.scrollEnabled = YES;
  _imageEditorBaseScrollView.backgroundColor = [UIColor whiteColor];
  _imageEditorBaseScrollView.bounces = YES;
  _imageEditorBaseScrollView.clipsToBounds = YES;
  _imageEditorBaseScrollView.showsHorizontalScrollIndicator = NO;
  _imageEditorBaseScrollView.showsVerticalScrollIndicator = NO;

  [_imageEditorBaseScrollView addSubview:_imageEditorImageView];
  
  _imageEditorBaseScrollView.contentSize = scaledImage.size;
  _imageEditorBaseScrollView.frame = (CGRect){.size=CGSizeMake(scrollWidth, scrollHeight)};
  _imageEditorBaseScrollView.minimumZoomScale = 1;
  _imageEditorBaseScrollView.maximumZoomScale = 8.0;
  [_imageEditorBaseScrollView setZoomScale:_imageEditorBaseScrollView.minimumZoomScale];
  
  CGFloat x = (imageViewWidth - scrollWidth) * 0.5;
  CGFloat y = (imageViewHeight - scrollHeight) * 0.5;
  [_imageEditorBaseScrollView setContentOffset:CGPointMake(x, y) animated:NO];
  
  [self addSubview: _imageEditorBaseScrollView];
  [self saveImage];
}

- (UIView*)viewForZoomingInScrollView:(UIScrollView *)scrollView
{
  for (id view in [_imageEditorBaseScrollView subviews]) {
    if ([view isKindOfClass:[UIImageView class]]) {
      return view;
    }
  }
  return  nil;
}

- (void)scrollViewDidEndDragging:(UIScrollView *)scrollView willDecelerate:(BOOL)decelerate
{
  _hasRecord = !decelerate;
  if (_hasRecord) {
    [self saveImage];
  }
}

- (void)scrollViewDidEndDecelerating:(UIScrollView *)scrollView
{
  if (!_hasRecord) {
    [self saveImage];
  }
}

- (void)scrollViewDidZoom:(UIScrollView *)scrollView
{
  [self saveImage];
}

- (void)saveImage
{
  CGSize screenShotSize = _imageEditorBaseScrollView.frame.size;
  UIImage *img;
  
  UIGraphicsBeginImageContextWithOptions(screenShotSize, NO, 2);
  
  CGContextRef context = UIGraphicsGetCurrentContext();
  CGContextSaveGState(context);

  
  CALayer *imgLayer = _imageEditorBaseScrollView.layer;
  CGPoint offset =  ((UIScrollView *)_imageEditorBaseScrollView).contentOffset;
  CGContextTranslateCTM(context, -offset.x, -offset.y);
  [imgLayer renderInContext:context];
  img = UIGraphicsGetImageFromCurrentImageContext();
  
  CGContextRestoreGState(context);
  
  UIGraphicsEndImageContext();
  
  NSMutableDictionary *response = [[NSMutableDictionary alloc] init];
  
  // base64 encoded image string
  NSData *data = UIImageJPEGRepresentation(img, 1);
  NSString *dataString = [data base64EncodedStringWithOptions:0];
  [response setObject:dataString forKey:@"data"];
  
  // file uri
  /* creating a temp url to be passed */
  NSString *ImageUUID = [[NSUUID UUID] UUIDString];
  NSString *ImageName = [ImageUUID stringByAppendingString:@".jpg"];
  NSString* path = [[NSTemporaryDirectory()stringByStandardizingPath] stringByAppendingPathComponent:ImageName];
  
  [data writeToFile:path atomically:YES];
  NSString *fileURL = [[NSURL fileURLWithPath:path] absoluteString];
  [response setObject:fileURL forKey:@"uri"];

  if (_onChange) {
    _onChange(@{@"data": response});
  }
}

- (void)setWidth:(NSNumber *)width {
  _width = width;
}

- (void)setOnChange:(RCTBubblingEventBlock)onChange {
  _onChange = onChange;
  if (_hasSetUrl) {
     [self saveImage];
  }
}

@end
