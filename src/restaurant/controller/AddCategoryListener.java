/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import restaurant.GUI.component.MenuPane;
import restaurant.GUI.component.inputFrame.AddCategoryDialog;

/**
 *
 * @author satthuvdh
 */
public class AddCategoryListener implements ActionListener {

    private MenuPane menuPane;
    private RestaurantController controller;

    public AddCategoryListener(RestaurantController controller,
            MenuPane menuPane) {
        this.menuPane = menuPane;
        this.controller = controller;
    }

    public void actionPerformed(ActionEvent e) {
        AddCategoryDialog addCategory = new AddCategoryDialog(
                controller, menuPane);
    }
}
