package com.mycompany.clientapp.client;

import com.mycompany.clientapp.config.Configuration;
import com.mycompany.clientapp.config.exceptions.IPException;
import com.mycompany.clientapp.config.exceptions.PortException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

public class Client {

    /**
     * Creates new server-connection on provided configuration
     * @throws PortException on incorrect port
     * @throws IPException on incorrect IP
     * @throws IOException on socket errors
     */
    public Client() throws IOException, PortException, IPException {
        Configuration config = new Configuration();
        String ip = config.getIp();
        int port = config.getPort();
        try {
            Socket socket = new Socket(ip, port);
            Proccess proccess = new Proccess(socket);
            proccess.start();
        }catch(NullPointerException npe){
            System.err.println(npe.getMessage());
        }catch(ConnectException ce){
            System.err.println("Server has not finded");
        } catch (IOException ioe){
            System.err.println("Check connection");
        } catch (Exception e) {
            throw e;
        }
    }

}
