var Router = require('react-router');
var React = require('react');

var SelectAnime = require('./pages/selectAnime');
var User = require('./pages/user');

var Route = Router.Route;
var DefaultRoute = Router.DefaultRoute;

var routes = (
    <Route>
        <DefaultRoute name="root" handler={User}/>
        <Route name="select-anime" path="select-anime" handler={SelectAnime}/>
    </Route>
);


Router.run(routes, function (Handler) {
    React.render(<Handler/>, document.body);
});
