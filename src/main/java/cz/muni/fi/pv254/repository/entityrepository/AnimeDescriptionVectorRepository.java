package cz.muni.fi.pv254.repository.entityrepository;

/**
 * Created by suomiy on 11/7/15.
 */

import com.mysema.query.jpa.impl.JPAQuery;
import cz.muni.fi.pv254.data.AnimeEntry;
import cz.muni.fi.pv254.data.entity.AnimeDescriptionVector;
import cz.muni.fi.pv254.data.entity.QAnimeDescriptionVector;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class AnimeDescriptionVectorRepository extends Repository<AnimeDescriptionVector> {

    public AnimeDescriptionVectorRepository() {
        super();
    }

    @Inject
    public AnimeDescriptionVectorRepository(EntityManager em) {
        super(em, null, false, AnimeDescriptionVector.class, QAnimeDescriptionVector.animeDescriptionVector);
    }


    public Map<Long, AnimeDescriptionVector> findDescriptionSimilarityVectors(List<AnimeEntry> animes) {
        QAnimeDescriptionVector animeDescriptionVector = QAnimeDescriptionVector.animeDescriptionVector;

        JPAQuery query = new JPAQuery(em)
                .from(animeDescriptionVector)
                .where(animeDescriptionVector.malId.in(animes.stream().map(AnimeEntry::getMalId).collect(Collectors.toList())));

        return query.list(animeDescriptionVector)
                .stream()
                .collect(Collectors.toMap(AnimeDescriptionVector::getMalId, v -> v));
    }
}
