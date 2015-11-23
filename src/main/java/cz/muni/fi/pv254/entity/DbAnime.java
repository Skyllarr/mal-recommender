package cz.muni.fi.pv254.entity;

import cz.muni.fi.pv254.dataUtils.JsonParser;
import cz.muni.fi.pv254.enums.AnimeType;
import cz.muni.fi.pv254.enums.Genre;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by skylar on 20.11.15.
 */
@Entity
@Table(name = "mal_anime")
@Getter
@Setter
public class DbAnime extends IdEntity{

    private String title;
    @Column(name = "image_link")
    private String imageLink;
    @Column(name = "mal_id")
    private Long malId;
    @Column(name = "genre_entries")
    private String genreEntries;
    private Long episodes;
    private AnimeType type;
    private Long popularity;
    private Long ranked;

    public DbAnime() {
        super();
    }

    public DbAnime(String title, String imageLink, Long malId, Long episodes, AnimeType type) {
        super();
        this.title = title;
        this.imageLink = imageLink;
        this.malId = malId;
        this.episodes = episodes;
        this.type = type;
    }

    public List<Genre> getGenreEntriesAsList() {
        return JsonParser.loadAsGenre(genreEntries);
    }

    public void setGenreEntriesAsString(List<Genre> genre) {
        this.genreEntries = JsonParser.save(genre);
    }

    public String toString() {
        return String.format("DbAnime - title: %s, imageLink %s, malId: %d, episodes: %d, type %s",
                title, imageLink, malId, episodes, type == null ? "null" : type.name());
    }
}