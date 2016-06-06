package nl.saxion.swaggerdemo.resources;

import nl.saxion.swaggerdemo.exception.NotFoundException;
import nl.saxion.swaggerdemo.model.Model;
import nl.saxion.swaggerdemo.model.Movie;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
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
    public Response getOrderById(
            @PathParam("movieId") int movieId)
            throws NotFoundException {
        Movie m = model.getMovieById(movieId);
        System.out.println(model.getMovieById(movieId) + " " + movieId);
        if (null != m) {
            return Response.ok().entity(m).build();
        } else {
            throw new NotFoundException(404, "movie not found");
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response postMovie(
            @FormParam("movieName") String movieName,
            @FormParam("movieLength") int movieLength,
            @FormParam("movieDesc") String movieDesc) {
        Movie movie = new Movie(movieName, movieLength, movieDesc);
        model.getMovies().add(movie);
        return Response.ok().entity(movie).build();
    }

    @DELETE
    public Response deleteMovie (@FormParam("movieId") int movieId) {
        if (model.getMovieById(movieId) == null) {
            return Response.status(406).build();
        } else {
            model.getMovies().remove(model.getMovieById(movieId));
        }
        return Response.ok().build();

    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response updateMovie(@FormParam("movieId") int movieId,
                                @FormParam("movieTitle") String movieTitle,
                                @FormParam("movieLength") int movieLength,
                                @FormParam("movieDesc") String movieDesc) {
        Movie movieToChange = model.getMovieById(movieId);
        if (movieToChange == null) {
            return Response.status(406).build();
        } else {
            movieToChange.setName(movieTitle);
            movieToChange.setLength(movieLength);
            movieToChange.setDescription(movieDesc);
            return Response.ok().entity(movieToChange).build();
        }

    }

}
