package cz.muni.fi.pv254.data;

import com.mysema.commons.lang.Pair;
import cz.muni.fi.pv254.data.entity.DbAnime;
import cz.muni.fi.pv254.data.enums.AnimeType;
import cz.muni.fi.pv254.data.enums.Genre;
import cz.muni.fi.pv254.init.Setup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suomiy on 11/24/15.
 */
public class Anime implements MAL {

    private DbAnime dbAnime;
    private List<Genre> genres;
    private List<Pair<Integer, Double>> differenceVector;
    private List<Double> descriptionSimilarityVector;

    public Anime() {
        dbAnime = new DbAnime();
        genres = new ArrayList<>();
        differenceVector = new ArrayList<>();
        descriptionSimilarityVector = new ArrayList<>();
    }

    public Anime(DbAnime dbAnime) {
        this.dbAnime = dbAnime;
        this.genres = dbAnime.getGenreEntriesAsList();
        differenceVector = dbAnime.getDifferenceVectorAsList();
        descriptionSimilarityVector = dbAnime.getDescriptionSimilarityVectorAsList();

        // takes less memory, but entity cannot be updated after this
        if(Setup.forbidEntitiesUpdates){
            dbAnime.setDifferenceVector(null);
            dbAnime.setDescriptionSimilarityVector(null);
            dbAnime.setGenreEntries(null);
        }
    }

    public Anime(String title, String imageLink, Long malId, Long episodes, AnimeType type) {
        dbAnime = new DbAnime(title, imageLink, malId, episodes, type);
        genres = new ArrayList<>();
        differenceVector = new ArrayList<>();
        descriptionSimilarityVector = new ArrayList<>();
    }

    public Long getEpisodes() {
        return dbAnime.getEpisodes();
    }

    public void setEpisodes(Long episodes) {
        dbAnime.setEpisodes(episodes);
    }

    public String getImageLink() {
        return dbAnime.getImageLink();
    }

    public void setImageLink(String imageLink) {
        dbAnime.setImageLink(imageLink);
    }

    public Long getMalId() {
        return dbAnime.getMalId();
    }

    public void setMalId(Long malId) {
        dbAnime.setMalId(malId);
    }

    public Long getPopularity() {
        return dbAnime.getPopularity();
    }

    public void setPopularity(Long popularity) {
        dbAnime.setPopularity(popularity);
    }

    public Long getRanked() {
        return dbAnime.getRanked();
    }

    public void setRanked(Long ranked) {
        dbAnime.setRanked(ranked);
    }

    public String getTitle() {
        return dbAnime.getTitle();
    }

    public void setTitle(String title) {
        dbAnime.setTitle(title);
    }

    public String getDescription() {
        return dbAnime.getDescription();
    }

    public void setDescription(String description) {
        dbAnime.setDescription(description);
    }

    public AnimeType getType() {
        return dbAnime.getType();
    }

    public void setType(AnimeType type) {
        dbAnime.setType(type);
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Pair<Integer, Double>> getDifferenceVector() {
        return differenceVector;
    }

    public void setDifferenceVector(List<Pair<Integer, Double>> differenceVector) {
        this.differenceVector = differenceVector;
    }

    public List<Double> getDescriptionSimilarityVector() {
        return descriptionSimilarityVector;
    }

    public void setDescriptionSimilarityVector(List<Double> descriptionSimilarityVector) {
        this.descriptionSimilarityVector = descriptionSimilarityVector;
    }

    public DbAnime getDbAnime() {
        return dbAnime;
    }

    public void setDbAnime(DbAnime dbAnime) {
        this.dbAnime = dbAnime;
    }

    public Long getId() {
        return dbAnime.getId();
    }

    public void setId(Long id) {
        dbAnime.setId(id);
    }

    public boolean isDeleted() {
        return dbAnime.isDeleted();
    }

    public void setDeleted(boolean deleted) {
        dbAnime.setDeleted(deleted);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnimeEntry || o instanceof Anime)) return false;

        MAL entity = (MAL) o;

        return getMalId() != null && entity.getMalId() != null && getMalId().equals(entity.getMalId());
    }

    @Override
    public int hashCode() {
        return (getMalId() != null) ? getMalId().intValue() : 0;
    }

    @Override
    public String toString() {
        return dbAnime.toString() + ", genres " + genres.size();
    }
}
