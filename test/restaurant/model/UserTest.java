/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.model;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import restaurant.model.exception.GeneralException;

/**
 *
 * @author $Le Hoang Hai
 */
public class UserTest {


    public UserTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of isActive method, of class User.
     */
    @Test
    public void testIsActive() {
        System.out.println("Test method isActive: Get the status of the account");
        UserImpl instance = new UserImpl("username", "Username1", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
        boolean expResult = true;
        boolean result = instance.isActive();
        assertEquals(expResult, result);
    }

    /**
     * Test of activate method, of class User.
     */
    @Test
    public void testActivate() {
        System.out.println("Test method activate: Set the status of the user to active");
         UserImpl instance = new UserImpl("username", "Username1", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
        instance.activate();
        boolean result = instance.isActive();
        boolean expResult = true;
        assertEquals(expResult, result);
    }

    /**
     * Test of deactivate method, of class User.
     */
    @Test
    public void testDeactivate() {
        System.out.println("Test method deactivate: Set the status of the user to deactive");
        UserImpl instance = new UserImpl("username", "Username1", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
        instance.deactivate();
        boolean result = instance.isActive();
        boolean expResult = false;
        assertEquals(expResult, result);
    }

    /**
     * Test of getAge method, of class User.
     */
    @Test
    public void testGetAge() {
        System.out.println("Test method getAge: Get age of the user");
        UserImpl instance = new UserImpl("username", "Username1", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
        int expResult = 20;
        int result = instance.getAge();
        assertEquals(expResult, result);
    }

    /**
     * Test of getName method, of class User.
     */
    @Test
    public void testGetName() {
        System.out.println("Test method getName: Get the name of the user");
        UserImpl instance = new UserImpl("username", "Username1", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
        String expResult = "Le Hoang Hai";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of setAge method, of class User.
     */
    @Test
    public void testSetAge() {
        System.out.println("Test method setAge: Set age of the user");
        UserImpl instance = new UserImpl("username", "Username1", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
        int age = 18;
        instance.setAge(age);
        int result = instance.getAge();
        assertEquals(age, result);
    }

    /**
     * Test of setName method, of class User.
     */
    @Test
    public void testSetName() {
        System.out.println("Test method setName: Set name of the user");
        String name = "Hoang Huy Cat";
        UserImpl instance = new UserImpl("username", "Username1", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
        instance.setName(name);
        String result = instance.getName();
        assertEquals(name, result);
    }

    /**
     * Test of setUsername method, of class User.
     */
    @Test
    public void testSetUsername() {
        System.out.println("Test method setUsername: Set username of the user");
        String username = "username1";
        UserImpl instance = new UserImpl("username", "Username1", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
        instance.setUsername(username);
        String result = instance.getUsername();
        assertEquals(username, result);
    }

    /**
     * Test of getPassword method, of class User.
     */
    @Test
    public void testGetPassword() {
        System.out.println("Test method getPassword: Get the password of the account");
        UserImpl instance = new UserImpl("username", "Username1", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
        String expResult = "Username1";
        String result = instance.getPassword();
        assertEquals(expResult, result);
    }

    /**
     * Test of getUsername method, of class User.
     */
    @Test
    public void testGetUsername() {
        System.out.println("Test method getUsername: Get the username of the user");
        UserImpl instance = new UserImpl("username", "Username1", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
        String expResult = "username";
        String result = instance.getUsername();
        assertEquals(expResult, result);
    }

    /**
     * Test of setPassword method, of class User.
     */
    @Test
    public void testSetPassword() {
        System.out.println("Test method setPassword: Set the password of the account");
        String password = "Username2";
        UserImpl instance = new UserImpl("username", "Username1", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
        instance.setPassword(password);
        String result = instance.getPassword();
        assertEquals(password, result);
    }

    /**
     * Test of getEmail method, of class User.
     */
    @Test
    public void testGetEmail() {
        System.out.println("Test method getEmail: Get the email of the user");
        UserImpl instance = new UserImpl("username", "Username1", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
        String expResult = "s3298775@rmit.edu.vn";
        String result = instance.getEmail();
        assertEquals(expResult, result);
    }

    /**
     * Test of setEmail method, of class User.
     */
    @Test
    public void testSetEmail() {
        System.out.println("Test method setEmail: Set the email of the user");
        String email = "abcdef@yahoo.com";
        UserImpl instance = new UserImpl("username", "Username1", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
        instance.setEmail(email);
        String result = instance.getEmail();
        assertEquals(email, result);
    }

    /**
     * Test of isGender method, of class User.
     */
    @Test
    public void testIsGender() {
        System.out.println("Test method isGender: Get the gender of the user");
        UserImpl instance = new UserImpl("username", "Username1", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
        boolean expResult = true;
        boolean result = instance.isGender();
        assertEquals(expResult, result);
    }

    /**
     * Test of setGender method, of class User.
     */
    @Test
    public void testSetGender() {
        System.out.println("Test method setGender: Set the gender of ther user");
        boolean gender = false;
        UserImpl instance = new UserImpl("username", "Username1", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
        instance.setGender(gender);
        boolean result = instance.isGender();
        assertEquals(gender, result);
    }

    /**
     * Test of equals method, of class User.
     */
    @Test
    public void testEquals1() {
        System.out.println("Test method equals 1: Check two account have same username");
        Object obj = new UserImpl("username", "Username2", "Hoang Huy Cat", 21, true, "abcdef@yahoo.com");
        UserImpl instance = new UserImpl("username", "Username1", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
        boolean expResult = true;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }

    @Test
    public void testEquals2() throws GeneralException, GeneralException {
        System.out.println("Test method equals 2: Check two account have different username");
        Object obj = new UserImpl("username1", "Username2", "Hoang Huy Cat", 21, true, "abcdef@yahoo.com");
        UserImpl instance = new UserImpl("username", "Username1", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }

    @Test
    public void testEquals3() throws GeneralException {
        System.out.println("Test method equals 3: Check accountwith other object");
        Object obj = new Table(new Location(1,1), "abc");
        UserImpl instance = new UserImpl("username", "Username1", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);
    }

    public class UserImpl extends User {

        public UserImpl(String username, String password, String name, int age, boolean gender, String email) {
            super(username, password, name, age, gender, email);
        }
    }
}
