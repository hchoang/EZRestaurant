/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.model.facade;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.regex.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import restaurant.controller.RestaurantController;
import restaurant.model.exception.*;
import restaurant.model.*;

public class RestaurantEngine implements RestaurantModel {

    public final String ADMIN = "ADMIN";
    public final String CASHIER = "CASHIER";
    public final String MANAGER = "MANAGER";
    public static final String VI_LANGUAGE = "vi";
    public static final String VI_COUNTRY = "VI";
    public static final String EN_LANGUAGE = "en";
    public static final String EN_COUNTRY = "EN";
    private String restaurantName;
    private String address;
    private String phone;
    private int maxTable;
    private int paperSize;
    private Menu menu = new Menu();
    private ArrayList<Table> tableList = new ArrayList<Table>();
    private LinkedList<Order> orders = new LinkedList<Order>();
    private ArrayList<User> accounts = new ArrayList<User>();
    private User current;
    private boolean login;
    private RestaurantController controller;
    private boolean testUnit;
    private double normalDiscount;
    private GregorianCalendar startDiscount, endDiscount;
    // variable for translating language
    private static Locale currentLocale = new Locale("vi", "VI");
//    private static Locale currentLocale = new Locale("en", "EN");
    public static ResourceBundle translate = ResourceBundle.getBundle("properties.DataTranslateBundle", currentLocale);
    /** FileFilter for CSV file */
    private FileFilter csvFilter = new FileFilter() {

        private final String csv = "csv";

        // Accept only the CSV file
        public boolean accept(File pathname) {
            if (pathname.isDirectory()) {
                return true;
            }

            String ext = getExtension(pathname);
            if (ext != null) {
                return ext.equals(this.csv);
            }

            return false;
        }

        public String getDescription() {
            return "All CSV file (*.csv)";
        }

        private String getExtension(File f) {
            String ext = null;
            String s = f.getName();
            int i = s.lastIndexOf('.');

            if (i > 0 && i < s.length() - 1) {
                ext = s.substring(i + 1).toLowerCase();
            }

            return ext;
        }
    };

    public RestaurantEngine() throws Exception {
        login = false;
        controller = null;
        testUnit = false;
        normalDiscount = 0;
        File directory = new File("data");
        directory.mkdir();
        File languageFile;

        if (testUnit) {
            languageFile = new File("data/languageTest.dat");
        } else {
            languageFile = new File("data/language.dat");
        }
        if (!languageFile.exists()) {
            saveLanguage();
        }

        loadLanguage();
        translate.getBundle("properties.DataTranslateBundle", currentLocale);
    }

    public RestaurantEngine(RestaurantController controller) {
        this.controller = controller;
    }

