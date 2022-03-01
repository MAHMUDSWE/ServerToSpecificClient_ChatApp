package ServerToSpecificClient.Server;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mahmud
 */
public class SpecificClient implements Runnable {

    Thread thread;
    public Socket serverClient;
    public ServerSocket serverSocket;
    ArrayList<ClientHandler> clients = new ArrayList<>();
    public BufferedReader br;

    SpecificClient(Socket serverClient, ServerSocket serverSocket, ArrayList<ClientHandler> clients) {
        this.serverClient = serverClient;
        this.serverSocket = serverSocket;
        this.clients = clients;
        
        thread = new Thread(this);
        thread.start();

    }

    @Override
    public void run() {
        br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            
            String serverMessage = null;
            
            try {
                serverMessage = br.readLine();
            } catch (IOException ex) {
                Logger.getLogger(SpecificClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String serverMessageFull = "Server  " + serverMessage;

            String[] splittedMessage = serverMessageFull.split(" ", 4);
            
            
            String userName = splittedMessage[2];

            for (int i = 0; i < clients.size(); i++) {
                ClientHandler temporaryClient = clients.get(i);

                if (temporaryClient.userName.equals(userName)) {
                    
                    serverMessageFull = splittedMessage[1] + "Server: " + splittedMessage[3];
                    try {
                        temporaryClient.outStream.writeUTF(serverMessageFull);

                    } catch (IOException ex) {
                        Logger.getLogger(SpecificClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        temporaryClient.outStream.flush();
                    } catch (IOException ex) {
                        Logger.getLogger(SpecificClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
            }
        }

    }
}
