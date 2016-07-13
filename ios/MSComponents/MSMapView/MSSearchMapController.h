//
//  MSSearchMapController.h
//  MishiBuyer
//
//  Created by sophia on 15/12/28.
//  Copyright © 2015年 Mishi Technology. All rights reserved.
//

#import "MSAdjustViewController.h"
#import "MSAddress.h"
#import "RCTBridge.h"

@interface MSSearchMapController : MSAdjustViewController

@property (nonatomic, strong) MSAddress *address;
@property (nonatomic,strong) RCTResponseSenderBlock callback;

@end
