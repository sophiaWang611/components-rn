/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */
'use strict';

var React = require('react');
var {
    AppRegistry,
    StyleSheet,
    Text,
    Navigator,
    TouchableOpacity,
    PixelRatio,
    Image,
    View,
    ScrollView
    } = require('react-native');

var {CommonStyle, MSBase} = require("./app/theme");
var Test = require("./app/__test__");

var RNComponents = React.createClass({
    getInitialState: function() {
        return {
            initialRoute: {
                component: <Test/>
            }
        };
    },

    render: function() {
        return (
            <Navigator
                initialRoute={this.state.initialRoute}
                renderScene={this.renderScene}
                configureScene={(route) => {
                    if (route.sceneConfig) {
                        return route.sceneConfig;
                    }
                    return Navigator.SceneConfigs.FloatFromRight;
                }}
                navigationBar={
                 <Navigator.NavigationBar
                   routeMapper={RouteMap}
                   style={{backgroundColor: '#F5F5F5'}}
                 />
              }
                />
        );
    },
    renderScene: function(route, nav) {
        if (route.component) {
            return React.cloneElement(route.component, {
                navigator: nav,
                route: route,
            });
        }
    }
});

var RouteMap = {
    LeftButton: (route, navigator, index, navState) => {
        return (
            <TouchableOpacity onPress={() => navigator.pop()}>
                <View><Text>Back</Text></View>
            </TouchableOpacity>
        );
    },
    RightButton: (route, navigator, index, navState) => { return null; },
    Title: (route, navigator, index, navState) => {
        return (<Text>{route&&route.title || "Test Page"}</Text>);
    }
};

module.exports = RNComponents;