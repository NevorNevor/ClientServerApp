package servertestapp.server.listener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import servertestapp.config.Logger;
import servertestapp.server.Proccess;

public class Connection extends Thread {

    private ServerSocket serverSocket;
    private List<Proccess> proccessies = new ArrayList<>();

    /**
     * Constructor
     * @param port
     * @throws IOException on socket errors 
     */
    public Connection(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        Logger.log("Port: " + port + " is listening");
    }

    /**
     * Waiting for new client connection.
     * On new connection creates new user proccess thread
     */
    @Override
    public void run() {
        try {
            while (!interrupted()) {
                Socket socket = serverSocket.accept();
                Proccess process = new Proccess(socket);
                proccessies.add(process);
                process.start();
                Logger.log(socket + "appended");
            }
        } catch (IOException ioe) {
            closeAll();
        }
    }

    /**
     * Close all started proccesies
     * @return proccess list
     */
    private List<Proccess> closeAll() {
        for(Proccess proccess : proccessies){
            proccess.close();
        }
        return proccessies;
    }

}
