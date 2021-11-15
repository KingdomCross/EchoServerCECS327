import java.net.*;
import java.io.*;

public class server {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java server <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]); //Port number of user input
        DatagramSocket serverSocket = null; //Creating server

        try {
            serverSocket = new DatagramSocket(portNumber);
            System.out.println("Create UDP Server on port " + portNumber);

            byte buffer[] = new byte[1024];
            while(true)
            {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                // listen for datagram packets
                serverSocket.receive(request);
                InetAddress clientAddress = request.getAddress(); //Client IP address
                int clientPort = request.getPort(); //Port from client
                String messageFromClient = new String(request.getData(), 0, request.getLength()).toUpperCase(); //client message
                System.out.println("Client: " + messageFromClient); //Output message to server

                //Create reply to send back
                DatagramPacket reply = new DatagramPacket(messageFromClient.getBytes(), messageFromClient.length(), clientAddress, clientPort);
                serverSocket.send(reply); //Send back to client
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}