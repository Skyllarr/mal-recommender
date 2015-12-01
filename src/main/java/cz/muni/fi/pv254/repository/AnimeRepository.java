package cz.muni.fi.pv254.repository;

/**
 * Created by suomiy on 11/7/15.
 */

import cz.muni.fi.pv254.data.Anime;
import cz.muni.fi.pv254.data.MAL;
import cz.muni.fi.pv254.data.entity.DbAnime;
import cz.muni.fi.pv254.repository.entityrepository.DbAnimeRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AnimeRepository {

    @Inject
    DbAnimeRepository dbAnimeRepository;

    public AnimeRepository(){
    }

    public Anime create(final Anime entity) throws Exception {
        updateGenres(entity);
        updateDifferenceVector(entity);
        updateDescriptionSimilarityVector(entity);
        return convertToAnime(dbAnimeRepository.create(entity.getDbAnime()));
    }

    public MAL findOne(final Long id) {
        return convertToAnime(dbAnimeRepository.findOne(id));
    }

    public Anime update(final Anime entity) {
        updateGenres(entity);
        updateDifferenceVector(entity);
        updateDescriptionSimilarityVector(entity);
        DbAnime dbAnime = dbAnimeRepository.update(entity.getDbAnime());
        entity.setDbAnime(dbAnime);
        return dbAnime == null ? null : entity;
    }

    public Anime delete(final Long id) throws Exception {
        return convertToAnime(dbAnimeRepository.delete(id));
    }

    public List<Anime> findAll() {
        return convert(dbAnimeRepository.findAll());
    }

    public List<Anime> findAllWithDeleted() {
        return convert(dbAnimeRepository.findAllWithDeleted());
    }


    public List<Anime> batchCreate(List<Anime> entities) throws Exception {
        updateGenres(entities);
        updateDifferenceVector(entities);
        updateDescriptionSimilarityVector(entities);
        return convert(dbAnimeRepository.batchCreate(reverseConvert(entities)));
    }

    public List<Anime> batchUpdate(List<Anime> entities) {
        updateGenres(entities);
        updateDifferenceVector(entities);
        updateDescriptionSimilarityVector(entities);
        return convert(dbAnimeRepository.batchUpdate(reverseConvert(entities)));
    }


    public Anime findByMalId(Long malId) {
        return convertToAnime(dbAnimeRepository.findByMalId(malId));
    }


    private Anime convertToAnime(DbAnime dbAnime) {
        return dbAnime == null ? null : new Anime(dbAnime);
    }

    private List<DbAnime> reverseConvert(List<Anime> list) {
        return list.stream().map(Anime::getDbAnime).collect(Collectors.toList());
    }

    private List<Anime> convert(List<DbAnime> list) {
        return list.stream().map(Anime::new).collect(Collectors.toList());
    }

    private void updateGenres(List<Anime> entities) {
        entities.forEach(this::updateGenres);
    }

    private void updateGenres(Anime entity) {
        entity.getDbAnime().setGenreEntriesAsString(entity.getGenres());
    }

    private void updateDifferenceVector(List<Anime> entities) {
        entities.forEach(this::updateDifferenceVector);
    }

    private void updateDifferenceVector(Anime entity) {
        entity.getDbAnime().setDifferenceVectorAsString(entity.getDifferenceVector());
    }

    private void updateDescriptionSimilarityVector(List<Anime> entities) {
        entities.forEach(this::updateDescriptionSimilarityVector);
    }

    private void updateDescriptionSimilarityVector(Anime entity) {
        entity.getDbAnime().setDescriptionSimilarityVectorAsString(entity.getDescriptionSimilarityVector());
    }
}
