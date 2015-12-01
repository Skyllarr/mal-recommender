package cz.muni.fi.pv254.dataUtils;

import cz.muni.fi.pv254.data.Anime;
import cz.muni.fi.pv254.data.AnimeEntry;
import cz.muni.fi.pv254.data.User;
import cz.muni.fi.pv254.repository.AnimeRepository;
import cz.muni.fi.pv254.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static cz.muni.fi.pv254.utils.Utils.show;

/**
 * Created by skylar on 23.11.15.
 */
@ApplicationScoped
public class DataStore {

    @Inject
    UserRepository userRepository;

    @Inject
    AnimeRepository animeRepository;

    private List<User> users;
    private List<Anime> animes;
    private List<Anime> animesForTextAnalysis;
    private List<AnimeEntry>  animeEntries;
    private Map<Long, Anime>  animeMalIdMap;
    private Map<Long, Integer>  animeViewCountMap;

    public List<Anime> findAllAnimes() {
        return animes;
    }

    public List<Anime> findAnimesForTextAnalysis() {
        return animesForTextAnalysis;
    }

    public List<User> findAllUsers() {
        return users;
    }

    public List<AnimeEntry> findAllAnimeEntries() {
        return animeEntries;
    }

    public void fetchData(){
        animesForTextAnalysis = animeRepository.findAllWithDeleted(); //animes = animeRepository.findAll();
        animesForTextAnalysis.sort((a,b) -> a.getMalId().compareTo(b.getMalId()));
        animes = animesForTextAnalysis.stream().filter(a -> !a.isDeleted()).collect(Collectors.toList());
        animes.sort((a,b) -> a.getMalId().compareTo(b.getMalId()));
        users = userRepository.findAll();
        prepareAnimeMaps();
    }

    public void partialFetchData(){
        animes = animeRepository.findAll();
        animes.sort((a,b) -> a.getMalId().compareTo(b.getMalId()));
        users = userRepository.findAll();
        prepareAnimeMaps();

        users.forEach(u -> {
            List<AnimeEntry> entries = u.getAnimeEntries();
            for (Iterator<AnimeEntry> iterator = entries.iterator(); iterator.hasNext(); ) {
                AnimeEntry entry = iterator.next();
                if (entry.getScore() == 0 || !animeMalIdMap.containsKey(entry.getMalId())) {
                    iterator.remove();
                }
            }
        });

    }

    public void flush(){
        userRepository.batchUpdate(users);
        animeRepository.batchUpdate(animes);
    }

    public User findUserByName(String name){
        return findUser(u -> u.getName() != null && u.getName().equals(name));
    }

    public User findUserById(Long Id){
        return findUser(u -> u.getId() != null && u.getId().longValue() ==  Id);
    }

    public Anime findAnimeById(Long Id){
        return findAnime(a -> a.getId() != null && a.getId().longValue() == Id);
    }

    public Anime findAnimeByMalId(Long malId) {
        return animeMalIdMap.get(malId);
    }

    public User findUserByMalId(Long malId) {
        return findUser(u -> u.getMalId() != null && u.getMalId().longValue() == malId);
    }

    public void deleteDbUser(Long id) throws Exception {
        users = findUsers( u -> u.getId().longValue() !=  id);
        userRepository.delete(id);
    }

    public void createAnime(Anime anime) throws Exception {
        animes.add(anime);
    }


    public List<AnimeEntry> findAllAnimeEntriesWithScore() {
        return filterUnscoredEntries(findAllAnimeEntries());
    }

    public Map<User, List<AnimeEntry>> findUsersWithEntries() {
        return users.stream().collect(Collectors.toMap(u -> u, User::getAnimeEntries));
    }

    public Map<User, List<AnimeEntry>> findUsersWithEntriesWithScore() {
        Map<User, List<AnimeEntry>> usersEntries = findUsersWithEntries();
        usersEntries.replaceAll((k, v) -> filterUnscoredEntries(v));
        return usersEntries;
    }

    public User findUser(Predicate<User> predicate){
        return users.stream().filter(predicate).findFirst().orElse(null);
    }

    public List<User> findUsers(Predicate<User> predicate){
        return users.stream().filter(predicate).collect(Collectors.toList());
    }

    public Anime findAnime(Predicate<Anime> predicate){
        return animes.stream().filter(predicate).findFirst().orElse(null);
    }

    public List<Anime> findAnimes(Predicate<Anime> predicate){
        return animes.stream().filter(predicate).collect(Collectors.toList());
    }

    public AnimeEntry findAnimeEntries(Predicate<AnimeEntry> predicate){
        return animeEntries.stream().filter(predicate).findFirst().orElse(null);
    }

    public List<AnimeEntry> findAnimeEntry(Predicate<AnimeEntry> predicate){
        return animeEntries.stream().filter(predicate).collect(Collectors.toList());
    }

    public Integer findAnimeViewCount(Long malId){
        return animeViewCountMap.get(malId);
    }

    private List<AnimeEntry> filterUnscoredEntries(List<AnimeEntry> entries){
        return entries.stream().filter(a -> a.getScore() > 0).collect(Collectors.toList());
    }

    public Map<Long,Map<AnimeEntry, AnimeEntry>> getUsersAsMapOfMaps() {
        return users.stream().collect(Collectors.toMap(User::getId, u -> u.getAnimeEntries()
                .stream().collect(Collectors.toMap(e -> e, e -> e))));
    }

    public void setData(List<User> users, List<Anime> animes) {
        this.users = users;
        this.animes = animes;
        animes.sort((a,b) -> a.getMalId().compareTo(b.getMalId()));
        prepareAnimeMaps();
    }

    public void setAnimes(List<Anime> animes) {
        this.animes = animes;
    }

    private void prepareAnimeMaps() {
        animeEntries = new ArrayList<>();
        users.forEach(u -> animeEntries.addAll(u.getAnimeEntries()));
        animeMalIdMap = animesForTextAnalysis.stream().collect(Collectors.toMap(Anime::getMalId, a -> a, (a,b) -> a,TreeMap::new)); //animes
        animeViewCountMap = new HashMap<>();

        animeEntries.forEach(a -> {
            Long animeId = a.getMalId();
            Integer count = 1;

            if(animeViewCountMap.containsKey(animeId)){
                count += animeViewCountMap.get(animeId);
            }

            animeViewCountMap.put(animeId, count);
        });

    }
}
