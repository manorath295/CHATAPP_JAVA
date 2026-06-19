import java.io.*;
import java.net.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Multi-threaded Chat Server
 * Handles multiple client connections simultaneously
 * Features: Message Logging, Command Processing, Active User Management
 */
public class Server {
    private static final int PORT = 5555;
    private static final String LOG_FILE = "chat_log.txt";
    private static Set<ServerHandler> clientHandlers = Collections.synchronizedSet(new HashSet<>());
    private static FileWriter logWriter;

    public static void main(String[] args) {
        System.out.println("Chat Server starting on port " + PORT + "...");

        try {
            // Initialize message logging
            logWriter = new FileWriter(LOG_FILE, true);
            logMessage("=== Chat Server Started at " + LocalDateTime.now() + " ===");
            
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("✓ Server is running. Waiting for clients...\n");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("→ New client connected: " + clientSocket.getInetAddress().getHostAddress());

                // Create a new thread for each client
                ServerHandler clientHandler = new ServerHandler(clientSocket, clientHandlers, logWriter);
                clientHandlers.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static synchronized void logMessage(String message) {
        try {
            if (logWriter != null) {
                logWriter.write(message + "\n");
                logWriter.flush();
            }
        } catch (IOException e) {
            System.err.println("Logging error: " + e.getMessage());
        }
    }

    public static Set<ServerHandler> getClientHandlers() {
        return clientHandlers;
    }
}
