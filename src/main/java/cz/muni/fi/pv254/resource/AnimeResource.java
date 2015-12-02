package cz.muni.fi.pv254.resource;

import cz.muni.fi.pv254.data.Anime;
import cz.muni.fi.pv254.data.dto.AnimeDTO;
import cz.muni.fi.pv254.data.dto.AnimeLightDTO;
import cz.muni.fi.pv254.data.dto.AnimeLightListDTO;
import cz.muni.fi.pv254.data.dto.AnimeListDTO;
import cz.muni.fi.pv254.dataUtils.DataStore;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
    public AnimeListDTO loadAll() {
        return new AnimeListDTO(dataStore.findAnimesForTextAnalysis()
                .stream()
                .map(AnimeDTO::new)
                .collect(Collectors.toList()));
    }

    @GET
    @Path("/loadalltitles")
    @Produces(MediaType.APPLICATION_JSON)
    public AnimeLightListDTO loadAllTitles() {
        return new AnimeLightListDTO(dataStore.findAnimesForTextAnalysis()
                .stream()
                .map(AnimeLightDTO::new)
                .collect(Collectors.toList()));
    }

    @GET
    @Path("/findanimebymalid/{malId}")
    @Produces(MediaType.APPLICATION_JSON)
    public AnimeDTO findAnimeByMalId(@PathParam("malId") Long malId) {
        Anime anime = dataStore.findAnimeByMalId(malId);
        return anime == null ? null : new AnimeDTO(anime);
    }

    @GET
    @Path("/loadalltitles/{count}")
    @Produces(MediaType.APPLICATION_JSON)
    public AnimeLightListDTO loadAllTitles(@PathParam("count") Long count) {
        List<Anime> animes = dataStore.findAnimesForTextAnalysis();
        long maxAnimes = count == null ? animes.size() : count;

        return new AnimeLightListDTO(animes.stream()
                .map(AnimeLightDTO::new).limit(maxAnimes)
                .collect(Collectors.toList()));
    }


    @GET
    @Path("/loadnotdeleted")
    @Produces(MediaType.APPLICATION_JSON)
    public AnimeListDTO loadNotDeleted() {
        return new AnimeListDTO(dataStore.findAllAnimes()
                .stream()
                .map(AnimeDTO::new)
                .collect(Collectors.toList()));
    }

}
