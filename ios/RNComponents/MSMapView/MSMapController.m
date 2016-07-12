//
//  MSMapController.m
//  MishiOS
//
//  Created by SumFlower on 14/12/16.
//  Copyright (c) 2014年 ___MISHI___. All rights reserved.
//
#define FirstSheetTag 100
#define SecondSheetTag 101
#define ThirdSheetTag 102

#import "MSMapController.h"
#import "MSAddress.h"
#import "MSMapStatus.h"
#import "MSCustomAnnotationView.h"
#import "MSColor.h"
#import "MSConfig.h"
#import <MAMapKit/MAMapKit.h>

#define MSdistanceFromEdge 15


@interface MSMapController ()<MAMapViewDelegate,UIActionSheetDelegate>
{
    MAMapView *myMapView;
    NSMutableArray *annotations;
    UIButton *backButton;
    
    NSURL *oneUrl;
    NSURL *twoUrl;
    NSURL *threeUrl;
}
@end

@implementation MSMapController


- (void)viewDidLoad {
    [super viewDidLoad];
    NSString *apiKey = [[MSConfig globalConfig] getMapKey];
    [MAMapServices sharedServices].apiKey = apiKey;
    myMapView = [[MAMapView alloc] initWithFrame:CGRectMake(0, 0, CGRectGetWidth(self.view.bounds), CGRectGetHeight(self.view.bounds))];
    myMapView.showsCompass = NO;
    myMapView.showsScale = NO;
    myMapView.showsUserLocation = YES;
    myMapView.userTrackingMode = MAUserTrackingModeFollow;

    [myMapView setDelegate:self];
    [myMapView setZoomLevel:13.0 atPivot:CGPointMake(self.view.bounds.size.width/2, self.view.bounds.size.height/2) animated:YES];    
    
    myMapView.centerCoordinate = CLLocationCoordinate2DMake(self.address.lat, self.address.lng);
    [self.view addSubview:myMapView];
    
    MAPointAnnotation *addressAnnotation = [[MAPointAnnotation alloc] init];
    addressAnnotation.coordinate = CLLocationCoordinate2DMake(self.address.lat, self.address.lng);
    annotations = [[NSMutableArray alloc] initWithObjects:addressAnnotation, nil];//增加标注
    [myMapView addAnnotations:annotations];
    [myMapView selectAnnotation:annotations[0] animated:YES];
  
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(goToGPS) name:MSCustomAnnotationViewNotification object:nil];

//    [myMapView reloadInputViews];
//    [self recenterMap];

    [self initUI];
    // Do any additional setup after loading the view.
}
-(void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
}
-(void)viewDidDisappear:(BOOL)animated
{
    [super viewDidDisappear:animated];
    myMapView.userTrackingMode = MAUserTrackingModeNone;
    myMapView.showsUserLocation = NO;
    [myMapView removeAnnotations:annotations];
    myMapView.delegate = nil;
}
-(void)viewWillDisappear:(BOOL)animated
{
    [super viewWillDisappear:animated];
    self.navigationController.navigationBarHidden = NO;
}
-(void)viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    self.navigationController.navigationBarHidden = YES;
}
-(void)initUI
{
    backButton = [UIButton buttonWithType:UIButtonTypeCustom];
    backButton.frame = CGRectMake(MSdistanceFromEdge, 30, 34, 34);
    [backButton setBackgroundImage:[UIImage imageNamed:@"btn_map_back"] forState:UIControlStateNormal];
    [backButton addTarget:self action:@selector(backAction) forControlEvents:UIControlEventTouchUpInside];
    backButton.backgroundColor = [MSColor clearColor];
    [backButton.layer setMasksToBounds:YES];
    [backButton.layer setCornerRadius:17];
    [self.view addSubview:backButton];
    
    
    UIButton *locationButton = [UIButton buttonWithType:UIButtonTypeCustom];
    locationButton.frame = CGRectMake(2 *MSdistanceFromEdge, self.view.frame.size.height - 68, 34, 34);
    [locationButton setBackgroundImage:[UIImage imageNamed:@"icon_location"] forState:UIControlStateNormal];
    locationButton.backgroundColor = [MSColor clearColor];
    [locationButton addTarget:self action:@selector(locationAction) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:locationButton];
    

}
- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
-(void)dealloc
{
    [[NSNotificationCenter defaultCenter] removeObserver:self];
}

