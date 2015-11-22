package cz.muni.fi.pv254.init;

import cz.muni.fi.pv254.algorithms.Normalizer;
import cz.muni.fi.pv254.entity.AnimeEntry;
import cz.muni.fi.pv254.entity.User;
import cz.muni.fi.pv254.repository.AnimeEntryRepository;
import cz.muni.fi.pv254.repository.UserRepository;
import cz.muni.fi.pv254.utils.AnimeListParser;
import cz.muni.fi.pv254.utils.StatisticsUtils;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Date;
import java.util.List;

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
    AnimeEntryRepository animeEntryRepository;

    @Inject
    UserRepository userRepository;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //Normalizer normalizer = new Normalizer(animeEntryRepository);
        //normalizer.normalize();
        //stats.showAverageAgeOfUsers();
        //animeListParser.run();
        System.out.println(new Date());
        List<AnimeEntry> animeEntries = animeEntryRepository.findAll();
        System.out.println(new Date());
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
