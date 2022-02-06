package ru.geekbrains.january_chat.chat_server.auth;

import ru.geekbrains.january_chat.chat_server.entity.User;
import ru.geekbrains.january_chat.chat_server.error.WrongCredentialsException;

import java.util.ArrayList;
import java.util.List;

public class InMemoryAuthService implements AuthService {

    private List<User> users;

    public InMemoryAuthService() {
        this.users = new ArrayList<>();
        users.addAll(List.of(
                new User("log1", "pass", "nick1", "secret"),
                new User("log2", "pass", "nick2", "secret"),
                new User("log3", "pass", "nick3", "secret"),
                new User("log4", "pass", "nick4", "secret"),
                new User("log5", "pass", "nick5 ", "secret")
        ));
    }

    @Override
    public void start() {
        System.out.println("Auth service started");
    }

    @Override
    public void stop() {
        System.out.println("Auth service stopped");
    }

    @Override
    public String authorizeUserByLoginAndPassword(String login, String password) {
        for (User user : users) {
            if (login.equals(user.getLogin()) && password.equals(user.getPassword())) {
                return user.getNick();
            }
        }
        throw new WrongCredentialsException("Wrong username or password");
    }

    @Override
    public String changeNick(String login, String newNick) {
        return null;
    }

    @Override
    public User createNewUser(String login, String password, String nick) {
        return null;
    }

    @Override
    public void deleteUser(String login, String pass) {

    }

    @Override
    public void changePassword(String login, String oldPass, String newPass) {

    }

    @Override
    public void resetPassword(String login, String newPass, String secret) {

    }
}