#pragma mark -- MapViewDelegate
-(MAAnnotationView *)mapView:(MAMapView *)mapView viewForAnnotation:(id<MAAnnotation>)annotation
{
    if ([annotation isKindOfClass:[MAPointAnnotation class]]) {
        static NSString *pointReuseIndetifier = @"MSCustomAnnotationView";
        MSCustomAnnotationView *annotationView = (MSCustomAnnotationView *)[mapView dequeueReusableAnnotationViewWithIdentifier:pointReuseIndetifier];
        if (annotationView == nil) {
            annotationView = [[MSCustomAnnotationView alloc] initWithAnnotation:annotation reuseIdentifier:pointReuseIndetifier];
            annotationView.canShowCallout = NO;
            annotationView.draggable = YES;
            annotationView.showGPSButton = YES;
        }
        annotationView.title = [NSString stringWithFormat:@"%@%@", self.address.addressDesc, self.address.poiName];
        annotationView.icon = [UIImage imageNamed:@"maplocation"];

        return annotationView;
    }
    return nil;
}

- (void)mapView:(MAMapView *)mapView didAddAnnotationViews:(NSArray *)views
{
    MAAnnotationView *view = views[0];
    // 放到该方法中用以保证userlocation的annotationView已经添加到地图上了。
    if ([view.annotation isKindOfClass:[MAUserLocation class]])
    {
        MAUserLocationRepresentation *pre = [[MAUserLocationRepresentation alloc] init];
        pre.showsAccuracyRing = YES;
        pre.fillColor = [MSColor alpha4blue];
        pre.image = [UIImage imageNamed:@"currentlocation"];
        pre.lineWidth = 0;
        
        [myMapView updateUserLocationRepresentation:pre];
        
        view.calloutOffset = CGPointMake(0, 0);
    }
    
}
/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

#pragma mark -- Actions
-(void)backAction
{
    //[self.navigationController popViewControllerAnimated:YES];
  [self dismissViewControllerAnimated:YES completion:nil];
}
-(void)locationAction
{
    //
    [myMapView setCenterCoordinate:myMapView.userLocation.location.coordinate animated:YES];
}

