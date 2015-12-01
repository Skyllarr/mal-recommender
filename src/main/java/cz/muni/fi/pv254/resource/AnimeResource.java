package cz.muni.fi.pv254.resource;

import cz.muni.fi.pv254.data.Anime;
import cz.muni.fi.pv254.data.enums.AnimeType;
import cz.muni.fi.pv254.data.enums.Genre;
import cz.muni.fi.pv254.dataUtils.DataStore;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.ApplicationScoped;
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
        return new AnimeListResult(dataStore.findAnimesForTextAnalysis()
                .stream()
                .map(AnimeResult::new)
                .collect(Collectors.toList()));
    }

    @GET
    @Path("/loadalltitles")
    @Produces(MediaType.APPLICATION_JSON)
    public AnimeLightListResult loadAllTitles() {
        return new AnimeLightListResult(dataStore.findAnimesForTextAnalysis()
                .stream()
                .map(AnimeLightResult::new)
                .collect(Collectors.toList()));
    }

    @GET
    @Path("/findanimebymalid/{malId}")
    @Produces(MediaType.APPLICATION_JSON)
    public AnimeResult findAnimeByMalId(@PathParam("malId") Long malId) {
        Anime anime = dataStore.findAnimeByMalId(malId);
        return anime == null ? null : new AnimeResult(anime);
    }

    @GET
    @Path("/loadalltitles/{count}")
    @Produces(MediaType.APPLICATION_JSON)
    public AnimeLightListResult loadAllTitles(@PathParam("count") Long count) {
        List<Anime> animes = dataStore.findAnimesForTextAnalysis();
        long maxAnimes = count == null ? animes.size() : count;

        return new AnimeLightListResult(animes.stream()
                .map(AnimeLightResult::new).limit(maxAnimes)
                .collect(Collectors.toList()));
    }


    @GET
    @Path("/loadnotdeleted")
    @Produces(MediaType.APPLICATION_JSON)
    public AnimeListResult loadNotDeleted() {
        return new AnimeListResult(dataStore.findAllAnimes()
                .stream()
                .map(AnimeResult::new)
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
    private static class AnimeLightListResult {
        private List<AnimeLightResult> animes;

        public AnimeLightListResult(List<AnimeLightResult> animes) {
            this.animes = animes;
        }
    }

    @Getter
    @Setter
    private static class AnimeResult {
        private String title;
        private String imageLink;
        private Long malId;
        private List<Genre> genreEntries;
        private Long episodes;
        private AnimeType type;
        private Long popularity;
        private String description;
        private boolean deleted;

        public AnimeResult(Anime anime) {
            title = anime.getTitle();
            imageLink = anime.getImageLink();
            malId = anime.getMalId();
            genreEntries = anime.getGenres();
            episodes = anime.getEpisodes();
            type = anime.getType();
            popularity = anime.getPopularity();
            description = anime.getDescription();
            deleted = anime.isDeleted();
        }
    }

    @Getter
    @Setter
    private static class AnimeLightResult {
        private String title;
        private Long malId;
        private boolean deleted;

        public AnimeLightResult(Anime anime) {
            title = anime.getTitle();
            malId = anime.getMalId();
            deleted = anime.isDeleted();
        }
    }
}
