package cz.muni.fi.pv254.init;

import cz.muni.fi.pv254.algorithms.Normalizer;
import cz.muni.fi.pv254.algorithms.OneSlope;
import cz.muni.fi.pv254.algorithms.TextAnalyzer;
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

import java.util.ArrayList;
import java.util.List;

import static cz.muni.fi.pv254.utils.Utils.*;

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
        new TestOneSlope().run(oneSlope, dataStore);
        //dataStore.fetchData();
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
