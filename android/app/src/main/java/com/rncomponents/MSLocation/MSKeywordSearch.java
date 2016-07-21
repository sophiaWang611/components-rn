package com.rncomponents.MSLocation;

import android.content.Context;

import com.amap.api.services.poisearch.PoiSearch;
import com.facebook.react.bridge.Callback;

/**
 * Created by sophia on 16/3/3.
 */
public class MSKeywordSearch extends MSBaseSearch {

    public void doSearch(String searchKey, String city, Context context, Callback callback) {
        super.setCallback(callback);
        PoiSearch.Query query = new PoiSearch.Query(searchKey, "", city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        PoiSearch poiSearch = new PoiSearch(context, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }


}
