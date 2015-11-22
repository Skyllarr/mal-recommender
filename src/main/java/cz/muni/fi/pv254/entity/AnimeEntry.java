package cz.muni.fi.pv254.entity;

import cz.muni.fi.pv254.enums.AnimeEntryStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

/**
 * Created by suomiy on 11/21/15.
 */
@Entity
@Table(name = "mal_anime_entry")
@Getter
@Setter
public class AnimeEntry extends IdEntity{

    @JoinColumn(name = "anime_id")
    @Fetch(FetchMode.SELECT)
    @ManyToOne(fetch = FetchType.LAZY)
    private Anime anime;

    @JoinColumn(name = "user_id")
    @Fetch(FetchMode.SELECT)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private Long score;
    @Column(name = "normalized_score")
    private Double normalizedScore;
    private AnimeEntryStatus status;

    public AnimeEntry() {
        super();
    }

    public AnimeEntry(Anime anime, User user, Long score, AnimeEntryStatus status) {
        super();
        this.anime = anime;
        this.user = user;
        this.score = score;
        this.status = status;
    }

    public String toString() {
        return String.format("AnimeEntry - anime's mal id: %s, user's mal id: %s, score: %d, status %s",
                anime == null ? "null" : anime.getMalId(),
                user == null ? "null" :  user.getMalId(),
                score,
                status == null ? "null" : status.name());
    }
}
