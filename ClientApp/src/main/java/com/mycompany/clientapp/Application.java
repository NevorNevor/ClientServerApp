package com.mycompany.clientapp;

import com.mycompany.clientapp.client.Client;

public class Application {

    /**
     * Start client
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            new Client();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
