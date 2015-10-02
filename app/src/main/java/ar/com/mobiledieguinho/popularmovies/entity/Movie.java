package ar.com.mobiledieguinho.popularmovies.entity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ar.com.mobiledieguinho.popularmovies.MovieListActivityFragment;
import ar.com.mobiledieguinho.popularmovies.contentprovider.MovieContract;

/**
 * Created by Dieguinho on 10/07/2015.
 */
public class Movie implements Parcelable {
    private static final String TAG = "Movie";

    @SerializedName("id") private long id;
    @SerializedName("title") private String title;
    @SerializedName("original_title") private String originalTitle;
    @SerializedName("backdrop_path") private String backdropPath;
    @SerializedName("poster_path") private String posterPath;
    @SerializedName("overview") private String synopsis;
    @SerializedName("release_date") private String releaseDate;
    @SerializedName("original_language") private String originalLanguage;
    @SerializedName("adult") private boolean adult;
    @SerializedName("budget") private long budget;

    @SerializedName("homepage") private String homepage;
    @SerializedName("imdb_id") private String idImdb;
    @SerializedName("popularity") private double popularity;

    @SerializedName("production_companies") private List<ProductionCompany> productionCompanies = new ArrayList<ProductionCompany>();
    @SerializedName("production_countries") private List<ProductionCountry> productionCountries = new ArrayList<ProductionCountry>();
    @SerializedName("revenue") private long revenue;
    @SerializedName("runtime") private int runtime;
    @SerializedName("spoken_languages") private List<SpokenLanguage> spokenLanguages = new ArrayList<SpokenLanguage>();

    @SerializedName("status") private String status;
    @SerializedName("tag_line") private String tagLine;
    @SerializedName("video") private boolean video;

    @SerializedName("vote_average") private double userRating;
    @SerializedName("vote_count") private long voteCount;

    private boolean favourite;

    public Movie() {
    }

    public Movie(long id, String title, String originalTitle, String backdropPath, String posterPath, String synopsis, String releaseDate, String originalLanguage, boolean adult, long budget, String homepage, String idImdb, double popularity, List<ProductionCompany> productionCompanies, List<ProductionCountry> productionCountries, long revenue, int runtime, List<SpokenLanguage> spokenLanguages, String status, String tagLine, boolean video, double userRating, long voteCount, boolean favourite) {
        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.backdropPath = backdropPath;
        this.posterPath = posterPath;
        this.synopsis = synopsis;
        this.releaseDate = releaseDate;
        this.originalLanguage = originalLanguage;
        this.adult = adult;
        this.budget = budget;
        this.homepage = homepage;
        this.idImdb = idImdb;
        this.popularity = popularity;
        this.productionCompanies = productionCompanies;
        this.productionCountries = productionCountries;
        this.revenue = revenue;
        this.runtime = runtime;
        this.spokenLanguages = spokenLanguages;
        this.status = status;
        this.tagLine = tagLine;
        this.video = video;
        this.userRating = userRating;
        this.voteCount = voteCount;
        this.favourite = favourite;
    }

    public Movie(Cursor cursor) {
        this.id = cursor.getInt(MovieListActivityFragment.COLUMN_ID);
        this.title = cursor.getString(MovieListActivityFragment.COLUMN_TITLE);
        this.originalTitle = cursor.getString(MovieListActivityFragment.COLUMN_ORIGINAL_TITLE);
        this.backdropPath = cursor.getString(MovieListActivityFragment.COLUMN_BACKDROP_PATH);
        this.posterPath = cursor.getString(MovieListActivityFragment.COLUMN_POSTER_PATH);
        this.synopsis = cursor.getString(MovieListActivityFragment.COLUMN_SYNOPSIS);
        this.releaseDate = cursor.getString(MovieListActivityFragment.COLUMN_RELEASE_DATE);
        this.originalLanguage = cursor.getString(MovieListActivityFragment.COLUMN_ORIGINAL_LANGUAGE);
        this.adult = cursor.getInt(MovieListActivityFragment.COLUMN_ADULT) != 0;
        this.budget = cursor.getLong(MovieListActivityFragment.COLUMN_BUDGET);
        this.homepage = cursor.getString(MovieListActivityFragment.COLUMN_HOMEPAGE);
        this.idImdb = cursor.getString(MovieListActivityFragment.COLUMN_ID_IMDB);
        this.popularity = cursor.getDouble(MovieListActivityFragment.COLUMN_POPULARITY);
//        this.productionCompanies = cursor.getString(MovieListActivityFragment.COLUMN_ID_PRODUCTION_COMPANY);
//        this.productionCountries = cursor.getString(MovieListActivityFragment.COLUMN_ID_PRODUCTION_COUNTRY);
        this.revenue = cursor.getLong(MovieListActivityFragment.COLUMN_REVENUE);
        this.runtime = cursor.getInt(MovieListActivityFragment.COLUMN_RUNTIME);
//        this.spokenLanguages = cursor.getString(MovieListActivityFragment.COLUMN_ID_SPOKEN_LANGUAGE);
        this.status = cursor.getString(MovieListActivityFragment.COLUMN_STATUS);
        this.tagLine = cursor.getString(MovieListActivityFragment.COLUMN_TAGLINE);
        this.video = cursor.getInt(MovieListActivityFragment.COLUMN_VIDEO) != 0;
        this.userRating = cursor.getDouble(MovieListActivityFragment.COLUMN_VOTE_AVERAGE);
        this.voteCount = cursor.getLong(MovieListActivityFragment.COLUMN_VOTE_COUNT);
        this.favourite = cursor.getInt(MovieListActivityFragment.COLUMN_FAVOURITE) != 0;
    }

