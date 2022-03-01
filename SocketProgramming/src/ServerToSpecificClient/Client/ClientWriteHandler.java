 
package ServerToSpecificClient.Client;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mahmud
 */
public class ClientWriteHandler implements Runnable{
    
    Thread thread;
    String userName;
    public Socket serverClient;
    public DataOutputStream outStream;
    public BufferedReader br;
    
    ClientWriteHandler(Socket serverClient, String userName) {
         this.serverClient = serverClient;
         this.userName = userName;
         thread = new Thread(this);
         thread.start();
    }

    @Override
    public void run() {
        br = new BufferedReader(new InputStreamReader(System.in));
        try{
            outStream = new DataOutputStream(serverClient.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ClientWriteHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(true)
        {
            try {
                sendMessage();
            } catch (IOException ex) {
                Logger.getLogger(ClientWriteHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
             
        }
    }
    
    public void sendMessage() throws IOException
    {
        String clientMessage = br.readLine();
        String clientMessageFull = userName + " : " + clientMessage;
        outStream.writeUTF(clientMessageFull);
    }
    
}
