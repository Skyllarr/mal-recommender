package cz.muni.fi.pv254.entity;

import cz.muni.fi.pv254.dataUtils.JsonParser;
import cz.muni.fi.pv254.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by suomiy on 11/7/15.
 */
@Entity
@Table(name = "mal_user")
@Getter
@Setter
public class DbUser extends  IdEntity{

    private String name;
    @Column(name = "mal_id")
    private Long malId;
    private LocalDate birthday;
    private Gender gender;
    @Column(name = "anime_entries")
    private String animeEntries;

    public DbUser() {
        super();
    }

    public DbUser(String name, Long malId, LocalDate birthday) {
        super();
        this.name = name;
        this.malId = malId;
        this.birthday = birthday;
    }

    public List<AnimeEntry> getAnimeEntriesAsList() {
        return JsonParser.loadAsAnimeEntry(animeEntries);
    }

    public void setAnimeEntriesAsString(List<AnimeEntry> animeEntries) {
        this.animeEntries = JsonParser.save(animeEntries);
    }

    public String toString() {
        return String.format("DbUser - id:  %d, name: %s, malId: %d, birthday: %s",getId(), name , malId, birthday);
    }

}
