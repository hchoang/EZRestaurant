/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import java.util.GregorianCalendar;
import java.util.Random;
import restaurant.model.Order;
import restaurant.model.facade.RestaurantEngine;

/**
 *
 * @author USER
 */
public class CreateCSV {

    public static void main(String[] args) throws Exception {
        RestaurantEngine r = new RestaurantEngine();
        r.saveInfo();
        r.saveMenu();
        r.saveOrder();
        r.saveUser();
        r.saveTable();
        r.logIn("admin", "admin");
        r.addUser("MANAGER", "manager", "Manager1", "Manager1", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
        r.addUser("CASHIER", "cashier", "Cashier1", "Cashier1", "Le Hoang Hai", 20, true, "s3298775@rmit.edu.vn");
        r.logIn("manager", "Manager1");
        r.addCategory("Drink");
        r.addItem("Coffee", 10000, "Drink");
        r.addItem("Coca", 5000, "Drink");
        r.addItem("Ice Tea", 1000, "Drink");
        GregorianCalendar date = new GregorianCalendar(2011, 11, 18);
        for (int i = 0; i < 10000; i++) {
            
            Random ran = new Random();
            double discount =ran.nextInt(51)/100.0;
            Order order = new Order(i + "",discount);
            int num = ran.nextInt(5) + 1;
            order.addOrderItem(r.getItem("Coffee"), num, "");
            num = ran.nextInt(5) + 1;
            order.addOrderItem(r.getItem("Coca"), num, "");
            num = ran.nextInt(5) + 1;
            order.addOrderItem(r.getItem("Ice Tea"), num, "");
            if (i % 1000 == 0) {
                date = new GregorianCalendar(date.get(GregorianCalendar.YEAR),
                        date.get(GregorianCalendar.MONTH), date.get(GregorianCalendar.DAY_OF_MONTH) + 1);
            }
            int hour=10;
            if (i % 1000 != 0) {
                hour = r.getOrders().get(i - 1).getTimeStamp().get(GregorianCalendar.HOUR_OF_DAY);
                int flag = ran.nextInt(100);
                if(flag==99 && hour < 21){
                    hour++;
                }
            }
            date=new GregorianCalendar(date.get(GregorianCalendar.YEAR),
                        date.get(GregorianCalendar.MONTH), date.get(GregorianCalendar.DAY_OF_MONTH), hour, 0);
            order.setTimeStamp(date);
            order.setFinished(true);
            r.getOrders().add(order);
        }
        r.exportOrder();
    }
}
