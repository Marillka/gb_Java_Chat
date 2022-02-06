package ru.geekbrains.january_chat.chat_server.server;

import ru.geekbrains.january_chat.chat_server.auth.AuthService;
import ru.geekbrains.january_chat.chat_server.auth.InMemoryAuthService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class Server {
    public static final String REGEX = "%!%";
    private static final int PORT = 1024;
    private AuthService authService;// ссылаемся на интерфейс
    private List<ClientHandler> clientHandlers;

    public Server(AuthService authService) {
        this.clientHandlers = new ArrayList<>();
        this.authService = authService;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server start!");
            while (true) {
                System.out.println("Waiting for connection.....");
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");
                ClientHandler clientHandler = new ClientHandler(socket, this);
                clientHandler.handle();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            authService.stop();
            shutdown();
        }
    }

    public void privateMessage(String fromNick, String toNick, String message) {
        for (ClientHandler clientHandler : clientHandlers) {
            if (clientHandler.getUserNick().equals(toNick)) {
                message = String.format("Private from %s to %s: %s", fromNick, toNick, message);
                clientHandler.send(message);
            }
        }
    }

    public void broadcastMessage(String from, String message) {
        message = "/broadcast" + REGEX + from + REGEX + message;
        for (ClientHandler clientHandler : clientHandlers) {
            clientHandler.send(message);
        }
    }

    public synchronized void addAuthorizedClientToList(ClientHandler clientHandler) {
        clientHandlers.add(clientHandler);
        sendOnlineClients();
    }

    public synchronized void removeAuthorizedClientToList(ClientHandler clientHandler) {
        clientHandlers.remove(clientHandler);
        sendOnlineClients();
    }

    public void sendOnlineClients() {
        var sb = new StringBuilder("/list");
        sb.append(REGEX);
        for (ClientHandler clientHandler : clientHandlers) {
            sb.append(clientHandler.getUserNick());
            sb.append(REGEX);
        }
        var message = sb.toString();
        for (ClientHandler clientHandler : clientHandlers) {
            clientHandler.send(message);
        }
    }

    public synchronized boolean isNickBusy(String nick) {
        for (ClientHandler clientHandler : clientHandlers) {
            if(clientHandler.getUserNick().equals(nick)) {
                return true;
            }
        }
        return false;
    }

    private void shutdown() {

    }

    public AuthService getAuthService() {
        return authService;
    }
}