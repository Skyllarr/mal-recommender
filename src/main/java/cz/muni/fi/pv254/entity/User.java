package cz.muni.fi.pv254.entity;

import cz.muni.fi.pv254.enums.Gender;
import cz.muni.fi.pv254.utils.JsonParser;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Created by suomiy on 11/7/15.
 */
@Entity
@Table(name = "mal_user")
@Getter
@Setter
public class User extends  IdEntity{

    private String name;
    @Column(name = "mal_id")
    private Long malId;
    private LocalDate birthday;
    private Gender gender;
    @Column(name = "anime_entries")
    private String animeEntries;

    public User() {
        super();
    }

    public User(String name, Long malId, LocalDate birthday) {
        super();
        this.name = name;
        this.malId = malId;
        this.birthday = birthday;
    }

    public List<AnimeEntry> getAnimeEntriesAsList() {
        return JsonParser.load(animeEntries);
    }

    public void setAnimeEntriesAsString(List<AnimeEntry> animeEntries) {
        this.animeEntries = JsonParser.save(animeEntries);
    }

    public String toString() {
        return String.format("User - name: %s, malId: %d, birthday: %s, animeEntries: %s", name , malId, birthday,
                animeEntries == null ? "null" : animeEntries.length());
    }

}
