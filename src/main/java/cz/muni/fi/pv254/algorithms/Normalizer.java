package cz.muni.fi.pv254.algorithms;

import cz.muni.fi.pv254.data.AnimeEntry;
import cz.muni.fi.pv254.data.User;
import cz.muni.fi.pv254.dataUtils.DataStore;
import cz.muni.fi.pv254.utils.Utils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;


/**
 * Created by skylar on 22.11.15.
 */
@ApplicationScoped
public class Normalizer {
    @Inject
    DataStore dataStore;

    private static final int maxNormalizedValue = 13; // do not allow excessively large scores

    public void normalize() {
        Double average = dataStore.getGlobalScoreAverage();
        Map<User, List<AnimeEntry>> usersEntries = dataStore.findUsersWithEntriesWithScore();

        for(User user : usersEntries.keySet()){
            List<AnimeEntry> animeEntries = usersEntries.get(user);
            if(animeEntries.size() == 0){
                return;
            }

            normalizeUser(average, animeEntries);
        }
    }

    public void normalizeUser(User user) {
        normalizeUser(dataStore.getGlobalScoreAverage(), user.getAnimeEntries());
    }

    private static void normalizeUser(Double average, List<AnimeEntry> animeEntries) {
        Double userAverage = calculateAverage(animeEntries);
        Double normalizationValue = average / userAverage;

        animeEntries.forEach( a -> {
            float result = (float) (a.getScore() * normalizationValue);
            result = result < maxNormalizedValue ? result : maxNormalizedValue;
            a.setNormalizedScore(result);
        });
    }

    public static Double calculateAverage(List<AnimeEntry> list){
        String badArg = "Bad Argument \"list\"";
        if(list == null || list.size() == 0){
            throw new IllegalArgumentException(badArg);
        }
        OptionalDouble average = list.stream()
                .mapToDouble( a -> (double) a.getScore() )
                .average();

        if(!average.isPresent()){
            throw new IllegalArgumentException(badArg);
        }

        return average.getAsDouble();
    }
}
