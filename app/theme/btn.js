/**
 * Created by litian on 15/11/2.
 */
'use strict';
var React = require('react-native');
var {
    StyleSheet,
    PixelRatio,
} = React;

var MSBase = require("./base");
var styleBtn = StyleSheet.create({
    btn:{
        marginTop:10,
        marginLeft:15,
        marginRight:15,
        paddingTop:10,
        paddingBottom:10,
        borderRadius:2
    },
    btnBgGreen:{
        backgroundColor: MSBase.bg_green
    },
    btnBgWhite:{
        backgroundColor: "#fff"
    },
    btnBgDisable:{
        backgroundColor: "#aaa"
    },
    btnBgDefault:{
        backgroundColor: "#fff",
        borderColor:'#ccc',
        borderStyle:'solid',
        borderWidth:1/PixelRatio.get(),
    },
    btnDisable:{
        backgroundColor: "#dedede"
    },
    btnText:{
        flex:1,
        alignSelf:'center',
        fontSize: 16,
        textAlign: "center"
    },
    btnWhiteText:{
        color: "#FFF"
    },
    btnBlackText:{
        color: "#444"
    },
    btnBlankText:{
        color: "#777",
    }
});
module.exports = styleBtn;
