//
//  RNImageCrop.m
//  MishiBuyer
//
//  Created by sophia on 16/1/15.
//  Copyright © 2016年 Mishi Technology. All rights reserved.
//

#import "RNImageCrop.h"
#import "UIImage+Resize.h"

@implementation RNImageCrop

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(cropImage: (float)rate path:(NSString *)path
                  callback:(RCTResponseSenderBlock)callback)
{
  UIImage *img = [self cropByPathRate:rate path:path];
  
  // file uri
  // base64 encoded image string
  NSData *data = UIImageJPEGRepresentation(img, 0.2);
  NSString *ImageUUID = [[NSUUID UUID] UUIDString];
  NSString *ImageName = [ImageUUID stringByAppendingString:@".jpg"];
  NSString* newPath = [[NSTemporaryDirectory()stringByStandardizingPath] stringByAppendingPathComponent:ImageName];
  
  [data writeToFile:newPath atomically:YES];
  NSString *fileURL = [[NSURL fileURLWithPath:newPath] absoluteString];
  
  NSMutableDictionary *searchResult = [[NSMutableDictionary alloc] init];
  [searchResult setObject:fileURL forKey:@"filePath"];
  NSMutableArray *results = [[NSMutableArray alloc] init];
  [results addObject:searchResult];
  callback(results);
}

-(UIImage*) cropByPathRate: (float)rate path:(NSString *)path {
  NSData *data = [NSData dataWithContentsOfURL:[NSURL URLWithString:path]];
  UIImage *image = [UIImage imageWithData:data];
  
  CGFloat scrollWidth = 1080;
  CGFloat scrollHeight = scrollWidth * rate;
  
  CGFloat srcWidth = image.size.width;
  CGFloat srcHeight = image.size.height;
  
  float widthRate = scrollWidth*1.0/srcWidth;
  float heightRate = scrollHeight*1.0/srcHeight;
  float imgRate = MAX(widthRate, heightRate);
  
  CGFloat targetWidth = srcWidth * imgRate;
  CGFloat targetHeight = srcHeight * imgRate;
  
  UIImage *resizedImage = [image resizedImage:CGSizeMake(targetWidth, targetHeight) interpolationQuality:kCGInterpolationHigh];
  
  CGFloat x = (targetWidth - scrollWidth) * 0.5;
  CGFloat y = (targetHeight - scrollHeight) * 0.5;

  return [resizedImage croppedImage:CGRectMake(x, y, scrollWidth, scrollHeight)];
  
}

@end
