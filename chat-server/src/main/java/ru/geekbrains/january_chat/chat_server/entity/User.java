package ru.geekbrains.january_chat.chat_server.entity;

public class User {
    private String login;
    private String password;
    private String nick;
    private String secret;

    public User(String login, String password, String nick, String secret) {
        this.login = login;
        this.password = password;
        this.nick = nick;
        this.secret = secret;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
