package restaurant.model;

import java.io.Serializable;
import restaurant.model.exception.GeneralException;

public class OrderItem implements Serializable {

    private Item item;
    private int quantity;
    private String note;

    /**
     * Constructor with item, quantity and note
     *
     * @param item the item in the menu
     * @param quantity the number of item
     * @param note the note for the item
     */
    public OrderItem(Item item, int quantity, String note) throws GeneralException {
        if(item==null)
            throw new GeneralException("Invalid Item! The item should not be null");
        this.item = item;
        if(quantity<=0)
            throw new GeneralException("Invalid quantity! The quantity must be at least 1!!");
        this.quantity = quantity;
        this.note = note;
    }

    /**
     * Accessors to the item
     *
     * @return the item
     */
    public Item getItem() {
        return item;
    }

    /**
     * Accessors to the note of the item in order
     *
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * Mutator to the note of the item in order
     *
     * @param note the new note of the item
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Accessors to the quantity
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Mutator to the quantity
     *
     * @param quantity the new quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double calculatePrice(){
        double result=0;
        result=this.quantity*this.getItem().getPrice();
        return result;
    }

    @Override
    public String toString() {
        return "\""+item.toString()+"\",\""+quantity+"\",\""+note+"\"";
    }
}
