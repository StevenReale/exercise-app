package com.portfolio.exerciseapp.model;

/**
 * Model class representing an application user.
 *
 * Contains information about the user - their id, username, password (hashed).
 */
public class User {

    private int userId;
    private String username;
    private String first;
    private String last;
    private String password;

    public User() {};

    public User(int userId, String username, String first, String last, String password) {
        this.userId = userId;
        this.username = username;
        this.first = first;
        this.last = last;
        this.password = password;
    }
}
