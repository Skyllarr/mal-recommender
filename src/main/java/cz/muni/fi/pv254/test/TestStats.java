package cz.muni.fi.pv254.test;

import cz.muni.fi.pv254.data.enums.Gender;
import cz.muni.fi.pv254.dataUtils.DataStore;
import cz.muni.fi.pv254.utils.StatisticsUtils;

import static cz.muni.fi.pv254.utils.Utils.*;

/**
 * Created by suomiy on 24.11.15.
 */
public class TestStats {

    public static void run(StatisticsUtils stats, DataStore dataStore) {
        show("age categories of users by step 5");
        showByListSize(stats.getAgeCategoriesOfAllUsers(5));
        show("age categories of users by step 1");
        showByListSize(stats.getAgeCategoriesOfAllUsers(1));
        show("age categories of females by step 1");
        showByListSize(stats.getAgeCategoriesOfAllUsersByGender(1, Gender.FEMALE));
        show("age categories of males by step 1");
        showByListSize(stats.getAgeCategoriesOfAllUsersByGender(1, Gender.MALE));
        show("favourite genres of users");
        show(stats.getMostFavouriteGenreOffUsers(dataStore.findAllUsers()));
        show("most watched anime vs mal");
        stats.getMostWatchedAnime().forEach((k, v)-> show(k.getMalId() + ": " + k.getPopularity() +": " + k.getRanked() + " : " + v));
        show("count anime entries by user");
        showSorted(stats.getDistributionOfEntriesByAllUsers());
        show("score");
        showSorted(stats.getDistributionOfScore());
        show("most watched anime");
        showSorted(stats.getMostWatchedAnime());
        show("gender");
        showSorted(stats.getDistributionOfGender());
        show("age by gender");
        show(stats.getAverageAgeByGender());
        show("genre by anime");
        showSorted(stats.getDistributionOfGenreByAnime());
        show("genre by all users");
        showSorted(stats.getDistributionOfGenreByAllUsers());
        show("normalized");
        showSortedByKeys(stats.getDistributionOfNormalizedScore(44));
        show("score");
        showSorted(stats.getDistributionOfScore());
    }
}
