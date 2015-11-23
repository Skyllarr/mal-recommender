package cz.muni.fi.pv254.entity;

import cz.muni.fi.pv254.enums.Gender;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by skylar on 23.11.15.
 */
public class User {

    private DbUser dbUser;
    private List<AnimeEntry> animeEntries;

    public User() {
        dbUser = new DbUser();
        animeEntries = new ArrayList<>();
    }

    public User(DbUser dbUser) {
        this.dbUser = dbUser;
        this.animeEntries = dbUser.getAnimeEntriesAsList();
    }

    public String getName() {
        return dbUser.getName();
    }

    public void setName(String name) {
        this.dbUser.setName(name);
    }

    public Gender getGender() {
        return dbUser.getGender();
    }

    public void setGender(Gender gender) {
        this.dbUser.setGender(gender);
    }

    public LocalDate getBirthday() {
        return dbUser.getBirthday();
    }

    public void setBirthday(LocalDate birthday) {
        this.dbUser.setBirthday(birthday);
    }

    public Long getMalId() {
        return dbUser.getMalId();
    }

    public void setMalId(Long malId) {
        this.dbUser.setMalId(malId);
    }

    public List<AnimeEntry> getAnimeEntries() {
        return animeEntries;
    }

    public void setAnimeEntries(List<AnimeEntry> animeEntries) {
        this.animeEntries = animeEntries;
    }

    public DbUser getDbUser() {
        return dbUser;
    }

    public void setDbUser(DbUser dbUser) {
        this.dbUser = dbUser;
    }

    public Long getId() {
        return dbUser.getId();
    }

    public void setId(Long id) {
        dbUser.setId(id);
    }

    public boolean isDeleted() {
        return dbUser.isDeleted();
    }

    public void setDeleted(boolean deleted) {
        dbUser.setDeleted(deleted);
    }

    public String toString() {
        return dbUser.toString() + ", animeEntries: " + animeEntries.size();
    }
}
