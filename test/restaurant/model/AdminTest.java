/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.model;

import restaurant.model.exception.DuplicateException;
import restaurant.model.exception.GeneralException;
import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class AdminTest {

    private static Admin instance;

    public AdminTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        instance = new Admin("admin", "Admin1", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of addUser method, of class Admin.
     */
    @Test
    public void testAddUser() throws Exception {
        System.out.println("Test method addUser 1: add valid a User ");
        User user = new Admin("admin1", "Admin1", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
        ArrayList<User> listUser = new ArrayList<User>();
        int expResult = 1;
        instance.addUser(user, listUser);
        int result = listUser.size();
        assertEquals(expResult, result);
    }

    @Test(expected = GeneralException.class)
    public void testAddUser1() throws Exception {
        System.out.println("Test method addUser 2: add a User with duplicate username");
        User user1 = new Admin("admin1", "Admin1", "Hai", 20, true, "s3298775@rmit.edu.vn");
        User user2 = new Admin("admin1", "Admin1", "Hai", 20, true, "s3298775@rmit.edu.vn");
        ArrayList<User> listUser = new ArrayList<User>();
        boolean expResult = true;
        instance.addUser(user1, listUser);
        instance.addUser(user2, listUser);
    }

    /**
     * Test of findUser method, of class Admin.
     */
    @Test
    public void testFindUser() throws DuplicateException {
        System.out.println("Test method findUser 1: find an existed User");
        String username = "manager";
        ArrayList<User> listUser = new ArrayList<User>();
        User expResult = new Manager("manager", "Manager1", "Hoang Huy Cat", 20, true, "s3298775@rmit.edu.vn");
        instance.addUser(expResult, listUser);
        User result = instance.findUser(username, listUser);
        assertEquals(expResult, result);
    }

    @Test
    public void testFindUser1() throws DuplicateException {
        System.out.println("Test method findUser 2: find an User not existed");
        String username = "admin1";
        ArrayList<User> listUser = new ArrayList<User>();
        User expResult = null;
        User result = instance.findUser(username, listUser);
        assertEquals(expResult, result);
    }

    /**
     * Test of editUser method, of class Admin.
     */
    @Test
    public void testEditUser() throws Exception {
        System.out.println("Test method editUser 1: Edit information of an user");
        User result = new Manager("manager", "Manager1", "Hoang Huy Cat", 20, true, "s3298775@rmit.edu.vn");
        String username = "manager1";
        String password = "Manager2";
        String confirm = "Manager2";
        String name = "Nguyen Thanh Luan";
        int age = 19;
        boolean gender = false;
        String email = "abcdef@yahoo.com";
        instance.editUser(result, username, password, confirm, name, age, gender, email);
        User expResult = new Manager("manager1", "Manager2", "Nguyen Thanh Luan", 19, false, "abcdef@yahoo.com");
        assertEquals(expResult, result);
    }

    @Test(expected = GeneralException.class)
    public void testEditUser1() throws Exception {
        System.out.println("Test method editUser 2: Edit information of an user with invalid confirm password");
        User result = new Manager("manager", "Manager1", "Hoang Huy Cat", 20, true, "s3298775@rmit.edu.vn");
        String username = "manager1";
        String password = "Manager2";
        String confirm = "Manager3";
        String name = "Nguyen Thanh Luan";
        int age = 19;
        boolean gender = false;
        String email = "abcdef@yahoo.com";
        instance.editUser(result, username, password, confirm, name, age, gender, email);
        User expResult = new Manager("manager1", "Manager2", "Nguyen Thanh Luan", 19, false, "abcdef@yahoo.com");
        assertEquals(expResult, result);
    }

    /**
     * Test of checkUsername method, of class Admin.
     */
    @Test
    public void testCheckUsername1() {
        System.out.println("Test method checkUsername 1: Check valid username");
        String username = "admin_-1";
        boolean expResult = true;
        boolean result = instance.checkUsername(username);
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckUsername2() {
        System.out.println("Test method checkUsername 2: Check invalid empty username");
        String username = "";
        boolean expResult = false;
        boolean result = instance.checkUsername(username);
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckUsername3() {
        System.out.println("Test method checkUsername 3: Check invalid username contains space");
        String username = "admin  ";
        boolean expResult = false;
        boolean result = instance.checkUsername(username);
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckUsername4() {
        System.out.println("Test method checkUsername 4: Check invalid username contains special character");
        String username = "admin@";
        boolean expResult = false;
        boolean result = instance.checkUsername(username);
        assertEquals(expResult, result);
    }

    /**
     * Test of checkPassword method, of class Admin.
     */
    @Test
    public void testCheckPassword1() {
        System.out.println("Test method checkPassword 1: Check valid password");
        String password = "Admin1";
        boolean expResult = true;
        boolean result = instance.checkPassword(password);
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckPassword2() {
        System.out.println("Test method checkPassword 2: Check invalid password without number");
        String password = "Administrator";
        boolean expResult = false;
        boolean result = instance.checkPassword(password);
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckPassword3() {
        System.out.println("Test method checkPassword 3: Check invalid password without uppercase character");
        String password = "admin1";
        boolean expResult = false;
        boolean result = instance.checkPassword(password);
        assertEquals(expResult, result);
    }

    /**
     * Test of checkName method, of class Admin.
     */
    @Test
    public void testCheckName1() {
        System.out.println("Test method checkName 1: Check valid fullname");
        String name = "Le Hoang Hai";
        boolean expResult = true;
        boolean result = instance.checkName(name);
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckName2() {
        System.out.println("Test method checkName 2: Check invalid fullname less than two words");
        String name = "Hai";
        boolean expResult = false;
        boolean result = instance.checkName(name);
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckName3() {
        System.out.println("Test method checkName 3: Check invalid fullname with special character");
        String name = "Le Hoang H@i";
        boolean expResult = false;
        boolean result = instance.checkName(name);
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckName4() {
        System.out.println("Test method checkName 4: Check invalid fullname with number");
        String name = "Le Hoang Ha1";
        boolean expResult = false;
        boolean result = instance.checkName(name);
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckName5() {
        System.out.println("Test method checkName 4: Check invalid fullname with two spaces between 2 words");
        String name = "Le Hoang  Hai";
        boolean expResult = false;
        boolean result = instance.checkName(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of checkEmail method, of class Admin.
     */
    @Test
    public void testCheckEmail() {
        System.out.println("Test method checkEmail 1: Check valid email");
        String email = "s3298.75@rmit.edu.vn";
        boolean expResult = true;
        boolean result = instance.checkEmail(email);
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckEmail1() {
        System.out.println("Test method checkEmail 2: Check invalid email without '@'");
        String email = "s3298775.rmit.edu.vn";
        boolean expResult = false;
        boolean result = instance.checkEmail(email);
        assertEquals(expResult, result);
    }

    @Test
    public void testCheckEmail3() {
        System.out.println("Test method checkEmail 3: Check invalid email with invalid domain (without .)");
        String email = "s3298775@rmit";
        boolean expResult = false;
        boolean result = instance.checkEmail(email);
        assertEquals(expResult, result);
    }

    /**
     * Test of deactivateUser method, of class Admin.
     */
    @Test
    public void testDeactivateUser() throws Exception {
        System.out.println("Test method deactivateUser 1: Deactivate a actived account");
        String username = "admin";
        ArrayList<User> listUser = new ArrayList<User>();
        instance.addUser(instance, listUser);
        instance.deactivateUser(username, listUser);
        boolean expResult = false;
        boolean result = listUser.get(0).isActive();
        assertEquals(expResult, result);
    }

    @Test(expected = GeneralException.class)
    public void testDeactivateUser1() throws Exception {
        System.out.println("Test method deactivateUser 2: Deactivate a unexisted account");
        String username = "NOTHING";
        ArrayList<User> listUser = new ArrayList<User>();
        instance.addUser(instance, listUser);
        instance.deactivateUser(username, listUser);
        boolean expResult = false;
        boolean result = listUser.get(0).isActive();
        assertEquals(expResult, result);
    }

    /**
     * Test of activateUser method, of class Admin.
     */
    @Test
    public void testActivateUser() throws Exception {
        System.out.println("Test method activateUser 1: Activate a deactived account");
        String username = "manager";
        User user = new Manager("manager", "Manager1", "Hoang Huy Cat", 20, true, "s3298775@rmit.edu.vn");
        ArrayList<User> listUser = new ArrayList<User>();
        instance.addUser(user, listUser);
        instance.deactivateUser(username, listUser);
        instance.activateUser(username, listUser);
        boolean expResult = true;
        boolean result = listUser.get(0).isActive();
        assertEquals(expResult, result);
    }

    @Test(expected = GeneralException.class)
    public void testActivateUser1() throws Exception {
        System.out.println("Test method activateUser 2: Activate a unexisted accountd");
        String username = "NOTHING";
        ArrayList<User> listUser = new ArrayList<User>();
        instance.addUser(instance, listUser);
        instance.activateUser(username, listUser);
        boolean expResult = false;
        boolean result = listUser.get(0).isActive();
        assertEquals(expResult, result);
    }
}
