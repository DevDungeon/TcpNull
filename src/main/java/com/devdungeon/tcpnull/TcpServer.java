/*
 * Copyright (C) 2016 NanoDano <nanodano@devdungeon.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
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
import javax.swing.JOptionPane;

/**
 *
 * @author NanoDano <nanodano@devdungeon.com>
 */
public class TcpServer extends Thread {

    ServerSocket listener;
    protected boolean serverRunning = false;
    protected InetAddress host;
    protected int port;
    private int connectionBacklog = 5;
    private boolean echoRequest;
    private boolean httpFormat;
    private MainWindow mainWindow;

    public TcpServer(int port, String host, boolean echoRequest, boolean httpFormat, MainWindow mainWindow) {
        this.port = port;
        this.echoRequest = echoRequest;
        this.httpFormat = httpFormat;
        this.mainWindow = mainWindow;
        try {
            this.host = InetAddress.getByName(host);
        } catch (UnknownHostException ex) {
            Logger.getLogger(TcpServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        startServer();
    }

    private void startServer() {
        try {
            listener = new ServerSocket(port, connectionBacklog, host);
        } catch (IOException ex) {
            mainWindow.statusLabel.setText("Error. Socket may be busy.");
            mainWindow.startButton.setSelected(false);
            mainWindow.setOptionsEnabledState(true);
            JOptionPane.showMessageDialog(mainWindow, "Error. Socket may be busy.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        serverRunning = true;

        // Handle incoming connections
        while (true) {
            try {
                Socket connectionSocket = listener.accept();
                (new RequestHandler(connectionSocket, echoRequest, httpFormat)).start();
            } catch (SocketException sex) {
                // It's ok if the socket gets closed. Sometimes we close it.
            } catch (IOException ex) {
                Logger.getLogger(TcpServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (serverRunning == false) {
                break;
            }
        }
    }

    public void stopServer() {
        try {
            listener.close();
        } catch (IOException ex) {
            Logger.getLogger(TcpServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        serverRunning = false;
    }

}
