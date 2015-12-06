package cz.muni.fi.pv254.data.entity;

import cz.muni.fi.pv254.data.Anime;
import cz.muni.fi.pv254.data.MAL;
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
 * Created by suomiy on 12/5/15.
 */
@Entity
@Table(name = "mal_anime")
@Getter
@Setter
public class AnimeDescriptionVector extends IdEntity implements MAL {

    @Column(name = "mal_id")
    private Long malId;

    @Column(name = "description_similarity_vector")
    private String descriptionSimilarityVector;

    public AnimeDescriptionVector() {
        super();
    }

    public AnimeDescriptionVector(Anime anime, List<Float> descriptionSimilarityVector) {
        super();
        this.malId = anime.getMalId();
        this.setId(anime.getId());
        this.setDeleted(anime.isDeleted());
        setDescriptionSimilarityVectorAsString(descriptionSimilarityVector);
    }

    public void setDescriptionSimilarityVector(String descriptionSimilarityVector) {
        this.descriptionSimilarityVector = Utils.removeEmptyStr(descriptionSimilarityVector);
    }


    public List<Float> getDescriptionSimilarityVectorAsList() {
        return JsonParser.loadAsDescriptionSimilarityVector(descriptionSimilarityVector);
    }

    public void setDescriptionSimilarityVectorAsString(List<Float> descriptionSimilarityVector) {
        setDescriptionSimilarityVector(JsonParser.save(descriptionSimilarityVector));
    }

    @Override
    public String toString() {
        return String.format("Anime - malId %d", malId);
    }

}
