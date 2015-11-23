package cz.muni.fi.pv254.entity;

import com.google.gson.annotations.SerializedName;
import cz.muni.fi.pv254.enums.AnimeEntryStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by suomiy on 11/21/15.
 */
@Getter
@Setter
public class AnimeEntry{

    @SerializedName("m")
    private Long malAnimeId;

    @SerializedName("s")
    private Long score;
    @SerializedName("n")
    private Double normalizedScore;
    @SerializedName("t")
    private AnimeEntryStatus status;

    public AnimeEntry() {
        super();
    }

    public AnimeEntry(Long malAnimeId, Long score, AnimeEntryStatus status) {
        super();
        this.malAnimeId = malAnimeId;
        this.score = score;
        this.status = status;
    }

    public String toString() {
        return String.format("AnimeEntry - anime's mal id: %s, score: %d, status %s",
                malAnimeId,
                score,
                status == null ? "null" : status.name());
    }
}
