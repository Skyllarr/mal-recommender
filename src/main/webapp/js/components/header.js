var $ = require('jquery');
var React = require('react');
var Reactbootstrap = require('react-bootstrap');
var Navbar = Reactbootstrap.Navbar;
var Nav = Reactbootstrap.Nav;
var NavItem = Reactbootstrap.NavItem;
var MenuItem = Reactbootstrap.MenuItem;
var DropdownButton = Reactbootstrap.DropdownButton;


module.exports = React.createClass({

    contextTypes: {
        router: React.PropTypes.func
    },

    render: function () {
        return (
            <div>
                <Navbar>
                    <div className="navbar-header large">
                        <div className="navbar-brand">
                            <a  href="#">Mal Recommender</a>
                        </div>
                    </div>
                    <Nav>
                        <NavItem href="#">Link</NavItem>
                        <NavItem href="#">Link</NavItem>
                        <NavItem>
                        </NavItem>
                    </Nav>
                </Navbar>
            </div>
        );
    }
});
