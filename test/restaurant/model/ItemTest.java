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


public class ItemTest {
    private static Item instance;
    public ItemTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of contructor Item method, of class Item.
     */
    @Test
    public void testItem1() throws GeneralException {
        System.out.println("Test method Item 1: Create an item with valid information");
        String name = "Bread";
        instance = new Item(name, 100);
    }

    @Test(expected=GeneralException.class)
    public void testItem2() throws GeneralException {
        System.out.println("Test method Item 2: Create an item with negative price");
        String name = "Bread";
        instance = new Item(name, -100);
    }

    /**
     * Test of getName method, of class Item.
     */
    @Test
    public void testGetName() throws GeneralException {
        System.out.println("Test method getName: Get name of the item");
        String name = "Bread";
        instance = new Item(name, 100);
        String expResult = name;
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of setName method, of class Item.
     */
    @Test
    public void testSetName() throws GeneralException {
        System.out.println("Test method setName: Set name of the item");
        String name = "Bread";
        instance = new Item(name, 100);
        String newName="Burger";
        instance.setName(newName);
        String expResult = newName;
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of getPrice method, of class Item.
     */
    @Test
    public void testGetPrice() throws GeneralException {
        System.out.println("Test method getPrice: Get the price of the item");
        String name = "Bread";
        long expResult = 110;
        instance = new Item(name, expResult);
        long result = instance.getPrice();
        assertEquals(expResult, result, 0);
    }

    /**
     * Test of setPrice method, of class Item.
     */
    @Test
    public void testSetPrice1() throws GeneralException {
        System.out.println("Test method setPrice1: Set the price of the item");
        long price = 100;
         String name = "Bread";
        instance = new Item(name, price);
        long expResult = 110;
        instance.setPrice(expResult);
        long result = instance.getPrice();
        assertEquals(result,expResult,0);
    }

    @Test(expected=GeneralException.class)
    public void testSetPrice2() throws GeneralException {
        System.out.println("Test method setPrice2: Set a negative price of the item");
        long price = 100;
         String name = "Bread";
        instance = new Item(name, price);
        long expResult = -110;
        instance.setPrice(expResult);
        long result = instance.getPrice();
        assertEquals(result,expResult,0);
    }

    /**
     * Test of getNumberOrder method, of class Item.
     */
    @Test
    public void testGetNumberOrder() throws GeneralException {
        System.out.println("Test method getNumberOrder: Get the number of order has the item");
        instance = new Item("Bread", 100);
        instance.increaseNum();
        int expResult = 1;
        int result = instance.getNumberOrder();
        assertEquals(expResult, result);    
    }

    /**
     * Test of increaseNum method, of class Item.
     */
    @Test
    public void testIncreaseNum() throws GeneralException {
        System.out.println("Test method increaseNum: Increase the number of order has the item");
        instance = new Item("Bread", 100);
        instance.increaseNum();
        assertTrue(instance.getNumberOrder()==1);
    }

    /**
     * Test of decreaseNum method, of class Item.
     */
    @Test
    public void testDecreaseNum() throws GeneralException {
        System.out.println("Test method decreaseNum: Decrease the number of order has the item");
        instance = new Item("Bread", 100);
        instance.increaseNum();
        instance.decreaseNum();
        assertTrue(instance.getNumberOrder()==0);
    }

    /**
     * Test of toString method, of class Item.
     */
    @Test
    public void testToString() throws GeneralException {
        System.out.println("Test method toString: Print information of the item");
        String name = "Bread";
        long price = 100;
        instance = new Item(name, price);
        String expResult = name+"\",\""+price;
        String result = instance.toString();
        assertEquals(expResult, result);
    }

}