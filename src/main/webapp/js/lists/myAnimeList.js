var React = require('react');
var Reactbootstrap = require('react-bootstrap');
var ListGroup = Reactbootstrap.ListGroup;
var ListGroupItem = Reactbootstrap.ListGroupItem;

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
