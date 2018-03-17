/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import restaurant.GUI.component.MenuPane;
import restaurant.GUI.component.inputFrame.AddItemDialog;
import restaurant.GUI.component.inputFrame.EditCategoryDialog;
import restaurant.GUI.component.inputFrame.EditItemDialog;
import restaurant.model.Category;
import restaurant.model.Item;

/**
 *
 * @author satthuvdh
 */
public class ManagerListener implements ActionListener {

    private MenuPane menuPane;
    private RestaurantController controller;
    private Category category;
    private Item item;

    public ManagerListener(MenuPane menuPane, RestaurantController controller,
            Category categoryName) {
        this.menuPane = menuPane;
        this.controller = controller;
        this.category = categoryName;
    }

    public ManagerListener(MenuPane menuPane, RestaurantController controller,
            Item itemName, Category category) {
        this.menuPane = menuPane;
        this.controller = controller;
        this.item = itemName;
        this.category = category;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("Add Item")) {
            JDialog addItem = new AddItemDialog(controller, menuPane, category);
        }
        if (ae.getActionCommand().equals("Edit Category")) {
            JDialog editCategory = new EditCategoryDialog(controller, menuPane,category);
        }
        if (ae.getActionCommand().equals("Delete Category")) {
            try {
                controller.getEZmodel().removeCategory(category.getName());
                menuPane.refresh();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        if (ae.getActionCommand().equals("Delete Item")) {
            try {
                controller.getEZmodel().removeItem(item.getName());
                menuPane.refresh();
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            
        }
        if (ae.getActionCommand().equals("Edit Item")) {
            JDialog editItem = new EditItemDialog(controller, menuPane, item,
                    category);
        }
    }
}
