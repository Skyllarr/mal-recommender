
module.exports = {

    loadGenres: function() {
        const storageJson = window.localStorage.getItem('genres');
        return storageJson == null ? this.getDefaultGenresSettings() : JSON.parse(storageJson);
    },

    saveGenres: function(genres) {
        window.localStorage.setItem('genres', JSON.stringify(genres));
    },

    getDefaultGenresSettings: function(){
        return {
            ACTION: true,
            ADVENTURE: true,
            CARS: true,
            COMEDY: true,
            DEMENTIA: true,
            DEMONS: true,
            DRAMA: true,
            ECCHI: true,
            FANTASY: true,
            GAME: true,
            HAREM: true,
            HENTAI: false,
            HISTORICAL: true,
            HORROR: true,
            JOSEI: true,
            KIDS: true,
            MAGIC: true,
            MARTIAL_ARTS: true,
            MECHA: true,
            MILITARY: true,
            MUSIC: true,
            MYSTERY: true,
            PARODY: true,
            POLICE: true,
            PSYCHOLOGICAL: true,
            ROMANCE: true,
            SAMURAI: true,
            SCHOOL: true,
            SCI_FI: true,
            SEINEN: true,
            SHOUJO: true,
            SHOUJO_AI: true,
            SHOUNEN: true,
            SHOUNEN_AI: true,
            SLICE_OF_LIFE: true,
            SPACE: true,
            SPORTS: true,
            SUPER_POWER: true,
            SUPERNATURAL: true,
            THRILLER: true,
            VAMPIRE: true,
            YAOI: true,
            YURI: true
        }

    }
};