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
var Input = Reactbootstrap.Input;
var Popover = Reactbootstrap.Popover;
var OverlayTrigger = Reactbootstrap.OverlayTrigger;


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
            seen: false,
            setScore: false,
            score: null
        }
    },

    componentDidMount: function () {
        this.loadData('anime/findanimebymalid/' + this.props.animeMalId, function (data) {
            var anime = this.findAnime(this.props.animeMalId);
            if(anime != null){
                data.seen = true;
                data.score = anime.score;
            }
        }.bind(this));
    },

    animeSetScore: function() {
        this.setState({setScore: true})
    },

    addAnime: function () {
        var score = this.refs.input.getValue();
        var state = this.state;
        state.score = score;
        this.saveAnime(state);
        this.setState({setScore: false, score: score, seen: true});
    },

    deleteAnime: function () {
        this.removeAnime(this.state);
        this.setState({seen: false, score: null});
    },


    render: function () {
        var anime = this.state;

        var form =  <form>
            <Input buttonBefore={<Button disabled >Set score</Button>} ref="input" type="select" placeholder="Set Score from 1 to 10"  buttonAfter={<Button bsStyle="info" onClick={this.addAnime}>OK</Button>}>
                <option value=""></option>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
                <option value="6">6</option>
                <option value="7">7</option>
                <option value="8">8</option>
                <option value="9">9</option>
                <option value="10">10</option>
            </Input>
        </form>;

        return (
            <Modal bsSize="large" show={true} onHide={this.props.onClose}>
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
                                    {anime.seen ?
                                        (anime.score != null ?
                                            <div>Your Score: {anime.score}
                                                <div className="pull-right">
                                                    SEEN
                                                </div>
                                            </div>  : "SEEN" )
                                        : "NOT SEEN"}
                                </ListGroupItem>
                            </ListGroup>
                        </Col>
                        <Col className="col-md-8">
                            <div dangerouslySetInnerHTML={{__html: anime.description}} ></div>
                        </Col>
                    </Row>
                    <Row>
                        <Col className="col-md-8">

                        </Col>
                        <Col className="col-md-4">
                            {!this.state.setScore &&
                            <ButtonGroup className="pull-right">
                                {anime.seen &&
                                <Button bsStyle="danger" onClick={this.deleteAnime}>REMOVE FROM MY LIST</Button>
                                }
                                {!anime.seen && !this.state.setScore &&
                                <Button bsStyle="info" onClick={this.animeSetScore}>ADD TO MY LIST</Button>
                                }
                            </ButtonGroup>
                            }
                            {!anime.deleted && this.state.setScore &&
                            <OverlayTrigger trigger="hover" placement="top"
                                            overlay={<Popover  id="note about score" bsStyle="default" placement="top" title="Score is necessary for One Slope algorithm."/>}>
                                {form}
                            </OverlayTrigger>
                            }
                            {(this.state.setScore && anime.deleted) &&
                            {form}
                            }
                        </Col>

                    </Row>
                </Modal.Body>
            </Modal>
        )
    }
});


