package edu.uci.ics.luisae.service.gateway.utilities;

import edu.uci.ics.luisae.service.gateway.logger.ServiceLogger;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;

public class Parse {
    public static MultivaluedMap Head(HttpHeaders headers){
        MultivaluedMap<String,String> map = headers.getRequestHeaders();
        for(String s: map.keySet()){
            ServiceLogger.LOGGER.info("key: " + s  + " \tvalue: " + map.getFirst(s));
        }
        return null;
    }
}
