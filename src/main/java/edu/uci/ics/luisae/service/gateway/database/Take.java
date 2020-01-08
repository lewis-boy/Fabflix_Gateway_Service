package edu.uci.ics.luisae.service.gateway.database;

import edu.uci.ics.luisae.service.gateway.GatewayService;
import edu.uci.ics.luisae.service.gateway.logger.ServiceLogger;
import edu.uci.ics.luisae.service.gateway.models.GatewayResponse;
import edu.uci.ics.luisae.service.gateway.utilities.Util;

import java.lang.invoke.SerializedLambda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Take {
    public static GatewayResponse findResponse(String tid){
        GatewayResponse returnResponse;
        String query = "SELECT JSON_OBJECT('transaction_id',transaction_id,'response',response,'http_status',http_status)\n" +
                "    as responseFound FROM responses WHERE transaction_id =?;";
        ServiceLogger.LOGGER.info("Preparing statement to find response");
        Connection con =  GatewayService.getConnectionPoolManager().requestCon();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,tid);
            ServiceLogger.LOGGER.info(ps.toString());
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                //ran into an error, we cannot rs.get whatever after we close the connection
                ServiceLogger.LOGGER.info("found response");
                returnResponse = Util.modelMapper(rs.getString("responseFound"), GatewayResponse.class);
                deleteResponse(tid, con);
                ServiceLogger.LOGGER.info("completed delete");
                closeConnection(con);
                ServiceLogger.LOGGER.info("completed closing xonnection");
                return returnResponse;
            }else{
                ServiceLogger.LOGGER.info("rs next was empty");
                closeConnection(con);
                return null;}

        }catch (SQLException e){
            ServiceLogger.LOGGER.info(e.getMessage());
            closeConnection(con);
            return null;
        }

    }

    private static void deleteResponse(String tid, Connection con){
        String query = "DELETE FROM responses WHERE transaction_id = ?;";
        try{
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,tid);
            ps.executeUpdate();
        }catch(SQLException e){
            ServiceLogger.LOGGER.info(e.getMessage());
        }
    }
    private static void closeConnection(Connection con){
        GatewayService.getConnectionPoolManager().releaseCon(con);
    }
}
