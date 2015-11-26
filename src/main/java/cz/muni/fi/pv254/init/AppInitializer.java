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

        // not working right
        show(stats.getDistributionOfNormalizedScore(10));

       /* showSorted(stats.getDistributionOfScore());
        showSorted(stats.getDistributionOfGender());
        showSorted(stats.getDistributionOfGenreByAnime());
        showSorted(stats.getDistributionOfGenreByAllUsers());*/

    }


    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
