package com.portfolio.exerciseapp.dao;

import com.portfolio.exerciseapp.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * The JdbcUserDao class is used for interacting with the user information in the datastore.
 *
 * While the DAO pattern allows us to encapsulate and abstract interactions with a data store,
 * this implementation class is specifically used to access data from a SQL database using JDBC.
 *
 * This DAO supports only Create and Read access for Users.
 * Note that password information is salted and hashed prior to being stored in the database.
 */
@Component
public class JdbcUserDao implements UserDao {

    // all of these methods will need rewritten (just copied and pasted from sample app)

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int getIdByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");

        int userId;
        try {
            userId = jdbcTemplate.queryForObject("SELECT user_id FROM users WHERE username = ?", int.class, username);
        } catch (EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException("User " + username + " was not found.");
        }

        return userId;
    }

    @Override
    public boolean userExists(String username) {
        int userCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users WHERE username = ?;", int.class, username);
        return (userCount == 1);
    }

    @Override
    public User getUserById(int userId) {
        String sql = "SELECT user_id, username, password_hash, first, last, role FROM users WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        if (results.next()) {
            return mapRowToUser(results);
        } else {
            return null;
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        // Intentionally excluding password_hash - Not a good idea to allow mass selection of user password data (even if hashed).
        String sql = "SELECT user_id, username, first, last, role FROM users ORDER BY username;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            User user = mapRowToUser(results);
            users.add(user);
        }

        return users;
    }

    @Override
    public User getByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");

        String sql = "SELECT user_id, username, password_hash, first, last, role FROM users WHERE username = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        if (results.next()) {
            return mapRowToUser(results);
        } else {
            throw new UsernameNotFoundException("User" + username + " was not found.");
        }
    }

    @Override
    public User create(String username, String password, String role) {
        String insertUserSql = "INSERT INTO users (username,password_hash,role) VALUES (?,?,?) RETURNING user_id";
        String password_hash = new BCryptPasswordEncoder().encode(password);
        String ssRole = role.toUpperCase().startsWith("ROLE_") ? role.toUpperCase() : "ROLE_" + role.toUpperCase();

        int userId = jdbcTemplate.queryForObject(insertUserSql, Integer.class, username, password_hash, ssRole);
        return getUserById(userId);
    }

    @Override
    public User update(User modifiedUser) {
        String sql = "UPDATE users SET first=?, last=? WHERE user_id=?;";
        jdbcTemplate.update(sql, modifiedUser.getFirst(), modifiedUser.getLast(), modifiedUser.getId());
        return getUserById(modifiedUser.getId());
    }

    /*
     * Helper method to convert a SqlRowSet into a User object.
     */
    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        // Password column is not always included
        if (hasColumnName(rs, "password_hash")) {
            user.setPassword(rs.getString("password_hash"));
        }
        user.setAuthorities(Objects.requireNonNull(rs.getString("role")));
        user.setFirst(rs.getString("first"));
        user.setLast(rs.getString("last"));
        return user;
    }

    /*
     * Helper method to determine if a SqlRowSet contains a particular column.
     * Used by the mapRowToUser method to check if the password_hash is included.
     */
    private boolean hasColumnName(SqlRowSet rs, String columnName) {
        SqlRowSetMetaData rsMetaData = rs.getMetaData();
        int numberOfColumns = rsMetaData.getColumnCount();

        // resultSet column indexes start from 1
        for (int i = 1; i < numberOfColumns + 1; i++) {
            String currentColumn = rsMetaData.getColumnName(i);
            if (columnName.equalsIgnoreCase(currentColumn)) {
                return true;
            }
        }
        return false;
    }
}
