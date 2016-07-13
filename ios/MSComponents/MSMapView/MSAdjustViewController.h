//
//  MSAdjustViewController.h
//  MishiApp
//
//  Created by SumFlower on 14/10/28.
//  Copyright (c) 2014年 ___MISHI___. All rights reserved.
//

#import <UIKit/UIKit.h>
//#import "MS.h"
@interface MSAdjustViewController : UIViewController

//右边buttion字体颜色
@property (nonatomic, strong) UIColor *rightStringColor;
//左右button是否可用
@property (nonatomic, assign) BOOL rightButtonItemEnable;
@property (nonatomic, assign) BOOL leftButtonItemEnable;
//设置左右button的图片
- (void)setLeftNavBarItemWithImageName:(NSString *)name;
- (void)setRightNavBarItemWithImageName:(NSString *)name;

//子类可能重写的方法
- (IBAction)back:(id)sender;
- (IBAction)rightBarTouched:(id)sender;
//设置右边button的字
-(void)setRightNavBarItemWithStr:(NSString *)str;
@end
