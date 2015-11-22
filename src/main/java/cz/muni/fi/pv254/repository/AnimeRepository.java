package cz.muni.fi.pv254.repository;

/**
 * Created by suomiy on 11/7/15.
 */

import com.mysema.query.jpa.impl.JPAQuery;
import cz.muni.fi.pv254.entity.Anime;
import cz.muni.fi.pv254.entity.QAnime;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

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

    public Anime findByMalId(Long malId) {
        QAnime anime = QAnime.anime;

        List<Anime> animes = new JPAQuery(em)
                .from(anime)
                .where(anime.malId.eq(malId))
                .list(anime);

        return animes.size() == 0 ? null : animes.get(0);
    }
}
