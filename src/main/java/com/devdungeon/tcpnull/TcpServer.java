/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.devdungeon.tcpnull;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dtron
 */
public class TcpServer extends Thread {
    
    ServerSocket listener;
    protected boolean serverRunning = false;
    protected InetAddress host;
    protected int port;
    private int connectionBacklog = 5;
    
    public TcpServer(int port, String host) {
        this.port = port;
        try {
            this.host = InetAddress.getByName(host);
        } catch (UnknownHostException ex) {
            Logger.getLogger(TcpServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void run() {
        System.out.println("Starting server...");
        startServer();
        System.out.println("Now server has ended...");
    }
    
    public void startServer() {
        try {
            listener = new ServerSocket(port, connectionBacklog, host);
        } catch (IOException ex) {
            Logger.getLogger(TcpServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        serverRunning = true;
        
        // Handle incoming connections
        while (true) {
            try {
                Socket connectionSocket = listener.accept();
                (new RequestHandler(connectionSocket)).start();
            } catch (SocketException sex) {
                // It's ok if the socket gets closed. Sometimes we close it.
            } catch (IOException ex) {
                Logger.getLogger(TcpServer.class.getName()).log(Level.SEVERE, null, ex);
            } 
            if (serverRunning == false) {
                System.out.println("Server stopped...");
                break;
            }
        }
    }
    
    public void stopServer() {
        System.out.println("Attempting to stop server...");
        try {
            listener.close();
        } catch (IOException ex) {
            Logger.getLogger(TcpServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        serverRunning = false;
    }
    
}
