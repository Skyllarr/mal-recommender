package cz.muni.fi.pv254.init;

import cz.muni.fi.pv254.algorithms.Normalizer;
import cz.muni.fi.pv254.algorithms.OneSlope;
import cz.muni.fi.pv254.dataUtils.DataStore;
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
    OneSlope oneSlope;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // init
        dataStore.fetchData();
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
