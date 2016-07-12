/**
 * Created by sophia on 16/2/3.
 */
'use strict';

var React = require('react');

var {
    StyleSheet,
    View,
    Text
    } = require('react-native');
var {CommonStyle,MSBase} = require("../theme");
var MSTextInput = require("../MSTextInput");

var TestInput = React.createClass({
    getInitialState: function() {
        return {
            account: "2222"
        };
    },

    render: function() {
        return (
            <View>
                <Text>Test MSTextInput</Text>
                <View style={[styles.inputContainer]}>
                    <MSTextInput keyboardType="numeric"
                                 style={styles.textInput}
                                 mask={"#### #### #### #### ####"}
                                 value={this.state.account}
                                 placeholder="输入卡号"
                                 onChangeText={(text)=>{this.setState({account:text.trim()})}}/>
                </View>
            </View>
        );
    }
});

var styles = StyleSheet.create({
    textInput: {
        flex:1,
        width: 175,
        fontSize: 14
    },
    inputContainer: {
        borderColor:MSBase.font_green,
        borderWidth:1,
        paddingTop:5,
        paddingBottom:5,
        paddingLeft:10,
        paddingRight:10,
        borderRadius: 2,
        height: 33,
    }
});

module.exports = TestInput;