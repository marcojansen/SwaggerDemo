//MOVIES

package nl.saxion.swaggerdemo.resources;

import io.swagger.annotations.*;
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
@Api(value="/movies", description = "Movie calls")
@Produces({"application/json", "application/xml"})
public class Movies {
    private Model model = Model.getInstance();

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @ApiOperation(value = "returns all the movies",
            notes = "Multiple movies",
            response = Movie.class,
            responseContainer = "List")
    public ArrayList<Movie> getInventory() {
        return model.getMovies();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    @Path("/{movieId}")
    @ApiOperation(value = "Find movie by ID",
            notes = "For valid response try integer IDs with value >= 0",
            response = Movie.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "movie not found")
    })
    public Movie getOrderById(
            @ApiParam(value = "ID of the movie that need to be fetched", required = true) @PathParam("movieId") int movieId,
            @Context final HttpServletResponse response) throws IOException {
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
    @ApiOperation(value = "Create a movie",
            notes = "Create a movie by a name",
            response = Movie.class,
            responseContainer = "Object")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Error")
    })
    public Movie postMovie(
            @ApiParam (value="movie name", required = true) @FormParam("movieName") String movieName,
            @ApiParam (value="movie length", required = true) @FormParam("movieLength") int movieLength,
            @ApiParam (value="movie description", required = true) @FormParam("movieDesc") String movieDesc,
            @Context final HttpServletResponse response) throws IOException {
        Movie movie = new Movie(movieName, movieLength, movieDesc);
        model.getMovies().add(movie);

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.flushBuffer();
        return movie;
    }

    @DELETE
    @ApiOperation(value = "Delete a movie",
            notes ="Delete a movie by id")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Error"),
            @ApiResponse(code = 200, message = "Successfully deleted"),
            @ApiResponse(code = 406, message = "Not accepted, movie with this id doesn't exist")
    })
    public Movie deleteMovie (
            @ApiParam (value="movie id", required = true) @FormParam("movieId") int movieId,
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
    @ApiOperation(value = "Update a movie",
            notes = "Update a movie by id",
            response = Movie.class,
            responseContainer = "Object")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Error"),
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 406, message = "Not accepted, movie with this id doesn't exist")})

    public Movie updateMovie(@ApiParam (value="movie id", required = true) @FormParam("movieId") int movieId,
                             @ApiParam (value="movie title", required = true) @FormParam("movieTitle") String movieTitle,
                             @ApiParam (value="movie length", required = true) @FormParam("movieLength") int movieLength,
                             @ApiParam(value="movie description", required = true) @FormParam("movieDesc") String movieDesc,
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