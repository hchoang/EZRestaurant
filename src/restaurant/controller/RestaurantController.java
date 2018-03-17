/*
 * Course name: Soft Eng: Process & Tools
 * Course ID: COSC2101
 * Team: Angry Eagles
 */
package restaurant.controller;

/**
 * 
 * @author Luan, Nguyen Thanh - S3312335
 */
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import restaurant.GUI.*;
import restaurant.GUI.component.TableReport;
import restaurant.GUI.component.inputFrame.AddDiscount;
import restaurant.GUI.component.inputFrame.LoginDialog;
import restaurant.GUI.component.inputFrame.SearchOrder;
import restaurant.model.facade.RestaurantModel;

public class RestaurantController extends MouseAdapter implements ActionListener {

    /** Apply MVC */
    private RestaurantModel EZmodel;
    private RestaurantView EZview;
    private static LoginDialog loginDialog;
    /** The current state of the application */
    public static boolean isRunning;


    public RestaurantController() {
        isRunning = false;
        this.logIn();
    }

    public RestaurantModel getEZmodel() {
        return EZmodel;
    }

    public RestaurantView getEZview() {
        return EZview;
    }

    /**
     * Open the login dialog and log in
     */
    private void logIn() {

        loginDialog = new LoginDialog(this);
        
    }

    public void createView(String userType) {
        String username = EZmodel.getCurrent().getUsername();
        String userFullName = EZmodel.getCurrent().getName();

        if (userType.equals("ADMIN")) {
            EZview = new AdminView(this, username, userFullName, userType);
        } else {
            EZview = new GeneralView(this, username, userFullName, userType);
        }
    }

    public void resetGeneralView() {
        if (EZview instanceof GeneralView) {
            EZview.repaint();
        }
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equals("Log in")) {
            loginDialog.setModel();
            this.EZmodel = loginDialog.getModel();

            if (EZmodel != null && EZmodel.getCurrent()!=null) {
                isRunning = true;
                loginDialog.dispose();          
                createView(EZmodel.checkType(EZmodel.getCurrent().getUsername()));
            }
        } else if (cmd.equals("Cancel")) {
            System.exit(0);
        } else if (cmd.equals("Exit")) {
            System.exit(0);
        } else if (cmd.equals("Sign Out")) {
            EZmodel.logOut();
            EZview.dispose();
            logIn();
            isRunning = false;
        } else if (cmd.equals("Help")) {
            try {
                File directory = new File("UserGuide/UserGuide.html");
                Desktop desktop = Desktop.getDesktop();
                desktop.open(directory);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (cmd.equals("Import")) {
            try {
                EZmodel.importOrder();
            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
//                        JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } else if (cmd.equals("Export")) {
            try {
                EZmodel.exportOrder();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (cmd.equals("Report")) {
            TableReport report = new TableReport(EZmodel);
        } else if (cmd.equals("Search")) {
            SearchOrder searchOrder = new SearchOrder(this);
            searchOrder.setVisible(true);
        } else if (cmd.equals("Discount")) {
            AddDiscount addDiscount = new AddDiscount(this, false);
        }
    }
}
