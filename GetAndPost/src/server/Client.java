package server;

import java.io.*;
import java.net.*;

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
                InetAddress serverInet = InetAddress.getByName("127.0.0.1");
                Socket conn = new Socket(serverInet, 8080); //try 3000 or 8080 if 80 doesn't work
                
                try(OutputStream out = conn.getOutputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream())))
                {
                    sendGet(out);
                    System.out.println(getResponse(br));
                }


                URL url = new URL(serverInet.getAddress().toString());

                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");

            }
            catch(IOException e)
            {
                e.printStackTrace();
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

        private String getResponse(BufferedReader br)
        {
            try
            {
                String input;
                StringBuilder response = new StringBuilder();
                while((input = br.readLine()) != null)
                {
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
    }
}
