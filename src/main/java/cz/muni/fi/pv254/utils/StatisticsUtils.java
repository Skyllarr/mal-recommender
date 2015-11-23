package cz.muni.fi.pv254.utils;

import cz.muni.fi.pv254.dataUtils.DataQueries;
import cz.muni.fi.pv254.entity.AnimeEntry;
import cz.muni.fi.pv254.entity.DbAnime;
import cz.muni.fi.pv254.entity.DbUser;
import cz.muni.fi.pv254.enums.Gender;
import cz.muni.fi.pv254.enums.Genre;
import cz.muni.fi.pv254.repository.DbAnimeRepository;
import cz.muni.fi.pv254.repository.DbUserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.chrono.IsoChronology;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by suomiy on 11/22/15.
 */
@ApplicationScoped
public class StatisticsUtils {

    @Inject
    DbUserRepository dbUserRepository;

    @Inject
    DbAnimeRepository dbAnimeRepository;

    List<DbUser> dbUsers;
    List<DbAnime> dbAnimes;

    public StatisticsUtils() {
//        dbUsers = dbUserRepository.findAll();
//        dbAnimes = dbAnimeRepository.findAll();
    }
    //TODO return list of ordered most common with count
    // link : http://stackoverflow.com/questions/19031213/java-get-most-common-element-in-a-list
    public static <T> T mostCommon(List<T> list) {
        Map<T, Integer> map = new HashMap<>();
        for (T t : list) {
            Integer val = map.get(t);
            map.put(t, val == null ? 1 : val + 1);
        }

        Map.Entry<T, Integer> max = null;
        for (Map.Entry<T, Integer> e : map.entrySet()) {
            if (max == null || e.getValue() > max.getValue())
                max = e;
        }
        return max.getKey();
    }

    public double getAverageAge(List<DbUser> dbUsers) {
        int total = 0;
        int count = 0;

        for(DbUser u : dbUsers){
            if(u.getBirthday() != null){
                count++;
                total += u.getBirthday().until(IsoChronology.INSTANCE.dateNow()).getYears();
            }
        }
        return (count > 0 ? ((double) total)/count : 0);
    }

    public void showAverageAge() {
        try {
            List<DbUser> females = new ArrayList<DbUser>();
            List<DbUser> males = new ArrayList<DbUser>();
            for(DbUser u : dbUsers){
                if(u.getGender() != null){
                    if (u.getGender() == Gender.FEMALE)
                        females.add(u);
                    else if (u.getGender() == Gender.MALE)
                         males.add(u);
                }
            }
            System.out.println("There are " + dbUsers.size() + "women and their average age is: " + getAverageAge(dbUsers));
            System.out.println("There are " + males.size() + "men and their average age is: " + getAverageAge(males));
            System.out.println("There are " + females.size() + "women and their average age is: " + getAverageAge(females));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int[] getCategories(List<DbUser> dbUsers) {
        int[] ageCategories = new int[20];
        for(DbUser u : dbUsers){
            if(u.getBirthday() != null){
                int age = u.getBirthday().until(IsoChronology.INSTANCE.dateNow()).getYears();
                int mod = age % 5;
                int category = ((mod == 0) ?  age : age + (5 - mod))  / 5  - 1;
                ageCategories[category]++;
            }
        }
        return ageCategories;
    }

    public void showAgeCategories() {
        try {
            List<DbUser> females = new ArrayList<DbUser>();
            List<DbUser> males = new ArrayList<DbUser>();
            System.out.println("Age categories of dbUsers: " + getCategories(dbUsers));
            System.out.println("Age categories of men: " + getCategories(females));
            System.out.println("Average age of women: " + getCategories(males));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Inject
    DataQueries dataQueries;
    public Genre getMostFavouriteGenre() {
        List<AnimeEntry> animeEntries = dataQueries.getAnimeEntries();
        animeEntries.stream().filter(a -> a.getScore() == 10);
        List<DbAnime> mostScoredDbAnime = dbAnimes.stream().filter(a -> animeEntries.contains(a)).collect(Collectors.toList());
        List<Genre> genres = new ArrayList<>();
        for(DbAnime dbAnime : mostScoredDbAnime) {
            genres.addAll(dbAnime.getGenreEntriesAsList());
        }
        return mostCommon(genres);
    }

    public Genre getMostWatchedGenre() {
        List<AnimeEntry> animeEntries = dataQueries.getAnimeEntries();
        List<DbAnime> dbAnimeFromEntries = dbAnimes.stream().filter(a -> animeEntries.contains(a)).collect(Collectors.toList());
        List<Genre> genres = new ArrayList<>();
        for(DbAnime dbAnime : dbAnimeFromEntries) {
            genres.addAll(dbAnime.getGenreEntriesAsList());
        }
        return mostCommon(genres);
    }

    public Genre getMostFavouriteGenreOf(List<DbUser> dbUsers) {
        List<AnimeEntry> animeEntries = new ArrayList<AnimeEntry>();
        for (DbUser u : dbUsers) {
            animeEntries.addAll(u.getAnimeEntriesAsList());
        }
        animeEntries.stream().filter(a -> a.getScore() == 10);
        List<DbAnime> mostScoredDbAnime = dbAnimes.stream().filter(a -> animeEntries.contains(a)).collect(Collectors.toList());
        List<Genre> genres = new ArrayList<>();
        for(DbAnime dbAnime : mostScoredDbAnime) {
            genres.addAll(dbAnime.getGenreEntriesAsList());
        }
        return mostCommon(genres);
    }

    public Genre getMostWatchedGenreOf(List<DbUser> dbUsers) {
        List<AnimeEntry> animeEntries = new ArrayList<AnimeEntry>();
        for (DbUser u : dbUsers) {
            animeEntries.addAll(u.getAnimeEntriesAsList());
        }
        List<DbAnime> dbAnimeFromEntries = dbAnimes.stream().filter(a -> animeEntries.contains(a)).collect(Collectors.toList());
        List<Genre> genres = new ArrayList<>();
        for(DbAnime dbAnime : dbAnimeFromEntries) {
            genres.addAll(dbAnime.getGenreEntriesAsList());
        }
        return mostCommon(genres);
    }

    public DbAnime mostWatchedAnime() {
        List<AnimeEntry> animeEntries = dataQueries.getAnimeEntries();
        List<DbAnime> dbAnimeFromEntries = dbAnimes.stream().filter(a -> animeEntries.contains(a)).collect(Collectors.toList());
        return mostCommon(dbAnimeFromEntries);
    }
}
