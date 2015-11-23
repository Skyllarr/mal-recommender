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
    //private AnimeEntryRepository animeEntryRepository;

    final List<Anime> animeBatch = new ArrayList<>();
    final List<AnimeEntry> animeEntryBatch = new ArrayList<>();

    private static final Map<Long , Anime> animeMap =  new HashMap<>();

   /* public AnimeCacher(AnimeRepository animeRepository,AnimeEntryRepository animeEntryRepository) {
        this.animeRepository = animeRepository;
        this.animeEntryRepository = animeEntryRepository;

        animeRepository.findAll().stream().forEach(a ->  animeMap.put(a.getMalId(), a));
    }*/

    public Anime findByMalId(Long malId) {
        return animeMap.get(malId);
    }

    public void create(Anime anime){
        animeMap.put(anime.getMalId(), anime);
        animeBatch.add(anime);
    }

    public void create(AnimeEntry animeEntry){
        animeEntryBatch.add(animeEntry);
    }

    public void flush(){
        try {
            animeRepository.batchCreate(animeBatch);
           // animeEntryRepository.batchCreate(animeEntryBatch);
        } catch (Exception e) {
            e.printStackTrace();
        }
        animeBatch.clear();
        animeEntryBatch.clear();
    }
}


