package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client
{

    private void sendGet(OutputStream out) {
        try {
            out.write("GET /default\r\n".getBytes());
            out.write("User-Agent: Mozilla/5.0\r\n".getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }   
    private static void sendGET() throws IOException {
        URL obj;
        obj = new URL();
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("GET ResponceCode: "+ responseCode);
    }

    private String getResponse(BufferedReader in) {
        try {
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine).append("\n");
            }
            return response.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args)
    {
        HTTPClient hc = new HTTPClient();
    }
    
    private static class HTTPClient
    {
        public HTTPClient()
        {
            System.out.println("Client starting...");
            try
            {
                String ipString = "127.0.0.1";
                //InetAddress serverInet = InetAddress.getByName(ipString);
                InetAddress serverInet = InetAddress.getLocalHost();
                int port = 8080;
                Socket conn = new Socket(serverInet, port); //try 3000 or 8080 if 80 doesn't work
                
                try(OutputStream out = conn.getOutputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream())))
                {
                    sendGet(out);
                    System.out.println(getResponse(br));
                }

                String protocol = "http";
                String host = serverInet.getHostName();
                String path = "/";
                URL url = new URL(protocol, host, port, path);

                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");

            }
            catch(IOException e)
            {
                e.printStackTrace();
                System.out.println("break before url obj created");
            }
        }

        private void sendGet(OutputStream out)
        {
            try
            {
                out.write("Get /default\r\n".getBytes());
                out.write("User-Agent: Mozilla/5.0\r\n".getBytes());
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        
        private void sendPost() throws Exception{
            
            try {
                InetAddress serverInet = InetAddress.getLocalHost();
                int port = 8080;
                String protocol = "http";
                String host = serverInet.getHostName();
                String path = "/";
                URL url = new URL(protocol, host, port, path);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                con.setRequestMethod("POST");
                con.setRequestProperty("User-Agent", "Mozilla/5.0");
                con.setRequestProperty("Content-Type", "text/html");
                con.setRequestProperty("Content-Length", "81");

                Scanner input = new Scanner(System.in);
                System.out.print("Diary Entry: ");
                String postEntry = input.nextLine();

                con.setDoOutput(true);
                DataOutputStream DOS = new DataOutputStream(con.getOutputStream());
                DOS.writeBytes(postEntry);
                DOS.flush();
                DOS.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }

        private String getResponse(BufferedReader br)
        {
            try
            {
                String input;
                StringBuilder response = new StringBuilder();
                while((input = br.readLine()) != null)
                {
                    System.out.println(input);
                    response.append(input).append("\n");
                }
                return response.toString();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            return "";
        }
        
        private String postResponse(BufferedReader br){
            try {
                String input;
                StringBuilder response = new StringBuilder();
                while((input = br.readLine()) != null) {
                    System.out.println(input);
                    response.append(input).append("\n");
                }
                return response.toString();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
