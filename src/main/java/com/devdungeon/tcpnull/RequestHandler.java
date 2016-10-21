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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NanoDano <nanodano@devdungeon.com>
 */
public class RequestHandler extends Thread {

    protected Socket connectionSocket = null;
    private final boolean httpFormat;
    private final boolean echoToClient;

    public RequestHandler(Socket connectionSocket, boolean echoToClient, boolean httpFormat) {
        this.connectionSocket = connectionSocket;
        this.echoToClient = echoToClient;
        this.httpFormat = httpFormat;
    }

    public void run() {
        try {
            if (echoToClient) {
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(this.connectionSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(this.connectionSocket.getOutputStream());
                String clientRequest = null;
                if (httpFormat) {
                    outToClient.write("HTTP/1.1 200 OK\n\n".getBytes());
                }
                clientRequest = inFromClient.readLine();
                outToClient.write(clientRequest.getBytes());
                outToClient.close();
                inFromClient.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
