import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        // Step 1: Create a socket which represents the server
        System.out.println("Starting a connection...");
        Socket connection = new Socket("127.0.0.1", 12345);
        System.out.println("Connected to server.");

        // Step 2: Create streams
        ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();

        ObjectInputStream input = new ObjectInputStream(connection.getInputStream());

        // Step 3: Process data coming or going to the stream
        Scanner keyboardInput = new Scanner(System.in);

        System.out.println("Write your message below:");
        String msg = "";
        String modifiedMsg = "";

        do {
            msg = keyboardInput.nextLine();
            output.writeObject(msg);
            output.flush();

            modifiedMsg = (String) input.readObject();

            System.out.println("Server returned: " + modifiedMsg);
        } while (!msg.equals("quit!"));

        // Step 4: Close streams
        input.close();
        output.close();
        connection.close();
    }
}
