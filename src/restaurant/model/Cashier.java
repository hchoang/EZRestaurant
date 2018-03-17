package restaurant.model;

import java.util.*;
import java.util.regex.Pattern;
import javax.swing.text.Position;
import restaurant.model.exception.*;

public class Cashier extends User {

    /**
     * Constructor with username, password and relevant info of the user using
     * the constructor of class <code>User</code>
     *
     * @param username username to login the account
     * @param password password to login the account
     * @param name name of the user
     * @param age age of the user
     * @param gender true is male, false is Female
     * @param email email to contact the user of the account
     */
    
    
    public Cashier(String username, String password, String name, int age, boolean gender, String email) {
        super(username, password, name, age, gender, email);
    }

    /**
     * Add a table
     * 
     * @param tableList the list of table
     * @param pos location to put the table
     * @param name the name of the table
     * @throws GeneralException when two table overlap each other or name is already existed
     */
    public void addTable(ArrayList<Table> tableList, Location pos, String name, int maxTable) throws GeneralException {
        Table table = new Table(pos, name);
       if (tableList.size()>=maxTable){
                throw new GeneralException("<html><b>Too many table !</b> Number of tables is equal or already exceeds maximun table allow");
       }

        for (int i = 0; i < tableList.size(); i++) {
            if (!(table.checkTable(tableList.get(i)) && tableList.get(i).checkTable(table))) {
                throw new GeneralException("<html><b>Overlapping!</b> Can not add table there.");
            }
        }
        if (searchTable(tableList, name) != null) {
            throw new GeneralException("<html><b>Name is already existed</b>");
        }
        if (!checkTableName(name)) {
            throw new GeneralException("<html><b>Invalid name:</b>\n Name of the table"
                    + " contains only letters, numbers, dash (-) and underscore (_)");
        }
        tableList.add(table);
    }

    /**
     * Get a table with a name
     *
     * @param tableList list of table
     * @param name the name to identify the table
     * @return the object <code>Table</code>
     * @throws GeneralException
     */
    public Table searchTable(ArrayList<Table> tableList, String name) throws GeneralException {
        for (int i = 0; i < tableList.size(); i++) {
            if (tableList.get(i).getName().equals(name)) {
                return tableList.get(i);
            }
        }
        return null;
    }

    /**
     * Get a table with start position
     *
     * @param tableList list of table
     * @param pos the location of the table
     * @return the Table in that position
     */
    public Table getTable(ArrayList<Table> tableList, Location pos) {
        for (int i = 0; i < tableList.size(); i++) {
            if (tableList.get(i).getStart().equals(pos)) {
                return tableList.get(i);
            }
        }
        return null;
    }

    /**
     * Modify the name of the table
     *
     * @param tableList list of table
     * @param oldName the original name of the table
     * @param newName the new name of the table
     * @throws GeneralException when table is not existed
     */
    public void modifyTable(ArrayList<Table> tableList, String oldName, String newName) throws GeneralException {
        if (searchTable(tableList, newName) != null && !newName.equals(oldName)) {
            throw new DuplicateException("The new name is already existed");
        } else {
            if (checkTableName(newName)) {
                Table table = searchTable(tableList, oldName);
                if (table != null) {
                    table.setName(newName);
                } else {
                    throw new GeneralException("The table does not exist");
                }
            } else {
                throw new GeneralException("<html><b>Invalid name:</b>\nName of the table"
                        + " contains more than one letters, numbers, dash (-), underscore (_) and space"
                        + "(not only spaces in the name)");
            }
        }
    }

    /**
     * Validate the name of the table. The email should contain character letter
     * (a-z and A-Z), number, dash (-), underscore (_) and space( ).
     *
     * @param name name of the table
     * @return <code>true</code> when table name is valid, otherwise return <code>false</code>
     */
    private boolean checkTableName(String name) {
        return Pattern.matches("^[a-zA-Z0-9_ -]+$", name.trim());
    }

