package cz.muni.fi.pv254.init;

import cz.muni.fi.pv254.algorithms.Normalizer;
import cz.muni.fi.pv254.algorithms.OneSlope;
import cz.muni.fi.pv254.data.Anime;
import cz.muni.fi.pv254.data.AnimeEntry;
import cz.muni.fi.pv254.data.User;
import cz.muni.fi.pv254.dataUtils.DataStore;
import cz.muni.fi.pv254.utils.StatisticsUtils;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import java.util.ArrayList;
import java.util.List;

import static cz.muni.fi.pv254.utils.Utils.show;
import static cz.muni.fi.pv254.utils.Utils.showSorted;
import static cz.muni.fi.pv254.utils.Utils.showSortedByKeys;

/**
 * Initializes application without web.xml file.
 */
@WebListener
class AppInitializer implements ServletContextListener {

    @Inject
    StatisticsUtils stats;

    @Inject
    DataStore dataStore;

    @Inject
    Normalizer normalizer;

    @Inject
    OneSlope oneSlope;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // init
        //dataStore.fetchData();


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

        animeEntry1.setMalAnimeId(1l);
        animeEntry2.setMalAnimeId(2l);
        animeEntry3.setMalAnimeId(3l);
        animeEntry4.setMalAnimeId(1l);
        animeEntry5.setMalAnimeId(2l);
        animeEntry6.setMalAnimeId(2l);
        animeEntry7.setMalAnimeId(3l);

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
        users.add(John);users.add(Mark);users.add(Lucy);

        List<Anime> anime = new ArrayList<>();
        anime.add(anime1);anime.add(anime3);anime.add(anime2);
        dataStore.setUsers(users);
        dataStore.setAnimes(anime);
        List<Double> oneSlopeValues= new ArrayList<>();
        oneSlopeValues = oneSlope.computeOneSlopeValues(lucyAnimes);
    }

    private void showStats() {
        show("favourite genres of users");
        show(stats.getMostFavouriteGenreOffUsers(dataStore.findAllUsers()));
        show("most watched anime vs mal");
        stats.getMostWatchedAnime().forEach((k, v)-> show(k.getMalId() + ": " + k.getPopularity() +": " + k.getRanked() + " : " + v));
        show("count anime entries by user");
        showSorted(stats.getDistributionOfEntriesByAllUsers());
        show("score");
        showSorted(stats.getDistributionOfScore());
        show("most watched anime");
        showSorted(stats.getMostWatchedAnime());
        show("gender");
        showSorted(stats.getDistributionOfGender());
        show("age by gender");
        show(stats.getAverageAgeByGender());
        show("genre by anime");
        showSorted(stats.getDistributionOfGenreByAnime());
        show("genre by all users");
        showSorted(stats.getDistributionOfGenreByAllUsers());
        show("normalized");
        showSortedByKeys(stats.getDistributionOfNormalizedScore(100));
        show("score");
        showSorted(stats.getDistributionOfScore());
    }


    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
