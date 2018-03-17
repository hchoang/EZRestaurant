package restaurant.model.facade;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import restaurant.controller.RestaurantController;
import restaurant.model.*;
import restaurant.model.exception.GeneralException;

public interface RestaurantModel {

    public ArrayList<Table> getTableList();

    public ArrayList<User> getAccounts();

    public ArrayList<Category> getCategoryList();

    public void setController(RestaurantController controller);

    public void logIn(String username, String pass) throws Exception;

    public void logOut();

    public User getCurrent();

    public String checkType(String username);

    public void addUser(String type, String username, String password, String confirm, String name, int age, boolean gender, String email) throws GeneralException, FileNotFoundException, IOException;

    public User getUser(String username);

    public void activeUser(String username) throws Exception;

    public void deactiveUser(String username) throws Exception;

    public void editUser(String oldUser, String type, String username, String password,
            String confirm, String name, int age, boolean gender, String email) throws Exception;

    public void addTable(Location pos, String name) throws Exception;

    public String generateTable() throws Exception;

    public void moveAndResizeTable(String tableName, Location posStart, int width, int height) throws Exception;

    public Table getTable(String name) throws Exception;

    public Table getTable(Location pos) throws Exception;

    public void modifyTable(String oldName, String newName) throws Exception;

    public void removeTable(String tableName) throws Exception;

    public void addItemOrder(String tableName, String nameItem, int quantity, String note) throws Exception;

    public void removeItemOrder(String tableName, Item item) throws Exception;

    public long calculateOrder(String tableName) throws Exception;

    public void completeOrder(String tableName) throws Exception;

    public void addCategory(String name) throws Exception;

    public void editCategory(String oldName, String newName) throws Exception;

    public void removeCategory(String categoryName) throws Exception;

    public Category getCategory(String categoryName) throws Exception;

    public Category searchCategory(String itemName) throws Exception;

    public void addItem(String name, long price, String categoryName) throws Exception;

    public Item getItem(String item) throws Exception;

    public void editItem(String itemName, String newName, long price) throws Exception;

    public void removeItem(String name) throws GeneralException, FileNotFoundException, IOException;

    public void saveUser() throws Exception;

    public void loadUser() throws Exception;

    public void saveTable() throws Exception;

    public void loadTable() throws Exception;

    public void saveOrder() throws Exception;

    public void loadOrder() throws Exception;

    public void saveMenu() throws Exception;

    public void loadMenu() throws Exception;

    public void printKitchenOrder(String tableName) throws Exception;

    public void printBill(String tableName) throws Exception;

    public String getAddress();

    public void setAddress(String address) throws Exception;

    public String getPhone();

    public void setPhone(String phone) throws Exception;

    public String getRestaurantName();

    public void setRestaurantName(String restaurantName) throws Exception;

    public void exportOrder() throws Exception;

    public void importOrder() throws Exception;

    public ArrayList<Order> searchOrder(String keyword, GregorianCalendar startDate, GregorianCalendar endDate) throws Exception;

    public HashMap<String, Long> revenueReport(GregorianCalendar startDate, GregorianCalendar endDate) throws Exception;

    public void printRevenueReport(GregorianCalendar startDate, GregorianCalendar endDate) throws Exception;

    public LinkedHashMap<String, Long> timeReport(GregorianCalendar startDate, GregorianCalendar endDate) throws Exception;

    public void printTimeReport(GregorianCalendar startDate, GregorianCalendar endDate) throws Exception;

    public Order getOrder(String id) throws Exception;

    public boolean isTest();

    public void setTest(boolean test);

    public void saveOldOrder(Order order) throws Exception;

    public boolean checkManager(String username, String pass) throws Exception;

    public void setSpecialDiscount(double specialDiscount, String tableName) throws Exception;

    public double getNormalDiscount() throws Exception;

    public int getMaxTable();

    public void setMaxTable(int maxNumber) throws Exception;

    public void setAccounts(ArrayList<User> accounts);

    public void setMenu(Menu menu);

    public void setOrders(LinkedList<Order> orders);

    public void setTableList(ArrayList<Table> tableList);
    
    public void setLanguage(String language, String country) throws Exception;

    public int compareDate(GregorianCalendar date, GregorianCalendar compareDate);

    public void setDiscount(double normalDiscount, GregorianCalendar start, GregorianCalendar end) throws Exception;

    public void applyDiscount() throws Exception;

    public String getCountry();
    
    public String getLanguage();

    public GregorianCalendar getStartDiscount();

    public GregorianCalendar getEndDiscount();

    public int getPrinterType();

    public void setPrinterType(int printerType);

    public void changeTable(Table current, Table later);
    
    public void saveInfo() throws FileNotFoundException, IOException;
}
