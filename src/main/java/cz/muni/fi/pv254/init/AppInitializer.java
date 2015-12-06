package cz.muni.fi.pv254.init;

import cz.muni.fi.pv254.algorithms.TextAnalyzer;
import cz.muni.fi.pv254.dataUtils.DataStore;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import static cz.muni.fi.pv254.utils.Utils.show;
import static cz.muni.fi.pv254.utils.Utils.showSortedFloat;

/**
 * Initializes application without web.xml file.
 */
@WebListener
class AppInitializer implements ServletContextListener {


    @Inject
    StartupBean startupBean;

    @Inject
    DataStore dataStore;

    @Inject
    TextAnalyzer textAnalyzer;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        dataStore.fetchData();
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
