/**
 * This is extend from React native v0.13.0
 *
 * items = [[],[]]
 * titles=['','']
 * defaultIndex = [1,2]
 *
 * <MSPicker
 *  items={items} 传入值
 *  titles={titles}
 *  defaultIndex={defaultIndex}
 *  isShow={this.state.showPicker} 控制是否显示
 *  onChange={(firstIndex, secondIndex)=>{}}
 *  onConfirm={(firstIndex, secondIndex) => { 点击确认后的回调函数
 *      console.log("222222")
 *      console.log(firstIndex);
 *      console.log(secondIndex);
 *      console.log("222222")
 *  }}>
 * </MSPicker>
 */
'use strict';

var NativeMethodsMixin = require('NativeMethodsMixin');
var React = require('React');
var ReactChildren = require('ReactChildren');
var ReactNativeViewAttributes = require('ReactNativeViewAttributes');
var RCTPickerIOSConsts = require('NativeModules').UIManager.MSPicker.Constants;
var StyleSheet = require('StyleSheet');
var View = require('View');
var Text = require('Text');
var TouchableWithoutFeedback = require('TouchableWithoutFeedback');
var PixelRatio = require('PixelRatio');
var Animated = require('Animated');
var {CommonStyle} = require("../theme/index");

var requireNativeComponent = require('requireNativeComponent');
var merge = require('merge');

var PICKER = 'picker';

var MSPicker = React.createClass({
    mixins: [NativeMethodsMixin],

    propTypes: {
        onChange: React.PropTypes.func,
        onConfirm: React.PropTypes.func,
        onCancel: React.PropTypes.func,
        titles: React.PropTypes.array,
        items: React.PropTypes.array,
        defaultIndex: React.PropTypes.array,
        isShow: React.PropTypes.bool,
        componentSize: React.PropTypes.oneOf([1, 2]),
        offset: React.PropTypes.number
    },

    getInitialState: function() {
        var stateVal = {
            isShow: this.props.isShow,
            offset: this.props.offset
        };
        return Object.assign({}, stateVal, this._stateFromProps(this.props));
    },

    componentWillReceiveProps: function(nextProps) {
        this.setState({
            selectedIndex: nextProps.defaultIndex ? (nextProps.defaultIndex[0] || 0) : 0,
            childIndex: nextProps.defaultIndex ? (nextProps.defaultIndex[1] || 0) : 0,
            items: nextProps.items,
            offset: nextProps.offset
        });
        var isShow = nextProps.isShow;
        if (isShow) {
            this._showPicker(nextProps.offset);
        } else {
            this._hidePicker();
        }
    },

    // Translate PickerIOS prop and children into stuff that RCTPickerIOS understands.
    _stateFromProps: function(props) {
        if (props.defaultIndex) {
            var selectedIndex = props.defaultIndex[0] || 0;
            var childIndex = props.defaultIndex[1] || 0;
        }
        var top = new Animated.Value(100000);
        var titles = props.titles;
        return {selectedIndex, childIndex, top, titles};
    },

    _hidePicker: function() {
        if (this.state && this.state.top) {
            Animated.timing(
                this.state.top,
                {toValue: 100000}
            ).start();
        }
    },
    _showPicker: function(offset) {
        if (this.state && this.state.top) {
            var val = RCTPickerIOSConsts.ScreenHeight
                - (40 + RCTPickerIOSConsts.ComponentHeight)
                - 64 + (offset || 0);
            Animated.timing(
                this.state.top,
                {toValue: val}
            ).start();
        }
    },

    render: function() {
        var titleDiv = <View/>;
        if (this.state.titles && this.state.titles.length == 2) {
            titleDiv = (
                <View style={[CommonStyle.row,CommonStyle.container, {height:25, justifyContent: 'center',}]}>
                    <View style={[CommonStyle.allCenter, {flex:1}]}>
                        <Text style={[CommonStyle.font777,CommonStyle.font28]}>{this.state.titles[0]}</Text>
                    </View>
                    <View style={[CommonStyle.allCenter, {flex:1}]}>
                        <Text style={[CommonStyle.font777,CommonStyle.font28]}>{this.state.titles[1]}</Text>
                    </View>
                </View>
            );
        }
        return (
            <Animated.View style={{top:this.state.top,position:"absolute"}}>
                <View style={[styles.pickContainer, this.props.style]}>
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
                    {titleDiv}
                    <MSPickerIOS
                        ref={PICKER}
                        style={styles.pickerIOS}
                        items={this.state.items}
                        selectedIndex={this.state.selectedIndex}
                        childIndex={this.state.childIndex}
                        componentSize={1}
                        onChange={this._onChange}
                        />
                </View>
            </Animated.View>
        );
    },

    _onChange: function(event) {
        this.setState({
            selectedIndex: event.nativeEvent.firstIndex,
            childIndex: event.nativeEvent.secondIndex,
        });
        if (this.props.onChange) {
            this.props.onChange(event.nativeEvent.firstIndex, event.nativeEvent.secondIndexs);
        }
    },
    _onConfirm: function() {
        if (this.props.onConfirm) {
            this.props.onConfirm(this.state.selectedIndex, this.state.childIndex);
        }
    },
    _onCancel: function() {
        this.props.onCancel && this.props.onCancel();
    }
});

var styles = StyleSheet.create({
    pickerIOS: {
        // The picker will conform to whatever width is given, but we do
        // have to set the component's height explicitly on the
        // surrounding view to ensure it gets rendered.
        height: RCTPickerIOSConsts.ComponentHeight,
    },
    pickContainer: {
        borderTopWidth: 1/PixelRatio.get(),
        borderBottomWidth: 1/PixelRatio.get(),
        borderColor: "#ccc",
        backgroundColor: "#FFF",
        width: RCTPickerIOSConsts.ScreenWidth,
        height: RCTPickerIOSConsts.ComponentHeight + 40
    },
    pickBTN: {
        flex:1,
        justifyContent: 'center',
    }
});

var MSPickerIOS = requireNativeComponent('MSPicker', MSPicker, {
    nativeOnly: {
        items: true,
        onChange: true,
        selectedIndex: true,
        childIndex: true,
        isShow: true,
        componentSize: true,
    },
});

module.exports = MSPicker;
