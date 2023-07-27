package com.portfolio.exerciseapp.dao;

import com.portfolio.exerciseapp.model.User;

import java.util.List;

/**
 * DAO class for interacting with the user information in the data store.
 *
 * The DAO pattern allows us to encapsulate and abstract interactions with a data store.
 * By using an interface, we are able to change the implementing class to support different
 * types of data sources. For example, we might have a DAO implementation that uses a database,
 * or we might have one that gets information from a file store. The methods are the same, but
 * the implementation, or the logic within those methods may be very different.
 */
public interface UserDao {

    /**
     * Get all users from the datastore ordered alphabetically by username.
     *
     * @return List of all User objects, or an empty list if no Users are found.
     */
    List<User> getAll();

    /**
     * Get a user from the datastore with the specified id.
     * If the id is not found, returns null.
     *
     * @param userId The id of the user to return.
     * @return The matching User object.
     */
    User getUserById(int userId);

    /**
     * Get a user from the datastore with the specified username.
     * If the username is not found, throws UsernameNotFoundException.
     *
     * @param username The username of the user to return.
     * @return The matching User object.
     */
    User getByUsername(String username);

    /**
     * Get a user id from the datastore with the specified id.
     * If the username is not found, throws UsernameNotFoundException.
     *
     * @param username The id of the user to return.
     * @return The matching User's id.
     */
    int getIdByUsername(String username);

    /**
     * Checks if a username exists.
     * @return true if the username exists in the datastore, false otherwise
     */
    boolean userExists(String username);

    /**
     * Adds a new user to the datastore.
     *
     * @param username the username for the new user
     * @param password the password for the new user
     * @param role the role for the new user
     * @return The new User object with its new id filled in.
     */
    User create(String username, String password, String role);

    /**
     * Updates the user profile information - display name, image url, and short bio.
     * @param modifiedUser the user data to update
     * @return the updated User object
     */
    User update(User modifiedUser);
}