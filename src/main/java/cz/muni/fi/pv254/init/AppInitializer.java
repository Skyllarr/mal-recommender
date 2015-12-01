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

import javax.inject.Inject;
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
    StatisticsUtils stats;

    @Inject
    DataStore dataStore;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // init
        dataStore.fetchData();

        /*TextAnalyzer textAnalyzer = new TextAnalyzer(dataStore);
        textAnalyzer.preProcess(true);
        dataStore.flush();
        User user = new User();
        List<AnimeEntry> listAnimeEntries = new ArrayList<>();
        listAnimeEntries.add(new AnimeEntry(269l, 8l, AnimeEntryStatus.COMPLETED));
        user.setAnimeEntries(listAnimeEntries);
        Utils.showSortedDouble(textAnalyzer.recommendToUser(user, dataStore));*/

        //show(new OneSlope(dataStore, false).recommendToUser(dataStore.findUserByName("lanblade"),10, null));

    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