    /**
     * Move or resize the table
     *
     * @param tableList the list table in the restaurant
     * @param table the table want to move or resize
     * @param start start point of the table
     * @param width width of the table
     * @param height height of the table
     * @throws GeneralException when two table overlap each other
     */
    public void moveAndResizeTable(ArrayList<Table> tableList, String tableName, Location start, int width, int height) throws GeneralException {
        Table temp = new Table(start, "");
        Location end = new Location(start.getX() + width, start.getY() + height);
        temp.setEnd(end);

        for (int i = 0; i < tableList.size(); i++) {
            if (!tableList.get(i).getName().equals(tableName)) {
                if (!(tableList.get(i).checkTable(temp) && temp.checkTable(tableList.get(i)))) {
                    throw new GeneralException("The tables are overlap each other! ");
                }
            }
        }
        Table table = searchTable(tableList, tableName);
        table.setHeight(height);
        table.setWidth(width);
        table.setStart(start);
        table.setEnd(end);
    }

    /**
     * Remove a table
     *
     * @param tableList the list of table
     * @param tableName table name
     */
    public void removeTable(ArrayList<Table> tableList, String tableName) throws GeneralException {
        for (int i = 0; i < tableList.size(); i++) {
            if (tableList.get(i).getName().equals(tableName)) {
                tableList.remove(i);
                return;
            }
        }
        throw new GeneralException("Table does not exist");
    }

    /**
     * Add item into current order of the table. If there is no order, the function will
     * create a new order and add item into it. When the item is already in the
     * order, the function will modify the information.
     *
     * @param orders list of orders
     * @param table the table
     * @param item the item want to add
     * @param quantity the quantity for the item in the order
     * @param note the note for that item in the order
     */
    public void addItemOrder(LinkedList<Order> orders, Table table, Item item, int quantity, String note, double normal) throws GeneralException {
        if (table.getCurrentOrder() == null) {
            Order order;
            if (orders.size() == 0) {
                order = new Order(0 + "",normal);
            } else {
                order = new Order((Integer.parseInt(orders.get(orders.size() - 1).getId()) + 1) + "",normal);
            }
            orders.addLast(order);
            table.setCurrentOrder(order);
        }
        table.getCurrentOrder().addOrderItem(item, quantity, note);
    }

    /**
     * Remove the item from order
     *
     * @param tableList the list of table
     * @param table the table
     * @param item the item want to delete
     */
    public void removeItemOrder(ArrayList<Table> tableList, Table table, Item item) throws GeneralException {
        table.getCurrentOrder().removeOrderItem(item);
    }

    /**
     * Calculate the money for the order
     *
     * @param tableList the list of table
     * @param table the table
     * @return total money of the order
     */
    public long calculateOrder(Table table) {
        Order order = table.getCurrentOrder();
        return order.calculateOrder();
    }

    /**
     * Check out the order
     *
     * @param tableList the list of table
     * @param table the table
     */
    public void completeOrder(ArrayList<Table> tableList, Table table) {
        Order order = table.getCurrentOrder();
        for (int i = order.getOrderItems().size() - 1; i >= 0; i--) {
            order.getOrderItems().get(i).getItem().decreaseNum();
        }
        order.setFinished(true);
        table.setCurrentOrder(null);
    }

    /**
     * Search a category contains the item
     *
     * @param menu the menu of item
     * @param itemName name of the item
     * @return the category contains that item
     */
    public Category searchCategory(Menu menu, String itemName) {
        return menu.searchCategory(itemName);
    }

    /**
     * Get the category with the name
     *
     * @param menu the menu of item
     * @param categoryName name of the category
     * @return the category with that name
     */
    public Category getCategory(Menu menu, String categoryName) {
        return menu.getCategory(categoryName);
    }

    /**
     * Get item with the name
     *
     * @param menu the menu of items
     * @param itemName name of the item
     * @return the Item have that name
     * @throws GeneralException
     */
    public Item getItem(Menu menu, String itemName) {
        return menu.findItem(itemName);
    }
}
