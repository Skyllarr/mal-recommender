package cz.muni.fi.pv254.data;

import com.google.gson.annotations.SerializedName;
import cz.muni.fi.pv254.data.enums.AnimeEntryStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by suomiy on 11/21/15.
 */
@Getter
@Setter
public class AnimeEntry implements MAL{

    @SerializedName("m")
    private Long malId;
    @SerializedName("s")
    private Long score;
    @SerializedName("n")
    private Double normalizedScore;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnimeEntry || o instanceof Anime)) return false;

        MAL entity = (MAL) o;

        return malId != null && entity.getMalId() != null && malId.equals(entity.getMalId());
    }

    @Override
    public int hashCode() {
        return (malId != null) ? malId.intValue() : 0;
    }

    @Override
    public String toString() {
        return String.format("AnimeEntry - dbAnime's mal id: %s, score: %d, status %s",
                malId,
                score,
                status == null ? "null" : status.name());
    }
}
