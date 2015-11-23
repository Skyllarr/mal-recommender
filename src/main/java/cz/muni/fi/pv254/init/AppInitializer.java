package cz.muni.fi.pv254.init;

import cz.muni.fi.pv254.repository.UserRepository;
import cz.muni.fi.pv254.utils.AnimeListParser;
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
       AnimeListParser animeListParser;


    @Inject
    UserRepository userRepository;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //Normalizer normalizer = new Normalizer(animeEntryRepository);
        //normalizer.normalize();
        //stats.showAverageAgeOfUsers();
        animeListParser.run();
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
