package cz.muni.fi.pv254.init;

import cz.muni.fi.pv254.algorithms.OneSlope;
import cz.muni.fi.pv254.data.Anime;
import cz.muni.fi.pv254.data.User;
import cz.muni.fi.pv254.dataUtils.DataStore;
import cz.muni.fi.pv254.repository.UserRepository;
import cz.muni.fi.pv254.test.TestOneSlope;
import cz.muni.fi.pv254.utils.MemoryUsage;
import cz.muni.fi.pv254.test.TestTextAnalyzer;
import cz.muni.fi.pv254.utils.StatisticsUtils;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import java.util.List;

import static cz.muni.fi.pv254.utils.Utils.show;
import static cz.muni.fi.pv254.utils.Utils.showSortedDouble;

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
    UserRepository userRepository;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // init
       /* dataStore.fetchData();*/
        TestTextAnalyzer textAnalyzer = new TestTextAnalyzer();
        textAnalyzer.run(dataStore);


    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
