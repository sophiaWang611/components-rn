/**
 * Created by sophia on 15/10/26.
 */
'use strict';

var {
    StyleSheet,
    PixelRatio,
    Platform
    } = require('react-native');

var MSBase = require('./base');

var CommonStyle = StyleSheet.create({
    pageLeft: {
        marginLeft: MSBase.margin_left_lg
    },
    pageRight: {
        marginRight: MSBase.margin_right_lg
    },
    pagePaddingTop: {
        paddingTop: MSBase.padding_top_lg
    },
    pagePaddingLeft: {
        paddingLeft: MSBase.padding_left_lg
    },
    pagePaddingRight: {
        paddingRight: MSBase.padding_right_lg
    },
    navTop: {
        marginTop: Platform.OS === "android" ? 20 : 30
    },
    navBotton: {
        marginBottom: MSBase.margin_bottom_sm
    },
    container: {
        backgroundColor:MSBase.bg_page
    },
    bg: {
        backgroundColor:MSBase.bg_page
    },
    bgFFF: {
        backgroundColor: MSBase.bg_white
    },
    bg999: {
        backgroundColor: MSBase.bg_dark
    },
    bg21af5e: {
        backgroundColor: MSBase.bg_green
    },
    fontRed: {
        color: MSBase.font_red
    },
    fontFFF: {
        color: MSBase.font_white
    },
    font21af5e: {
        color: MSBase.font_green
    },
    font555: {
        color: "#555"
    },
    font999: {
        color: MSBase.font_blank_4
    },
    fontffaa30: {
        color: MSBase.font_orange
    },
    font1d974e: {
        color: "#1d974e"
    },
    fontfec472: {
        color: "#fec472"
    },
    font10: {
        fontSize: MSBase.font_size_10
    },
    font12: {
        fontSize: MSBase.font_size_12
    },
    font777: {
        color: MSBase.font_blank_3
    },
    font444: {
        color: MSBase.font_blank_2
    },
    font00AAFF: {
        color: MSBase.font_blue
    },
    font14: {
        fontSize: MSBase.font_size_14
    },
    font16: {
        fontSize: MSBase.font_size_16
    },
    font20: {
        fontSize: 10
    },
    font24: {
        fontSize: 12
    },
    font28: {
        fontSize: 14
    },
    font30: {
        fontSize: 14
    },
    font32: {
        fontSize: 16
    },
    font36: {
        fontSize: 18
    },
    font38: {
        fontSize: 20
    },
    font40: {
        fontSize: 20
    },
    row: {
        flexDirection: "row"
    },
    column: {
        flexDirection: "column"
    },
    atCenter: {
        textAlign: 'center'
    },
    show: {
        opacity: 1
    },
    hide: {
        opacity: 0
    },
    separatorLine: {
        borderColor:MSBase.border_normal,
        width: 1 / PixelRatio.get(),
        borderRightWidth: 2 / PixelRatio.get()
    },
    borderBottom:{
        borderColor:MSBase.border_normal,
        borderBottomWidth: 1 / PixelRatio.get()
    },
    allCenter: {
        alignItems: 'center',
        justifyContent: 'center'
    },
    verticalCenter: {
        justifyContent: 'center'
    },
    flex1: {
        flex:1
    },
    btnGreen:{
        backgroundColor: MSBase.bg_green
    },
    btnGray:{
        backgroundColor: MSBase.bg_gray
    },
    fwbold: {
        fontWeight: 'bold'
    },
    bgffaa30: {
        backgroundColor: MSBase.bg_orange
    },
    marginTop20:{
        marginTop:MSBase.margin_top_lg
    },
    containerMarginTop:{
        marginTop:MSBase.margin_top_lg
    },
    icon_core:{
        width:6,
        height:10
    }
});

module.exports = CommonStyle;