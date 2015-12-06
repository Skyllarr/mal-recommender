var React = require('react');
var Reactbootstrap = require('react-bootstrap');
var Row = Reactbootstrap.Row;
var Col = Reactbootstrap.Col;
var Table = Reactbootstrap.Table;
var ButtonGroup = Reactbootstrap.ButtonGroup;
var Button = Reactbootstrap.Button;

var AnimeStore = require('../animeStore');
var GenreStore = require('../genreStore');
var Common = require('../common');
var AnimeColumn = require('../components/animeColumn');
var Header = require('../components/header');
var AnimeDetail = require('../components/animeDetail');
var GenreSettings = require('../components/genresSettings');

module.exports = React.createClass({

    mixins: [AnimeStore, GenreStore, Common],


    getInitialState: function () {
        return {
            slopeOneList: [],
            slopeOneWeirdList: [],
            tfIdfList: [],
            randomList: [],
            genres: this.loadGenres(),
            showGenreList: false,
            showAnimeDetail: null

        }
    },

    componentDidMount: function () {
        this.onUserListChanged()
    },

    onUserListChanged: function(){
        var myList = this.loadAnimes();
        if(myList != null || myList.length != 0) {
            this.postData('recommend', {entries: myList, genres: this.state.genres}, function (data) {
                if (data != null) {
                    [data.slopeOneList, data.slopeOneWeirdList, data.tfIdfList].forEach( function(list) {
                        this.sortListByValue(list, 'recommendationValue')
                    }.bind(this));
                }
            }.bind(this));
        }
    },

    closeAnimeDetail: function() {
        this.setState({showAnimeDetail: null});
    },

    animeClicked: function(malId) {
        this.setState({showAnimeDetail: malId})
    },

    setGenres: function (genres) {
        this.setState({genres: genres, showGenreList: false});
        this.saveGenres(genres)
    },

    showGenres: function () {
        this.setState({showGenreList: true});
    },

    render: function () {
        return (
            <div>
                <Header navId={1}/>
                <Row>
                    <Col className="col-md-2"/>
                    <Col className="col-md-8">
                        <Table>
                            <thead>
                                <Col className="col-md-12">
                                    <ButtonGroup className="pull-right" style={{'margin-bottom': '15px'}}>
                                        <Button bsStyle="info" onClick={this.showGenres}>FILTER GENRES</Button>
                                    </ButtonGroup>
                                </Col>
                            </thead>
                            <AnimeColumn title='Slope One' onUserListChanged={this.onUserListChanged} message={this.state.slopeOneListMessage} onItemClicked={this.animeClicked} animes={this.state.slopeOneList} />
                            <AnimeColumn title='Weird' onUserListChanged={this.onUserListChanged} message={this.state.slopeOneWeirdListMessage} onItemClicked={this.animeClicked} animes={this.state.slopeOneWeirdList}/>
                            <AnimeColumn title='Tf-Idf'  onUserListChanged={this.onUserListChanged} message={this.state.tfIdfListMessage} onItemClicked={this.animeClicked} animes={this.state.tfIdfList}/>
                            <AnimeColumn title='Random' onUserListChanged={this.onUserListChanged} message={this.state.randomListMessage} onItemClicked={this.animeClicked} animes={this.state.randomList}/>
                        </Table>
                    </Col>
                    <Col className="col-md-2"/>
                </Row>
                {this.state.showAnimeDetail != null &&
                <AnimeDetail onClose={this.closeAnimeDetail} animeMalId={this.state.showAnimeDetail}/>
                }
                {this.state.showGenreList &&
                <GenreSettings onClose={this.setGenres} genres={this.state.genres}/>
                }
            </div>
        );
    }
});