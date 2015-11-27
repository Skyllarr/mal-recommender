package cz.muni.fi.pv254.algorithms;

import cz.muni.fi.pv254.data.Anime;
import cz.muni.fi.pv254.data.AnimeEntry;
import cz.muni.fi.pv254.data.User;
import cz.muni.fi.pv254.dataUtils.DataStore;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by skylar on 23.11.15.
 */
public class OneSlope {
    @Inject
    DataStore dataStore;

    public List<Double> computeOneSlopeValues(List<AnimeEntry> userAnimeEntries) {
       // dataStore.fetchData();
        List<Double> reccomendationValues = new ArrayList<>();
        final Double[] nominator = {1.0};
        final Double[] denominator = {1.0};

        for(AnimeEntry userAnimeEntry : userAnimeEntries) {
            dataStore.findAllAnimes().stream().filter(anime -> !userAnimeEntries.contains(anime)).forEach(anime -> {
                Double scoreUnratedAnime = 0.0;
                Double scoreRatedAnime =0.0;
                int numberOfUsers = 0;
                Double difference = 0.0;

                for(User user : dataStore.findUsers(u -> u.getAnimeEntries().contains(userAnimeEntry) && u.getAnimeEntries().contains(anime))) {
                    numberOfUsers++;
                    denominator[0]++;
                    for (AnimeEntry unratedAnime : user.getAnimeEntries().stream().filter(a -> a.getMalAnimeId() == anime.getMalId()).collect(Collectors.toList())) {
                        scoreUnratedAnime = unratedAnime.getNormalizedScore();
                    }
                    for (AnimeEntry ratedAnimeByOtherUser : user.getAnimeEntries().stream().filter(a -> a.getMalAnimeId() == anime.getMalId()).collect(Collectors.toList())) {
                        scoreUnratedAnime = ratedAnimeByOtherUser.getNormalizedScore();
                    }
                    difference += scoreUnratedAnime - scoreRatedAnime;
                }
                nominator[0] += numberOfUsers * (userAnimeEntry.getNormalizedScore() + (difference / numberOfUsers));
            });
            reccomendationValues.add(nominator[0] / denominator[0]);
        }
        return reccomendationValues;
    }
}
