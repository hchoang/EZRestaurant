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


public class LocationTest {
    private Location instance;
    public LocationTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of contructor Location method, of class Location.
     */
    @Test
    public void testLocation1() throws GeneralException {
        System.out.println("Test method Location 1: Create new Location");
        int x = 2;
        int y = 4;
        instance = new Location(x, y);
    }

    @Test(expected=GeneralException.class)
    public void testLocation2() throws GeneralException {
        System.out.println("Test method Location 2: Create new Location with negative x");
        int x = -2;
        int y = 4;
        instance = new Location(x, y);
    }

    @Test(expected=GeneralException.class)
    public void testLocation3() throws GeneralException {
        System.out.println("Test method Location 3: Create new Location with negative y");
        int x = 2;
        int y = -4;
        instance = new Location(x, y);
    }

    @Test(expected=GeneralException.class)
    public void testLocation4() throws GeneralException {
        System.out.println("Test method Location 4: Create new Location with negative x and y");
        int x = -2;
        int y = -4;
        instance = new Location(x, y);
    }

    /**
     * Test of getX method, of class Location.
     */
    @Test
    public void testGetX() throws GeneralException {
        System.out.println("Test method getX: Get the x axis");
        int x = 2;
        int y = 4;
        instance = new Location(x, y);
        int expResult = x;
        int result = instance.getX();
        assertEquals(expResult, result);
    }

    /**
     * Test of setX method, of class Location.
     */
    @Test
    public void testSetX1() throws GeneralException {
        System.out.println("Test method setX 1: Set the x axis");
        int x = 2;
        int y = 4;
        instance = new Location(x, y);
        int newX=5;
        int expResult = newX;
        instance.setX(newX);
        int result = instance.getX();
        assertEquals(expResult, result);
    }

    @Test(expected=GeneralException.class)
    public void testSetX2() throws GeneralException {
        System.out.println("Test method setX 2: Set the x axis with negative number");
        int x = 2;
        int y = 4;
        instance = new Location(x, y);
        int newX=-5;
        instance.setX(newX);
    }

    /**
     * Test of getY method, of class Location.
     */
    @Test
    public void testGetY() throws GeneralException {
        System.out.println("Test method getY: Get the y axis");
        int x = 2;
        int y = 4;
        instance = new Location(x, y);
        int expResult = y;
        int result = instance.getY();
        assertEquals(expResult, result);
    }

    /**
     * Test of setY method, of class Location.
     */
    @Test
    public void testSetY1() throws GeneralException {
        System.out.println("Test method setY 1: Set the y axis");
        int x = 2;
        int y = 4;
        instance = new Location(x, y);
        int newY=5;
        int expResult = newY;
        instance.setY(newY);
        int result = instance.getY();
        assertEquals(expResult, result);
    }

    @Test(expected=GeneralException.class)
    public void testSetY2() throws GeneralException {
        System.out.println("Test method setY 2: Set the y axis with negative number");
        int x = 2;
        int y = 4;
        instance = new Location(x, y);
        int newY=-5;
        instance.setY(newY);
    }

    /**
     * Test of equals method, of class Location.
     */
    @Test
    public void testEquals1() throws GeneralException {
        System.out.println("Test method equals 1: Check two equal location");
        instance = new Location(4,5);
        boolean expResult = true;
        boolean result = instance.equals(new Location(4,5));
        assertEquals(expResult, result);
    }
    
    @Test
    public void testEquals2() throws GeneralException {
        System.out.println("Test method equals 2: Check two not equal location");
        instance = new Location(4,5);
        boolean expResult = false;
        boolean result = instance.equals(new Location(0,0));
        assertEquals(expResult, result);
    }
}