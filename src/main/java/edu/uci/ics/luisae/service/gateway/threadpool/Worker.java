package edu.uci.ics.luisae.service.gateway.threadpool;

import edu.uci.ics.luisae.service.gateway.Base.Tag;
import edu.uci.ics.luisae.service.gateway.GatewayService;
import edu.uci.ics.luisae.service.gateway.database.Insert;
import edu.uci.ics.luisae.service.gateway.logger.ServiceLogger;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.util.List;
import java.util.Map;


public class Worker extends Thread {
    int id;
    ThreadPool threadPool;
    //hikari threadpool

    private Worker(int id, ThreadPool threadPool) {
        this.id = id;
        this.threadPool = threadPool;
    }

    private void attachHeaders(Invocation.Builder invocation, ClientRequest request){
        invocation.header("email", request.getEmail());
        invocation.header("session_id",request.getSession_id());
        invocation.header("transaction_id", request.getTransaction_id());
    }

    public static Worker CreateWorker(int id, ThreadPool threadPool) {
        return new Worker(id,threadPool);
    }

    public void process(ClientRequest request, Connection con) {
        //todo how would i remake queries that have path params in the middle like /get/tt13737/get/tt010101
        ServiceLogger.LOGGER.info("Worker Processing");
        request.print();
            Response response;
            Invocation.Builder invocationBuilder;
            Client client = ClientBuilder.newClient();
            client.register(JacksonFeature.class);
            //WebTarget webTarget = client.target(request.getURI()+request.getEndpoint());
            WebTarget webTarget = client.target(request.getURI()).path(request.getEndpoint());
            ServiceLogger.LOGGER.info("WORKER URI: " + webTarget.getUri().toString());
            if(request.getType().getTag() == Tag.GET){
                if(request.getPathParams()!= null) {
                    for (Map.Entry<String, List<String>> entry : request.getPathParams().entrySet()) {
                        ServiceLogger.LOGGER.info("Key: " + entry.getKey() + "\tValue: " + entry.getValue().get(0));
                        webTarget = webTarget.resolveTemplate(entry.getKey(), entry.getValue().get(0));
                    }
                }
                if(request.getQueryParams()!= null){
                    for(Map.Entry<String, List<String>> entry : request.getQueryParams().entrySet()) {
                        ServiceLogger.LOGGER.info("Key: " + entry.getKey() + "\tValue: " + entry.getValue().get(0));
                        webTarget = webTarget.queryParam(entry.getKey(), entry.getValue().get(0));
                    }
                }
                invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
                attachHeaders(invocationBuilder, request);
                ServiceLogger.LOGGER.info(webTarget.toString());

                response = invocationBuilder.get();
            }
            else if(request.getType().getTag() == Tag.POST){
                invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON_TYPE);
                attachHeaders(invocationBuilder, request);
                //TODO testing
                request.print();
                response = invocationBuilder.post(Entity.entity(request.getRequest(),MediaType.APPLICATION_JSON));
            }
            else{
                response = null;
            }
            if(response.getStatus() == 500){
                ServiceLogger.LOGGER.warning("Grizzly Connectiono error");
            }
            ServiceLogger.LOGGER.info("Response code: " + response.getStatus());
            Insert.InsertResponse(response,request,con);
            ServiceLogger.LOGGER.info("Worker done");
    }

    @Override
    public void run() {
        while (true) {
            //TEST
//            Integer integer = threadPool.takeInt();
//            ServiceLogger.LOGGER.info("Worker #" + id + "  " + integer.toString());
            //TEST END

            ClientRequest request = threadPool.takeRequest();
            Connection con = GatewayService.getConnectionPoolManager().requestCon();
            process(request, con);
            GatewayService.getConnectionPoolManager().releaseCon(con);
        }
    }

}
