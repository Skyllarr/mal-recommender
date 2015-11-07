package cz.muni.fi.pv254.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Represents base for all reasonable entities with Long Id.
 */
@MappedSuperclass
public abstract class IdEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private boolean deleted = false;

    protected IdEntity() {
    }

    protected IdEntity(IdEntity entity) {
        this.id = entity.id;
    }

    /**
     * Get the id of entity
     *
     * @return id of entity
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IdEntity)) return false;

        IdEntity entity = (IdEntity) o;

        return id != null && entity.id != null && id.equals(entity.id);
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return id.intValue();
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "IdEntity{" +
                "id=" + id +
                '}';
    }
}
