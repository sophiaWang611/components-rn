/**
 * Created by sophia on 16/2/3.
 */
'use strict';

var React = require('react');
var {
    AppRegistry,
    StyleSheet,
    Text,
    View,
    ListView,
    TouchableOpacity,
    PixelRatio,
    Dimensions,
    TouchableWithoutFeedback,
    Animated,
    ScrollView,
    Image
    } = require('react-native');

var TestTheme = require("./theme/testTheme");

var TestInput = require("./TestInput");
var TestRefreshList = require("./TestRefreshList");
var TestMSEvaluate = require("./TestMSEvaluate");
var TestMSMapView = require("./TestMSMapView");
var TestMSRadio = require("./TestMSRadio");
var TestMSScrollableTabView = require("./TestMSScrollableTabView");
var TestMSSwiper = require("./TestMSSwiper");
var TestMSTextArea = require("./TestMSTextArea");
var TestMSPicker = require("./TestPicker");
var TestPhotoPicker = require("./TestPhotoPicker");

var UITestView = React.createClass({
    getInitialState: function() {
        return {};
    },

    render: function() {
        return (
            <ScrollView>
                <View>
                    <View style={TestTheme.separator}>
                        <TouchableOpacity style={TestTheme.btn} onPress={()=>{this._goPage(<TestMSPicker/>)}}>
                            <Text style={TestTheme.btnText}>测试MSPicker</Text>
                        </TouchableOpacity>
                    </View>
                    <View style={TestTheme.separator}>
                        <TouchableOpacity style={TestTheme.btn} onPress={()=>{this._goPage(<TestPhotoPicker/>)}}>
                            <Text style={TestTheme.btnText}>测试MSPhotoPicker</Text>
                        </TouchableOpacity>
                    </View>
                    <View style={TestTheme.separator}>
                        <TestInput/>
                    </View>
                    <View style={TestTheme.separator}>
                        <TouchableOpacity style={TestTheme.btn} onPress={()=>{this._goPage(<TestRefreshList/>)}}>
                            <Text style={TestTheme.btnText}>TestRefreshList</Text>
                        </TouchableOpacity>
                    </View>
                    <View style={TestTheme.separator}>
                        <TestMSEvaluate/>
                    </View>
                    <View style={TestTheme.separator}>
                        <TestMSMapView/>
                    </View>
                    <View style={TestTheme.separator}>
                        <TestMSRadio/>
                    </View>
                    <View style={TestTheme.separator}>
                        <TouchableOpacity style={[TestTheme.btn, {width:200}]} onPress={()=>{this._goPage(<TestMSScrollableTabView/>)}}>
                            <Text style={TestTheme.btnText}>TestMSScrollableTabView</Text>
                        </TouchableOpacity>
                    </View>
                    <View style={TestTheme.separator}>
                        <TestMSSwiper/>
                    </View>
                    <View style={TestTheme.separator}>
                        <TouchableOpacity style={TestTheme.btn} onPress={()=>{this._goPage(<TestMSTextArea/>)}}>
                            <Text style={TestTheme.btnText}>TestMSTextArea</Text>
                        </TouchableOpacity>
                    </View>
                </View>
            </ScrollView>
        );
    },
    _goPage: function(component) {
        this.props.navigator.push({
            component: component
        });
    }
});

module.exports = UITestView;