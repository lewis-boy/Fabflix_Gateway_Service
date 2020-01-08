package edu.uci.ics.luisae.service.gateway.threadpool;

import edu.uci.ics.luisae.service.gateway.Base.RequestModel;
import edu.uci.ics.luisae.service.gateway.Base.RequestType;
import edu.uci.ics.luisae.service.gateway.logger.ServiceLogger;

import javax.ws.rs.core.MultivaluedMap;
import java.util.List;
import java.util.Map;

public class ClientRequest {
    private String email;
    private String session_id;
    private String transaction_id;
    private String URI;
    private String endpoint;

    //TODO Add request model data members
    private RequestModel request;
    private RequestType type;
    private MultivaluedMap<String, String> queryParams;
    private MultivaluedMap<String, String> pathParams;


    public ClientRequest() {
    }
    //TODO add printing statement to check under the hood
    public ClientRequest(String email,
                         String session_id,
                         String transaction_id,
                         String URI,
                         String endpoint,
                         RequestModel request,
                         RequestType type,
                         MultivaluedMap<String, String> queryParams,
                         MultivaluedMap<String,String> pathParams){
        this.email = email;
        this.session_id = session_id;
        this.transaction_id = transaction_id;
        this.URI = URI;
        this.endpoint = endpoint;
        this.request = request;
        this.type = type;
        this.queryParams = queryParams;
        this.pathParams = pathParams;
    }

    public void print(){
        ServiceLogger.LOGGER.info(email);
        ServiceLogger.LOGGER.info(session_id);
        ServiceLogger.LOGGER.info(transaction_id);
        ServiceLogger.LOGGER.info(URI);
        ServiceLogger.LOGGER.info(endpoint);
    }

    public MultivaluedMap<String, String> getPathParams() {
        return pathParams;
    }

    public void setPathParams(MultivaluedMap<String, String> pathParams) {
        this.pathParams = pathParams;
    }

    public MultivaluedMap<String, String> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(MultivaluedMap<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public RequestModel getRequest() {
        return request;
    }

    public void setRequest(RequestModel request) {
        this.request = request;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
