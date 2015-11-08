package cz.muni.fi.pv254.entity;

//import org.hibernate.annotations.BatchSize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;

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

    @Column(name = "last_seen")
    private OffsetDateTime lastSeen;

    private LocalDate birthday;
}
