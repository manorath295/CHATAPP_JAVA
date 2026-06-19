@echo off
echo Compiling Java Chat Application...
cd src
javac Server.java ServerHandler.java Client.java

if %ERRORLEVEL% EQU 0 (
    echo.
    echo Compilation successful!
    echo.
    echo To run the application:
    echo 1. Run this in terminal 1: java Server
    echo 2. Run this in terminal 2: java Client
    echo 3. Run this in terminal 3: java Client
    echo.
    echo Open multiple client terminals for testing!
) else (
    echo Compilation failed!
    pause
)
