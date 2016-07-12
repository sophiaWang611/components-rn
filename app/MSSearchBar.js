/**
 * Created by sophia on 16/3/5.
 *
 * @providesModule MSSearchBar
 */
'use strict';

var React = require('react');

var {
    StyleSheet,
    View,
    Text,
    Image,
    TouchableWithoutFeedback,
    TextInput
    } = require('react-native');


var MSSearchBar = React.createClass({
    getInitialState: function () {
        return {
            dom: this._beforeSearch,
        };
    },

    render: function() {
        return this.state.dom();
    },

    _beforeSearch: function() {
        return (
            <TouchableWithoutFeedback onPress={()=>{this.setState({dom: this._showEditInput})}}>
                <View style={{marginTop:5,marginBottom:5, borderRadius:2, height:34, flex:1, marginRight:15, backgroundColor:"#f5f5f5",alignItems: 'center',justifyContent: 'center'}}>
                    <View style={{flexDirection: "row",alignItems: 'center',justifyContent: 'center'}}>
                        <Image source={require("./img/search_icon_gray.png")}
                               style={{height:12,width:12}}/>
                        <Text style={{color:"#999", fontSize:13, marginLeft:5}}>搜索地点</Text>
                    </View>
                </View>
            </TouchableWithoutFeedback>
        )
    },
    _showEditInput: function() {
        return (
            <View style={{marginTop:5,marginBottom:5, flexDirection: "row",alignItems: 'center',height:34, flex:1, marginRight:15}}>
                <View style={{flexDirection: "row",alignItems: 'center',borderRadius:2, flex:1, backgroundColor:"#f5f5f5"}}>
                    <View style={{justifyContent:"flex-start", marginLeft: 5}}>
                        <Image source={require("./img/search_icon_gray.png")} style={{height:12,width:12}}/>
                    </View>
                    <TextInput placeholder={this.props.placeholder || "搜索地址"}
                               autoFocus={true}
                               onChangeText={this._onChange}
                               style={{height:35,flex:1, fontSize: 14}}/>
                </View>
                <View style={{justifyContent:"flex-end",width:40, alignItems:"flex-end"}}>
                    <Text style={{fontSize: 16, color: "#007aff"}} onPress={this._doClear}>取消</Text>
                </View>
            </View>
        )
    },
    _onChange: function(txt) {
        this.props.onChange && this.props.onChange(txt);
    },
    _doClear: function() {
        this.setState({dom: this._beforeSearch});
        this._onChange("");
    }
});

var styles = StyleSheet.create({});

module.exports = MSSearchBar;
