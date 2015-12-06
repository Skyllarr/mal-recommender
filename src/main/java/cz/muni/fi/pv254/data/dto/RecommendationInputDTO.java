package cz.muni.fi.pv254.data.dto;

import cz.muni.fi.pv254.data.AnimeEntry;
import cz.muni.fi.pv254.data.enums.Genre;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * Created by suomiy on 12/2/15.
 */
@Getter
@Setter
public class RecommendationInputDTO {
    private List<AnimeEntry> entries;
    private Map<Genre, Boolean> genres;

    public RecommendationInputDTO() {
    }
}