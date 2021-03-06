/**
 * @providesModule MSOverlay
 * @flow-weak
 */

'use strict';

var React = require('react');
var {
    View,
    PropTypes,
    StyleSheet,
    requireNativeComponent,
    Platform,
    NativeModules
    } = require('react-native');

type Props = {
    isVisible: boolean;
}

var Overlay = React.createClass({
    propTypes: {
        ...View.propTypes,
        /**
         * When this property is set to `true`, the Overlay will appear on
         * `UIWindowLevelStatusBar`, otherwise it will appear below that.
         */
        aboveStatusBar: React.PropTypes.bool,

        /**
         * Determines the visibility of the Overlay. When it is not visible,
         * an empty View is rendered.
         */
        isVisible: React.PropTypes.bool,
    },

    getDefaultProps(): Props {
        return {
            aboveStatusBar: false,
            isVisible: false,
        }
    },

    render() {
        var {
            isVisible,
            } = this.props;

        if (this.props.isVisible) {
            return (
                <MSOverlay isVisible={true} style={styles.container} pointerEvents="none" aboveStatusBar={this.props.aboveStatusBar}>
                    {React.Children.map(this.props.children, React.cloneElement)}
                </MSOverlay>
            );
        } else {
            return <View />;
        }
    }
});

var MSOverlay = requireNativeComponent('MSOverlay', Overlay);

var styles = StyleSheet.create({
    container: {
        position: 'absolute',
        top: 0,
        bottom: 0,
        left: 0,
        right: 0,
        borderWidth: 0,
        backgroundColor: 'transparent',
    },
});

module.exports = Overlay;
