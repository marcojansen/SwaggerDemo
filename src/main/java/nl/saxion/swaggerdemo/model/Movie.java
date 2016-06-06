package nl.saxion.swaggerdemo.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

/**
 * Created by Veldmuus on 10/05/16.
 */
@XmlRootElement
public class Movie {
    private String name;
    private static int lastId = 0;
    private int id;
    private int length;
    private String description;
    private ArrayList<Review> reviews = new ArrayList<Review>();

    public Movie() {
    }

    public Movie(String name, int length, String description) {
        this.name = name;
        this.id = ++lastId;
        this.length = length;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addReview(Review r) {
        reviews.add(r);
    }

    public void removeReview(Review r) {
        reviews.remove(r);
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public Review getReviewById(int reviewId) {
        for (Review r : reviews) {
            if ( r.getId() == reviewId) {
                return r;
            }
        }
        return null;
    }
}
