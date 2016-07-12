/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */
'use strict';

var {AppRegistry} = require('react-native');
var RNComponents = require('./appSetUp');

var {
    MSActionSheet,
    MSDatePicker,
    MSHtmlView,
    MSModal,
    MSPicker,
    MSRefreshableListView,
    MSRefreshableScrollView,
    MSSlider,
    MSTableView,
    MSTextInput,
    MSCircleTextView,
    MSEvaluate,
    MSImageEditor,
    MSLoadingView,
    MSMapView,
    MSPhotoPicker,
    MSRadioView,
    MSScrollableTabView,
    MSSearchBar,
    MSSwiperView,
    MSSwitchView,
    MSTextAreaView,
    MSWebView
    } = require("MSComponents");

AppRegistry.registerComponent('RNComponents', () => RNComponents);
