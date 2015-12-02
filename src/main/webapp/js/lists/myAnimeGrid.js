var React = require('react');
var Reactbootstrap = require('react-bootstrap');
var Row = Reactbootstrap.Row;
var Col = Reactbootstrap.Col;
var Table = Reactbootstrap.Table;
var Image = Reactbootstrap.Image;
var ListGroupItem = Reactbootstrap.ListGroupItem;

module.exports = React.createClass({

    render: function () {

        var buffer = [];
        return (
            <Table>
                {
                    this.props.animes.map(function (anime, index) {
                        buffer.push(anime);
                        var show = buffer.length == 6 || index == this.props.animes.length - 1;

                        var result = show ? (
                            <div key={index}>
                                <Row>
                                    {
                                        buffer.map(function (anime, index) {
                                            return (
                                                <Col className="col-md-2 container portrait">
                                                    <Image  className="pointer" src={anime.imageLink} onClick={function (){ this.props.onItemClicked(anime.malId)}.bind(this)}/>
                                                </Col>
                                            )
                                        }.bind(this))
                                    }
                                </Row>
                                <Row>
                                    {
                                        buffer.map(function (anime, index) {
                                            return (
                                                <Col className="col-md-2">
                                                    <ListGroupItem style={{'margin-bottom': '10px'}}>
                                                        <a className="pointer" onClick={function (){ this.props.onItemClicked(anime.malId)}.bind(this)} >{anime.title}</a>
                                                    </ListGroupItem>
                                                </Col>
                                            )
                                        }.bind(this))
                                    }
                                </Row>
                            </div>
                        ) : null;

                        if(show){
                            buffer = [];
                        }

                        return result;

                    }.bind(this))

                }
            </Table>
        );
    }
});