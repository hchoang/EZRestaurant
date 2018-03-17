/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.model.facade;

import java.io.File;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import restaurant.model.Category;
import restaurant.model.Item;
import restaurant.model.Location;
import restaurant.model.Menu;
import restaurant.model.Order;
import restaurant.model.Table;
import restaurant.model.User;
import restaurant.model.exception.DuplicateException;
import restaurant.model.exception.GeneralException;
import restaurant.model.exception.InvalidInputException;
import restaurant.model.exception.PermissionException;

/**
 *
 * @author $Le Hoang Hai
 */
public class RestaurantEngineTest {

    private static RestaurantEngine instance;

    public RestaurantEngineTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        instance = new RestaurantEngine();
        instance.setTest(true);
        instance.saveMenu();
        instance.saveOrder();
        instance.saveTable();
        instance.saveUser();
        String username = "admin";
        String pass = "admin";
        instance.logIn(username, pass);
        ArrayList<User> accounts = instance.getAccounts();
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getUsername().equals("admin1")
                    || accounts.get(i).getUsername().equals("manager1")
                    || accounts.get(i).getUsername().equals("manager1")) {
                accounts.remove(i);
            }
        }

        instance.addUser("ADMIN", "admin1", "Admin1", "Admin1", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
        instance.addUser("MANAGER", "manager1", "Manager1", "Manager1", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
        instance.addUser("CASHIER", "cashier1", "Cashier1", "Cashier1", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");

        instance.logOut();
        instance.logIn("manager1", "Manager1");
        instance.addCategory("Breakfast");
        instance.addItem("Bread", 1000, "Breakfast");
        instance.addTable(new Location(1, 1), "1");
        instance.addItemOrder(instance.getTable("1").getName(), "Bread", 1, "Note");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of logIn method, of class RestaurantEngine.
     */
    @Test
    public void testLogIn1() throws Exception {
        System.out.println("Test method logIn 1: Log in the system by using default admin");
        String username = "admin";
        String pass = "admin";
        instance.logIn(username, pass);
    }

    @Test(expected = GeneralException.class)
    public void testLogIn2() throws Exception {
        System.out.println("Test method logIn 2: Log in the system by using default admin with wrong password");
        String username = "admin";
        String pass = "1234";
        instance.logIn(username, pass);
    }

    @Test
    public void testLogIn3() throws Exception {
        System.out.println("Test method logIn 3: Log in the system by using normal admin");
        instance.logIn("admin1", "Admin1");
    }

    @Test
    public void testLogIn4() throws Exception {
        System.out.println("Test method logIn 4: Log in the system by using manager");
        instance.logIn("manager1", "Manager1");
    }

    @Test
    public void testLogIn5() throws Exception {
        System.out.println("Test method logIn 5: Log in the system by using cashier");
        instance.logIn("cashier1", "Cashier1");
    }

    @Test(expected = GeneralException.class)
    public void testLogIn6() throws Exception {
        System.out.println("Test method logIn 6: Log in the system by using unexisted account");
        instance.logIn("abcdef", "123456");
    }

    @Test(expected = GeneralException.class)
    public void testLogIn7() throws Exception {
        System.out.println("Test method logIn 7: Log in the system by using unexisted account");
        String username = "admin";
        String pass = "admin";
        instance.logIn(username, pass);
        instance.addUser("ADMIN", "admin2", "Admin2", "Admin2", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
        instance.deactiveUser("admin2");
        instance.logOut();
        instance.logIn("admin2", "Admin2");
    }

    /**
     * Test of logOut method, of class RestaurantEngine.
     */
    @Test
    public void testLogOut() throws Exception {
        System.out.println("Test method logOut: Log out the system after login");
        String username = "admin";
        String pass = "admin";
        instance.logIn(username, pass);
        instance.logOut();
    }

    /**
     * Test of addTable method, of class RestaurantEngine.
     */
    @Test
    public void testAddTable1() throws Exception {
        System.out.println("Test method addTable 1: Add the table into the "
                + "restaurant by cashier (or manager)");
        instance.logIn("manager1", "Manager1");
        Location pos = new Location(200, 200);
        String name = "2";
        instance.addTable(pos, name);
        Table result = instance.getTable(name);
        assertTrue(result != null);
    }

    @Test(expected = PermissionException.class)
    public void testAddTable2() throws Exception {
        System.out.println("Test method addTable 2: Add the table into the "
                + "restaurant by admin (error)");
        instance.logIn("admin", "admin");
        Location pos = new Location(200, 200);
        String name = "2";
        instance.addTable(pos, name);
        Table result = instance.getTable(name);
        assertTrue(result != null);
    }

    @Test(expected = GeneralException.class)
    public void testAddTable3() throws Exception {
        System.out.println("Test method addTable 3: Add the table on another table");
        instance.logIn("manager1", "Manager1");
        Location pos = new Location(500, 500);
        String name1 = "3";
        String name2 = "4";
        instance.addTable(pos, name1);
        instance.addTable(pos, name2);
    }

    @Test(expected = GeneralException.class)
    public void testAddTable4() throws Exception {
        System.out.println("Test method addTable 4: Add the table near another table");
        instance.logIn("manager1", "Manager1");
        Location pos = new Location(700, 700);
        Location pos1 = new Location(705, 705);
        String name1 = "5";
        String name2 = "6";
        instance.addTable(pos, name1);
        instance.addTable(pos1, name2);
    }

    @Test(expected = GeneralException.class)
    public void testAddTable5() throws Exception {
        System.out.println("Test method addTable 5: Add the table with existed name");
        instance.logIn("manager1", "Manager1");
        Location pos = new Location(900, 900);
        String name = "1";
        instance.addTable(pos, name);
    }

    /**
     * Test of generateTable method, of class RestaurantEngine.
     */
    @Test
    public void testGenerateTable() throws Exception {
        System.out.println("Test method generateTable: Generate table name automatically");
        instance.logIn("manager1", "Manager1");
        String num = instance.generateTable();
        assertTrue(instance.getTable(num) == null);
    }

    /**
     * Test of getTable method, of class RestaurantEngine.
     */
    @Test
    public void testGetTableByName1() throws Exception {
        System.out.println("Test method getTable 1: Get table by name with cashier (or manager)");
        instance.logIn("manager1", "Manager1");
        String name = "1";
        Table result = instance.getTable(name);
        assertTrue(result != null);
    }

    @Test(expected = PermissionException.class)
    public void testGetTableByName2() throws Exception {
        System.out.println("Test method getTable 2: Get table by name with admin (error)");
        instance.logIn("admin", "admin");
        String name = "1";
        Table result = instance.getTable(name);
    }

    @Test
    public void testGetTableByName3() throws Exception {
        System.out.println("Test method getTable 3: Get table by unexisted name");
        instance.logIn("manager1", "Manager1");
        String name = "Table";
        Table result = instance.getTable(name);
        assertTrue(result == null);
    }

    /**
     * Test of getTable method, of class RestaurantEngine.
     */
    @Test
    public void testGetTableByLocation1() throws Exception {
        System.out.println("Test method getTable 1: Get the table by the location with the cashier (or manager)");
        instance.logIn("manager1", "Manager1");
        Location posStart = null;
        Table expResult = null;
        Table result = instance.getTable(posStart);
        assertEquals(expResult, result);
    }

    @Test(expected = PermissionException.class)
    public void testGetTableByLocation2() throws Exception {
        System.out.println("Test method getTable 2: Get the table by the location with the admin (error)");
        instance.logIn("admin", "admin");
        Location posStart = null;
        Table expResult = null;
        Table result = instance.getTable(posStart);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetTableByLocation3() throws Exception {
        System.out.println("Test method getTable 3: Get the unexisted table by the location");
        instance.logIn("manager1", "Manager1");
        Location posStart = null;
        Table expResult = null;
        Table result = instance.getTable(posStart);
        assertEquals(expResult, result);
    }

    /**
     * Test of moveAndResizeTable method, of class RestaurantEngine.
     */
    @Test
    public void testMoveAndResizeTable1() throws Exception {
        System.out.println("Test method moveAndResizeTable 1: Move and resize the table by the cashier (or manager)");
        instance.logIn("manager1", "Manager1");
        String tableName = "1";
        Location posStart = new Location(1100, 1100);
        int width = 10;
        int height = 10;
        instance.moveAndResizeTable(tableName, posStart, width, height);
        Table result = instance.getTable(posStart);
        assertTrue(result != null && result.getWidth() == width && result.getHeight() == height);
    }

    @Test(expected = PermissionException.class)
    public void testMoveAndResizeTable2() throws Exception {
        System.out.println("Test method moveAndResizeTable 2: Move and resize the table by the admin");
        instance.logIn("admin", "admin");
        String tableName = "1";
        Location posStart = new Location(1100, 1100);
        int width = 10;
        int height = 10;
        instance.moveAndResizeTable(tableName, posStart, width, height);
    }

    @Test(expected = GeneralException.class)
    public void testMoveAndResizeTable3() throws Exception {
        System.out.println("Test method moveAndResizeTable 3: Move and resize the table on another table");
        instance.logIn("manager1", "Manager1");
        Location posStart = new Location(1300, 1300);
        instance.addTable(posStart, "7");
        String tableName = "1";
        int width = 10;
        int height = 10;
        instance.moveAndResizeTable(tableName, posStart, width, height);
    }

    @Test(expected = GeneralException.class)
    public void testMoveAndResizeTable4() throws Exception {
        System.out.println("Test method moveAndResizeTable 4: Move and resize to an invalid location");
        instance.logIn("manager1", "Manager1");
        Location posStart = new Location(1300, 1300);
        instance.addTable(posStart, "7");
        String tableName = "1";
        int width = -10;
        int height = -10;
        instance.moveAndResizeTable(tableName, posStart, width, height);
    }

    /**
     * Test of modifyTable method, of class RestaurantEngine.
     */
    @Test
    public void testModifyTable1() throws Exception {
        System.out.println("Test method modifyTable 1: Modify name of the table with cashier (or manager)");
        instance.logIn("manager1", "Manager1");
        instance.addTable(new Location(200, 500), "ABC");
        String oldName = "ABC";
        String newName = "Table 1";
        instance.modifyTable(oldName, newName);
        Table table = instance.getTable("Table 1");
        assertTrue(table != null);
    }

    @Test(expected = PermissionException.class)
    public void testModifyTable2() throws Exception {
        System.out.println("Test method modifyTable 2: Modify name of the table with admin");
        instance.logIn("admin", "admin");
        instance.addTable(new Location(200, 500), "ABC");
        String oldName = "ABC";
        String newName = "Table 1";
        instance.modifyTable(oldName, newName);
    }

    @Test(expected = GeneralException.class)
    public void testModifyTable3() throws Exception {
        System.out.println("Test method modifyTable 3: Modify name of the table with existed new name");
        instance.logIn("manager1", "Manager1");
        instance.addTable(new Location(200, 500), "ABCDEF");
        String oldName = "ABCDEF";
        String newName = "1";
        instance.modifyTable(oldName, newName);
        Table table = instance.getTable("Table 1");
        assertTrue(table != null);
    }

    @Test(expected = GeneralException.class)
    public void testModifyTable4() throws Exception {
        System.out.println("Test method modifyTable 4: Modify name of the table with existed new name");
        instance.logIn("manager1", "Manager1");
        instance.addTable(new Location(200, 500), "ABCDEF");
        String oldName = "ABCDEF";
        String newName = "1";
        instance.modifyTable(oldName, newName);
        Table table = instance.getTable("Table 1");
        assertTrue(table != null);
    }

    /**
     * Test of removeTable method, of class RestaurantEngine.
     */
    @Test
    public void testRemoveTable1() throws Exception {
        System.out.println("Test method removeTable 1: Remove table by cashier (or manager)");
        instance.logIn("manager1", "Manager1");
        instance.addTable(new Location(1500, 1500), "ABCDEF");
        String tableName = "ABCDEF";
        instance.removeTable(tableName);
    }

    @Test(expected = PermissionException.class)
    public void testRemoveTable2() throws Exception {
        System.out.println("Test method removeTable 2: Remove table by admin");
        instance.logIn("admin", "admin");
        instance.addTable(new Location(200, 500), "ABCDEF");
        String tableName = "ABCDEF";
        instance.removeTable(tableName);
    }

    @Test(expected = GeneralException.class)
    public void testRemoveTable3() throws Exception {
        System.out.println("Test method removeTable 3: Remove unexisted table");
        instance.logIn("manager1", "Manager1");
        String tableName = "ABCDEF";
        instance.removeTable(tableName);
    }

    /**
     * Test of addItemOrder method, of class RestaurantEngine.
     */
    @Test
    public void testAddItemOrder1() throws Exception {
        System.out.println("Test method addItemOrders 1: Add item into order by cashier (or manager)");
        instance.logIn("manager1", "Manager1");
        instance.addTable(new Location(3500,3500), "ABCDEF Table");
        Table table = instance.getTable("ABCDEF Table");
        Item item = new Item("Chicken", 10000);
        instance.addCategory("nothing1");
        instance.addItem("Chicken", 10000, "nothing1");
        int quantity = 1;
        String note = "abc";
        instance.addItemOrder(table.getName(), item.getName(), quantity, note);
        String result = table.getCurrentOrder().getOrderItems().get(0).getItem().getName();
        assertEquals(result, "Chicken");
    }

    @Test(expected = PermissionException.class)
    public void testAddItemOrder2() throws Exception {
        System.out.println("Test method addItemOrders 2: Add item into order by admin");
        instance.logIn("admin", "admin");
        Table table = instance.getTableList().get(0);
        Item item = new Item("Chicken1", 10000);
        instance.addItem("Chicken1", 10000, instance.getCategoryList().get(0).getName());
        int quantity = 1;
        String note = "abc";
        instance.addItemOrder(table.getName(), item.getName(), quantity, note);

    }

    @Test(expected = GeneralException.class)
    public void testAddItemOrder3() throws Exception {
        System.out.println("Test method addItemOrders 3: Add item into order with negative quantity");
        instance.logIn("manager1", "Manager1");
        Table table = instance.getTableList().get(0);
        Item item = new Item("Chicken2", 10000);
        instance.addItem("Chicken2", 10000, instance.getCategoryList().get(0).getName());
        int quantity = -1;
        String note = "abc";
        instance.addItemOrder(table.getName(), item.getName(), quantity, note);
    }

    @Test(expected = GeneralException.class)
    public void testAddItemOrder4() throws Exception {
        System.out.println("Test method addItemOrders 4: Add item into order with 0 quantity");
        instance.logIn("manager1", "Manager1");
        Table table = instance.getTableList().get(0);
        Item item = new Item("Chicken3", 10000);
        instance.addItem("Chicken3", 10000, instance.getCategoryList().get(0).getName());
        int quantity = 0;
        String note = "abc";
        instance.addItemOrder(table.getName(), item.getName(), quantity, note);
    }

    /**
     * Test of removeItemOrder method, of class RestaurantEngine.
     */
    @Test
    public void testRemoveItemOrder1() throws Exception {
        System.out.println("Test method removeItemOrder 1: Remove the item from the order by cashier (or manager)");
        instance.logIn("manager1", "Manager1");
        Table table = instance.getTableList().get(0);
        int size = table.getCurrentOrder().getOrderItems().size();
        Item item = instance.getTable(table.getName()).getCurrentOrder().getOrderItems().get(0).getItem();
        instance.removeItemOrder(table.getName(), item);
        int result = table.getCurrentOrder().getOrderItems().size();
        assertTrue(result == size - 1);
    }

    @Test(expected = PermissionException.class)
    public void testRemoveItemOrder2() throws Exception {
        System.out.println("Test method removeItemOrder 2: Remove the item from the order by admin (error)");
        instance.logIn("admin", "admin");
        Table table = instance.getTableList().get(0);
        int size = table.getCurrentOrder().getOrderItems().size();
        Item item = instance.getTable(table.getName()).getCurrentOrder().getOrderItems().get(0).getItem();
        instance.removeItemOrder(table.getName(), item);
    }

    @Test(expected = GeneralException.class)
    public void testRemoveItemOrder3() throws Exception {
        System.out.println("Test method removeItemOrder 3: Remove the unexisted item from the order");
        instance.logIn("manager1", "Manager1");
        Table table = instance.getTableList().get(0);
        int size = table.getCurrentOrder().getOrderItems().size();
        Item item = new Item("abcdefasdasd", 1000);
        instance.removeItemOrder(table.getName(), item);
    }

    /**
     * Test of calculateOrder method, of class RestaurantEngine.
     */
    @Test
    public void testCalculateOrder1() throws Exception {
        System.out.println("Test method calculateOrder 1: Calculate the sub-total price of the order by cashier (or manager)");
        instance.logIn("manager1", "Manager1");
        Table table = new Table(new Location(100, 100), "ABCUIO");
        Order order = new Order("1",0);
        order.addOrderItem(new Item("Beef", 1000), 10, "");
        table.setCurrentOrder(order);
        long expResult = 1000 * 10;
        long result = instance.calculateOrder(table.getName());
        assertEquals(expResult, result);
    }

    @Test(expected = PermissionException.class)
    public void testCalculateOrder2() throws Exception {
        System.out.println("Test method calculateOrder 2: Calculate the sub-total price of the order by a");
        instance.logIn("admin", "admin");
        Table table = new Table(new Location(100, 100), "ABCUIO");
        Order order = new Order("1",0);
        order.addOrderItem(new Item("Beef", 1000), 10, "");
        table.setCurrentOrder(order);
        long expResult = 1000 * 10;
        long result = instance.calculateOrder(table.getName());
    }

    /**
     * Test of completeOrder method, of class RestaurantEngine.
     */
    @Test
    public void testCompleteOrder1() throws Exception {
        System.out.println("Test method completeOrder 1: Check out the order by cashier (or manager)");
        instance.logIn("manager1", "Manager1");
        instance.addTable(new Location(3000,3000), "Nothing");
        Table table = instance.getTable("Nothing");
        Order order = new Order("1",0);
        order.addOrderItem(new Item("Beef", 1000), 10, "");
        table.setCurrentOrder(order);
        instance.completeOrder(table.getName());
        assertTrue(table.getCurrentOrder() == null);
    }

    @Test(expected = PermissionException.class)
    public void testCompleteOrder2() throws Exception {
        System.out.println("Test method completeOrder 2: Check out the order by admin");
        instance.logIn("admin", "admin");
        Table table = instance.getTable("3");
        Order order = new Order("1",0);
        order.addOrderItem(new Item("Beef", 1000), 10, "");
        table.setCurrentOrder(order);
        instance.completeOrder(table.getName());
    }

    /**
     * Test of addCategory method, of class RestaurantEngine.
     */
    @Test
    public void testAddCategory1() throws Exception {
        System.out.println("Test method addCategory 1: Add category into menu by the manager");
        instance.logIn("manager1", "Manager1");
        String name = "Lunch";
        instance.addCategory(name);
        assertTrue(instance.getCategory(name) != null);
    }

    @Test(expected = PermissionException.class)
    public void testAddCategory2() throws Exception {
        System.out.println("Test method addCategory 2: Add category into menu by the admin/cashier");
        instance.logIn("admin", "admin");
        String name = "Lunch1";
        instance.addCategory(name);
    }

    @Test(expected = DuplicateException.class)
    public void testAddCategory3() throws Exception {
        System.out.println("Test method addCategory 3: Add duplicate category into menu");
        instance.logIn("manager1", "Manager1");
        String name = "Lunch3";
        instance.addCategory(name);
        instance.addCategory(name);
    }

    @Test(expected = InvalidInputException.class)
    public void testAddCategory4() throws Exception {
        System.out.println("Test method addCategory 4: Add category with empty name into menu");
        instance.logIn("manager1", "Manager1");
        String name = "";
        instance.addCategory(name);
    }

    @Test(expected = InvalidInputException.class)
    public void testAddCategory5() throws Exception {
        System.out.println("Test method addCategory 5: Add category with only-space name into menu");
        instance.logIn("manager1", "Manager1");
        String name = "       ";
        instance.addCategory(name);
    }

    /**
     * Test of editCategory method, of class RestaurantEngine.
     */
    @Test
    public void testEditCategory1() throws Exception {
        System.out.println("Test method editCategory 1: Edit name of the category by manager");
        instance.logIn("manager1", "Manager1");
        String name = "Dinner";
        instance.addCategory(name);
        String newName = "Dinner+";
        instance.editCategory(name, newName);
        assertTrue(instance.getCategory(newName) != null);
    }

    @Test(expected = PermissionException.class)
    public void testEditCategory2() throws Exception {
        System.out.println("Test method editCategory 2: Edit name of the category by admin/cashier");
        instance.logIn("admin", "admin");
        String name = "Dinner";
        instance.addCategory(name);
        String newName = "Dinner+";
        instance.editCategory(name, newName);
    }

    @Test(expected = GeneralException.class)
    public void testEditCategory3() throws Exception {
        System.out.println("Test method editCategory 3: Edit name of the unexisted category");
        instance.logIn("manager1", "Manager1");
        String name = "zcxbfdgzdf";
        String newName = "Dinner+";
        instance.editCategory(name, newName);
    }

    @Test(expected = GeneralException.class)
    public void testEditCategory4() throws Exception {
        System.out.println("Test method editCategory 4: Edit name of the category with empty new name");
        instance.logIn("manager1", "Manager1");
        String name = "Dinner++";
        instance.addCategory(name);
        String newName = "";
        instance.editCategory(name, newName);
    }

    @Test(expected = GeneralException.class)
    public void testEditCategory5() throws Exception {
        System.out.println("Test method editCategory 5: Edit name of the category with only-space new name");
        instance.logIn("manager1", "Manager1");
        String name = "Dinner+++";
        instance.addCategory(name);
        String newName = "           ";
        instance.editCategory(name, newName);
    }

    @Test(expected = GeneralException.class)
    public void testEditCategory6() throws Exception {
        System.out.println("Test method editCategory 6: Edit name of the category with existed new name");
        instance.logIn("manager1", "Manager1");
        String name = "Dinner++++";
        String newName = "Dinner-";
        instance.addCategory(name);
        instance.addCategory(newName);
        instance.editCategory(name, newName);
    }

    /**
     * Test of removeCategory method, of class RestaurantEngine.
     */
    @Test
    public void testRemoveCategory1() throws Exception {
        System.out.println("Test method removeCategory 1: Remove category from the menu by manager");
        instance.logIn("manager1", "Manager1");
        String categoryName = "Western";
        instance.addCategory(categoryName);
        instance.removeCategory(categoryName);
        assertTrue(instance.getCategory(categoryName) == null);
    }

    @Test(expected = PermissionException.class)
    public void testRemoveCategory2() throws Exception {
        System.out.println("Test method removeCategory 2: Remove category from the menu by admin/cashier");
        instance.logIn("admin", "admin");
        String categoryName = "Western";
        instance.removeCategory(instance.getCategoryList().get(0).getName());
    }

    @Test(expected = GeneralException.class)
    public void testRemoveCategory3() throws Exception {
        System.out.println("Test method removeCategory 3: Remove unexisted category from the menu");
        instance.logIn("manager1", "Manager1");
        String categoryName = "Western+";
        instance.removeCategory(categoryName);
    }

    @Test(expected = GeneralException.class)
    public void testRemoveCategory4() throws Exception {
        System.out.println("Test method removeCategory 4: Remove category having an ordered item from the menu");
        instance.logIn("manager1", "Manager1");
        String categoryName = "Western+";
        instance.addCategory(categoryName);
        instance.addItem("ABCHJK", 1000, categoryName);
        instance.addItemOrder(instance.getTableList().get(0).getName(), "ABCHJK", 1, categoryName);
        instance.removeCategory(categoryName);
    }

    /**
     * Test of searchCategory method, of class RestaurantEngine.
     */
    @Test
    public void testSearchCategory1() throws Exception {
        System.out.println("Test method searchCategory 1: Search category contains item by manager");
        instance.logIn("manager1", "Manager1");
        String itemName = "Coffee";
        String categoryName = "Drink";
        instance.addCategory(categoryName);
        instance.addItem(itemName, 1000, categoryName);
        Category expResult = instance.getCategory(categoryName);
        Category result = instance.searchCategory(itemName);
        assertEquals(expResult, result);
    }

    @Test(expected = PermissionException.class)
    public void testSearchCategory2() throws Exception {
        System.out.println("Test method searchCategory 2: Search category contains item by admin/cashier");
        instance.logIn("admin", "admin");
        String itemName = "Beer";
        String categoryName = "Drink1";
        instance.addCategory(categoryName);
        instance.addItem(itemName, 1000, categoryName);
        Category expResult = instance.getCategory(categoryName);
        Category result = instance.searchCategory(itemName);
    }

    @Test
    public void testSearchCategory3() throws Exception {
        System.out.println("Test method searchCategory 3: Search category contains unexisted item");
        instance.logIn("manager1", "Manager1");
        String itemName = "Cookies";
        Category expResult = null;
        Category result = instance.searchCategory(itemName);
        assertEquals(expResult, result);
    }

    /**
     * Test of getCategory method, of class RestaurantEngine.
     */
    @Test
    public void testGetCategory1() throws Exception {
        System.out.println("Test method getCategory 1: Get category by name by manager");
        instance.logIn("manager1", "Manager1");
        String categoryName = "Noodle";
        instance.addCategory(categoryName);
        Category result = instance.getCategory(categoryName);
        assertTrue(result != null);
    }

    @Test(expected = PermissionException.class)
    public void testGetCategory2() throws Exception {
        System.out.println("Test method getCategory 2: Get category by name by admin/cashier");
        instance.logIn("admin", "admin");
        String categoryName = "Noodle";
        Category result = instance.getCategory(categoryName);
    }

    @Test
    public void testGetCategory3() throws Exception {
        System.out.println("Test method getCategory 3: Get unexisted category by name by manager");
        instance.logIn("manager1", "Manager1");
        String categoryName = "xcvdhjbd";
        Category result = instance.getCategory(categoryName);
        assertTrue(result == null);
    }

    /**
     * Test of addItem method, of class RestaurantEngine.
     */
    @Test
    public void testAddItem1() throws Exception {
        System.out.println("Test method addItem 1: Add item into the category by manager");
        instance.logIn("manager1", "Manager1");
        instance.addCategory("Rice");
        String name = "High quality";
        long price = 2000;
        String categoryName = "Rice";
        instance.addItem(name, price, categoryName);
        assertTrue(instance.searchCategory(name) != null);
    }

    @Test(expected = PermissionException.class)
    public void testAddItem2() throws Exception {
        System.out.println("Test method addItem 2: Add item into the category by admin/cashier");
        instance.logIn("admin", "admin");
        String name = "High quality1";
        long price = 2000;
        String categoryName = "Rice";
        instance.addItem(name, price, categoryName);
    }

    @Test(expected = GeneralException.class)
    public void testAddItem3() throws Exception {
        System.out.println("Test method addItem 3: Add item into the unexisted category");
        instance.logIn("manager1", "Manager1");
        String name = "High quality11";
        long price = 2000;
        String categoryName = "Rice11111";
        instance.addItem(name, price, categoryName);
    }

    @Test(expected = DuplicateException.class)
    public void testAddItem4() throws Exception {
        System.out.println("Test method addItem 4: Add existed item in category into that category");
        instance.logIn("manager1", "Manager1");
        String categoryName = "Rice+++";
        String name = "High quality++";
        long price = 2000;
        instance.addCategory(categoryName);
        instance.addItem(name, price, categoryName);
        instance.addItem(name, price, categoryName);
    }

    @Test(expected = DuplicateException.class)
    public void testAddItem5() throws Exception {
        System.out.println("Test method addItem 5: Add existed item in category into another category");
        instance.logIn("manager1", "Manager1");
        String categoryName = "Rice++++";
        String categoryName1 = "Chicken+++";
        String name = "High quality+++";
        long price = 2000;
        instance.addCategory(categoryName);
        instance.addCategory(categoryName1);
        instance.addItem(name, price, categoryName);
        instance.addItem(name, price, categoryName1);
    }

    @Test(expected = InvalidInputException.class)
    public void testAddItem6() throws Exception {
        System.out.println("Test method addItem 6: Add item with empty name into category");
        instance.logIn("manager1", "Manager1");
        String categoryName = "Rice-";
        String name = "";
        long price = 2000;
        instance.addCategory(categoryName);
        instance.addItem(name, price, categoryName);
    }

    @Test(expected = InvalidInputException.class)
    public void testAddItem7() throws Exception {
        System.out.println("Test method addItem 7: Add item with only-space name into ategory");
        instance.logIn("manager1", "Manager1");
        String categoryName = "Rice--";
        String name = "           ";
        long price = 2000;
        instance.addCategory(categoryName);
        instance.addItem(name, price, categoryName);
    }

    @Test(expected = GeneralException.class)
    public void testAddItem8() throws Exception {
        System.out.println("Test method addItem 8: Add item with negative price into ategory");
        instance.logIn("manager1", "Manager1");
        String categoryName = "Rice---";
        String name = "Drink++";
        long price = -2000;
        instance.addCategory(categoryName);
        instance.addItem(name, price, categoryName);
    }

    /**
     * Test of editItem method, of class RestaurantEngine.
     */
    @Test
    public void testEditItem1() throws Exception {
        System.out.println("Test method editItem 1: Edit item information by manager");
        instance.logIn("manager1", "Manager1");
        String categoryName = "Beer+";
        instance.addCategory(categoryName);
        String name = "Tiger";
        long price = 2000;
        instance.addItem(name, price, categoryName);
        String newName = "Tiger+";
        long newPrice = 3000;
        instance.editItem(name, newName, newPrice);
        assertTrue(instance.searchCategory(newName) != null);
    }

    @Test(expected = PermissionException.class)
    public void testEditItem2() throws Exception {
        System.out.println("Test method editItem 2: Edit item information by admin/cashier");
        instance.logIn("admin", "admin");
        String categoryName = "Beer++";
        String name = "Tiger-";
        long price = 2000;
        String newName = "Tiger--";
        instance.editItem(name, newName, price);
    }

    @Test(expected = GeneralException.class)
    public void testEditItem3() throws Exception {
        System.out.println("Test method editItem 3: Edit unexisted item information");
        instance.logIn("manager1", "Manager1");
        String categoryName = "Beer-";
        instance.addCategory(categoryName);
        String name = "Tiger*";
        long price = 2000;
        String newName = "Tiger**";
        long newPrice = 3000;
        instance.editItem(name, newName, newPrice);
    }

    @Test(expected = DuplicateException.class)
    public void testEditItem4() throws Exception {
        System.out.println("Test method editItem 4: Edit item information with existed new name");
        instance.logIn("manager1", "Manager1");
        String categoryName = "Beer--";
        instance.addCategory(categoryName);
        String name = "Tiger/";
        long price = 2000;
        String newName = "Tiger//";
        instance.addItem(name, price, categoryName);
        instance.addItem(newName, price, categoryName);
        long newPrice = 3000;
        instance.editItem(name, newName, newPrice);
    }

    @Test(expected = GeneralException.class)
    public void testEditItem5() throws Exception {
        System.out.println("Test method editItem 5: Edit item information with existed new name");
        instance.logIn("manager1", "Manager1");
        String categoryName = "Beer--";
        instance.addCategory(categoryName);
        String name = "Heneiken";
        long price = 2000;
        String newName = "Heneiken+";
        instance.addItem(name, price, categoryName);
        instance.addItem(newName, price, categoryName);
        instance.addTable(new Location(2000, 2000), name);
        instance.addItemOrder(name, "Heneiken", 1, "Nothing");
        long newPrice = 3000;
        instance.editItem(name, newName, newPrice);
    }

    /**
     * Test of removeItem method, of class RestaurantEngine.
     */
    @Test
    public void testRemoveItem1() throws Exception {
        System.out.println("Test method removeItem 1: Remove item from the menu by manager");
        instance.logIn("manager1", "Manager1");
        String categoryName = "Beer!!!";
        instance.addCategory(categoryName);
        String name = "Heneiken1";
        long price = 2000;
        instance.addItem(name, price, categoryName);
        instance.removeItem(name);
    }

    @Test(expected = PermissionException.class)
    public void testRemoveItem2() throws Exception {
        System.out.println("Test method removeItem 2: Remove item from the menu by admin/cashier");
        instance.logIn("admin", "admin");
        String categoryName = "Beer***";
        instance.addCategory(categoryName);
        String name = "Heneiken12";
        long price = 2000;
        instance.removeItem(name);
    }

    @Test(expected = GeneralException.class)
    public void testRemoveItem3() throws Exception {
        System.out.println("Test method removeItem 3: Remove unexisted item from the menu");
        instance.logIn("manager1", "Manager1");
        String categoryName = "Beer**";
        instance.addCategory(categoryName);
        String name = "Heneiken19";
        long price = 2000;
        instance.removeItem(name);
    }

    @Test(expected = GeneralException.class)
    public void testRemoveItem4() throws Exception {
        System.out.println("Test method removeItem 4: Remove unexisted item from the menu");
        instance.logIn("manager1", "Manager1");
        String categoryName = "Beer--";
        instance.addCategory(categoryName);
        String name = "Heneiken36";
        long price = 2000;
        instance.addItem(name, price, categoryName);
        instance.addTable(new Location(2300, 2300), name);
        instance.addItemOrder(name, "Heneiken36", 1, "Nothing");
        instance.removeItem(name);
    }

    /**
     * Test of getItem method, of class RestaurantEngine.
     */
    @Test
    public void testGetItem() throws Exception {
        System.out.println("Test method getItem: Get item by name");
        instance.logIn("manager1", "Manager1");
        String categoryName = "Dessert";
        instance.addCategory(categoryName);
        String name = "Cake";
        long price = 2000;
        instance.addItem(name, price, categoryName);
        Item result = instance.getItem(name);
        assertTrue(result != null);
    }

    /**
     * Test of checkType method, of class RestaurantEngine.
     */
    @Test
    public void testCheckType() {
        System.out.println("Test method checkType: Find the type of the account");
        String username = "admin";
        String expResult = "ADMIN";
        String result = instance.checkType(username);
        assertEquals(expResult, result);
    }

    /**
     * Test of addUser method, of class RestaurantEngine.
     */
    @Test
    public void testAddUser1() throws Exception {
        System.out.println("Test method addUser 1: Add new admin account into the system");
        instance.logIn("admin", "admin");
        String type = instance.ADMIN;
        String username = "admin3";
        String password = "Admin3";
        String confirm = "Admin3";
        String name = "Le Hoang Hai";
        int age = 20;
        boolean gender = true;
        String email = "abcdef@yahoo.com";

        instance.addUser(type, username, password, confirm, name, age, gender, email);
        instance.logIn(username, password);
    }

    @Test
    public void testAddUser2() throws Exception {
        System.out.println("Test method addUser 2: Add new manager account into the system");
        instance.logIn("admin", "admin");
        String type = instance.MANAGER;
        String username = "manager2";
        String password = "Manager2";
        String confirm = "Manager2";
        String name = "Le Hoang Hai";
        int age = 20;
        boolean gender = true;
        String email = "abcdef@yahoo.com";
        instance.addUser(type, username, password, confirm, name, age, gender, email);
        instance.logIn(username, password);
    }

    @Test
    public void testAddUser3() throws Exception {
        System.out.println("Test method addUser 3: Add new cashier account into the system");
        instance.logIn("admin", "admin");
        String type = instance.CASHIER;
        String username = "cashier2";
        String password = "Cashier2";
        String confirm = "Cashier2";
        String name = "Le Hoang Hai";
        int age = 20;
        boolean gender = true;
        String email = "abcdef@yahoo.com";
        instance.addUser(type, username, password, confirm, name, age, gender, email);
        instance.logIn(username, password);
    }

    @Test(expected = PermissionException.class)
    public void testAddUser4() throws Exception {
        System.out.println("Test method addUser 4: Add new account by manager/cashier into the system");
        instance.logIn("manager1", "Manager1");
        String type = instance.CASHIER;
        String username = "cashier2";
        String password = "Cashier2";
        String confirm = "Cashier2";
        String name = "Le Hoang Hai";
        int age = 20;
        boolean gender = true;
        String email = "abcdef@yahoo.com";
        instance.addUser(type, username, password, confirm, name, age, gender, email);
        instance.logIn(username, password);
    }

    @Test(expected = GeneralException.class)
    public void testAddUser5() throws Exception {
        System.out.println("Test method addUser 5: Add new account with username having space into the system");
        instance.logIn("admin", "admin");
        String type = instance.CASHIER;
        String username = "cashi er2";
        String password = "Cashier2";
        String confirm = "Cashier2";
        String name = "Le Hoang Hai";
        int age = 20;
        boolean gender = true;
        String email = "abcdef@yahoo.com";
        instance.addUser(type, username, password, confirm, name, age, gender, email);
        instance.logIn(username, password);
    }

    @Test(expected = GeneralException.class)
    public void testAddUser6() throws Exception {
        System.out.println("Test method addUser 6: Add new account with username having upper character into the system");
        instance.logIn("admin", "admin");
        String type = instance.CASHIER;
        String username = "Cashier2";
        String password = "Cashier2";
        String confirm = "Cashier2";
        String name = "Le Hoang Hai";
        int age = 20;
        boolean gender = true;
        String email = "abcdef@yahoo.com";
        instance.addUser(type, username, password, confirm, name, age, gender, email);
        instance.logIn(username, password);
    }

    @Test(expected = GeneralException.class)
    public void testAddUser7() throws Exception {
        System.out.println("Test method addUser 7: Add new account with username having special character into the system");
        instance.logIn("admin", "admin");
        String type = instance.CASHIER;
        String username = "cashier@2";
        String password = "Cashier2";
        String confirm = "Cashier2";
        String name = "Le Hoang Hai";
        int age = 20;
        boolean gender = true;
        String email = "abcdef@yahoo.com";
        instance.addUser(type, username, password, confirm, name, age, gender, email);
        instance.logIn(username, password);
    }

    @Test(expected = GeneralException.class)
    public void testAddUser8() throws Exception {
        System.out.println("Test method addUser 8: Add new account with username having special character into the system");
        instance.logIn("admin", "admin");
        String type = instance.CASHIER;
        String username = "cashier@2";
        String password = "Cashier2";
        String confirm = "Cashier2";
        String name = "Le Hoang Hai";
        int age = 20;
        boolean gender = true;
        String email = "abcdef@yahoo.com";
        instance.addUser(type, username, password, confirm, name, age, gender, email);
        instance.logIn(username, password);
    }

    @Test(expected = GeneralException.class)
    public void testAddUser9() throws Exception {
        System.out.println("Test method addUser 9: Add new account with empty username into the system");
        instance.logIn("admin", "admin");
        String type = instance.CASHIER;
        String username = "";
        String password = "Cashier2";
        String confirm = "Cashier2";
        String name = "Le Hoang Hai";
        int age = 20;
        boolean gender = true;
        String email = "abcdef@yahoo.com";
        instance.addUser(type, username, password, confirm, name, age, gender, email);
        instance.logIn(username, password);
    }

    @Test(expected = GeneralException.class)
    public void testAddUser10() throws Exception {
        System.out.println("Test method addUser 10: Add new account with password without upper character into the system");
        instance.logIn("admin", "admin");
        String type = instance.CASHIER;
        String username = "cashier2";
        String password = "cashier2";
        String confirm = "cashier2";
        String name = "Le Hoang Hai";
        int age = 20;
        boolean gender = true;
        String email = "abcdef@yahoo.com";
        instance.addUser(type, username, password, confirm, name, age, gender, email);
        instance.logIn(username, password);
    }

    @Test(expected = GeneralException.class)
    public void testAddUser11() throws Exception {
        System.out.println("Test method addUser 11: Add new account with password without number into the system");
        instance.logIn("admin", "admin");
        String type = instance.CASHIER;
        String username = "cashier2";
        String password = "Cashier";
        String confirm = "Cashier";
        String name = "Le Hoang Hai";
        int age = 20;
        boolean gender = true;
        String email = "abcdef@yahoo.com";
        instance.addUser(type, username, password, confirm, name, age, gender, email);
        instance.logIn(username, password);
    }

    @Test(expected = GeneralException.class)
    public void testAddUser12() throws Exception {
        System.out.println("Test method addUser 12: Add new account with password least than 6 words into the system");
        instance.logIn("admin", "admin");
        String type = instance.CASHIER;
        String username = "cashier2";
        String password = "a";
        String confirm = "a";
        String name = "Le Hoang Hai";
        int age = 20;
        boolean gender = true;
        String email = "abcdef@yahoo.com";
        instance.addUser(type, username, password, confirm, name, age, gender, email);
        instance.logIn(username, password);
    }

    @Test(expected = GeneralException.class)
    public void testAddUser13() throws Exception {
        System.out.println("Test method addUser 13: Add new account with password least than 6 words into the system");
        instance.logIn("admin", "admin");
        String type = instance.CASHIER;
        String username = "cashier2";
        String password = "a";
        String confirm = "a";
        String name = "Le Hoang Hai";
        int age = 20;
        boolean gender = true;
        String email = "abcdef@yahoo.com";
        instance.addUser(type, username, password, confirm, name, age, gender, email);
        instance.logIn(username, password);
    }

    @Test(expected = GeneralException.class)
    public void testAddUser14() throws Exception {
        System.out.println("Test method addUser 14: Add new account with full name having only 1 word into the system");
        instance.logIn("admin", "admin");
        String type = instance.CASHIER;
        String username = "cashier2";
        String password = "Cashier2";
        String confirm = "Cashier2";
        String name = "Le";
        int age = 20;
        boolean gender = true;
        String email = "abcdef@yahoo.com";
        instance.addUser(type, username, password, confirm, name, age, gender, email);
        instance.logIn(username, password);
    }

    @Test(expected = GeneralException.class)
    public void testAddUser15() throws Exception {
        System.out.println("Test method addUser 15: Add new account with full name having the first character of the word is lowercase into the system");
        instance.logIn("admin", "admin");
        String type = instance.CASHIER;
        String username = "cashier2";
        String password = "Cashier2";
        String confirm = "Cashier2";
        String name = "le Hoang Hai";
        int age = 20;
        boolean gender = true;
        String email = "abcdef@yahoo.com";
        instance.addUser(type, username, password, confirm, name, age, gender, email);
        instance.logIn(username, password);
    }

    @Test(expected = GeneralException.class)
    public void testAddUser16() throws Exception {
        System.out.println("Test method addUser 16: Add new account with full name having the confirm password is different from the password is lowercase into the system");
        instance.logIn("admin", "admin");
        String type = instance.CASHIER;
        String username = "cashier2";
        String password = "Cashier2";
        String confirm = "sdfashier2";
        String name = "Le Hoang Hai";
        int age = 20;
        boolean gender = true;
        String email = "abcdef@yahoo.com";
        instance.addUser(type, username, password, confirm, name, age, gender, email);
        instance.logIn(username, password);
    }

    @Test(expected = GeneralException.class)
    public void testAddUser17() throws Exception {
        System.out.println("Test method addUser 17: Add new account with full name having the invalid email into the system");
        instance.logIn("admin", "admin");
        String type = instance.CASHIER;
        String username = "cashier2";
        String password = "Cashier2";
        String confirm = "sdfashier2";
        String name = "Le Hoang Hai";
        int age = 20;
        boolean gender = true;
        String email = "abcdef.yahoo.com";
        instance.addUser(type, username, password, confirm, name, age, gender, email);
        instance.logIn(username, password);
    }

    /**
     * Test of activeUser method, of class RestaurantEngine.
     */
    @Test
    public void testActiveUser1() throws Exception {
        System.out.println("Test method activeUser 1: Active account by admin");
        instance.logIn("admin", "admin");
        String username = "cashier1";
        instance.activeUser(username);
        instance.logIn("cashier1", "Cashier1");
    }

    @Test(expected = PermissionException.class)
    public void testActiveUser2() throws Exception {
        System.out.println("Test method activeUser 2: Active account by manager/cashier");
        instance.logIn("manager1", "Manager1");
        String username = "cashier1";
        instance.activeUser(username);
        instance.logIn("cashier1", "Cashier1");
    }

    @Test(expected = GeneralException.class)
    public void testActiveUser3() throws Exception {
        System.out.println("Test method activeUser 3: Active unexisted account");
        instance.logIn("admin", "admin");
        String username = "carfgafg";
        instance.activeUser(username);
    }

    /**
     * Test of deactiveUser method, of class RestaurantEngine.
     */
    @Test(expected = GeneralException.class)
    public void testDeactiveUser1() throws Exception {
        System.out.println("Test method deactiveUser 1: Deactive account by admin");
        instance.logIn("admin", "admin");
        instance.addUser("MANAGER", "manager10", "Manager10", "Manager10", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
        String username = "manager10";
        instance.deactiveUser(username);
        instance.logIn("manager10", "Manager10");
    }

    @Test(expected = PermissionException.class)
    public void testDeactiveUser2() throws Exception {
        System.out.println("Test method deactiveUser 2: Deactive account by manager/cashier");
        instance.logIn("manager1", "Manager1");
        instance.addUser("MANAGER", "manager11", "Manager10", "Manager10", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
        String username = "manager11";
        instance.deactiveUser(username);
        instance.logIn("manager11", "Manager10");
    }

    @Test(expected = GeneralException.class)
    public void testDeactiveUser3() throws Exception {
        System.out.println("Test method deactiveUser 3: Deactive default admin");
        instance.logIn("admin", "admin");
        String username = "admin";
        instance.deactiveUser(username);
    }

    @Test(expected = GeneralException.class)
    public void testDeactiveUser4() throws Exception {
        System.out.println("Test method deactiveUser 4: Deactive unexisted account");
        instance.logIn("admin", "admin");
        String username = "zcxvfgh";
        instance.deactiveUser(username);
    }

    /**
     * Test of editUser method, of class RestaurantEngine.
     */
    @Test
    public void testEditUser1() throws Exception {
        System.out.println("Test method editUser 1: Edit account by admin");
        instance.logIn("admin", "admin");
        instance.addUser("MANAGER", "manager11", "Manager11", "Manager11", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");       
        String oldUser = "manager11";
        String username = "manager12";
        String password = "Manager12";
        String confirm = "Manager12";
        String name = "Nguyen Thanh Luan";
        int age = 21;
        boolean gender = false;
        String email = "abcdef@yahoo.com";
        instance.editUser(oldUser, "MANAGER", username, password, confirm, name, age, gender, email);
        instance.logIn(username, password);
    }

    @Test(expected = PermissionException.class)
    public void testEditUser2() throws Exception {
        System.out.println("Test method editUser 2: Edit account by manager/cashier");
        instance.logIn("manager1", "Manager1");
        instance.addUser("MANAGER", "manager13", "Manager11", "Manager11", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");    
        String oldUser = "manager13";
        String username = "manager14";
        String password = "Manager14";
        String confirm = "Manager14";
        String name = "Nguyen Thanh Luan";
        int age = 21;
        boolean gender = false;
        String email = "abcdef@yahoo.com";
        instance.editUser(oldUser, "MANAGER",username, password, confirm, name, age, gender, email);
        instance.logIn(username, password);
    }

    @Test(expected = GeneralException.class)
    public void testEditUser3() throws Exception {
        System.out.println("Test method editUser 3: Edit unexisted account");
        instance.logIn("admin", "admin");
        String oldUser = "manager13";
        String username = "manager14";
        String password = "Manager14";
        String confirm = "Manager14";
        String name = "Nguyen Thanh Luan";
        int age = 21;
        boolean gender = false;
        String email = "abcdef@yahoo.com";
        instance.editUser(oldUser, "MANAGER",username, password, confirm, name, age, gender, email);
        instance.logIn(username, password);
    }

    @Test(expected = GeneralException.class)
    public void testEditUser4() throws Exception {
        System.out.println("Test method editUser 4: Edit username of the default admin account");
        instance.logIn("admin", "admin");
        String oldUser = "admin";
        String username = "manager14";
        String password = "Manager14";
        String confirm = "Manager14";
        String name = "Nguyen Thanh Luan";
        int age = 21;
        boolean gender = false;
        String email = "abcdef@yahoo.com";
        instance.editUser(oldUser, "MANAGER",username, password, confirm, name, age, gender, email);
        instance.logIn(username, password);
    }

    @Test(expected = GeneralException.class)
    public void testEditUser5() throws Exception {
        System.out.println("Test method editUser 5: Edit account with invalid username");
        instance.logIn("admin", "admin");
        instance.addUser("MANAGER", "manager15", "Manager11", "Manager11", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
        String oldUser = "manager15";
        String username = "manager14  ";
        String password = "Manager14";
        String confirm = "Manager14";
        String name = "Nguyen Thanh Luan";
        int age = 21;
        boolean gender = false;
        String email = "abcdef@yahoo.com";
        instance.editUser(oldUser, "MANAGER",username, password, confirm, name, age, gender, email);
        instance.logIn(username, password);
    }

    @Test(expected = GeneralException.class)
    public void testEditUser6() throws Exception {
        System.out.println("Test method editUser 6: Edit account with invalid password");
        instance.logIn("admin", "admin");
        instance.addUser("MANAGER", "manager16", "Manager11", "Manager11", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
        String oldUser = "manager16";
        String username = "manager14";
        String password = "manager14";
        String confirm = "manager14";
        String name = "Nguyen Thanh Luan";
        int age = 21;
        boolean gender = false;
        String email = "abcdef@yahoo.com";
        instance.editUser(oldUser, "MANAGER",username, password, confirm, name, age, gender, email);
        instance.logIn(username, password);
    }

    @Test(expected = GeneralException.class)
    public void testEditUser7() throws Exception {
        System.out.println("Test method editUser 7: Edit account with invalid confirm password");
        instance.logIn("admin", "admin");
        instance.addUser("MANAGER", "manager17", "Manager11", "Manager11", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
        String oldUser = "manager17";
        String username = "manager14";
        String password = "Manager14";
        String confirm = "Manager15";
        String name = "Nguyen Thanh Luan";
        int age = 21;
        boolean gender = false;
        String email = "abcdef@yahoo.com";
        instance.editUser(oldUser, "MANAGER",username, password, confirm, name, age, gender, email);
        instance.logIn(username, password);
    }

    @Test(expected = GeneralException.class)
    public void testEditUser8() throws Exception {
        System.out.println("Test method editUser 8: Edit account with invalid fullname");
        instance.logIn("admin", "admin");
        instance.addUser("MANAGER", "manager18", "Manager11", "Manager11", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
        String oldUser = "manager18";
        String username = "manager14";
        String password = "Manager14";
        String confirm = "Manager14";
        String name = "NguyenThanhLuan";
        int age = 21;
        boolean gender = false;
        String email = "abcdef@yahoo.com";
        instance.editUser(oldUser,"MANAGER", username, password, confirm, name, age, gender, email);
        instance.logIn(username, password);
    }

    @Test(expected = GeneralException.class)
    public void testEditUser9() throws Exception {
        System.out.println("Test method editUser 9: Edit account with invalid email");
        instance.logIn("admin", "admin");
        instance.addUser("MANAGER", "manager19", "Manager11", "Manager11", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
        String oldUser = "manager19";
        String username = "manager14";
        String password = "Manager14";
        String confirm = "Manager14";
        String name = "Nguyen Thanh Luan";
        int age = 21;
        boolean gender = false;
        String email = "abcdef.yahoo.com";
        instance.editUser(oldUser, "MANAGER",username, password, confirm, name, age, gender, email);
        instance.logIn(username, password);
    }

    /**
     * Test of saveUser method and loadUser method, of class RestaurantEngine.
     */
    @Test
    public void testSaveAndLoadUser() throws Exception {
        System.out.println("Test method saveUser and loadUser: Save and load list of account in the system");
        int expResult = instance.getAccounts().size();
        instance.saveUser();
        instance.setAccounts(new ArrayList<User>());
        instance.loadUser();
        int result = instance.getAccounts().size();
        assertEquals(expResult, result);
    }

    /**
     * Test of saveTable method and loadTable method, of class RestaurantEngine.
     */
    @Test
    public void testSaveAndLoadTable() throws Exception {
        System.out.println("Test method saveTable and loadTable: Save and load list of table in the system");
        int expResult = instance.getTableList().size();
        instance.saveTable();
        instance.setTableList(new ArrayList<Table>());
        instance.loadTable();
        int result = instance.getTableList().size();
        assertEquals(expResult, result);
    }

    /**
     * Test of saveOrder method and loadOrder method, of class RestaurantEngine.
     */
    @Test
    public void testSaveandLoadOrder() throws Exception {
        System.out.println("Test method saveOrder and loadOrder: Save and load list of order in the system");
        int expResult = instance.getOrders().size();
        instance.saveOrder();
        instance.setOrders(new LinkedList<Order>());
        instance.loadOrder();
        int result = instance.getOrders().size();
        assertEquals(expResult, result);
    }

    /**
     * Test of saveMenu method and loadMenu method, of class RestaurantEngine.
     */
    @Test
    public void testSaveAndLoadMenu() throws Exception {
        System.out.println("Test method saveMenu and loadMenu: Save and load list of category in the system");
        int expResult = instance.getCategoryList().size();
        instance.saveMenu();
        instance.setMenu(new Menu());
        instance.loadMenu();
        int result = instance.getCategoryList().size();
        assertEquals(expResult, result);
    }


    /**
     * Test of exportOrder method and importOrder method, of class RestaurantEngine.
     */
    @Test
    public void testExportOrder() throws Exception {
        System.out.println("Test method exportOrder and importOrder: Export and import old order to CSV file");
        instance.logIn("manager1", "Manager1");
        int num = 0;
        LinkedList<Order> orders = instance.getOrders();
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).isFinished() == false) {
                orders.remove(i);
                i--;
            }
        }
        int expResult = orders.size();
        instance.exportOrder();
        instance.setOrders(new LinkedList<Order>());
        instance.importOrder();
        int result = instance.getOrders().size();
        assertEquals(expResult,result);
    }

    @Before
    public void setUpSearch() throws Exception {
        instance.setOrders(new LinkedList<Order>());
        LinkedList<Order> orders = instance.getOrders();
        Order order1 = new Order("1",0);
        order1.setFinished(true);
        order1.addOrderItem(new Item("Burger", 3000), 5, "Hot");
        orders.add(order1);

        Order order2 = new Order("2",0);
        order2.setFinished(true);
        order2.addOrderItem(new Item("Cheese", 3000), 5, "Hot");
        order2.setTimeStamp(new GregorianCalendar(2012, 12, 11));
        orders.add(order2);

        Order order3 = new Order("3",0);
        order3.setFinished(true);
        order3.addOrderItem(new Item("Bread", 3000), 5, "Hot");
        orders.add(order3);

        Order order4 = new Order("4",0);
        order4.setFinished(true);
        order4.addOrderItem(new Item("Chicken", 15000), 5, "Hot");
        orders.add(order4);

        Order order5 = new Order("5",0);
        order5.setFinished(true);
        order5.addOrderItem(new Item("Chicken", 15000), 44, "Hot");
        orders.add(order5);

        Order order6 = new Order("6",0);
        order6.setFinished(true);
        order6.addOrderItem(new Item("Chicken", 15000), 3, "Cold");
        orders.add(order6);
    }

    /**
     * Test of searchOrder method, of class RestaurantEngine.
     */
    @Test
    public void testSearchOrder() throws Exception {
        System.out.println("Test method searchOrder: Search old order with the keyword in id");
        instance.logIn("manager1", "Manager1");
        String keyword = "1";
        ArrayList<Order> array = instance.searchOrder(keyword, new GregorianCalendar(), new GregorianCalendar());
        Order order = array.get(0);
        String result = order.getId();
        assertTrue(result.indexOf(keyword) != -1);
    }

    /**
     * Test of getOrder method, of class RestaurantEngine.
     */
    @Test
    public void testGetOrder() throws Exception {
        System.out.println("Test method getOrder 1: Get order by id with Manager");
        instance.logIn("manager1", "Manager1");
        String id = "0";
        Order expResult =instance.getOrders().get(0);
        Order result = instance.getOrder(id);
        assertEquals(expResult, result);
    }
}
