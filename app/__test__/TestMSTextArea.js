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

var MSTextArea = require("../MSTextArea");

var TestMSTextArea = React.createClass({
    getInitialState: function() {
        return {
            text: ""
        };
    },
    render: function() {
        return (
            <View>
                <Text>Test MSTextArea</Text>
                <MSTextArea value={this.state.text}
                            maxLength={16}
                            autoFocus={false}
                            placeholder={"请填写5-16字的说明"}
                            onChangeText={(text)=>{this.setState({text:text})}}/>
            </View>
        );
    }
});

var styles = StyleSheet.create({});

module.exports = TestMSTextArea;