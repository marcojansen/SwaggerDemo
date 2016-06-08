package nl.saxion.swaggerdemo.resources;

import nl.saxion.swaggerdemo.model.Model;
import nl.saxion.swaggerdemo.model.Movie;
import nl.saxion.swaggerdemo.model.Review;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Created by MarcoJansen on 01-06-16.
 */

@Path("/reviews")
@Produces({"application/json", "application/xml"})
public class Reviews {
    private Model model = Model.getInstance();

    @POST
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Review postReview(
            @FormParam("rating") int rating,
            @FormParam("movieId") int movieId,
            @FormParam("reviewDesc") String reviewDesc,
            @Context final HttpServletResponse response) throws IOException {

        Movie m = model.getMovieById(movieId);

        if (m != null) {
            String description = reviewDesc == null ? "" : reviewDesc;
            Review r = new Review(movieId, rating, description);
            m.addReview(r);
            response.setStatus(200);
            response.flushBuffer();
            return r;
        }
        response.sendError(400, "Bad request, movie with id not found");
        return null;
    }


    @DELETE
    public Review deleteReview (
            @FormParam("movieId") int movieId,
            @FormParam("reviewId") int reviewId,
            @Context final HttpServletResponse response) throws IOException {
        Review r = model.removeReviewById(movieId, reviewId);
        if (r != null) {
            response.setStatus(200);
            response.flushBuffer();
            return r;
        } else {
            response.sendError(400, "movie or review with this id doesn't exist");
            return null;
        }
    }
}
