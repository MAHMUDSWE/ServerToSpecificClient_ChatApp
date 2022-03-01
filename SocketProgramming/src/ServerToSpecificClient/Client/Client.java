package ServerToSpecificClient.Client;

import java.net.*;
import java.io.*;

/**
 *
 * @author Mahmud
 */
public class Client {

    public Socket serverClient;
    public String userName;
    public DataInputStream inStream;
    public DataOutputStream outStream;
    public BufferedReader br;

    public static void main(String[] args) throws IOException {
        new Client().connectToServer();
    }

    private void connectToServer() throws IOException {
        serverClient = new Socket("localhost", 5555);
        System.out.println("Connected to the Server.");

        inStream = new DataInputStream(serverClient.getInputStream());
        outStream = new DataOutputStream(serverClient.getOutputStream());
        br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter your name: ");
        userName = br.readLine();

        outStream.writeUTF(userName);

        run();

    }

    private void run() {
        ClientWriteHandler clientWriteHandler = new ClientWriteHandler(serverClient, userName);
        ClientReadHandler clientReadHandler = new ClientReadHandler(serverClient);
    }
}
