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
public class UserEntryDTO {
    private Long malId;
    private Long score;

    public UserEntryDTO() {
    }
}
