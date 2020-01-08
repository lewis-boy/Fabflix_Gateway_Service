package edu.uci.ics.luisae.service.gateway.resources;

import edu.uci.ics.luisae.service.gateway.Base.Headers;
import edu.uci.ics.luisae.service.gateway.Base.RequestType;
import edu.uci.ics.luisae.service.gateway.GatewayService;
import edu.uci.ics.luisae.service.gateway.core.Intercommunication;
import edu.uci.ics.luisae.service.gateway.database.Take;
import edu.uci.ics.luisae.service.gateway.logger.ServiceLogger;
import edu.uci.ics.luisae.service.gateway.models.BILLINGmodels.DeleteRequest;
import edu.uci.ics.luisae.service.gateway.models.BILLINGmodels.InsertUpdateRequest;
import edu.uci.ics.luisae.service.gateway.models.BILLINGmodels.RetrieveClearPlaceRequest;
import edu.uci.ics.luisae.service.gateway.models.GatewayResponse;
import edu.uci.ics.luisae.service.gateway.models.IDMmodels.request.PrivilegeRequest;
import edu.uci.ics.luisae.service.gateway.models.IDMmodels.request.RegisterAndLoginRequest;
import edu.uci.ics.luisae.service.gateway.models.IDMmodels.request.SessionRequest;
import edu.uci.ics.luisae.service.gateway.models.MOVIEmodels.ThumbnailRequest;
import edu.uci.ics.luisae.service.gateway.threadpool.ClientRequest;
import edu.uci.ics.luisae.service.gateway.transaction.TransactionGenerator;
import edu.uci.ics.luisae.service.gateway.utilities.Util;
import org.apache.commons.text.StringEscapeUtils;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;


@Path("g")
public class GatewayEndpoints {

