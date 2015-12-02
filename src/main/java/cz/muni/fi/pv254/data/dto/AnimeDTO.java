package cz.muni.fi.pv254.data.dto;

import cz.muni.fi.pv254.data.Anime;
import cz.muni.fi.pv254.data.enums.AnimeType;
import cz.muni.fi.pv254.data.enums.Genre;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by suomiy on 12/2/15.
 */
@Getter
@Setter
public class AnimeDTO {
    private String title;
    private String imageLink;
    private Long malId;
    private List<Genre> genreEntries;
    private Long episodes;
    private AnimeType type;
    private Long popularity;
    private String description;
    private boolean deleted;
    private Double recommendationValue;

    public AnimeDTO(Anime anime) {
        setFomAnime(anime);
    }

    public AnimeDTO(Anime anime, Double recommendationValue) {
        setFomAnime(anime);
        this.recommendationValue = recommendationValue;
    }


    private void setFomAnime(Anime anime) {
        title = anime.getTitle();
        imageLink = anime.getImageLink();
        malId = anime.getMalId();
        genreEntries = anime.getGenres();
        episodes = anime.getEpisodes();
        type = anime.getType();
        popularity = anime.getPopularity();
        description = anime.getDescription();
        deleted = anime.isDeleted();
    }


}