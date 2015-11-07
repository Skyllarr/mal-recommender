package cz.muni.fi.pv254.repository;

/**
 * Created by suomiy on 11/7/15.
 */

import cz.muni.fi.pv254.entity.MalUser;
import cz.muni.fi.pv254.entity.QMalUser;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;


/**
 * Created by John on 10.7.2015.
 */

@ApplicationScoped
@Transactional
public class MalUserRepository extends Repository<MalUser> {
    public MalUserRepository() {
        super();
    }

    @Inject
    public MalUserRepository(EntityManager em) {
        super(em, MalUser.class, QMalUser.malUser);
    }
}