    @Path("idm/register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response apiRegister(String jsonText){
        ServiceLogger.LOGGER.info("Inside register idm");
        //checking session
        //LoginAndSessionResponse sessionResponse = Intercommunication.sessionCheck(headers);
//        if(sessionResponse != null)
//            return Response.status(Response.Status.OK).entity(sessionResponse).build();
        String transactionId = TransactionGenerator.generate();
        //map jsontext/create request model
        RegisterAndLoginRequest request = Util.modelMapper(jsonText, RegisterAndLoginRequest.class);
        if(request == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        //build clientRequest
        ClientRequest cr = new ClientRequest(request.getEmail(),
                null,
                transactionId,
                Intercommunication.getIdmPath(),
                GatewayService.getIdmConfigs().getRegisterPath(),
                request,
                RequestType.IDM_REGISTER,null,null);
        GatewayService.getThreadPool().putRequest(cr);
        //TODO get request delay from configs and find out what message to send
        return Response.status(Response.Status.NO_CONTENT).header("transaction_id",transactionId)
                .header("request_delay", GatewayService.getThreadConfigs().getRequestDelay()).header("Please wait...", "String").build();
    }

    @Path("idm/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response apiLogin(String jsonText){
//        LoginAndSessionResponse sessionResponse = Intercommunication.sessionCheck(headers);
//        if(sessionResponse != null)
//            return Response.status(Response.Status.OK).entity(sessionResponse).build();
        String transactionId = TransactionGenerator.generate();
        RegisterAndLoginRequest request = Util.modelMapper(jsonText, RegisterAndLoginRequest.class);
        if(request == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        //build clientRequest
        ClientRequest cr = new ClientRequest(request.getEmail(),
                null,
                transactionId,
                Intercommunication.getIdmPath(),
                GatewayService.getIdmConfigs().getLoginPath(),
                request,
                RequestType.IDM_LOGIN,null,null);
        GatewayService.getThreadPool().putRequest(cr);
        //TODO get request delay from configs and find out what message to send
        return Response.status(Response.Status.NO_CONTENT).header("transaction_id",transactionId)
                .header("request_delay", GatewayService.getThreadConfigs().getRequestDelay()).header("Please wait...", "String").build();
    }

    @Path("idm/session")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response apiSession(String jsonText){
        String transactionId = TransactionGenerator.generate();
        SessionRequest request = Util.modelMapper(jsonText, SessionRequest.class);
        if(request == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        //build clientRequest
        ClientRequest cr = new ClientRequest(request.getEmail(),
                null,
                transactionId,
                Intercommunication.getIdmPath(),
                GatewayService.getIdmConfigs().getSessionPath(),
                request,
                RequestType.IDM_SESSION,null,null);
        GatewayService.getThreadPool().putRequest(cr);
        //TODO get request delay from configs and find out what message to send
        return Response.status(Response.Status.NO_CONTENT).header("transaction_id",transactionId)
                .header("request_delay", GatewayService.getThreadConfigs().getRequestDelay()).header("Please wait...", "String").build();
    }

    @Path("idm/privilege")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response apiPrivilege(String jsonText){
        String transactionId = TransactionGenerator.generate();
        PrivilegeRequest request = Util.modelMapper(jsonText, PrivilegeRequest.class);
        if(request == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        //build clientRequest
        ClientRequest cr = new ClientRequest(request.getEmail(),
                null,
                transactionId,
                Intercommunication.getIdmPath(),
                GatewayService.getIdmConfigs().getPrivilegePath(),
                request,
                RequestType.IDM_PRIVILEGE,null,null);
        GatewayService.getThreadPool().putRequest(cr);
        //TODO get request delay from configs and find out what message to send
        return Response.status(Response.Status.NO_CONTENT).header("transaction_id",transactionId)
                .header("request_delay", GatewayService.getThreadConfigs().getRequestDelay()).header("Please wait...", "String").build();
    }


    @Path("movies/search")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response apiMovieSearch(@Context HttpHeaders headers, @Context UriInfo uriInfo){
        String transactionId = TransactionGenerator.generate();
        ClientRequest cr = new ClientRequest(headers.getHeaderString("email"),
                headers.getHeaderString("session_id"),
                transactionId,
                Intercommunication.getMoviesPath(),
                GatewayService.getMoviesConfigs().getSearchPath(),
                null,
                RequestType.MOVIES_SEARCH,
                uriInfo.getQueryParameters(),
                null);
        GatewayService.getThreadPool().putRequest(cr);
        return Response.status(Response.Status.NO_CONTENT).header("transaction_id",transactionId)
                .header("request_delay", GatewayService.getThreadConfigs().getRequestDelay()).header("Please wait...", "String").build();
    }

    @Path("movies/get/{movie_id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response apiMovieGetSearch(@Context HttpHeaders headers, @PathParam("movie_id")String  movie_id){
        String transactionId = TransactionGenerator.generate();
        ClientRequest cr = new ClientRequest(headers.getHeaderString("email"),
                headers.getHeaderString("session_id"),
                transactionId,
                Intercommunication.getMoviesPath(),
                GatewayService.getMoviesConfigs().getGetPath()+movie_id,
                null,
                RequestType.MOVIES_GET,
                null,
                null);
        GatewayService.getThreadPool().putRequest(cr);
        return Response.status(Response.Status.NO_CONTENT).header("transaction_id",transactionId)
                .header("request_delay", GatewayService.getThreadConfigs().getRequestDelay()).header("Please wait...", "String").build();
    }

    @Path("movies/browse/{phrase}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response apiMovieGetPhrase(@Context HttpHeaders headers, @Context UriInfo uriInfo){
        String transactionId = TransactionGenerator.generate();
        ClientRequest cr = new ClientRequest(headers.getHeaderString("email"),
                headers.getHeaderString("session_id"),
                transactionId,
                Intercommunication.getMoviesPath(),
                GatewayService.getMoviesConfigs().getBrowsePath() + uriInfo.getPathParameters().get("phrase"),
                null,
                RequestType.MOVIES_BROWSE,
                uriInfo.getQueryParameters(),
                null);
        GatewayService.getThreadPool().putRequest(cr);
        return Response.status(Response.Status.NO_CONTENT).header("transaction_id",transactionId)
                .header("request_delay", GatewayService.getThreadConfigs().getRequestDelay()).header("Please wait...", "String").build();

    }

    @Path("movies/thumbnail")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response apiMovieGetThumbnail(@Context HttpHeaders headers, String jsonText){
        String transactionId = TransactionGenerator.generate();
        ThumbnailRequest request = Util.modelMapper(jsonText, ThumbnailRequest.class);
        if(request == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        ClientRequest cr = new ClientRequest(headers.getHeaderString("email"),
                headers.getHeaderString("session_id"),
                transactionId,
                Intercommunication.getMoviesPath(),
                GatewayService.getMoviesConfigs().getThumbnailPath(),
                request,
                RequestType.MOVIES_THUMBNAIL,
                null,
                null);
        GatewayService.getThreadPool().putRequest(cr);
        return Response.status(Response.Status.NO_CONTENT).header("transaction_id",transactionId)
                .header("request_delay", GatewayService.getThreadConfigs().getRequestDelay()).header("Please wait...", "String").build();

    }

    @Path("movies/people")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response apiMovieGetPeople(@Context HttpHeaders headers, @Context UriInfo uriInfo){
        String transactionId = TransactionGenerator.generate();
        ClientRequest cr = new ClientRequest(headers.getHeaderString("email"),
                headers.getHeaderString("session_id"),
                transactionId,
                Intercommunication.getMoviesPath(),
                GatewayService.getMoviesConfigs().getPeoplePath(),
                null,
                RequestType.MOVIES_PEOPLE,
                uriInfo.getQueryParameters(),
                null);
        GatewayService.getThreadPool().putRequest(cr);
        return Response.status(Response.Status.NO_CONTENT).header("transaction_id",transactionId)
                .header("request_delay", GatewayService.getThreadConfigs().getRequestDelay()).header("Please wait...", "String").build();

    }

    @Path("movies/people/search")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response apiMoviePeopleSearch(@Context HttpHeaders headers, @Context UriInfo uriInfo){
        String transactionId = TransactionGenerator.generate();
        ClientRequest cr = new ClientRequest(headers.getHeaderString("email"),
                headers.getHeaderString("session_id"),
                transactionId,
                Intercommunication.getMoviesPath(),
                GatewayService.getMoviesConfigs().getPeopleSearchPath(),
                null,
                RequestType.MOVIES_PEOPLE_SEARCH,
                uriInfo.getQueryParameters(),
                null);
        GatewayService.getThreadPool().putRequest(cr);
        return Response.status(Response.Status.NO_CONTENT).header("transaction_id",transactionId)
                .header("request_delay", GatewayService.getThreadConfigs().getRequestDelay()).header("Please wait...", "String").build();

    }

    @Path("movies/people/get/{person_id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response apiMovieGetPersonId(@Context HttpHeaders headers, @Context UriInfo uriInfo){
        String transactionId = TransactionGenerator.generate();
        ClientRequest cr = new ClientRequest(headers.getHeaderString("email"),
                headers.getHeaderString("session_id"),
                transactionId,
                Intercommunication.getMoviesPath(),
                GatewayService.getMoviesConfigs().getPeopleGetPath() + uriInfo.getPathParameters().get("person_id"),
                null,
                RequestType.MOVIES_PEOPLE_GET,
                null,
                null);
        GatewayService.getThreadPool().putRequest(cr);
        return Response.status(Response.Status.NO_CONTENT).header("transaction_id",transactionId)
                .header("request_delay", GatewayService.getThreadConfigs().getRequestDelay()).header("Please wait...", "String").build();

    }

    @Path("billing/cart/insert")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response apiBillingCartInsert(@Context HttpHeaders headers,
                                         String jsonText){
        String transactionId = TransactionGenerator.generate();
        InsertUpdateRequest request = Util.modelMapper(jsonText, InsertUpdateRequest.class);
        if(request == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        ClientRequest cr = new ClientRequest(headers.getHeaderString("email"),
                headers.getHeaderString("session_id"),
                transactionId,
                Intercommunication.getBillingPath(),
                GatewayService.getBillingConfigs().getCartInsertPath(),
                request,
                RequestType.BILLING_CART_INSERT,
                null,
                null);
        GatewayService.getThreadPool().putRequest(cr);
        return Response.status(Response.Status.NO_CONTENT).header("transaction_id",transactionId)
                .header("request_delay", GatewayService.getThreadConfigs().getRequestDelay()).header("Please wait...", "String").build();

    }

    @Path("billing/cart/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response apiBillingCartUpdate(@Context HttpHeaders headers,
                                         String jsonText){
        String transactionId = TransactionGenerator.generate();
        InsertUpdateRequest request = Util.modelMapper(jsonText, InsertUpdateRequest.class);
        if(request == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        ClientRequest cr = new ClientRequest(headers.getHeaderString("email"),
                headers.getHeaderString("session_id"),
                transactionId,
                Intercommunication.getBillingPath(),
                GatewayService.getBillingConfigs().getCartUpdatePath(),
                request,
                RequestType.BILLING_CART_UPDATE,
                null,
                null);
        GatewayService.getThreadPool().putRequest(cr);
        return Response.status(Response.Status.NO_CONTENT).header("transaction_id",transactionId)
                .header("request_delay", GatewayService.getThreadConfigs().getRequestDelay()).header("Please wait...", "String").build();

    }

    @Path("billing/cart/delete")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response apiBillingCartDelete(@Context HttpHeaders headers,
                                         String jsonText){
        String transactionId = TransactionGenerator.generate();
        DeleteRequest request = Util.modelMapper(jsonText, DeleteRequest.class);
        if(request == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        ClientRequest cr = new ClientRequest(headers.getHeaderString("email"),
                headers.getHeaderString("session_id"),
                transactionId,
                Intercommunication.getBillingPath(),
                GatewayService.getBillingConfigs().getCartDeletePath(),
                request,
                RequestType.BILLING_CART_DELETE,
                null,
                null);
        GatewayService.getThreadPool().putRequest(cr);
        return Response.status(Response.Status.NO_CONTENT).header("transaction_id",transactionId)
                .header("request_delay", GatewayService.getThreadConfigs().getRequestDelay()).header("Please wait...", "String").build();

    }

    @Path("billing/cart/retrieve")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response apiBillingCartRetrieve(@Context HttpHeaders headers,
                                         String jsonText){
        String transactionId = TransactionGenerator.generate();
        RetrieveClearPlaceRequest request = Util.modelMapper(jsonText, RetrieveClearPlaceRequest.class);
        if(request == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        ClientRequest cr = new ClientRequest(headers.getHeaderString("email"),
                headers.getHeaderString("session_id"),
                transactionId,
                Intercommunication.getBillingPath(),
                GatewayService.getBillingConfigs().getCartRetrievePath(),
                request,
                RequestType.BILLING_CART_RETRIEVE,
                null,
                null);
        GatewayService.getThreadPool().putRequest(cr);
        return Response.status(Response.Status.NO_CONTENT).header("transaction_id",transactionId)
                .header("request_delay", GatewayService.getThreadConfigs().getRequestDelay()).header("Please wait...", "String").build();

    }

    @Path("billing/cart/clear")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response apiBillingCartClear(@Context HttpHeaders headers,
                                           String jsonText){
        String transactionId = TransactionGenerator.generate();
        RetrieveClearPlaceRequest request = Util.modelMapper(jsonText, RetrieveClearPlaceRequest.class);
        if(request == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        ClientRequest cr = new ClientRequest(headers.getHeaderString("email"),
                headers.getHeaderString("session_id"),
                transactionId,
                Intercommunication.getBillingPath(),
                GatewayService.getBillingConfigs().getCartClearPath(),
                request,
                RequestType.BILLING_CART_CLEAR,
                null,
                null);
        GatewayService.getThreadPool().putRequest(cr);
        return Response.status(Response.Status.NO_CONTENT).header("transaction_id",transactionId)
                .header("request_delay", GatewayService.getThreadConfigs().getRequestDelay()).header("Please wait...", "String").build();

    }

    @Path("billing/order/place")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response apiBillingOrderPlace(@Context HttpHeaders headers,
                                        String jsonText){
        String transactionId = TransactionGenerator.generate();
        RetrieveClearPlaceRequest request = Util.modelMapper(jsonText, RetrieveClearPlaceRequest.class);
        if(request == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        ClientRequest cr = new ClientRequest(headers.getHeaderString("email"),
                headers.getHeaderString("session_id"),
                transactionId,
                Intercommunication.getBillingPath(),
                GatewayService.getBillingConfigs().getOrderPlacePath(),
                request,
                RequestType.BILLING_ORDER_PLACE,
                null,
                null);
        GatewayService.getThreadPool().putRequest(cr);
        return Response.status(Response.Status.NO_CONTENT).header("transaction_id",transactionId)
                .header("request_delay", GatewayService.getThreadConfigs().getRequestDelay()).header("Please wait...", "String").build();

    }

    @Path("billing/order/retrieve")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response apiBillingOrderRetrieve(@Context HttpHeaders headers,
                                         String jsonText){
        String transactionId = TransactionGenerator.generate();
        RetrieveClearPlaceRequest request = Util.modelMapper(jsonText, RetrieveClearPlaceRequest.class);
        if(request == null)
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        ClientRequest cr = new ClientRequest(headers.getHeaderString("email"),
                headers.getHeaderString("session_id"),
                transactionId,
                Intercommunication.getBillingPath(),
                GatewayService.getBillingConfigs().getOrderRetrievePath(),
                request,
                RequestType.BILLING_ORDER_RETRIEVE,
                null,
                null);
        GatewayService.getThreadPool().putRequest(cr);
        return Response.status(Response.Status.NO_CONTENT).header("transaction_id",transactionId)
                .header("request_delay", GatewayService.getThreadConfigs().getRequestDelay()).header("Please wait...", "String").build();

    }





    @Path("report")
    @GET
    @Produces
    public Response report(@Context HttpHeaders headers){
        //TODO check session still, session may have expired or needs to be updated
        //TODO for login you dont have to check the session
        //TODO if session update send new session back
        //we are assuming that between the time of placing an order and checking up on it through report, the session will never expire
        //but it can be revoked, so we will always assume there will be a session_id available to place in the response back.
        Headers heads = Util.createHeaders(headers);
//        LoginAndSessionResponse sessionResponse = Intercommunication.sessionCheck(headers);
//        heads.setSession_id(sessionResponse.getSession_id());


        //hikari is good because we dont have to disconnenct and reconnect every 30 mins, hiari does it for us
        GatewayResponse response = Take.findResponse(headers.getHeaderString("transaction_id").replace("\"",""));
        if(response == null){
            return Response.status(Response.Status.NO_CONTENT).header("transaction_id",headers.getHeaderString("transaction_id"))
                    .header("request_delay", GatewayService.getThreadConfigs().getRequestDelay()).header("message", "Please wait...").build();
        }
        return Util.buildGeneralResponse(heads,response);
    }





    @Path("test")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response test(@Context UriInfo uriInfo){
        Invocation.Builder invocationBuilder;
        Client client = ClientBuilder.newClient();
        client.register(JacksonFeature.class);
        //WebTarget webTarget = client.target(request.getURI()+request.getEndpoint());
        WebTarget webTarget = client.target(Intercommunication.getMoviesPath()).path(GatewayService.getMoviesConfigs().getGetPath());
        ServiceLogger.LOGGER.info("WORKER URI: " + webTarget.getUri().toString());
        return Response.status(Response.Status.OK).build();
    }
}
