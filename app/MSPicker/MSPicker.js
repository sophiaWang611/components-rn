/**
 * Created by sophia on 16/2/15.
 */
'use strict';

var React = require('react');

var {
    StyleSheet,
    View,
    Picker,
    NativeModules
    } = require('react-native');

var MSNativePicker = NativeModules.MSPicker;

var MSPicker = React.createClass({
    propTypes: {
        onConfirm: React.PropTypes.func,
        onCancel: React.PropTypes.func,
        titles: React.PropTypes.array,
        items: React.PropTypes.array,
        defaultIndex: React.PropTypes.array,
        isShow: React.PropTypes.bool,
        componentSize: React.PropTypes.oneOf([1, 2]),
    },

    getInitialState: function() {
        return this._stateFromProps(this.props);
    },

    componentWillReceiveProps: function(nextProps) {
        this.setState(this._stateFromProps(nextProps));
    },

    // Translate PickerIOS prop and children into stuff that RCTPickerIOS understands.
    _stateFromProps: function(props) {
        if (props.defaultIndex) {
            var selectedIndex = props.defaultIndex[0] || 0;
            var childIndex = props.defaultIndex[1] || 0;
        }
        var titles = props.titles || [""], items = props.items,
            componentSize = props.componentSize, isShow = props.isShow,
            onConfirm = props.onConfirm, onCancel = props.onCancel;
        return {selectedIndex, childIndex, titles, items,
            componentSize, isShow, onConfirm, onCancel};
    },

    render: function () {
        if (this.state.componentSize === 1) {
            return this.dialogPicker();
        } else if (this.state.componentSize === 2) {
            return this.picker();
        } else {
            return <View/>
        }
    },

    dialogPicker: function() {
        if (this.state.isShow) {
            MSNativePicker.showPickerDialog(this.state.items, this.state.titles,
                this._onConfirm, this._onCancel);
        }
        return <View/>
    },

    picker: function() {
        if (this.state.isShow) {
            MSNativePicker.showColumnPicker(this.state.items, this.state.titles,
                [this.state.selectedIndex, this.state.childIndex], this._onConfirm, this._onCancel);
        }
        return <View/>
    },

    _onConfirm: function(data) {
        var returnVal = data || {};
        this.state.onConfirm && this.state.onConfirm(returnVal.selectedIndex, returnVal.childIndex)
    },

    _onCancel: function(data) {
        var returnVal = data || {};
        this.state.onCancel && this.state.onCancel(returnVal.selectedIndex, returnVal.childIndex)
    }

});

var styles = StyleSheet.create({});

module.exports = MSPicker;