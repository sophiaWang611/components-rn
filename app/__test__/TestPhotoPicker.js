/**
 * Created by sophia on 16/2/17.
 */
'use strict';

var React = require('react');

var {
    StyleSheet,
    View,
    Text,
    TouchableOpacity,
    Dimensions,
    ScrollView,
    PixelRatio
    } = require('react-native');

var wWidth = Dimensions.get('window').width;
var {CommonStyle, MSBase} = require("../theme");
var MSPhotoPicker = require("../MSPhotoPicker");
var ImageEditor = require("../MSImageEditor");

var TestPhotoPicker = React.createClass({
    getInitialState: function () {
        return {
            photoUri: "",
            photoWithEditUri: "",
            croppedUri: ""
        };
    },

    render: function () {
        var self = this, imageEditView = <View/>;
        if ((this.state.photoUri && this.state.photoUri.length > 0)
            || (this.state.photoWithEditUri && this.state.photoWithEditUri.length > 0)) {
            imageEditView = (
                <ImageEditor imageSourceUri={this.state.photoUri || this.state.photoWithEditUri}
                             onChangeImg={(e)=>{self.setState({croppedUri: e.nativeEvent.data.uri})}}
                             width={wWidth}/>
            );
        }
        return (
                <View style={{flex: 1}}>
                    <View style={{marginTop: MSBase.margin_top_mid}}>
                        <MSPhotoPicker title={"选择图片来源"}
                                       takePhotoTxt={'相机拍摄'}
                                       choosePhotoTxt={'图片库'}
                                       onChoosePhoto={(uri)=>{this.setState({photoUri: uri});}}
                                       type={"USER"}
                                       allowsEditing={false}
                                       isMain={false}>
                            <Text style={[{justifyContent: 'center',paddingLeft: 5,height:20, marginTop: 20, backgroundColor:MSBase.bg_green}]}>
                                测试无编辑页面
                            </Text>
                        </MSPhotoPicker>
                        <Text>Uri: {this.state.photoUri}</Text>
                    </View>

                    <View style={{marginTop: MSBase.margin_top_mid}}>
                        <MSPhotoPicker title={"选择图片来源"}
                                       takePhotoTxt={'相机拍摄'}
                                       choosePhotoTxt={'图片库'}
                                       onChoosePhoto={(uri)=>{this.setState({photoWithEditUri: uri});}}
                                       type={"USER"}
                                       allowsEditing={true}
                                       isMain={false}>
                            <Text style={[{justifyContent: 'center',paddingLeft: 5,height:20, marginTop: 20, backgroundColor:MSBase.bg_green}]}>
                                测试有编辑页面
                            </Text>
                        </MSPhotoPicker>
                        <Text>Uri: {this.state.photoWithEditUri}</Text>
                    </View>

                    <View style={{marginTop: MSBase.margin_top_mid}}>
                        <Text style={[{justifyContent: 'center',paddingLeft: 5,height:20, marginTop: 20, backgroundColor:MSBase.bg_green}]}
                              onPress={this._cropImage}>
                            测试图片编辑
                        </Text>
                        <Text>Uri: {this.state.croppedUri}</Text>
                        <View style={{borderColor:"#ccc",borderWidth:1/PixelRatio.get()}}>
                            {imageEditView}
                        </View>
                    </View>
                </View>
        );
    }
});

module.exports = TestPhotoPicker;