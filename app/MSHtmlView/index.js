/**
 * Created by sophia on 16/4/5.
 */
'use strict';

var React = require('react');
var {Platform} = require('react-native');
var Component = null;

var MSHtmlView = React.createClass({
    propTypes:{
        text: React.PropTypes.string,
        paddingTop: React.PropTypes.number,
        paddingBottom: React.PropTypes.number,
        paddingLeft: React.PropTypes.number,
        paddingRight: React.PropTypes.number,
    },

    render: function () {
        if (Platform.OS === 'android') {
            Component = require("./MSTextView");
            return (
                <Component {...this.props}>{this.props.text}</Component>
            );
        } else {
            Component = require("./MSHtmlView");
            return (
                <Component {...this.props}/>
            );
        }
    }
});

module.exports = MSHtmlView;