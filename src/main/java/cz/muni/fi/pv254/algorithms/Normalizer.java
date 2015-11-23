package cz.muni.fi.pv254.algorithms;

import cz.muni.fi.pv254.entity.AnimeEntry;
import cz.muni.fi.pv254.entity.User;
//import cz.muni.fi.pv254.repository.AnimeEntryRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by skylar on 22.11.15.
 */
public class Normalizer {
    //private AnimeEntryRepository animeEntryRepository;

    //public Normalizer (AnimeEntryRepository animeEntryRepository) {
        //this.animeEntryRepository = animeEntryRepository;


    public void normalize() {
        //List<AnimeEntry>  entries = animeEntryRepository.findAll();
        //System.out.print("a");
     /*           .stream()
                .filter(a -> a.getScore() > 0)
                .collect(Collectors.toList());

        OptionalDouble average = entries.stream()
                .mapToDouble( a -> (double) a.getScore() )
                .average();;

        if(!average.isPresent()){
            return;
        }

        Map<User,Set<AnimeEntry>>  usersEntries = new HashMap<>();

        for(AnimeEntry entry : entries){
            User user = entry.getUser();
            Set<AnimeEntry> set;

            if(usersEntries.containsKey(user)) {
                set = usersEntries.get(user);
            }else{
                set = new HashSet<>();
                usersEntries.put(user, set);
            }
            set.add(entry);
        }

        for(User user : usersEntries.keySet()){
            Set<AnimeEntry> set = usersEntries.get(user);
            OptionalDouble userAverage = set.stream()
                    .mapToDouble( a -> (double) a.getScore() )
                    .average();;

            Double normalizationValue = userAverage.isPresent() ? average.getAsDouble() / userAverage.getAsDouble() : 1;

            set.stream().forEach( a -> a.setNormalizedScore( a.getScore() * normalizationValue));
        }*/
    }
}
