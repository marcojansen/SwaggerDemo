package nl.saxion.swaggerdemo.model;

import java.util.ArrayList;

/**
 * Created by Veldmuus on 10/05/16.
 */
public class Model {
    private static Model instance = null;
    private ArrayList<Movie> movies = new ArrayList<Movie>();

    private Model() {
        Movie testMovie1 = new Movie("Hello World", 123,"Hello Description Wdrd");
        movies.add(testMovie1);
    }

    public static Model getInstance() {
        if(instance == null) {
            instance = new Model();
        }
        return instance;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void addFilms(Movie f){
        this.movies.add(f);
    }

    public Movie getMovieById(int id){
        for (Movie movie : this.movies) {
            if(movie.getId() == id)
                return movie;
        }
        return null;
    }

    public void addReviewToMovie(Review r) {
        Movie movie = getMovieById(r.getMovieId());
        movie.addReview(r);
    }

    public void removeReview(Review r) {
        getMovieById(r.getMovieId()).removeReview(r);
    }

    public Review removeReviewById(int movieId, int reviewId) {
        Movie m = getMovieById(movieId);
        if (m != null) {
            Review r = m.getReviewById(reviewId);
            if (r != null) {
                m.removeReview(r);
                return r;
            }
        }
        return null;
    }



    public Review getReviewById(int movieId, int id) {
        Movie m = getMovieById(movieId);
        for (Review review : m.getReviews()) {
            if (review.getId() == id) {
                return review;
            }
        }
        return null;
    }
}
