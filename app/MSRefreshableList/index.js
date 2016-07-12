/**
 * @providesModule MSRefreshableListView
 */
var React = require('react');

var {
    PropTypes,
    ListView,
    Platform
    } = require('react-native');

var isPromise = require('is-promise');
var {Delay} = require('../utils');
var ControlledRefreshableListView = require('./ControlledRefreshableListView');

const LISTVIEW_REF = 'listview';

var RefreshableListView = React.createClass({
    propTypes: {
        loadData: React.PropTypes.func.isRequired,
        minDisplayTime: React.PropTypes.number,
        minBetweenTime: React.PropTypes.number,
        // props passed to child
        refreshPrompt: React.PropTypes.oneOfType([React.PropTypes.string, React.PropTypes.element]),
        refreshDescription: React.PropTypes.oneOfType([React.PropTypes.string, React.PropTypes.element]),
        minPulldownDistance: React.PropTypes.number,
    },
    getDefaultProps() {
        return {
            minDisplayTime: 300,
            minBetweenTime: 300,
            minPulldownDistance: 40
        }
    },
    getInitialState() {
        return {
            isRefreshing: false
        }
    },
    handleRefresh() {
        //console.log('handleRefresh');
        if (this.willRefresh) return;

        this.willRefresh = true;

        var loadingDataPromise = new Promise((resolve) => {
            var loadDataReturnValue = this.props.loadData(resolve);

            if (isPromise(loadDataReturnValue)) {
                loadingDataPromise = loadDataReturnValue;
            }

            Promise.all([
                loadingDataPromise,
                new Promise((resolve) => this.setState({isRefreshing: true}, resolve)),
                Delay(this.props.minDisplayTime)
            ])
                .then(() => Delay(this.props.minBetweenTime))
                .then(() => {
                    this.willRefresh = false;
                    this.setState({isRefreshing: false})
                })
        })
    },
    getScrollResponder() {
        return this.refs[LISTVIEW_REF].getScrollResponder()
    },
    setNativeProps(props) {
        this.refs[LISTVIEW_REF].setNativeProps(props)
    },
    render() {
        var isRefreshing = this.props.isRefreshing || this.state.isRefreshing;
        return (
            <ControlledRefreshableListView
                {...this.props}
                ref={LISTVIEW_REF}
                onRefresh={this.handleRefresh}
                isRefreshing={isRefreshing}
            />
        )
    },
});

RefreshableListView.DataSource = ListView.DataSource;

module.exports = RefreshableListView;
