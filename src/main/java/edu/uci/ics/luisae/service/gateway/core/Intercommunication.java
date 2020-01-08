package edu.uci.ics.luisae.service.gateway.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uci.ics.luisae.service.gateway.Base.RequestModel;
import edu.uci.ics.luisae.service.gateway.Base.ResponseModel;
import edu.uci.ics.luisae.service.gateway.Base.Result;
import edu.uci.ics.luisae.service.gateway.GatewayService;
import edu.uci.ics.luisae.service.gateway.logger.ServiceLogger;
import edu.uci.ics.luisae.service.gateway.models.IDMmodels.request.PrivilegeRequest;
import edu.uci.ics.luisae.service.gateway.models.IDMmodels.request.SessionRequest;
import edu.uci.ics.luisae.service.gateway.models.IDMmodels.response.LoginAndSessionResponse;
import edu.uci.ics.luisae.service.gateway.models.IDMmodels.response.RegisterAndPrivilegeResponse;
import edu.uci.ics.luisae.service.gateway.utilities.Util;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

public class Intercommunication {


    //only POSTs
    public static <T,S extends ResponseModel> S idmIntercommunication(T request, Class<S> type, String endpoint){
        //TODO initialize request and
        String servicePath = getIdmPath();
        String endpointPath =  endpoint;
        ServiceLogger.LOGGER.info("url: " + servicePath);
        ServiceLogger.LOGGER.info("endpoint: " + endpointPath);
        Client client = ClientBuilder.newClient();
        client.register(JacksonFeature.class);

        WebTarget webTarget = client.target(servicePath).path(endpointPath);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
        Response jsonResponse = invocationBuilder.post(Entity.entity(request, MediaType.APPLICATION_JSON));

        return Util.modelMapper(jsonResponse.readEntity(String.class), type);
    }

    public static LoginAndSessionResponse sessionCheck(HttpHeaders headers){
        SessionRequest request = new SessionRequest(headers.getHeaderString("email")
                ,headers.getHeaderString("session_id"));
        LoginAndSessionResponse sessionResponse = Intercommunication.idmIntercommunication(request, LoginAndSessionResponse.class,
                GatewayService.getIdmConfigs().getSessionPath());
        return sessionResponse;
    }





    public static String getIdmPath(){
        return GatewayService.getIdmConfigs().getScheme() + GatewayService.getIdmConfigs().getHostName() + ":"
                + GatewayService.getIdmConfigs().getPort() + GatewayService.getIdmConfigs().getPath();
    }
    public static String getMoviesPath(){
        return GatewayService.getMoviesConfigs().getScheme() + GatewayService.getMoviesConfigs().getHostName() + ":"
                + GatewayService.getMoviesConfigs().getPort() + GatewayService.getMoviesConfigs().getPath();
    }
    public static String getBillingPath(){
        return GatewayService.getBillingConfigs().getScheme() + GatewayService.getBillingConfigs().getHostName() + ":"
                + GatewayService.getBillingConfigs().getPort() + GatewayService.getBillingConfigs().getPath();
    }

}
