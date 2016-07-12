/**
 * Created by sophia on 16/2/3.
 */
'use strict';

var React = require('react');

var {
    StyleSheet,
    View,
    } = require('react-native');

var MSEvaluate = require("../MSEvaluate");

var TestMSEvaluate = React.createClass({
    render: function() {
        return (
            <View>
                <MSEvaluate score={1}/>
                <MSEvaluate score={2}/>
                <MSEvaluate score={3}/>
                <MSEvaluate score={4}/>
                <MSEvaluate score={5}/>
            </View>
        );
    }
});

var styles = StyleSheet.create({});

module.exports = TestMSEvaluate;