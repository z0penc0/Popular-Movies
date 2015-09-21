package ar.com.mobiledieguinho.popularmovies.entity;

import java.util.List;

/**
 * Created by Dieguinho on 19/07/2015.
 */
public class ResponsePage {
    private long page;
    private List<Movie> results;

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
