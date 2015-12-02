package cz.muni.fi.pv254.repository.entityrepository;

/**
 * Created by suomiy on 11/7/15.
 */

import com.mysema.query.jpa.impl.JPAQuery;
import cz.muni.fi.pv254.data.entity.DbUser;
import cz.muni.fi.pv254.data.entity.QDbUser;
import cz.muni.fi.pv254.init.Setup;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class DbUserRepository extends Repository<DbUser> {

    private static final Long maxResultCount = Setup.userMaxResultCount;

    public DbUserRepository() {
        super();
    }

    @Inject
    public DbUserRepository(EntityManager em) {
        super(em, maxResultCount, Setup.forbidEntitiesUpdates, DbUser.class, QDbUser.dbUser);
    }

    @Override
    public List<DbUser> findAll() {
        QDbUser dbUser = QDbUser.dbUser;
        JPAQuery query = new JPAQuery(em).from(dbUser).where(dbUser.deleted.isFalse());

        return maxResultCount == null ?  query.list(dbUser) : query.limit(maxResultCount).list(dbUser);
    }

    public DbUser findByName(String name) {
        QDbUser dbUser = QDbUser.dbUser;

        List<DbUser> dbUsers = new JPAQuery(em)
                .from(dbUser)
                .where(dbUser.name.eq(name))
                .list(dbUser);

        return dbUsers.size() == 0 ? null : dbUsers.get(0);
    }

    public DbUser findByMalId(Long malId) {
        QDbUser dbUser = QDbUser.dbUser;

        List<DbUser> dbUsers = new JPAQuery(em)
                .from(dbUser)
                .where(dbUser.malId.eq(malId))
                .list(dbUser);

        return dbUsers.size() == 0 ? null : dbUsers.get(0);
    }
}
