package nl.saxion.swaggerdemo.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by MarcoJansen on 01-06-16.
 */
@XmlRootElement
public class Review {
    private int movieId;
    private int id;
    private static int lastId = 0;
    private int rating;
    private String description;

    public Review(int movieId, int rating, String description) {
        this.movieId = movieId;
        this.rating = rating;
        this.description = description;
        this.id = ++lastId;
    }

    public Review() {
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
