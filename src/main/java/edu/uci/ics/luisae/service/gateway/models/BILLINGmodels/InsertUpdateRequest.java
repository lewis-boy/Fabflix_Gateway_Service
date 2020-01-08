package edu.uci.ics.luisae.service.gateway.models.BILLINGmodels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InsertUpdateRequest extends DeleteRequest {
    @JsonProperty(value = "quantity", required = true)
    private Integer quantity;

    public InsertUpdateRequest(){}

    @JsonProperty(value = "quantity", required = true)
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
