/**
 * Created by sophia on 16/7/12.
 * @providesModule MSWebView
 */

'use strict';

var {Platform, WebView} = require('react-native');

if (Platform.OS === 'android') {
    module.exports = require("./MSWebView");
} else {
    module.exports = WebView;
}
