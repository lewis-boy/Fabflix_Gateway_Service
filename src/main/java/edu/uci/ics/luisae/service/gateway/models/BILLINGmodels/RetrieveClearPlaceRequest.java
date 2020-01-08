package edu.uci.ics.luisae.service.gateway.models.BILLINGmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.luisae.service.gateway.Base.RequestModel;

public class RetrieveClearPlaceRequest extends RequestModel {
    @JsonProperty(value = "email", required = true)
    private String email;

    public RetrieveClearPlaceRequest(){}

    @JsonProperty(value = "email", required = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
