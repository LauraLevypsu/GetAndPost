package server;

import java.io.*;
import java.net.*;
import java.util.StringTokenizer;

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

        /*public void handleRequest(Socket socket) {
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));) {
                String headerLine = in.readLine();
                StringTokenizer tokenizer =
                        new StringTokenizer(headerLine);
                String httpMethod = tokenizer.nextToken();


                if (httpMethod.equals("GET")) {
                    System.out.println("Get method processed");
                    String httpQueryString = tokenizer.nextToken();
                    StringBuilder responseBuffer = new StringBuilder();
                    responseBuffer
                            .append("<html><h1>WebServer Home Page.... </h1><br>")
                            .append("<b>Welcome to my web server!</b><BR>")
                            .append("</html>");
                    sendResponse(socket, 200, responseBuffer.toString());
                } else {
                    System.out.println("The HTTP method is not recognized");
                    sendResponse(socket, 405, "Method Not Allowed");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/

        /*public void sendResponse(Socket socket,
                                 int statusCode, String responseString) {
            String statusLine;
            String serverHeader = "Server: WebServer\r\n";
            String contentTypeHeader = "Content-Type: text/html\r\n";

            try (DataOutputStream out =
                         new DataOutputStream(socket.getOutputStream());) {
                if (statusCode == 200) {
                    statusLine = "HTTP/1.0 200 OK" + "\r\n";
                    String contentLengthHeader = "Content-Length: "
                            + responseString.length() + "\r\n";

                    out.writeBytes(statusLine);
                    out.writeBytes(serverHeader);
                    out.writeBytes(contentTypeHeader);
                    out.writeBytes(contentLengthHeader);
                    out.writeBytes("\r\n");
                    out.writeBytes(responseString);
                } else if (statusCode == 405) {
                    statusLine = "HTTP/1.0 405 Method Not Allowed" + "\r\n";
                    out.writeBytes(statusLine);
                    out.writeBytes("\r\n");
                } else {
                    statusLine = "HTTP/1.0 404 Not Found" + "\r\n";
                    out.writeBytes(statusLine);
                    out.writeBytes("\r\n");
                }
                out.close();
            } catch (IOException ex) {
                // Handle exception
            }
        }*/

    }
}