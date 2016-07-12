package com.rncomponents.MSMap;

import android.app.Activity;
import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.rncomponents.Constants;
import com.rncomponents.ContextTools;

/**
 * Created by sophia on 16/2/4.
 */
public class MSMapManager extends ReactContextBaseJavaModule implements ActivityEventListener {

    private Callback mCallback;

    public MSMapManager(ReactApplicationContext reactContext) {
        super(reactContext);
        reactContext.addActivityEventListener(this);
    }

    @Override
    public String getName() {
        return "MSMapViewManager";
    }

    @ReactMethod
    public void presentMapViewWithPOIName(String province, String city, String district,
                          String poiName, double lat, double lng) {
        Activity activity = getCurrentActivity();
        Intent intent = new Intent(activity, MapActivity.class);
        Address address = new Address(province, city, district, poiName, lat, lng);
        intent.putExtra(ContextTools.KEY_INTENT_ADDRESS_ID, JSON.toJSONString(address));
        activity.startActivity(intent);
    }

    @ReactMethod
    public void presentSearchMapView(String cityName, String poiName, String addressDesc, Callback callback) {
        Activity activity = getCurrentActivity();
        Intent intent = new Intent(activity, CustomLocationActivity.class);
        Address address = new Address("", cityName, "", poiName, 0.0, 0.0);
        address.setAddressDesc(addressDesc);
        intent.putExtra(ContextTools.KEY_INTENT_ADDRESS_ID, JSON.toJSONString(address));
        activity.startActivityForResult(intent, Constants.SEARCH_MAP_RESULT_CODE, null);

        mCallback = callback;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) return;
        int RESULT_CANCELED = Activity.RESULT_CANCELED;
        int RESULT_OK = Activity.RESULT_OK;
        if (resultCode == RESULT_CANCELED && mCallback != null) {
            mCallback.invoke(Arguments.createMap());
            return;
        }
        if (resultCode == RESULT_OK && requestCode == Constants.SEARCH_MAP_RESULT_CODE && mCallback != null) {
            WritableMap returnVal = Arguments.createMap();
            returnVal.putString("data", data.getStringExtra(ContextTools.KEY_INTENT_ADDRESS_RESULT));
            mCallback.invoke(returnVal);
        }
    }
}
