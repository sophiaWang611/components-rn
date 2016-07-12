//
//  MSColor.m
//  Packaging
//
//  Created by SumFlower on 14-10-22.
//  Copyright (c) 2014年 夏敏. All rights reserved.
//
#define kColor(r,g,b,a) [UIColor colorWithRed:r/255.0 green:g/255.0 blue:b/255.0 alpha:a]

#7979789

#import "MSColor.h"

@implementation MSColor

+(UIColor *)f4White {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#f4f4f4" andClear:1];
    return color;
}
+(UIColor *)alpha5White {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#ffffff" andClear:.5];
    return color;
}
+(UIColor *)alpha6White {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#ffffff" andClear:.6];
    return color;
}
+(UIColor *)alpha8White {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#ffffff" andClear:.8];
    return color;
}
+(UIColor *)alpha95White {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#ffffff" andClear:.95];
    return color;
}
+(UIColor *)alpha90White {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#ffffff" andClear:.9];
    return color;
}
+(UIColor *)alpha05White {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#ffffff" andClear:.05];
    return color;
}
+(UIColor *)alpha04White {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#ffffff" andClear:.04];
    return color;
}
+(UIColor *)white {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#ffffff" andClear:1];
    return color;
}
+(UIColor *)lineWhite {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#aaaaaa" andClear:1];
    return color;
}

+(UIColor*)disableWhite
{
    return [[self class] transformHexadecimalTodecimalism:@"#9fde65" andClear:1];
}
+(UIColor *)alpha8Black {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#444444" andClear:.8];
    return color;
}
+(UIColor *)alpha6Black {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#444444" andClear:.6];
    return color;
}
+(UIColor *)black {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#444444" andClear:1];
    return color;
}
+(UIColor *)c7Black {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#000000" andClear:.7];
    return color;
}
+(UIColor *)c6Black {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#000000" andClear:.6];
    return color;
}
+(UIColor *)c8Black {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#000000" andClear:.8];
    return color;
}
+(UIColor *)c5Black {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#000000" andClear:.5];
    return color;
}

+(UIColor *)black9Alpha {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#444444" andClear:.9];
    return color;
}
+(UIColor *)black8Alpha {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#444444" andClear:.8];
    return color;
}
+(UIColor *)blackAlpha {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#444444" andClear:.4];
    return color;
}
+(UIColor *)blackHAlpha {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#000000" andClear:.4];
    return color;
}
+(UIColor *)cBlack {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#000000" andClear:1];
    return color;
}

+(UIColor *)blackxAlpha {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#444444" andClear:.3];
    return color;
}

+ (UIColor *)blackGray{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#66a22c" andClear:1];
    return color;
}
+ (UIColor *)seprateLineGray{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#d2d2d2" andClear:1];
    return color;
}
+ (UIColor *)lineGray{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#d7d7d7" andClear:1];
    return color;
}

+(UIColor *)darkGray {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#777777" andClear:1];
    return color;
}
+(UIColor *)xlgray {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#666666" andClear:1];
    return color;
}
+(UIColor *)gray {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#999999" andClear:1];
    return color;
}
+(UIColor *)separatorGray {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#e1e1e1" andClear:1];
    return color;
}
+(UIColor *)lightxGray {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#ececec" andClear:1];
    return color;
}
+(UIColor *)lightEBGray {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#ebebeb" andClear:1];
    return color;
}
+(UIColor *)lightGray {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#cccccc" andClear:1];
    return color;
}
+(UIColor *)lightAlphaGray {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#cccccc" andClear:.4];
    return color;
}
+(UIColor *)lightAlpha95Gray {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#cccccc" andClear:.95];
    return color;
}
+(UIColor *)lightAlpha50Gray {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#cccccc" andClear:.5];
    return color;
}
+(UIColor *)headergray{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#949aa6" andClear:1];
    return color;
}
+(UIColor *)xxblue {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#007aff" andClear:1];
    return color;
}
+(UIColor *)blue {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#00aaff" andClear:1];
    return color;
}
+(UIColor *)alpha4blue
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#008aff" andClear:.4];
    return color;
}
+(UIColor *)twoblue{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#5172a3" andClear:1];
    return color;
}
+(UIColor *)tagBlue{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#0089ff" andClear:1];
    return color;
}
+(UIColor*)threeBlue
{
    return [[self class] transformHexadecimalTodecimalism:@"#666682" andClear:1];
}
+(UIColor *)alpha95Orange {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#ffaa30" andClear:.95];
    return color;
}
+(UIColor *)orange {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#ffaa30" andClear:1];
    return color;
}
+(UIColor *)aliOrange{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#ff6c00" andClear:1];
    return color;
}
+(UIColor *)dashLineColor {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#f6f6f6" andClear:1];
    return color;
}
+(UIColor *)red {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#f74163" andClear:1];
    return color;
}

