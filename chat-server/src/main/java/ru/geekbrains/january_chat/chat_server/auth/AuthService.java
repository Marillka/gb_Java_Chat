package ru.geekbrains.january_chat.chat_server.auth;

import ru.geekbrains.january_chat.chat_server.entity.User;

public interface AuthService {
    void start();
    void stop();
    String authorizeUserByLoginAndPassword(String login, String password);
    String changeNick(String login, String newNick);
    User createNewUser(String login, String password, String nick);
    void deleteUser(String login, String pass);
    void changePassword(String login,String oldPass, String newPass);
    void resetPassword(String login, String newPass, String secret);
}
