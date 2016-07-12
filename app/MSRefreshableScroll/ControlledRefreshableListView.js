var React = require('react');
var {
    PropTypes,
    StyleSheet,
    View,
    Platform,
    ListView,
    Text,
    Dimensions,
    PixelRatio,
    ScrollView,
    ActivityIndicatorIOS,
    Image,
    PullToRefreshViewAndroid
    } = require('react-native');

const SCROLL_EVENT_THROTTLE = 32;
const MIN_PULLDOWN_DISTANCE = 40;
const SCROLL_REF = 'scrollview';
const width = Dimensions.get("window").width;
const statusStrDown = "下拉可以刷新";
const statusStrUp = "松开立即刷新";
const statusStrLoad = "正在刷新数据中";

/*
 * state transitions:
 *   {isRefreshing: false}
 *   v - show loading spinner
 *   {isRefreshing: true, waitingForRelease: true}
 *   v - reset scroll position, offset scroll top
 *   {isRefreshing: true, waitingForRelease: false}
 *   v - hide loading spinner
 *   {isRefreshing: false}
 */

var ControlledRefreshableListView = React.createClass({
    propTypes: {
        onRefresh: PropTypes.func.isRequired,
        isRefreshing: PropTypes.bool.isRequired,
        refreshPrompt: PropTypes.oneOfType([PropTypes.string, PropTypes.element]),
        refreshDescription: PropTypes.oneOfType([PropTypes.string, PropTypes.element]),
        minPulldownDistance: PropTypes.number,
        ignoreInertialScroll: PropTypes.bool,
        scrollEventThrottle: PropTypes.number,
        onScroll: PropTypes.func,
        onResponderGrant: PropTypes.func,
        onResponderRelease: PropTypes.func,
    },
    getDefaultProps() {
        this._lastScrollDispatchTime = 0;
        return {
            minPulldownDistance: MIN_PULLDOWN_DISTANCE,
            scrollEventThrottle: SCROLL_EVENT_THROTTLE,
            ignoreInertialScroll: true,
        }
    },
    getInitialState() {
        return {
            waitingForRelease: false,
            msg: statusStrDown,
            rotate: '0deg'
        }
    },
    componentWillReceiveProps(nextProps) {
        if (!this.props.isRefreshing && nextProps.isRefreshing && this.isTouching) {

            this.waitingForRelease = true
            this.setState({waitingForRelease: true})
        }
    },
    componentWillUpdate(nextProps, nextState) {
        if (Platform.OS === 'ios') {
            if (
                this.isReleaseUpdate(this.props, this.state, nextProps, nextState)
            ) {
                this.getScrollResponder().scrollWithoutAnimationTo(
                    -(this.lastContentInsetTop + MIN_PULLDOWN_DISTANCE),
                    this.lastContentOffsetX
                )
            }
        }
    },
    componentDidUpdate(prevProps, prevState) {
        if (Platform.OS === 'ios') {
            if (
                this.isReleaseUpdate(prevProps, prevState, this.props, this.state)
            ) {
                this.getScrollResponder().scrollWithoutAnimationTo(
                    -(this.lastContentInsetTop),
                    this.lastContentOffsetX
                )
            }
        }
    },
    handleScroll(e) {
        //console.log('handleScroll');
        let now = (new Date()).getTime(),
            scrollEventThrottle = this.props.scrollEventThrottle || 0;

        if (scrollEventThrottle > 0 && this._lastScrollDispatchTime > 0
                && scrollEventThrottle > (now - this._lastScrollDispatchTime)) {
            return;
        }
        this._lastScrollDispatchTime = now;
        var scrollY = e.nativeEvent.contentInset.top + e.nativeEvent.contentOffset.y;
        this.lastContentInsetTop = e.nativeEvent.contentInset.top;
        this.lastContentOffsetX = e.nativeEvent.contentOffset.x;
        //console.log(scrollY);
        if (!this.isTouching) {
            //console.log('!isTouching');
            if (scrollY < -this.props.minPulldownDistance) {
                if (!this.props.isRefreshing) {
                    if (this.props.onRefresh) {
                        this.props.onRefresh()
                    }
                }
            }
        }
        if (this.isTouching) {
            //console.log('isTouching');
            if (scrollY < -this.props.minPulldownDistance) {
                if(this.state.msg !== statusStrUp){
                    this.setState({msg: statusStrUp, rotate: '180deg'});
                }
            } else {
                if(this.state.msg !== statusStrDown){
                    this.setState({msg: statusStrDown, rotate: '0deg'});
                }
            }
        }

        this.props.onScroll && this.props.onScroll(e)
    },
    handleResponderGrant() {
        this.isTouching = true;
        if (this.props.onResponderGrant) {
            this.props.onResponderGrant.apply(null, arguments)
        }
    },
    handleResponderRelease(e) {
        this.isTouching = false;
        if (this.isWaitingForRelease()) {
            this.waitingForRelease = false;
            this.setState({waitingForRelease: false})
        }
        if (this.props.onResponderRelease) {
            this.props.onResponderRelease.apply(null, arguments)
        }
    },
    getContentContainerStyle() {
        if (!this.props.isRefreshing || this.isWaitingForRelease()) return null;

        return {marginTop: MIN_PULLDOWN_DISTANCE}
    },
    getScrollResponder() {
        return this.refs[SCROLL_REF].getScrollResponder()
    },
    setNativeProps(props) {
        this.refs[SCROLL_REF].setNativeProps(props)
    },
    isWaitingForRelease() {
        return this.waitingForRelease || this.props.waitingForRelease
    },
    isReleaseUpdate(oldProps, oldState, newProps, newState) {
        return (
        (!oldProps.isRefreshing && newProps.isRefreshing && !this.waitingForRelease) ||
        (oldProps.isRefreshing && oldState.waitingForRelease && !newState.waitingForRelease)
        )
    },
    render: function () {
        if (Platform.OS === 'android') {
            return (
                <PullToRefreshViewAndroid
                    style={[{flex:1}]}
                    refreshing={this.props.isRefreshing}
                    onRefresh={this.props.onRefresh}
                    colors={["#21af5e"]}
                    progressBackgroundColor={'#fff'}>
                    <ScrollView {...this.props}
                        style={[stylesheet.fillParent]}
                        onScroll={this.handleScroll}
                        onResponderGrant={this.handleResponderGrant}
                        onResponderRelease={this.handleResponderRelease}
                        ref={SCROLL_REF}
                        contentContainerStyle={this.getContentContainerStyle()}>
                        {this.props.children}
                    </ScrollView>
                </PullToRefreshViewAndroid>
            );
        }
        return (
            <View style={[stylesheet.container]}>
                <ScrollView {...this.props}
                            style={[stylesheet.fillParent]}
                            onScroll={this.handleScroll}
                            onResponderGrant={this.handleResponderGrant}
                            onResponderRelease={this.handleResponderRelease}
                            ref={SCROLL_REF}
                            contentContainerStyle={this.getContentContainerStyle()}>
                    {this._getLoading()}
                    {this.props.children}
                </ScrollView>
            </View>
        );
    },

    _getLoading:function() {
        var msg = this.state.msg;
        var loadImg = <Image source={require("../../img/icon_load.png")} style={[stylesheet.imageLabel, {transform: [{rotate: this.state.rotate}]}]}/>;
        if (this.props.isRefreshing) {
            msg = statusStrLoad;
            loadImg = <ActivityIndicatorIOS style={stylesheet.imageLabel}/>;
        }
        return (
            <View style={[stylesheet.fillParent, {flexDirection: "row", marginTop: -30}]}>
                <View style={{justifyContent: 'center', flex: 1, alignItems: "center"}}>
                    {loadImg}
                    <Text>{msg}</Text>
                    <Text></Text>
                </View>
            </View>
        );
    }
});

var stylesheet = StyleSheet.create({
    container: {
        flex: 1,
    },
    fillParent: {
        backgroundColor: 'transparent',
        top: 0,
        left: 0,
        right: 0,
        bottom: 0,
    },
    imageLabel: {
        position: "absolute",
        left: width / 2 - 120,
        width: 11,
        height: 15,
    }
});

ControlledRefreshableListView.DataSource = ListView.DataSource;

module.exports = ControlledRefreshableListView;
