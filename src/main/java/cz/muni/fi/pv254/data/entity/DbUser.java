package cz.muni.fi.pv254.data.entity;

import cz.muni.fi.pv254.data.AnimeEntry;
import cz.muni.fi.pv254.data.MAL;
import cz.muni.fi.pv254.data.enums.Gender;
import cz.muni.fi.pv254.dataUtils.JsonParser;
import cz.muni.fi.pv254.utils.Utils;
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
public class DbUser extends  IdEntity implements MAL {

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
        setName(name);
        this.malId = malId;
        this.birthday = birthday;
    }

    public void setAnimeEntries(String animeEntries) {
        this.animeEntries = Utils.removeEmptyStr(animeEntries);
    }

    public void setName(String name) {
        this.name = Utils.removeEmptyStr(name);
    }

    public List<AnimeEntry> getAnimeEntriesAsList() {
        return JsonParser.loadAsAnimeEntry(animeEntries);
    }

    public void setAnimeEntriesAsString(List<AnimeEntry> animeEntries) {
        setAnimeEntries(JsonParser.save(animeEntries));
    }

    @Override
    public String toString() {
        return String.format("User - id:  %d, name: %s, malId: %d, gender: %s, birthday: %s",
                getId(), name , malId, gender == null ? "null" : gender.name(), birthday);
    }

}
