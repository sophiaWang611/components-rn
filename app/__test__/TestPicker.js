/**
 * Created by sophia on 16/2/15.
 */
'use strict';

var React = require('react');

var {
    StyleSheet,
    View,
    Text,
    TouchableOpacity,
    } = require('react-native');

var {CommonStyle, MSBase} = require("../theme");
var MSPicker = require("../MSPicker");

var TestPicker = React.createClass({
    getInitialState: function () {
        var doubleItem = [], time = [];
        for (let i = 0; i < 24; i++) {
            time[i] = (i < 10 ? "0" : "") + i;
        }
        doubleItem[0] = time;
        for (let i = 0; i < 60; i++) {
            time[i] = (i < 10 ? "0" : "") + i;
        }
        doubleItem[1] = time;
        return {
            item: ["男", "女"],
            doubleItem: doubleItem,
            showPicker: false,
            showDoublePicker: false,
            selectedReason: "",
            firstSelected: "",
            secondSelected: ""
        };
    },

    render: function () {
        return (
            <View style={{marginTop:20}}>
                <Text>Test MSPicker</Text>
                <TouchableOpacity
                    style={[{justifyContent: 'center',paddingLeft: 5,height:20, backgroundColor:MSBase.bg_green}]}
                    onPress={()=>{this.setState({showPicker:true})}}>
                    <Text>显示单项选择器, 值：{this.state.selectedReason}</Text>
                </TouchableOpacity>
                <MSPicker
                    componentSize={1}
                    items={[this.state.item]}
                    titles={["选择性别"]}
                    defaultIndex={[0]}
                    isShow={this.state.showPicker}
                    onConfirm={(firstIndex, secondIndex) => {
                        this.setState({
                            selectedReason: this.state.item[firstIndex],
                            showPicker: false
                        });
                    }}
                    onCancel={()=>{this.setState({showPicker:false})}}>
                </MSPicker>

                <TouchableOpacity
                    style={[{justifyContent: 'center',paddingLeft: 5,height:20, marginTop: 20, backgroundColor:MSBase.bg_green}]}
                    onPress={()=>{this.setState({showDoublePicker:true})}}>
                    <Text>显示双相项选择器, 值：{this.state.firstSelected}:{this.state.secondSelected}</Text>
                </TouchableOpacity>
                <MSPicker
                    componentSize={2}
                    items={this.state.doubleItem}
                    titles={["开始时间", "结束时间"]}
                    defaultIndex={[0, 0]}
                    isShow={this.state.showDoublePicker}
                    onConfirm={(firstIndex, secondIndex) => {
                        this.setState({
                            firstSelected: this.state.doubleItem[0][firstIndex],
                            secondSelected: this.state.doubleItem[1][secondIndex],
                            showDoublePicker: false
                        });
                    }}
                    onCancel={()=>{this.setState({showDoublePicker:false})}}>
                </MSPicker>
            </View>
        );
    }
});

var styles = StyleSheet.create({});

module.exports = TestPicker;