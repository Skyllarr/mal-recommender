package cz.muni.fi.pv254.resource;

import cz.muni.fi.pv254.data.entity.DbAnime;
import cz.muni.fi.pv254.data.enums.AnimeType;
import cz.muni.fi.pv254.dataUtils.DataStore;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by suomiy on 11/29/15.
 */
@Path("/anime")
@ApplicationScoped
public class AnimeResource {

    @Inject
    DataStore dataStore;

    @GET
    @Path("/loadall")
    @Produces(MediaType.APPLICATION_JSON)
    public AnimeListResult loadAll() {
        return new AnimeListResult(dataStore.findAllAnimes()
                .stream()
                .map(a -> new AnimeResult(a.getDbAnime()))
                .collect(Collectors.toList()));
    }

    @Getter
    @Setter
    private static class AnimeListResult {
        private List<AnimeResult> animes;

        public AnimeListResult(List<AnimeResult> animes) {
            this.animes = animes;
        }
    }

    @Getter
    @Setter
    private static class AnimeResult {
        private String title;
        private String imageLink;
        private Long malId;
        private String genreEntries;
        private Long episodes;
        private AnimeType type;

        public AnimeResult(DbAnime dbAnime) {
            title = dbAnime.getTitle();
            imageLink = dbAnime.getImageLink();
            malId = dbAnime.getMalId();
            genreEntries = dbAnime.getGenreEntries();
            episodes = dbAnime.getEpisodes();
            type = dbAnime.getType();
        }
    }
}
