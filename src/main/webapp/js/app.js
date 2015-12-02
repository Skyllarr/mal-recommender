var Router = require('react-router');
var React = require('react');

var Main = require('./pages/main');
var AnimeRecommendations = require('./pages/animeRecommendations');
var ViewAllAnimes = require('./pages/viewAllAnimes');
var ViewMyAnimeList = require('./pages/viewMyAnimeList');


var Route = Router.Route;
var DefaultRoute = Router.DefaultRoute;

var routes = (
    <Route>
        <DefaultRoute name="root" handler={Main}/>
        <Route name="animeRecommendations" path="anime-recommendations" handler={AnimeRecommendations}/>
        <Route name="viewAllAnimes" path="view-all" handler={ViewAllAnimes}/>
        <Route name="viewMyAnimeList" path="my-list" handler={ViewMyAnimeList}/>
    </Route>
);



Router.run(routes, function (Handler) {
    React.render(<Handler/>, document.body);
});
