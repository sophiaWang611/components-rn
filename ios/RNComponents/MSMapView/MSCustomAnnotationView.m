//
//  MSCustomAnnotationView.m
//  MishiOS
//
//  Created by SumFlower on 15/3/23.
//  Copyright (c) 2015年 ___MISHI___. All rights reserved.
//
#define kWidth 29
#define kHeight 29
#define kCallViewWidth 225
#define kCallViewHeight 52

#import "MSCustomAnnotationView.h"
#import "MSColor.h"
#import "MSFont.h"

NSString *const MSCustomAnnotationViewNotification = @"MSCustomAnnotationViewNotification";

@interface MSCustomAnnotationView()

@property (nonatomic, strong) UIImageView *iconImageView;

@end



@implementation MSCustomAnnotationView
@synthesize title = _title;
@synthesize describe = _describe;
/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect {
    // Drawing code
}
*/
#pragma mark -- override

-(UIImage *)icon
{
    return self.iconImageView.image;
}

-(void)setIcon:(UIImage *)icon
{
    self.iconImageView.image = icon;
}

#pragma mark -- select
-(void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    if (selected) {
        if (self.calloutView == nil) {
            self.calloutView = [[MSCustomCalloutView alloc] initWithFrame:CGRectMake(0, 0, kCallViewWidth, kCallViewHeight)];
            self.calloutView.center = CGPointMake(CGRectGetWidth(self.bounds) / 2.f + self.calloutOffset.x,
                                                  -CGRectGetHeight(self.calloutView.bounds) / 2.f + self.calloutOffset.y);
            [self addLayout:self.calloutView];
        }
        [self addSubview:self.calloutView];
    }
    [super setSelected:selected animated:animated];
    
}

