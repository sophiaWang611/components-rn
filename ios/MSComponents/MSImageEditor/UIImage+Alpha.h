//
//  UIImage+Alpha.h
//  UITest
//
//  Created by mars.tsang on 14-12-4.
//  Copyright (c) 2014年 ___MISHI___. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIImage (Alpha )

- (BOOL)hasAlpha;
- (UIImage *)imageWithAlpha;
- (UIImage *)transparentBorderImage:(NSUInteger)borderSize;

@end
