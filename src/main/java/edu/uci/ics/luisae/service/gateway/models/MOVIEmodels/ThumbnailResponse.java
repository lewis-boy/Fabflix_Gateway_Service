package edu.uci.ics.luisae.service.gateway.models.MOVIEmodels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.luisae.service.gateway.Base.ResponseModel;
import edu.uci.ics.luisae.service.gateway.models.MOVIEmodels.MovieClasses.Thumbnail;

public class ThumbnailResponse extends ResponseModel {
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty(value = "thumbnails")
    Thumbnail[] thumbnails;

    public ThumbnailResponse(){}

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty(value = "thumbnails")
    public Thumbnail[] getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(Thumbnail[] thumbnails) {
        this.thumbnails = thumbnails;
    }
}
