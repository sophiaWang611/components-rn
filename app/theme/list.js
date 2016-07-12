/**
 * Created by litian on 15/11/4.
 */
'use strict';
var {
    StyleSheet,
    PixelRatio,
    } = require('react-native');
var MSBase = require("./base");

var styleList = StyleSheet.create({
    listContainer:{
        backgroundColor:MSBase.bg_white,
        borderColor:MSBase.border_normal,
        borderTopWidth:1/PixelRatio.get(),
        borderBottomWidth:1/PixelRatio.get()
    },
    listOutTitle:{
        marginBottom:MSBase.margin_bottom_mid,
        fontSize:MSBase.font_size_18,
        color:MSBase.font_blank_2
    },
    listRow:{
        flexDirection:'row',
        alignItems:'center',
        justifyContent:'flex-start',
        borderColor: MSBase.border_normal,
        borderBottomWidth:1/PixelRatio.get(),
        paddingLeft:MSBase.padding_left_lg,
        paddingRight:MSBase.padding_right_lg,
        paddingTop:MSBase.padding_top_smid,
        paddingBottom:MSBase.padding_bottom_smid,
    },
    noBottomBorder:{
        borderBottomWidth:0
    },
    rowLabelText:{
        fontSize:MSBase.font_size_16,
        alignItems:'center',
        color:MSBase.font_blank_2,
        marginRight:MSBase.margin_right_sm
    },
    rowInput:{
        flex: 1,
        fontSize:MSBase.font_size_16,
        height:24
    },
    rowMainText:{
        fontSize:MSBase.font_size_16,
        alignItems:'center',
        color:MSBase.font_blank_2,
        marginRight:MSBase.margin_right_sm
    },
    rowSubText:{
        textAlign:'right',
        flexDirection:'row',
        justifyContent:'flex-end',
        alignItems:'flex-end',
        fontSize:MSBase.font_size_16,
        color:MSBase.font_blank_3,
        marginRight:MSBase.margin_right_mid,
        flex:1
    },
    subText:{
        fontSize:MSBase.font_size_14,
        color:MSBase.font_blank_3
    },
    mainText:{
        fontSize:MSBase.font_size_16,
        color:MSBase.font_blank_2
    },
    hasValue:{
        color:MSBase.font_blank_2
    },
    icon_core:{
        width:6,
        height:10
    }
});
module.exports = styleList;