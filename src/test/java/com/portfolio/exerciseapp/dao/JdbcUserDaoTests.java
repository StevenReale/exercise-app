package com.portfolio.exerciseapp.dao;

import com.portfolio.exerciseapp.model.Authority;
import com.portfolio.exerciseapp.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class JdbcUserDaoTests extends BaseDaoTests {

    private static final User USER_ADMIN = new User(100, "admin", "$2a$10$We8.y4IV/uQOPT1crppxR.aASgeKFr24ISrkHcqWWSYlxRu4oeqE6", "Diane", "Gress", "ROLE_ADMIN");
    private static final User USER_1 = new User(101, "user1", "$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC", "Stephen", "Reale", "ROLE_USER");
    private static final User USER_2 = new User(102, "user2", "$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC", "Suzie", "Q", "ROLE_USER");

    private static final List<User> ALL_USERS = Arrays.asList(new User[] {USER_ADMIN, USER_1, USER_2});

    private JdbcUserDao dao;

    @Before
    public void setup() {
        dao = new JdbcUserDao(dataSource);
    }

    @Test
    public void getAll_returns_correct_list_sorted_by_name() {
        List<User> result = dao.getAll();
        Assert.assertNotNull("getAll returned null", result);
        assertUserListsMatch("getAll returned wrong or partial data", ALL_USERS, result);
    }

    @Test
    public void getUserById_returns_correct_user() {
        User result = dao.getUserById(USER_1.getId());
        Assert.assertNotNull("getUserById returned null", result);
        assertUsersMatch("getUserById returned wrong or partial data", USER_1, result);
    }

    @Test
    public void getUserById_returns_null_when_id_not_found() {
        User result = dao.getUserById(9999);
        Assert.assertNull("getUserById failed to return null for id not in database", result);
    }

    @Test
    public void userExists_returns_true_for_existing_username() {
        boolean result = dao.userExists(USER_1.getUsername());
        Assert.assertTrue("Expected true for existing username", result);
    }

    @Test
    public void userExists_returns_false_for_new_username() {
        boolean result = dao.userExists("New User - Test Exists");
        Assert.assertFalse("Expected false for new username", result);
    }

    @Test
    public void create_returns_user_with_id_and_correct_values() {
        User newUser = new User(0, "new_user", "password", null, null, "ROLE_TEST");
        User createdUser = dao.create(newUser.getUsername(), "password", "TEST");

        Assert.assertNotNull("create returned null", createdUser);

        int newId = createdUser.getId();
        Assert.assertTrue("create returned a user without an id", newId > 0);

        newUser.setId(newId);
        assertUsersMatch("create returned a user with wrong or partial data", newUser, createdUser);
    }

    @Test
    public void created_user_has_expected_values_when_retrieved() {
        User newUser = new User(0, "new_user", "password", "New", "Person", "ROLE_TESt");
        User createdUser = dao.create(newUser.getUsername(), "password", "TEST");

        int newId = createdUser.getId();
        User retrievedUser = dao.getUserById(newId);

        assertUsersMatch("create did not save correct data in database", createdUser, retrievedUser);
    }

    @Test
    public void update_returns_user_with_correct_values() {
        User user = new User();
        user.setId(USER_2.getId());
        user.setUsername(USER_2.getUsername());
        user.setAuthorities(USER_2.getAuthorities());

        // change updatable fields
        user.setFirst("Modified First");
        user.setLast("Modified Last");

        User result = dao.update(user);
        assertUsersMatch("update returned wrong or partial data", user, result);
    }

    @Test
    public void updated_user_has_expected_values_when_retrieved() {
        User user = new User();
        user.setId(USER_2.getId());
        user.setUsername(USER_2.getUsername());
        user.setAuthorities(USER_2.getAuthorities());

        // change updatable fields
        user.setFirst("Modified First");
        user.setLast("Modified Last");

        User result = dao.update(user);
        User retrievedUser = dao.getUserById(result.getId());
        assertUsersMatch("update returned wrong or partial data", user, retrievedUser);
    }


    private void assertUsersMatch(String messagePrefix, User expected, User actual) {
        Assert.assertEquals(messagePrefix + ": User user_id field does not match expected value.", expected.getId(), actual.getId());
        Assert.assertEquals(messagePrefix + ": User (user_id=" + expected.getId() + ") contains unexpected data in field 'user_id'.",
                expected.getId(), actual.getId());
        Assert.assertEquals(messagePrefix + ": User (user_id=" + expected.getId() + ") contains unexpected data in field 'username'.",
                expected.getUsername(), actual.getUsername());
        // Password will be hashed differently each time, and is not returned by all queries, so ignore

        Assert.assertEquals(messagePrefix + ": User (user_id=" + expected.getId() + ") contains wrong number of authorities.",
                expected.getAuthorities().size(), actual.getAuthorities().size());
        for (Authority authority : expected.getAuthorities()) {
            Assert.assertTrue(messagePrefix + ": User (user_id=" + expected.getId() + ") does not contain expected authority " + authority.getName(),
                    actual.getAuthorities().contains(authority));
        }
        Assert.assertEquals(messagePrefix + ": User first field does not match expected value.",
                expected.getFirst(), actual.getFirst());
        Assert.assertEquals(messagePrefix + ": User last field does not match expected value.",
                expected.getLast(), actual.getLast());


    }

    private void assertUserListsMatch(String messagePrefix, List<User> expectedList, List<User> actualList) {
        Assert.assertEquals(messagePrefix + ": List size incorrect.", expectedList.size(), actualList.size());
        for (int i=0; i< expectedList.size(); i++) {
            User expected = expectedList.get(i);
            User actual = actualList.get(i);
            if (i==0) {
                // For first item, if ids do not match, may indicate bad sorting
                Assert.assertEquals(messagePrefix + ": first user id does not match expected, check sort order",
                        expected.getId(), actual.getId());
            }
            assertUsersMatch(messagePrefix, expected, actual);
        }
    }
}