    /**
     * Log in into the program. All data are loaded from files
     *
     * @param username the username of the user
     * @param pass password of the user
     * @throws Exception
     */
    public void logIn(String username, String pass) throws Exception {
        checkFile();
        loadUser();
        loadInfo();
        loadTable();
        if (accounts.isEmpty()) {
            Admin default_admin = new Admin("admin", "admin", "", 20, true, "");
            accounts.add(default_admin);
        }
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getUsername().equals(username)) {
                if (accounts.get(i).isActive() == true) {
                    if (accounts.get(i).getPassword().equals(pass)) {
                        current = accounts.get(i);
                        if (!(current instanceof Admin)) {
                            loadMenu();
                            loadOrder();
                            DiscountThread discount = new DiscountThread(this);
                            discount.start();
                        }
                        return;
                    }
                    throw new GeneralException(translate.getString("WRONG-PASSWORD"));
                }
                throw new GeneralException(translate.getString("USERNAME-HAS-BEEN-DEACTIVATED"));
            }
        }
        throw new GeneralException(translate.getString("USERNAME-DOES-NOT-EXIST"));

    }

    private void validateTable(int maxTable) throws Exception {
        int num = 0;
        ArrayList<Table> pos = new ArrayList<Table>();
        for (int i = 0; i < tableList.size(); i++) {
            if (tableList.get(i).getCurrentOrder() == null) {
                pos.add(tableList.get(i));
                num++;
            }
        }
        System.out.println(num + "-" + (tableList.size() - maxTable));
        if ((tableList.size() - num) <= maxTable) {
            for (int i = 0; i < num; i++) {
                System.out.println(tableList.size() <= maxTable);
                if (tableList.size() <= maxTable) {
                    break;
                }
                System.out.println(tableList.remove(pos.get(i)));
            }
        } else {
            throw new GeneralException("Too many occupied table to free !!!");
        }
        saveTable();
    }

    /**
     * Log out from system
     */
    public void logOut() {
        if (login == true) {
            login = false;
            current = null;
        }
    }

    /**
     * Check if required files are existed or not. If not, create new file
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void checkFile() throws FileNotFoundException, IOException {
        File directory = new File("data");
        directory.mkdir();
        File userFile;
        File menuFile;
        File tableFile;
        File orderFile;
        File infoFile;

        if (testUnit) {
            userFile = new File("data/userTest.dat");
            menuFile = new File("data/menuTest.dat");
            tableFile = new File("data/tableTest.dat");
            orderFile = new File("data/orderTest.dat");
            infoFile = new File("data/infoTest.dat");
        } else {
            userFile = new File("data/user.dat");
            menuFile = new File("data/menu.dat");
            tableFile = new File("data/table.dat");
            orderFile = new File("data/order.dat");
            infoFile = new File("data/info.dat");
        }
        if (!userFile.exists()) {
            saveUser();
        }
        if (!menuFile.exists()) {
            saveMenu();
        }
        if (!tableFile.exists()) {
            saveTable();
        }
        if (!orderFile.exists()) {
            saveOrder();
        }
        if (!infoFile.exists()) {
            saveInfo();
        }
    }

    /**
     * Add table into the program
     *
     * @param pos position of the table
     * @param name name of the table
     * @throws Exception
     */
    public void addTable(Location pos, String name) throws Exception {

        if (current instanceof Cashier) {
            ((Cashier) current).addTable(tableList, pos, name, maxTable);

            saveTable();

            if (controller != null) {
                controller.resetGeneralView();
            }
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    /**
     * Return the available number (name) for a table
     */
    public String generateTable() throws Exception {
        int i = 1;
        while (true) {
            if (getTable(i + "") == null) {
                break;
            }
            i++;
        }
        return i + "";
    }

    /**
     * Get the table with the name
     * 
     * @param name name of table
     * @return <code>Table</code> has the name or null 
     * if there is no table with that name
     * @throws Exception
     */
    public Table getTable(String name) throws Exception {
        if (current instanceof Cashier) {
            return ((Cashier) current).searchTable(tableList, name);
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    /**
     * Get table with the location
     *
     * @param posStart the start point of the table
     * @return the table in that position
     */
    public Table getTable(Location posStart) throws Exception {
        if (current instanceof Cashier) {
            for (int i = 0; i < tableList.size(); i++) {
                if (tableList.get(i).getStart().equals(posStart)) {
                    return tableList.get(i);
                }
            }
            return null;
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    /**
     * Move or resize the table
     *
     * @param tableName name of the table
     * @param posStart the start point of the table
     * @param width width of the table
     * @param height height of the table
     * @throws Exception
     */
    public void moveAndResizeTable(String tableName, Location posStart,
            int width, int height) throws Exception {

        if (current instanceof Cashier) {
            ((Cashier) current).moveAndResizeTable(
                    tableList, tableName, posStart, width, height);

            saveTable();

        } else {
            throw new PermissionException(translate.getString("Permissin Denied!"));
        }
    }

    /**
     * Modify the name of the table
     *
     * @param oldName the original name of the table
     * @param newName the new name of the table
     * @throws Exception
     */
    public void modifyTable(String oldName, String newName) throws Exception {
        if (current instanceof Cashier) {
            ((Cashier) current).modifyTable(tableList, oldName, newName);

            saveTable();

        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    /**
     * Remove the table 
     * 
     * @param tableName table name
     * @throws Exception
     */
    public void removeTable(String tableName) throws Exception {
        if (current instanceof Cashier) {
            ((Cashier) current).removeTable(tableList, tableName);

            saveTable();

        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    /**
     * Add item into order
     * 
     * @param tableName name of the table
     * @param itemName item of the item
     * @param quantity quantity of the item
     * @param note the note for item in the order
     * @throws Exception
     */
    public void addItemOrder(String tableName, String nameItem,
            int quantity, String note)
            throws Exception {
        if (current instanceof Cashier) {

            Item menuItem = getItem(nameItem);

            ((Cashier) current).addItemOrder(
                    orders, getTable(tableName), menuItem, quantity, note, normalDiscount);
            saveOrder();
            saveMenu();
            saveTable();

        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    /**
     * Remove item in order
     *
     * @param table the table has the order
     * @param item
     * @throws Exception
     */
    public void removeItemOrder(String tableName, Item item) throws Exception {
        if (current instanceof Cashier) {
            Item menuItem = getItem(item.getName());
            ((Cashier) current).removeItemOrder(tableList, getTable(tableName), item);
            saveOrder();
            saveMenu();
            saveTable();
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    /**
     * Calculate the total price in the order
     *
     * @param table the table
     * @return the total price for the order of the table
     * @throws PermissionException
     */
    public long calculateOrder(String tableName) throws Exception {
        if (current instanceof Cashier) {
            return ((Cashier) current).calculateOrder(getTable(tableName));
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    /**
     * Check out the order
     *
     * @param table the table has the order
     * @throws Exception
     */
    public void completeOrder(String tableName) throws Exception {
        if (current instanceof Cashier) {
            if (((Cashier) current).getTable(tableList,
                    getTable(tableName).getStart()) != null) {
                ((Cashier) current).completeOrder(tableList, getTable(tableName));
            }
            saveOrder();
            saveTable();
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    /**
     * Add new category
     * @param name name of the category
     * @throws Exception
     */
    public void addCategory(String name) throws Exception {
        if (current instanceof Manager) {
            ((Manager) current).addCategory(menu, name);
            saveMenu();
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    /**
     * Edit name of the category
     *
     * @param oldName the original name of the category
     * @param newName the new name of the category
     * @throws Exception
     */
    public void editCategory(String oldName, String newName) throws Exception {
        if (current instanceof Manager) {
            ((Manager) current).editCategory(menu, oldName, newName);
            saveMenu();
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    /**
     * Remove category with the name
     *
     * @param categoryName name of the category
     * @throws Exception
     */
    public void removeCategory(String categoryName) throws Exception {
        if (current instanceof Manager) {
            ((Manager) current).removeCategory(menu, categoryName);
            saveMenu();
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    /**
     * Search category contains item
     *
     * @param itemName name of the item
     * @return <code>Category</code> contains that item
     * @throws Exception
     */
    public Category searchCategory(String itemName) throws Exception {
        if (current instanceof Manager) {
            return ((Manager) current).searchCategory(menu, itemName);
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    public Category getCategory(String categoryName) throws Exception {
        if (current instanceof Cashier) {
            return ((Cashier) current).getCategory(menu, categoryName);
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    /**
     * Add new item into menu
     *
     * @param name name of the item
     * @param price price of the item
     * @param categoryName name of the category
     * @throws Exception
     */
    public void addItem(String name, long price, String categoryName)
            throws Exception {
        if (current instanceof Manager) {
            ((Manager) current).addItem(menu, name, price, categoryName);
            saveMenu();
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    /**
     * Edit the name and price of the item
     *
     * @param itemName name of the item
     * @param newName new name of the item
     * @param price new price of the item
     * @throws Exception
     */
    public void editItem(String itemName, String newName, long price)
            throws Exception {
        if (current instanceof Manager) {
            ((Manager) current).editItem(menu, itemName, newName, price);
            saveMenu();
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    /**
     * remove item with the name
     *
     * @param name name of the Item
     * @throws Exception
     */
    public void removeItem(String name)
            throws GeneralException, FileNotFoundException, IOException {
        if (current instanceof Manager) {
            ((Manager) current).removeItem(menu, name);
            saveMenu();
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    /**
     * Get item with the name
     *
     * @param itemName name of the item
     * @return <code>Item</code> has that name
     * @throws Exception
     */
    public Item getItem(String itemName) throws Exception {
        if (current instanceof Cashier) {
            return ((Cashier) current).getItem(menu, itemName);
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    /**
     * Check type of the account
     *
     * @param username username of account
     * @return ADMIN for admin, CASHIER for cashier and MANAGER for manager
     */
    public String checkType(String username) {
        User user = null;
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getUsername().equals(username)) {
                user = accounts.get(i);
            }
        }
        if (user != null) {
            if (user instanceof Admin) {
                return ADMIN;
            } else if (user instanceof Manager) {
                return MANAGER;
            } else {
                return CASHIER;
            }
        }
        return translate.getString("INVALID");
    }

    /**
     * Validate information of the account
     *
     * @param username username of the account
     * @param password password of the account
     * @param confirm confirm password
     * @param name name of the user
     * @param email email of the user
     * @throws GeneralException
     */
    private void validateInfo(String username, String password, String confirm,
            String name, String email)
            throws GeneralException {
        if (!((Admin) current).checkUsername(username)) {
            throw new GeneralException("<html><b>" + translate.getString("Invalid-username!") + "</b>"
                    + "\n" + translate.getString("Username-should-only-contains-one-or-more-lowercase")
                    + " " + translate.getString("letter-(a-z),-number,-dash-(-)-and-underscore-(_)"));
        }

        if (!((Admin) current).checkPassword(password)) {
            throw new GeneralException("<html><b>" + translate.getString("Invalid-password!") + "</b>\n"
                    + translate.getString("Password-should-only-contains") + " "
                    + translate.getString("six-or-more-letter-(a-z-and-A-Z),") + " "
                    + translate.getString("number,-dash-(-)-and-underscore-(_)"));
        }

        if (!password.equals(confirm)) {
            throw new GeneralException("<html><b>" + translate.getString("Invalid-confirm-password!") + "</b>"
                    + "\n" + translate.getString("Confirm-password-should-match-with-the-password"));
        }

        if (!((Admin) current).checkName(name)) {
            throw new GeneralException("<html><b>" + translate.getString("Invalid-fullname!") + "</b>\n"
                    + translate.getString("Fullname-should-contain-at-least-two-words"));
        }

        if (!((Admin) current).checkEmail(email)) {
            throw new GeneralException("<html><b>" + translate.getString("Invalid-email!") + "</b>\n"
                    + translate.getString("Email-should-contain-character-'@'-and-domain"));
        }
    }

    /**
     * Add new users
     *
     * @param type type of the account
     * @param username username of the account
     * @param password password of the account
     * @param confirm confirm password
     * @param name name of the user
     * @param age  age of the user
     * @param gender true is male, false is female
     * @param email email of the user
     * @return true if successful, otherwise false
     * @throws GeneralException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void addUser(String type, String username, String password,
            String confirm, String name, int age, boolean gender, String email)
            throws GeneralException, FileNotFoundException, IOException {
        User newUser;

        if (current instanceof Admin) {

            validateInfo(username, password, confirm, name, email);

            if (type.equals(translate.getString(ADMIN))) {
                newUser = new Admin(username, password, name,
                        age, gender, email);

            } else {

                if (type.equals(translate.getString(CASHIER))) {
                    newUser = new Cashier(username, password, name,
                            age, gender, email);

                } else {
                    newUser = new Manager(username, password, name,
                            age, gender, email);
                }

            }

            ((Admin) current).addUser(newUser, accounts);
            saveUser();
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    /**
     * Activate the user
     *
     * @param username username of the account
     * @throws Exception
     */
    public void activeUser(String username) throws Exception {
        if (current instanceof Admin) {
            ((Admin) current).activateUser(username, accounts);
            saveUser();
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    /**
     * Deactivate the user
     *
     * @param username username of the account
     * @throws Exception
     */
    public void deactiveUser(String username) throws Exception {
        if (current instanceof Admin) {
            if (!username.equals("admin")) {
                ((Admin) current).deactivateUser(username, accounts);
                saveUser();
            } else {
                throw new PermissionException(translate.getString("Default-admin-cannot-be-deactivated"));
            }
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    /**
     * Edit information of the account
     *
     * @param oldUser the original username
     * @param username the new username of the accounts
     * @param password the password of the accounts
     * @param confirm confirm password
     * @param name the name of the user
     * @param age the age of the user
     * @param gender true is male, false is female
     * @param email the email of the user
     * @throws Exception
     */
    public void editUser(String oldUser, String type, String username, String password,
            String confirm, String name, int age, boolean gender, String email)
            throws Exception {
        if (current instanceof Admin) {
            if ((oldUser.equals("admin") && (username.equals("admin") || type.equals("ADMIN")))
                    || !oldUser.equals("admin")) {
                User user = ((Admin) current).findUser(oldUser, accounts);
                if (user != null) {
                    validateInfo(username, password, confirm, name, email);
                    if (type.equals(checkType(user.getUsername()))) {
                        ((Admin) current).editUser(user, username, password,
                                confirm, name, age, gender, email);
                    } else {
                        for (int i = 0; i < accounts.size(); i++) {
                            if (accounts.get(i).getUsername().equals(oldUser)) {
                                accounts.remove(i);
                                break;
                            }
                        }
                        addUser(type, username, password, confirm, name, age, gender, email);
                    }
                    saveUser();
                } else {
                    throw new GeneralException(translate.getString("User is not exist"));
                }
            } else {
                if (!username.equals("admin")) {
                    throw new PermissionException(translate.getString("USERNAME OF DEFAULT ADMIN ")
                            + translate.getString("CANNOT BE EDITED"));
                } else {
                    throw new PermissionException(translate.getString("TYPE OF DEFAULT ADMIN ")
                            + "CANNOT BE EDITED");
                }
            }
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    /**
     * Save user list into "user.dat" file
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void saveUser() throws FileNotFoundException, IOException {
        ObjectOutputStream oos;
        if (!testUnit) {
            oos = new ObjectOutputStream(new FileOutputStream("data/user.dat"));
        } else {
            oos = new ObjectOutputStream(
                    new FileOutputStream("data/userTest.dat"));
        }
        oos.writeObject(accounts);
        oos.close();
    }

    /**
     * Load user list from "user.dat" file
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void loadUser() throws IOException, ClassNotFoundException {
        ObjectInputStream ois;
        if (!testUnit) {
            ois = new ObjectInputStream(new FileInputStream("data/user.dat"));
        } else {
            ois = new ObjectInputStream(
                    new FileInputStream("data/userTest.dat"));
        }
        accounts = (ArrayList<User>) (ois.readObject());
        ois.close();
    }

    /**
     * Save table list into "table.dat" file
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void saveTable() throws FileNotFoundException, IOException {
        ObjectOutputStream oos;
        if (!testUnit) {
            oos = new ObjectOutputStream(new FileOutputStream("data/table.dat"));
        } else {
            oos = new ObjectOutputStream(
                    new FileOutputStream("data/tableTest.dat"));
        }
        oos.writeObject(tableList);
        oos.close();
    }

    /**
     * Load table list from "table.dat" file
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void loadTable() throws IOException, ClassNotFoundException {
        ObjectInputStream ois;
        if (!testUnit) {
            ois = new ObjectInputStream(new FileInputStream("data/table.dat"));
        } else {
            ois = new ObjectInputStream(
                    new FileInputStream("data/tableTest.dat"));
        }
        tableList = (ArrayList<Table>) (ois.readObject());
        ois.close();
    }

    /**
     * Save order list into "order.dat" file
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void saveOrder() throws FileNotFoundException, IOException {
        ObjectOutputStream oos;
        if (!testUnit) {
            oos = new ObjectOutputStream(new FileOutputStream("data/order.dat"));
        } else {
            oos = new ObjectOutputStream(
                    new FileOutputStream("data/orderTest.dat"));
        }
        oos.writeObject(orders);
        oos.close();
    }

    /**
     * Load order list from "order.dat" file
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void loadOrder() throws IOException, ClassNotFoundException {
        ObjectInputStream ois;
        if (!testUnit) {
            ois = new ObjectInputStream(new FileInputStream("data/order.dat"));
        } else {
            ois = new ObjectInputStream(
                    new FileInputStream("data/orderTest.dat"));
        }
        orders = (LinkedList<Order>) (ois.readObject());
        ois.close();
    }

    /**
     * Save menu into "menu.dat" file
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void saveMenu() throws FileNotFoundException, IOException {
        ObjectOutputStream oos;
        if (!testUnit) {
            oos = new ObjectOutputStream(new FileOutputStream("data/menu.dat"));
        } else {
            oos = new ObjectOutputStream(
                    new FileOutputStream("data/menuTest.dat"));
        }

        oos.writeObject(menu);
        oos.close();
    }

    /**
     * Load menu from "menu.dat" file
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void loadMenu() throws IOException, ClassNotFoundException {
        ObjectInputStream ois;
        if (!testUnit) {
            ois = new ObjectInputStream(new FileInputStream("data/menu.dat"));
        } else {
            ois = new ObjectInputStream(
                    new FileInputStream("data/menuTest.dat"));
        }
        menu = (Menu) (ois.readObject());
        ois.close();
    }

    /**
     * Save order list into "order.dat" file
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void saveInfo() throws FileNotFoundException, IOException {
        ObjectOutputStream oos;
        if (!testUnit) {
            oos = new ObjectOutputStream(new FileOutputStream("data/info.dat"));
        } else {
            oos = new ObjectOutputStream(
                    new FileOutputStream("data/infoTest.dat"));

        }
        ArrayList<Object> info = new ArrayList<Object>();
        info.add(restaurantName);
        info.add(address);
        info.add(phone);
        info.add(maxTable + "");
        info.add(normalDiscount + "");
        info.add(startDiscount);
        info.add(endDiscount);
        info.add(paperSize + "");
        oos.writeObject(info);
        oos.close();
    }

    /**
     * Load order list from "order.dat" file
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void loadInfo() throws IOException, ClassNotFoundException {
        ObjectInputStream ois;
        if (!testUnit) {
            ois = new ObjectInputStream(new FileInputStream("data/info.dat"));
        } else {
            ois = new ObjectInputStream(
                    new FileInputStream("data/infoTest.dat"));
        }
        ArrayList<Object> info = new ArrayList<Object>();
        info = (ArrayList<Object>) (ois.readObject());
        if (info != null && info.size() == 8) {
            restaurantName = (String) info.get(0);
            address = (String) info.get(1);
            phone = (String) info.get(2);
            maxTable = Integer.parseInt((String) info.get(3));
            normalDiscount = Double.parseDouble((String) info.get(4));
            startDiscount = (GregorianCalendar) info.get(5);
            endDiscount = (GregorianCalendar) info.get(6);
            paperSize = Integer.parseInt((String) info.get(7));
        } else {
            restaurantName = "Default Restaurant Name";
            address = "Default Address";
            phone = "0123456789";
            maxTable = 100;
            normalDiscount = 0;
            startDiscount = null;
            endDiscount = null;
            paperSize = 58;
        }

        ois.close();
    }

    public void saveLanguage() throws FileNotFoundException, IOException {
        ObjectOutputStream oos;
        if (!testUnit) {
            oos = new ObjectOutputStream(new FileOutputStream("data/language.dat"));
        } else {
            oos = new ObjectOutputStream(
                    new FileOutputStream("data/languageTest.dat"));

        }
        ArrayList<Locale> array = new ArrayList<Locale>();
        array.add(currentLocale);
        oos.writeObject(array);
        oos.close();
    }

    /**
     * Load order list from "order.dat" file
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void loadLanguage() throws IOException, ClassNotFoundException {
        ObjectInputStream ois;
        if (!testUnit) {
            ois = new ObjectInputStream(new FileInputStream("data/language.dat"));
        } else {
            ois = new ObjectInputStream(
                    new FileInputStream("data/languageTest.dat"));
        }
        ArrayList<Locale> array = (ArrayList<Locale>) ois.readObject();
        if (array.size() != 0) {
            currentLocale = array.get(0);
        } else {
            currentLocale = new Locale("", "");
        }
        translate = ResourceBundle.getBundle("properties/DataTranslateBundle", currentLocale);
        ois.close();
    }

    /**
     * Print the current order in the table
     * @param tableName name of the table
     * @throws Exception
     */
    public void printKitchenOrder(String tableName) throws Exception {
        if (getTable(tableName) != null) {
            System.out.println(paperSize);
            getTable(tableName).printerKitchenOrder(normalDiscount, paperSize);
        }
    }

    /**
     * Print the bill
     * @param tableName name of the table
     * @throws Exception
     */
    public void printBill(String tableName) throws Exception {
        if (getTable(tableName) != null) {
            ArrayList<String> resInfo = new ArrayList<String>();
            resInfo.add(restaurantName + "");
            resInfo.add(address + "");
            resInfo.add(phone + "");
            getTable(tableName).printerBill(resInfo, normalDiscount, paperSize);
        }
        saveTable();
        saveOrder();
    }

    /**
     * Get user with the name
     * @param username the username of the account
     * @return
     */
    public User getUser(String username) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getUsername().equals(username)) {
                return accounts.get(i);
            }
        }
        return null;
    }

    /**
     * Accessors to current user
     * @return <code>User</code> that login into the program
     */
    public User getCurrent() {
        return current;
    }

    /**
     * Accessors to the list of accounts
     * @return <code>ArrayList</code> of accounts
     */
    public ArrayList<User> getAccounts() {
        return accounts;
    }

    /**
     * Accessors to the list of the category
     * @return <code>ArrayList</code> of the category
     */
    public ArrayList<Category> getCategoryList() {
        return menu.getCategoryList();
    }

    /**
     * Accessors to the list of the table
     * @return <code>HashMap</code> of the table
     */
    public ArrayList<Table> getTableList() {
        return tableList;
    }

    public void setController(RestaurantController controller) {
        this.controller = controller;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) throws Exception {
        if (current instanceof Admin) {
            this.address = address;
            saveInfo();
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) throws Exception {
        if (current instanceof Admin) {
            if (Pattern.matches("^[0-9]{9,12}", phone)) {
                this.phone = phone;
                saveInfo();
            } else {
                throw new PermissionException(translate.getString("Invalid-phone-number"));
            }
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName)
            throws Exception {
        if (current instanceof Admin) {
            this.restaurantName = restaurantName;
            saveInfo();
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    public void exportOrder() throws Exception {
        if (current instanceof Manager) {
            LinkedList<Order> orderArray = orders;
            JFrame frame = new JFrame();
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(csvFilter);
            for (int i = 0; i < orderArray.size(); i++) {
                if (orderArray.get(i).isFinished() == false) {
                    orderArray.remove(i);
                    i--;
                }
            }
            if (!testUnit) {
                if (fc.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    long start = System.currentTimeMillis();
                    File selFile = fc.getSelectedFile();
                    ((Manager) current).exportOrder(selFile, orderArray);
                    long end = System.currentTimeMillis();
                    double time = (end - start) / 1000.0;
                    JOptionPane.showMessageDialog(null, translate.getString("Export successfully in ") + time
                            + translate.getString("s."), translate.getString("Success"),
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                File selFile = new File("data/testCSV.csv");
                ((Manager) current).exportOrder(selFile, orderArray);
            }
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    public void importOrder() throws Exception {
        if (current instanceof Manager) {
            JFrame frame = new JFrame();
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(csvFilter);
            if (!testUnit) {
                if (fc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    File selFile = fc.getSelectedFile();
                    long start = System.currentTimeMillis();
                    ((Manager) current).importOrder(selFile, orders);
                    long end = System.currentTimeMillis();
                    double time = (end - start) / 1000.0;
                    JOptionPane.showMessageDialog(null, translate.getString("Import successfully in ") + time + "s.", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                File selFile = new File("data/testCSV.csv");
                ((Manager) current).importOrder(selFile, orders);
            }
            saveOrder();
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    public LinkedList<Order> getOrders() {
        return orders;
    }

    public int compareDate(GregorianCalendar date, GregorianCalendar compareDate) {
        if (date.get(GregorianCalendar.YEAR) == compareDate.get(GregorianCalendar.YEAR)) {
            if (date.get(GregorianCalendar.DAY_OF_YEAR) == compareDate.get(GregorianCalendar.DAY_OF_YEAR)) {
                return 0;
            } else {
                if (date.get(GregorianCalendar.DAY_OF_YEAR) < compareDate.get(GregorianCalendar.DAY_OF_YEAR)) {
                    return -1;
                } else {
                    return 1;
                }
            }
        } else {
            if (date.get(GregorianCalendar.YEAR) > compareDate.get(GregorianCalendar.YEAR)) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    private LinkedList<Order> findOrderByDate(GregorianCalendar startDate, GregorianCalendar endDate) {
        LinkedList<Order> listOrder = new LinkedList<Order>();
        if (startDate == null && endDate == null) {
            listOrder = orders;
        } else {
            for (int i = 0; i < orders.size(); i++) {
                if (orders.get(i).isFinished()) {
                    GregorianCalendar date = orders.get(i).getTimeStamp();

                    if (startDate != null && endDate != null) {
                        if (compareDate(startDate, date) <= 0 && compareDate(endDate, date) >= 0) {
                            listOrder.add(orders.get(i));
                        }
                    } else {
                        if (startDate != null) {
                            if (compareDate(startDate, date) <= 0) {
                                listOrder.add(orders.get(i));
                            }
                        }
                        if (endDate != null) {
                            if (compareDate(endDate, date) >= 0) {
                                listOrder.add(orders.get(i));
                            }
                        }
                    }
                }
            }
        }
        return listOrder;
    }

    public ArrayList<Order> searchOrder(String keyword, GregorianCalendar startDate, GregorianCalendar endDate)
            throws PermissionException, GeneralException {
        if (current instanceof Manager) {
            LinkedList<Order> orderList = findOrderByDate(startDate, endDate);
            if (keyword.equals("")) {
                return new ArrayList<Order>();
            }
            return ((Manager) current).searchOrder(keyword, orderList);
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    public HashMap<String, Long> revenueReport(GregorianCalendar startDate, GregorianCalendar endDate) throws Exception {
        if (current instanceof Manager) {

            return ((Manager) current).revenueReport(startDate, endDate, orders);
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    public void printRevenueReport(GregorianCalendar startDate, GregorianCalendar endDate)
            throws Exception {
        if (current instanceof Manager) {

            ((Manager) current).printRevenueReport(startDate, endDate, orders);
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    public LinkedHashMap<String, Long> timeReport(GregorianCalendar startDate, GregorianCalendar endDate) throws Exception {
        if (current instanceof Manager) {
            return ((Manager) current).timeReport(startDate, endDate, orders);
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    public void printTimeReport(GregorianCalendar startDate, GregorianCalendar endDate) throws Exception {
        if (current instanceof Manager) {
            ((Manager) current).printTimeReport(startDate, endDate, orders);
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    public Order getOrder(String id) throws PermissionException {
        if (current instanceof Manager) {
            int numId;
            id = id.replaceAll("<html>", "").replaceAll("<b>", "").
                    replaceAll("</b>", "");
            if (!Pattern.matches("[0-9]+", id)) {
                throw new PermissionException(translate.getString("Invalid id order"));
            }
            numId = Integer.parseInt(id.trim());
            return orders.get(numId);
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    public boolean isTest() {
        return testUnit;
    }

    public void setTest(boolean test) {
        this.testUnit = test;
    }

    public void saveOldOrder(Order order) throws Exception {
        if (current instanceof Manager) {
            for (int i = 0; i < orders.size(); i++) {
                if (order.getId().equals(orders.get(i).getId())) {
                    orders.get(i).setManagerNote(order.getManagerNote());
                    saveOrder();
                }
            }
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    public void setAccounts(ArrayList<User> accounts) {
        this.accounts = accounts;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void setOrders(LinkedList<Order> orders) {
        this.orders = orders;
    }

    public void setTableList(ArrayList<Table> tableList) {
        this.tableList = tableList;
    }

    public void setMaxTable(int maxNumber) throws Exception {
        if (current instanceof Admin) {
            if (maxNumber > 0) {
                validateTable(maxNumber);
                this.maxTable = maxNumber;
            }
            saveInfo();
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    public int getMaxTable() {
        return maxTable;
    }

    public double getNormalDiscount() throws PermissionException {
        if (current instanceof Cashier) {
            return normalDiscount;
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    public void setDiscount(double normalDiscount, GregorianCalendar start, GregorianCalendar end) throws Exception {
        if (current instanceof Manager) {
            GregorianCalendar today = new GregorianCalendar();
            if (compareDate(start, today) >= 0 && compareDate(end, start) >= 0) {
                if (normalDiscount >= 0 && normalDiscount <= 0.5) {
                    startDiscount = start;
                    endDiscount = end;
                    this.normalDiscount = normalDiscount;
                } else {
                    throw new GeneralException(translate.getString("Normal discount should be only from 0% to 50%"));
                }
            } else {
                if (compareDate(start, today) == -1) {
                    throw new GeneralException(translate.getString("Start date of discount should be after or on today"));
                } else {
                    throw new GeneralException(translate.getString("End date of discount should be after or on start date"));
                }
            }
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    public void applyDiscount() throws Exception {
        if (current instanceof Cashier) {
            if (startDiscount != null && endDiscount != null) {
                GregorianCalendar today = new GregorianCalendar();
                if (compareDate(today, startDiscount) < 0 || compareDate(today, endDiscount) > 0) {
                    startDiscount = null;
                    endDiscount = null;
                    normalDiscount = 0;
                }
                for (int i = 0; i < tableList.size(); i++) {
                    Order order = tableList.get(i).getCurrentOrder();
                    if (order != null) {
                        order.setDiscount(normalDiscount);
                    }
                }
                saveOrder();
            }
        } else {
            throw new PermissionException(translate.getString("Permission-Denied!"));
        }
    }

    public void setSpecialDiscount(double specialDiscount, String tableName) throws Exception {
        if (specialDiscount >= 0 && specialDiscount <= 1) {
            if (getTable(tableName).getCurrentOrder() != null) {
                getTable(tableName).getCurrentOrder().setSpecial(specialDiscount);
                saveTable();
                saveOrder();
            } else {
                throw new GeneralException(translate.getString("Table is vacant!!! Can not edit special discount"));
            }
        } else {
            throw new GeneralException(translate.getString("Special discount should be only from 0% to 100%"));
        }
    }

    public boolean checkManager(String username, String pass) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getUsername().equals(username) && (accounts.get(i) instanceof Manager)) {
                if (accounts.get(i).isActive() == true) {
                    if (accounts.get(i).getPassword().equals(pass)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void setLanguage(String language, String country) throws Exception {
        if ((language.length() == 2 || language.isEmpty()) && (country.length() == 2 || country.isEmpty())) {
            currentLocale = new Locale(language, country);
            translate = ResourceBundle.getBundle("properties/DataTranslateBundle", currentLocale);
            saveLanguage();
        } else {
            throw new GeneralException("Language/Country code are not valid !!!!!");
        }
    }

    public String getLanguage() {
        return currentLocale.getLanguage();
    }

    public String getCountry() {
        return currentLocale.getCountry();
    }

    public GregorianCalendar getEndDiscount() {
        return endDiscount;
    }

    public GregorianCalendar getStartDiscount() {
        return startDiscount;
    }


    public void changeTable(Table current, Table later) {
        later.setCurrentOrder(current.getCurrentOrder());
        current.setCurrentOrder(null);

    }

    public int getPrinterType() {
        return paperSize;
    }

    public void setPrinterType(int printerType) {
        this.paperSize = printerType;
    }
}
