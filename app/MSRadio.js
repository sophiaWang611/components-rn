/**
 * Created by litian on 15/11/5.
 */
'use strict';
/**
 * 选中
 * */
var React = require('react');

var {
    Text,
    View,
    Image,
    TouchableHighlight,
    StyleSheet
    } = require('react-native');

var MSBase = require('./theme/base');

var MSRadio = React.createClass({
    getInitialState:function(){
        return {}
    },
    _onPress:function(){
        var checked = !this.props.checked;
        if (typeof this.props.onChange === 'function'){
            this.props.onChange(checked);
        }
    },
    render:function(){
        var icon = this.props.checked ? require("./img/icon_dispatching2.png") : require("./img/icon_dispatching.png");
        var textColor = this.props.checked ? MSBase.font_blank_2 : MSBase.font_blank_4;
        var txt = <Text/>;
        if (this.props.text&&this.props.text.length!=0) {
            txt = <Text style={[styles.txt,{color:textColor},(this.props.textStyle||{})]}>{this.props.text}</Text>
        }
        return (
            <TouchableHighlight onPress={this._onPress} style={{flex:1}} underlayColor='#fff' activeOpacity={1}>
                <View style={{flexDirection:'row', alignItems:'center', justifyContent:'flex-start'}}>
                    <Image source={icon} style={[{width:22,height:22}]} />
                    {txt}
                </View>
            </TouchableHighlight>
        )
    }
});

var styles = StyleSheet.create({
    txt: {
        fontSize:MSBase.font_size_16,
        marginLeft:15
    }
});

module.exports = MSRadio;