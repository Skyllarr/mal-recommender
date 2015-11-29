var $ = require('jquery');
var React = require('react');
var Reactbootstrap = require('react-bootstrap');
var Navbar = Reactbootstrap.Navbar;
var Nav = Reactbootstrap.Nav;
var NavItem = Reactbootstrap.NavItem;
var MenuItem = Reactbootstrap.MenuItem;
var DropdownButton = Reactbootstrap.DropdownButton;

var Header = require('../components/header');

module.exports = React.createClass({

    contextTypes: {
        router: React.PropTypes.func
    },

    render: function () {

        return (
            <div>
                <Header/>
            </div>
        );
    }
});
