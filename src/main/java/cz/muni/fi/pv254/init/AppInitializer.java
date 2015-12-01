package cz.muni.fi.pv254.init;

import cz.muni.fi.pv254.algorithms.OneSlope;
import cz.muni.fi.pv254.algorithms.TextAnalyzer;
import cz.muni.fi.pv254.data.AnimeEntry;
import cz.muni.fi.pv254.data.User;
import cz.muni.fi.pv254.data.enums.AnimeEntryStatus;
import cz.muni.fi.pv254.dataUtils.DataStore;
import cz.muni.fi.pv254.test.TestTextAnalyzer;
import cz.muni.fi.pv254.utils.MemoryUsage;
import cz.muni.fi.pv254.utils.StatisticsUtils;
import cz.muni.fi.pv254.utils.Utils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import java.util.ArrayList;
import java.util.List;

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
