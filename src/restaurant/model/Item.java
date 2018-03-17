package restaurant.model;

import java.io.Serializable;
import java.util.Objects;
import restaurant.model.exception.GeneralException;

public class Item implements Serializable {

    private String name;
    private long price;
    private int numberOrder;// Number of orders have this item

    /**
     * Constructor with name and price of the item
     *
     * @param name name of the item
     * @param price price of the item (unit: VND)
     */
    public Item(String name, long price) throws GeneralException {
        this.name = name;
        if (!checkPrice(price)) {
            throw new GeneralException("Invalid price");
        }
        this.price = price;
        numberOrder = 0;
    }

    /**
     * Accessors of the name
     *
     * @return name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * Mutator of the name
     *
     * @param name new name for the item
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Accessors of the price
     *
     * @return price of the item
     */
    public long getPrice() {
        return price;
    }

    /**
     * Mutator of the price
     * 
     * @param price new price for the item
     */
    public void setPrice(long price) throws GeneralException {
        if (!checkPrice(price)) {
            throw new GeneralException("Invalid price");
        }
        this.price = price;
    }

    /**
     * Accessors of the numberOrder
     *
     * @return number of order have this item
     */
    public int getNumberOrder() {
        return numberOrder;
    }

    /**
     * Increase number of order by 1
     */
    public void increaseNum() {
        numberOrder++;
    }

    /**
     * Decrease number of order by 1
     */
    public void decreaseNum() {
        numberOrder--;
    }

    @Override
    public String toString() {
        return name + "\",\"" + price;
    }

    private boolean checkPrice(long price) {
        return price > 0.0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.name);
        return hash;
    }


}
