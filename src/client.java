import java.io.*;
import java.net.*;
import java.util.Scanner;

public class client {
    public static void main(String[] args) {
        //Report user input error and explanation
        if (args.length != 2) {
            System.err.println("Usage: java client <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0]; //Need to be user IP address, should not be public when possible
        int portNumber = Integer.parseInt(args[1]); //portNumber need to match with server port number by user input
        DatagramSocket clientSocket = null;
        Scanner in = new Scanner(System.in);

        try {
            InetAddress aHost = InetAddress.getByName(hostName); //Must match with user IPv4 address
            //InetAddress aHost = InetAddress.getLocalHost();
            System.out.println("Wait..."); //For user notification
            if (!aHost.isReachable(1000)) { //Testing connection to IPv4
                System.out.println("Your IPv4 address not reachable");
                System.exit(1);
            }
            clientSocket = new DatagramSocket();

            byte[] buffer = new byte[1024];

            //During porting, loop and send user message
            while (true) {
                System.out.print("Enter your response: ");
                String userInput = in.nextLine();

                if (userInput.equals("!exit")){ //Exit from program
                    break;
                }

                byte[] m = userInput.getBytes();
                DatagramPacket request = new DatagramPacket(m, m.length, aHost, portNumber);
                clientSocket.send(request); //Send to server
                clientSocket.receive(request); //Should have buffer implement, not sure how
                System.out.println("Server Reply: " + new String(request.getData())); //Output reply from server, should be outputting capitalize
            }
        }
        catch (UnknownHostException e){
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        }
        catch (SocketException e){
            System.out.println("Socket: " + e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
        catch (Exception e){
            System.out.println("Socket: " + e.getMessage());
        }
        finally {
            if (clientSocket != null)
                clientSocket.close();
        }
    }
}