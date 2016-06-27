package servertestapp.server;

import java.io.IOException;
import servertestapp.config.Configuration;
import servertestapp.config.exceptions.PortException;
import servertestapp.server.listener.Connection;

public class Server {

    /**
     * Creates new Connections on provided configuration
     */
    public Server() {
        try {
            Configuration config = new Configuration();
            Connection connection = new Connection(config.getPort());
            connection.start();
        } catch (IOException ioe) {
            System.err.println("Error!!! Check your properties file");
        } catch (PortException pex) {
            System.err.println(pex.getMessage());
        }
    }

}
