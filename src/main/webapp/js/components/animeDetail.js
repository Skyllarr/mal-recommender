var React = require('react');
var Reactbootstrap = require('react-bootstrap');
var Common = require('../common');
var AnimeStore = require('../animeStore');

var Modal = Reactbootstrap.Modal;
var Col = Reactbootstrap.Col;
var Row = Reactbootstrap.Row;
var Image = Reactbootstrap.Image;
var ListGroup = Reactbootstrap.ListGroup;
var ListGroupItem = Reactbootstrap.ListGroupItem;
var ButtonGroup = Reactbootstrap.ButtonGroup;
var Button = Reactbootstrap.Button;


module.exports = React.createClass({

    mixins: [Common, AnimeStore],

    getInitialState: function () {
        return {
            title: "",
            imageLink: "",
            malId: null,
            genreEntries: [],
            episodes: null,
            type: "",
            popularity: null,
            description: "",
            deleted: false,
            seen: false
        }
    },

    componentDidMount: function () {
        this.loadData('anime/findanimebymalid/' + this.props.animeMalId, function (data) {
            if(this.containsAnime(data)){
                data.seen = true;
            }
        }.bind(this));
    },

    addAnime: function () {
        this.saveAnime(this.state);
        this.setState({seen: true});
    },

    deleteAnime: function () {
        this.removeAnime(this.state);
        this.setState({seen: false});
    },

    render: function () {
        var anime = this.state;
        return (
            <Modal
                bsSize="large"
                show={true}
                onHide={this.props.onClose}
            >
                <Modal.Header closeButton={true}>
                    <Modal.Title>{anime.title}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Row>
                        <Col className="col-md-4 container portrait">
                            <Image  src={anime.imageLink} />
                            <ListGroup>
                                <ListGroupItem>
                                    {anime.type}
                                </ListGroupItem>
                                <ListGroupItem>
                                    {  this.getGenresString( anime.genreEntries)  }
                                </ListGroupItem>
                                <ListGroupItem>

                                    { anime.episodes + ' Episode' + (anime.episodes == 1 ?  '' : 's')}
                                </ListGroupItem>
                                <ListGroupItem>

                                    MAL Popularity: #{  anime.popularity }
                                </ListGroupItem>
                                <ListGroupItem>
                                    <a target="_blank" href={"http://myanimelist.net/anime/" + anime.malId} >MAL Link</a>
                                </ListGroupItem>
                                <ListGroupItem bsStyle={anime.deleted ? "danger" : "info"}>
                                    One Slope Recommendations
                                </ListGroupItem>
                                <ListGroupItem bsStyle="info">
                                    Tf-Idf Recommendations
                                </ListGroupItem>
                                <ListGroupItem bsStyle={anime.seen ? "success" : ""}>
                                    {anime.seen ? "SEEN" : "NOT SEEN"}
                                </ListGroupItem>
                            </ListGroup>
                        </Col>
                        <Col className="col-md-8">
                            <div dangerouslySetInnerHTML={{__html: anime.description}} ></div>
                        </Col>
                    </Row>
                    <Row>
                        <Col className="col-md-6">

                        </Col>
                        <Col className="col-md-6">
                            <ButtonGroup className="pull-right">
                                {anime.seen &&
                                    <Button bsStyle="danger" onClick={this.deleteAnime}>REMOVE FROM MY LIST</Button>
                                }
                                {!anime.seen &&
                                    <Button bsStyle="info" onClick={this.addAnime}>ADD TO MY LIST</Button>
                                }
                            </ButtonGroup>
                        </Col>

                    </Row>
                </Modal.Body>
            </Modal>
        )
    }
});
