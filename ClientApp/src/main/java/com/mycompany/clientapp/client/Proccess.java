package com.mycompany.clientapp.client;

import com.mycompany.clientapp.config.Logger;
import com.mycompany.clientapp.config.exceptions.ExitException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class Proccess extends Thread {

    private BufferedReader in;
    private BufferedReader console;
    private PrintWriter out;
    private Socket socket;
    private Response response;

    public Proccess(Socket socket) throws NullPointerException {
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            console = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException ioe) {
            close();
            Logger.log(socket + " Proccess error!!!");
        }
    }

    public BufferedReader getIn() {
        return in;
    }

    /**
     * Creates new response thread on each console request to server
     */
    @Override
    public void run() {
        try {
            while (!interrupted()) {
                System.out.println("Enter your number");
                try {
                    String number = console.readLine();
                    exitMessage(number);
                    Integer.parseInt(number);
                    responseStart(number);
                    out.println(number);
                } catch (ExitException ee) {
                    Logger.log(ee.getMessage());
                } catch (SocketException se) {
                    close();
                } catch (NumberFormatException nfe) {
                    System.err.println("Your number is not correct");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Interrupt proccess thread
     */
    public void close() {
        try {
            if (response != null) {
                response.interrupt();
            }
            interrupt();
            in.close();
            out.close();
            socket.close();
            Logger.log("Program execution interrupted");
        } catch (IOException ioe) {
            Logger.log(socket + " Connection close");
        }
    }

    /**
     * Creates new response thread if current response = null
     *
     * @param message request-message
     */
    private void responseStart(String message) {
        if (response == null && !isInterrupted()) {
            response = new Response(this);
        }
        onResponseTerminated();
        response.start(message);
    }

    /**
     * Creates new response if response is terminated
     */
    private void onResponseTerminated() {
        if (response.getState() == State.TERMINATED && !isInterrupted()) {
            response = new Response(this);
        }
    }

    /**
     * Interrupt proccess and exit programm if message = "exit"
     *
     * @param message request-message
     * @throws ExitException on "exit" message
     */
    private void exitMessage(String message) throws ExitException {
        if (message.equals("exit")) {
            close();
            throw new ExitException();
        }
    }

}
