package cz.muni.fi.pv254.data;

import com.google.gson.annotations.SerializedName;
import cz.muni.fi.pv254.data.enums.AnimeEntryStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;

/**
 * Created by suomiy on 11/21/15.
 */
@Getter
@Setter
public class AnimeEntry implements MAL{

    @SerializedName("m")
    private long malId;
    @SerializedName("s")
    private long score;
    @SerializedName("n")
    private double normalizedScore;
    @SerializedName("t")
    private AnimeEntryStatus status;

    public AnimeEntry() {
    }

    public AnimeEntry(Long malId, Long score, AnimeEntryStatus status) {
        super();
        this.malId = malId;
        this.score = score;
        this.status = status;
    }

    @Override
    public void setMalId(Long malId){
        this.malId = malId;
    }

    @Override
    public Long getMalId(){
        return malId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnimeEntry || o instanceof Anime)) return false;

        MAL entity = (MAL) o;

        return entity.getMalId() != null && malId == entity.getMalId();
    }

    @Override
    public int hashCode() {
        return (int) malId;
    }

    @Override
    public String toString() {
        return String.format("AnimeEntry - dbAnime's mal id: %s, normalized score: %s, score: %d, status %s",
                malId,
                normalizedScore,
                score,
                status == null ? "null" : status.name());
    }
}
