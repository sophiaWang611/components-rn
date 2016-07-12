/**
 * Created by sophia on 16/3/15.
 */
'use strict';

var React = require('react');

var {
    StyleSheet,
    View,
    Text,
    Platform,
    Image,
    ActivityIndicatorIOS
    } = require('react-native');

var container = {flexDirection: "row",alignSelf: 'center',marginTop:20,backgroundColor:'transparent'};

if (Platform.OS === 'android') {
    module.exports = React.createClass({

        render: function () {
            return (
                <View style={[container, this.props.style]}>
                    <Image source={require("../img/loading.gif")} style={{height:15,width:15}}/>
                    <Text style={{marginTop:-3,marginLeft:5}}>正在加载</Text>
                </View>
            );
        }
    });
} else {
    module.exports = React.createClass({
        render: function () {
            return (
                <View style={[container, this.props.style]}>
                    <ActivityIndicatorIOS style={{alignSelf: 'center'}}/>
                    <Text style={{marginTop:2,marginLeft:5}}>正在加载</Text>
                </View>
            );
        }
    });
}