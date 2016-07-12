/**
 * Created by sophia on 15/11/19.
 */
'use strict';

var React = require('react-native');
var MSMapViewManager = React.NativeModules.MSMapViewManager;


var MSMapView = {
    renderMap: function(province, city, district, poiName, lat, lng) {
        return MSMapViewManager.presentMapViewWithPOIName(province, city, district, poiName, lat, lng);
    },
    renderSearchMap: function(cityName, poiName, addressDesc, callback) {
        return MSMapViewManager.presentSearchMapView(cityName, poiName, addressDesc, callback);
    }
};

module.exports = MSMapView;