package cz.muni.fi.pv254.data;

import cz.muni.fi.pv254.data.entity.DbRemoteUser;
import cz.muni.fi.pv254.data.entity.DbUser;
import cz.muni.fi.pv254.data.enums.Gender;
import cz.muni.fi.pv254.init.Setup;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by suomiy on 12/2/15.
 */
public class RemoteUser {

    private DbRemoteUser dbRemoteUser;
    private List<AnimeEntry> animeEntries;

    public RemoteUser() {
        dbRemoteUser = new DbRemoteUser();
        animeEntries = new ArrayList<>();
    }

    public RemoteUser(DbRemoteUser dbRemoteUser) {
        this.dbRemoteUser = dbRemoteUser;
        this.animeEntries = dbRemoteUser.getAnimeEntriesAsList();
    }

    public RemoteUser(String address, String userAgent, OffsetDateTime lastSeen, Long visitsCount) {
        dbRemoteUser = new DbRemoteUser(address, userAgent, lastSeen, visitsCount);
        animeEntries = new ArrayList<>();
    }


    public String getAddress() {
        return dbRemoteUser.getAddress();
    }

    public void setAddress(String address) {
        dbRemoteUser.setAddress(address);
    }

    public String getUserAgent() {
        return dbRemoteUser.getUserAgent();
    }

    public void setUserAgent(String userAgent) {
        dbRemoteUser.setUserAgent(userAgent);
    }

    public OffsetDateTime getLastSeen() {
        return dbRemoteUser.getLastSeen();
    }

    public void setLastSeen(OffsetDateTime lastSeen) {
        dbRemoteUser.setLastSeen(lastSeen);
    }


    public Long getVisitsCount() {
        return dbRemoteUser.getVisitsCount();
    }

    public void setVisitsCount(Long visitsCount) {
        dbRemoteUser.setVisitsCount(visitsCount);
    }

    public List<AnimeEntry> getAnimeEntries() {
        return animeEntries;
    }

    public void setAnimeEntries(List<AnimeEntry> animeEntries) {
        this.animeEntries = animeEntries;
    }

    public DbRemoteUser getDbRemoteUser() {
        return dbRemoteUser;
    }

    public void setDbRemoteUser(DbRemoteUser dbRemoteUser) {
        this.dbRemoteUser = dbRemoteUser;
    }

    public Long getId() {
        return dbRemoteUser.getId();
    }

    public void setId(Long id) {
        dbRemoteUser.setId(id);
    }

    public boolean isDeleted() {
        return dbRemoteUser.isDeleted();
    }

    public void setDeleted(boolean deleted) {
        dbRemoteUser.setDeleted(deleted);
    }

    @Override
    public String toString() {
        return dbRemoteUser.toString() + ", animeEntries: " + animeEntries.size();
    }
}