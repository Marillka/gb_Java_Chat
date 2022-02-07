package ru.geekbrains.january_chat.chat_server.error;

public class WrongCredentialsException extends RuntimeException {

    public WrongCredentialsException() {
    }

    public WrongCredentialsException(String message) {
        super(message);
    }
}
