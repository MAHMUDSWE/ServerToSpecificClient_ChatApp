package ServerToSpecificClient.Client;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mahmud
 */
public class ClientReadHandler implements Runnable {

    Thread thread;
    public Socket serverClient;

    public DataInputStream inStream;

    ClientReadHandler(Socket serverClient) {
        this.serverClient = serverClient;

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {

        while (true) {
            try {
                receiveMessage();
            } catch (IOException ex) {
                Logger.getLogger(ClientReadHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void receiveMessage() throws IOException {
        try {
            inStream = new DataInputStream(serverClient.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ClientReadHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        String serverMessage = null;
        try {
            serverMessage = inStream.readUTF();
        } catch (IOException ex) {
            Logger.getLogger(ClientReadHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println(serverMessage);
    }

}
