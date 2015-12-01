package cz.muni.fi.pv254.init;

import cz.muni.fi.pv254.algorithms.TextAnalyzer;
import cz.muni.fi.pv254.dataUtils.DataStore;
import cz.muni.fi.pv254.utils.StatisticsUtils;
import cz.muni.fi.pv254.utils.Utils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by suomiy on 12/1/15.
 */

@Singleton
@Startup
public class StartupBean {

    @Inject
    DataStore dataStore;

    @PostConstruct
    public void initialize() {
        /* new Thread(() -> {
             dataStore.fetchData();
         }).start();*/
    }
}
