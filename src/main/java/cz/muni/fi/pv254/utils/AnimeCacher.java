package cz.muni.fi.pv254.utils;

import cz.muni.fi.pv254.entity.Anime;
import cz.muni.fi.pv254.entity.AnimeEntry;
//import cz.muni.fi.pv254.repository.AnimeEntryRepository;
import cz.muni.fi.pv254.repository.AnimeRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by suomiy on 11/21/15.
 */
public class AnimeCacher {

    private AnimeRepository animeRepository;

    final List<Anime> animeBatch = new ArrayList<>();

    private static final Map<Long , Anime> animeMap =  new HashMap<>();

    public AnimeCacher(AnimeRepository animeRepository) {
        this.animeRepository = animeRepository;

        animeRepository.findAll().stream().forEach(a ->  animeMap.put(a.getMalId(), a));
    }

    public Anime findByMalId(Long malId) {
        return animeMap.get(malId);
    }

    public void create(Anime anime){
        animeMap.put(anime.getMalId(), anime);
        animeBatch.add(anime);
    }

    public void flush(){
        try {
            animeRepository.batchCreate(animeBatch);
        } catch (Exception e) {
            e.printStackTrace();
        }
        animeBatch.clear();
    }
}


