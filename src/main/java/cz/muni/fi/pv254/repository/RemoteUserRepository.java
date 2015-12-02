package cz.muni.fi.pv254.repository;

/**
 * Created by suomiy on 11/7/15.
 */

import cz.muni.fi.pv254.data.RemoteUser;
import cz.muni.fi.pv254.data.entity.DbRemoteUser;
import cz.muni.fi.pv254.repository.entityrepository.DbRemoteUserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class RemoteUserRepository {

    @Inject
    DbRemoteUserRepository dbRemoteUserRepository;

    public RemoteUserRepository(){
    }

    public RemoteUser create(final RemoteUser entity) throws Exception {
        updateAnimeEntries(entity);
        return convertToRemoteUser(dbRemoteUserRepository.create(entity.getDbRemoteUser()));
    }

    public RemoteUser findOne(final Long id) {
        return convertToRemoteUser(dbRemoteUserRepository.findOne(id));
    }

    public RemoteUser update(final RemoteUser entity) {
        updateAnimeEntries(entity);
        DbRemoteUser dbRemoteUser = dbRemoteUserRepository.update(entity.getDbRemoteUser());
        entity.setDbRemoteUser(dbRemoteUser);
        return dbRemoteUser == null ? null : entity;
    }

    public RemoteUser delete(final Long id) throws Exception {
        return convertToRemoteUser(dbRemoteUserRepository.delete(id));
    }

    public List<RemoteUser> findAll() {
        return convert(dbRemoteUserRepository.findAll());
    }

    public List<RemoteUser> batchCreate(List<RemoteUser> entities) throws Exception {
        updateAnimeEntries(entities);
        return convert(dbRemoteUserRepository.batchCreate(reverseConvert(entities)));
    }

    public List<RemoteUser> batchUpdate(List<RemoteUser> entities) {
        updateAnimeEntries(entities);
        return convert(dbRemoteUserRepository.batchUpdate(reverseConvert(entities)));
    }

    public RemoteUser findByAddress(String address) {
        return convertToRemoteUser(dbRemoteUserRepository.findByAddress(address));
    }

    private RemoteUser convertToRemoteUser(DbRemoteUser dbRemoteUser) {
        return dbRemoteUser == null ? null : new RemoteUser(dbRemoteUser);
    }

    private List<DbRemoteUser> reverseConvert(List<RemoteUser> list) {
        return list.stream().map(RemoteUser::getDbRemoteUser).collect(Collectors.toList());
    }

    private List<RemoteUser> convert(List<DbRemoteUser> list) {
        return list.stream().map(RemoteUser::new).collect(Collectors.toList());
    }


    private void updateAnimeEntries(List<RemoteUser> entities) {
        entities.forEach(this::updateAnimeEntries);
    }

    private void updateAnimeEntries(RemoteUser entity) {
        entity.getDbRemoteUser().setAnimeEntriesAsString(entity.getAnimeEntries());
    }
}
