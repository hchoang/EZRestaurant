package restaurant.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import restaurant.model.exception.*;

public class Category implements Serializable {

    private ArrayList<Item> itemList = new ArrayList<Item>();
    private String name;
    private boolean collapse;

    /**
     * Constructor with the name of the category
     *
     * @param name name of the category
     */
    public Category(String name) {
        this.name = name;
        setCollapse(true);
    }

    public boolean isCollapse() {
        return collapse;
    }

    public void setCollapse(boolean collapse) {
        if (!itemList.isEmpty()) {
            this.collapse = collapse;
        } else {
            this.collapse = false;
        }
    }

    /**
     * Accessors to the name of category
     *
     * @return the name of category
     */
    public String getName() {
        return name;
    }

    /**
     * Mutators to the name of category
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Accessors to the list of item in category
     *
     * @return the list of item
     */
    public ArrayList<Item> getItemList() {
        return itemList;
    }

    /**
     * Add new item into category
     *
     * @param item the item want to add
     * @throws DuplicateException when item exists in the category
     */
    public void addItem(Item item) throws DuplicateException, InvalidInputException {
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).equals(item)) {
                throw new DuplicateException("Item is existed in this category already");
            }
        }
        if (item.getName().trim().equals("")) {
            throw new InvalidInputException("The name is empty. Please type again");
        }
        itemList.add(item);
    }

    /**
     * Remove item in the category
     *
     * @param item item want to delete
     * @throws GeneralException when item is already ordered
     */
    public void removeItem(Item item) throws GeneralException {
        if (item.getNumberOrder() == 0) {
            for (int i = 0; i < itemList.size(); i++) {
                if (itemList.get(i).getName().equals(item.getName())) {
                    itemList.remove(i);
                }
            }
        } else {
            throw new GeneralException("Can not delete item! Item is already ordered");
        }
    }

    /**
     * Find item in the category
     *
     * @param itemName the name of the item
     * @return the object <code>Item</code> that have the name
     */
    public Item findItem(String itemName) {
        for (int i = 0; i < itemList.size(); i++) {
            if (itemList.get(i).getName().equals(itemName)) {
                return itemList.get(i);
            }
        }
        return null;
    }

    /**
     * Edit information of the item
     *
     * @param item the item
     * @param name the new name of the item
     * @param price
     * @throws GeneralException
     */
    public void editItem(Item item, String name, long price) throws GeneralException {
        if(item == null)
            throw new GeneralException("Item does not exist");
        if (item.getNumberOrder() == 0) {
            item.setName(name);
            item.setPrice(price);
        } else {
            throw new GeneralException("Can not edit item! Item is already ordered");
        }

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Category other = (Category) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.name);
        return hash;
    }  
}
