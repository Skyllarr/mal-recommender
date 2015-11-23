package cz.muni.fi.pv254.dataUtils;

import cz.muni.fi.pv254.entity.DbAnime;
import cz.muni.fi.pv254.repository.DbAnimeRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import cz.muni.fi.pv254.repository.AnimeEntryRepository;

/**
 * Created by suomiy on 11/21/15.
 */
public class AnimeCacher {

    private DbAnimeRepository dbAnimeRepository;

    final List<DbAnime> dbAnimeBatch = new ArrayList<>();

    private static final Map<Long , DbAnime> animeMap =  new HashMap<>();

    public AnimeCacher(DbAnimeRepository dbAnimeRepository) {
        this.dbAnimeRepository = dbAnimeRepository;

        dbAnimeRepository.findAll().stream().forEach(a ->  animeMap.put(a.getMalId(), a));
    }

    public DbAnime findByMalId(Long malId) {
        return animeMap.get(malId);
    }

    public void create(DbAnime dbAnime){
        animeMap.put(dbAnime.getMalId(), dbAnime);
        dbAnimeBatch.add(dbAnime);
    }

    public void flush(){
        try {
            dbAnimeRepository.batchCreate(dbAnimeBatch);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dbAnimeBatch.clear();
    }
}


