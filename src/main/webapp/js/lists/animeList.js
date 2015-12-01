var React = require('react');
var Reactbootstrap = require('react-bootstrap');
var Input = Reactbootstrap.Input;
var Button = Reactbootstrap.Button;


var Common = require('../common');

module.exports = React.createClass({

    contextTypes: {
        router: React.PropTypes.func
    },

    mixins: [Common],

    getInitialState: function () {
        return {
            animes: [],
            oneSlopeOnly: false,
            updateEntropy: true
        }
    },

    componentDidMount: function () {
        if(this.state.animes.length == 0){
            this.loadData('anime/loadalltitles', function (data) {

                data.animes.sort(( function (a, b) {return a.title.localeCompare(b.title)}))

            });
        }
    },

    handleSearchChange() {
        var value = this.refs.input.getValue().escapeRegExp();

        this.state.animes.forEach(function (anime) {
            anime.doNotShow = (value != null && value != '' &&  !(new RegExp(value)).test(anime.title)) ? true : null;
        });

        this.setState({animes: this.state.animes});
    },

    handleSearchOneSlope(event) {
        var value = event.target.checked;

        if(value != this.state.oneSlopeOnly){
            this.setState({oneSlopeOnly: value});
            this.state.animes.forEach(function (anime) {
                anime.doNotShowOneSlope = (value != null && value &&  anime.deleted) ? true : null;
            });
        }

        this.setState({animes: this.state.animes});
    },

    //to not rerender after clicking on an item
    componentWillReceiveProps: function(nextProps) {
        this.setState({
            updateEntropy: nextProps.updateEntropy
        });

    },

    shouldComponentUpdate: function(nextProps) {
        return this.state.updateEntropy == nextProps.updateEntropy;
    },

    render: function () {
        return (
            <div>
                <form>
                    <Input
                        type="text"
                        value={this.state.value}
                        placeholder="Search"
                        ref="input"
                        onChange={this.handleSearchChange}
                        buttonAfter={<Button disabled>One Slope only </Button>}
                        addonAfter={<input type="checkbox"  onChange={this.handleSearchOneSlope}/>}
                       />
                </form>

                <ul className="list-unstyled">

                    {
                        this.state.animes.map(function (anime, index) {
                            return (
                                anime.doNotShow == null && anime.doNotShowOneSlope == null &&
                                <li key={index}>
                                    <a className="pointer" onClick={function (){ this.props.onItemClicked(anime.malId)}.bind(this)} >{anime.title}</a>
                                </li>
                            )
                        }.bind(this))
                    }
                </ul>
            </div>
        );
    }
});
