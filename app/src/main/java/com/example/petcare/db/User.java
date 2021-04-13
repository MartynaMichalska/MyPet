package com.example.petcare.db;

public class User {

    public String getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    private final String id;
    private final String login;


    public User(String id, String login) {
        this.id = id;
        this.login = login;
    }
    public User() {
        this.id ="";
        this.login ="";
    }
}
