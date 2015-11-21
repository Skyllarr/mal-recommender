package cz.muni.fi.pv254.repository;

/**
 * Created by suomiy on 11/7/15.
 */

import cz.muni.fi.pv254.entity.Anime;
import cz.muni.fi.pv254.entity.AnimeEntry;
import cz.muni.fi.pv254.entity.QAnime;
import cz.muni.fi.pv254.entity.QAnimeEntry;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class AnimeEntryRepository extends Repository<AnimeEntry> {
    public AnimeEntryRepository() {
        super();
    }

    @Inject
    public AnimeEntryRepository(EntityManager em) {
        super(em, AnimeEntry.class, QAnimeEntry.animeEntry);
    }
}