    private Movie(Parcel parcel){
        this.id = parcel.readLong();
        this.title = parcel.readString();
        this.originalTitle = parcel.readString();
        this.backdropPath = parcel.readString();
        this.posterPath = parcel.readString();
        this.synopsis = parcel.readString();
        this.userRating = parcel.readDouble();
        this.releaseDate = parcel.readString();
        this.originalLanguage = parcel.readString();
        this.adult = parcel.readByte() != 0;
        this.budget = parcel.readLong();
        this.homepage = parcel.readString();
        this.idImdb = parcel.readString();
        this.popularity = parcel.readDouble();
        parcel.readTypedList(productionCompanies, ProductionCompany.CREATOR);
        parcel.readTypedList(productionCountries, ProductionCountry.CREATOR);
        this.revenue = parcel.readLong();
        this.runtime = parcel.readInt();
        parcel.readTypedList(spokenLanguages, SpokenLanguage.CREATOR);
        this.status = parcel.readString();
        this.tagLine = parcel.readString();
        this.video = parcel.readByte() != 0;
        this.voteCount = parcel.readLong();
        this.favourite = parcel.readByte() != 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public double getUserRating() {
        return userRating;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }

    public void setUserRating(long userRating) {
        this.userRating = userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getIdImdb() {
        return idImdb;
    }

    public void setIdImdb(String idImdb) {
        this.idImdb = idImdb;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public List<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public List<ProductionCountry> getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(List<ProductionCountry> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public List<SpokenLanguage> getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(List<SpokenLanguage> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(long voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public ContentValues getAsContentValues(){
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry._ID, getId());
        values.put(MovieContract.MovieEntry.COLUMN_TITLE, getTitle());
        values.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE, getOriginalTitle());
        values.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH, getBackdropPath());
        values.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, getPosterPath());
        values.put(MovieContract.MovieEntry.COLUMN_SYNOPSIS, getSynopsis());
        values.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, getReleaseDate());
        values.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE, getOriginalLanguage());
        values.put(MovieContract.MovieEntry.COLUMN_ADULT, isAdult());
        values.put(MovieContract.MovieEntry.COLUMN_BUDGET, getBudget());
        values.put(MovieContract.MovieEntry.COLUMN_HOMEPAGE, getHomepage());
        values.put(MovieContract.MovieEntry.COLUMN_ID_IMDB, getIdImdb());
        values.put(MovieContract.MovieEntry.COLUMN_POPULARITY, getPopularity());
//        values.put(MovieContract.MovieEntry.COLUMN_ID_PRODUCTION_COMPANY, );
//        values.put(MovieContract.MovieEntry.COLUMN_ID_PRODUCTION_COUNTRY, );
        values.put(MovieContract.MovieEntry.COLUMN_REVENUE, getRevenue());
        values.put(MovieContract.MovieEntry.COLUMN_RUNTIME, getRuntime());
//        values.put(MovieContract.MovieEntry.COLUMN_ID_SPOKEN_LANGUAGE, movie.get);
        values.put(MovieContract.MovieEntry.COLUMN_STATUS, getStatus());
        values.put(MovieContract.MovieEntry.COLUMN_TAGLINE, getTagLine());
        values.put(MovieContract.MovieEntry.COLUMN_VIDEO, isVideo());
        values.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, getUserRating());
        values.put(MovieContract.MovieEntry.COLUMN_VOTE_COUNT, getVoteCount());
        values.put(MovieContract.MovieEntry.COLUMN_FAVOURITE, isFavourite());
//        values.put(MovieContract.MovieEntry.COLUMN_ID_TRAILER, movie.get);
//        values.put(MovieContract.MovieEntry.COLUMN_ID_REVIEW, );
        return values;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.originalTitle);
        dest.writeString(this.backdropPath);
        dest.writeString(this.posterPath);
        dest.writeString(this.synopsis);
        dest.writeDouble(this.userRating);
        dest.writeString(this.releaseDate);
        dest.writeString(this.originalLanguage);
        dest.writeByte((byte) (this.adult ? 1 : 0));
        dest.writeLong(this.budget);
        dest.writeString(this.homepage);
        dest.writeString(this.idImdb);
        dest.writeDouble(this.popularity);
        dest.writeTypedList(this.productionCompanies);
        dest.writeTypedList(this.productionCountries);
        dest.writeLong(this.revenue);
        dest.writeInt(this.runtime);
        dest.writeTypedList(this.spokenLanguages);
        dest.writeString(this.status);
        dest.writeString(this.tagLine);
        dest.writeByte((byte) (this.video ? 1 : 0));
        dest.writeLong(this.voteCount);
        dest.writeByte((byte) (this.favourite ? 1 : 0));
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }

    };
}
