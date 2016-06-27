package com.mycompany.clientapp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.SocketException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Thread listen socket in. If response have done, write it to console
 */
public class Response extends Thread {

    private BufferedReader in;
    private List<String> messages = new CopyOnWriteArrayList<>();
    private final Proccess proccess;

    public Response(Proccess proccess) {
        this.in = proccess.getIn();
        this.proccess = proccess;
    }

    /**
     * Waiting for server response
     */
    @Override
    public void run() {
        while (!interrupted()) {
            try {
                String response = in.readLine();
                System.out.println("Response on your number: " + messages.remove(0) + " is " + response);
            } catch (SocketException se) {
                proccess.close();
                System.err.println("Server had disconected");
            } catch (IOException ioe) {
                System.err.println("Input stream error " + ioe.getMessage());
            } finally {
                interrupt();
            }
            stopOnEmptyMessages();
        }
    }

    /**
     * Start new response
     *
     * @param message for server proccesing
     */
    public synchronized void start(String message) {
        addRequest(message);
        if (isInterrupted() || getState() == State.NEW) {
            super.start();
        }
    }

    /**
     * Add new message for response waiting
     *
     * @param message to add
     */
    private void addRequest(String message) {
        messages.add(message);
    }

    /**
     * Interrupt thread on messages list is empty
     */
    private void stopOnEmptyMessages() {
        if (messages.isEmpty()) {
            interrupt();
        }
    }

}
