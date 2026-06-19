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
✅ **Message Logging** - All chat messages saved to `chat_log.txt` for persistence  
✅ **Command System** - `/help`, `/users`, `/clear`, `/quit` commands for user control  

## 💻 Commands

| Command | Description | Example |
|---------|-------------|---------|
| `/help` | Display all available commands | `/help` |
| `/users` | List all active connected users | `/users` |
| `/clear` | Clear your chat screen | `/clear` |
| `/quit` | Disconnect and exit chat | `/quit` |

**Example Usage:**
```
Type: /users
Output:
✓ Active Users (3):
  1. Alice
  2. Bob
  3. Charlie
```

## 📁 Message Logging

Every message sent in the chat is automatically saved to **`chat_log.txt`** in the project root directory.

- **What gets logged:**
  - All user messages with timestamps
  - User join/leave notifications
  - Server events
  
- **File Location:** `JavaChatApp/chat_log.txt`

- **Example Log Entry:**
  ```
  [14:30:45] Alice: Hello everyone!
  [14:30:50] Bob: Hi Alice!
  [14:31:02] SERVER: Charlie has left the chat
  ```

**Interview Point:** "Implemented file-based message persistence using FileWriter, allowing chat history retrieval and audit logging."

| Skill | Implementation |
|-------|-----------------|
| **Multithreading** | `Runnable` interface + `Thread` class for concurrent client handling |
| **Thread Safety** | `Collections.synchronizedSet()` for race condition prevention |
| **Socket Programming** | TCP/IP communication with ServerSocket & Socket classes |
| **Concurrent Broadcasts** | Synchronized message distribution to all connected clients |
| **GUI Development** | Swing components (JFrame, JPanel, JTextArea) with event listeners |
| **I/O Streams** | PrintWriter/BufferedReader for network communication |
| **Exception Handling** | Graceful error management and resource cleanup |
| **File I/O & Logging** | FileWriter for persistent message logging |
| **Command Parsing** | String parsing and validation for command processing |

## 📂 Project Structure

```
JavaChatApp/
├── src/
│   ├── Server.java           (Main server with message logging)
│   ├── ServerHandler.java    (Thread handler + command parser)
│   ├── Client.java           (Swing GUI client)
│   └── compile_and_run.bat   (Batch script for Windows)
├── chat_log.txt              (Auto-generated message log)
├── README.md                 (This file)
└── .git/                     (Version control)
```

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

## ✅ TESTING & VERIFICATION CHECKLIST

### Step 4: Test Features (after running server & 2+ clients)

#### ✓ Test Basic Messaging
1. In **Client 1**: Type `Hello everyone!`
2. **Verify:** Message appears in all other clients with timestamp
3. **Check:** Message logged in `chat_log.txt`

#### ✓ Test /help Command
1. In **Client 1**: Type `/help`
2. **Verify:** Shows all available commands
3. **Expected Output:**
   ```
   ╔═══════════════════════════════════╗
   ║     AVAILABLE COMMANDS             ║
   ╠═══════════════════════════════════╣
   ║ /help   - Show this help message  ║
   ║ /users  - List active users       ║
   ║ /clear  - Clear chat screen       ║
   ║ /quit   - Exit chat                ║
   ╚═══════════════════════════════════╝
   ```

#### ✓ Test /users Command
1. In **any client**: Type `/users`
2. **Verify:** Shows all connected users count and names
3. **Expected:** Should list Alice, Bob, etc.

#### ✓ Test /clear Command
1. In **any client**: Type `/clear`
2. **Verify:** Your screen clears (50+ blank lines)

#### ✓ Test /quit Command
1. In **Client 1**: Type `/quit`
2. **Verify:** Client 1 disconnects
3. **Check:** Other clients see "Alice has left the chat"

#### ✓ Test Message Logging
1. After chat session, check **`chat_log.txt`** in project root
2. **Verify:** All messages appear with timestamps
3. **Example:**
   ```
   [HH:MM:SS] Alice: Hello
   [HH:MM:SS] Bob: Hi Alice
   [HH:MM:SS] SERVER: Charlie has joined the chat
   ```

#### ✓ Test Concurrent Messages
1. Open 3-4 clients
2. Send messages from multiple clients simultaneously
3. **Verify:** All messages broadcast to everyone in real-time
4. **Verify:** Timestamps are accurate
5. **Check:** `chat_log.txt` has all messages in correct order

### Common Issues & Fixes

| Issue | Solution |
|-------|----------|
| "Connection refused" | Make sure Server is running first |
| GUI doesn't appear | Java 8+ required; try `java -version` |
| Messages not appearing | Check both clients are connected |
| `chat_log.txt` not found | It's created automatically on first message |

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

## 🚀 Possible Future Enhancements

- **Private messaging (1-to-1 chat)** - Direct messages between specific users
- **User authentication/login** - Username & password validation
- **Group chat rooms** - Multiple separate chat channels
- **Emoji & formatting support** - Rich text messaging
- **Thread pool executor** - Better resource management for 1000+ users
- **Database integration** - MySQL/PostgreSQL for persistent user storage
- **Message search** - Find messages in chat history
- **User profiles** - Display user info and avatars

## 📊 Project Statistics

- **Lines of Code:** ~300
- **Classes:** 3 (Server, ServerHandler, Client)
- **Threading Model:** 1 thread per client connection
- **Max Concurrent Users:** 100+ (tested locally)
- **Build Time:** 2-3 hours
- **Difficulty Level:** Intermediate