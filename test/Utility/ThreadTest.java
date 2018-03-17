/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Utility;

import java.util.GregorianCalendar;
import javax.swing.JOptionPane;

/**
 *
 * @author $Le Hoang Hai
 */
public class ThreadTest extends Thread  {
public ThreadTest() {

    }

    public void run() {
        while (true) {
            try {

                this.sleep(1000);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }
}
