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
public class OrderItemTest {
    private static OrderItem instance ;
    public OrderItemTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of constructor OrderItem method, of class OrderItem.
     */
    @Test
    public void testOrderItem1() throws GeneralException {
        System.out.println("Test method OrderItem 1: Create a valid order item with note");
        Item expResult = new Item("Bread", 1000);
        instance= new OrderItem(expResult, 1, "Note");
    }

    @Test
    public void testOrderItem2() throws GeneralException {
        System.out.println("Test method OrderItem 2: Create a valid order item without note");
        Item expResult = new Item("Bread", 1000);
        instance= new OrderItem(expResult, 1, "");
    }

    @Test(expected=GeneralException.class)
    public void testOrderItem3() throws GeneralException {
        System.out.println("Test method OrderItem 3: Create a invalid order item with negative quantity");
        Item expResult = new Item("Bread", 1000);
        instance= new OrderItem(expResult, -1, "");
    }

    @Test(expected=GeneralException.class)
    public void testOrderItem4() throws GeneralException {
        System.out.println("Test method OrderItem 4: Create a invalid order item with quantity 0");
        Item expResult = new Item("Bread", 1000);
        instance= new OrderItem(expResult, 0, "");
    }

    @Test(expected=GeneralException.class)
    public void testOrderItem5() throws GeneralException {
        System.out.println("Test method OrderItem 5: Create a invalid order item with null item");
        Item expResult = null;
        instance= new OrderItem(expResult, 1, "");
    }

    /**
     * Test of getItem method, of class OrderItem.
     */
    @Test
    public void testGetItem() throws GeneralException {
        System.out.println("Test method getItem: Get item in the order item");
        Item expResult = new Item("Bread", 1000);
        instance= new OrderItem(expResult, 1, "");
        Item result = instance.getItem();
        assertEquals(expResult, result);
    }

    /**
     * Test of getNote method, of class OrderItem.
     */
    @Test
    public void testGetNote() throws GeneralException {
        System.out.println("Test method getNote: Get note of the order item");
        Item item = new Item("Bread", 1000);
        String expResult = "Note";
        instance= new OrderItem(item, 1, expResult);
        String result = instance.getNote();
        assertEquals(expResult, result);
    }

    /**
     * Test of setNote method, of class OrderItem.
     */
    @Test
    public void testSetNote() throws GeneralException {
        System.out.println("Test method setNote: Set the note for the order item");
        Item item = new Item("Bread", 1000);
        String expResult = "Note";
        instance= new OrderItem(item, 1, "Nothing");
        instance.setNote(expResult);
        String result = instance.getNote();
        assertEquals(expResult, result);
    }

    /**
     * Test of getQuantity method, of class OrderItem.
     */
    @Test
    public void testGetQuantity() throws GeneralException {
        System.out.println("Test method getQuantity: Get the quantity for order item");
        int expResult = 3;
        Item item = new Item("Bread", 1000);
        instance= new OrderItem(item, expResult, "Nothing");
        int result = instance.getQuantity();
        assertEquals(expResult, result);
    }

    /**
     * Test of setQuantity method, of class OrderItem.
     */
    @Test
    public void testSetQuantity() throws GeneralException {
        System.out.println("Test method setQuantity: Set the quantity for order item");
        int expResult = 3;
        Item item = new Item("Bread", 1000);
        instance= new OrderItem(item, 1, "Nothing");
        instance.setQuantity(expResult);
         int result = instance.getQuantity();
        assertEquals(expResult, result);
    }

    /**
     * Test of calculatePrice method, of class OrderItem.
     */
    @Test
    public void testCalculatePrice() throws GeneralException {
        System.out.println("Test method calculatePrice: Calculate the price of order item");
        long price = 1000;
        int quantity = 3;
       Item item = new Item("Bread", price);
        instance= new OrderItem(item, quantity, "Nothing");
        double expResult = price*quantity;
        double result = instance.calculatePrice();
        assertEquals(expResult, result, 0.0);
    }
}