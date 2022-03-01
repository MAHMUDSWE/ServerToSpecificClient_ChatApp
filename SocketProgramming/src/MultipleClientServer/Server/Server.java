package MultipleClientServer.Server;

import java.net.*;
import java.io.*;

public class Server {

    public static void main(String[] args) throws Exception {
        try {
            ServerSocket server = new ServerSocket(8889);
            
            int counter = 0;
            System.out.println("Server Started ....");
            
            while (true) {
                counter++;
                Socket serverClient = server.accept();   
                System.out.println(" >> " + "Client No:" + counter + " started!");
                ServerClientThread sct = new ServerClientThread(serverClient, counter);  
                sct.start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

class ServerClientThread extends Thread {

    Socket serverClient;
    int clientNo;
     

    ServerClientThread(Socket inSocket, int counter) {
        serverClient = inSocket;
        clientNo = counter;
    }

    @Override
    public void run() {
        try {
            DataInputStream inStream = new DataInputStream(serverClient.getInputStream());
            DataOutputStream outStream = new DataOutputStream(serverClient.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            
            String clientMessage = "", serverMessage = "";
            while (!clientMessage.equals("bye")) {
                clientMessage = inStream.readUTF();

                System.out.println("From Client-" + clientNo + " Message is : " + clientMessage);

                
                System.out.println("Enter message :");
                serverMessage = br.readLine();
                 
                String  serverMessageFull = "From Server to Client-" + clientNo + " Message is: " + serverMessage;

                outStream.writeUTF(serverMessageFull);
                outStream.flush();
            }
            inStream.close();
            outStream.close();
            serverClient.close();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            System.out.println("Client -" + clientNo + " exit!! ");
        }
    }
}
