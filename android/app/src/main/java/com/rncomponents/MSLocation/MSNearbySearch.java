package com.rncomponents.MSLocation;

import android.content.Context;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.poisearch.PoiSearch;
import com.facebook.react.bridge.Callback;

/**
 * Created by sophia on 16/3/3.
 */
public class MSNearbySearch extends MSBaseSearch {

    public void doSearch(double lat, double lon, Context context, Callback callback) {
        super.setCallback(callback);
        PoiSearch poiSearch = new PoiSearch(context, new PoiSearch.Query("", "", ""));
        LatLonPoint center = new LatLonPoint(lat, lon);
        poiSearch.setBound(new PoiSearch.SearchBound(center, 1000));
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

}
