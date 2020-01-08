package edu.uci.ics.luisae.service.gateway.models.MOVIEmodels;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.luisae.service.gateway.Base.ResponseModel;
import edu.uci.ics.luisae.service.gateway.models.MOVIEmodels.MovieClasses.Person;

public class PeopleSearchResponse extends ResponseModel {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "people", required = true)
    private Person[] people;

    public PeopleSearchResponse(){}
    @JsonCreator
    public PeopleSearchResponse(@JsonProperty(value = "people", required = true) Person[] people){
        this.people = people;
    }

    public void setPeople(Person[] people) {
        this.people = people;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "people", required = true)
    public Person[] getPeople() {
        return people;
    }
}
