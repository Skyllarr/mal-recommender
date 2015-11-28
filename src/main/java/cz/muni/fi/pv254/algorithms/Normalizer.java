package cz.muni.fi.pv254.algorithms;

import cz.muni.fi.pv254.data.AnimeEntry;
import cz.muni.fi.pv254.data.User;
import cz.muni.fi.pv254.dataUtils.DataStore;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;


/**
 * Created by skylar on 22.11.15.
 */
public class Normalizer {

    public void normalize(DataStore dataStore) {
        List<AnimeEntry>  entries = dataStore.findAllAnimeEntriesWithScore();
        Map<User, List<AnimeEntry>> usersEntries = dataStore.findUsersWithEntriesWithScore();

        Double average = calculateAverage(entries);

        for(User user : usersEntries.keySet()){
            List<AnimeEntry> animeEntries = usersEntries.get(user);
            if(animeEntries.size() == 0){
                continue;
            }

            Double userAverage = calculateAverage(animeEntries);
            Double normalizationValue = average / userAverage;

            animeEntries.forEach( a -> a.setNormalizedScore( a.getScore() * normalizationValue));
        }
    }

    private Double calculateAverage(List<AnimeEntry> list){
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
