var $ = require('jquery');


module.exports = {

    transitionTo: function (route, params, activeTab) {
        var query = activeTab !== undefined ? $.extend({}, this.keyQuery(), {a: activeTab}) : this.keyQuery();
        this.context.router.transitionTo(route, params, query);
    },

    keyQuery: function () {
        var q = this.context.router.getCurrentQuery();
        return q.key !== undefined ? {key: q.key} : null;
    }

};