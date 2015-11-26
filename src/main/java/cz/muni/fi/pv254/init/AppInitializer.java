package cz.muni.fi.pv254.init;

import cz.muni.fi.pv254.algorithms.Normalizer;
import cz.muni.fi.pv254.dataUtils.DataStore;
import cz.muni.fi.pv254.utils.MemoryUsage;
import cz.muni.fi.pv254.utils.StatisticsUtils;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

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

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // init
        dataStore.fetchData();

    }

    private void showStats() {
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
