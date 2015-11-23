package cz.muni.fi.pv254.algorithms;

import cz.muni.fi.pv254.entity.DbAnime;
import cz.muni.fi.pv254.entity.DbUser;
import cz.muni.fi.pv254.repository.DbAnimeRepository;
import cz.muni.fi.pv254.repository.DbUserRepository;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by skylar on 23.11.15.
 */
public class OneSlope {
    @Inject
    DbAnimeRepository dbAnimeRepository;
    @Inject
    DbUserRepository dbUserRepository;

    public OneSlope() {}
    public OneSlope(DbAnimeRepository dbAnimeRepository, DbUserRepository dbUserRepository) {
        this.dbAnimeRepository = dbAnimeRepository;
        this.dbUserRepository = dbUserRepository;
    }

    public void computeOneSlopeValues() {
        List<DbAnime> dbAnimes = dbAnimeRepository.findAll();
        List<DbUser> dbUsers = dbUserRepository.findAll();

        for(DbAnime dbAnime : dbAnimes){
            int numberOfRatings = 0;
            int numberOfRatedPairs = 0; //will be denominator for equation of one slope for this dbAnime
            for(DbUser dbUser : dbUsers) {
                if (dbUser.getAnimeEntriesAsList().contains(dbAnime)) {
                    numberOfRatings++;
                    numberOfRatedPairs += dbUser.getAnimeEntriesAsList().size() - 1;
                }
            }
        }
    }
}
