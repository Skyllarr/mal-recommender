package cz.muni.fi.pv254.data.entity;

import com.mysema.commons.lang.Pair;
import cz.muni.fi.pv254.data.MAL;
import cz.muni.fi.pv254.data.enums.AnimeType;
import cz.muni.fi.pv254.data.enums.Genre;
import cz.muni.fi.pv254.dataUtils.JsonParser;
import cz.muni.fi.pv254.utils.Utils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collections;
import java.util.List;

/**
 * Created by skylar on 20.11.15.
 */
@Entity
@Table(name = "mal_anime")
@Getter
@Setter
public class DbAnime extends IdEntity implements MAL {

    private String title;
    @Column(name = "image_link")
    private String imageLink;
    @Column(name = "mal_id")
    private Long malId;
    @Column(name = "genre_entries")
    private String genreEntries;
    @Column(name = "difference_vector")
    private String differenceVector;
    private Long episodes;
    private AnimeType type;
    private Long popularity;
    private Long ranked;
    private String description;

    public DbAnime() {
        super();
    }

    public DbAnime(String title, String imageLink, Long malId, Long episodes, AnimeType type) {
        super();
        setTitle(title);
        setImageLink(imageLink);
        this.malId = malId;
        this.episodes = episodes;
        this.type = type;
    }

    public void setGenreEntries(String genreEntries) {
        this.genreEntries = Utils.removeEmptyStr(genreEntries);
    }

    public void setDifferenceVector(String differenceVector) {
        this.differenceVector = Utils.removeEmptyStr(differenceVector);
    }


    public void setImageLink(String imageLink) {
        this.imageLink = Utils.removeEmptyStr(imageLink);
    }

    public void setTitle(String title) {
        this.title = Utils.removeEmptyStr(title);
    }


    public List<Genre> getGenreEntriesAsList() {
        return JsonParser.loadAsGenre(genreEntries);
    }

    public void setGenreEntriesAsString(List<Genre> genre) {
        setGenreEntries(JsonParser.save(genre));
    }

    public List<Pair<Integer, Double>> getDifferenceVectorAsList() {
        return JsonParser.loadAsDifferenceVector(differenceVector);
    }

    public void setDifferenceVectorAsString(List<Pair<Integer, Double>> differenceVector) {
        setDifferenceVector(JsonParser.save(differenceVector));
    }

    @Override
    public String toString() {
        return String.format("Anime - title %s, imageLink %s, malId %d, episodes %d, type %s, popularity %d, ranked %d",
                title, imageLink, malId, episodes, type == null ? "null" : type.name(), popularity, ranked);
    }
}