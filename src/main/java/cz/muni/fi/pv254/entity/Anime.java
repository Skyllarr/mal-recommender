package cz.muni.fi.pv254.entity;

/**
 * Created by skylar on 20.11.15.
 */
public class Anime {

    private String series_title;
    private int series_episodes;
    private String series_start;
    private String series_end;

    public Anime(){

    }

    public Anime(String series_title, int series_episodes, String series_start, String series_end) {
        this.series_title = series_title;
        this.series_episodes = series_episodes;
        this.series_start = series_start;
        this.series_end = series_end;
    }

    public String getTitle() {
        return series_title;
    }

    public void setSeries_title(String series_title) {
        this.series_title = series_title;
    }

    public int getEpisodes() {
        return series_episodes;
    }

    public void setEpisodes(int series_episodes) {
        this.series_episodes = series_episodes;
    }

    public String getSeries_start() {
        return series_start;
    }

    public void setSeries_start(String series_start) {
        this.series_start = series_start;
    }

    public String getSeries_end() {
        return series_end;
    }

    public void setSeries_end(String series_end) {
        this.series_end = series_end;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Anime Details - ");
        sb.append("Name:" + getTitle());
        sb.append(", ");
        sb.append("Number of episodes:" + getEpisodes());
        sb.append(", ");
        sb.append("Start of anime:" + getSeries_start());
        sb.append(", ");
        sb.append("End of anime:" + getSeries_end());

        return sb.toString();
    }
}
