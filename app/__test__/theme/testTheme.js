/**
 * Created by sophia on 16/2/29.
 */
'use strict';

var React = require('react-native');
var {StyleSheet} = React;

var {CommonStyle, MSBase} = require("../../theme");

var TestTheme = StyleSheet.create({
    separator: {
        marginTop:20,
        borderColor: "#AAA",
        borderWidth:1,
        marginBottom:20,
        backgroundColor: "#F5F5F5"
    },
    btn: {
        height:20,
        backgroundColor:MSBase.bg_green,
        alignItems: 'flex-start',
        justifyContent: 'center',
        paddingLeft: 10
    },
    btnText: {
        color: MSBase.font_white
    }
});

module.exports = Object.assign(CommonStyle, TestTheme);