/**
 * Created by sophia on 16/2/3.
 */
'use strict';

var React = require('react');

var {
    StyleSheet,
    View,
    Text,
    } = require('react-native');

var {CommonStyle} = require("../theme");
var MSRadio = require("../MSRadio");

var TestMSRadio = React.createClass({
    getInitialState: function() {
        return {
            value: false
        };
    },

    render: function() {
        return (
            <View>
                <Text>Test MSRadio</Text>
                <View style={[CommonStyle.row]}>
                    <Text style={{width:100}}>{this.state.value ? "选中" : "未选中"}</Text>
                    <MSRadio checked={this.state.value}
                             onChange={(checked)=>{this.setState({value:checked});}}/>
                </View>
            </View>
        );
    }
});

var styles = StyleSheet.create({});

module.exports = TestMSRadio;