package com.rncomponents.MSLocation;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiItemDetail;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

import java.util.List;

/**
 * Created by sophia on 16/3/3.
 */
public class MSBaseSearch implements PoiSearch.OnPoiSearchListener {
    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        WritableMap returnVal = Arguments.createMap();
        if (i == 0 && poiResult != null) {
            List<PoiItem> poiItems = poiResult.getPois();
            returnVal = convertData(poiItems);
        } else {
            returnVal.putString("err", "找不到地址");
        }
        this.callback.invoke(returnVal);
    }

    @Override
    public void onPoiItemDetailSearched(PoiItemDetail poiItemDetail, int i) {

    }

    public WritableMap convertData(List<PoiItem> poiItems) {
        WritableMap retrunVal = Arguments.createMap();
        WritableArray results = Arguments.createArray();
        if (poiItems != null && poiItems.size() != 0) {
            for (PoiItem poiItem : poiItems) {
                WritableMap item = Arguments.createMap();
                item.putString("name", poiItem.getTitle());
                item.putString("address", poiItem.getSnippet());
                item.putString("province", poiItem.getProvinceName());
                item.putString("provinceCode", poiItem.getProvinceCode());
                item.putString("city", poiItem.getCityName());
                item.putString("cityCode", poiItem.getCityCode());
                item.putString("district", poiItem.getAdName());
                item.putString("districtCode", poiItem.getAdCode());
                item.putString("latitude", String.valueOf(poiItem.getLatLonPoint().getLatitude()));
                item.putString("longitude", String.valueOf(poiItem.getLatLonPoint().getLongitude()));
                results.pushMap(item);
            }
        }
        retrunVal.putArray("data", results);
        return retrunVal;
    }
}
