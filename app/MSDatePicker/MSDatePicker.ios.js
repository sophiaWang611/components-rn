/**
 * Copyright (c) 2015-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 *
 * @providesModule MSDatePickerIOS
 * @flow
 *
 * This is a controlled component version of RCTDatePickerIOS
 */
'use strict';

var {
    NativeMethodsMixin,
    UIManager,
    StyleSheet,
    View,
    requireNativeComponent
    } = require('react-native');

var React = require('React');
var RCTDatePickerIOSConsts = UIManager.RCTDatePicker.Constants;


type DefaultProps = {
    mode: 'date' | 'time' | 'datetime';
};

type Event = Object;

/**
 * Use `MSDatePickerIOS` to render a date/time picker (selector) on iOS.  This is
 * a controlled component, so you must hook in to the `onDateChange` callback
 * and update the `date` prop in order for the component to update, otherwise
 * the user's change will be reverted immediately to reflect `props.date` as the
 * source of truth.
 */
var MSDatePickerIOS = React.createClass({
        // TOOD: Put a better type for _picker
        _picker: (undefined: ?$FlowFixMe),

    mixins: [NativeMethodsMixin],

    propTypes: {
...View.propTypes,
    /**
     * The currently selected date.
     */
        date: React.PropTypes.instanceOf(Date).isRequired,

    /**
     * Date change handler.
     *
     * This is called when the user changes the date or time in the UI.
     * The first and only argument is a Date object representing the new
     * date and time.
     */
        onChange: React.PropTypes.func.isRequired,

    /**
     * Maximum date.
     *
     * Restricts the range of possible date/time values.
     */
        maximumDate: React.PropTypes.instanceOf(Date),

    /**
     * Minimum date.
     *
     * Restricts the range of possible date/time values.
     */
        minimumDate: React.PropTypes.instanceOf(Date),

    /**
     * The date picker mode.
     */
        mode: React.PropTypes.oneOf(['date', 'time', 'datetime']),

    /**
     * The interval at which minutes can be selected.
     */
        minuteInterval: React.PropTypes.oneOf([1, 2, 3, 4, 5, 6, 10, 12, 15, 20, 30]),

    /**
     * Timezone offset in minutes.
     *
     * By default, the date picker will use the device's timezone. With this
     * parameter, it is possible to force a certain timezone offset. For
     * instance, to show times in Pacific Standard Time, pass -7 * 60.
     */
        timeZoneOffsetInMinutes: React.PropTypes.number,
},

getDefaultProps: function(): DefaultProps {
    return {
        mode: 'datetime',
    };
},

getInitialState: function () {
    return {date: this.props.date}
},

_onChange: function(event: Event) {
    var nativeTimeStamp = event.nativeEvent.timestamp;
    this.props.onDateChange && this.props.onDateChange(
        new Date(nativeTimeStamp)
    );
    this.props.onChange && this.props.onChange(event);

    // We expect the onChange* handlers to be in charge of updating our `date`
    // prop. That way they can also disallow/undo/mutate the selection of
    // certain values. In other words, the embedder of this component should
    // be the source of truth, not the native component.
    var propsTimeStamp = this.props.date.getTime();
    if (this._picker && nativeTimeStamp !== propsTimeStamp) {
        this._picker.setNativeProps({
            date: propsTimeStamp,
        });
    }
},

render: function() {
    var props = this.props;
    return (
        <View style={props.style}>
            <RCTDatePickerIOS
                ref={ picker => this._picker = picker }
                style={styles.datePickerIOS}
                date={props.date.getTime()}
                mode={props.mode}
                minuteInterval={props.minuteInterval}
                timeZoneOffsetInMinutes={props.timeZoneOffsetInMinutes}
                onChange={this._onChange}
                />
        </View>
    );
}
});

let wWidth = require('react-native').Dimensions.get('window').width;
var styles = StyleSheet.create({
    datePickerIOS: {
        height: RCTDatePickerIOSConsts.ComponentHeight,
        width: wWidth
    }
});

var RCTDatePickerIOS = requireNativeComponent('RCTDatePicker', MSDatePickerIOS, {
    nativeOnly: { onChange: true },
});

module.exports = MSDatePickerIOS;
