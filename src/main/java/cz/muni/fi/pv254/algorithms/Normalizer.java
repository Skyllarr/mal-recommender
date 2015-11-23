package cz.muni.fi.pv254.algorithms;

import cz.muni.fi.pv254.dataUtils.DataQueries;
import cz.muni.fi.pv254.entity.AnimeEntry;
import cz.muni.fi.pv254.entity.DbUser;
import cz.muni.fi.pv254.repository.DbUserRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;


/**
 * Created by skylar on 22.11.15.
 */
public class Normalizer {


    @Inject
    DbUserRepository dbUserRepository;

    public Normalizer () {

    }

    @Inject
    DataQueries dataQueries;

    public void normalize() {
        List<AnimeEntry>  entries = dataQueries.getAnimeEntriesWithScore();
        Map<DbUser, List<AnimeEntry>> usersEntries = dataQueries.getUsersWithEntriesWithScore();

        Double average = calculateAverage(entries);

        for(DbUser dbUser : usersEntries.keySet()){
            List<AnimeEntry> animeEntries = usersEntries.get(dbUser);
            Double userAverage = calculateAverage(animeEntries);

            Double normalizationValue = average / userAverage;

            animeEntries.stream().forEach( a -> a.setNormalizedScore( a.getScore() * normalizationValue));

            dbUser.setAnimeEntriesAsString(animeEntries);
        }
    }

    private Double calculateAverage(List<AnimeEntry> list){
        if(list == null || list.size() == 0){
            throw new IllegalArgumentException("Bad Argument list");
        }
        OptionalDouble average = list.stream()
                .mapToDouble( a -> (double) a.getScore() )
                .average();

        if(!average.isPresent()){
            throw new IllegalArgumentException("Bad Argument list");
        }

        return average.getAsDouble();
    }
}
