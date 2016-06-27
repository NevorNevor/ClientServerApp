package servertestapp.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import servertestapp.config.Logger;
import servertestapp.logic.Factorization;

public class Proccess extends Thread {

    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;

    /**
     * Creates new proccess on every client connection
     *
     * @param socket
     */
    public Proccess(Socket socket) throws NullPointerException {
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException ioe) {
            close();
            Logger.log(socket + " Proccess error!!!");
        }
    }

    /**
     * Waiting for client request Invokes factorization Send factors as response
     */
    @Override
    public void run() {
        try {
            while (!interrupted()) {
                String number = readNumber();
                Logger.log("Recived number: " + number + " from socket: " + socket);
                Factorization factor = new Factorization(Integer.parseInt(number));
                out.println(factor.factor());
            }
        } catch (NumberFormatException nfe) {
            out.println("Incorrect number");
        } catch (NullPointerException npe) {
            System.err.println(npe.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            close();
            System.err.println("Client: " + socket + " is lost");
        }
    }

    /**
     * Close proccess
     */
    public void close() {
        try {
            interrupt();
            in.close();
            out.close();
            socket.close();
        } catch (IOException ioe) {
            Logger.log(socket + " Connection close");
        }
    }

    /**
     * Read message from client stream
     *
     * @return message
     * @throws IOException on stream errors
     * @throws NullPointerException on null message
     */
    private String readNumber() throws IOException, NullPointerException {
        String number = in.readLine();
        if (number == null) {
            throw new NullPointerException("Connection close");
        }
        return number;
    }

}
