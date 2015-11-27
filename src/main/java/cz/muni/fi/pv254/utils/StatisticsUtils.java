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
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by suomiy on 11/22/15.
 */
@ApplicationScoped
public class StatisticsUtils {

    @Inject
    DataStore dataStore;


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
            mergeToDistribution(result, map.get(a.getMalId()).getGenres());
        });
        return result;
    }

    public Map<Object, Integer> getDistributionOfEntriesByAllUsers() {
        return dataStore.findAllUsers().stream().collect(Collectors.toMap(User::getId, a -> a.getAnimeEntries().size() ));
    }

    public Map<Object, Integer> getDistributionOfScore() {
        return getDistribution(dataStore.findAllAnimeEntries().stream()
                .map(AnimeEntry::getScore)
                .collect(Collectors.toList()));
    }

    // normalize score can be unbounded
    public Map<Object, Integer> getDistributionOfNormalizedScore(int outputSize) {
        return getDistribution(dataStore.findAllAnimeEntries().stream()
                .map(AnimeEntry::getNormalizedScore)
                .collect(Collectors.toList()), outputSize);
    }

    public Map<Object, Integer> getDistributionOfGender() {
        return getDistribution(dataStore.findAllUsers().stream()
                .map(User::getGender)
                .collect(Collectors.toList()));
    }

    public Map<Gender, Double> getAverageAgeByGender() {
        Map<Gender, Double> map = new HashMap<>();
        map.put(Gender.FEMALE, getAverageAge(dataStore.findUsers(u -> u.getGender() == Gender.FEMALE)));
        map.put(Gender.MALE, getAverageAge(dataStore.findUsers(u -> u.getGender() == Gender.MALE)));
        map.put(null, getAverageAge(dataStore.findUsers(u -> u.getGender() ==null)));

        return map;
    }

    public Map<Anime, Integer> getMostWatchedAnime() {
        Map<Anime, Integer> result = new HashMap<>();
        getDistribution(dataStore.findAllAnimeEntries()
                .stream()
                .map(AnimeEntry::getMalId)
                .collect(Collectors.toList()))
                .forEach((k,v) -> result.put(dataStore.findAnimeByMalId((Long)k), v));
        return result;
    }

    public Map<Object,  List<Genre>> getMostFavouriteGenreOffUsers(List<User> users) {
        return users.stream().collect(Collectors.toMap(u -> u, a -> getFavoriteGenres(a.getAnimeEntries())));
    }

    private   List<Genre> getFavoriteGenres(List<AnimeEntry> entries) {
        Map<Object, Integer> result = getDistribution(new ArrayList<>());
        entries.stream().forEach(e -> mergeToDistribution(result, dataStore.findAnimeByMalId(e.getMalId()).getGenres()));
        return Utils.sortByValue(result).keySet().stream().map(e -> (Genre)e).collect(Collectors.toList());
    }

    public Map<Integer, List<User>> getAgeCategories(List<User> users, int yearStep) {
        if(yearStep < 1){
            throw new IllegalArgumentException(yearStep + " can be minimum 1");
        }

        Map<Integer, List<User>> map = new HashMap<>();
        users.forEach( user -> {
            Integer category = null;
            List<User> mapUsers = null;

            if(user.getBirthday() != null){
                int age = user.getBirthday().until(IsoChronology.INSTANCE.dateNow()).getYears();
                if(age < 0){
                    throw new IllegalArgumentException(user + " has negative age");
                }
                age = age == 0 ? yearStep : age;
                int mod = age % yearStep;
                category = ((mod == 0) ?  age : age + (yearStep - mod))  / yearStep;
            }

            if(map.containsKey(category)){
                mapUsers = map.get(category);
            }else{
                mapUsers = new ArrayList<>();
                map.put(category, mapUsers);
            }

            mapUsers.add(user);
        });
        return map;
    }

    public Map<Integer, List<User>> getAgeCategoriesOfAllUsersByGender(int yearStep, Gender gender) {
        return getAgeCategories(dataStore.findUsers(u -> u.getGender() == gender), yearStep);
    }

    public Map<Integer, List<User>> getAgeCategoriesOfAllUsers( int yearStep) {
        return getAgeCategories(dataStore.findAllUsers(), yearStep);
    }

    private double getAverageAge(List<User> users) {
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

    private static Map<Object, Integer> getDistribution(List<?> list) {
        return mergeToDistribution(new HashMap<>(),list);
    }

    private static Map<Object, Integer> mergeToDistribution(Map<Object, Integer> map, List<?> list) {
        list.stream().forEach( e -> {
            if(!map.containsKey(e)){
                map.put(e, 0);
            }
            map.merge(e, 1, (v,n) -> v + n);
        });

        return  map;
    }

}
