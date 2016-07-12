/**
 * Created by sophia on 16/2/3.
 */
'use strict';

var React = require('react');

var {
    StyleSheet,
    View,
    Text,
    TouchableOpacity
    } = require('react-native');

var {CommonStyle, MSBase} = require("../theme");
var MSMapView = require("../MSMapView");

var TestMSMapView = React.createClass({
    render: function() {
        return (
            <View>
                <Text>Test MSMapView</Text>
                <TouchableOpacity
                    style={[CommonStyle.allCenter,{height:20, backgroundColor:MSBase.bg_green, width:150}]}
                    onPress={()=>{
                        var address = {
                            province: "上海市",
                            city: "上海市",
                            district: "徐汇区",
                            poiName: "徐家汇(地铁站)",
                            lat: 31.193761,
                            lng: 121.43635
                        };
                        MSMapView.renderMap(address.province, address.city, address.district, address.poiName, address.lat, address.lng);
                }}>
                    <Text style={{color: MSBase.font_white}}>显示地图信息</Text>
                </TouchableOpacity>
            </View>
        );
    }
});

var styles = StyleSheet.create({});

module.exports = TestMSMapView;