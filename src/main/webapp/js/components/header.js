var React = require('react');
var Reactbootstrap = require('react-bootstrap');
var Panel = Reactbootstrap.Panel;

var Nav = Reactbootstrap.Nav;
var NavItem = Reactbootstrap.NavItem;
var Col = Reactbootstrap.Col;
var Row = Reactbootstrap.Row;



var Common = require('../common');

module.exports = React.createClass({

    contextTypes: {
        router: React.PropTypes.func
    },

    mixins: [Common],

    onClick: function(route) {
        this.transitionTo(route);
    },

    render: function () {
        return (
            <Row>
                <Col className="col-md-2"/>
                <Col className="col-md-8">
                    <Panel>
                        <h2 >MAL Recommender</h2>
                        <Nav bsStyle="pills" >
                            <NavItem  onClick={function (){this.onClick('selectAnime')}.bind(this)}>Select Animes</NavItem>
                            <NavItem  onClick={function (){this.onClick('viewAllAnimes')}.bind(this)}>All Animes</NavItem>
                            <NavItem  onClick={function (){this.onClick('viewMyAnimeList')}.bind(this)}>My List</NavItem>
                        </Nav>
                    </Panel>
                </Col>
                <Col className="col-md-2"/>
            </Row>
        );
    }
});
