/**
 * Created by sophia on 16/4/12.
 */
'use strict';

var React = require('react');

var {
    StyleSheet,
    View,
    Text,
    Platform,
    TouchableWithoutFeedback,
    Dimensions,
    PixelRatio,
    DatePickerAndroid
    } = require('react-native');

var PropTypes = require('ReactPropTypes');
var Animated = require('Animated');
var {CommonStyle} = require("../theme/index");
var wHeight = Dimensions.get('window').height;
var wWidth = Dimensions.get('window').width;

var DatePicker = null;
var componentHeight = 0;
if (Platform.OS !== 'android') {
    DatePicker = require("./MSDatePicker.ios");
    var RCTDatePickerIOSConsts = require('UIManager').RCTDatePicker.Constants;
    componentHeight = RCTDatePickerIOSConsts.ComponentHeight;
} else {
    DatePicker = DatePickerAndroid;
}

var MSDatePicker = React.createClass({
    propTypes: {
        onChange: React.PropTypes.func,
        onConfirm: React.PropTypes.func,
        onCancel: React.PropTypes.func,
        isShow: React.PropTypes.bool,
        offsetY: React.PropTypes.number,
        defaultDate: React.PropTypes.string.isRequired,
        minDate: React.PropTypes.string,
        maxDate: React.PropTypes.string,
    },

    getInitialState: function () {
        return {
            selectedDate: this.props.defaultDate,
            isShow: this.props.isShow,
            top: new Animated.Value(10000),
            offsetY: this.props.offsetY,
            minDate: this.props.minDate,
            maxDate: this.props.maxDate
        };
    },

    componentWillReceiveProps: function(props) {
        if (Platform.OS === 'android') {
            if (props.isShow) {
                this.showAndroidPicker(props.defaultDate, props.minDate, props.maxDate);
            }
        } else {
            this.setState({
                selectedDate: props.defaultDate,
                isShow: props.isShow,
                offsetY: props.offsetY,
                minDate: props.minDate,
                maxDate: props.maxDate
            });
            if (props.isShow) {
                this._showPicker(props.offsetY);
            } else {
                this._hidePicker();
            }
        }
    },

    async showAndroidPicker(selectedDate, minDate, maxDate) {
        const {action, year, month, day} = await DatePicker.open({
            date: this._convertDate(selectedDate),
            minDate: this._convertDate(minDate),
            maxDate: this._convertDate(maxDate)
        });
        if (action === DatePicker.dateSetAction) {
            this._onConfirm(new Date(year, month, day));
        }
        if (action === DatePicker.dismissedAction) {
            this._onCancel();
        }
    },

    render: function () {
        if (Platform.OS === 'android') {
            return <View/>
        }
        return (
            <Animated.View style={{top:this.state.top, position:"absolute", opacity:Number(this.state.isShow)}}>
                <View style={[styles.pickContainer]}>
                    <View style={[CommonStyle.row,{height:40, borderColor:"#CCC",borderBottomWidth:1/PixelRatio.get()}]}>
                        <TouchableWithoutFeedback onPress={()=> {this._onCancel()}}>
                            <View style={[CommonStyle.pageLeft, styles.pickBTN,{alignItems:"flex-start"}]}>
                                <Text style={[CommonStyle.font21af5e,CommonStyle.font28]}>取消</Text>
                            </View>
                        </TouchableWithoutFeedback>
                        <TouchableWithoutFeedback onPress={()=> {this._onConfirm()}}>
                            <View style={[CommonStyle.pageRight, styles.pickBTN,{alignItems:"flex-end"}]}>
                                <Text style={[CommonStyle.font21af5e,CommonStyle.font28]}>确定</Text>
                            </View>
                        </TouchableWithoutFeedback>
                    </View>
                    <DatePicker minimumDate={this._convertDate(this.state.minDate)}
                                maximumDate={this._convertDate(this.state.maxDate)}
                                mode={"date"}
                                date={this._convertDate(this.state.selectedDate)}
                                onChange={this._onDateChange}/>
                </View>
            </Animated.View>
        );
    },
    _convertDate: function(dateStr) {
        if (!dateStr || dateStr.length <= 0) return new Date();

        if (Object.prototype.toString.apply(dateStr) === '[object Date]') return dateStr;

        var dateArr = dateStr.replace(/\//g, "-").split("-");
        return new Date(dateArr[0], (dateArr[1] - 1), dateArr[2]);
    },
    _hidePicker: function() {
        if (this.state && this.state.top) {
            Animated.timing(
                this.state.top,
                {toValue: 10000}
            ).start();
        }
    },
    _showPicker: function(offsetY) {
        if (this.state && this.state.top) {
            var val = wHeight - (40 + componentHeight) - 64
                + (offsetY || 0);
            Animated.timing(
                this.state.top,
                {toValue: val}
            ).start();
        }
    },
    _onCancel: function() {
        this.props.onCancel && this.props.onCancel();
    },
    _onConfirm: function(date) {
        var d = date || this.state.selectedDate;
        this.setState({selectedDate: d});
        this.props.onConfirm && this.props.onConfirm(d);
    },
    _onDateChange: function(date) {
        this.props.onChange && this.props.onChange(date);
        let d = new Date(date.nativeEvent.timestamp),
            month = (d.getMonth() + 1), day = d.getDate();
        month = month > 10 ? month : ("0" + month);
        day = day > 10 ? day : ("0" + day);
        this.setState({selectedDate: d.getFullYear() + "-" + month + "-" + day});
    }
});

var styles = StyleSheet.create({
    pickContainer: {
        borderTopWidth: 1/PixelRatio.get(),
        borderBottomWidth: 1/PixelRatio.get(),
        borderColor: "#ccc",
        backgroundColor: "#FFF",
        width: wWidth,
        height: componentHeight + 40
    },
    pickBTN: {
        flex:1,
        justifyContent: 'center',
    }
});

module.exports = MSDatePicker;