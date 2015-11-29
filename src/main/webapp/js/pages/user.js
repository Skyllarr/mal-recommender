var $ = require('jquery');
var React = require('react');
var Reactbootstrap = require('react-bootstrap');
var Navbar = Reactbootstrap.Navbar;

var Common = require('../common');

var Header = require('../components/header');

module.exports = React.createClass({

    contextTypes: {
        router: React.PropTypes.func
    },

    mixins: [Common],

    onClick: function(target) {
        this.transitionTo(target);
    },

    render: function () {

        return (
            <div>
                <Header/>
                <div className="row">
                    <div className="col-md-2"></div>
                    <div className="col-md-10">
                        <label>Hello World</label>
                        <br/>
                        <br/>
                        <button className="btn btn-default flow" type="button" onClick={function(){this.onClick('select-anime')}.bind(this)}>
                            Save
                        </button>
                    </div>
                </div>

            </div>
        );
    }
});
