/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.model;

import java.util.GregorianCalendar;
import java.util.HashMap;
import org.junit.Before;
import restaurant.model.exception.DuplicateException;
import restaurant.model.exception.InvalidInputException;
import restaurant.model.exception.GeneralException;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ManagerTest {

    private static Manager instance;
    private static LinkedList<Order> orders;

    public ManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        instance = new Manager("manager", "Manager1", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of addCategory method, of class Manager.
     */
    @Test
    public void testAddCategory1() throws Exception {
        System.out.println("Test method addCategory 1: Add a category into menu");
        Menu menu = new Menu();
        String name = "Category";
        instance.addCategory(menu, name);
        assertTrue(menu.getCategoryList().size() == 1);
    }

    @Test(expected = DuplicateException.class)
    public void testAddCategory2() throws Exception {
        System.out.println("Test method addCategory 2: Add an existed category into menu");
        Menu menu = new Menu();
        String name = "Category";
        instance.addCategory(menu, name);
        instance.addCategory(menu, name);
    }

    @Test(expected = InvalidInputException.class)
    public void testAddCategory3() throws Exception {
        System.out.println("Test method addCategory 3: Add a category with empty name into menu");
        Menu menu = new Menu();
        String name = "";
        instance.addCategory(menu, name);
    }

    @Test(expected = InvalidInputException.class)
    public void testAddCategory4() throws Exception {
        System.out.println("Test method addCategory 4: Add a category with only-space name into menu");
        Menu menu = new Menu();
        String name = "      ";
        instance.addCategory(menu, name);
    }

    /**
     * Test of removeCategory method, of class Manager.
     */
    @Test
    public void testRemoveCategory1() throws Exception {
        System.out.println("Test method removeCategory 1: Remove a valid category");
        Menu menu = new Menu();
        String name = "Category";
        instance.addCategory(menu, name);
        instance.removeCategory(menu, name);
        assertTrue(menu.getCategoryList().isEmpty());
    }

    @Test(expected = GeneralException.class)
    public void testRemoveCategory2() throws Exception {
        System.out.println("Test method removeCategory 2: Remove a valid category");
        Menu menu = new Menu();
        String name = "Category";
        instance.removeCategory(menu, name);
    }

    /**
     * Test of addItem method, of class Manager.
     */
    @Test
    public void testAddItem1() throws Exception {
        System.out.println("Test method addItem 1: Add a valid item into a category");
        Menu menu = new Menu();
        String name = "Bread";
        long price = 110;
        String categoryName = "Category";
        instance.addCategory(menu, categoryName);
        instance.addItem(menu, name, price, categoryName);
        assertTrue(instance.searchCategory(menu, name).getItemList().size() == 1);
    }

    @Test(expected = GeneralException.class)
    public void testAddItem2() throws Exception {
        System.out.println("Test method addItem 2: Add a existed item in a category to that category");
        Menu menu = new Menu();
        String name = "Bread";
        long price = 110;
        String categoryName = "Category";
        instance.addCategory(menu, categoryName);
        instance.addItem(menu, name, price, categoryName);
        instance.addItem(menu, name, price, categoryName);
    }

    @Test(expected = GeneralException.class)
    public void testAddItem3() throws Exception {
        System.out.println("Test method addItem 3: Add a existed item in a category to another category");
        Menu menu = new Menu();
        String name = "Bread";
        long price = 110;
        String categoryName = "Category";
        instance.addCategory(menu, categoryName);
        String categoryName1 = "Category1";
        instance.addCategory(menu, categoryName1);
        instance.addItem(menu, name, price, categoryName);
        instance.addItem(menu, name, price, categoryName1);
    }

    @Test(expected = GeneralException.class)
    public void testAddItem4() throws Exception {
        System.out.println("Test method addItem 4: Add a valid item into an unexisted category");
        Menu menu = new Menu();
        String name = "Bread";
        long price = 110;
        String categoryName = "Category";
        instance.addItem(menu, name, price, categoryName);
    }

    @Test(expected = InvalidInputException.class)
    public void testAddItem5() throws Exception {
        System.out.println("Test method addItem 5: Add a item with empty name into a category");
        Menu menu = new Menu();
        String name = "";
        long price = 110;
        String categoryName = "Category";
        instance.addCategory(menu, categoryName);
        instance.addItem(menu, name, price, categoryName);
    }

    @Test(expected = InvalidInputException.class)
    public void testAddItem6() throws Exception {
        System.out.println("Test method addItem 6: Add a item with only-space name into a category");
        Menu menu = new Menu();
        String name = "     ";
        long price = 110;
        String categoryName = "Category";
        instance.addCategory(menu, categoryName);
        instance.addItem(menu, name, price, categoryName);
    }

    /**
     * Test of removeItem method, of class Manager.
     */
    @Test
    public void testRemoveItem1() throws Exception {
        System.out.println("Test method removeItem 1: Remove item from the category");
        Menu menu = new Menu();
        String name = "Bread";
        long price = 110;
        String categoryName = "Category";
        instance.addCategory(menu, categoryName);
        instance.addItem(menu, name, price, categoryName);
        Category category = instance.searchCategory(menu, name);
        instance.removeItem(menu, name);
        assertTrue(category.getItemList().isEmpty());
    }

    @Test(expected = GeneralException.class)
    public void testRemoveItem2() throws Exception {
        System.out.println("Test method removeItem 2: Remove unexisted item from the category");
        Menu menu = new Menu();
        String name = "Bread";
        long price = 110;
        String categoryName = "Category";
        instance.addCategory(menu, categoryName);
        Category category = instance.searchCategory(menu, name);
        instance.removeItem(menu, name);
    }

    @Test(expected = GeneralException.class)
    public void testRemoveItem3() throws Exception {
        System.out.println("Test method removeItem 3: Remove ordered item from the category");
        Menu menu = new Menu();
        String name = "Bread";
        long price = 110;
        String categoryName = "Category";
        instance.addCategory(menu, categoryName);
        instance.addItem(menu, name, price, categoryName);
        //Add item into table
        instance.addItemOrder(new LinkedList<Order>(), new Table(new Location(1, 1),
                "Table 1"), instance.getItem(menu, name), 1, "",0);
        Category category = instance.searchCategory(menu, name);
        instance.removeItem(menu, name);
    }

    /**
     * Test of editCategory method, of class Manager.
     */
    @Test
    public void testEditCategory1() throws Exception {
        System.out.println("Test method editCategory 1: Edit name of the category");
        Menu menu = new Menu();
        String oldName = "Bread";
        String newName = "Burger";
        instance.addCategory(menu, oldName);
        instance.editCategory(menu, oldName, newName);
        assertTrue(instance.getCategory(menu, newName) != null);
    }

    @Test(expected = GeneralException.class)
    public void testEditCategory2() throws Exception {
        System.out.println("Test method editCategory 2: Edit name of the unexisted category");
        Menu menu = new Menu();
        String oldName = "Bread";
        String newName = "Burger";
        instance.editCategory(menu, oldName, newName);
        assertTrue(instance.getCategory(menu, newName) != null);
    }

    @Test(expected = GeneralException.class)
    public void testEditCategory3() throws Exception {
        System.out.println("Test method editCategory 3: Edit name of the category by an empty name");
        Menu menu = new Menu();
        String oldName = "Bread";
        String newName = "";
        instance.addCategory(menu, oldName);
        instance.editCategory(menu, oldName, newName);
    }

    @Test(expected = GeneralException.class)
    public void testEditCategory4() throws Exception {
        System.out.println("Test method editCategory 4: Edit name of the category by a only-space name");
        Menu menu = new Menu();
        String oldName = "Bread";
        String newName = "     ";
        instance.addCategory(menu, oldName);
        instance.editCategory(menu, oldName, newName);
    }

    /**
     * Test of editItem method, of class Manager.
     */
    @Test
    public void testEditItem1() throws Exception {
        System.out.println("Test method editItem 1: Edit name anh price of an item");
        Menu menu = new Menu();
        String itemName = "Bread";
        String newName = "Burger";
        long price = 100;
        long newPrice = 140;
        String categoryName = "Category";
        instance.addCategory(menu, categoryName);
        instance.addItem(menu, itemName, price, categoryName);
        instance.editItem(menu, itemName, newName, newPrice);
        assertTrue(instance.getItem(menu, newName) != null && instance.getItem(menu, newName).getPrice() == newPrice);
    }

    @Test(expected = GeneralException.class)
    public void testEditItem2() throws Exception {
        System.out.println("Test method editItem 2: Edit name anh price of an unexisted item");
        Menu menu = new Menu();
        String itemName = "Bread";
        String newName = "Burger";
        long newPrice = 140;
        instance.editItem(menu, itemName, newName, newPrice);
    }

    @Test(expected = GeneralException.class)
    public void testEditItem3() throws Exception {
        System.out.println("Test method editItem 3: Edit existed name anh price of an item");
        Menu menu = new Menu();
        String itemName = "Bread";
        String newName = "Burger";
        long price = 100;
        long newPrice = 140;
        String categoryName = "Category";
        instance.addCategory(menu, categoryName);
        instance.addItem(menu, itemName, price, categoryName);
        instance.addItem(menu, newName, price, categoryName);
        instance.editItem(menu, itemName, newName, newPrice);
    }

    @Test(expected = GeneralException.class)
    public void testEditItem4() throws Exception {
        System.out.println("Test method editItem 4: Edit name anh negative price of an item");
        Menu menu = new Menu();
        String itemName = "Bread";
        String newName = "Burger";
        long price = 100;
        long newPrice = -140;
        String categoryName = "Category";
        instance.addCategory(menu, categoryName);
        instance.addItem(menu, itemName, price, categoryName);
        instance.editItem(menu, itemName, newName, newPrice);
    }

    @Test(expected = GeneralException.class)
    public void testEditItem5() throws Exception {
        System.out.println("Test method editItem 5: Edit name anh price of an ordered item");
        Menu menu = new Menu();
        String itemName = "Bread";
        String newName = "Burger";
        long price = 100;
        long newPrice = 140;
        String categoryName = "Category";
        instance.addCategory(menu, categoryName);
        instance.addItem(menu, itemName, price, categoryName);
        instance.addItemOrder(new LinkedList<Order>(), new Table(new Location(1, 1),
                itemName), instance.getItem(menu, itemName), 1, "",0);
        instance.editItem(menu, itemName, newName, newPrice);
    }

    @Before
    public void setUpSearch() throws Exception {
        orders = new LinkedList<Order>();
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
     * Test of searchOrder method, of class Manager.
     */
    @Test
    public void testSearchOrder1() throws GeneralException {
        System.out.println("Test method searchOrder 1: Search order having the key word in the id");
        String keyword = "1";
        ArrayList<Order> array = instance.searchOrder(keyword, orders);
        Order order = array.get(0);
        String result = order.getId();
        assertTrue(result.indexOf(keyword) != -1);
    }

    @Test
    public void testSearchOrder2() throws GeneralException {
        System.out.println("Test method searchOrder 2: Search order having the key word in the date");
        String keyword = "12";
        ArrayList<Order> array = instance.searchOrder(keyword, orders);
        Order order = array.get(0);
        String result = order.getTimeStamp().get(GregorianCalendar.YEAR) + "";
        assertTrue(result.indexOf(keyword) != -1);
    }

    @Test
    public void testSearchOrder3() throws GeneralException {
        System.out.println("Test method searchOrder 3: Search order having the key word in item name");
        String keyword = "Bread";
        ArrayList<Order> array = instance.searchOrder(keyword, orders);
        Order order = array.get(0);
        String result = order.getOrderItems().get(0).getItem().getName();
        System.out.println(result+"-"+keyword);
        assertTrue(result.indexOf(keyword) != -1);
    }

    @Test
    public void testSearchOrder4() throws GeneralException {
        System.out.println("Test method searchOrder 4: Search order having the key word in item price");
        String keyword = "15000";
        ArrayList<Order> array = instance.searchOrder(keyword, orders);
        Order order = array.get(0);
        String result = order.getOrderItems().get(0).getItem().getPrice() + "";
        assertTrue(result.indexOf(keyword) != -1);
    }

    @Test
    public void testSearchOrder5() throws GeneralException {
        System.out.println("Test method searchOrder 5: Search order having the key word in quantity");
        String keyword = "44";
        ArrayList<Order> array = instance.searchOrder(keyword, orders);
        Order order = array.get(0);
        String result = order.getOrderItems().get(0).getQuantity() + "";
        assertTrue(result.indexOf(keyword) != -1);
    }

    @Test
    public void testSearchOrder6() throws GeneralException {
        System.out.println("Test method searchOrder 6: Search order having the key word in note item");
        String keyword = "Cold";
        ArrayList<Order> array = instance.searchOrder(keyword, orders);
        Order order = array.get(0);
        String result = order.getOrderItems().get(0).getNote() + "";
        assertTrue(result.indexOf(keyword) != -1);
    }

    /**
     * Test of exportOrder method, of class Manager.
     */
    @Test
    public void testExportAndImportOrder() throws Exception {
        System.out.println("Test method exportOrder and importOrder: Export/Import order to CSV file");
        File file = new File("Jsdf23sd1f32sd1f.csv");
        LinkedList<Order> ordersBefore = new LinkedList<Order>();
        Order order = new Order("1",0);
        order.addOrderItem(new Item("Bread", 1000), 1, "Good");
        order.setFinished(true);
        ordersBefore.add(order);
        instance.exportOrder(file, ordersBefore);

        LinkedList<Order> ordersAfter = new LinkedList<Order>();
        instance.importOrder(file, ordersAfter);
        if (ordersAfter.size() > 0) {
            Order result = ordersBefore.get(0);
            Item itemResult = result.getOrderItems().get(0).getItem();
            assertTrue(itemResult.getName().equals("Bread"));
        } else {
            fail("Can not import the file");
        }
    }

    /**
     * Test of revenueReport method, of class Manager.
     */
    @Test
    public void testRevenueReport() throws Exception {
        System.out.println("Test method revenueReport: Generate the revenue report of each item during a period of time");
        GregorianCalendar startDate = new GregorianCalendar();
        GregorianCalendar endDate = new GregorianCalendar();
        LinkedList<Order> orders = new LinkedList<Order>();
        Order order = new Order("123456",0);
        order.addOrderItem(new Item("Egg", 1000), 1, null);
        orders.add(order);
        HashMap<String, Long> expResult = new HashMap<String, Long>();
        long revenue = 1000;
        expResult.put("Egg",revenue);
        HashMap result = instance.revenueReport(startDate, endDate, orders);
        assertEquals(expResult, result);

    }

}
