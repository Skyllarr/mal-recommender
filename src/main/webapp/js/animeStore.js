
module.exports = {

    loadAnimes: function() {
        const storageJson = window.localStorage.getItem('animes');
        return storageJson == null ? [] : JSON.parse(storageJson);
    },

    saveAnimes: function(animes) {
        animes.sort(( function (a, b) {return a.title.localeCompare(b.title)}));
        window.localStorage.setItem('animes', JSON.stringify(animes));
    },

    findAnime: function(anime) {
        if(anime == null && anime.malId == null){
            return null;
        }

        var list = this.loadAnimes();
        for (var i = 0; i < list.length; i++) {
            if (list[i].malId === anime || list[i].malId == anime.malId) {
                return list[i];
            }
        }
        return null
    },

    saveAnime: function(anime) {
        var animes = this.loadAnimes();
        animes.push({malId: anime.malId, title: anime.title, deleted: anime.deleted, imageLink: anime.imageLink, score: anime.score});
        this.saveAnimes(animes);
    },

    removeAnime: function(anime) {
        var animes = this.loadAnimes();
        animes = animes.filter(function(a){ return !(a.malId == anime || a.malId == anime.malId) });
        this.saveAnimes(animes);
    },

    containsAnime: function(anime) {
        return this.findAnime(anime) != null;
    },

    save: function(key, value) {
        window.localStorage.setItem(key, JSON.stringify(value));

    },

    load: function(key) {
        return JSON.parse(window.localStorage.getItem(key));
    }
};