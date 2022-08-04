package sg.edu.rp.c346.id21021436.mymovies;

import java.io.Serializable;

public class Movie implements Serializable {

    private int _id;
    private String title;
    private String genre;
    private int year;
    private String rating;

    public Movie(int _id, String title, String genre, int year, String rating) {

        this._id = _id;
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.rating = rating;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "_id=" + _id +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", year=" + year +
                ", rating='" + rating + '\'' +
                '}';
    }
}
