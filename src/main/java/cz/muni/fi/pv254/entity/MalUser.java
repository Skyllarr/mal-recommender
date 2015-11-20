package cz.muni.fi.pv254.entity;

//import org.hibernate.annotations.BatchSize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;

/**
 * Created suomiy on 11/7/15.
 */
//@BatchSize(size = 20)
@Entity
@Table(name = "mal_user")
@Getter
@Setter
public class MalUser extends  IdEntity{

    private String name;
    private int user_id;

    //private Set<Anime> anime;

    @Column(name = "last_seen")
    private OffsetDateTime lastSeen;

    private LocalDate birthday;

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("User Details - ");
        sb.append("Name:" + getName());
        sb.append(", ");
        sb.append("Id:" + getId());
        sb.append(", ");

        return sb.toString();
    }
}
