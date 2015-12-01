var React = require('react');
var Reactbootstrap = require('react-bootstrap');
var Row = Reactbootstrap.Row;
var Col = Reactbootstrap.Col;

var Header = require('../components/header');
var AnimeDetail = require('../components/animeDetail');
var AnimeList = require('../lists/animeList');

module.exports = React.createClass({

    getInitialState: function () {
        return {
            showAnimeDetail: null,
            updateEntropy: true
        }
    },

    closeAnimeDetail: function() {
        this.setState({showAnimeDetail: null, updateEntropy: (!this.state.updateEntropy)});
    },


    animeClicked: function(malId) {
        this.setState({showAnimeDetail: malId, updateEntropy: (!this.state.updateEntropy)});
    },
    render: function () {

        return (
            <div>
                <Header/>
                <Row>
                    <Col className="col-md-2"/>
                    <Col className="col-md-8">
                        <AnimeList updateEntropy={this.state.updateEntropy} onItemClicked={this.animeClicked}/>
                    </Col>
                    <Col className="col-md-2"/>
                </Row>
                {this.state.showAnimeDetail != null &&
                <AnimeDetail onClose={this.closeAnimeDetail} animeMalId={this.state.showAnimeDetail}/>
                }
            </div>
        );
    }
});
