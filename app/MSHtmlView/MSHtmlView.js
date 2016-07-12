/**
 * Created by sophia on 16/3/18.
 */
'use strict';

var React = require('react');

var {
    StyleSheet,
    View,
    requireNativeComponent,
    } = require('react-native');

var MSHtmlTxtView = requireNativeComponent('MSHtmlTxt', null);

var MSTextView = React.createClass({
    propTypes:{
        ...View.propTypes,
        text: React.PropTypes.string,
        paddingTop: React.PropTypes.number,
        paddingBottom: React.PropTypes.number,
        paddingLeft: React.PropTypes.number,
        paddingRight: React.PropTypes.number,
    },

    render: function () {
        return (
            <MSHtmlTxtView {...this.props}/>
        );
    }
});

module.exports = MSTextView;