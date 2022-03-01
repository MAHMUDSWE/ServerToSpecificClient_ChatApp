 
package ServerToSpecificClient.Server;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mahmud
 */
public class ClientHandler extends Thread {
    
    public String userName;
    public Socket serverClient;
    public DataInputStream inStream;
    public DataOutputStream outStream;
    public BufferedReader br;
    
    ClientHandler(Socket serverClient) throws IOException
    {
        this.serverClient = serverClient;
        inStream = new DataInputStream(serverClient.getInputStream());
        outStream = new DataOutputStream(serverClient.getOutputStream());
        br = new BufferedReader(new InputStreamReader(System.in));
    }
    
    
    @Override
    public void run()
    {
        try {
            
            userName = inStream.readUTF();
            System.out.println(userName +" Connected");
            
            
            new Thread(() -> {
                while(true)
                {
                    try{
                        receiveMessage();
                    } catch (IOException ex) {
                        Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            }).start();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void receiveMessage() throws IOException{
        
        String clientsMessage = inStream.readUTF();
        System.out.println(clientsMessage);
    }
}
