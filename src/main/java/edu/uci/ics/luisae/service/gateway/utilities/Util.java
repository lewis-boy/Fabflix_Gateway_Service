package edu.uci.ics.luisae.service.gateway.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uci.ics.luisae.service.gateway.Base.Headers;
import edu.uci.ics.luisae.service.gateway.logger.ServiceLogger;
import edu.uci.ics.luisae.service.gateway.models.GatewayResponse;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Util {
    public static PreparedStatement prepareStatement(String query, Param[] paramList, Connection con)
            throws SQLException
    {
        ServiceLogger.LOGGER.info("Preparing Statement");

        int count = 1;

        PreparedStatement ps = con.prepareStatement(query);

        for (Param param : paramList)
            ps.setObject(count++, param.getParam(), param.getType());

        ServiceLogger.LOGGER.info("QueryReady: " + ps.toString());

        return ps;
    }

    public static <T> T modelMapper(String jsonString, Class<T> className)
    {
        ObjectMapper mapper = new ObjectMapper();

        ServiceLogger.LOGGER.info("Mapping object");

        try {
            return mapper.readValue(jsonString, className);

        } catch (IOException e) {
            ServiceLogger.LOGGER.info("Mapping Object Failed: " + e.getMessage());
            ServiceLogger.LOGGER.info(e.getLocalizedMessage());
            return null;

        }
    }

    public static Headers createHeaders(HttpHeaders headers){
        Headers heads = new Headers();
        heads.setEmail(headers.getHeaderString("email"));
        heads.setSession_id(headers.getHeaderString("session_id"));
        heads.setTransaction_id(headers.getHeaderString("transaction_id"));
        return heads;
    }

    public static String getQueryParams(UriInfo uriInfo){
        String queryParams = "?";
        MultivaluedMap<String,String> uriMap = uriInfo.getQueryParameters();
        for(Map.Entry<String,List<String>> entry : uriMap.entrySet())
            queryParams += entry.getKey() + "=" + entry.getValue().get(0) + "&";
        queryParams = queryParams.substring(0,queryParams.length()-1);
        //ServiceLogger.LOGGER.info(queryParams);
        return queryParams;
    }

    public static Response buildGeneralResponse(Headers heads, GatewayResponse response){
        return Response.status(response.getHttp_status()).entity(response.getResponse()).header("email",heads.getEmail())
        .header("session_id",heads.getSession_id()).header("transaction_id",heads.getTransaction_id()).build();
    }



}
