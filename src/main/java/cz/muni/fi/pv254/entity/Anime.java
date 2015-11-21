package cz.muni.fi.pv254.entity;

import cz.muni.fi.pv254.enums.AnimeType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by skylar on 20.11.15.
 */
@Entity
@Table(name = "mal_anime")
@Getter
@Setter
public class Anime extends IdEntity{

    private String title;
    @Column(name = "image_link")
    private String imageLink;
    @Column(name = "mal_id")
    private Long malId;
    private Long episodes;
    private AnimeType type;

    public Anime() {
        super();
    }

    public Anime(String title, String imageLink, Long malId, Long episodes, AnimeType type) {
        super();
        this.title = title;
        this.imageLink = imageLink;
        this.malId = malId;
        this.episodes = episodes;
        this.type = type;
    }

    public String toString() {
        return String.format("Anime - title: %s, imageLink %s, malId: %d, episodes: %d, type %s",
                title, imageLink, malId, episodes, type == null ? "null" : type.name());
    }
}