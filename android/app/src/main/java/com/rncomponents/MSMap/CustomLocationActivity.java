package com.rncomponents.MSMap;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.mishi.common.util.StringUtils;
import com.mishi.common.util.log.MsSdkLog;
import com.rncomponents.ContextTools;
import com.rncomponents.MSBase.MSBaseActivity;
import com.rncomponents.R;

import java.lang.reflect.Field;

public class CustomLocationActivity extends MSBaseActivity implements View.OnClickListener,
        AMap.OnMapLoadedListener, GeocodeSearch.OnGeocodeSearchListener {

    public static final String CURRENT_POSITION = "current_position";

    private String mCity;//城市
    private String mDistrict;// 区县
    private LatLonPoint mCurrentPosition;

    private MapView mMapView;
    private AMap mAMap;
    private TextView mTvTargetPosition;

    private boolean isFirstResume = true;
    private boolean isModifyAddress = false;
    private String keyWords;// 买家收货地址关键词（不超过30个字，v1.7以上版本必传）

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_location);

        if (!initParams()) {
            finish();
            return;
        }
        initActionBar();

        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mAMap = mMapView.getMap();
        mAMap.setOnMapLoadedListener(this);

        mTvTargetPosition = (TextView) findViewById(R.id.target_position);
        mTvTargetPosition.setOnClickListener(this);
        findViewById(R.id.map_position_icon).setOnClickListener(this);
    }

    @Override
    public void onMapLoaded() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                location(keyWords, mCity);
            }
        }, 100);
    }

    private boolean initParams() {
        Intent intent = getIntent();
        if (intent == null) {
            return false;
        } else {
            String dataStr = null;
            Bundle bl = intent.getExtras();
            if (null != bl) {
                dataStr = (String) bl.get(ContextTools.KEY_INTENT_ADDRESS_ID);
            }
            Address address = new Address();
            if (dataStr != null) {
                address = JSON.parseObject(dataStr, Address.class);
            }

            keyWords = address.getKeyWords();
            mDistrict = address.getDistrict();
            String city = address.getCity();
            if (TextUtils.isEmpty(city)) {
                return false;
            }

            mCity = city;

            mCurrentPosition = intent.getParcelableExtra(CURRENT_POSITION);
        }
        return true;
    }

    private void initActionBar() {
        findViewById(R.id.actionbar_btn_back).setOnClickListener(this);

        TextView tvLeft = (TextView) findViewById(R.id.actionbar_tv_title);
        tvLeft.setText(R.string.determine_location);

        TextView tvFinish = (TextView) findViewById(R.id.actionbar_right_text_btn);
        tvFinish.setText(R.string.finish);
        tvFinish.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
        isFirstResume = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMapView != null) {
            mMapView.onDestroy();
        }
    }

    private void location(String searchWords, String city) {
        MsSdkLog.i("dingzuo", "地理编码定位");
        GeocodeSearch geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        // 第一个参数表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode

        GeocodeQuery query = new GeocodeQuery(searchWords, city);// 第一个参数表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode，
        geocoderSearch.getFromLocationNameAsyn(query);// 设置同步地理编码请求

    }

    /**
     * 逆地理编码回调
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {

    }

    /**
     * 地理编码查询回调
     */
    @Override
    public void onGeocodeSearched(GeocodeResult result, int rCode) {
        if (rCode == 0) {
            if (result != null && result.getGeocodeAddressList() != null
                    && result.getGeocodeAddressList().size() > 0) {
                GeocodeAddress address = result.getGeocodeAddressList().get(0);

                moveToPosition(address.getLatLonPoint());
                MsSdkLog.i("dingzuo", "地址反查定位：定位到了地点");
            } else {
                MsSdkLog.i("dingzuo", "第一次没定位到，第二次根据区县");
                location(mDistrict, mCity);
            }
        } else {
            moveToPosition(null);
            MsSdkLog.i("dingzuo", "未能正常搜索，错误码：" + rCode);
        }
    }

    private void moveToPosition(LatLonPoint llp) {
        if (llp == null) {
            mTvTargetPosition.setText(keyWords);
            return;
        }
        mTvTargetPosition.setText(keyWords);

        LatLng ll = new LatLng(llp.getLatitude(), llp.getLongitude());
        mAMap.clear();
        mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 17));
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.actionbar_btn_back:
                finish();
                break;
            case R.id.actionbar_right_text_btn:
                onClickFinish();
                break;
            case R.id.target_position:
            case R.id.map_position_icon:
                location(keyWords, mCity);
                break;
            default:
                break;
        }
    }

    private void onClickFinish() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("配送员将会按照地图定位上门取餐，请务必确认地图定位正确！");
        builder.setPositiveButton("定位正确", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LatLng ll = mAMap.getCameraPosition().target;
                returnLocation(ll);
            }
        });
        builder.setNegativeButton("返回调整定位", null);
        builder.show();
    }

    private void returnLocation(final LatLng ll) {
        GeocodeSearch search = new GeocodeSearch(this);
        search.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
                if ((rCode == 0) && (result != null)) {
                    RegeocodeAddress address = result.getRegeocodeAddress();
                    if (address != null) {
                        Address buyerAddress = new Address();
                        buyerAddress.province = address.getProvince();
                        buyerAddress.city = address.getCity();
                        if (StringUtils.isEmpty(buyerAddress.city)) {
                            buyerAddress.city = address.getProvince();
                        }
                        buyerAddress.cityCode = address.getCityCode();
                        buyerAddress.district = address.getDistrict();
                        buyerAddress.districtCode = address.getAdCode();
                        buyerAddress.keyWords = keyWords;
                        buyerAddress.setLatitude(ll.latitude);
                        buyerAddress.setLongitude(ll.longitude);

                        Intent data = new Intent();
                        data.putExtra(ContextTools.KEY_INTENT_ADDRESS_RESULT, JSON.toJSONString(buyerAddress));
                        setResult(RESULT_OK, data);
                        finish();
                    }
                }
            }

            @Override
            public void onGeocodeSearched(GeocodeResult result, int rCode) {

            }
        });
        search.getFromLocationAsyn(new RegeocodeQuery(new LatLonPoint(ll.latitude, ll.longitude), 20, GeocodeSearch.AMAP));
    }

    private static Field getFields(Class cuClass, String fieldName) throws NoSuchFieldException {
        Field[] fields = cuClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                field.setAccessible(true);
                return field;
            }
        }
        throw new NoSuchFieldException("No such Field:" + fieldName);
    }
}
