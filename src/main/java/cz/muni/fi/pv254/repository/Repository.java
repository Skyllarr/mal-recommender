package cz.muni.fi.pv254.repository;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.path.EntityPathBase;
import cz.muni.fi.pv254.entity.IdEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.StatelessSession;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import java.util.ArrayList;
import java.util.List;

public abstract class Repository<E extends IdEntity> {

    protected EntityManager em;
    protected Class<E> entityClass;
    protected EntityPathBase<E> entityPathBase;

    public Repository() {
    }

    protected Repository(EntityManager em, final Class<E> entityClass, final EntityPathBase<E> entityPathBase) {
        this.em = em;
        this.entityClass = entityClass;
        this.entityPathBase = entityPathBase;
    }

    public E create(final E entity) throws Exception {
        if (entity.getId() != null) {
            throw new Exception("Create: Already exists");
        }
        em.persist(entity);
        return entity;
    }

    public E findOne(final Long id) {
        return em.find(entityClass, id);
    }

    public E update(final E entity) {
        return em.merge(entity);
    }

    public E delete(final Long id) throws Exception {
        E entity = findOne(id);
        if (entity == null) {
            throw new Exception("Delete: Id " + id.toString() + " not found");
        }
        em.remove(entity);
        return entity;
    }

    public List<E> findAll() {
        return from().list(entityPathBase);
    }

    protected JPQLQuery from() {
        return new JPAQuery(em).from(entityPathBase);
    }

    public List<E> batchCreate(List<E> entities) throws Exception {
        final List<E> savedEntities = new ArrayList<>(entities.size());
        em.setFlushMode(FlushModeType.COMMIT);
        for (E entity : entities) {
            if (entity.getId() != null) {
                throw new Exception("Create: Already exists");
            }
            em.persist(entity);
        }
        em.flush();
        em.clear();
        em.setFlushMode(FlushModeType.AUTO);
        return savedEntities;
    }

}