+(UIColor *)tasteRed
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#f75c2f" andClear:1];
    return color;
}

+(UIColor *)wineRed
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#a05a62" andClear:1];
    return color;
}

+(UIColor *)priceColor
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#60a031" andClear:1];
    return color;
}
+(UIColor *)alphaGreen {
    
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#76b43c" andClear:.95];
    return color;
}
+(UIColor *)mishiGreen
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#21af5e" andClear:1];
    return color;
}
+(UIColor *)mishiPressGreen {
    
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#198042" andClear:1];
    return color;
}
+(UIColor *)baseLineGreen{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#21ad5a" andClear:1];
    return color;
}

+(UIColor *)unSelectedColor{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#f7f7f7" andClear:1];
    return color;
}
+ (UIColor *)mishiNorGreen {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#21af5e" andClear:1];
    return color;
}
+(UIColor *)alpha5Green {
    
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#76b43c" andClear:.5];
    return color;
}
+(UIColor *)alpha80Green {
    
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#76b43c" andClear:.80];
    return color;
}

+(UIColor *)darkGreen {
    
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#64a12c" andClear:1];
    return color;
}

+(UIColor *)lineGreen {
    
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#66a22e" andClear:1];
    return color;
}

+ (UIColor *)grayWhite
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#f5f5f5" andClear:1];
    return color;
}
+(UIColor *)dark36Green {
    
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#689e36" andClear:1];
    return color;
}
+(UIColor *)green {
    
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#76b43c" andClear:1];
    return color;
}
+(UIColor *)searchGreen {
    
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#198042" andClear:1];
    return color;
}
+(UIColor *)buyGiveGreen
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#53c96f" andClear:1];
    return color;
}
+(UIColor *)tagGreen
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#7cb745" andClear:1];
    return color;
}
+(UIColor *)blackGreen {
    
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#527235" andClear:1];
    return color;
}

