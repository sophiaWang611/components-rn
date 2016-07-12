/**
 * Created by sophia on 15/11/18.
 */
'use strict';

var React = require('react');
var {
    Dimensions,
    Text,
    View,
    TouchableOpacity,
    PanResponder,
    Animated,
    StyleSheet,
    } = require('react-native');

var deviceWidth = Dimensions.get('window').width;

var MSScrollableTabView = React.createClass({
    getDefaultProps() {
        return {
            tabBarPosition: 'top',
            edgeHitWidth: 30,
            springTension: 50,
            springFriction: 10
        }
    },

    getInitialState() {
        var currentPage = this.props.initialPage || 0;
        return { currentPage: currentPage, scrollValue: new Animated.Value(currentPage) };
    },

    componentWillMount() {
        var release = (e, gestureState) => {
            var relativeGestureDistance = gestureState.dx / deviceWidth,
                lastPageIndex = this.props.children.length - 1,
                vx = gestureState.vx,
                newPage = this.state.currentPage;

            if (relativeGestureDistance < -0.5 || (relativeGestureDistance < 0 && vx <= -0.5)) {
                newPage = newPage + 1;
            } else if (relativeGestureDistance > 0.5 || (relativeGestureDistance > 0 && vx >= 0.5)) {
                newPage = newPage - 1;
            }

            this.props.hasTouch && this.props.hasTouch(false);
            this.goToPage(Math.max(0, Math.min(newPage, this.props.children.length - 1)));
        }

        this._panResponder = PanResponder.create({
            // Claim responder if it's a horizontal pan
            onMoveShouldSetPanResponder: (e, gestureState) => {
                if (Math.abs(gestureState.dx) > Math.abs(gestureState.dy)) {
                    if ((gestureState.moveX <= this.props.edgeHitWidth ||
                        gestureState.moveX >= deviceWidth - this.props.edgeHitWidth) &&
                        this.props.locked !== true) {
                        this.props.hasTouch && this.props.hasTouch(true);
                        return true;
                    }
                }
            },

            // Touch is released, scroll to the one that you're closest to
            onPanResponderRelease: release,
            onPanResponderTerminate: release,

            // Dragging, move the view with the touch
            onPanResponderMove: (e, gestureState) => {
                var dx = gestureState.dx;
                var lastPageIndex = this.props.children.length - 1;

                // This is awkward because when we are scrolling we are offsetting the underlying view
                // to the left (-x)
                var offsetX = dx - (this.state.currentPage * deviceWidth);
                this.state.scrollValue.setValue(-1 * offsetX / deviceWidth);
            },
        });
    },

    goToPage(pageNumber) {
        this.props.onChangeTab && this.props.onChangeTab({
            i: pageNumber, ref: this.props.children[pageNumber]
        });

        this.setState({
            currentPage: pageNumber
        });

        Animated.spring(this.state.scrollValue, {toValue: pageNumber, friction: this.props.springFriction, tension: this.props.springTension}).start();
    },

    renderTabBar(props) {
        if (this.props.renderTabBar === false) {
            return null;
        } else if (this.props.renderTabBar) {
            return React.cloneElement(this.props.renderTabBar(), props);
        } else {
            return <DefaultTabBar {...props} />;
        }
    },

    render() {
        var sceneContainerStyle = {
            width: deviceWidth * this.props.children.length,
            flex: 1,
            flexDirection: 'row'
        };

        var translateX = this.state.scrollValue.interpolate({
            inputRange: [0, 1], outputRange: [0, -deviceWidth]
        });

        var tabBarProps = {
            goToPage: this.goToPage,
            tabs: this.props.children.map((child) => child.props.tabLabel),
            activeTab: this.state.currentPage,
            scrollValue: this.state.scrollValue
        };

        return (
            <View style={{flex: 1}}>
                {this.props.tabBarPosition === 'top' ? this.renderTabBar(tabBarProps) : null}
                <Animated.View style={[sceneContainerStyle,{transform: [{translateX}]}]}
                    {...this._panResponder.panHandlers}>
                    {this.props.children}
                </Animated.View>
                {this.props.tabBarPosition === 'bottom' ? this.renderTabBar(tabBarProps) : null}
            </View>
        );
    }
});

var DefaultTabBar = React.createClass({
    propTypes: {
        goToPage: React.PropTypes.func,
        activeTab: React.PropTypes.number,
        tabs: React.PropTypes.array
    },

    renderTabOption(name, page) {
        var isTabActive = this.props.activeTab === page;

        return (
            <TouchableOpacity style={[styles.tab]} key={name} onPress={() => this.props.goToPage(page)}>
                <View>
                    <Text style={{color: isTabActive ? 'navy' : 'black', fontWeight: isTabActive ? 'bold' : 'normal'}}>{name}</Text>
                </View>
            </TouchableOpacity>
        );
    },

    render() {
        var numberOfTabs = this.props.tabs.length;
        var tabUnderlineStyle = {
            position: 'absolute',
            width: deviceWidth / numberOfTabs,
            height: 4,
            backgroundColor: 'navy',
            bottom: 0,
        };

        var left = this.props.scrollValue.interpolate({
            inputRange: [0, 1], outputRange: [0, deviceWidth / numberOfTabs]
        });

        return (
            <View style={styles.tabs}>
                {this.props.tabs.map((tab, i) => this.renderTabOption(tab, i))}
                <Animated.View style={[tabUnderlineStyle, {left}]} />
            </View>
        );
    },
});

var styles = StyleSheet.create({
    tab: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
        paddingBottom: 10,
    },

    tabs: {
        height: 50,
        flexDirection: 'row',
        justifyContent: 'space-around',
        marginTop: 20,
        borderWidth: 1,
        borderTopWidth: 0,
        borderLeftWidth: 0,
        borderRightWidth: 0,
        borderBottomColor: '#ccc',
    },
});

module.exports = MSScrollableTabView;