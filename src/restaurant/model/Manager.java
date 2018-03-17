package restaurant.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Pattern;
import restaurant.model.exception.DuplicateException;
import restaurant.model.exception.GeneralException;
import restaurant.model.exception.InvalidInputException;

public class Manager extends Cashier {

    private boolean revenue;

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
    public Manager(String username, String password, String name, int age, boolean gender, String email) {
        super(username, password, name, age, gender, email);
    }

    /**
     * Add a category into menu
     *
     * @param menu the menu
     * @param name name of the category
     * @throws DuplicateException when there is a category with same name
     */
    public void addCategory(Menu menu, String name)
            throws DuplicateException, InvalidInputException {
        menu.addCategory(name);
    }

    /**
     * Remove the category
     * 
     * @param menu the menu
     * @param categoryName the name of category
     * @throws GeneralException when category is not existed
     */
    public void removeCategory(Menu menu, String categoryName) throws GeneralException {
        if (menu.getCategory(categoryName) != null) {
            menu.deleteCategory(menu.getCategory(categoryName));
        } else {
            throw new GeneralException("Category does not exist");
        }
    }

    /**
     * Add new item into menu
     *
     * @param menu the menu of item
     * @param name name of the item
     * @param price the price for the item
     * @param categoryName the category name of the item
     * @throws DuplicateException when there is a item in the menu with same name
     */
    public void addItem(Menu menu, String name, long price, String categoryName)
            throws DuplicateException, GeneralException, InvalidInputException {
        menu.addItem(name, price, categoryName);
    }

    /**
     * Remove item from the menu
     *
     * @param menu the menu of item
     * @param name name of the item
     * @throws GeneralException
     */
    public void removeItem(Menu menu, String name) throws GeneralException {
        menu.removeItem(name);
    }

    /**
     * Edit the name of the category
     *
     * @param menu menu of item
     * @param oldName the original name of the category
     * @param newName the new name of the category
     * @throws GeneralException when the new name is already existed
     */
    public void editCategory(Menu menu, String oldName, String newName)
            throws GeneralException {
        menu.editCategory(oldName, newName);
    }

    /**
     * Edit information of the item
     *
     * @param menu the menu of items
     * @param itemName the name of item
     * @param newName the new name for the item
     * @param price the new price for the item
     * @throws GeneralException when new name is existed or item does not exist
     */
    public void editItem(Menu menu, String itemName, String newName, long price)
            throws GeneralException {
        Category category = searchCategory(menu, itemName);
        if (category != null) {
            if (!itemName.equals(newName)) {
                for (int i = 0; i < menu.getCategoryList().size(); i++) {
                    if (menu.getCategoryList().get(i).findItem(newName) != null) {
                        throw new DuplicateException("New name of item is "
                                + "already exists in the category "
                                + menu.getCategoryList().get(i).getName());
                    }
                }
            }
            category.editItem(getItem(menu, itemName), newName, price);
        } else {
            throw new GeneralException("Item does not exist");
        }
    }

