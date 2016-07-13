//
//  MSMapStatus.m
//  MishiOS
//
//  Created by SumFlower on 14/12/17.
//  Copyright (c) 2014年 ___MISHI___. All rights reserved.
//

#import "MSMapStatus.h"

@implementation MSMapStatus
-(instancetype)init:(MapSelectStatus)mapType andUrl:(NSURL *)Url
{
    self = [super init];
    if (self) {
        _mapType = mapType;
        _Url = Url;
    }
    return self;
    
}
-(NSString *)des
{
    switch (self.mapType) {
        case GaoDeMap:
            return @"高德地图";
            break;
        case BaiDuMap:
            return @"百度地图";
            break;
        case TengXunMap:
            return @"腾讯地图";
            break;
        default:
            break;
    }
}
@end
