package com.rncomponents.MSMap;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.InfoWindowAdapter;
import com.amap.api.maps2d.AMap.OnMapLoadedListener;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.rncomponents.ContextTools;
import com.rncomponents.MSBase.MSBaseActivity;
import com.rncomponents.R;

public class MapActivity extends MSBaseActivity implements LocationSource, OnMapLoadedListener,
        InfoWindowAdapter, OnClickListener {
    private static final String TAG = "MapActivity";
    private static final String UM_PAGE_NAME = "viewmap";
    private Button btnBack;// 返回
    private Button btnOtherMap;// 其他地图导航
    private Button btnPosition;// 当前定位
    private AMap aMap;
    private MapView mapView;
    private LatLng latlng = null;// new LatLng(31.238068, 121.501654);// 上海市经纬度
    private String addressDesc = "上海市";
    private LatLng positionLatlng = null;
    private Address address = null;

    private OnLocationChangedListener mListener;
    private int num = 0;
    private LocationManagerProxy mLocationManagerProxy;
    private AMapLocationListener mAMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if ((aMapLocation != null) && (aMapLocation.getAMapException().getErrorCode() == 0)) {
                Double geoLat = aMapLocation.getLatitude();
                Double geoLng = aMapLocation.getLongitude();
                positionLatlng = new LatLng(geoLat, geoLng);
                mListener.onLocationChanged(aMapLocation);// 显示小蓝点
                if (latlng != null) {
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(latlng));
                }
            }
            num = num + 1; // 只定位一次的话，位置不准确，，基本上三次定位后变化就不大了
            if (num == 3) {
                stopLocation();
            }
        }

        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        String dataStr = null;
        Intent intent = getIntent();
        if (null != intent) {
            Bundle bl = intent.getExtras();
            if (null != bl) {
                dataStr = (String) bl.get(ContextTools.KEY_INTENT_ADDRESS_ID);
            }
        }

        if (dataStr != null) {
            address = JSON.parseObject(dataStr, Address.class);
        }
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState); // 此方法必须重写
        if (aMap == null) {//初始化AMap对象
            aMap = mapView.getMap();
            if (address != null) {
                latlng = new LatLng(address.getLatitude(), address.getLongitude());
                addressDesc = address.getCity() + address.getDistrict() + address.getKeyWords();
                setUpMap(latlng);
            }

        }
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));

        btnBack = (Button) findViewById(R.id.map_back);// 返回
        btnPosition = (Button) findViewById(R.id.map_position);// 当前定位
        btnBack.setOnClickListener(this);
        btnPosition.setOnClickListener(this);
        setUpMap();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.map_back) {
            finish();
        } else if (id == R.id.map_other_map) {
            displayOptions();
        } else if (id == R.id.map_position && positionLatlng != null) {
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(positionLatlng));
        }
    }

    public void displayOptions() {

        Uri mUri = Uri.parse("geo:" + latlng.latitude + "," + latlng.longitude + "?q=" + addressDesc);
        Intent intent = new Intent(Intent.ACTION_VIEW, mUri);

        if (ContextTools.intentCanOpen(this, intent)) {
            startActivity(intent);
        } else {
            ContextTools.showToastMessage(this, 2, "您的手机尚未安装地图工具，建议您先安装地图软件！");
        }
    }

    private void setUpMap(LatLng latlng) {
        aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
        aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
        addMarkersToMap(latlng);// 往地图上添加marker
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();

//        MobclickAgent.onPageStart(UM_PAGE_NAME);
//        MobclickAgent.onResume(this);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();

//        MobclickAgent.onPageEnd(UM_PAGE_NAME);
//        MobclickAgent.onPause(this);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    /**
     * 在地图上添加marker
     */
    private void addMarkersToMap(LatLng latlng) {
        drawMarkers(latlng);// 添加自定义marker
    }

    /**
     * 绘制系统默认的1种marker背景图片
     */
    public void drawMarkers(LatLng latlng) {
        String snippetValue = address.getKeyWords();
        Marker marker = aMap.addMarker(new MarkerOptions()
                .position(latlng)
                .snippet(snippetValue)
                .title(address.getCity() + address.getDistrict())
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.map_position_icon2))
//				.icon(BitmapDescriptorFactory
//						.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .draggable(true));
        marker.showInfoWindow();// 设置默认显示一个infowinfow
    }


    /**
     * 监听amap地图加载成功事件回调
     */
    @Override
    public void onMapLoaded() {
        // 设置所有maker显示在当前可视区域地图中
        LatLngBounds bounds = new LatLngBounds.Builder()

                .include(latlng).build();
        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));
    }

    /**
     * 监听自定义infowindow窗口的infocontents事件回调
     */
    @Override
    public View getInfoContents(Marker marker) {

        View infoContent = getLayoutInflater().inflate(
                R.layout.custom_info_window, null);
        render(marker, infoContent);
        return infoContent;
    }

    /**
     * 监听自定义infowindow窗口的infowindow事件回调
     */
    @Override
    public View getInfoWindow(Marker marker) {

        View infoWindow = getLayoutInflater().inflate(
                R.layout.custom_info_window, null);

        render(marker, infoWindow);
        return infoWindow;
    }

    /**
     * 自定义infowinfow窗口
     */
    public void render(Marker marker, View view) {

        String title = marker.getTitle();
        TextView titleUi = ((TextView) view.findViewById(R.id.title));
        if (title != null) {

            titleUi.setText(title);

        } else {
            titleUi.setText("");
        }
        String snippet = marker.getSnippet();
        TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
        if (snippet != null) {

            snippetUi.setText(snippet);
        } else {
            snippetUi.setText("");
        }
        btnOtherMap = (Button) view.findViewById(R.id.map_other_map);// 其他地图导航
        btnOtherMap.setOnClickListener(this);
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.map_position_icon2));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.argb(102, 0, 138, 255));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(102, 0, 138, 255));// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
    }


    private void stopLocation() {
        if (mLocationManagerProxy != null) {
            mLocationManagerProxy.removeUpdates(mAMapLocationListener);
            mLocationManagerProxy.destroy();
        }
        mLocationManagerProxy = null;
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mLocationManagerProxy == null) {
            mLocationManagerProxy = LocationManagerProxy.getInstance(this);
            mLocationManagerProxy.requestLocationData(LocationProviderProxy.AMapNetwork, -1, 10, mAMapLocationListener);
        }
    }

    @Override
    public void deactivate() {

    }
}
