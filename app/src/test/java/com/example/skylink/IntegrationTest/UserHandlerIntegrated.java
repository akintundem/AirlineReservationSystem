package com.example.skylink.IntegrationTest;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.example.skylink.TestUtils.TestUtils;
import com.example.skylink.application.Services;
import com.example.skylink.business.Implementations.UserHandler;
import com.example.skylink.business.Interface.IUserHandler;
import com.example.skylink.objects.Implementations.UserProperties;
import com.example.skylink.objects.Interfaces.IUserProperties;
import com.example.skylink.presentation.Session;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class UserHandlerIntegrated {
    private IUserHandler userHandler;
    private File tempDB;

    @Before
    public void setUp() throws IOException {
        System.out.println("Starting integration test for User Handler");
        this.tempDB = TestUtils.copyDB();
        this.userHandler = new UserHandler(true);
        assertNotNull(userHandler);

    }

    @Test
    public void testCreateUser_Success() {
        IUserProperties mockUserProperties = new UserProperties("Mayokun Moses Akintunde", "person1@gmail.com", "mayor101");
        String rePassword = "mayor101";

        try {
            userHandler.createUser(mockUserProperties, rePassword);
        } catch (UserHandler.UserValidationException e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    public void testCreateUser_EmptyName() {
        IUserProperties mockUserProperties = new UserProperties("", "akintundemayokun@gmail.com", "mayor101");
        String rePassword = "mayor101";

        try {
            userHandler.createUser(mockUserProperties, rePassword);
            fail("User creation should fail for empty name");
        } catch (UserHandler.UserValidationException e) {
            assertEquals("Name cannot be empty", e.getMessage());
        }
    }

    @Test
    public void testCreateUser_InvalidEmailFormat() {
        IUserProperties mockUserProperties = new UserProperties("Mayokun Moses Akintunde", "invalidemail", "mayor101");
        String rePassword = "mayor101";

        try {
            userHandler.createUser(mockUserProperties, rePassword);
            fail("User creation should fail for invalid email format");
        } catch (UserHandler.UserValidationException e) {
            assertEquals("Invalid email format", e.getMessage());
        }
    }

    @Test
    public void testCreateUser_PasswordMismatch() {
        IUserProperties mockUserProperties = new UserProperties("Mayokun Moses Akintunde", "akintundemayokun@gmail.com", "mayor101");
        String rePassword = "differentPassword";

        try {
            userHandler.createUser(mockUserProperties, rePassword);
            fail("User creation should fail for password mismatch");
        } catch (UserHandler.UserValidationException e) {
            assertEquals("Passwords do not match", e.getMessage());
        }
    }

    @Test
    public void testUpdateUserProfile_Success() {
        // Create a user for testing
        IUserProperties mockUserProperties = new UserProperties(
                "Mayokun Moses Akintunde",
                "akintundemayokun@gmail.com",
                "mayor101"
        );
        String rePassword = "mayor101";

        try {
            userHandler.createUser(mockUserProperties, rePassword);
        } catch (UserHandler.UserValidationException e) {
            fail("Exception should not be thrown");
        }

        // Update the user profile
        IUserProperties updatedUserProperties = new UserProperties(
                "Mayokun M. Akintunde",
                "mayorakintunde@gmail.com",
                "mayor101",
                "Male",
                "456 Side St",
                "9876543210",
                "1990-01-01",
                "New Country"
        );

        try {
            boolean result = userHandler.updateUserProfile(updatedUserProperties);
            assertTrue(result);
        } catch (UserHandler.UserValidationException e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    public void testUpdateUserProfile_EmptyPassword() {
        // Create a user for testing
        IUserProperties mockUserProperties = new UserProperties(
                "Mayokun Moses Akintunde",
                "person2@gmail.com",
                "mayor101"
        );
        String rePassword = "mayor101";

        try {
            userHandler.createUser(mockUserProperties, rePassword);
        } catch (UserHandler.UserValidationException e) {
            fail("Exception should not be thrown");
        }

        // Update the user profile with an empty password
        IUserProperties updatedUserProperties = new UserProperties(
                "Mayokun M. Akintunde",
                "person2@gmail.com",
                "",
                "Male",
                "456 Side St",
                "9876543210",
                "1990-01-01",
                "New Country"
        );

        assertThrows(UserHandler.UserValidationException.class, () -> {
            userHandler.updateUserProfile(updatedUserProperties);
        });
    }

    @Test
    public void testUpdateUserProfile_NullUserProperties() {
        // Create a user for testing
        IUserProperties mockUserProperties = new UserProperties(
                "Mayokun Moses Akintunde",
                "person3@gmail.com",
                "mayor101"
        );
        String rePassword = "mayor101";

        try {
            userHandler.createUser(mockUserProperties, rePassword);
        } catch (UserHandler.UserValidationException e) {
            fail("Exception should not be thrown");
        }

        // Perform the test with null user properties
        try {
            boolean result = userHandler.updateUserProfile(null);
            assertFalse(result);
        } catch (UserHandler.UserValidationException e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    public void testSignin_Success() {
        IUserProperties mockUserProperties = new UserProperties("Mayokun Moses Akintunde", "person1@gmail.com", "mayor101");
        String rePassword = "mayor101";

        try {
            userHandler.createUser(mockUserProperties, rePassword);         // add user to the database (it will hash the password)

            // create same user again with normal password
            IUserProperties mockUserProperties2 = new UserProperties("Mayokun Moses Akintunde", "person1@gmail.com", "mayor101");
            boolean result = userHandler.signinUser(mockUserProperties2);
            assertTrue(result);
        } catch (UserHandler.UserValidationException e) {
            fail("Exception should not be thrown");
        }
    }

    @Test
    public void testSignin_Fail() {
        IUserProperties mockUserProperties = new UserProperties("Mayokun Moses Akintunde", "person1@gmail.com", "mayor101");
        String rePassword = "mayor101";

        try {
            userHandler.createUser(mockUserProperties, rePassword);         // add user to the database (it will hash the password)
            IUserProperties mockUserProperties2 = new UserProperties("Mayokun Moses Akintunde", "person1@gmail.com", "mayor111");
            boolean result = userHandler.signinUser(mockUserProperties2);   // should fail because password is different
            assertFalse(result);
        } catch (UserHandler.UserValidationException e) {
            fail("Exception should not be thrown");
        }

    }

    @Test
    public void testUserIdByEmail() {
        IUserProperties mockUserProperties = new UserProperties("Mayokun Moses Akintunde", "person1@gmail.com", "mayor101");
        String rePassword = "mayor101";

        long userIdFromSession = -1;

        try {
            userHandler.createUser(mockUserProperties, rePassword);
            userIdFromSession = Session.getInstance().getUserProperties().getUser_id();

            long resultingUserId = userHandler.getUserIdByEmail("person1@gmail.com");

            assertEquals(userIdFromSession, resultingUserId);

        } catch (UserHandler.UserValidationException e) {
            fail("Exception should not be thrown");
        }

    }

    @Test
    public void testUserIdByWrongEmail() {
        IUserProperties mockUserProperties = new UserProperties("Mayokun Moses Akintunde", "person1@gmail.com", "mayor101");
        String rePassword = "mayor101";

        long userIdFromSession = -1;

        try {
            userHandler.createUser(mockUserProperties, rePassword);

            long resultingUserId = userHandler.getUserIdByEmail("person2@gmail.com");

            assertEquals(userIdFromSession, resultingUserId);


        } catch (UserHandler.UserValidationException e) {
            fail("Exception should not be thrown");

        }

    }

    @Test
    public void testUserByEmail() {
        IUserProperties mockUserProperties = new UserProperties("Mayokun Moses Akintunde", "person1@gmail.com", "mayor101");
        String rePassword = "mayor101";

        IUserProperties userFromSession = null;
        try {
            userHandler.createUser(mockUserProperties, rePassword);
            Session.getInstance().getUserProperties().setFullName(mockUserProperties.getFullName());
            Session.getInstance().getUserProperties().setEmail(mockUserProperties.getEmail());
            Session.getInstance().getUserProperties().setPassword(mockUserProperties.getPassword());

            userFromSession = Session.getInstance().getUserProperties();

            IUserProperties resultingUser = userHandler.getUserByEmail("person1@gmail.com");

            assertEquals(userFromSession.getFullName(), resultingUser.getFullName());
            assertEquals(userFromSession.getEmail(), resultingUser.getEmail());
            assertEquals(userFromSession.getPassword(), resultingUser.getPassword());


        } catch (UserHandler.UserValidationException e) {
            fail("Exception should not be thrown");

        }

    }

    @After
    public void tearDown() {
        System.out.println("Reset database.");
        // reset DB
        this.tempDB.delete();

        // clear Services
        Services.clean();
    }
}
