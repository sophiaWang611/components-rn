/**
 * Created by sophia on 16/7/12.
 * @providesModule MSLocationService
 */
'use strict';

var React = require('react-native');

var LocationService = React.NativeModules.MSLocationService;

var MSLocationService = {
    userCity: function(cb) {
        return LocationService.userCity((resp)=>{
            if (!resp) resp = {};
            cb && cb(resp.err, resp.data);
        });
    },

    userNearbyPOIs: function(cb) {
        return LocationService.userNearbyPOIs((resp)=>{
            if (!resp) resp = {};
            cb && cb(resp.err, resp.data);
        });
    },

    cityKeywordSearchPOIs: function(city, searchKey, cb) {
        return LocationService.cityKeywordSearchPOIs(city, searchKey, (resp)=>{
            if (!resp) resp = {};
            cb && cb(resp.err, resp.data);
        });
    },

    userLocation: function(cb) {
        if (LocationService.userLocation) {
            return LocationService.userLocation(cb);
        }
        cb && cb();
    }
};

module.exports = MSLocationService;