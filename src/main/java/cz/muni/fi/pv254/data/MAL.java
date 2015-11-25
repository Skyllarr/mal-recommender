package cz.muni.fi.pv254.data;

/**
 * Created by suomiy on 11/25/15.
 */
public interface MAL {
    Long getId();
    void setId(Long id);
    Long getMalId();
    void setMalId(Long malId);
    boolean isDeleted();
    void setDeleted(boolean deleted);
}
