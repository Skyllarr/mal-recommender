var React = require('react');
var Reactbootstrap = require('react-bootstrap');
var Common = require('../common');
var AnimeStore = require('../animeStore');

var Modal = Reactbootstrap.Modal;
var Col = Reactbootstrap.Col;
var Row = Reactbootstrap.Row;
var ButtonGroup = Reactbootstrap.ButtonGroup;
var Button = Reactbootstrap.Button;
var Input = Reactbootstrap.Input;


module.exports = React.createClass({

    mixins: [Common, AnimeStore],

    getInitialState: function () {
        return {
        }
    },

    componentDidMount: function () {
        this.setState(this.props.genres);
    },

    onClose: function () {
        this.props.onClose(this.state);
    },

    toggleAll: function () {
        var genres = this.state;
        var toggleCount = 0;
        for(var genre in genres) {
            if (genres.hasOwnProperty(genre)) {
                toggleCount += genres[genre] ? -1 : 1
            }
        }

        var toggleValue = toggleCount >= 0;

        for(var genre in genres) {
            if (genres.hasOwnProperty(genre)) {
                 genres[genre] = toggleValue;
            }
        }

        this.setState(genres);
    },


    render: function () {
        var genres = this.state;

        var output = [];
        var buffer = [];
        const cols = 4;


        for(var genre in genres){
            if(genres.hasOwnProperty(genre)) {
                var item = (
                    <Input type="checkbox" label={this.getGenreString(genre)} checked={genres[genre]} onChange={function (genre, event){
                        var res = {};
                        res[genre] = event.target.checked;
                        this.setState(res)
                    }.bind(this, genre)} />
                );
                output.push(item);
            }
        }

        return (
            <Modal bsSize="large" show={true} onHide={this.onClose}>
                <Modal.Header closeButton={true}>
                    <Modal.Title>Show me these genres</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Col className="col-md-12">
                        <ButtonGroup className="pull-right" style={{'margin-bottom': '15px'}}>
                            <Button bsStyle="info" onClick={this.toggleAll}>TOGGLE ALL</Button>
                        </ButtonGroup>
                    </Col>
                    {
                        output.map(function (genre, index) {
                            buffer.push(genre);
                            var show = buffer.length == cols || index == output.length - 1;

                            var result = show ? (
                                    <Row key={index}>
                                        {
                                            buffer.map(function (genre, index) {
                                                return (
                                                    <Col key={index} className="col-md-3">
                                                        {genre}
                                                    </Col>
                                                )
                                            }.bind(this))
                                        }
                                    </Row>
                            ) : null;

                            if(show){
                                buffer = [];
                            }

                            return result;

                        }.bind(this))

                    }
                </Modal.Body>
            </Modal>
        )
    }
});


