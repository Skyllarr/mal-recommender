package cz.muni.fi.pv254.data.entity;

import cz.muni.fi.pv254.data.AnimeEntry;
import cz.muni.fi.pv254.data.enums.Gender;
import cz.muni.fi.pv254.dataUtils.JsonParser;
import cz.muni.fi.pv254.utils.Utils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Created by suomiy on 12/2/15.
 */
@Entity
@Table(name = "public.user")
@Getter
@Setter
public class DbRemoteUser extends  IdEntity{

    private String address;
    @Column(name = "user_agent")
    private String userAgent;
    @Column(name = "last_seen")
    private OffsetDateTime lastSeen;
    @Column(name = "visits_count")
    private Long visitsCount;
    @Column(name = "anime_entries")
    private String animeEntries;

    public DbRemoteUser() {
        super();
    }

    public DbRemoteUser(String address, String userAgent, OffsetDateTime lastSeen, Long visitsCount) {
        super();
        setAddress(address);
        setUserAgent(userAgent);
        this.lastSeen = lastSeen;
        this.visitsCount = visitsCount;
    }
    public void setAddress(String address) {
        this.address = Utils.removeEmptyStr(address);
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = Utils.removeEmptyStr(userAgent);
    }
    public void setAnimeEntries(String animeEntries) {
        this.animeEntries = Utils.removeEmptyStr(animeEntries);
    }

    public List<AnimeEntry> getAnimeEntriesAsList() {
        return JsonParser.loadAsAnimeEntry(animeEntries);
    }

    public void setAnimeEntriesAsString(List<AnimeEntry> animeEntries) {
        setAnimeEntries(JsonParser.save(animeEntries));
    }

    @Override
    public String toString() {
        return String.format("Remote User - id:  %d, address: %s, lastSeen: %s",
                getId(), address , lastSeen );
    }
}
