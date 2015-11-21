package cz.muni.fi.pv254.repository;

/**
 * Created by suomiy on 11/7/15.
 */

import cz.muni.fi.pv254.entity.Anime;
import cz.muni.fi.pv254.entity.QAnime;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
@Transactional
public class AnimeRepository extends Repository<Anime> {
    public AnimeRepository() {
        super();
    }

    @Inject
    public AnimeRepository(EntityManager em) {
        super(em, Anime.class, QAnime.anime);
    }
}
