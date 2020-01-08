package edu.uci.ics.luisae.service.gateway.models.MOVIEmodels;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.luisae.service.gateway.Base.ResponseModel;
import edu.uci.ics.luisae.service.gateway.models.MOVIEmodels.MovieClasses.Movie;

public class MovieSearchResponse extends ResponseModel {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "movies", required = true)
    private Movie[] movies;
    public MovieSearchResponse(){}
    @JsonCreator
    public MovieSearchResponse(@JsonProperty(value = "movies", required = true) Movie[] movies){
        this.movies = movies;
    }

    public void setMovies(Movie[] movies) {
        this.movies = movies;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "movies", required = true)
    public Movie[] getMovies() {
        return movies;
    }

}
