package cz.muni.fi.pv254.repository;

/**
 * Created by suomiy on 11/7/15.
 */

import com.mysema.query.jpa.impl.JPAQuery;
import cz.muni.fi.pv254.entity.DbAnime;
import cz.muni.fi.pv254.entity.QDbAnime;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class DbAnimeRepository extends Repository<DbAnime> {
    public DbAnimeRepository() {
        super();
    }

    @Inject
    public DbAnimeRepository(EntityManager em) {
        super(em, DbAnime.class, QDbAnime.dbAnime);
    }

    public DbAnime findByMalId(Long malId) {
        QDbAnime dbAnime = QDbAnime.dbAnime;

        List<DbAnime> dbAnimes = new JPAQuery(em)
                .from(dbAnime)
                .where(dbAnime.malId.eq(malId))
                .list(dbAnime);

        return dbAnimes.size() == 0 ? null : dbAnimes.get(0);
    }
}
