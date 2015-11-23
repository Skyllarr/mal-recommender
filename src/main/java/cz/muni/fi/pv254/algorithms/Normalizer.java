package cz.muni.fi.pv254.algorithms;

import cz.muni.fi.pv254.entity.AnimeEntry;
import cz.muni.fi.pv254.entity.User;
import cz.muni.fi.pv254.repository.UserRepository;
//import cz.muni.fi.pv254.repository.AnimeEntryRepository;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by skylar on 22.11.15.
 */
public class Normalizer {


    @Inject
    UserRepository userRepository;

    public Normalizer () {

    }


    public void normalize() {
        List<User>  users = userRepository.findAll();
        Map<User, List<AnimeEntry>>  usersEntries = new HashMap<>();
        List<AnimeEntry>  entries = new ArrayList<>();

        for(User user : users){
            List<AnimeEntry> list =  user.getAnimeEntriesAsList()
                    .stream()
                    .filter(a -> a.getScore() > 0)
                    .collect(Collectors.toList());

            usersEntries.put(user, list);
            entries.addAll(list);
        }

        OptionalDouble average = entries.stream()
                .mapToDouble( a -> (double) a.getScore() )
                .average();;

        if(!average.isPresent()){
            return;
        }


        for(User user : usersEntries.keySet()){
            List<AnimeEntry> animeEntries = usersEntries.get(user);
            OptionalDouble userAverage = animeEntries.stream()
                    .mapToDouble( a -> (double) a.getScore() )
                    .average();

            Double normalizationValue = userAverage.isPresent() ? average.getAsDouble() / userAverage.getAsDouble() : 1;

            animeEntries.stream().forEach( a -> a.setNormalizedScore( a.getScore() * normalizationValue));

            user.setAnimeEntriesAsString(animeEntries);
        }
    }
}
