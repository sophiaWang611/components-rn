'use strict'

var React = require('react');
var {
    requireNativeComponent,
    PropTypes,
    } = require('react-native');

var RCTSeekBar = requireNativeComponent('RCTSeekBar', null);

module.exports = React.createClass({
    propTypes: {
        value: PropTypes.number,
        minimumValue: PropTypes.number,
        maximumValue: PropTypes.number,
        onValueChange: PropTypes.func,
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