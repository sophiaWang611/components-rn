/**
 * @providesModule MSImageEditor
 * @flow
 */
'use strict';

var React = require('react');

var {
    NativeModules,
    View,
    PropTypes,
    StyleSheet,
    requireNativeComponent,
    Dimensions,
    Platform
} = require('react-native');

var RNImageEditor = requireNativeComponent('RNImageEditor', null);
const scale = Dimensions.get('window').scale;

var MSImageEditor = React.createClass({

    propTypes: {
        ...View.propTypes,
        imageSourceUri: React.PropTypes.string,
        width: React.PropTypes.number,
        onChangeImg: React.PropTypes.func
    },

    getInitialState: function() {
        return {width: this.props.width};
    },

    componentWillReceiveProps: function(nextProps) {
        this.setState({width: nextProps.width});
    },

    render() {
        var width = this.state.width;
        if (Platform.OS === 'android') {
            width = width * scale;
        }

        return (
            <RNImageEditor imageSourceUri={this.props.imageSourceUri}
                           width={width}
                           height={width}
                           ratio={0.75}
                           onChange={this.props.onChangeImg}/>
        );
    }
});

module.exports = MSImageEditor;
