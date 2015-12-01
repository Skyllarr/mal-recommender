var $ = require('jquery');

String.prototype.capitalizeFirstLetter = function() {
    return this.charAt(0).toUpperCase() + this.slice(1);
};

module.exports = {

    transitionTo: function (route, params, activeTab) {
        var query = activeTab !== undefined ? $.extend({}, this.keyQuery(), {a: activeTab}) : this.keyQuery();
        this.context.router.transitionTo(route, params, query);
    },

    keyQuery: function () {
        var q = this.context.router.getCurrentQuery();
        return q.key !== undefined ? {key: q.key} : null;
    },

    loadData: function (url, successCallback, failCallback) {
        $.ajax({
            url: '/api/' + url,
            type: 'GET',
            dataType: 'json'
        }).done(function (data) {
            if (successCallback != null && successCallback instanceof Function) {
                successCallback(data)
            }

            this.setState(data);
        }.bind(this)).fail(function (data) {
            if (failCallback != null && failCallback instanceof Function) {
                failCallback(data)
            }
        }.bind(this));
    },

    getGenresString: function (genres){
        var result = "";
        genres.forEach(function (genre, index) {
            var parsed = genre == 'SCI_FI' ? genre.toLowerCase().replace(/_/g, '-') : genre.toLowerCase().replace(/_/g, ' ');
            result = result + parsed.capitalizeFirstLetter() + ((genres.length == index + 1 ) ? "" : ", ");
        });

        return result;
    }
};