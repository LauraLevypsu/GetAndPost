package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler implements Runnable
{
    private final Socket socket;
    
    public ClientHandler(Socket s)
    {
        this.socket = s;
    }

    @Override
    public void run()
    {
        System.out.println("\nClientHandler started for " + this.socket);
        handleRequest(this.socket);
        System.out.println("ClientHandler terminated for " + this.socket);
    }

    private void handleRequest(Socket s)
    {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));)
        {
            String header = br.readLine();
            StringTokenizer tokenizer = new StringTokenizer(header);
            String httpMethod = tokenizer.nextToken();
            
            if(httpMethod.equalsIgnoreCase("GET"))
            {
                handleGet(s);
//                System.out.println("Get!");
//                String httpQueryString = tokenizer.nextToken();
//                StringBuilder responseBuffer = new StringBuilder();
//                responseBuffer.append("<html><h1>WebServer Home Page</h1><br>")
//                        .append("<b>Welcome to my web server!</b><BR>")
//                        .append("</html>");
//                sendResponse(s, 200, responseBuffer.toString());
            }
            else if(httpMethod.equalsIgnoreCase("POST")){
                handlePost();
            }
            else
            {
                System.out.println("Unrecognized method :( "+httpMethod);
                sendResponse(s, 405, "Not allowed");
                //Something about adding String httpQueryString = tokenizer.nextToken() again somewhere I think?
                //See book, ch4, Building Simple HTTP Server, about 2/3 down before sendResponse() method
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    private void handleGet(Socket s) {
        System.out.println("Get!");
        StringBuilder responseBuffer = new StringBuilder();
        responseBuffer.append("<html><h1>WebServer Home Page</h1><br>")
                .append("<b>Welcome to my web server!</b><BR>")
                .append("</html>");
        sendResponse(s, 200, responseBuffer.toString());
    }


    public void handlePost(){
        System.out.println("post! :)");
    }

    private void sendResponse(Socket s, int statusCode, String response)
    {
        String status;
        String server = "Server: WebServer\r\n";
        String contentType = "Content-Type: text/html\r\n";
        
        try(DataOutputStream out = new DataOutputStream(s.getOutputStream());)
        {
            if (statusCode == 200)
            {
                status = "HTTP/1.0 200 OK" + "\r\n";
                String contentLengthHeader = "Content-Length: " + response.length() + "\r\n";
                
                out.writeBytes(status);
                out.writeBytes(server);
                out.writeBytes(contentType);
                out.writeBytes(contentLengthHeader);
                out.writeBytes("\r\n");
                out.writeBytes(response);
            }
            else if (statusCode == 405)
            {
                status = "HTTP/1.0 405 Method Not Allowed" + "\r\n";
                out.writeBytes(status);
                out.writeBytes("\r\n");
            }
            else
            {
                status = "HTTP/1.0 404 Not Found" + "\r\n";
                out.writeBytes(status);
                out.writeBytes("\r\n");
            }
            out.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
