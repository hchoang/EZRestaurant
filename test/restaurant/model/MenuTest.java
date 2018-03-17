/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.model;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import restaurant.model.exception.DuplicateException;
import restaurant.model.exception.GeneralException;
import restaurant.model.exception.InvalidInputException;

/**
 *
 * @author $Le Hoang Hai
 */
public class MenuTest {

    public MenuTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of getCategoryList method, of class Menu.
     */
    @Test
    public void testGetCategoryList() throws DuplicateException, InvalidInputException {
        System.out.println("Test method getCategoryList: Get the list of category");
        Menu instance = new Menu();
        String name = "Category";
        instance.addCategory(name);
        int expResult = 1;
        int result = instance.getCategoryList().size();
        assertEquals(expResult, result);
    }

    /**
     * Test of addCategory method, of class Menu.
     */
    @Test
    public void testAddCategory1() throws Exception {
        System.out.println("Test method addCategory 1: Add the category");
        Menu instance = new Menu();
        String name = "Category";
        instance.addCategory(name);
        int expResult = 1;
        int result = instance.getCategoryList().size();
        assertEquals(expResult, result);
    }

    @Test(expected = DuplicateException.class)
    public void testAddCategory2() throws Exception {
        System.out.println("Test method addCategory 2: Add the duplicate category");
        Menu instance = new Menu();
        String name = "Category";
        instance.addCategory(name);
        instance.addCategory(name);
    }

    @Test(expected = InvalidInputException.class)
    public void testAddCategory3() throws Exception {
        System.out.println("Test method addCategory 3: Add the category with empty name");
        Menu instance = new Menu();
        String name = "";
        instance.addCategory(name);
    }

    @Test(expected = InvalidInputException.class)
    public void testAddCategory4() throws Exception {
        System.out.println("Test method addCategory 4: Add the category with only-space name");
        Menu instance = new Menu();
        String name = "        ";
        instance.addCategory(name);
    }

    /**
     * Test of deleteCategory method, of class Menu.
     */
    @Test
    public void testDeleteCategory1() throws Exception {
        System.out.println("Test method deleteCategory 1: Delete category contains from the menu");
        String name = "Category";
        Category category = new Category(name);
        Menu instance = new Menu();
        instance.addCategory(name);
        instance.addItem("Bread", 100, name);
        instance.deleteCategory(category);
        int expResult = 0;
        int result = instance.getCategoryList().size();
        assertEquals(expResult, result);
    }

    @Test(expected = GeneralException.class)
    public void testDeleteCategory2() throws Exception {
        System.out.println("Test method deleteCategory 2: Delete category contains "
                + "more than one ordered item from the menu");
        String name = "Category";
        Menu instance = new Menu();
        instance.addCategory(name);
        instance.addItem("Bread", 100, name);
        instance.findItem("Bread").increaseNum();
        instance.deleteCategory(instance.getCategory(name));
    }

