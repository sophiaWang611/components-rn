/**
 * Created by sophia on 16/2/17.
 */
'use strict';

var {Platform} = require('react-native');

if (Platform.OS === 'android') {
    module.exports = require("./MSPicker");
} else {
    module.exports = require("./MSPicker.ios");
}