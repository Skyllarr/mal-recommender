package cz.muni.fi.pv254.init;

import cz.muni.fi.pv254.dataUtils.DataStore;
import cz.muni.fi.pv254.test.TestStats;
import cz.muni.fi.pv254.utils.StatisticsUtils;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import static cz.muni.fi.pv254.utils.Utils.show;

/**
 * Initializes application without web.xml file.
 */
@WebListener
class AppInitializer implements ServletContextListener {


    @Inject
    StartupBean startupBean;

    @Inject
    DataStore dataStore;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        dataStore.fetchData();
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
