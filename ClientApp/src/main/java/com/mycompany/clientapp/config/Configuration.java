package com.mycompany.clientapp.config;

import com.mycompany.clientapp.config.exceptions.IPException;
import com.mycompany.clientapp.config.exceptions.PortException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

    private int port;
    private String ip;

    public Configuration() throws PortException, IPException, IOException {
        Properties properties = checkProperties();
        String cfgPort = properties.getProperty("port");
        if (!checkPort(cfgPort)) {
            throw new PortException();
        }
        ip = properties.getProperty("ip");
        if (!checkIP(ip)) {
            throw new IPException();
        }
    }

    /**
     * Get config. port
     *
     * @return port
     */
    public int getPort() {
        return port;
    }

    /**
     * Get config. port
     *
     * @return ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * Reading properties from cfg file
     *
     * @throws IOException on cfg file not found
     * @return properties from cfg file
     */
    private Properties checkProperties() throws IOException {
        Properties properties = new Properties();
        String propertiesFile = "properties.cfg";
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFile);) {
            if (inputStream == null) {
                throw new FileNotFoundException("Properties file not found");
            }
            properties.load(inputStream);
            Logger.log("Properties reading - Successfull");
            return properties;
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

    /**
     * @param cfgIp ip-string from property file
     * @return true if ip is correct
     */
    private boolean checkIP(String cfgIp) {
        return IP.validate(cfgIp);
    }

}