    /**
     * Test of getCategory method, of class Menu.
     */
    @Test
    public void testGetCategory1() throws DuplicateException, InvalidInputException {
        System.out.println("Test method getCategory 1: Get the category by name");
        String name = "Category";
        Category category = new Category(name);
        Menu instance = new Menu();
        instance.addCategory(name);
        Category expResult = category;
        Category result = instance.getCategory(name);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetCategory2() throws DuplicateException, InvalidInputException {
        System.out.println("Test method getCategory 2: Get the unexisted category by name");
        String name = "Category";
        Category category = new Category(name);
        Menu instance = new Menu();
        Category expResult = null;
        Category result = instance.getCategory(name);
        assertEquals(expResult, result);
    }

    /**
     * Test of searchCategory method, of class Menu.
     */
    @Test
    public void testSearchCategory1() throws DuplicateException, InvalidInputException, GeneralException {
        System.out.println("Test method searchCategory 1: Search category contains item");
        String name = "Category";
        String itemName = "Bread";
        Menu instance = new Menu();
        instance.addCategory(name);
        instance.addItem(itemName, 100, name);
        Category result = instance.searchCategory(itemName);
        Category expResult = instance.getCategory(name);
        assertEquals(expResult, result);
    }

    @Test
    public void testSearchCategory2() throws DuplicateException, InvalidInputException, GeneralException {
        System.out.println("Test method searchCategory 2: Search category contains unexisted item");
        String name = "Category";
        String itemName = "Bread";
        Menu instance = new Menu();
        instance.addCategory(name);
        Category result = instance.searchCategory(itemName);
        Category expResult = null;
        assertEquals(expResult, result);
    }

    /**
     * Test of editCategory method, of class Menu.
     */
    @Test
    public void testEditCategory1() throws Exception {
        System.out.println("Test method editCategory 1: Edit the category name");
        String oldName = "Bread";
        String newName = "Burger";
        Menu instance = new Menu();
        instance.addCategory(oldName);
        instance.editCategory(oldName, newName);
        Category result = instance.getCategory(newName);
        assertTrue(result != null);
    }

    @Test(expected = GeneralException.class)
    public void testEditCategory2() throws Exception {
        System.out.println("Test method editCategory 2: Edit the category name with an existed new name");
        String oldName = "Bread";
        String newName = "Burger";
        Menu instance = new Menu();
        instance.addCategory(oldName);
        instance.addCategory(newName);
        instance.editCategory(oldName, newName);
    }

    @Test(expected = GeneralException.class)
    public void testEditCategory3() throws Exception {
        System.out.println("Test method editCategory 3: Edit the category name with an empty new name");
        String oldName = "Bread";
        String newName = "";
        Menu instance = new Menu();
        instance.addCategory(oldName);
        instance.editCategory(oldName, newName);
    }

    @Test(expected = GeneralException.class)
    public void testEditCategory4() throws Exception {
        System.out.println("Test method editCategory 4: Edit the category name with an only-space new name");
        String oldName = "Bread";
        String newName = "         ";
        Menu instance = new Menu();
        instance.addCategory(oldName);
        instance.editCategory(oldName, newName);
    }

    @Test(expected = GeneralException.class)
    public void testEditCategory5() throws Exception {
        System.out.println("Test method editCategory 5: Edit the name of unexisted category");
        String oldName = "Bread";
        String newName = "Burger";
        Menu instance = new Menu();
        instance.editCategory(oldName, newName);
    }

    /**
     * Test of findItem method, of class Menu.
     */
    @Test
    public void testFindItem1() throws DuplicateException, InvalidInputException, GeneralException {
        System.out.println("Test method findItem 1: Find item by name");
        String name = "Category";
        String itemName = "Bread";
        Menu instance = new Menu();
        instance.addCategory(name);
        instance.addItem(itemName, 100, name);
        Item result = instance.findItem(itemName);
        assertTrue(result != null);
    }

    @Test
    public void testFindItem2() throws DuplicateException, InvalidInputException, GeneralException {
        System.out.println("Test method findItem 2: Find unexisted item by name");
        String name = "Category";
        String itemName = "Bread";
        Menu instance = new Menu();
        instance.addCategory(name);
        Item result = instance.findItem(name);
        assertTrue(result == null);
    }

    /**
     * Test of addItem method, of class Menu.
     */
    @Test
    public void testAddItem1() throws Exception {
        System.out.println("Test method addItem 1: Add item into category");
        String name = "Category";
        String itemName = "Bread";
        long price = 100;
        Menu instance = new Menu();
        instance.addCategory(name);
        instance.addItem(itemName, price, name);
        Item result = instance.findItem(itemName);
        assertTrue(result != null);
    }

    @Test(expected = GeneralException.class)
    public void testAddItem2() throws Exception {
        System.out.println("Test method addItem 2: Add item into unexisted category");
        String name = "Category";
        String itemName = "Bread";
        long price = 100;
        Menu instance = new Menu();
        instance.addItem(itemName, price, name);
    }

    @Test(expected = DuplicateException.class)
    public void testAddItem3() throws Exception {
        System.out.println("Test method addItem 3: Add duplicate item in a category into that category");
        String name = "Category";
        String itemName = "Bread";
        long price = 100;
        Menu instance = new Menu();
        instance.addCategory(name);
        instance.addItem(itemName, price, name);
        instance.addItem(itemName, price, name);
    }

    @Test(expected = DuplicateException.class)
    public void testAddItem4() throws Exception {
        System.out.println("Test method addItem 4: Add duplicate item in a category into another category");
        String name = "Category";
        String name1 = "Another category";
        String itemName = "Bread";
        long price = 100;
        Menu instance = new Menu();
        instance.addCategory(name);
        instance.addCategory(name1);
        instance.addItem(itemName, price, name);
        instance.addItem(itemName, price, name1);
    }

    /**
     * Test of removeItem method, of class Menu.
     */
    @Test
    public void testRemoveItem1() throws Exception {
        System.out.println("Test method removeItem 1: Remove item from menu");
        String name = "Category";
        String itemName = "Bread";
        long price = 100;
        Menu instance = new Menu();
        instance.addCategory(name);
        instance.addItem(itemName, price, name);
        instance.removeItem(itemName);
        Item result = instance.findItem(itemName);
        assertTrue(result==null);
    }

    @Test(expected = GeneralException.class)
    public void testRemoveItem2() throws Exception {
        System.out.println("Test method removeItem 2: Remove unexisted item from menu");
        String name = "Category";
        String itemName = "Bread";
        long price = 100;
        Menu instance = new Menu();
        instance.addCategory(name);
        instance.removeItem(itemName);
    }

    @Test(expected = GeneralException.class)
    public void testRemoveItem3() throws Exception {
        System.out.println("Test method removeItem 3: Remove ordered item from menu");
        String name = "Category";
        String itemName = "Bread";
        long price = 100;
        Menu instance = new Menu();
        instance.addCategory(name);
        instance.addItem(itemName, price, name);
        instance.findItem(itemName).increaseNum();
        instance.removeItem(itemName);
    }
}