    public ArrayList<Order> searchOrder(String keyword, LinkedList<Order> orders) throws GeneralException {
        ArrayList<Order> result = new ArrayList<Order>();
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            String date = order.getTimeStamp().get(GregorianCalendar.DAY_OF_MONTH) + "/"
                    + order.getTimeStamp().get((GregorianCalendar.MONTH) + 1) + "/"
                    + order.getTimeStamp().get(GregorianCalendar.YEAR);

            if (!searchKeyword(order.getId(), keyword).equalsIgnoreCase(order.getId())
                    || !searchKeyword(order.getManagerNote(), keyword).equalsIgnoreCase(order.getManagerNote())
                    || !searchKeyword(date, keyword).equalsIgnoreCase(date)) {
                result.add(order);
                continue;
            }

            ArrayList<OrderItem> items = order.getOrderItems();
            for (int j = 0; j < items.size(); j++) {

                if (!searchKeyword(items.get(j).getNote(), keyword).equalsIgnoreCase(items.get(j).getNote())
                        || !searchKeyword(items.get(j).getQuantity() + "", keyword).equalsIgnoreCase(items.get(j).getQuantity() + "")) {
                    result.add(order);
                    break;
                }
                Item item = items.get(j).getItem();
                if (!searchKeyword(item.getName(), keyword).equalsIgnoreCase(item.getName())
                        || !searchKeyword(item.getPrice() + "", keyword).equalsIgnoreCase(item.getPrice() + "")) {
                    result.add(order);
                    break;
                }
            }
        }
        return result;
    }

    public String searchKeyword(String data, String keyword) {
        String result = data;
        data = data.toLowerCase();
        keyword = keyword.toLowerCase();
        String[] words = keyword.split(" ");
        boolean flag = false;
        for (int i = 0; i < words.length; i++) {
            if (data.indexOf(words[i]) != -1) {
                flag = true;
                result = result.replaceAll(words[i], "<b>" + words[i] + "</b>");
            }
        }
        if (flag) {
            result = "<html>" + result;
        }
        return result;
    }

    public void exportOrder(File file, LinkedList<Order> orders) throws Exception {
        String path = file.getAbsolutePath();
        if (file.getAbsolutePath().indexOf(".csv") == -1) {
            path += ".csv";
        }
        FileWriter fstream = new FileWriter(path);
        BufferedWriter out = new BufferedWriter(fstream);
        for (int i = 0; i < orders.size(); i++) {

            Order order = orders.get(i);
            out.write(order.toString());
            out.newLine();
            ArrayList<OrderItem> orderItems = order.getOrderItems();
            for (int j = 0; j < orderItems.size(); j++) {
                out.write(orderItems.get(j).toString());
                out.newLine();
            }
        }
        out.close();
    }

    public void importOrder(File file, LinkedList<Order> orders) throws Exception {
        String path = file.getAbsolutePath();
        if (file.getAbsolutePath().indexOf(".csv") == -1) {
            throw new GeneralException("Invalid CSV file!!! The chosen file is not .csv file");
        }
        FileInputStream fstream = new FileInputStream(file);
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        int k = 0;
        strLine = br.readLine();
        while (strLine != null) {

            ArrayList<String> result = splitStringinCSV(strLine);
            if (result.size() == 6) {
                Order order = new Order(result.get(0));
                if (checkDuplicateOrder(order, orders)) {
                    GregorianCalendar date = generateDate(result.get(2));
                    date.set(GregorianCalendar.MONTH, date.get(GregorianCalendar.MONTH) + 1);
                    int hour = Integer.parseInt(result.get(1));
                    date.set(GregorianCalendar.HOUR_OF_DAY, hour);
                    order.setTimeStamp(date);
                    order.setManagerNote(result.get(5));
                    int numItem = Integer.parseInt(result.get(4));
                    double discount = Double.parseDouble(result.get(3));
                    order.setDiscount(discount);
                    for (int i = 0; i < numItem; i++) {
                        if ((strLine = br.readLine()) != null) {

                            ArrayList<String> orderItems = splitStringinCSV(strLine);
                            Item item = new Item(orderItems.get(0),
                                    Long.parseLong(orderItems.get(1)));
                            if (orderItems.size() == 4) {
                                order.addOrderItem(item,
                                        Integer.parseInt(orderItems.get(2)),
                                        orderItems.get(3));
                            } else {
                                order.addOrderItem(item,
                                        Integer.parseInt(orderItems.get(2)),
                                        "");
                            }
                        }
                    }
                    order.setFinished(true);
                    orders.add(order);
                    strLine = br.readLine();
                } else {
                    int numItem = Integer.parseInt(result.get(4));
                    for (int i = 0; i < numItem; i++) {
                        strLine = br.readLine();
                    }
                    strLine = br.readLine();
                }
            } else {
                throw new GeneralException("Invalid CSV file!!! Format is wrong");
            }
        }
        in.close();
    }

    private GregorianCalendar generateDate(String dateString) throws GeneralException {
        String[] array = dateString.split("/");
        if (array.length == 3) {
            GregorianCalendar date = new GregorianCalendar(Integer.parseInt(array[2]),
                    Integer.parseInt(array[1]) - 1,
                    Integer.parseInt(array[0]));
            return date;
        } else {
            throw new GeneralException("Date error!!!");
        }
    }

    private boolean checkDuplicateOrder(Order order, LinkedList<Order> orders) {
        int id = 0;
        try {
            id = Integer.parseInt(order.getId());
        } catch (NumberFormatException nfe) {
            return false;
        }
        if (orders.size() > 0) {
            int first = 0;
            int upto = orders.size();
            while (first < upto) {
                int mid = (first + upto) / 2;
                int numMid = Integer.parseInt(orders.get(mid).getId());
                if (id < numMid) {
                    upto = mid;
                } else if (id > numMid) {
                    first = mid + 1;
                } else {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    private ArrayList<String> splitStringinCSV(String string) {
        String[] array = string.split(",");
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < array.length; i++) {
            String s = array[i];
            result.add(s.substring(1, s.length() - 1));
        }
        return result;
    }

    private String modifyStringInCSV(String s) {
        s = replaceStringAt(s, 0, 1, "");
        s = replaceStringAt(s, s.length() - 1, 1, "");
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '"' && s.charAt(i + 1) == '"') {
                s = replaceStringAt(s, i, 2, "\"");
                i = i + 1;
            }
        }
        return s;
    }

    private String replaceStringAt(String s, int from, int length, String c) {
        return s.substring(0, from) + c + s.substring(from + length);
    }

    private int compareDate(GregorianCalendar date, GregorianCalendar date1) {
        if (date.get(GregorianCalendar.DAY_OF_YEAR) == date1.get(GregorianCalendar.DAY_OF_YEAR)
                && date.get(GregorianCalendar.YEAR) == date1.get(GregorianCalendar.YEAR)) {
            return 0;
        } else {
            if (date.get(GregorianCalendar.YEAR) == date1.get(GregorianCalendar.YEAR)) {
                if (date.get(GregorianCalendar.DAY_OF_YEAR) < date1.get(GregorianCalendar.DAY_OF_YEAR)) {
                    return -1;
                } else {

                    return 1;
                }
            } else {
                if (date.get(GregorianCalendar.YEAR) > date1.get(GregorianCalendar.YEAR)) {
                    return 1;
                } else {
                    return -1;
                }
            }
        }
    }

    private ArrayList<Order> getOrdersByADate(GregorianCalendar date, LinkedList<Order> orders) {
        ArrayList<Order> ordersSearchFound = new ArrayList<Order>();
        int start = 0;
        int end = 0;
        int first = 0;
        int upto = orders.size() - 1;
        while (first < upto) {
            int mid = (first + upto) / 2;
            GregorianCalendar dateOrder = (GregorianCalendar) orders.get(mid).getTimeStamp();
            GregorianCalendar dateOrderBefore;

            if (mid != 0) {
                dateOrderBefore = (GregorianCalendar) orders.get(mid - 1).getTimeStamp();
            } else {
                dateOrderBefore = null;
            }

            if (compareDate(date, dateOrder) == -1) {
                upto = mid;
            } else if (compareDate(date, dateOrder) == 1) {
                first = mid + 1;
            } else {
                if (dateOrderBefore == null || compareDate(date, dateOrderBefore) == 1) {
                    start = mid;
                    break;
                } else {
                    upto = mid;
                }
            }
        }
        first = 0;
        upto = orders.size() - 1;
        while (first < upto) {
            int mid = (first + upto) / 2;
            GregorianCalendar dateOrder = (GregorianCalendar) orders.get(mid).getTimeStamp();
            GregorianCalendar dateOrderAfter;
            if (mid != orders.size() - 1) {
                dateOrderAfter = (GregorianCalendar) orders.get(mid + 1).getTimeStamp();
            } else {
                dateOrderAfter = null;
            }
            if (compareDate(date, dateOrder) == -1) {
                upto = mid;
            } else if (compareDate(date, dateOrder) == 1) {
                first = mid + 1;
            } else {
                if (dateOrderAfter == null || compareDate(date, dateOrderAfter) == -1) {
                    end = mid;
                    break;
                } else {
                    if (mid == orders.size() - 2) {
                        if (compareDate(date, dateOrder) == 0) {
                            end = mid + 1;
                            break;
                        } else {
                            end = mid;
                            break;
                        }
                    } else {
                        first = mid + 1;
                    }
                }
            }
        }
        if (start != end) {
            for (int i = start; i <= end; i++) {
                ordersSearchFound.add(orders.get(i));
            }
        } else {
            if (compareDate(date, orders.get(start).getTimeStamp()) == 0) {
                ordersSearchFound.add(orders.get(start));
            }
        }
        return ordersSearchFound;
    }

    private ArrayList<Order> searchOrdersByDate(GregorianCalendar startDate, GregorianCalendar endDate, LinkedList<Order> orders) throws Exception {

        ArrayList<Order> ordersSearchFound = new ArrayList<Order>();

        GregorianCalendar currDate = new GregorianCalendar(startDate.get(GregorianCalendar.YEAR),
                startDate.get(GregorianCalendar.MONTH), startDate.get(GregorianCalendar.DAY_OF_MONTH));

        int day = endDate.get(GregorianCalendar.DAY_OF_YEAR);
        int year = endDate.get(GregorianCalendar.YEAR);
        int j = 0;
        while (currDate.get(GregorianCalendar.DAY_OF_YEAR) <= day
                && currDate.get(GregorianCalendar.YEAR) <= year) {

            ordersSearchFound.addAll(getOrdersByADate(currDate, orders));
            currDate.set(GregorianCalendar.DAY_OF_YEAR, currDate.get(GregorianCalendar.DAY_OF_YEAR) + 1);
        }

        return ordersSearchFound;
    }

    private long revenueByDate(GregorianCalendar startDate, GregorianCalendar endDate, LinkedList<Order> orders) throws Exception {
        ArrayList<Order> ordersSearchFound = searchOrdersByDate(startDate, endDate, orders);
        long revenue = 0;
        for (int i = 0; i < ordersSearchFound.size(); i++) {
            revenue += ordersSearchFound.get(i).calculateOrder();
        }
        return revenue;
    }

    private long revenueByHour(int hour, GregorianCalendar startDate, GregorianCalendar endDate, LinkedList<Order> orders) throws Exception {
        ArrayList<Order> ordersSearchFound = searchOrdersByDate(startDate, endDate, orders);
        long revenue = 0;
        if (hour < (10 + 21) / 2) {
            for (int i = 0; i < ordersSearchFound.size(); i++) {
                Order order = ordersSearchFound.get(i);
                int num = order.getTimeStamp().get(GregorianCalendar.HOUR_OF_DAY);
                if (num == hour) {
                    revenue += order.calculateOrder();
                }
                if (num > hour) {
                    break;
                }
            }
        } else {
            for (int i = ordersSearchFound.size() - 1; i > 0; i--) {
                Order order = ordersSearchFound.get(i);
                int num = order.getTimeStamp().get(GregorianCalendar.HOUR_OF_DAY);
                if (num == hour) {
                    revenue += order.calculateOrder();
                }
                if (num < hour) {
                    break;
                }
            }
        }
        return revenue;
    }

    private HashMap<String, Long> revenueByItem(GregorianCalendar startDate, GregorianCalendar endDate, LinkedList<Order> orders) throws Exception {

        ArrayList<Order> ordersFound = searchOrdersByDate(startDate, endDate, orders);

        HashMap<String, Long> itemsRevenue = new HashMap<String, Long>();

        for (int j = 0; j < ordersFound.size(); j++) {
            Order order = ordersFound.get(j);
            
                    itemsRevenue.put(order.getId(), order.calculateTotal());
        }

        return sortHashMap(itemsRevenue);
    }

    private HashMap<String, Long> sortHashMap(HashMap<String, Long> input) {
        Map<String, Long> tempMap = new HashMap<String, Long>();
        for (String wsState : input.keySet()) {
            tempMap.put(wsState, input.get(wsState));
        }

        List<String> mapKeys = new ArrayList<String>(tempMap.keySet());
        List<Long> mapValues = new ArrayList<Long>(tempMap.values());
        HashMap<String, Long> sortedMap = new LinkedHashMap<String, Long>();
        TreeSet<Long> sortedSet = new TreeSet<Long>(mapValues);
        Object[] sortedArray = sortedSet.toArray();
        int size = sortedArray.length;
        for (int i = size - 1; i >= 0; i--) {
            sortedMap.put(mapKeys.get(mapValues.indexOf(sortedArray[i])),
                    (Long) sortedArray[i]);
        }
        return sortedMap;
    }

    private Long calReveItemInOrders(String itemCal, ArrayList<Order> ordersFound) {
        long revenueDay = 0;
        for (int j = 0; j < ordersFound.size(); j++) {
            Order order = ordersFound.get(j);
            for (int k = 0; k < order.getOrderItems().size(); k++) {
                OrderItem item = order.getOrderItems().get(k);
                if (itemCal.equals(item.getItem().getName())) {
                    revenueDay += item.calculatePrice();
                }
            }
        }
        return revenueDay;
    }

    public void printRevenueReport(GregorianCalendar startDate, GregorianCalendar endDate, LinkedList<Order> orders) throws Exception {
        HashMap<String, Long> itemsRevenue = revenueByItem(startDate, endDate, orders);
        long revenueDay = revenueByDate(startDate, endDate, orders);
        ArrayList<Long> revenueDaily = new ArrayList<Long>();
        revenueDaily.add(revenueDay);
        PrintReport print = new PrintReport(revenueDaily, itemsRevenue, startDate, endDate);
        print.printerRevenueReport();
    }

    public HashMap<String, Long> revenueReport(GregorianCalendar startDate, GregorianCalendar endDate, LinkedList<Order> orders) throws Exception {
        return revenueByItem(startDate, endDate, orders);
    }

    public void printTimeReport(GregorianCalendar startDate, GregorianCalendar endDate, LinkedList<Order> orders) throws Exception {
        int date = startDate.get(GregorianCalendar.DAY_OF_MONTH);
        int month = startDate.get(GregorianCalendar.MONTH);
        int year = startDate.get(GregorianCalendar.YEAR);
        int dateEnd = endDate.get(GregorianCalendar.DAY_OF_MONTH);
        int monthEnd = endDate.get(GregorianCalendar.MONTH);
        int yearEnd = endDate.get(GregorianCalendar.YEAR);
        Date date1 = startDate.getTime();
        Date date2 = endDate.getTime();
        HashMap<String, Long> itemsRevenue = revenueByItem(startDate, endDate, orders);

        long difference = Math.abs(date2.getTime() - date1.getTime());
        long days = difference / (1000 * 60 * 60 * 24) + 1;
        ArrayList<Long> revenueDaily = new ArrayList<Long>();
        GregorianCalendar day = new GregorianCalendar(year, month, date);

        for (int i = 0; i < days; i++) {
            revenueDaily.add(revenueByDate(day, day, orders));
        }
        GregorianCalendar dayStart = new GregorianCalendar(year, month, date);
        GregorianCalendar dayEnd = new GregorianCalendar(yearEnd, monthEnd, dateEnd);
        new PrintReport(revenueDaily, itemsRevenue, dayStart, dayEnd).printerTimeReport();
    }

    public LinkedHashMap<String, Long> timeReport(GregorianCalendar startDate, GregorianCalendar endDate, LinkedList<Order> orders) throws Exception {
        LinkedHashMap<String, Long> result = new LinkedHashMap<String, Long>();
        for (int hour = 10; hour < 22; hour++) {
            result.put(hour + ":00-" + hour + ":59", revenueByHour(hour, startDate, endDate, orders));
        }

        return result;
    }
}
