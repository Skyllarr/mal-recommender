var React = require('react');
var Reactbootstrap = require('react-bootstrap');


var Common = require('../common');

module.exports = React.createClass({

    contextTypes: {
        router: React.PropTypes.func
    },


    mixins: [Common],

    getInitialState: function () {
        return {
            animes: []
        }
    },

    componentDidMount: function () {
        if(this.state.animes.length == 0){
            this.loadData('anime/loadalltitles', function (data) {

                data.animes.sort(( function (a, b) {return a.title.localeCompare(b.title)}))

            });
        }
    },

    render: function () {
        return (
            <ul className="list-unstyled">

                {
                    this.state.animes.map(function (anime, index) {
                        return (
                            <li key={index}>
                                <a className="pointer" onClick={function (){ this.props.onItemClicked(anime.malId)}.bind(this)} >{anime.title}</a>
                            </li>
                        )
                    }.bind(this))
                }
            </ul>
        );
    }
});
