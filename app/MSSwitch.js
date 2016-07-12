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
    Switch,
    requireNativeComponent
    } = require('react-native');

if (Platform.OS === 'android') {
    var NativeMethodsMixin = require('NativeMethodsMixin');
    var MSSwitchView = requireNativeComponent('MSSwitchView', null);

    module.exports = React.createClass({
       mixins: [NativeMethodsMixin],
       propTypes: {
            ...View.propTypes,
            value: React.PropTypes.bool,
            enable: React.PropTypes.bool,
            onValueChange: React.PropTypes.func
        },

        getDefaultProps: function () {
            return {
                enable: true,
                value: false
            };
        },

        render: function () {
            return (
                <MSSwitchView {...this.props} onChange={this._onChange}/>
            );
        },
        _onChange: function(event) {
            if (this.props.value === event.nativeEvent.value || !this.props.enable) {
                return;
            }

            this.props.onChange && this.props.onChange(event);
            this.props.onValueChange && this.props.onValueChange(event.nativeEvent.value);
        },
    });
} else {
    module.exports = Switch;
}
