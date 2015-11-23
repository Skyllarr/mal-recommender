package cz.muni.fi.pv254.init;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cz.muni.fi.pv254.algorithms.Normalizer;
import cz.muni.fi.pv254.entity.AnimeEntry;
import cz.muni.fi.pv254.entity.User;
import cz.muni.fi.pv254.enums.AnimeEntryStatus;
import cz.muni.fi.pv254.repository.UserRepository;
import cz.muni.fi.pv254.utils.AnimeListParser;
import cz.muni.fi.pv254.utils.StatisticsUtils;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Initializes application without web.xml file.
 */
@WebListener
class AppInitializer implements ServletContextListener {

    //@Inject
    //StatisticsUtils stats;

    @Inject
       AnimeListParser animeListParser;

    //@Inject
   // AnimeEntryRepository animeEntryRepository;

    @Inject
    UserRepository userRepository;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //Normalizer normalizer = new Normalizer(animeEntryRepository);
        //normalizer.normalize();
        //stats.showAverageAgeOfUsers();
        //animeListParser.run();
        AnimeEntry entry = new AnimeEntry(35000l, 10l, AnimeEntryStatus.ALL_ANIME);
        try {
            ArrayList<AnimeEntry> list = new ArrayList<>();
            list.add(entry);
            list.add(entry);
            Gson gson = new Gson();
            String str = gson.toJson(list);
            Type listType = new TypeToken<ArrayList<AnimeEntry>>() {
            }.getType();
            List<AnimeEntry> list1 = gson.fromJson(str, listType);
            System.out.println(list1.get(0));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
