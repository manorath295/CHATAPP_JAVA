import java.io.*;
import java.net.*;
import java.util.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Handles individual client connections
 * Runs in a separate thread for each client
 * Features: Command Parsing, Message Logging, User List
 */
public class ServerHandler implements Runnable {
    private Socket socket;
    private Set<ServerHandler> allClients;
    private PrintWriter out;
    private BufferedReader in;
    private String clientName;
    private FileWriter logWriter;

    public ServerHandler(Socket socket, Set<ServerHandler> allClients, FileWriter logWriter) {
        this.socket = socket;
        this.allClients = allClients;
        this.logWriter = logWriter;
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
            logAndBroadcast("SERVER", clientName + " has joined the chat");

            // Send welcome message with commands
            out.println("\n=== Welcome to Java Chat App ===");
            out.println("Type /help to see all commands\n");

            // Read messages from this client
            String message;
            while ((message = in.readLine()) != null) {
                if (message.equalsIgnoreCase("/quit")) {
                    break;
                }

                // Check if it's a command
                if (message.startsWith("/")) {
                    handleCommand(message);
                } else {
                    System.out.println("[" + clientName + "]: " + message);
                    logAndBroadcast(clientName, message);
                }
            }

        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        } finally {
            disconnect();
        }
    }

    private void handleCommand(String command) {
        String cmd = command.toLowerCase().trim();

        if (cmd.equals("/help")) {
            out.println("\n╔═══════════════════════════════════╗");
            out.println("║     AVAILABLE COMMANDS             ║");
            out.println("╠═══════════════════════════════════╣");
            out.println("║ /help   - Show this help message  ║");
            out.println("║ /users  - List active users       ║");
            out.println("║ /clear  - Clear chat screen       ║");
            out.println("║ /quit   - Exit chat               ║");
            out.println("╚═══════════════════════════════════╝\n");

        } else if (cmd.equals("/users")) {
            out.println("\n✓ Active Users (" + allClients.size() + "):");
            synchronized (allClients) {
                int count = 1;
                for (ServerHandler handler : allClients) {
                    out.println("  " + count + ". " + handler.clientName);
                    count++;
                }
            }
            out.println();

        } else if (cmd.equals("/clear")) {
            for (int i = 0; i < 50; i++) {
                out.println();
            }
            out.println("=== Chat Cleared ===\n");
            System.out.println("[" + clientName + "]: cleared chat");

        } else {
            out.println("❌ Unknown command: " + command);
            out.println("Type /help for available commands\n");
        }
    }

    private void logAndBroadcast(String sender, String message) {
        String timestamp = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String formattedMessage = "[" + timestamp + "] " + sender + ": " + message;

        // Log to file
        Server.logMessage(formattedMessage);

        // Broadcast to all clients
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
            logAndBroadcast("SERVER", clientName + " has left the chat");
        } catch (IOException e) {
            System.err.println("Error closing socket: " + e.getMessage());
        }
    }
}