+(UIColor *)tabGray{
    
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#888888" andClear:1];
    return color;
}
+(UIColor *)dcBargray
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#dcdcdc" andClear:1];
    return color;
}
+(UIColor *)alphadcBargray
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#dcdcdc" andClear:.95];
    return color;
    
}
+(UIColor *)bargray
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#fafafa" andClear:1];
    return color;
    
}
+(UIColor *)barAlphaGray
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#fafafa" andClear:.95];
    return color;
    
}
+(UIColor *)background
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#f7f7f7" andClear:1];
    return color;
}
+(UIColor *)timelabelGray
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#cecece" andClear:1];
    return color;
}
+(UIColor *)xgray
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#999999" andClear:1];
    return color;
}
+(UIColor *)tipGray
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#95979d" andClear:1];
    return color;
}
+(UIColor *)xblue
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#00aaff" andClear:1];
    return color;
}
+(UIColor *)barxGray
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#e2e2e2" andClear:1];
    return color;
}
+(UIColor *)xGray
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#dddddd" andClear:1];
    return color;
}
+(UIColor *)smokeAlpha95Gray
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#eeeeee" andClear:.95];
    return color;
}
+(UIColor *)smokeGray
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#eeeeee" andClear:1];
    return color;
}
+(UIColor *)grayBlue
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#5e9030" andClear:1];
    return color;
}
+(UIColor *)sheetGray
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#edeef1" andClear:1];
    return color;
}
+(UIColor *)dotgray
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#ededed" andClear:1];
    return color;
}
+(UIColor *)sectionGray
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#ededf0" andClear:1];
    return color;
}
+(UIColor *)topScrollCateGray
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#e3e3e3" andClear:1];
    return color;
}
+(UIColor *)listGray
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#fafafa" andClear:1];
    return color;
}
+(UIColor *)xxGray
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#66a030" andClear:1];
    return color;
}
+(UIColor *)xgreen
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#558528" andClear:1];
    return color;
}
+(UIColor *)xorange
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#f79741" andClear:1];
    return color;
}
+(UIColor *)xxorange
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#f8d33f" andClear:1];
    return color;
}
+(UIColor *)deliverOrange
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#ff9844" andClear:1];
    return color;
}
+(UIColor *)levelGray
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#cdcdcd" andClear:1];
    return color;
}
+(UIColor *)darkBlue
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#039bfb" andClear:1];
    return color;
}
+(UIColor *)lightBlue
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#24c4e1" andClear:1];
    return color;
}
+(UIColor *)greenBlue
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#66d8a9" andClear:1];
    return color;
}
+(UIColor *)boradColor
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#ededed" andClear:1];
    return color;
}

+ (UIColor *)greenCount
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#3c9640" andClear:1];
    return color;
}


+(UIColor *)heRed {
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#d80c24" andClear:1];
    return color;
}

+(UIColor *)greenEat
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#21af5e" andClear:1];

    return color;
}

+ (UIColor *)greenTitle
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#21AD5A" andClear:1];
    
    return color;
}

+ (UIColor *)mishiGreen20per
{
    UIColor *color = [[self class] transformHexadecimalTodecimalism:@"#cce9d8" andClear:1];
    
    return color;
}

+(UIColor *)transformHexadecimalTodecimalism:(NSString *)str andClear:(CGFloat)clear {
    if (str.length == 7) {
        //验证长度
        NSString *subStr = [str substringWithRange:NSMakeRange(1, 6)];
        NSInteger R = strtoul([[subStr substringWithRange:NSMakeRange(0, 2)] UTF8String], 0, 16);
        NSInteger G = strtoul([[subStr substringWithRange:NSMakeRange(2, 2)] UTF8String], 0, 16);
        NSInteger B = strtoul([[subStr substringWithRange:NSMakeRange(4, 2)] UTF8String], 0, 16);
        UIColor *color = kColor(R, G, B, clear);
        return color;
    }else {
        return nil;
    }
}
+(UIColor *)authBackBorderColor
{
    return [[self class] transformHexadecimalTodecimalism:@"#eaeaea" andClear:1];
}
+(UIColor *)authBackColor
{
    return [[self class] transformHexadecimalTodecimalism:@"#f1f1f1" andClear:1];
}

+(UIColor*)buttonDisableTitle
{
    return [[self class] transformHexadecimalTodecimalism:@"#198042" andClear:1];
}

+(UIColor*)cellSelectedBackGround
{
    return [[self class] transformHexadecimalTodecimalism:@"#eeeeee" andClear:1];
}
+(UIColor *)lightlineColor
{
    return [[self class] transformHexadecimalTodecimalism:@"#e7e7e7" andClear:1];
}
+(UIColor *)oxGray
{
    return [[self class] transformHexadecimalTodecimalism:@"#b9b9b9" andClear:1];
}

+(UIColor *)yellow
{
    return [[self class] transformHexadecimalTodecimalism:@"#ffb22d" andClear:1];
}
+ (UIColor *)cellSelectYellow
{
    return [[self class] transformHexadecimalTodecimalism:@"#fffaeb" andClear:1];
}
+(UIColor *)scoreYellow
{
    return [[self class] transformHexadecimalTodecimalism:@"#ffb22d" andClear:1];
}

@end
