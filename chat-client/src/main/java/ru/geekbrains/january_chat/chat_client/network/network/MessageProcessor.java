package ru.geekbrains.january_chat.chat_client.network.network;

// Нечто, что должно обрабатывать входящие сообщения по сети
public interface MessageProcessor {
    void processMessage(String message);
}
