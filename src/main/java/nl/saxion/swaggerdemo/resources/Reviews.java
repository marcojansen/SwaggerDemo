package nl.saxion.swaggerdemo.resources;

import nl.saxion.swaggerdemo.model.ErrorResponse;
import nl.saxion.swaggerdemo.model.Model;
import nl.saxion.swaggerdemo.model.Movie;
import nl.saxion.swaggerdemo.model.Review;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
/**
 * Created by MarcoJansen on 01-06-16.
 */

@Path("/reviews")
@Produces({"application/json", "application/xml"})
public class Reviews {
    private Model model = Model.getInstance();

    @POST
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response postReview(
            @FormParam("rating") int rating,
            @FormParam("movieId") int movieId,
            @FormParam("reviewDesc") String reviewDesc) {

        Movie m = model.getMovieById(movieId);

        if (m != null) {
            String description = reviewDesc == null ? "" : reviewDesc;
            Review r = new Review(movieId, rating, description);
            m.addReview(r);
            return Response.ok().entity(r).build();
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).entity(new ErrorResponse("Movie with supplied id not found")).build();

    }


    @DELETE
    public Response deleteReview (
            @FormParam("movieId") int movieId,
            @FormParam("reviewId") int reviewId) {
        if (model.removeReviewById(movieId, reviewId)) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse("Movie or review with supplied id not found")).build();
        }
    }
}
