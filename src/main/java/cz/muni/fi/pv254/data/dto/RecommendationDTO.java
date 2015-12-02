package cz.muni.fi.pv254.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by suomiy on 12/2/15.
 */
@Getter
@Setter
public class RecommendationDTO {
    private List<AnimeDTO> slopeOneList;
    private List<AnimeDTO> slopeOneWeirdList;
    private List<AnimeDTO> tfIdfList;
    private List<AnimeDTO> randomList;

    private String slopeOneListMessage;
    private String slopeOneWeirdListMessage;
    private String tfIdfListMessage;


    public RecommendationDTO() {
    }
}