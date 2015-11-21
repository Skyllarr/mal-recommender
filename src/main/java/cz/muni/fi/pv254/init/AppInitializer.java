package cz.muni.fi.pv254.init;

import cz.muni.fi.pv254.entity.Anime;
import cz.muni.fi.pv254.entity.AnimeEntry;
import cz.muni.fi.pv254.entity.User;
import cz.muni.fi.pv254.enums.AnimeEntryStatus;
import cz.muni.fi.pv254.enums.AnimeType;
import cz.muni.fi.pv254.repository.AnimeEntryRepository;
import cz.muni.fi.pv254.repository.AnimeRepository;
import cz.muni.fi.pv254.repository.MalUserRepository;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.time.LocalDate;

/**
 * Initializes application without web.xml file.
 */
@WebListener
class AppInitializer implements ServletContextListener {


    @Inject
    MalUserRepository malUserRepository;

    @Inject
    AnimeRepository animeRepository;

    @Inject
    AnimeEntryRepository animeEntryRepository;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try { //example create and find
            User user = new User("user", 1l, LocalDate.of(2000,1,1));
            malUserRepository.create(user);
            System.out.println(malUserRepository.findOne(user.getId()));

            Anime anime = new Anime("title", "http://link", 5l, 20l, AnimeType.TV);
            animeRepository.create(anime);
            System.out.println(animeRepository.findOne(anime.getId()));

            AnimeEntry entry = new AnimeEntry(anime, user, 10l, AnimeEntryStatus.CURRENTLY_WATCHING);
            animeEntryRepository.create(entry);
            System.out.println(animeEntryRepository.findOne(entry.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //create an instance
        //DomParser dpe = new DomParser();

        //call run example
        //dpe.run();
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
