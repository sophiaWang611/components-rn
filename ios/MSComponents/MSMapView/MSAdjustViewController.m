//
//  MSAdjustViewController.m
//  MishiApp
//
//  Created by SumFlower on 14/10/28.
//  Copyright (c) 2014年 ___MISHI___. All rights reserved.
//

#import "MSAdjustViewController.h"

#import "MSColor.h"
#import "MSFont.h"

@interface MSAdjustViewController ()

@property (nonatomic, strong) UISwipeGestureRecognizer *leftSwipeGestureRecognizer;
@property (nonatomic, strong) UISwipeGestureRecognizer *rightSwipeGestureRecognizer;

@property (nonatomic, strong) UIBarButtonItem *rightBt;
@property (nonatomic, strong) UIButton *leftButton;


@end

@implementation MSAdjustViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.navigationController.navigationBar.titleTextAttributes = @{NSForegroundColorAttributeName:[MSColor black],NSFontAttributeName:[MSFont xlarge]};
    if ([UIDevice currentDevice].systemVersion.floatValue >=7.0) {
        self.edgesForExtendedLayout = UIRectEdgeNone;
        self.extendedLayoutIncludesOpaqueBars = NO;
        self.modalPresentationCapturesStatusBarAppearance = NO;
    }
    self.navigationController.interactivePopGestureRecognizer.enabled = YES;
    
    [self setLeftNavBarItemWithImageName:@"back"];
    
    
    
//    UIView *bottomline = [[UIView alloc] initWithFrame:CGRectMake(0, self.navigationController.navigationBar.frame.size.height , self.navigationController.navigationBar.frame.size.width, 0.6)];
//    bottomline.backgroundColor = [MSColor mishiGreen];
//    [self.navigationController.navigationBar addSubview:bottomline];
    
    self.view.backgroundColor = [MSColor background];
    [self.navigationController.navigationBar setBackgroundColor:[MSColor barAlphaGray]];
    
    __weak typeof (self.navigationController) weakSelf = self.navigationController;
    if ([self.navigationController respondsToSelector:@selector(interactivePopGestureRecognizer)]) {
        self.navigationController.interactivePopGestureRecognizer.delegate = weakSelf;
    }
    
    if ([self respondsToSelector:@selector(setEdgesForExtendedLayout:)]){
        [self setEdgesForExtendedLayout:UIRectEdgeNone];
    }
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (BOOL)shouldAutorotate
{
    return YES;
}

// 所有派生类，默认只支持portrait方向。子类可以覆盖这个函数以支持更多的方向
- (UIInterfaceOrientationMask)supportedInterfaceOrientations
{
    return UIInterfaceOrientationMaskPortrait;
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/
- (void)setLeftNavBarItemWithImageName:(NSString *)name {

    self.leftButton = [UIButton buttonWithType:UIButtonTypeCustom];
    self.leftButton.frame = CGRectMake(0, 0, 20, 20);
    [self.leftButton setImage:[UIImage imageNamed:name] forState:UIControlStateNormal];
    [self.leftButton addTarget:self action:@selector(back:) forControlEvents:UIControlEventTouchUpInside];
    UIBarButtonItem *left = [[UIBarButtonItem alloc] initWithCustomView:self.leftButton];
    self.navigationItem.leftBarButtonItem = left;
}

- (void)setRightNavBarItemWithImageName:(NSString *)name {
    if (nil == name) {
        return;
    }
    UIButton *rightButton = [UIButton buttonWithType:UIButtonTypeCustom];
    rightButton.frame = CGRectMake(0, 0, 20, 20);
    [rightButton setBackgroundImage:[UIImage imageNamed:name] forState:UIControlStateNormal];
    [rightButton addTarget:self action:@selector(rightBarTouched:) forControlEvents:UIControlEventTouchUpInside];
    UIBarButtonItem *right = [[UIBarButtonItem alloc] initWithCustomView:rightButton];

    
    self.navigationItem.rightBarButtonItem = right;
    
}

-(void)setRightNavBarItemWithStr:(NSString *)str
{
    self.rightBt = [[UIBarButtonItem alloc] initWithTitle:str style:UIBarButtonItemStylePlain target:self action:@selector(rightBarTouched:)];
    [self.rightBt setTitleTextAttributes:[NSDictionary dictionaryWithObjectsAndKeys:[MSFont xnormal], NSFontAttributeName,nil] forState:UIControlStateNormal];
    self.rightBt.tintColor = [MSColor mishiGreen];

    UIBarButtonItem *fixbutton = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemFixedSpace target:nil action:nil];
    fixbutton.width = 10;

    self.navigationItem.rightBarButtonItems = [NSArray arrayWithObjects:fixbutton,self.rightBt, nil];
}

//方法重写
-(void)back:(id)sender
{
    //
    [self.navigationController popViewControllerAnimated:YES];
}
-(void)rightBarTouched:(id)sender
{
    //
}

-(void)setRightStringColor:(MSColor *)rightStringColor
{
    _rightStringColor = rightStringColor;
    self.rightBt.tintColor = _rightStringColor;
}

-(void)setRightButtonItemEnable:(BOOL)rightButtonItemEnable
{
    _rightButtonItemEnable = rightButtonItemEnable;
    if (_rightButtonItemEnable) {
        self.rightBt.enabled = YES;
    }else {
        self.rightBt.enabled = NO;
    }
}
-(void)setLeftButtonItemEnable:(BOOL)leftButtonItemEnable
{
    _leftButtonItemEnable = leftButtonItemEnable;
    if (_leftButtonItemEnable) {
        self.leftButton.enabled = YES;
    }else {
        self.leftButton.enabled = NO;
    }
}
@end
