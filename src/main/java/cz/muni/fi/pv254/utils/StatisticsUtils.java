package cz.muni.fi.pv254.utils;

import cz.muni.fi.pv254.entity.User;
import cz.muni.fi.pv254.repository.AnimeEntryRepository;
import cz.muni.fi.pv254.repository.AnimeRepository;
import cz.muni.fi.pv254.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.chrono.IsoChronology;
import java.util.List;

/**
 * Created by suomiy on 11/22/15.
 */
@ApplicationScoped
public class StatisticsUtils {

    @Inject
    UserRepository userRepository;

    @Inject
    AnimeRepository animeRepository;

    @Inject
    AnimeEntryRepository animeEntryRepository;


    public void showAverageAgeOfUsers(){
        try {
            List<User> users = userRepository.findAll();
            int total = 0;
            int count = 0;

            for(User u : users){
                if(u.getBirthday() != null){
                    count++;
                    total += u.getBirthday().until(IsoChronology.INSTANCE.dateNow()).getYears();
                }
            }

            System.out.println("Average age: " + (count > 0 ? ((double) total)/count : 0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
