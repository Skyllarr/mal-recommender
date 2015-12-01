var $ = require('jquery');
var React = require('react');
var Reactbootstrap = require('react-bootstrap');
var ListGroup = Reactbootstrap.ListGroup;
var ListGroupItem = Reactbootstrap.ListGroupItem;
var Panel = Reactbootstrap.Panel;
var Row = Reactbootstrap.Row;
var Col = Reactbootstrap.Col;

var Common = require('../common');
var Header = require('../components/header');
var AnimeDetail = require('../components/animeDetail');

module.exports = React.createClass({

    render: function () {
        return (

            <ListGroup className="list-unstyled">
                {
                    this.props.animes.map(function (anime, index) {
                        return (
                            <ListGroupItem key={index}>
                                <a className="pointer" onClick={function (){ this.props.onItemClicked(anime.malId)}.bind(this)} >{anime.title}</a>
                            </ListGroupItem>
                        )
                    }.bind(this))
                }
            </ListGroup>

        );
    }
});
