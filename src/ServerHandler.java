import java.io.*;
import java.net.*;
import java.util.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Handles individual client connections
 * Runs in a separate thread for each client
 */
public class ServerHandler implements Runnable {
    private Socket socket;
    private Set<ServerHandler> allClients;
    private PrintWriter out;
    private BufferedReader in;
    private String clientName;

    public ServerHandler(Socket socket, Set<ServerHandler> allClients) {
        this.socket = socket;
        this.allClients = allClients;
    }

    @Override
    public void run() {
        try {
            // Initialize input/output streams
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Receive client name
            clientName = in.readLine();
            if (clientName == null || clientName.isEmpty()) {
                clientName = "User-" + socket.getPort();
            }

            System.out.println("✓ " + clientName + " joined the chat");

            // Notify all clients
            broadcastMessage("SERVER", clientName + " has joined the chat");

            // Read messages from this client
            String message;
            while ((message = in.readLine()) != null) {
                if (message.equalsIgnoreCase("EXIT")) {
                    break;
                }
                System.out.println("[" + clientName + "]: " + message);
                broadcastMessage(clientName, message);
            }

        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        } finally {
            disconnect();
        }
    }

    private void broadcastMessage(String sender, String message) {
        String timestamp = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String formattedMessage = "[" + timestamp + "] " + sender + ": " + message;

        synchronized (allClients) {
            for (ServerHandler client : allClients) {
                client.out.println(formattedMessage);
            }
        }
    }

    private void disconnect() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            allClients.remove(this);
            System.out.println("✗ " + clientName + " disconnected");
            broadcastMessage("SERVER", clientName + " has left the chat");
        } catch (IOException e) {
            System.err.println("Error closing socket: " + e.getMessage());
        }
    }
}
