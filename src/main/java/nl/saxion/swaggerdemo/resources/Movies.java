package nl.saxion.swaggerdemo.resources;

import nl.saxion.swaggerdemo.model.Model;
import nl.saxion.swaggerdemo.model.Movie;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("/movies")
@Produces({"application/json", "application/xml"})
public class Movies {
    private Model model = Model.getInstance();

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Movie> getInventory() {
        return model.getMovies();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Path("/{movieId}")
    public Movie getOrderById(
            @PathParam("movieId") int movieId, @Context final HttpServletResponse response) throws IOException {
        Movie m = model.getMovieById(movieId);


        if(m != null) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.flushBuffer();
            return m;
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Movie with this id does not exist");
            response.flushBuffer();
            return null;
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Movie postMovie(
            @FormParam("movieName") String movieName,
            @FormParam("movieLength") int movieLength,
            @FormParam("movieDesc") String movieDesc,
            @Context final HttpServletResponse response) throws IOException {
        Movie movie = new Movie(movieName, movieLength, movieDesc);
        model.getMovies().add(movie);

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.flushBuffer();
        return movie;
    }

    @DELETE
    public Movie deleteMovie (@FormParam("movieId") int movieId,
                                 @Context final HttpServletResponse response) throws IOException {
        Movie toDelete = model.getMovieById(movieId);
        if (toDelete == null) {
            response.sendError(400, "Movie with this id not found");
            return null;
        } else {
            model.getMovies().remove(toDelete);
            response.setStatus(200);
            response.flushBuffer();
            return toDelete;
        }
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Movie updateMovie(@FormParam("movieId") int movieId,
                                @FormParam("movieTitle") String movieTitle,
                                @FormParam("movieLength") int movieLength,
                                @FormParam("movieDesc") String movieDesc,
                                @Context final HttpServletResponse response) throws IOException {
        Movie movieToChange = model.getMovieById(movieId);
        if (movieToChange == null) {
            response.sendError(400, "Movie with this id not found");
            return null;
        } else {
            movieToChange.setName(movieTitle);
            movieToChange.setLength(movieLength);
            movieToChange.setDescription(movieDesc);
            response.setStatus(200);
            response.flushBuffer();
            return movieToChange;
        }

    }

}
