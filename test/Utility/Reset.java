/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Utility;

import java.io.FileNotFoundException;
import restaurant.model.facade.RestaurantEngine;

/**
 *
 * @author USER
 */
public class Reset {
    public static void main(String[] args) throws Exception {
        RestaurantEngine r = new RestaurantEngine();
        r.saveInfo();
        r.saveMenu();
        r.saveOrder();
        r.saveUser();
        r.saveTable();
        r.saveLanguage();
        r.logIn("admin", "admin");
        r.addUser("MANAGER", "manager", "Manager1", "Manager1", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
        r.addUser("CASHIER", "cashier", "Cashier1", "Cashier1", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
    }
}
