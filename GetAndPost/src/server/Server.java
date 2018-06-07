package server;

import java.io.*;
import java.net.*;

public class Server
{
    public static void main(String[] args)
    {
        WebServer ws = new WebServer();
    }

    private static class WebServer
    {
        public WebServer()
        {
            System.out.print("Server starting. ");
            try(ServerSocket sSocket = new ServerSocket(8080)) //try 3000 or 8080 if 80 doesn't work
            {
                while(true)
                {
                    System.out.print("Connecting to client... ");
                    Socket remote = sSocket.accept();
                    System.out.println("Connected!");
                    new Thread(new ClientHandler(remote)).start();
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}