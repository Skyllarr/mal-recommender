
module.exports = {

    loadAnimes: function() {
        const storageJson = window.localStorage.getItem('animes');
        return storageJson == null ? [] : JSON.parse(storageJson);
    },

    saveAnimes: function(animes) {
        animes.sort(( function (a, b) {return a.title.localeCompare(b.title)}));
        window.localStorage.setItem('animes', JSON.stringify(animes));
    },

    saveAnime: function(anime) {
        var animes = this.loadAnimes();
        animes.push({malId: anime.malId, title: anime.title, deleted: anime.deleted});
        this.saveAnimes(animes);
    },

    removeAnime: function(anime) {
        var animes = this.loadAnimes();
        animes = animes.filter(function(a){ return !(a.malId == anime || a.malId == anime.malId) });
        this.saveAnimes(animes);
    },

    containsAnime: function(anime) {
        if(anime == null && anime.malId == null){
            return false;
        }

        var list = this.loadAnimes();
        for (var i = 0; i < list.length; i++) {
            if (list[i].malId === anime || list[i].malId == anime.malId) {
                return true;
            }
        }
        return false;
    },

    save: function(key, value) {
        window.localStorage.setItem(key, JSON.stringify(value));

    },

    load: function(key) {
        return JSON.parse(window.localStorage.getItem(key));
    }
};