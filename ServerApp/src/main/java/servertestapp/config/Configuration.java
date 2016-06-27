package servertestapp.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import servertestapp.config.exceptions.PortException;

public class Configuration {

    private int port;

    public Configuration() throws PortException,IOException {
        String cfgPort = checkProperties();
        if (!checkPort(cfgPort)) {
            throw new PortException();           
        } 
    }

    /**
     * Get config. port
     * @return port
     */
    public int getPort() {
        return port;
    }

    /**
     * Reading port from cfg file
     *
     * @throws IOException on cfg file not found
     * @return port from cfg file
     */
   private String checkProperties() throws IOException {
        Properties properties = new Properties();
        String propertiesFile = "properties.cfg";       
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFile);) {
            if (inputStream == null){
                throw new FileNotFoundException("Properties file not found");
            }
            properties.load(inputStream);
            Logger.log("Properties reading - Successfull");
            return properties.getProperty("port");
        }
    }

    /**
     * @param cfgPort port-string from property file
     * @return true if port is correct
     */
    private boolean checkPort(String cfgPort) {
        try {
            port = Integer.parseInt(cfgPort);
            if (port >= 1024 && port <= 49151) {
                Logger.log("Port set to: " + port);
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
