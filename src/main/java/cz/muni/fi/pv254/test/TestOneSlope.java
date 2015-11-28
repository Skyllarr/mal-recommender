package cz.muni.fi.pv254.test;

import cz.muni.fi.pv254.algorithms.OneSlope;
import cz.muni.fi.pv254.data.Anime;
import cz.muni.fi.pv254.data.AnimeEntry;
import cz.muni.fi.pv254.data.User;
import cz.muni.fi.pv254.dataUtils.DataStore;

import java.util.ArrayList;
import java.util.List;

import static cz.muni.fi.pv254.utils.Utils.show;

/**
 * Created by skylar on 24.11.15.
 */
public class TestOneSlope {


    public static void run(DataStore dataStore) {

        User John = new User();
        User Mark = new User();
        User Lucy = new User();
        User Jessica = new User();

        John.setMalId(1l);
        Mark.setMalId(2l);
        Lucy.setMalId(3l);
        Jessica.setMalId(4l);

        John.setId(1l);
        Mark.setId(2l);
        Lucy.setId(3l);
        Jessica.setId(4l);

        Anime anime1 = new Anime();
        Anime anime2 = new Anime();
        Anime anime3 = new Anime();
        Anime anime4 = new Anime();

        anime1.setMalId(1l);
        anime1.setTitle("A");
        anime2.setMalId(2l);
        anime2.setTitle("B");
        anime3.setMalId(3l);
        anime3.setTitle("C");
        anime4.setMalId(4l);
        anime4.setTitle("D");

        AnimeEntry animeEntry1 = new AnimeEntry();
        AnimeEntry animeEntry2 = new AnimeEntry();
        AnimeEntry animeEntry3 = new AnimeEntry();
        AnimeEntry animeEntry4 = new AnimeEntry();
        AnimeEntry animeEntry5 = new AnimeEntry();
        AnimeEntry animeEntry6 = new AnimeEntry();
        AnimeEntry animeEntry7 = new AnimeEntry();
        AnimeEntry animeEntry8 = new AnimeEntry();
        AnimeEntry animeEntry9 = new AnimeEntry();
        AnimeEntry animeEntry10 = new AnimeEntry();
        AnimeEntry animeEntry11 = new AnimeEntry();

        animeEntry1.setMalId(1l);
        animeEntry2.setMalId(1l);
        animeEntry3.setMalId(1l);
        animeEntry4.setMalId(2l);
        animeEntry5.setMalId(2l);
        animeEntry6.setMalId(2l);
        animeEntry7.setMalId(3l);
        animeEntry8.setMalId(3l);
        animeEntry9.setMalId(3l);
        animeEntry10.setMalId(4l);
        animeEntry11.setMalId(4l);

        animeEntry1.setNormalizedScore(4.0);
        animeEntry2.setNormalizedScore(5.0);
        animeEntry3.setNormalizedScore(3.0);
        animeEntry4.setNormalizedScore(3.0);
        animeEntry5.setNormalizedScore(4.0);
        animeEntry6.setNormalizedScore(2.0);
        animeEntry7.setNormalizedScore(1.0);
        animeEntry8.setNormalizedScore(2.0);
        animeEntry9.setNormalizedScore(5.0);
        animeEntry10.setNormalizedScore(3.0);
        animeEntry11.setNormalizedScore(1.0);


        List<AnimeEntry> johnAnimes = new ArrayList<>();
        List<AnimeEntry> markAnimes = new ArrayList<>();
        List<AnimeEntry> lucyAnimes = new ArrayList<>();
        List<AnimeEntry> jessicaAnimes = new ArrayList<>();


        johnAnimes.add(animeEntry8);
        johnAnimes.add(animeEntry10);
        johnAnimes.add(animeEntry2);
        johnAnimes.add(animeEntry4);
        markAnimes.add(animeEntry3);
        markAnimes.add(animeEntry5);
        markAnimes.add(animeEntry11);
        lucyAnimes.add(animeEntry6);
        lucyAnimes.add(animeEntry9);
        jessicaAnimes.add(animeEntry7);
        jessicaAnimes.add(animeEntry1);

        John.setAnimeEntries(johnAnimes);
        Mark.setAnimeEntries(markAnimes);
        Lucy.setAnimeEntries(lucyAnimes);
        Jessica.setAnimeEntries(jessicaAnimes);

        List<User> users = new ArrayList<>();
        users.add(John);
        users.add(Mark);
        //users.add(Lucy);
        users.add(Jessica);

        List<Anime> anime = new ArrayList<>();
        anime.add(anime1);
        anime.add(anime2);
        anime.add(anime3);
        anime.add(anime4);
        dataStore.setData(users, anime);
        //dataStore.setAnimes(anime);
        //List<Double> oneSlopeValues = new ArrayList<>();
        OneSlope oneSlope = new OneSlope(dataStore, true);
        oneSlope.preProcess();
        show(oneSlope.recommendToUser(Lucy));

    }
}
