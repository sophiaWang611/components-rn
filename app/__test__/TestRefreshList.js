/**
 * Created by sophia on 16/2/3.
 */
'use strict';

var React = require('react');

var {
    StyleSheet,
    View,
    ListView,
    Text
    } = require('react-native');

var {CommonStyle,MSBase} = require("../theme");
var MSRefreshableList = require("../MSRefreshableList");

var TestRefreshList = React.createClass({
    getInitialState: function() {
        return {
            dataSource: new ListView.DataSource({
                rowHasChanged: (row1, row2) => row1 !== row2
            }),
            allList: []
        };
    },

    componentWillMount: function() {
        var list = this._getData();
        this.setState({
            allList: list,
            dataSource: this.state.dataSource.cloneWithRows(list)
        })
    },

    _getData: function() {
        var list = this.state.allList, len = list.length;
        for (var i = (len + 1); i < (len + 11); i++) {
            list.push({id: i, name: "testData" + i});
        }
        return list;
    },

    render: function() {
        return (
            <View style={{height: 300}}>
                <Text>Test MSRefreshableList</Text>
                <MSRefreshableList
                    style={{marginTop: 5}}
                    loadData={this._getData}
                    dataSource={this.state.dataSource}
                    renderRow={this._renderRow}
                    onEndReached={this._getData}
                    pageSize= {5}
                    onEndReachedThreshold={20}
                    />
            </View>
        );
    },
    _renderRow: function(item) {
        return (
            <View style={[CommonStyle.allCenter,
            {height:35,marginTop:1,backgroundColor:"#fff",borderWidth:1,borderColor:"#eee"}]}>
                <Text style={{color:"#000"}}>{item.name}</Text>
            </View>
        );
    }
});


var styles = StyleSheet.create({});

module.exports = TestRefreshList;