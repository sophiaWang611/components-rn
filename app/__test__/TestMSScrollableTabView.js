/**
 * Created by sophia on 16/2/3.
 */
'use strict';

var React = require('react');

var {
    StyleSheet,
    View,
    Text,
    TouchableWithoutFeedback,
    Animated,
    ListView,
    Dimensions,
    PixelRatio
    } = require('react-native');

var {CommonStyle,MSBase} = require("../theme");
var MSScrollableTabView = require("../MSScrollableTabView");
const deviceWidth = Dimensions.get('window').width;

var TestMSScrollableTabView = React.createClass({
    getInitialState: function() {
        return {
            list: [
                {title:"Tab1", number:"1"},
                {title:"Tab2", number:"2"},
                {title:"Tab3", number:"3"}
            ]
        };
    },

    render: function() {
        return (
            <View>
                <Text>Test MSScrollableTabView</Text>
                <MSScrollableTabView
                    edgeHitWidth={330}
                    initialPage={0}
                    renderTabBar={()=> <TabBar list={this.state.list}/>}>
                    {this.state.list.map((v, k)=>
                            <TabContent obj={v}
                                        key={k}
                                        tabLabel={v.id}/>
                    )}
                </MSScrollableTabView>
            </View>
        );
    }
});
var TabBar = React.createClass({

    propTypes: {
        goToPage: React.PropTypes.func,
        activeTab: React.PropTypes.number,
        tabs: React.PropTypes.array
    },

    renderTabOption(obj, page) {
        var isTabActive = this.props.activeTab === page;

        return (
            <TouchableWithoutFeedback onPress={() => this.props.goToPage(page)} key={page}>
                <View style={[styles.orderNumItem]}>
                    <Text style={[styles.orderNumItemText, isTabActive?styles.activeOrderText:{}]}>{obj.title}</Text>
                    <Text style={[styles.orderNumItemNum, isTabActive?styles.activeOrderText:{}]}>{obj.number}</Text>
                </View>
            </TouchableWithoutFeedback>
        );
    },

    render() {
        var numberOfTabs = this.props.tabs.length;
        var left = this.props.scrollValue.interpolate({
            inputRange: [0, 1], outputRange: [0, deviceWidth / numberOfTabs]
        });
        var tabUnderlineStyle = {
            position: 'absolute',
            width: deviceWidth / numberOfTabs,
            height: 2,
            backgroundColor: '#21ad5a',
            bottom: 0,
        };

        return (
            <View style={[styles.orderNumContainer, CommonStyle.row]}>
                {this.props.list.map((tab, i) => this.renderTabOption(tab, i))}
                <Animated.View style={[tabUnderlineStyle, {left}]} />
            </View>
        );
    },
});
var TabContent = React.createClass({
    getInitialState: function() {
        return {
            dataSource: new ListView.DataSource({
                rowHasChanged: (row1, row2) => row1 !== row2
            })
        };
    },
    componentWillMount: function() {
        var list = [];
        var obj = this.props.obj;
        for(var i = 0; i < 3; i++) {
            list.push(obj);
        }
        this.setState({
            dataSource: this.state.dataSource.cloneWithRows(list)
        });
    },
    render: function() {
        return (
            <ListView dataSource={this.state.dataSource}
                      renderRow={this._renderRow}/>
        );
    },
    _renderRow: function(item) {
        return (
            <View style={[CommonStyle.allCenter,
            {height:35,marginTop:1,backgroundColor:"#fff",borderWidth:1,borderColor:"#eee"}]}>
                <Text style={{color:"#000"}}>{item.title}</Text>
            </View>
        );
    }
});

var styles = StyleSheet.create({
    rderTitleBTNContainer: {
        borderWidth:1/PixelRatio.get(),
        borderColor:"#21af5e",
        flexDirection: "row",
    },
    orderNumContainer: {
        backgroundColor:"#fff",
        borderBottomWidth: 1/PixelRatio.get(),
        borderTopWidth: 1/PixelRatio.get(),
        borderColor:"#eee",
    },
    orderNumItem: {
        flex: 1,
        alignItems: 'center',
        width: 59,
    },
    orderNumItemText: {
        color: "#444",
        marginTop: 10,
        fontSize: MSBase.font_size_16,
        marginBottom: 5,
    },
    orderNumItemNum: {
        color: "#444",
        fontSize: MSBase.font_size_24,
        marginBottom: 10,
    },
    activeTitle: {
        backgroundColor:"#21af5e",
        color: "#FFF"
    },
    activeOrderText: {
        color:"#21ad5a"
    },
});

module.exports = TestMSScrollableTabView;