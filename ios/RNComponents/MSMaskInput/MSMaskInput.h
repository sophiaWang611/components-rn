//
//  MSMaskInput.h
//  MishiBuyer
//
//  Created by sophia on 15/11/26.
//  Copyright © 2015年 Mishi Technology. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "RCTTextField.h"

@interface MSMaskInput : RCTTextField

@property (nonatomic,strong) NSString * mask;
@property (nonatomic,strong) NSString * regular;

@end
