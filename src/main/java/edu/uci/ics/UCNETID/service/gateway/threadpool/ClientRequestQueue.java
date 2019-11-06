package edu.uci.ics.UCNETID.service.gateway.threadpool;

public class ClientRequestQueue {
    private ListNode head;
    private ListNode tail;

    public ClientRequestQueue() {

    }

    public synchronized void enqueue(ClientRequest clientRequest) {

    }

    public synchronized ClientRequest dequeue() {
        return null;
    }

    boolean isEmpty() {
        return true;
    }
}
