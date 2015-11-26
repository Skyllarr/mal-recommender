package cz.muni.fi.pv254.utils;

import cz.muni.fi.pv254.data.Anime;
import cz.muni.fi.pv254.data.AnimeEntry;
import cz.muni.fi.pv254.data.User;
import cz.muni.fi.pv254.data.enums.Genre;
import cz.muni.fi.pv254.dataUtils.DataStore;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.chrono.IsoChronology;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by suomiy on 11/22/15.
 */
@ApplicationScoped
public class StatisticsUtils {

    @Inject
    DataStore dataStore;

    public Map<Object, Integer> getDistributionOfGender() {
        return getDistribution(dataStore.findAllUsers().stream()
                .map(User::getGender)
                .collect(Collectors.toList()));
    }

    public Map<Object, Integer> getDistributionOfGenreByAnime() {
        List<Genre> genres = new ArrayList<>();
        dataStore.findAllAnimes().forEach(a -> a.getGenres().forEach(genres::add));
        return getDistribution(genres);
    }

    public Map<Object, Integer> getDistributionOfGenreByAllUsers() {
        Map<Object, Integer> result = getDistribution(new ArrayList<>());

        List<AnimeEntry> animeEntries =  dataStore.findAllAnimeEntries();
        Map<Long, Anime> map = dataStore.findAllAnimes().stream().collect(Collectors.toMap(Anime::getMalId, a -> a ));

        animeEntries.stream().forEach(a -> {
            mergetoDistribution(result, map.get(a.getMalAnimeId()).getGenres());
        });
        return result;
    }

    public Map<Object, Integer> getDistributionOfScore() {
        return getDistribution(dataStore.findAllAnimeEntries().stream()
                .map(AnimeEntry::getScore)
                .collect(Collectors.toList()));
    }

    //TODO fix
    public Map<Object, Integer> getDistributionOfNormalizedScore(int outputSize) {
        return getDistribution(dataStore.findAllAnimeEntries().stream()
                .map(AnimeEntry::getNormalizedScore)
                .collect(Collectors.toList()), outputSize);
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

    /*public void showAverageAge() {
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
    public List<Genre> getMostWatchedGenre() {
        List<AnimeEntry> animeEntries = dataStore.findAllAnimeEntries();
        List<Anime> animeFromEntries = animes.stream().filter(a -> animeEntries.contains(a)).collect(Collectors.toList());
        List<Genre> genres = new ArrayList<>();
        for(Anime anime : animeFromEntries) {
            genres.addAll(anime.getGenres());
        }
        return getDistribution(genres);
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
        return getDistribution(genres);
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
        return getDistribution(genres);
    }

    public Anime mostWatchedAnime() {
        List<AnimeEntry> animeEntries = dataStore.findAllAnimeEntries();
        List<Anime> animeFromEntries = animes.stream().filter(a -> animeEntries.contains(a)).collect(Collectors.toList());
        return getDistribution(animeFromEntries);
    }*/


    private static Map<Object, Integer> getDistribution(List<?> list) {
        return mergetoDistribution(new HashMap<>(),list);
    }

    private static Map<Object, Integer> mergetoDistribution( Map<Object, Integer> map, List<?> list) {
        list.stream().forEach( e -> {
            if(!map.containsKey(e)){
                map.put(e, 0);
            }
            map.merge(e, 1, (v,n) -> v + n);
        });

        return  map;
    }

    //TODO not working correctly
    private static Map<Object, Integer> getDistribution(List<Number> list, int outputSize) {
        if(list == null || list.size() == 0){
            throw new IllegalArgumentException("list");
        }
        List<Double> doubleList = list.parallelStream().map( n -> n == null ? 0 : n.doubleValue()).collect(Collectors.toList());
        Double max = doubleList.parallelStream().max(Double::compare).get();
        Double min = doubleList.parallelStream().min(Double::compare).get();
        Double precision = (max - min) / outputSize;

        return getDistribution(doubleList.parallelStream().map(a -> a - (a % precision)).collect(Collectors.toList()));
    }

}