-(void)userMapWith:(CGFloat)lat andLng:(CGFloat)lng
{
    //高德
    NSString *addrStr = [NSString stringWithFormat:@"%@%@%@%@",self.address.province,self.address.city,self.address.district,self.address.poiName];
    NSString *gaodeStr = [NSString stringWithFormat:@"iosamap://navi?sourceApplication=applicationName&backScheme=applicationScheme&poiname=%@&poiid=BGVIS&lat=%f&lon=%f&dev=1&style=2",addrStr,lat,lng];
    NSString *gaodeEncod = [gaodeStr stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
    NSURL *gaodeURL = [NSURL URLWithString:gaodeEncod];
    //百度
//    NSString *baiduS = [NSString stringWithFormat:@"baidumap://map/direction?origin=我的位置&destination=%f,%f&mode=driving",lat,lng];
    NSString *baiduS = [NSString stringWithFormat:@"baidumap://map/direction?origin=我的位置&destination=name:%@|latlng:%f,%f&mode=driving",addrStr,lat,lng];

    NSString *baiduStr = [baiduS stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
    NSURL *baiduURL = [NSURL URLWithString:baiduStr];
    //腾讯
    NSString *txS = [NSString stringWithFormat:@""];
    NSString *txStr = [txS stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];//腾讯url
    NSURL *tengxunURL = [NSURL URLWithString:txStr];
    
    NSMutableArray *mapArray = [[NSMutableArray alloc] init];
    
    if ([[UIApplication sharedApplication] canOpenURL:gaodeURL]) {
        MSMapStatus *map = [[MSMapStatus alloc] init:GaoDeMap andUrl:gaodeURL];
        [mapArray addObject:map];
    }
    if ([[UIApplication sharedApplication] canOpenURL:baiduURL]) {
        MSMapStatus *map = [[MSMapStatus alloc] init:BaiDuMap andUrl:baiduURL];
        [mapArray addObject:map];
    }
    if ([[UIApplication sharedApplication] canOpenURL:tengxunURL]) {
        MSMapStatus *map = [[MSMapStatus alloc] init:TengXunMap andUrl:tengxunURL];
        [mapArray addObject:map];
    }
    if (mapArray.count == 0) {
        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:nil message:@"请安装地图" delegate:nil cancelButtonTitle:@"确定" otherButtonTitles: nil];
        [alert show];
    }else if (mapArray.count == 1) {
        MSMapStatus *map = [mapArray firstObject];
        oneUrl = map.Url;
        UIActionSheet *sheet = [[UIActionSheet alloc] initWithTitle:@"选择地图导航" delegate:self cancelButtonTitle:@"取消" destructiveButtonTitle:nil otherButtonTitles:map.des, nil];
        sheet.tag = FirstSheetTag;
        [sheet showInView:self.navigationController.view];
    }else if (mapArray.count == 2) {
        MSMapStatus *mapone = mapArray[0];
        oneUrl = mapone.Url;
        MSMapStatus *mapTwo = mapArray[1];
        twoUrl = mapTwo.Url;
        UIActionSheet *sheet = [[UIActionSheet alloc] initWithTitle:@"选择地图导航" delegate:self cancelButtonTitle:@"取消" destructiveButtonTitle:nil otherButtonTitles:mapone.des,mapTwo.des, nil];
        sheet.tag = SecondSheetTag;
        [sheet showInView:self.navigationController.view];
    }else if (mapArray.count == 3) {
        MSMapStatus *mapone = mapArray[0];
        oneUrl = mapone.Url;
        MSMapStatus *maptwo = mapArray[1];
        twoUrl = maptwo.Url;
        MSMapStatus *mapthree = mapArray[2];
        threeUrl = mapthree.Url;
        UIActionSheet *sheet = [[UIActionSheet alloc] initWithTitle:@"选择地图导航" delegate:self cancelButtonTitle:@"取消" destructiveButtonTitle:nil otherButtonTitles:mapone.des,maptwo.des,mapthree.des, nil];
        sheet.tag = ThirdSheetTag;
        [sheet showInView:self.navigationController.view];
    }
}

-(void)recenterMap
{
    NSArray *coordinates=[myMapView valueForKeyPath:@"annotations.coordinate"];
    CLLocationCoordinate2D maxCoord={-90.0f,-180.0f};
    CLLocationCoordinate2D minCoord={90.0f,180.0f};
    for (NSValue *value in coordinates) {
        
        CLLocationCoordinate2D coord={0.0f,0.0f};
        [value getValue:&coord];
        
        //排除零点和当前位置
        if ((coord.latitude == 0.0f && coord.longitude == 0.0f) || (coord.latitude == myMapView.userLocation.coordinate.latitude && coord.longitude == myMapView.userLocation.coordinate.longitude))
        {
            continue;
        }
        
        if (coord.longitude>maxCoord.longitude) {
            maxCoord.longitude=coord.longitude;
        }
        if(coord.latitude>maxCoord.latitude){
            maxCoord.latitude=coord.latitude;
        }
        if (coord.longitude<minCoord.longitude) {
            minCoord.longitude=coord.longitude;
        }
        if(coord.latitude<minCoord.latitude){
            minCoord.latitude=coord.latitude;
        }
    }
    MACoordinateRegion region={{0.0f,0.0f},{0.0f,0.0f}};
    region.center.longitude=(minCoord.longitude+maxCoord.longitude)/2.0;
    region.center.latitude=(minCoord.latitude+maxCoord.latitude)/2.0;
    region.span.longitudeDelta=maxCoord.longitude-minCoord.longitude;
    region.span.latitudeDelta=maxCoord.latitude-minCoord.latitude;
    [myMapView setRegion:region animated:NO];
}

#pragma mark -- ActionSheet
-(void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if (actionSheet.tag == FirstSheetTag) {
        if (buttonIndex == 0) {
            [[UIApplication sharedApplication] openURL:oneUrl];
        }
    }
    
    if (actionSheet.tag == SecondSheetTag) {
        if (buttonIndex == 0) {
            [[UIApplication sharedApplication] openURL:oneUrl];
        }else if (buttonIndex == 1){
            [[UIApplication sharedApplication] openURL:twoUrl];
        }
    }
    
    if (actionSheet.tag == ThirdSheetTag) {
        if (buttonIndex == 0) {
            [[UIApplication sharedApplication] openURL:oneUrl];
        }else if (buttonIndex == 1) {
            [[UIApplication sharedApplication] openURL:twoUrl];
        }else if (buttonIndex == 2){
            [[UIApplication sharedApplication] openURL:threeUrl];
        }
    }
}
#pragma mark -- CustomAnnotationViewDelegate
-(void)goToGPS
{
    [self userMapWith:self.address.lat andLng:self.address.lng];

}
@end
