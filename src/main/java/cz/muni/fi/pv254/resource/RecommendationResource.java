package cz.muni.fi.pv254.resource;

import cz.muni.fi.pv254.algorithms.Normalizer;
import cz.muni.fi.pv254.algorithms.OneSlope;
import cz.muni.fi.pv254.data.Anime;
import cz.muni.fi.pv254.data.AnimeEntry;
import cz.muni.fi.pv254.data.User;
import cz.muni.fi.pv254.data.dto.AnimeDTO;
import cz.muni.fi.pv254.data.dto.RecommendationDTO;
import cz.muni.fi.pv254.data.enums.AnimeEntryStatus;
import cz.muni.fi.pv254.data.enums.Genre;
import cz.muni.fi.pv254.dataUtils.DataStore;
import cz.muni.fi.pv254.utils.Utils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by suomiy on 11/29/15.
 */
@Path("/recommend")
@ApplicationScoped
public class RecommendationResource {

    @Inject
    DataStore dataStore;

    @Inject
    Normalizer normalizer;

    private static final int minOneSlopeEntries = 7;
    private static final int maxOneSlopeWeirdEntries = 2;
    private static final int outputSize = 10;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RecommendationDTO recommend(List<AnimeEntry> entries) {
        if(entries == null) {
            return  null;
        }

        RecommendationDTO result = new RecommendationDTO();
        List<AnimeDTO> slopeOneList = null;
        List<AnimeDTO> slopeOneWeirdList = null;
        List<AnimeDTO> tfIdfList = null;

        //TODO optimize this!
        List<AnimeDTO> randomList = pickNRandom(dataStore.findAnimesForTextAnalysis().stream().filter(a -> !a.getGenres().contains(Genre.HENTAI))
                .collect(Collectors.toList()), outputSize).stream()
                .map(e -> new AnimeDTO(e, 0D))
                .collect(Collectors.toList());

        List<AnimeEntry> allEntries = entries.stream().filter(e -> e.getMalId() != 0).collect(Collectors.toList());

        allEntries.forEach(e -> {
            e.setNormalizedScore(e.getScore());
            e.setStatus(AnimeEntryStatus.COMPLETED);
        });

        List<AnimeEntry> oneslopeEntries = allEntries.stream()
                .filter(e -> e.getScore() != 0 && !dataStore.findAnimeByMalId(e.getMalId()).isDeleted())
                .collect(Collectors.toList());

        Utils.show("recommending to user with " + allEntries.size());
        if(oneslopeEntries.size() >= minOneSlopeEntries){
            User user = new User();
            user.setAnimeEntries(oneslopeEntries);
            normalizer.normalizeUser(user);

            Map<Anime, Double> map = Utils.sortByValue((new OneSlope(dataStore, false)).recommendToUser(user, minOneSlopeEntries, null));
            slopeOneList = map.entrySet().stream()
                    .limit(outputSize).map(e -> new AnimeDTO(e.getKey(), e.getValue()))
                    .collect(Collectors.toList());

            map = Utils.sortByValue((new OneSlope(dataStore, false)).recommendToUser(user, null, maxOneSlopeWeirdEntries));
            slopeOneWeirdList = map.entrySet().stream()
                    .limit(outputSize).map(e -> new AnimeDTO(e.getKey(), e.getValue()))
                    .collect(Collectors.toList());
        }else{
            String message = "Only " + oneslopeEntries.size() + " scored animes designated for One Slope algorithm are in your list. Minimum of " + minOneSlopeEntries + "  is required.";
            result.setSlopeOneListMessage(message);
            result.setSlopeOneWeirdListMessage(message);
        }

        result.setRandomList(randomList);
        result.setSlopeOneList(slopeOneList);
        result.setSlopeOneWeirdList(slopeOneWeirdList);
        result.setTfIdfList(tfIdfList);
        result.setTfIdfListMessage("TBD");

        return result;
    }


    private static <V> List<V> pickNRandom(List<V> lst, int n) {
        List<V> copy = new ArrayList<>(lst);
        Collections.shuffle(copy);
        return copy.subList(0, n);
    }

}
