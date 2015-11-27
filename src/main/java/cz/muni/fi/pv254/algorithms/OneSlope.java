package cz.muni.fi.pv254.algorithms;

import com.mysema.commons.lang.Pair;
import cz.muni.fi.pv254.data.Anime;
import cz.muni.fi.pv254.data.AnimeEntry;
import cz.muni.fi.pv254.data.User;
import cz.muni.fi.pv254.dataUtils.DataStore;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cz.muni.fi.pv254.utils.Utils.show;

/**
 * Created by skylar on 23.11.15.
 */
public class OneSlope {
    @Inject
    DataStore dataStore;

    public boolean checkAnimeEntryListDifference(List<AnimeEntry> list1, List<AnimeEntry> list2) {
        boolean contains = false;
        boolean doesNotContain = false;

        for (AnimeEntry animeEntry : list1) {
            contains = contains || list2.contains(animeEntry);
            doesNotContain = doesNotContain || !list2.contains(animeEntry);
            if (contains && doesNotContain)
                return true;
        }
        return false;
    }

    public boolean checkIfContainsScore(List<AnimeEntry> list, Anime anime, AnimeEntry animeEntry) {
        return list.contains(anime) && list.contains(animeEntry);
    }

    public Double getScoreOfAnimeWithMalId (List<AnimeEntry> animeEntries, Long malId) {
        for (AnimeEntry animeEntry : animeEntries) {
            if (animeEntry.getMalId() == malId)
                return animeEntry.getNormalizedScore();
        }
        return null;
    }

    public Map<Anime, Double> computeOneSlopeValues(List<AnimeEntry> userAnimeEntries) {
        Map<Anime, Pair<Double, Integer>> reccomendationValues = new HashMap<>();
        List<AnimeEntry> seenAnime = userAnimeEntries.stream().filter(a -> a.getNormalizedScore() != null).collect(Collectors.toList());
        List<Anime> unSeenAnime = dataStore.findAnimes(anime -> !seenAnime.contains(anime));
        List<User> intersectingUsers = dataStore.findUsers(u -> checkAnimeEntryListDifference(u.getAnimeEntries(), seenAnime));

        for(AnimeEntry seenAnimeEntry : seenAnime) { //pre vsetky hodnotene anime Lucy
            for (Anime unseenAnime : unSeenAnime){ //pre vsetky anime co nehodnotila
                double animeDifferenceCount = 0.0;
                List<User> usersContainingBoth = intersectingUsers.stream().filter(u -> {
                    List<AnimeEntry> list = u.getAnimeEntries();
                    list = list.stream().filter(a -> a.getNormalizedScore() != null).collect(Collectors.toList());
                    return list.contains(unseenAnime) && list.contains(seenAnimeEntry);
                }).collect(Collectors.toList());

                for (User user : usersContainingBoth) {
                    animeDifferenceCount += computeDifference(user.getAnimeEntries(), unseenAnime, seenAnimeEntry);
                }
                if (usersContainingBoth.size() > 0){
                    int usersWithBoth = usersContainingBoth.size();
                    double averagedAnimeDifference = (animeDifferenceCount / usersWithBoth);
                    double nominator = usersWithBoth * (seenAnimeEntry.getNormalizedScore() + averagedAnimeDifference);

                    if (reccomendationValues.containsKey(unseenAnime)) {
                        Pair<Double, Integer> mapValue = reccomendationValues.get(unseenAnime);
                        reccomendationValues.put(unseenAnime,
                                new Pair<>(mapValue.getFirst() + nominator, mapValue.getSecond() + usersWithBoth));
                    } else {
                        reccomendationValues.put(unseenAnime, new Pair<>(nominator, usersWithBoth));
                    }
                }
            }
        }
        return reccomendationValues.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getFirst() / e.getValue().getSecond()));
    }

    public Double computeDifference(List<AnimeEntry> entriesUser1, Anime anime, AnimeEntry entry) {
        AnimeEntry commonEntry = null;
        AnimeEntry unCommonEntry = null;

        for (AnimeEntry scoredAnime : entriesUser1) {

            if(scoredAnime.equals(entry)) {
                commonEntry = scoredAnime;
            }
            if(scoredAnime.equals(anime)) {
                unCommonEntry = scoredAnime;
            }
        }

        return unCommonEntry.getNormalizedScore() - commonEntry.getNormalizedScore();
    }
}
