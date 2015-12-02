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
        var titleObj = {
            title1: 'ANIME RECOMMENDATIONS',
            title2: 'MY LIST',
            title3: 'ALL ANIMES',
            title4: 'ABOUT'
        };

        titleObj['title' + this.props.navId] = <b> {titleObj['title' + this.props.navId]}</b>;

        return (
            <Row>
                <Col className="col-md-2"/>
                <Col className="col-md-8">
                    <Panel>
                        <h2><b  className="color">MAL RECOMMENDER</b></h2>
                        <Nav bsStyle="pills" >
                            <NavItem  onClick={function (){this.onClick('animeRecommendations', 1)}.bind(this)}>{titleObj.title1}</NavItem>
                            <NavItem  onClick={function (){this.onClick('viewMyAnimeList', 2)}.bind(this)}>{titleObj.title2}</NavItem>
                            <NavItem  onClick={function (){this.onClick('viewAllAnimes', 3)}.bind(this)}>{titleObj.title3}</NavItem>

                            <NavItem className="pull-right disabled" onClick={function (){return false}.bind(this)}>{titleObj.title4}</NavItem>
                        </Nav>
                    </Panel>
                </Col>
                <Col className="col-md-2"/>
            </Row>
        );
    }
});
