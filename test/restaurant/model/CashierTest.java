/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.model;

import restaurant.model.exception.*;
import java.util.ArrayList;
import java.util.LinkedList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CashierTest {

    private static Cashier instance;

    public CashierTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        instance = new Cashier("cashier", "Cashier1", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of addTable method, of class Cashier.
     */
    @Test
    public void testAddTable1() throws Exception {
        System.out.println("Test method addTable 1: Add valid table");
        ArrayList<Table> tableList = new ArrayList<Table>();
        Location pos = new Location(1, 1);
        String name = "Table";
        Location pos1 = new Location(500, 500);
        String name1 = "Table1";
        instance.addTable(tableList, pos, name,100);
        instance.addTable(tableList, pos1, name1,100);
        int expResult = 2;
        int result = tableList.size();
        assertEquals(expResult, result);
    }

    @Test(expected = GeneralException.class)
    public void testAddTable2() throws Exception {
        System.out.println("Test method addTable 2: Add a table on another table");
        ArrayList<Table> tableList = new ArrayList<Table>();
        Location pos = new Location(1, 1);
        String name = "Table";
        Location pos1 = new Location(1, 2);
        String name1 = "Table1";
        instance.addTable(tableList, pos, name,100);
        instance.addTable(tableList, pos1, name1,100);
    }

    @Test(expected = GeneralException.class)
    public void testAddTable3() throws Exception {
        System.out.println("Test method addTable 3: Add a table with an existed name");
        ArrayList<Table> tableList = new ArrayList<Table>();
        Location pos = new Location(1, 1);
        String name = "Table";
        Location pos1 = new Location(500, 500);
        instance.addTable(tableList, pos, name,100);
        instance.addTable(tableList, pos1, name,100);
    }

    @Test(expected = GeneralException.class)
    public void testAddTable4() throws Exception {
        System.out.println("Test method addTable 4: Add a table with name contains special character");
        ArrayList<Table> tableList = new ArrayList<Table>();
        Location pos = new Location(1, 1);
        String name = "Table@";
        instance.addTable(tableList, pos, name,100);
    }

    @Test(expected = GeneralException.class)
    public void testAddTable5() throws Exception {
        System.out.println("Test method addTable 5: Add a table with empty name");
        ArrayList<Table> tableList = new ArrayList<Table>();
        Location pos = new Location(1, 1);
        String name = "";
        instance.addTable(tableList, pos, name,100);
    }

    @Test(expected = GeneralException.class)
    public void testAddTable6() throws Exception {
        System.out.println("Test method addTable 6: Add a table with name has only spaces");
        ArrayList<Table> tableList = new ArrayList<Table>();
        Location pos = new Location(1, 1);
        String name = "       ";
        instance.addTable(tableList, pos, name,100);
    }

    /**
     * Test of getTable method, of class Cashier.
     */
    @Test
    public void testSearchTable1() throws Exception {
        System.out.println("Test method searchTable 1: Search table by the name");
        ArrayList<Table> tableList = new ArrayList<Table>();
        String name = "Table";
        Table expResult = new Table(new Location(1, 1), name);
        instance.addTable(tableList, new Location(1, 1), name,100);
        Table result = instance.searchTable(tableList, name);
        assertEquals(expResult, result);
    }

    @Test
    public void testSearchTable2() throws Exception {
        System.out.println("Test method searchTable 2: Search unexisted table");
        ArrayList<Table> tableList = new ArrayList<Table>();
        String name = "Table";
        Table expResult = null;
        Table result = instance.searchTable(tableList, name);
        assertEquals(expResult, result);
    }

    /**
     * Test of getTable method, of class Cashier.
     */
    @Test
    public void testGetTable1() throws GeneralException {
        System.out.println("Test method getTable 1: Get table by the position");
        ArrayList<Table> tableList = new ArrayList<Table>();
        Location pos = new Location(1, 1);
        Table expResult = new Table(pos, "Table");
        instance.addTable(tableList, pos, "Table",100);
        Table result = instance.getTable(tableList, pos);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetTable2() throws GeneralException {
        System.out.println("Test method getTable 2: Get unexited table by the position");
        ArrayList<Table> tableList = new ArrayList<Table>();
        Location pos = new Location(1, 1);
        Table expResult = null;
        Table result = instance.getTable(tableList, pos);
        assertEquals(expResult, result);
    }

    /**
     * Test of modifyTable method, of class Cashier.
     */
    @Test
    public void testModifyTable1() throws Exception {
        System.out.println("Test method modifyTable 1: Modify name of the table");
        ArrayList<Table> tableList = new ArrayList<Table>();
        String oldName = "Table";
        instance.addTable(tableList, new Location(1, 1), oldName,100);
        String newName = "Table1";
        instance.modifyTable(tableList, oldName, newName);
        Table result = instance.searchTable(tableList, newName);
        assertTrue(result != null);
    }

    @Test(expected = GeneralException.class)
    public void testModifyTable2() throws Exception {
        System.out.println("Test method modifyTable 2: Modify name of the unexisted table");
        ArrayList<Table> tableList = new ArrayList<Table>();
        String oldName = "Table";
        String newName = "Table1";
        instance.modifyTable(tableList, oldName, newName);
    }

    @Test(expected = GeneralException.class)
    public void testModifyTable3() throws Exception {
        System.out.println("Test method modifyTable 3: Modify name of the table with existed name");
        ArrayList<Table> tableList = new ArrayList<Table>();
        String oldName = "Table";
        String newName = "Table1";
        instance.addTable(tableList, new Location(1, 1), oldName,100);
        instance.addTable(tableList, new Location(200, 200), newName,100);
        instance.modifyTable(tableList, oldName, newName);
    }

    @Test(expected = GeneralException.class)
    public void testModifyTable4() throws Exception {
        System.out.println("Test method modifyTable 4: Modify name of table with invalid new name");
        ArrayList<Table> tableList = new ArrayList<Table>();
        String oldName = "Table";
        String newName = "@@@@@@@@";
        instance.addTable(tableList, new Location(1, 1), oldName,100);
        instance.addTable(tableList, new Location(200, 200), newName,100);
        instance.modifyTable(tableList, oldName, newName);
    }

    /**
     * Test of moveAndResizeTable method, of class Cashier.
     */
    @Test
    public void testMoveAndResizeTable1() throws Exception {
        System.out.println("Test method moveAndResizeTable 1: Move a table");
        ArrayList<Table> tableList = new ArrayList<Table>();
        String name = "Table";
        instance.addTable(tableList, new Location(1, 1), name,100);
        Table table = instance.searchTable(tableList, name);
        Location start = new Location(100, 100);
        instance.moveAndResizeTable(tableList, table.getName(), start, table.getWidth(), table.getHeight());
        Table result = instance.getTable(tableList, start);
        assertTrue(result != null);
    }

    @Test
    public void testMoveAndResizeTable2() throws Exception {
        System.out.println("Test method moveAndResizeTable 2: Resize a table");
        ArrayList<Table> tableList = new ArrayList<Table>();
        String name = "Table";
        Location start = new Location(1, 1);
        instance.addTable(tableList, start, name,100);

        Table table = instance.searchTable(tableList, name);
        int oldWidth = table.getWidth();
        int oldHeight = table.getHeight();
        int newWidth = 200;
        int newHeight = 200;
        instance.moveAndResizeTable(tableList, table.getName(), start, newWidth, newHeight);
        Table result = table;
        assertTrue(result.getHeight() == newHeight && result.getWidth() == newWidth);
    }

    @Test(expected = GeneralException.class)
    public void testMoveAndResizeTable3() throws Exception {
        System.out.println("Test method moveAndResizeTable 3: Move a on another table");
        ArrayList<Table> tableList = new ArrayList<Table>();
        String name = "Table";
        instance.addTable(tableList, new Location(1, 1), name,100);

        Table table = instance.searchTable(tableList, name);
        Location start = new Location(100, 100);
        instance.addTable(tableList, start, "Table 1",100);
        instance.moveAndResizeTable(tableList, table.getName(), start, table.getWidth(), table.getHeight());
    }

    @Test(expected = GeneralException.class)
    public void testMoveAndResizeTable4() throws Exception {
        System.out.println("Test method moveAndResizeTable 4: Move a table to invalid location");
        ArrayList<Table> tableList = new ArrayList<Table>();
        String name = "Table";
        instance.addTable(tableList, new Location(1, 1), name,100);

        Table table = instance.searchTable(tableList, name);
        Location start = new Location(-100, -100);
        instance.addTable(tableList, start, "Table 1",100);
        instance.moveAndResizeTable(tableList, table.getName(), start, table.getWidth(), table.getHeight());
    }

    @Test(expected = GeneralException.class)
    public void testMoveAndResizeTable5() throws Exception {
        System.out.println("Test method moveAndResizeTable 5: Resize a table on another table");
        ArrayList<Table> tableList = new ArrayList<Table>();
        String name = "Table";
        Location start = new Location(1, 1);
        instance.addTable(tableList, start, name,100);
        instance.addTable(tableList, new Location(100, 100), "Table 1",100);
        Table table = instance.searchTable(tableList, name);
        int oldWidth = table.getWidth();
        int oldHeight = table.getHeight();
        int newWidth = 200;
        int newHeight = 200;
        instance.moveAndResizeTable(tableList, table.getName(), start, newWidth, newHeight);
        Table result = table;
        assertTrue(result.getHeight() == newHeight && result.getWidth() == newWidth);
    }

    @Test(expected = GeneralException.class)
    public void testMoveAndResizeTable6() throws Exception {
        System.out.println("Test method moveAndResizeTable 6: Resize a table with invalid width and height");
        ArrayList<Table> tableList = new ArrayList<Table>();
        String name = "Table";
        Location start = new Location(1, 1);
        instance.addTable(tableList, start, name,100);
        Table table = instance.searchTable(tableList, name);
        int oldWidth = table.getWidth();
        int oldHeight = table.getHeight();
        int newWidth = -200;
        int newHeight = -200;
        instance.moveAndResizeTable(tableList, table.getName(), start, newWidth, newHeight);
    }

    /**
     * Test of removeTable method, of class Cashier.
     */
    @Test
    public void testRemoveTable1() throws GeneralException {
        System.out.println("Test method removeTable 1: Remove an existed table");
        ArrayList<Table> tableList = new ArrayList<Table>();
        String name = "Table";
        instance.addTable(tableList, new Location(1, 1), name,100);
        instance.removeTable(tableList, name);
        int expResult = 0;
        int result = tableList.size();
        assertEquals(expResult, result);
    }

    @Test(expected = GeneralException.class)
    public void testRemoveTable2() throws GeneralException {
        System.out.println("Test method removeTable 2: Remove an unexisted table");
        ArrayList<Table> tableList = new ArrayList<Table>();
        instance.removeTable(tableList, "Table1");
        int expResult = 0;
        int result = tableList.size();
        assertEquals(expResult, result);
    }

    /**
     * Test of addItemOrder method, of class Cashier.
     */
    @Test
    public void testAddItemOrder1() throws GeneralException {
        System.out.println("Test method addItemOrder 1: Add item into order");
        LinkedList<Order> orders = new LinkedList<Order>();
        Table table = new Table(new Location(1, 1), "Table");
        Item item = new Item("Breakfast", 1000);
        int quantity = 1;
        String note = "Nothing";
        instance.addItemOrder(orders, table, item, quantity, note,0);
        int result = table.getCurrentOrder().getOrderItems().size();
        int expResult = 1;
        assertEquals(expResult, result);
    }

    /**
     * Test of removeItemOrder method, of class Cashier.
     */
    @Test
    public void testRemoveItemOrder1() throws Exception {
        System.out.println("Test method removeItemOrder 1: Remove an existed item");
        LinkedList<Order> orders = new LinkedList<Order>();
        ArrayList<Table> tableList = new ArrayList<Table>();
        Table table = new Table(new Location(1, 1), "Table");
        Item item = new Item("Breakfast", 1000);
        int quantity = 1;
        String note = "Nothing";
        instance.addItemOrder(orders, table, item, quantity, note,0);
        instance.removeItemOrder(tableList, table, item);
        int result = table.getCurrentOrder().getOrderItems().size();
        int expResult = 0;
        assertEquals(expResult, result);
    }

    @Test(expected = GeneralException.class)
    public void testRemoveItemOrder2() throws Exception {
        System.out.println("Test method removeItemOrder 2: Remove an unexisted item");
        LinkedList<Order> orders = new LinkedList<Order>();
        ArrayList<Table> tableList = new ArrayList<Table>();
        Table table = new Table(new Location(1, 1), "Table");
        Item item = new Item("Breakfast", 1000);
        Item item1 = new Item("Lunch", 1000);
        int quantity = 1;
        String note = "Nothing";
        instance.addItemOrder(orders, table, item, quantity, note,0);
        instance.removeItemOrder(tableList, table, item1);
    }

    /**
     * Test of calculateOrder method, of class Cashier.
     */
    @Test
    public void testCalculateOrder1() throws GeneralException {
        System.out.println("Test method calculateOrder 1: Calculate order with one item");
        ArrayList<Table> tableList = new ArrayList<Table>();
        LinkedList<Order> orders = new LinkedList<Order>();
        Table table = new Table(new Location(1, 1), "Table");
        Item item = new Item("Breakfast", 1000);
        int quantity = 1;
        String note = "Nothing";
        instance.addItemOrder(orders, table, item, quantity, note,0);
        double expResult = item.getPrice();
        double result = instance.calculateOrder(table);
        assertEquals(expResult, result, 0.0);
    }

    @Test
    public void testCalculateOrder2() throws GeneralException {
        System.out.println("Test method calculateOrder 2: Calculate order with many item");
        ArrayList<Table> tableList = new ArrayList<Table>();
        LinkedList<Order> orders = new LinkedList<Order>();
        Table table = new Table(new Location(1, 1), "Table");
        Item item = new Item("Breakfast", 1000);
        Item item1 = new Item("Lunch", 1100);
        int quantity = 2;
        String note = "Nothing";
        instance.addItemOrder(orders, table, item, quantity, note,0);
        instance.addItemOrder(orders, table, item1, quantity, note,0);
        double expResult = item.getPrice() * quantity + item1.getPrice() * quantity;
        double result = instance.calculateOrder(table);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of completeOrder method, of class Cashier.
     */
    @Test
    public void testCompleteOrder() throws GeneralException {
        System.out.println("Test method completeOrder: Check out the order");
        ArrayList<Table> tableList = null;
        LinkedList<Order> orders = new LinkedList<Order>();
        Table table = new Table(new Location(1, 1), "Table");
        Item item = new Item("Breakfast", 1000);
        int quantity = 2;
        String note = "Nothing";
        instance.addItemOrder(orders, table, item, quantity, note,0);
        instance.completeOrder(tableList, table);
        assertTrue(table.getCurrentOrder()==null);
    }

    /**
     * Test of searchCategory method, of class Cashier.
     */
    @Test
    public void testSearchCategory1() throws DuplicateException, InvalidInputException, GeneralException {
        System.out.println("Test method searchCategory 1: Search category with existed item");
        Menu menu = new Menu();
        String itemName = "Bread";
        menu.addCategory("Breakfast");
        menu.addItem(itemName, 1000, "Breakfast");
        Category result = instance.searchCategory(menu, itemName);
        assertTrue(result!=null);
    }

    @Test
    public void testSearchCategory2() throws DuplicateException, InvalidInputException, GeneralException {
        System.out.println("Test method searchCategory 2: Search category with unexisted item");
        Menu menu = new Menu();
        String itemName = "Bread";
        Category result = instance.searchCategory(menu, itemName);
        assertTrue(result==null);
    }

    /**
     * Test of getCategory method, of class Cashier.
     */
    @Test
    public void testGetCategory1() throws DuplicateException, InvalidInputException {
        System.out.println("Test method getCategory 1: Get category by category name");
        Menu menu = new Menu();
        String categoryName = "Breakfast";
        menu.addCategory(categoryName);
        Category result = instance.getCategory(menu, categoryName);
        assertTrue(result!=null);
    }

    @Test
    public void testGetCategory2() throws DuplicateException, InvalidInputException {
        System.out.println("Test method getCategory 2: Get unexisted category by category name");
        Menu menu = new Menu();
        String categoryName = "Breakfast";
        Category result = instance.getCategory(menu, categoryName);
        assertTrue(result==null);
    }

    /**
     * Test of getItem method, of class Cashier.
     */
    @Test
    public void testGetItem1() throws DuplicateException, InvalidInputException, GeneralException {
        System.out.println("Test method getItem 1: Get item by name");
        Menu menu = new Menu();
        String itemName = "Bread";
        menu.addCategory("Breakfast");
        menu.addItem(itemName, 1000, "Breakfast");
        Item result = instance.getItem(menu, itemName);
        assertTrue(result!=null);
    }

    @Test
    public void testGetItem2() throws DuplicateException, InvalidInputException, GeneralException {
        System.out.println("Test method getItem 2: Get unexisted item by name");
        Menu menu = new Menu();
        String itemName = "Bread";
        Item result = instance.getItem(menu, itemName);
        assertTrue(result==null);
    }
}
