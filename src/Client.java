import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

/**
 * Chat Client with GUI (Swing)
 * Connects to server and enables real-time messaging
 */
public class Client extends JFrame {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5555;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String clientName;

    // GUI Components
    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton, exitButton;
    private JLabel statusLabel;

    public Client() {
        setTitle("Java Chat Application");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Status panel
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusLabel = new JLabel("Connecting...");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusPanel.add(statusLabel);

        // Chat display area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        chatArea.setBackground(new Color(245, 245, 245));
        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Chat Messages"));

        // Message input panel
        JPanel inputPanel = new JPanel(new BorderLayout(5, 0));
        messageField = new JTextField();
        messageField.setFont(new Font("Arial", Font.PLAIN, 12));
        messageField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                }
            }
        });

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Arial", Font.BOLD, 12));
        sendButton.addActionListener(e -> sendMessage());

        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 12));
        exitButton.addActionListener(e -> disconnectAndExit());

        buttonPanel.add(sendButton);
        buttonPanel.add(exitButton);

        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(buttonPanel, BorderLayout.EAST);
        inputPanel.setBorder(BorderFactory.createTitledBorder("Send Message"));

        // Add components to main panel
        mainPanel.add(statusPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);

        // Connect to server in a separate thread
        new Thread(this::connectToServer).start();
    }

    private void connectToServer() {
        try {
            // Prompt for username
            clientName = JOptionPane.showInputDialog(this, "Enter your name:", "Guest");
            if (clientName == null || clientName.trim().isEmpty()) {
                clientName = "Guest";
            }

            // Connect to server
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Send client name to server
            out.println(clientName);

            statusLabel.setText("✓ Connected as: " + clientName);
            statusLabel.setForeground(new Color(0, 128, 0));
            chatArea.append("=== Connected to server ===\n");

            messageField.setEnabled(true);
            sendButton.setEnabled(true);

            // Listen for messages from server
            listenForMessages();

        } catch (IOException e) {
            statusLabel.setText("✗ Failed to connect to server");
            statusLabel.setForeground(Color.RED);
            chatArea.append("ERROR: Cannot connect to server at " + SERVER_ADDRESS + ":" + SERVER_PORT + "\n");
            JOptionPane.showMessageDialog(this, "Cannot connect to server. Make sure it's running.", "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listenForMessages() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                final String msg = message;
                SwingUtilities.invokeLater(() -> chatArea.append(msg + "\n"));
            }
        } catch (IOException e) {
            if (!socket.isClosed()) {
                chatArea.append("\nDISCONNECTED: " + e.getMessage() + "\n");
            }
        }
    }

    private void sendMessage() {
        String message = messageField.getText().trim();
        if (message.isEmpty()) {
            return;
        }

        if (out != null) {
            out.println(message);
            messageField.setText("");
        }
    }

    private void disconnectAndExit() {
        try {
            if (out != null) {
                out.println("EXIT");
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Client());
    }
}
