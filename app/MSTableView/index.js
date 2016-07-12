/**
 * Created by sophia on 16/3/4.
 *
 * @providesModule MSTableView
 */
'use strict';

var {Platform, View} = require('react-native');

if (Platform.OS === 'android') {
    module.exports = View;
} else {
    module.exports = require("./MSTableView");
}