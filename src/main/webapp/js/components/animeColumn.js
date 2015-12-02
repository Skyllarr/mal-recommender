var React = require('react');
var Reactbootstrap = require('react-bootstrap');
var ListGroupItem = Reactbootstrap.ListGroupItem;
var Col = Reactbootstrap.Col;
var Image = Reactbootstrap.Image;

module.exports = React.createClass({

    render: function () {
        return (
                <Col className="col-md-3 container portrait">
                    <ListGroupItem style={{'margin-bottom': '10px'}}>
                       <b className="color"> {this.props.title.toUpperCase()}</b>
                    </ListGroupItem>
                    {this.props.message != null && this.props.message != "" &&
                        <ListGroupItem bsStyle="warning" style={{'margin-top': '10px'}}>
                            {this.props.message}
                        </ListGroupItem>
                    }

                    {
                        this.props.animes.map(function (anime, index) {
                            return (
                                <div  key={index}>
                                    <Image className="pointer" src={anime.imageLink}
                                           onClick={function (){ this.props.onItemClicked(anime.malId)}.bind(this)}/>
                                    <ListGroupItem bsStyle={anime.deleted ? "" : "info"} style={{'margin-bottom': '10px'}}>
                                        <a className="pointer"
                                           onClick={function (){ this.props.onItemClicked(anime.malId)}.bind(this)}>{anime.title}</a>
                                    </ListGroupItem>
                                </div>
                            );
                        }.bind(this))
                    }
                </Col>
        )
    }
});

/*
















 */