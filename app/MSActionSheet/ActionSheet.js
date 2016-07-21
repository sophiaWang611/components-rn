/**
 * Created by sophia on 16/3/9.
 */
'use strict';

var React = require('react');

var {
    StyleSheet,
    View,
    Text,
    NativeModules
    } = require('react-native');

var MSNativePicker = NativeModules.MSPicker;

var ActionSheetAndroid = {

    showActionSheetWithOptions: function(options, callback) {
        if (!options || !options.options || !options.cancelButtonIndex) {
            return;
        }
        let opArr = Array.from(options.options);
        opArr.splice(options.cancelButtonIndex, 1);

        MSNativePicker.showPickerDialog([opArr], options.titles, callback,
            ()=>{callback({selectedIndex: options.cancelButtonIndex})});
    }
};


module.exports = ActionSheetAndroid;