#pragma mark -- custom
-(id)initWithAnnotation:(id<MAAnnotation>)annotation reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithAnnotation:annotation reuseIdentifier:reuseIdentifier];
    if (self) {
        self.bounds = CGRectMake(0.f, 0.f, kWidth, kHeight);
        self.iconImageView = [[UIImageView alloc] initWithFrame:CGRectMake(0, 0, kWidth, kHeight)];
        [self addSubview:self.iconImageView];
    }
    return self;
}
#pragma mark -- UI
-(void)addLayout:(MSCustomCalloutView *)view
{


//    [view addConstraint:[NSLayoutConstraint constraintWithItem:titleLabel
//                                                     attribute:NSLayoutAttributeHeight
//                                                     relatedBy:NSLayoutRelationEqual
//                                                        toItem:nil
//                                                     attribute:NSLayoutAttributeNotAnAttribute
//                                                    multiplier:0
//                                                      constant:12.0f]];

    /*
    UILabel *describeLabel = [[UILabel alloc] init];
    describeLabel.font = [MSFont xsmall];
    describeLabel.textColor = [MSColor darkGray];
    describeLabel.textAlignment = NSTextAlignmentLeft;
    describeLabel.translatesAutoresizingMaskIntoConstraints = NO;
    describeLabel.text = self.describe;
    [view addSubview:describeLabel];
    [view addConstraint:[NSLayoutConstraint constraintWithItem:describeLabel
                                                     attribute:NSLayoutAttributeTop
                                                     relatedBy:NSLayoutRelationEqual
                                                        toItem:titleLabel
                                                     attribute:NSLayoutAttributeBottom
                                                    multiplier:1
                                                      constant:4.0f]];
    [view addConstraint:[NSLayoutConstraint constraintWithItem:describeLabel
                                                     attribute:NSLayoutAttributeLeft
                                                     relatedBy:NSLayoutRelationEqual
                                                        toItem:view
                                                     attribute:NSLayoutAttributeLeft
                                                    multiplier:1
                                                      constant:15.0f]];
    [view addConstraint:[NSLayoutConstraint constraintWithItem:describeLabel
                                                     attribute:NSLayoutAttributeWidth
                                                     relatedBy:NSLayoutRelationEqual
                                                        toItem:nil
                                                     attribute:NSLayoutAttributeNotAnAttribute
                                                    multiplier:1
                                                      constant:114.0f]];
    [view addConstraint:[NSLayoutConstraint constraintWithItem:describeLabel
                                                     attribute:NSLayoutAttributeHeight
                                                     relatedBy:NSLayoutRelationEqual
                                                        toItem:nil
                                                     attribute:NSLayoutAttributeNotAnAttribute
                                                    multiplier:0
                                                      constant:10.0f]];
     */
  UIButton *GPSButton = nil;
  if (_showGPSButton == YES) {
    GPSButton = [UIButton buttonWithType:UIButtonTypeCustom];
    GPSButton.layer.masksToBounds = YES;
    GPSButton.layer.cornerRadius = 2.0;
    [GPSButton setTitleColor:[MSColor white] forState:UIControlStateNormal];
    [GPSButton setTitleColor:[MSColor white] forState:UIControlStateHighlighted];
    [GPSButton setTitle:@"导航" forState:UIControlStateNormal];
    [GPSButton setTitle:@"导航" forState:UIControlStateHighlighted];
    [GPSButton addTarget:self action:@selector(GPSAction:) forControlEvents:UIControlEventTouchUpInside];
    [GPSButton setBackgroundColor:[MSColor mishiGreen]];
    GPSButton.titleLabel.font = [MSFont small];
    GPSButton.translatesAutoresizingMaskIntoConstraints = NO;
    [view addSubview:GPSButton];
    [view addConstraint:[NSLayoutConstraint constraintWithItem:GPSButton
                                                     attribute:NSLayoutAttributeTop
                                                     relatedBy:NSLayoutRelationEqual
                                                        toItem:view
                                                     attribute:NSLayoutAttributeTop
                                                    multiplier:1
                                                      constant:9.0f]];
    [view addConstraint:[NSLayoutConstraint constraintWithItem:GPSButton
                                                     attribute:NSLayoutAttributeRight
                                                     relatedBy:NSLayoutRelationEqual
                                                        toItem:view
                                                     attribute:NSLayoutAttributeRight
                                                    multiplier:1
                                                      constant:-15.0f]];
    [view addConstraint:[NSLayoutConstraint constraintWithItem:GPSButton
                                                     attribute:NSLayoutAttributeHeight
                                                     relatedBy:NSLayoutRelationEqual
                                                        toItem:nil
                                                     attribute:NSLayoutAttributeNotAnAttribute
                                                    multiplier:0
                                                      constant:24.0f]];
    [view addConstraint:[NSLayoutConstraint constraintWithItem:GPSButton
                                                     attribute:NSLayoutAttributeWidth
                                                     relatedBy:NSLayoutRelationEqual
                                                        toItem:nil
                                                     attribute:NSLayoutAttributeNotAnAttribute
                                                    multiplier:0
                                                      constant:51]];
  }


//    self.title = @"光宇上东城光宇上东城光宇上东城光宇上东城";
    UILabel *titleLabel = [[UILabel alloc] init];
    titleLabel.numberOfLines = 2;
    titleLabel.textAlignment = NSTextAlignmentLeft;
    titleLabel.font = [MSFont small];
    titleLabel.text = self.title;
    titleLabel.textColor = [MSColor black];
    titleLabel.translatesAutoresizingMaskIntoConstraints = NO;
    [view addSubview:titleLabel];

    [view addConstraint:[NSLayoutConstraint constraintWithItem:titleLabel
                                                     attribute:NSLayoutAttributeTop
                                                     relatedBy:NSLayoutRelationEqual
                                                        toItem:view
                                                     attribute:NSLayoutAttributeTop
                                                    multiplier:1
                                                      constant:9.0f]];
    [view addConstraint:[NSLayoutConstraint constraintWithItem:titleLabel
                                                     attribute:NSLayoutAttributeLeft
                                                     relatedBy:NSLayoutRelationEqual
                                                        toItem:view
                                                     attribute:NSLayoutAttributeLeft
                                                    multiplier:1
                                                      constant:15.0f]];
    [view addConstraint:[NSLayoutConstraint constraintWithItem:titleLabel
                                                     attribute:NSLayoutAttributeWidth
                                                     relatedBy:NSLayoutRelationEqual
                                                        toItem:nil
                                                     attribute:NSLayoutAttributeNotAnAttribute
                                                    multiplier:1
                                                      constant:114.0f]];
    if (self.title.length <= 9 && GPSButton != Nil) {
        [view addConstraint:[NSLayoutConstraint constraintWithItem:titleLabel
                                                         attribute:NSLayoutAttributeCenterY
                                                         relatedBy:NSLayoutRelationEqual
                                                            toItem:GPSButton
                                                         attribute:NSLayoutAttributeCenterY
                                                        multiplier:1
                                                          constant:0.0]];

    }

}
-(void)GPSAction:(UIButton *)button
{
    [[NSNotificationCenter defaultCenter] postNotificationName:MSCustomAnnotationViewNotification object:nil];
}
@end
