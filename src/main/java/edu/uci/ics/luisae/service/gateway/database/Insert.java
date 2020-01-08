package edu.uci.ics.luisae.service.gateway.database;

import edu.uci.ics.luisae.service.gateway.logger.ServiceLogger;
import edu.uci.ics.luisae.service.gateway.threadpool.ClientRequest;
import edu.uci.ics.luisae.service.gateway.utilities.Param;
import edu.uci.ics.luisae.service.gateway.utilities.Util;

import javax.ws.rs.core.Response;
import javax.xml.ws.Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class Insert {
    public static void InsertResponse(Response response, ClientRequest request, Connection con){
        int updated = 0;
        String query = "INSERT INTO responses (transaction_id,email,session_id,response,http_status)" +
                " VALUES(?,?,?,?,?);";
        ServiceLogger.LOGGER.info("entity: " + response.getEntity().toString());
        Param[] params = new Param[]{
                //THIS MAY CAUSE AN ERROR
                Param.create(Types.VARCHAR, request.getTransaction_id()),
                Param.create(Types.VARCHAR, request.getEmail()),
                Param.create(Types.VARCHAR, request.getSession_id()),
                Param.create(Types.VARCHAR, response.readEntity(String.class)),
                Param.create(Types.INTEGER, response.getStatus())
        };
        try{
            PreparedStatement ps = Util.prepareStatement(query,params,con);
            updated = ps.executeUpdate();
        }
        catch(SQLException e ){
            ServiceLogger.LOGGER.warning(e.getMessage());
            ServiceLogger.LOGGER.warning("updated: " + updated);
        }
    }
}
