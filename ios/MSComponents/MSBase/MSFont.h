//
//  MSFont.h
//  Packaging
//
//  Created by SumFlower on 14-10-22.
//  Copyright (c) 2014年 夏敏. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface MSFont : UIFont
//80px
+(UIFont *)xolarge;
//68px
+(UIFont *)xxxlarge;
//60px
+(UIFont *)xlllage;
//54px
+(UIFont *)x27large;
//52px
+(UIFont *)x26llarge;
//58px
+(UIFont *)lllarge;
//50px
+(UIFont *)xllarge;
//48px
+(UIFont *)xxlarge;
//48px加粗
+(UIFont *)xxboldlarge;
//44px
+ (UIFont *)xxlarges;
//40px加粗
+(UIFont *)xboldlarge;
//22px
+(UIFont *)font22Large;
//21px
+(UIFont *)font21Large;
//40px
+(UIFont *)xlarge; // xlarge
//36px
+(UIFont *)oxlarge;
+(UIFont *)boldoxlarge;
//34px
+(UIFont *)large34;
+(UIFont *)boldLarge34;
//32px
+(UIFont *)large; // large
//30px
+(UIFont *)slarge;
//100px
+(UIFont *)carryCashLarge;
//28px
+(UIFont *)xnormal; // xnormal
//28px加粗
+(UIFont *)xboldnormal;
//26px
+(UIFont *)normal; // normal
//24px
+(UIFont *)small; //small

//24px bold
+(UIFont*)boldsmall;
//22px
+(UIFont *)middleSmall;

//20px
+(UIFont *)xsmall; //xsmall
//加粗//20px
+(UIFont *)xboldSmall;

//[MSFont nomral]
//[MSColor white]
//18px
+(UIFont *)xxsmall;

// 16px
+(UIFont*)xxxsmall;

// 12px
+(UIFont*)smallest;


//转换字体为系统字体
+ (UIFont *)convertFontToSystemFont:(UIFont *)font;
//加粗
+ (UIFont *)convertBoldFontToSystemFont:(UIFont *)font;
@end
