var React = require('react');
var Reactbootstrap = require('react-bootstrap');
var Row = Reactbootstrap.Row;
var Col = Reactbootstrap.Col;

var AnimeStore = require('../animeStore');
var Header = require('../components/header');
var AnimeDetail = require('../components/animeDetail');
var MyAnimeList = require('../lists/myAnimeList');

module.exports = React.createClass({

    mixins: [AnimeStore],

    getInitialState: function () {
        return {
            animes: [],
            showAnimeDetail: null
        }
    },

    componentDidMount: function () {
        this.setState({animes: this.loadAnimes()});
    },

    closeAnimeDetail: function() {
        var malId = this.state.showAnimeDetail;

        if(!this.containsAnime(malId)){
            var animesFiltered = this.state.animes.filter(function(a){ return a.malId != malId  });
            this.setState({animes: animesFiltered});
        }

        this.setState({showAnimeDetail: null});
    },

    animeClicked: function(malId) {
        this.setState({showAnimeDetail: malId})
    },

    render: function () {

        return (
            <div>
                <Header/>
                <Row>
                    <Col className="col-md-2"/>
                    <Col className="col-md-8">
                        <MyAnimeList onItemClicked={this.animeClicked} animes={this.state.animes} />
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
