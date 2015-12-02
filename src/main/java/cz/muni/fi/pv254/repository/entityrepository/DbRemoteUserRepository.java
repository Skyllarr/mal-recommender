package cz.muni.fi.pv254.repository.entityrepository;

/**
 * Created by suomiy on 12/2/15.
 */

import com.mysema.query.jpa.impl.JPAQuery;
import cz.muni.fi.pv254.data.entity.DbRemoteUser;
import cz.muni.fi.pv254.data.entity.DbUser;
import cz.muni.fi.pv254.data.entity.QDbRemoteUser;
import cz.muni.fi.pv254.data.entity.QDbUser;
import cz.muni.fi.pv254.init.Setup;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class DbRemoteUserRepository extends Repository<DbRemoteUser> {


    public DbRemoteUserRepository() {
        super();
    }

    @Inject
    public DbRemoteUserRepository(EntityManager em) {
        super(em, null, false, DbRemoteUser.class, QDbRemoteUser.dbRemoteUser);
    }


    public DbRemoteUser findByAddress(String address) {
        QDbRemoteUser dbRemoteUser = QDbRemoteUser.dbRemoteUser;

        List<DbRemoteUser> dbRemoteUsers = new JPAQuery(em)
                .from(dbRemoteUser).limit(1)
                .where(dbRemoteUser.address.eq(address))
                .list(dbRemoteUser);

        return dbRemoteUsers.size() == 0 ? null : dbRemoteUsers.get(0);
    }

}
