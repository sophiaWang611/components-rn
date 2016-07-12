/**
 * Created by sophia on 16/3/3.
 *
 * @providesModule MSToast
 */
'use strict';

var React = require('react-native');
var {Platform, ToastAndroid} = React;

if (Platform.OS == "android") {
    var MSLoadingDialog = React.NativeModules.MSLoadingDialog;
    module.exports = {
        showTop : function (message) {
            MSLoadingDialog.showLoadingDialog(message);
        },
        showCenter : function (message) {
            MSLoadingDialog.showLoadingDialog(message);
        },
        showBottom : function (message) {
            MSLoadingDialog.showLoadingDialog(message);
        },
        hide: function() {
            MSLoadingDialog.hideLoadingDialog();
        },
        showShortTop : function (message) {
            ToastAndroid.show(message, ToastAndroid.SHORT)
        },
        showShortCenter : function (message) {
            ToastAndroid.show(message, ToastAndroid.SHORT)
        },
        showShortBottom : function (message) {
            ToastAndroid.show(message, ToastAndroid.SHORT)
        },
        showLongTop : function (message) {
            ToastAndroid.show(message, ToastAndroid.LONG)
        },
        showLongCenter : function (message) {
            ToastAndroid.show(message, ToastAndroid.LONG)
        },
        showLongBottom : function (message) {
            ToastAndroid.show(message, ToastAndroid.LONG)
        },
        show : function (message) {
            ToastAndroid.show(message, ToastAndroid.SHORT)
        }
    }
} else {
    module.exports = require("react-native-toast");
}