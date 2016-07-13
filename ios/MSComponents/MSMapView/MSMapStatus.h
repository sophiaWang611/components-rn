//
//  MSMapStatus.h
//  MishiOS
//
//  Created by SumFlower on 14/12/17.
//  Copyright (c) 2014年 ___MISHI___. All rights reserved.
//
typedef enum {
    GaoDeMap = 0,
    BaiDuMap,
    TengXunMap
}MapSelectStatus;
#import <Foundation/Foundation.h>

@interface MSMapStatus : NSObject
@property MapSelectStatus mapType;
@property (nonatomic, strong) NSString *des;
@property (nonatomic, strong) NSURL *Url;
-(instancetype)init:(MapSelectStatus)mapType andUrl:(NSURL *)Url;
@end
