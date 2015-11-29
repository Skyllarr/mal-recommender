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

    onClick: function() {
        $.ajax({
            url: "/api/anime/loadall",
            type: 'GET',
            dataType: 'json'
        }).done(function (data) {
            console.log(data);
        }.bind(this)).fail(function (data) {
            console.log("fail");
        }.bind(this));
    },

    render: function () {
        return (
            <div>
                <Header/>
                <div className="row">
                    <div className="col-md-2"></div>
                    <div className="col-md-10">
                        <br/>
                        <br/>
                        <button className="btn btn-default flow" type="button" onClick={this.onClick}>
                            Get Animes
                        </button>
                    </div>
                </div>
            </div>
        );
    }
});
