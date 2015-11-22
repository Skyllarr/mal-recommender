package cz.muni.fi.pv254.repository;

/**
 * Created by suomiy on 11/7/15.
 */

import com.mysema.query.jpa.impl.JPAQuery;
import cz.muni.fi.pv254.entity.AnimeEntry;
import cz.muni.fi.pv254.entity.QUser;
import cz.muni.fi.pv254.entity.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class UserRepository extends Repository<User> {
    public UserRepository() {
        super();
    }

    @Inject
    public UserRepository(EntityManager em) {
        super(em, User.class, QUser.user);
    }

    public User findByName(String name) {
        QUser user = QUser.user;

        List<User> users = new JPAQuery(em)
                .from(user)
                .where(user.name.eq(name))
                .list(user);

        return users.size() == 0 ? null : users.get(0);
    }
}
