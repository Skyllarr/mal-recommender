package cz.muni.fi.pv254.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by suomiy on 12/2/15.
 */
@Getter
@Setter
public class AnimeLightListDTO {
    private List<AnimeLightDTO> animes;

    public AnimeLightListDTO(List<AnimeLightDTO> animes) {
        this.animes = animes;
    }
}