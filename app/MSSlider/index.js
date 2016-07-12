/**
 * Created by sophia on 16/6/8.
 */
'use strict';

var {Platform} = require('react-native');

if (Platform.OS === 'android') {
    module.exports = require("./MSSliderAndroid");
} else {
    module.exports = require("./MSSliderIOS");
}

