package com.portfolio.exerciseapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Model class representing an application user.
 *
 * Contains information about the user - their id, username, password (hashed) and authorities (user roles).
 */
public class User {

    private int id;
    private String username;
    private String first;

    private String last;

    // User roles
    private Set<Authority> authorities = new HashSet<>();

    @JsonIgnore
    private String password;

    /*
     * The activated property is not currently used by this application. It exists because it
     * is required by the common `security` package code. This allows for a user to be deactivated
     * (preventing log-in) in the future.
     *
     * For now, it is intentionally set to true in the constructors to always have 'active' users
     * and is not updatable. (There is no setter for this property.)
     */
    @JsonIgnore
    private boolean activated;


    public User() {
        this.activated = true;
    }

    public User(int id, String username, String password, String first, String last, String authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        if(authorities != null) this.setAuthorities(authorities);
        this.activated = true;
        this.first = first;
        this.last = last;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActivated() {
        return activated;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public void setAuthorities(String authorities) {
        String[] roles = authorities.split(",");
        for(String role : roles) {
            String authority = role.contains("ROLE_") ? role : "ROLE_" + role;
            this.authorities.add(new Authority(authority));
        }
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    /*
     * Note that the `equals` method must be overridden in a class to support checking object equality
     * based on the values of its properties - for example here, two User instances are
     * considered equal if they have the same `name` and `authorities` values. If this method is not
     * overridden in a class, the .equals method only returns true when comparing the same exact instance
     * of a class to itself.
     *
     * Note that it is a best practice to ensure that `equals` and `hashcode` methods are consistent by
     * overriding both of them together and using the same properties in both methods to check equality
     * and generate the hash.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                activated == user.activated &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(authorities, user.authorities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, activated, authorities);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", activated=" + activated +
                ", authorities=" + authorities +
                '}';
    }
}
