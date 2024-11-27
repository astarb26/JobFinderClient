package com.example.jobfinderclient;

import com.example.jobfinderclient.model.Request;
import com.google.gson.Gson;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    static Gson gson = new Gson();

    public static void saveToServer(Request request) {
        PrintWriter writer;
        Socket myServer;

        try {
            myServer = new Socket("localhost", 12346);
            writer = new PrintWriter(myServer.getOutputStream(), true);
            String x = gson.toJson(request);
            writer.println(x);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Response requestToServer(Request request) throws IOException {
        PrintWriter writer = null;
        Scanner reader;
        ObjectInputStream inFromServer = null;
        Socket myServer = null;
        Response response;

        try {
            myServer = new Socket("localhost", 12346);
            writer = new PrintWriter(myServer.getOutputStream(), true);
            String x = gson.toJson(request);
            writer.println(x);
            writer.flush();

            reader = new Scanner(new InputStreamReader(myServer.getInputStream()));
            String jsonResponse = reader.next();

            response = gson.fromJson(jsonResponse, Response.class);
            return response;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (inFromServer != null) {
                    inFromServer.close();
                }
                if (myServer != null) {
                    myServer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}