# Multithreaded Chat Application 💬

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Swing](https://img.shields.io/badge/Swing-007396?style=for-the-badge)
![Socket Programming](https://img.shields.io/badge/Sockets-000000?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-green.svg?style=for-the-badge)

A **real-time, multi-client chat application** built with **Java**, demonstrating **Socket Programming**, **Multithreading**, and **GUI development**. Supports concurrent client connections with thread-safe message broadcasting.

## 📋 Quick Info

| Aspect | Detail |
|--------|--------|
| **Language** | Java 8+ |
| **Architecture** | Client-Server (Socket-based) |
| **Concurrency** | Multithreading (1 thread/client) |
| **GUI Framework** | Swing |
| **Build Time** | ~2-3 hours |
| **Difficulty** | Intermediate |
| **External Libraries** | None (pure Java) |

## Features

✅ **Multi-client support** - Handle multiple connections simultaneously using threads  
✅ **Real-time messaging** - Broadcast messages to all connected clients  
✅ **GUI Interface** - User-friendly Swing-based client interface  
✅ **Timestamp support** - Messages include server-side timestamps  
✅ **User join/leave notifications** - Automatic notifications when users connect/disconnect  

## 🎯 Key Competencies Demonstrated

| Skill | Implementation |
|-------|-----------------|
| **Multithreading** | `Runnable` interface + `Thread` class for concurrent client handling |
| **Thread Safety** | `Collections.synchronizedSet()` for race condition prevention |
| **Socket Programming** | TCP/IP communication with ServerSocket & Socket classes |
| **Concurrent Broadcasts** | Synchronized message distribution to all connected clients |
| **GUI Development** | Swing components (JFrame, JPanel, JTextArea) with event listeners |
| **I/O Streams** | PrintWriter/BufferedReader for network communication |
| **Exception Handling** | Graceful error management and resource cleanup |

## Architecture

### Components

1. **Server.java** - Main server that listens for client connections
   - Accepts socket connections
   - Creates a new thread (ServerHandler) for each client
   - Maintains a synchronized set of all connected clients

2. **ServerHandler.java** - Handles individual client connections
   - Runs in a separate thread (Runnable)
   - Receives messages from client
   - Broadcasts to all connected clients
   - Manages client disconnection

3. **Client.java** - GUI client application
   - Swing-based user interface
   - Connects to server via Socket
   - Sends and receives messages
   - Real-time message display

## How to Compile & Run

### Step 1: Compile
```bash
cd src
javac Server.java ServerHandler.java Client.java
```

### Step 2: Run Server (Terminal 1)
```bash
java Server
```

You should see:
```
Chat Server starting on port 5555...
✓ Server is running. Waiting for clients...
```

### Step 3: Run Clients (Terminal 2, 3, 4...)
```bash
java Client
```

Each client will:
- Ask for a username
- Connect to the server
- Open a GUI window
- Display messages in real-time

## Technical Concepts Used

### 1. Multithreading
- `Runnable` interface implemented in ServerHandler
- One thread per client connection
- Concurrent message handling

### 2. Socket Programming
- Server Socket for listening
- Client Socket for connections
- PrintWriter/BufferedReader for I/O

### 3. Thread Safety
- `Collections.synchronizedSet()` for thread-safe client storage
- Synchronized broadcast to prevent race conditions

### 4. GUI Development
- JFrame, JPanel, JTextArea components
- Event listeners for user actions
- SwingUtilities.invokeLater() for thread-safe GUI updates

### 5. Object-Oriented Programming
- Proper encapsulation
- Single responsibility principle
- Reusable components

## Example Usage

**Server Terminal:**
```
Chat Server starting on port 5555...
✓ Server is running. Waiting for clients...
→ New client connected: 127.0.0.1
✓ Alice joined the chat
→ New client connected: 127.0.0.1
✓ Bob joined the chat
[Alice]: Hey Bob!
[Bob]: Hi Alice!
✗ Alice disconnected
```

**Client Terminals:**
Each opens a GUI with chat messages and input field.

## 🏆 Interview Highlights

**Q: How do you handle multiple clients?**
- Each client connection runs in a separate thread (ServerHandler implements Runnable)
- Server creates a new thread when a client connects
- Threads communicate via synchronized collections

**Q: What about thread safety?**
- Used `Collections.synchronizedSet()` to prevent concurrent modification exceptions
- Broadcast method is synchronized to ensure atomicity
- No race conditions in message delivery

**Q: Can you scale this?**
- Yes! Can handle 100+ concurrent clients with minimal overhead
- Thread pool could be added for better resource management
- Database could replace in-memory storage

## 📦 Requirements

- Java 8 or higher
- No external libraries required (uses built-in Java classes)

## 🚀 Possible Enhancements

- File transfer between clients
- Private messaging (1-to-1 chat)
- User authentication/login
- Message persistence (save chat history)
- Group chat rooms
- Emoji support
- Thread pool executor for better resource management
- Database integration (MySQL/PostgreSQL)