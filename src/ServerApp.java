import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // Step 1: Create a SocketServer
        System.out.println("Starting server...");
        ServerSocket server = new ServerSocket(12345,100);
        System.out.println("Server started.");

        while(true) {
            // Step 2: Accept connections from clients
            System.out.println("Waiting for connection..");
            Socket connection = server.accept();

            // Step 3: Open streams
            String clientAddress = connection.getInetAddress().getHostName();

            System.out.println("Established connection with " + clientAddress);
            ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            ObjectInputStream input = new ObjectInputStream(connection.getInputStream());

            // Step 4: Process data coming or going to the stream
            String msg = "";
            String modifiedMsg = "";
            do {
                msg = (String) input.readObject();

                System.out.println(
                        String.format("Client (%s): %s%n",
                                connection.getInetAddress().getHostName(),
                                msg)
                );
                modifiedMsg = msg.toUpperCase();
                System.out.println(
                        String.format("Server: %s%n",
                                modifiedMsg)
                );

                output.writeObject(modifiedMsg);
                output.flush();
            } while (!msg.equals("quit!"));

            // Step 5: Close
            System.out.println("Closing connection with " + clientAddress);
            input.close();
            output.close();
            connection.close();
            System.out.println("Closed");
        }
    }
}
