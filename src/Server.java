import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Multi-threaded Chat Server
 * Handles multiple client connections simultaneously
 */
public class Server {
    private static final int PORT = 5555;
    private static Set<ServerHandler> clientHandlers = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {
        System.out.println("Chat Server starting on port " + PORT + "...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("✓ Server is running. Waiting for clients...\n");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("→ New client connected: " + clientSocket.getInetAddress().getHostAddress());

                // Create a new thread for each client
                ServerHandler clientHandler = new ServerHandler(clientSocket, clientHandlers);
                clientHandlers.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
