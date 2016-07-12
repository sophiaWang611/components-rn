/**
 * Created by Eugene on 15/11/4.
 *
 * @providesModule MSEvaluate
 */
'use strict';

var React = require('react');

var {CommonStyle} = require('./theme');

var {
    StyleSheet,
    View,
    PixelRatio,
    Image,
    } = require('react-native');

/*评分*/
var Evaluate = React.createClass({
    getInitialState: function () {
        return {}
    },
    render: function () {
        //评分
        var score = this.props.score;
        //设定map的数组长度为5
        var list = [1, 2, 3, 4, 5];
        //遍历的每个节点的key值
        var index = 1;
        //选中的星星
        var checked_list = list.slice(0, score);
        var checked = checked_list.map(()=>
                <Image source={require("./img/icon_green_star.png")} style={{height: 12, width: 13, marginLeft: 5}}
                       key={'star_'+ index++}/>
        );
        //未选中的星星
        var unchecked_list = list.slice(0, 5 - score);
        var unchecked = unchecked_list.map(()=>
                <Image source={require("./img/icon_gray_star.png")} style={{height: 12, width: 13, marginLeft: 5}}
                       key={'star_'+ index++}/>
        );

        return (
            <View style={[CommonStyle.row]}>
                {checked}
                {unchecked}
            </View>
        )
    }
});


module.exports = Evaluate;