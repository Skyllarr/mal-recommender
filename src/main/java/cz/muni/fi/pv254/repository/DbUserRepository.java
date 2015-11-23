package cz.muni.fi.pv254.repository;

/**
 * Created by suomiy on 11/7/15.
 */

import com.mysema.query.jpa.impl.JPAQuery;
import cz.muni.fi.pv254.entity.DbUser;
import cz.muni.fi.pv254.entity.QDbUser;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class DbUserRepository extends Repository<DbUser> {

    private final int startId = 1048;

    public DbUserRepository() {
        super();
    }

    @Inject
    public DbUserRepository(EntityManager em) {
        super(em, DbUser.class, QDbUser.dbUser);
    }


    public DbUser findByName(String name) {
        QDbUser dbUser = QDbUser.dbUser;

        List<DbUser> dbUsers = new JPAQuery(em)
                .from(dbUser)
                .where(dbUser.name.eq(name))
                .list(dbUser);

        return dbUsers.size() == 0 ? null : dbUsers.get(0);
    }

    public List<DbUser> find(int maxCount) {
        QDbUser dbUser = QDbUser.dbUser;
        final int stopValue = maxCount + startId - 1;
        return new JPAQuery(em)
                .from(dbUser)
                .where(dbUser.id.between(1, stopValue))
                .list(dbUser);
    }
}
