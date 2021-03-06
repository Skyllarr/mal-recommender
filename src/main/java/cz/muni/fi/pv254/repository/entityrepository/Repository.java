package cz.muni.fi.pv254.repository.entityrepository;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.path.EntityPathBase;
import cz.muni.fi.pv254.data.User;
import cz.muni.fi.pv254.data.entity.IdEntity;
import cz.muni.fi.pv254.dataUtils.DataStore;
import cz.muni.fi.pv254.init.Setup;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import java.util.ArrayList;
import java.util.List;

public abstract class Repository<E extends IdEntity> {

    protected EntityManager em;
    protected Class<E> entityClass;
    protected EntityPathBase<E> entityPathBase;

    @Inject
    DataStore dataStore;

    private final Long maxResultCount;

    private final boolean forbidEntitiesUpdates;


    public Repository() {
        maxResultCount = null;
        forbidEntitiesUpdates = false;
    }

    protected Repository(EntityManager em, Long maxResultCount, boolean forbidEntitiesUpdates, final Class<E> entityClass, final EntityPathBase<E> entityPathBase) {
        this.em = em;
        this.entityClass = entityClass;
        this.entityPathBase = entityPathBase;
        if( maxResultCount != null && maxResultCount < 0){
            throw new IllegalArgumentException("maxResultCount is negative number!");
        }
        this.maxResultCount = maxResultCount;
        this.forbidEntitiesUpdates = forbidEntitiesUpdates;
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
        if(forbidEntitiesUpdates){
            throw new UnsupportedOperationException("Cannot update entity when forbidEntitiesUpdates == true ");
        }

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
        return maxResultCount == null ?  from().list(entityPathBase) : from().limit(maxResultCount).list(entityPathBase);
    }


    protected JPQLQuery from() {
        return new JPAQuery(em).from(entityPathBase);
    }

    public List<E> batchCreate(List<E> entities) throws Exception {
        return batchUpdate(entities, true);
    }

    public List<E> batchUpdate(List<E> entities) {
        if(forbidEntitiesUpdates){
            throw new UnsupportedOperationException("Cannot update entities when forbidEntitiesUpdates == true ");
        }

        try {
            return batchUpdate(entities, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<E> batchUpdate(List<E> entities, boolean create) throws Exception {
        final List<E> savedEntities = new ArrayList<>(entities.size());
        em.setFlushMode(FlushModeType.COMMIT);
        for (E entity : entities) {
            if (entity.getId() != null) {
                if(create){
                    throw new Exception("Create: Already exists");
                }
                em.merge(entity);
            }else{
                em.persist(entity);
            }
        }
        em.flush();
        em.clear();
        em.setFlushMode(FlushModeType.AUTO);
        return savedEntities;
    }

}
