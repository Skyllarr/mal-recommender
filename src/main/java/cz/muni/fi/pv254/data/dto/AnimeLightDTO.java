package cz.muni.fi.pv254.data.dto;

import cz.muni.fi.pv254.data.Anime;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by suomiy on 12/2/15.
 */
@Getter
@Setter
public class AnimeLightDTO {
    private String title;
    private Long malId;
    private boolean deleted;

    public AnimeLightDTO(Anime anime) {
        title = anime.getTitle();
        malId = anime.getMalId();
        deleted = anime.isDeleted();
    }
}