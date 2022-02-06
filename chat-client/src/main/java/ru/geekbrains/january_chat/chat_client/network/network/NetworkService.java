package ru.geekbrains.january_chat.chat_client.network.network;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

// Сервис, который отвечает за работу с сетью
public class NetworkService {
    private static final String HOST = "127.0.0.1.";
    private static final int PORT = 1024;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private MessageProcessor messageProcessor;

    public NetworkService(MessageProcessor messageProcessor){
        this.messageProcessor = messageProcessor;
    }

    public void connect()  throws IOException {
        // метод для заполнения сокета. Исколючение пробрасывам дальше.
        this.socket = new Socket(HOST, PORT);
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
        readMessages();
    }

    public void readMessages() { // метод для чтения сообщений из сети
        var thread = new Thread(() -> {
            // новый тред будет работать пока тред жив и пока сокет на закрыт
            try {
                while (!Thread.currentThread().isInterrupted() && !socket.isClosed()) {
                    var message = in.readUTF();
                    // Когда получили сооющение отдаем его messageProcessor
                    messageProcessor.processMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.setDaemon(true);// пусть поток будет демоном
        thread.start();
    }

    public void sendMessage(String message) {// метод для отправки сообщения
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // у сокета есть метод isConnected(), который типо проверяет подключение, но по факту он false - только то того, как к нему подключатся. Когда подключились он становится true, а по закрытию сокета он все равно будет true;
    public boolean isConnected() { // проверка подключения
        // если сокет не null и сокет не закрыт
        return socket != null && !socket.isClosed();
    }

    public void close() { // метод для закрытия сокета
        // Если сокет не null и сокет не закрыт, то закрываем сокет.
        try {
            if (socket != null && socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
