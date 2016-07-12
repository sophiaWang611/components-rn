/**
 * Created by sophia on 16/1/13.
 */
'use strict';

var React = require('react');

var {
    NativeModules,
    View,
    PropTypes,
    StyleSheet,
    requireNativeComponent,
    Platform,
    Text
    } = require('react-native');

if (Platform.OS === 'android') {
    module.exports = React.createClass({
        propTypes: {
            text: React.PropTypes.string,
            color: React.PropTypes.string,
            width: React.PropTypes.number,
            fontSize: React.PropTypes.number
        },
        render: function () {
            let bgColor = this.props.color || "#f74163",
                size = this.props.width || 16,
                fontSize = this.props.fontSize || 9;
            return (
                <View style={[this.props.style,{position:"relative",
                alignItems: 'center',justifyContent: 'center',
                width:size,height:size,borderRadius:size/2,backgroundColor: bgColor}]}>
                    <Text style={{color:"#FFF",fontSize:fontSize}}>{this.props.text}</Text>
                </View>
            );
        }
    });
} else {
    var MSCircleTextView = React.createClass({
        propTypes: {
            ...View.propTypes,
            text: React.PropTypes.string,
            color: React.PropTypes.string,
            width: React.PropTypes.number,
            fontSize: React.PropTypes.number
        },

        render: function () {
            return (
                <MSCircleText {...this.props}/>
            );
        }
    });

    var MSCircleText = requireNativeComponent('MSCircleText', MSCircleTextView);
    module.exports = MSCircleTextView;
}