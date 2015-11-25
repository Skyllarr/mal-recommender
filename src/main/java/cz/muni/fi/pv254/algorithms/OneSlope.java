package cz.muni.fi.pv254.algorithms;

import cz.muni.fi.pv254.data.Anime;
import cz.muni.fi.pv254.data.User;
import cz.muni.fi.pv254.dataUtils.DataStore;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by skylar on 23.11.15.
 */
public class OneSlope {
    @Inject
    DataStore dataStore;

    public void computeOneSlopeValues() {
        List<Anime> animes = dataStore.findAllAnimes();
        List<User> users = dataStore.findAllUsers();

        for(Anime anime : animes){
            int numberOfRatings = 0;
            int numberOfRatedPairs = 0; //will be denominator for equation of one slope for this anime
            for(User dbUser : users) {
                if (dbUser.getAnimeEntries().contains(anime)) {
                    numberOfRatings++;
                    numberOfRatedPairs += dbUser.getAnimeEntries().size() - 1;
                }
            }
        }
    }
}
