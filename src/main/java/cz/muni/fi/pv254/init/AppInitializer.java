package cz.muni.fi.pv254.init;

import com.mysema.commons.lang.Pair;
import cz.muni.fi.pv254.algorithms.Normalizer;
import cz.muni.fi.pv254.algorithms.OneSlopeDb;
import cz.muni.fi.pv254.data.Anime;
import cz.muni.fi.pv254.data.AnimeEntry;
import cz.muni.fi.pv254.data.User;
import cz.muni.fi.pv254.data.enums.Gender;
import cz.muni.fi.pv254.dataUtils.DataStore;
import cz.muni.fi.pv254.test.TestOneSlope;
import cz.muni.fi.pv254.utils.StatisticsUtils;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

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
    OneSlopeDb oneSlopeDb;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // init
        //dataStore.fetchData();
        //dataStore.findAllUsers().forEach(u -> show(u));
        //show(oneSlope.computeOneSlopeValues(dataStore.findUserByName("art_17").getAnimeEntries()));


        TestOneSlope.run(oneSlopeDb, dataStore);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
