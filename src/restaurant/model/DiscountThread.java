/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.model;

import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import restaurant.model.facade.*;

/**
 *
 * @author $Le Hoang Hai
 */
public class DiscountThread extends Thread {

    GregorianCalendar startDiscount, endDiscount;
    RestaurantEngine model;

    public DiscountThread(RestaurantEngine model) {
        this.model = model;
    }

    public void run() {
        while (true) {
            try {
                model.applyDiscount();
                this.sleep(1000);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }
}
