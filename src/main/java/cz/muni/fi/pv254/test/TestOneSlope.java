package cz.muni.fi.pv254.test;

import cz.muni.fi.pv254.algorithms.OneSlope;
import cz.muni.fi.pv254.data.Anime;
import cz.muni.fi.pv254.data.AnimeEntry;
import cz.muni.fi.pv254.data.User;
import cz.muni.fi.pv254.dataUtils.DataStore;
import cz.muni.fi.pv254.repository.AnimeRepository;
import cz.muni.fi.pv254.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by skylar on 24.11.15.
 */
public class TestOneSlope {


    public static void run(OneSlope oneSlope, DataStore dataStore) {

        User John = new User();
        User Mark = new User();
        User Lucy = new User();

        Anime anime1 = new Anime();
        Anime anime2 = new Anime();
        Anime anime3 = new Anime();

        anime1.setMalId(1l);
        anime2.setMalId(2l);
        anime3.setMalId(3l);

        AnimeEntry animeEntry1 = new AnimeEntry();
        AnimeEntry animeEntry2 = new AnimeEntry();
        AnimeEntry animeEntry3 = new AnimeEntry();
        AnimeEntry animeEntry4 = new AnimeEntry();
        AnimeEntry animeEntry5 = new AnimeEntry();
        AnimeEntry animeEntry6 = new AnimeEntry();
        AnimeEntry animeEntry7 = new AnimeEntry();

        animeEntry1.setMalId(1l);
        animeEntry2.setMalId(2l);
        animeEntry3.setMalId(3l);
        animeEntry4.setMalId(1l);
        animeEntry5.setMalId(2l);
        animeEntry6.setMalId(2l);
        animeEntry7.setMalId(3l);

        animeEntry1.setNormalizedScore(5.0);
        animeEntry2.setNormalizedScore(3.0);
        animeEntry3.setNormalizedScore(2.0);
        animeEntry4.setNormalizedScore(3.0);
        animeEntry5.setNormalizedScore(4.0);
        animeEntry6.setNormalizedScore(2.0);
        animeEntry7.setNormalizedScore(5.0);


        List<AnimeEntry> johnAnimes = new ArrayList<>();
        List<AnimeEntry> markAnimes = new ArrayList<>();
        List<AnimeEntry> lucyAnimes = new ArrayList<>();


        johnAnimes.add(animeEntry1);
        johnAnimes.add(animeEntry2);
        johnAnimes.add(animeEntry3);
        markAnimes.add(animeEntry4);
        markAnimes.add(animeEntry5);
        lucyAnimes.add(animeEntry6);
        lucyAnimes.add(animeEntry7);

        John.setAnimeEntries(johnAnimes);
        Mark.setAnimeEntries(markAnimes);
        Lucy.setAnimeEntries(lucyAnimes);

        List<User> users = new ArrayList<>();
        users.add(John);
        users.add(Mark);
        users.add(Lucy);

        List<Anime> anime = new ArrayList<>();
        anime.add(anime1);
        anime.add(anime3);
        anime.add(anime2);
        dataStore.setUsers(users);
        dataStore.setAnimes(anime);
        List<Double> oneSlopeValues = new ArrayList<>();
        oneSlope.computeOneSlopeValues(lucyAnimes);
    }
}
