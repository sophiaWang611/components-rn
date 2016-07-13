//
//  MSCustomAnnotationView.h
//  MishiOS
//
//  Created by SumFlower on 15/3/23.
//  Copyright (c) 2015年 ___MISHI___. All rights reserved.
//

#import <MAMapKit/MAMapKit.h>
#import "MSCustomCalloutView.h"

extern NSString *const MSCustomAnnotationViewNotification;


@interface MSCustomAnnotationView : MAAnnotationView
@property (nonatomic, strong) NSString *title;
@property (nonatomic, strong) NSString *describe;
@property (nonatomic, strong) UIImage *icon;
@property (nonatomic, strong) MSCustomCalloutView *calloutView;
@property (nonatomic) BOOL showGPSButton;

@end


