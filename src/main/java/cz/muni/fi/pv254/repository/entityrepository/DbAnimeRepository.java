package cz.muni.fi.pv254.repository.entityrepository;

/**
 * Created by suomiy on 11/7/15.
 */

import com.mysema.query.jpa.impl.JPAQuery;
import cz.muni.fi.pv254.data.entity.DbAnime;
import cz.muni.fi.pv254.data.entity.QDbAnime;
import cz.muni.fi.pv254.init.Setup;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class DbAnimeRepository extends Repository<DbAnime> {

    private static final Long maxResultCount = Setup.animeMaxResultCount;

    public DbAnimeRepository() {
        super();
    }

    @Inject
    public DbAnimeRepository(EntityManager em) {
        super(em, maxResultCount, DbAnime.class, QDbAnime.dbAnime);
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