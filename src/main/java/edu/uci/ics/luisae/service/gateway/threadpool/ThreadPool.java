package edu.uci.ics.luisae.service.gateway.threadpool;

import edu.uci.ics.luisae.service.gateway.logger.ServiceLogger;

import javax.ws.rs.client.Client;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool
{
    private int numWorkers;

    private ArrayList<Worker> workers;
    private BlockingQueue<ClientRequest> queue;
    //TODO test here
    //private BlockingQueue<Integer> intQueue;

    /*
     * BlockingQueue is a interface that allows us
     * to choose the type of implementation of the queue.
     * In this case we are using a LinkedBlockingQueue.
     *
     * BlockingQueue as the name implies will block
     * any thread requesting from it if the queue is empty
     * but only if you use the correct function
     */
    private ThreadPool(int numWorkers)
    {
        this.numWorkers = numWorkers;

        workers = new ArrayList<>();
        queue = new LinkedBlockingQueue<>();
        //TODO test
        //intQueue = new LinkedBlockingQueue<>();
        for(int i = 0; i < numWorkers; i++){
            workers.add(Worker.CreateWorker(i,this));
            workers.get(i).start();
        }

        // TODO more work is needed to create the threads
    }

    public static ThreadPool createThreadPool(int numWorkers)
    {
        return new ThreadPool(numWorkers);
    }

    /*
     * Note that this function only has package scoped
     * as it should only be called with the package by
     * a worker
     * 
     * Make sure to use the correct functions that will
     * block a thread if the queue is unavailable or empty
     */
    ClientRequest takeRequest()
    {
        // TODO *take* the request from the queue
        try {
            return queue.take();
        }
        catch(InterruptedException e){
            ServiceLogger.LOGGER.warning("TAKE: Interrupted while waiting ");
            ServiceLogger.LOGGER.warning(e.getMessage());
            ServiceLogger.LOGGER.warning(e.getLocalizedMessage());
            return null;
        }
    }

    //TODO test here
//    Integer takeInt(){
//        try{
//            return intQueue.take();
//        }catch(InterruptedException e){
//            ServiceLogger.LOGGER.warning("TAKE: Interrupted while waiting ");
//            ServiceLogger.LOGGER.warning(e.getMessage());
//            ServiceLogger.LOGGER.warning(e.getLocalizedMessage());
//            return null;
//        }
//    }
//
//    public void putInt(int i){
//        try{
//            Integer k = new Integer(i);
//            intQueue.put(k);
//        }catch(InterruptedException e){
//            ServiceLogger.LOGGER.warning("PUT: Interrupted while waiting ");
//            ServiceLogger.LOGGER.warning(e.getMessage());
//            ServiceLogger.LOGGER.warning(e.getLocalizedMessage());
//        }
//    }






    public void putRequest(ClientRequest request)
    {
        // TODO *put* the request into the queue
        try{
            queue.put(request);
        }
        catch(InterruptedException e){
            ServiceLogger.LOGGER.warning("PUT: Interrupted while waiting ");
            ServiceLogger.LOGGER.warning(e.getMessage());
            ServiceLogger.LOGGER.warning(e.getLocalizedMessage());
        }
    }

}
