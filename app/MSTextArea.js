/**
 * Created by litian on 15/11/6.
 *
 * @providesModule MSTextAreaView
 */
'use strict';
var React = require('react');

var {
    StyleSheet,
    Text,
    View,
    TextInput,
    Image,
    PixelRatio,
    TouchableOpacity,
    Dimensions,
    Platform
    } = require('react-native');

var MSBase = require('./theme/base');
const width = Dimensions.get('window').width;

var MSTextInput = require("./MSTextInput");

var MSTextArea = React.createClass({
    propTypes:{
        onChangeText:React.PropTypes.func,
        autoFocus:React.PropTypes.bool,
        maxLength:React.PropTypes.number,
        placeholder:React.PropTypes.string,
        onFocus: React.PropTypes.func
    },
    getInitialState:function(){
        return {
            value:this.props.value || '',
            besides:0
        }
    },
    getDefaultProps(){
        return {
            maxLength: null,
            autoFocus: false
        }
    },
    componentWillMount:function(){
        this._updateBesides();
    },
    _updateBesides:function(){
        var len = this.state.value ? this.state.value.length : 0;
        if(this.props.maxLength){
            var besides = this.props.maxLength - len;
            this.setState({besides});
        }
    },
    _onChangeText:function(value){
        this.setState({value:value});
        setTimeout(() => {
            this._updateBesides();
        },2);

        if (typeof this.props.onChangeText === 'function'){
            this.props.onChangeText(value);
        }
    },
    componentWillReceiveProps:function(nextProps){
        this.setState({value:nextProps.value});
        setTimeout(() => {
            this._updateBesides();
        },2);
    },
    render:function(){
        var besides = this.props.maxLength ?
            <Text style={styles.besides}>{this.state.besides}</Text> : null;
        return (
            <View style={[styles.container, this.props.containerStyle]}>
                <MSTextInput
                    containerStyle={{width: width, height: 118, alignItems:"flex-start"}}
                    autoFocus={this.props.autoFocus}
                    maxLength={this.props.maxLength}
                    placeholder={this.props.placeholder}
                    returnKeyType={'next'}
                    clearButtonMode={'while-editing'}
                    style={[styles.textarea,{padding:0},Platform.OS != 'android'&&{height:118,justifyContent:'center',alignItems:'center'},this.props.style]}
                    onChangeText={this._onChangeText}
                    multiline={true}
                    numberOfLines={6}
                    editable={typeof this.props.editable === "undefined" ? true : this.props.editable}
                    value={this.state.value}
                    onFocus={(e)=>{this.props.onFocus && this.props.onFocus(e)}}
                    />
                {besides}
            </View>
        )
    },
});

var styles = StyleSheet.create({
    container:{
        position:'relative',
        borderBottomWidth:1/PixelRatio.get(),
        borderTopWidth:1/PixelRatio.get(),
        borderColor:MSBase.border_dark,
        paddingTop: 5,
        paddingBottom: 5,
        backgroundColor:MSBase.bg_white,
        height:118,
    },
    textarea:{
        fontSize:MSBase.font_size_16,
        width:width,
        backgroundColor:MSBase.bg_white,
        color:MSBase.font_blank_2,
        paddingLeft:MSBase.padding_left_lg,
        paddingRight:MSBase.padding_right_lg,
    },
    besides:{
        position:'absolute',
        right:10,
        bottom:10,
        color:MSBase.font_blank_4,
        backgroundColor:'rgba(0,0,0,0)',
        fontSize:MSBase.font_size_12
    }
});

module.exports = MSTextArea;