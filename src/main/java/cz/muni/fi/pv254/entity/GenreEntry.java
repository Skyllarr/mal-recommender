package cz.muni.fi.pv254.entity;

import cz.muni.fi.pv254.enums.Genre;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by skylar on 22.11.15.
 */

@Entity
@Table(name = "mal_genre_entry")
@Getter
@Setter
public class GenreEntry extends IdEntity {
    @JoinColumn(name = "anime_id")
    @Fetch(FetchMode.SELECT)
    @ManyToOne
    private Anime anime;

    private Genre genre;
}
