/**
 * Created by sophia on 16/3/9.
 *
 * @providesModule MSActionSheet
 */
'use strict';

var {Platform, ActionSheetIOS} = require('react-native');

if (Platform.OS === 'android') {
    module.exports = require("./ActionSheet");
} else {
    module.exports = ActionSheetIOS;
}