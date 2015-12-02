var React = require('react');
var Reactbootstrap = require('react-bootstrap');
var Row = Reactbootstrap.Row;
var Col = Reactbootstrap.Col;
var Table = Reactbootstrap.Table;


var AnimeStore = require('../animeStore');
var Common = require('../common');
var AnimeColumn = require('../components/animeColumn');
var Header = require('../components/header');
var AnimeDetail = require('../components/animeDetail');

module.exports = React.createClass({

    mixins: [AnimeStore, Common],


    getInitialState: function () {
        return {
            slopeOneList: [],
            slopeOneWeirdList: [],
            tfIdfList: [],
            randomList: [],
            showAnimeDetail: null
        }
    },

    componentDidMount: function () {
        this.onUserListChanged()
    },

    onUserListChanged: function(){
        var myList = this.loadAnimes();
        this.postData('recommend', myList, function (data) {
            [data.slopeOneList, data.slopeOneWeirdList, data.tfIdfList, data.randomList].forEach(function (list) {
                if(list != null){
                    list.sort(function (a, b) {
                        return b.recommendationValue - a.recommendationValue
                    });
                }
            });
        });
    },

    closeAnimeDetail: function() {
        this.setState({showAnimeDetail: null});
    },

    animeClicked: function(malId) {
        this.setState({showAnimeDetail: malId})
    },

    render: function () {
        return (
            <div>
                <Header navId={1}/>
                <Row>
                    <Col className="col-md-2"/>
                    <Col className="col-md-8">
                        <Table>
                            <AnimeColumn title='Slope One' onUserListChanged={this.onUserListChanged} message={this.state.slopeOneListMessage} onItemClicked={this.animeClicked} animes={this.state.slopeOneList} />
                            <AnimeColumn title='Weird' onUserListChanged={this.onUserListChanged} message={this.state.slopeOneWeirdListMessage} onItemClicked={this.animeClicked} animes={this.state.slopeOneWeirdList}/>
                            <AnimeColumn title='Random' onUserListChanged={this.onUserListChanged} message={this.state.randomListMessage} onItemClicked={this.animeClicked} animes={this.state.randomList}/>
                            <AnimeColumn title='Tf-Idf'  onUserListChanged={this.onUserListChanged} message={this.state.tfIdfListMessage} onItemClicked={this.animeClicked} animes={this.state.tfIdfList}/>
                        </Table>
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