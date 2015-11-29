package cz.muni.fi.pv254.data;

import cz.muni.fi.pv254.data.entity.DbUser;
import cz.muni.fi.pv254.data.enums.Gender;
import cz.muni.fi.pv254.init.Setup;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by skylar on 23.11.15.
 */
public class User implements MAL {

    private DbUser dbUser;
    private List<AnimeEntry> animeEntries;

    public User() {
        dbUser = new DbUser();
        animeEntries = new ArrayList<>();
    }

    public User(DbUser dbUser) {
        this.dbUser = dbUser;
        this.animeEntries = dbUser.getAnimeEntriesAsList();

        // takes less memory, but entity cannot be updated after this
        if(Setup.forbidEntitiesUpdates){
            dbUser.setAnimeEntries(null);
        }
    }

    public User(String name, Long malId, LocalDate birthday) {
        dbUser = new DbUser(name, malId, birthday);
        animeEntries = new ArrayList<>();
    }

    public String getName() {
        return dbUser.getName();
    }

    public void setName(String name) {
        dbUser.setName(name);
    }

    public Gender getGender() {
        return dbUser.getGender();
    }

    public void setGender(Gender gender) {
        dbUser.setGender(gender);
    }

    public LocalDate getBirthday() {
        return dbUser.getBirthday();
    }

    public void setBirthday(LocalDate birthday) {
        dbUser.setBirthday(birthday);
    }

    public Long getMalId() {
        return dbUser.getMalId();
    }

    public void setMalId(Long malId) {
        dbUser.setMalId(malId);
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

    @Override
    public String toString() {
        return dbUser.toString() + ", animeEntries: " + animeEntries.size();
    }
}