package cz.muni.fi.pv254.repository;

/**
 * Created by suomiy on 11/7/15.
 */

import cz.muni.fi.pv254.entity.DbUser;
import cz.muni.fi.pv254.entity.User;

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
        return convertToUser(dbUserRepository.create(entity.getDbUser()));
    }

    public User findOne(final Long id) {
        return convertToUser(dbUserRepository.findOne(id));
    }

    public User update(final User entity) {
        entity.getDbUser().setAnimeEntriesAsString(entity.getAnimeEntries());
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
        return convert(dbUserRepository.batchCreate(reverseConvert(entities)));
    }
    public User findByName(String name) {
        return convertToUser(dbUserRepository.findByName(name));
    }

    public List<User> find(int maxCount) {
        return convert(dbUserRepository.find(maxCount));
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
}
