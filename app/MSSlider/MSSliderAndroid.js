'use strict'

var React = require('react');
var {
    requireNativeComponent,
    } = require('react-native');

var RCTSeekBar = requireNativeComponent('RCTSeekBar', null);

module.exports = React.createClass({
    propTypes: {
        value: React.PropTypes.number,
        minimumValue: React.PropTypes.number,
        maximumValue: React.PropTypes.number,
        onValueChange: React.PropTypes.func,
    },

    getInitialState() {
        return {
            value: this.props.value,
        };
    },

    _onChange(event:Event) {
        if (!this.props.onValueChange) {
            return;
        }
        this.props.onValueChange(event.nativeEvent.progress);
    },

    render() {
        return <RCTSeekBar  {...this.props}
                    value={this.state.value}
                    style={[{height:32}, this.props.style]}
                    onChange={this._onChange}/>;
    },
});