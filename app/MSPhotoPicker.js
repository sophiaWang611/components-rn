/**
 * Created by sophia on 15/12/15.
 *
 * @providesModule MSPhotoPicker
 */
'use strict';

var React = require('react');

var {
    StyleSheet,
    View,
    ActionSheetIOS,
    Text,
    TouchableOpacity,
    TouchableWithoutFeedback,
    Platform,
    NativeModules
    } = require('react-native');

var MSNativePicker = null;
if (Platform.OS === 'android') {
    MSNativePicker = NativeModules.MSPhotoPicker;
} else {
    MSNativePicker = NativeModules.UIImagePickerManager;
}

var MSPhotoPicker = React.createClass({
    propTypes: {
        //show on action sheet
        title: React.PropTypes.string,

        //title of sheet for take phto
        takePhotoTxt: React.PropTypes.string,

        //title of sheet for choose photo
        choosePhotoTxt: React.PropTypes.string,

        //callback function for choose photo
        onChoosePhoto: React.PropTypes.func,

        //title of sheet for cancel text
        cancelTxt: React.PropTypes.string,

        //callback function for cancel sheet
        onCancel: React.PropTypes.func,

        /**
         * custom other options, will be present before "Cancel" button.
         * item in array should be like: {actionTitle: actionCallBack}
         */
        customOptions: React.PropTypes.array,

        allowsEditing: React.PropTypes.bool
    },

    getInitialState: function() {
        return this._setStateByProps(this.props);
    },

    componentWillReceiveProps: function(props) {
        this.setState(this._setStateByProps(props));
    },

    _setStateByProps: function(props) {
        var optTitles = [], optAction = [];

        optTitles.push(props.takePhotoTxt || "拍照");
        optAction.push(this._renderCamera);

        optTitles.push(props.choosePhotoTxt || "相册选取");
        optAction.push(this._renderPhoto);

        if (props.customOptions && props.customOptions.length > 0) {
            try {
                props.customOptions.map((object, k)=>{
                    var title = Object.getOwnPropertyNames(object)[0];
                    optTitles.push(title);
                    optAction.push(object[title]);
                });
            } catch(err) {
                throw new Error('Invalid parameter "customOptions": ' + err);
            }
        }

        optTitles.push(props.cancelTxt || "取消");
        optAction.push(props.onCancel);

        if (optTitles.length !== optAction.length) {
            throw new Error('Invalid parameter "customOptions": ' + err);
        }

        return {
            title: props.title || "",
            optTitles: optTitles,
            optAction: optAction,
            cancelIndex: (optTitles.length - 1),
            onChoosePhoto: props.onChoosePhoto,
            onCancel: props.onCancel
        };
    },

    render: function () {
        var triggerButton = this.props.children;
        if (!triggerButton) {
            triggerButton = (<Text>点击上传图片</Text>);
        }
        return (
            <TouchableWithoutFeedback onPress={this._renderSheet}>
                <View>{triggerButton}</View>
            </TouchableWithoutFeedback>
        );
    },

    _renderSheet: function() {
        if (Platform.OS === 'android') {
            this._renderForAndroid()
        } else {
            this._renderForIOS();
        }
    },

    _renderForAndroid: function() {
        MSNativePicker.showPhotoPick(this.props.allowsEditing || false, (data)=>{
            this._onChoose(false, data);
        });
    },

    _renderForIOS: function() {
        ActionSheetIOS.showActionSheetWithOptions({
            "title": this.state.title,
            "options": this.state.optTitles,
            "cancelButtonIndex": this.state.cancelIndex
        }, (optionIndex)=>{
            if (this.state.optAction[optionIndex]) {
                var actionName = this.state.optTitles[optionIndex];
                this.state.optAction[optionIndex](actionName);
            }
        });
    },

    _renderCamera: function() {
        MSNativePicker.launchCamera({
            allowsEditing: this.props.allowsEditing || false,
            quality: 1
        }, this._onChoose);
    },

    _renderPhoto: function() {
        MSNativePicker.launchImageLibrary({
            allowsEditing: this.props.allowsEditing || false,
            quality: 1
        }, this._onChoose);
    },

    _onChoose: function(isCancel, res) {
        if (isCancel) {
            this.state.onCancel && this.state.onCancel();
        } else {
            this.state.onChoosePhoto && this.state.onChoosePhoto(res.uri || "");
        }
    }
});

var styles = StyleSheet.create({});

module.exports = MSPhotoPicker;