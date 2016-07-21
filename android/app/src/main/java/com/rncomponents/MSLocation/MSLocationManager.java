package com.rncomponents.MSLocation;


import android.content.Context;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.mishi.common.util.StringUtils;
import com.mishi.xstate.XState;
import com.mishi.xstate.util.XStateConstants;

/**
 * Created by sophia on 16/3/3.
 */
public class MSLocationManager extends ReactContextBaseJavaModule {
    private MSLocationService locationService;
    private Context context;

    public MSLocationManager(ReactApplicationContext reactContext) {
        super(reactContext);
        context = reactContext;
        locationService = MSLocationService.getInstance(reactContext);
        locationService.init();
        locationService.getCurrentLocal(null);
    }

    @Override
    public String getName() {
        return "MSLocationService";
    }

    @ReactMethod
    public void userCity(Callback callback) {
        MSAddress address = locationService.getAddress();
        if (StringUtils.isEmpty(locationService.getCity()) && address != null
                && address.getLat() > 0 && address.getLon() > 0) {
            this.getAddressByPoint(callback);
            return;
        }
        genResultForGetCity(callback);
    }

    @ReactMethod
    public void userNearbyPOIs(Callback callback) {
        if (locationService.getAddress() != null) {
            MSNearbySearch search = new MSNearbySearch();
            search.doSearch(locationService.getAddress().getLat(), locationService.getAddress().getLon(),
                    super.getReactApplicationContext(), callback);
        } else {
            WritableMap returnVal = Arguments.createMap();
            returnVal.putString("err", "定位失败");
            callback.invoke(returnVal);
        }
    }

    @ReactMethod
    public void cityKeywordSearchPOIs(String city,  String searchKey, Callback callback) {
        MSKeywordSearch search = new MSKeywordSearch();
        search.doSearch(searchKey, city, super.getReactApplicationContext(), callback);
    }

    @ReactMethod
    public void userLocation(Callback callback) {
        String lat = XState.getValue(XStateConstants.KEY_LAT);
        String lng = XState.getValue(XStateConstants.KEY_LNG);
        if (!StringUtils.isEmpty(lat) && !StringUtils.isEmpty(lng)) {
            WritableMap returnVal = Arguments.createMap();
            returnVal.putString("longitude", lng);
            returnVal.putString("latitude", lat);
            callback.invoke(returnVal);
        }
    }

    private void getAddressByPoint(final Callback callback) {
        final MSAddress address = locationService.getAddress();
        GeocodeSearch search = new GeocodeSearch(context);
        search.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult result, int i) {
                if (i == 0 && result != null && result.getRegeocodeAddress() != null) {
                    address.setValues(result.getRegeocodeAddress());
                }
                genResultForGetCity(callback);
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });
        LatLonPoint point = new LatLonPoint(address.getLat(), address.getLon());
        RegeocodeQuery query = new RegeocodeQuery(point, 0, GeocodeSearch.AMAP);
        search.getFromLocationAsyn(query);
    }

    private void genResultForGetCity(Callback callback) {
        WritableMap returnVal = Arguments.createMap();
        if (locationService.getCity() != null) {
            returnVal.putString("data", locationService.getCity());
        } else {
            returnVal.putString("err", "定位失败");
        }
        callback.invoke(returnVal);
    }
}
