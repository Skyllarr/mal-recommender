package cz.muni.fi.pv254.dataUtils;

import cz.muni.fi.pv254.entity.AnimeEntry;
import cz.muni.fi.pv254.entity.DbAnime;
import cz.muni.fi.pv254.entity.DbUser;
import cz.muni.fi.pv254.repository.DbAnimeRepository;
import cz.muni.fi.pv254.repository.DbUserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by skylar on 23.11.15.
 */
@ApplicationScoped
public class DataQueries {

    @Inject
    DbUserRepository dbUserRepository;
    @Inject
    DbAnimeRepository dbAnimeRepository;

    private List<DbUser> dbUsers;
    private List<DbAnime> dbAnimes;

    public void DataQueries() {
        dbUsers = dbUserRepository.findAll();
        dbAnimes = dbAnimeRepository.findAll();
    }

    public List<AnimeEntry> getAnimeEntries() {
        List<AnimeEntry>  entries = new ArrayList<>();
        for(DbUser dbUser : dbUsers){
            List<AnimeEntry> list =  dbUser.getAnimeEntriesAsList();
            entries.addAll(list);
        }
        return entries;
    }

    public List<AnimeEntry> getAnimeEntriesWithScore() {
        return filterUnscoredEntries(getAnimeEntries());
    }

    public Map<DbUser, List<AnimeEntry>> getUsersWithEntries() {
        Map<DbUser, List<AnimeEntry>> usersEntries = new HashMap<>();
        for(DbUser dbUser : dbUsers){
            List<AnimeEntry> list =  dbUser.getAnimeEntriesAsList();
            usersEntries.put(dbUser, list);
        }
        return usersEntries;
    }

    public Map<DbUser, List<AnimeEntry>> getUsersWithEntriesWithScore() {
        Map<DbUser, List<AnimeEntry>> usersEntries = new HashMap<>();
        for(DbUser dbUser : dbUsers){
            List<AnimeEntry> list =  filterUnscoredEntries(dbUser.getAnimeEntriesAsList());
            usersEntries.put(dbUser, list);
        }
        return usersEntries;
    }

    public List<AnimeEntry> filterUnscoredEntries(List<AnimeEntry> entries){
        return entries.stream()
                .filter(a -> a.getScore() > 0)
                .collect(Collectors.toList());
    }
}
