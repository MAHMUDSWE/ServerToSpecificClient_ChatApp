 
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
public class Server {
    
    public Socket serverClient;
    public BufferedReader bufferedReader;
    ArrayList<ClientHandler> clients = new ArrayList<>();
    
    public static void main(String[] args) throws IOException {
        new Server().newClientConnect();
    }
    
    public ServerSocket serverSocket;
    private void newClientConnect() throws IOException{
        serverSocket = new ServerSocket(5555);
        System.out.println("Server Started.");
        System.out.println("Server waiting for clients....");
        
        ClientAdd clientAdd; //Adding new client
        clientAdd = new ClientAdd(serverClient, serverSocket, clients);
        
        SpecificClient specificClient; //Chossing specific client
        specificClient = new SpecificClient(serverClient, serverSocket, clients);
    }
    

    
}

class ClientAdd implements Runnable{
    Thread thread;
    public Socket serverClient;
    ArrayList<ClientHandler> clients = new ArrayList<>();
    public ServerSocket serverSocket;
    
    ClientAdd(Socket serverClient, ServerSocket serverSocket, ArrayList<ClientHandler> clients){
        
        this.serverClient = serverClient;
        this.serverSocket = serverSocket;
        
        this.clients = clients;
        thread = new Thread(this);
        thread.start();
    }
    
    @Override
    public void run()
    {
        try {
            System.out.println("Send message to specific client Syntax - 'UserName' Space 'Message'.");
            while(true)
            {
                serverClient = serverSocket.accept();
                System.out.println("New Client Connecting....");

                ClientHandler newClient = new ClientHandler(serverClient);

                clients.add(newClient);

                newClient.start();
            }

            
        } catch (IOException ex) {
            Logger.getLogger(ClientAdd.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
