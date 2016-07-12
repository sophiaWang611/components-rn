/**
 * Created by sophia on 16/2/3.
 */
'use strict';

var React = require('react');

var {
    StyleSheet,
    View,
    PixelRatio,
    Image,
    Text
    } = require('react-native');

var {CommonStyle,MSBase} = require("../theme");
var MSSwiper = require("../MSSwiper");

var TestMSSwiper = React.createClass({
    getInitialState: function() {
        return {
            items: [
                {title: "Test MSSwiper1...."},
                {title: "Test MSSwiper2...."},
                {title: "Test MSSwiper3...."}
            ]
        };
    },
    render: function() {
        var createRow = (item,i) => {
            return (
                <View key={i} style={[CommonStyle.row,CommonStyle.pagePaddingLeft,CommonStyle.pagePaddingRight,
                            {alignItems: "center", height:40}]}>
                    <Image source={require("../img/icon_phoneNull.png")} style={{width: 16, height: 16}}/>
                    <Text style={{flex:1,marginLeft:5,fontSize:MSBase.font_size_14}} numberOfLines={1}>{item.title}</Text>
                </View>
            );
        };
        return (
            <View style={[styles.platformNotices,CommonStyle.row,{overflow:'hidden',borderWidth: 1/PixelRatio.get(), borderColor:"#EEE"}]}>
                <MSSwiper
                    horizontal={false}
                    height={32*3}
                    autoplayDirection={false}
                    autoplay={true}
                    loop={true}
                    autoplayTimeout={1}
                    showsPagination={false}
                    showsButtons={false}
                    >
                    {this.state.items.map(createRow)}
                </MSSwiper>
            </View>
        );
    }
});


var styles = StyleSheet.create({
    platformNotices:{
        backgroundColor:'#fff',
        height:40,
        borderBottomWidth: 1 / PixelRatio.get(),
        borderColor:"#eee",
        marginBottom:10
    },
});

module.exports = TestMSSwiper;