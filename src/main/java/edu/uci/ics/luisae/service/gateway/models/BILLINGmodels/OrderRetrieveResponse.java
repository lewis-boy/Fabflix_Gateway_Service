package edu.uci.ics.luisae.service.gateway.models.BILLINGmodels;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uci.ics.luisae.service.gateway.Base.ResponseModel;
import edu.uci.ics.luisae.service.gateway.models.BILLINGmodels.BillingClasses.TransactionModel;

public class OrderRetrieveResponse extends ResponseModel {
    @JsonProperty(value = "transactions")
    private TransactionModel[] transactionModel;

    public OrderRetrieveResponse(){}

    @JsonProperty(value = "transactions")
    public TransactionModel[] getTransactionModel() {
        return transactionModel;
    }

    public void setTransactionModel(TransactionModel[] transactionModel) {
        this.transactionModel = transactionModel;
    }
}
