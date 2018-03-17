package restaurant.model;

import java.io.Serializable;
import java.text.*;
import java.util.*;
import restaurant.model.exception.GeneralException;

public class Order implements Serializable {

    private ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
    private GregorianCalendar timeStamp;
    private final String id;
    private String managerNote;
    private boolean finished;
    private double discount;
    private double special;

    public Order(String id, double normal) {
        timeStamp = new GregorianCalendar();
        finished = false;
        this.id = id;
        managerNote = "nothing";
        discount = normal;
        special = 0;
    }
    public Order(String id) {
        this.id = id;
        finished = false;
    }


    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public GregorianCalendar getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(GregorianCalendar timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getId() {
        return id;
    }

    /**
     * Accessors of list of items
     *
     * @return <code>ArrayList</code> of OrderItem.
     */
    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }

    /**
     * Find the order item in the order
     *
     * @param item item in the menu
     * @return <code>OrderItem</code> in the order or null if there are no item
     *          in order
     */
    public OrderItem findOrderItem(Item item) {
        for (int i = 0; i < orderItems.size(); i++) {
            
            if (orderItems.get(i).getItem().getName().equals(item.getName())) {
                return orderItems.get(i);
            }
        }
        return null;
    }

    /**
     * Add and modify item into order
     *
     * @param item the item in menu
     * @param quantity number of item in the order
     * @param note then note for the item
     */
    public void addOrderItem(Item item, int quantity, String note) throws GeneralException {
        OrderItem oItem;
        if (findOrderItem(item) == null) {
            oItem = new OrderItem(item, quantity, note);
            orderItems.add(oItem);
            item.increaseNum();
        } else {
            oItem = findOrderItem(item);
            oItem.setNote(note);
            oItem.setQuantity(quantity);
        }
    }

    /**
     * Remove item from the order
     *
     * @param item the item
     * @throws GeneralException when item is not existed
     */
    public void removeOrderItem(Item item) throws GeneralException {
        OrderItem oItem = findOrderItem(item);
        if (oItem != null) {
            oItem.getItem().decreaseNum();
            orderItems.remove(oItem);
        } else {
            throw new GeneralException("Item has not been ordered yet");
        }
    }

    /**
     * Calculate the total money of the order
     *
     * @return the money of the order
     */
    public long calculateOrder() {
        long total = 0;
        for (int i = 0; i < orderItems.size(); i++) {
            long price = orderItems.get(i).getItem().getPrice();
            int quantity = orderItems.get(i).getQuantity();
            total += price * quantity;
        }
        return total;
    }

    public long calculateVAT() {
        return (long) ((calculateOrder() - calculateDiscount()) * 10 / 100);
    }

    public long calculateDiscount() {
        return (long) (discount * calculateOrder());
    }

    public long calculateTotal() {
        return (long) ((calculateOrder() - calculateDiscount()) + calculateVAT());
    }

    public String getManagerNote() throws GeneralException {
        return managerNote;
    }

    public void setManagerNote(String managerNote) throws GeneralException {
        this.managerNote = managerNote;
    }

    @Override
    public String toString() {
        String Order = "\"" + id + "\",\"" + timeStamp.get(Calendar.HOUR_OF_DAY) + "\",\""
                + timeStamp.get(Calendar.DAY_OF_MONTH) + "/"
                + timeStamp.get(Calendar.MONTH) + "/"
                + timeStamp.get(Calendar.YEAR) + "\",\""
                + discount + "\",\""
                + orderItems.size() + "\",\"";

        Order += managerNote + "\"";
        return Order;
    }

    public void setDiscount(double normal) {
        discount = (normal > special) ? normal : special;
    }

    public void setSpecial(double special) throws GeneralException {
        if (special >= 0 && special <= 1) {
            this.special = special;
            discount = (discount > special) ? discount : special;
        } else {
            throw new GeneralException("Special discount should be only from 0% to 100%");
        }
    }
}
