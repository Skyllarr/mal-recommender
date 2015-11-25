package cz.muni.fi.pv254.utils;

import cz.muni.fi.pv254.data.Anime;
import cz.muni.fi.pv254.data.AnimeEntry;
import cz.muni.fi.pv254.data.User;
import cz.muni.fi.pv254.data.enums.Gender;
import cz.muni.fi.pv254.data.enums.Genre;
import cz.muni.fi.pv254.dataUtils.DataStore;

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
    DataStore dataStore;

    List<User> users;
    List<Anime> animes;

    public StatisticsUtils() {
    }

    public void init() {
        users = dataStore.findAllUsers();
        animes = dataStore.findAllAnimes();
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

    public double getAverageAge(List<User> users) {
        int total = 0;
        int count = 0;

        for(User u : users){
            if(u.getBirthday() != null){
                count++;
                total += u.getBirthday().until(IsoChronology.INSTANCE.dateNow()).getYears();
            }
        }
        return (count > 0 ? ((double) total)/count : 0);
    }

    public void showAverageAge() {
        try {
            List<User> females = new ArrayList<>();
            List<User> males = new ArrayList<>();
            for(User u : users){
                if(u.getGender() != null){
                    if (u.getGender() == Gender.FEMALE)
                        females.add(u);
                    else if (u.getGender() == Gender.MALE)
                         males.add(u);
                }
            }
            System.out.println("There are " + users.size() + "women and their average age is: " + getAverageAge(users));
            System.out.println("There are " + males.size() + "men and their average age is: " + getAverageAge(males));
            System.out.println("There are " + females.size() + "women and their average age is: " + getAverageAge(females));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int[] getCategories(List<User> users) {
        int[] ageCategories = new int[20];
        for(User u : users){
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
            List<User> females = new ArrayList<>();
            List<User> males = new ArrayList<>();
            System.out.println("Age categories of users: " + getCategories(users));
            System.out.println("Age categories of men: " + getCategories(females));
            System.out.println("Average age of women: " + getCategories(males));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Genre getMostFavouriteGenre() {
        List<AnimeEntry> animeEntries = dataStore.findAllAnimeEntries();
        animeEntries.stream().filter(a -> a.getScore() == 10);
        List<Anime> mostScoredAnime = animes.stream().filter(a -> animeEntries.contains(a)).collect(Collectors.toList());
        List<Genre> genres = new ArrayList<>();
        for(Anime anime : mostScoredAnime) {
            genres.addAll(anime.getGenres());
        }
        return mostCommon(genres);
    }

    public Genre getMostWatchedGenre() {
        List<AnimeEntry> animeEntries = dataStore.findAllAnimeEntries();
        List<Anime> animeFromEntries = animes.stream().filter(a -> animeEntries.contains(a)).collect(Collectors.toList());
        List<Genre> genres = new ArrayList<>();
        for(Anime anime : animeFromEntries) {
            genres.addAll(anime.getGenres());
        }
        return mostCommon(genres);
    }

    public Genre getMostFavouriteGenreOf(List<User> users) {
        List<AnimeEntry> animeEntries = new ArrayList<AnimeEntry>();
        for (User u : users) {
            animeEntries.addAll(u.getAnimeEntries());
        }
        animeEntries.stream().filter(a -> a.getScore() == 10);
        List<Anime> mostScoredAnime = animes.stream().filter(a -> animeEntries.contains(a)).collect(Collectors.toList());
        List<Genre> genres = new ArrayList<>();
        for(Anime anime : mostScoredAnime) {
            genres.addAll(anime.getGenres());
        }
        return mostCommon(genres);
    }

    public Genre getMostWatchedGenreOf(List<User> users) {
        List<AnimeEntry> animeEntries = new ArrayList<AnimeEntry>();
        for (User u : users) {
            animeEntries.addAll(u.getAnimeEntries());
        }
        List<Anime> animeFromEntries = animes.stream().filter(a -> animeEntries.contains(a)).collect(Collectors.toList());
        List<Genre> genres = new ArrayList<>();
        for(Anime anime : animeFromEntries) {
            genres.addAll(anime.getGenres());
        }
        return mostCommon(genres);
    }

    public Anime mostWatchedAnime() {
        List<AnimeEntry> animeEntries = dataStore.findAllAnimeEntries();
        List<Anime> animeFromEntries = animes.stream().filter(a -> animeEntries.contains(a)).collect(Collectors.toList());
        return mostCommon(animeFromEntries);
    }
}
