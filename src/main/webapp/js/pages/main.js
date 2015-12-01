var React = require('react');
var Reactbootstrap = require('react-bootstrap');
var Col = Reactbootstrap.Col;
var Row = Reactbootstrap.Row;

var Common = require('../common');
var Header = require('../components/header');

module.exports = React.createClass({

    contextTypes: {
        router: React.PropTypes.func
    },

    mixins: [Common],

    render: function () {

        return (
            <div>
                <Header/>
                <Row>
                    <Col className="col-md-2"/>
                    <Col className="col-md-8">
                        <label>Hello World</label>
                    </Col>
                    <Col className="col-md-2"/>
                </Row>
            </div>
        );
    }
});
