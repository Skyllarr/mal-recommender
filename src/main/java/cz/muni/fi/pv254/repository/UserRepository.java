package cz.muni.fi.pv254.repository;

/**
 * Created by suomiy on 11/7/15.
 */

import cz.muni.fi.pv254.data.User;
import cz.muni.fi.pv254.data.entity.DbUser;
import cz.muni.fi.pv254.repository.entityrepository.DbUserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserRepository {

    @Inject
    DbUserRepository dbUserRepository;

    public UserRepository(){
    }

    public User create(final User entity) throws Exception {
        updateAnimeEntries(entity);
        return convertToUser(dbUserRepository.create(entity.getDbUser()));
    }

    public User findOne(final Long id) {
        return convertToUser(dbUserRepository.findOne(id));
    }

    public User update(final User entity) {
        updateAnimeEntries(entity);
        DbUser dbUser = dbUserRepository.update(entity.getDbUser());
        entity.setDbUser(dbUser);
        return dbUser == null ? null : entity;
    }

    public User delete(final Long id) throws Exception {
        return convertToUser(dbUserRepository.delete(id));
    }

    public List<User> findAll() {
        return convert(dbUserRepository.findAll());
    }

    public List<User> batchCreate(List<User> entities) throws Exception {
        updateAnimeEntries(entities);
        return convert(dbUserRepository.batchCreate(reverseConvert(entities)));
    }

    public List<User> batchUpdate(List<User> entities) {
        updateAnimeEntries(entities);
        return convert(dbUserRepository.batchUpdate(reverseConvert(entities)));
    }

    public User findByName(String name) {
        return convertToUser(dbUserRepository.findByName(name));
    }

    public User findByMalId(Long malId) {
        return convertToUser(dbUserRepository.findByMalId(malId));
    }

    private User convertToUser(DbUser dbUser) {
        return dbUser == null ? null : new User(dbUser);
    }

    private List<DbUser> reverseConvert(List<User> list) {
        return list.stream().map(User::getDbUser).collect(Collectors.toList());
    }

    private List<User> convert(List<DbUser> list) {
        return list.stream().map(User::new).collect(Collectors.toList());
    }


    private void updateAnimeEntries(List<User> entities) {
        entities.forEach(this::updateAnimeEntries);
    }

    private void updateAnimeEntries(User entity) {
        entity.getDbUser().setAnimeEntriesAsString(entity.getAnimeEntries());
    }
}
