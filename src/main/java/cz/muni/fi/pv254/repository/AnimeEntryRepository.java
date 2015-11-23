package cz.muni.fi.pv254.repository;

/**
 * Created by suomiy on 11/7/15.
 */
/*
import cz.muni.fi.pv254.entity.Anime;
import cz.muni.fi.pv254.entity.AnimeEntry;
import cz.muni.fi.pv254.entity.QAnimeEntry;
import cz.muni.fi.pv254.entity.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Transactional
public class AnimeEntryRepository extends Repository<AnimeEntry> {

    @Inject
    UserRepository userRepository;

    @Inject
    AnimeRepository animeRepository;

    public AnimeEntryRepository() {
        super();
    }

    @Inject
    public AnimeEntryRepository(EntityManager em) {
        super(em, AnimeEntry.class, QAnimeEntry.animeEntry);
    }

    public List<AnimeEntry> batchfindAll() {
        List<Anime> animes = animeRepository.findAll();
        List<User> users = userRepository.findAll();

        List<AnimeEntry> animeEntries = new ArrayList<>();

        em.setFlushMode(FlushModeType.COMMIT);

        for (User user : users) {
            animeEntries.addAll(user.getAnimeEntries());
        }
        em.flush();
        em.clear();
        em.setFlushMode(FlushModeType.AUTO);
        return animeEntries;
    }
}*/
