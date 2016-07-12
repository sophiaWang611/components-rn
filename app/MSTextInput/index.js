/**
 * Created by sophia on 15/11/30.
 */
'use strict';

var React = require('react');
var MSTextInput = require("./MSTextInput");

var {
    StyleSheet,
    View,
    Image,
    Dimensions,
    TouchableWithoutFeedback
    } = require('react-native');
var {CommonStyle} = require("../theme");
const width = Dimensions.get("window").width;

var TextInput = React.createClass({
    getInitialState: function () {
        return {
            value: this.props.value
        };
    },

    componentWillReceiveProps: function(nextPro) {
        if (nextPro.value && typeof this.state.value != "undefined" && this.state.value.length === 0) {
            this.setState({
                value: nextPro.value
            });
        }
        if (nextPro.multiline) {
            this.setState({
                value: nextPro.value || ""
            });
        }
    },

    render: function () {
        var containerStyle = this.props.containerStyle || {};
        var val = this.state.value;
        if (this.state.value != this.props.value && this.props.value
            && this.props.value.length == 0) {
            //清空内容
            val = this.props.value;
        }
        return (
            <View style={[CommonStyle.row, {width: width*2/3,height:24, alignItems: 'center',flex:1}, containerStyle]}>
                <MSTextInput
                    clearButtonMode={"always"}
                    {...this.props}
                    style={[{fontSize:15,flex:1,backgroundColor: 'transparent',padding:0},this.props.style]}
                    value={val}
                    onChangeText={this._onChangeText}
                    />
            </View>
        );
    },
    _onChangeText: function(text) {
        this.setState({
            value: text
        });
        this.props.onChangeText && this.props.onChangeText(text.replace(/ /g, ""));
    },
});

module.exports = TextInput;