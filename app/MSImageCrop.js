/**
 * Created by sophia on 16/7/12.
 *
 * @providesModule MSImageCrop
 */
'use strict';

var React = require('react-native');
var RNImageCrop = React.NativeModules.RNImageCrop;

var {
    StyleSheet,
    View,
    } = React;

var MSImageCrop = {
    cropImage: function(rate, path, cb) {
        RNImageCrop.cropImage(rate, path, (data)=>{
            cb && cb(data.filePath);
        });
    }
};

module.exports = MSImageCrop;