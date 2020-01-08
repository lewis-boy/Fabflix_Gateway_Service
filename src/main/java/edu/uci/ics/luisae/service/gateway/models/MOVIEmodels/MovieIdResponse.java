package edu.uci.ics.luisae.service.gateway.models.MOVIEmodels;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.luisae.service.gateway.Base.ResponseModel;
import edu.uci.ics.luisae.service.gateway.models.MOVIEmodels.MovieClasses.FullMovie;

public class MovieIdResponse extends ResponseModel {
    @JsonProperty(value = "movie", required = true)
    private FullMovie fullMovie;
    public MovieIdResponse(){}
    @JsonCreator public MovieIdResponse(@JsonProperty(value = "movie", required = true) FullMovie fullMovie){
        this.fullMovie = fullMovie;
    }

    @JsonProperty(value = "movie", required = true)
    public FullMovie getFullMovie() {
        return fullMovie;
    }

    public void setFullMovie(FullMovie fullMovie) {
        this.fullMovie = fullMovie;
    }
}
