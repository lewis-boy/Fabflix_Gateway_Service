package edu.uci.ics.luisae.service.gateway.models.BILLINGmodels;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.luisae.service.gateway.Base.ResponseModel;
import edu.uci.ics.luisae.service.gateway.models.BILLINGmodels.BillingClasses.Items;

public class RetrieveResponse extends ResponseModel {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "items")
    Items[] items;

    public RetrieveResponse(){}

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "items")
    public Items[] getItems() {
        return items;
    }

    public void setItems(Items[] items) {
        this.items = items;
    }
}
