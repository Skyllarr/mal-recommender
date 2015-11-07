package cz.muni.fi.pv254.entity;

//import org.hibernate.annotations.BatchSize;
import javax.persistence.*;

/**
 * Created suomiy on 11/7/15.
 */
//@BatchSize(size = 20)
@Entity
@Table(name = "user")
public class MalUser extends  IdEntity{

    private String name;


}
