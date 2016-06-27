package servertestapp;

import servertestapp.server.Server;

public class Application {

    /**
     * Start server
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            new Server();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
