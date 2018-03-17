/*
 * Course name: Soft Eng: Process & Tools
 * Course ID: COSC2101
 * Team: Angry Eagles
 */
package restaurant.GUI;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ResourceBundle;
import javax.swing.*;
import restaurant.GUI.component.Resizer;
import restaurant.controller.RestaurantController;
import restaurant.model.facade.RestaurantEngine;

/**
 * 
 * @author Luan, Nguyen Thanh - S3312335
 */
public abstract class RestaurantView extends JFrame {

    protected RestaurantController controller;
    /** This will help to appear the names on the top right */
    protected String username;
    protected String userFullname;
    /** Determine which kind of user to be served */
    protected String type;
    /** General Components that can be reused */
    private Resizer resizer;
    private JLabel label;
    private JMenuItem menuItem;
    private JPanel panel;
    private JPanel insidePanel;
    private JButton button;
    /** Core Components for this view */
    private JToolBar toolBar;
    private JMenuBar menuBar;
    private JMenu menu;
    private JSplitPane splitPane;
    private JPanel menuPane;
    private JPanel tablePane;
    private JScrollPane menuScrollPane;
    private JScrollPane tableScrollPane;
    private JTextField searchTextField;
    private static ResourceBundle rb;

    public RestaurantView(RestaurantController controller, String username, String userFullname, String type) throws HeadlessException {
        this.controller = controller;
        this.username = username;
        this.userFullname = userFullname;
        this.type = type;
        rb = RestaurantEngine.translate;

        // Frame initializing
        setTitle(rb.getString("EZRestaurant - ") + type + " - " + username);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        init();
    }
    
    private void init() {
        menuBar = setMenuBar();
        this.setJMenuBar(menuBar);
    }

    /**
     * Creating the MenuBar for this createView
     * Base on the GUI design, the MenuBar is the same within two user kinds.
     */
    private JMenuBar setMenuBar() {
        // Create the MenuBar
        JMenuBar menuBar = new JMenuBar();

        // Re-align the MenuBar to right-most aligning
        menuBar.add(Box.createHorizontalGlue());

        // Put the name of the user in
        if (userFullname.equals("")) {
            label = new JLabel(username + "  ");
        } else {
            label = new JLabel(userFullname + " (" + username + ")  ");
        }
        label.setFont(new java.awt.Font("Tahoma", Font.BOLD, 14));
        menuBar.add(label);

        // Put the gear button menu next to the label
        URL imageURL = GeneralView.class.getResource("images/Gear.png");
        if (imageURL != null) {
            ImageIcon icon = new ImageIcon(imageURL);

            menu = new JMenu();
            menu.setIcon(icon);
        }
        menuBar.add(menu);

        // Put menuItems in menu
        // There will be 3 menuItems: Help | Sign Out | Exit
        menuItem = new JMenuItem(rb.getString("Help"), KeyEvent.VK_H);
        imageURL = GeneralView.class.getResource("images/Help.png");
        menuItem.setIcon(new ImageIcon(imageURL));
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        menuItem.setActionCommand("Help");
        menuItem.addActionListener(controller);
        menu.add(menuItem);

        menu.insertSeparator(1);
        menuItem = new JMenuItem(rb.getString("Sign Out"), KeyEvent.VK_O);
        imageURL = GeneralView.class.getResource("images/SignOut.png");
        menuItem.setIcon(new ImageIcon(imageURL));
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
                ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("Sign Out");
        menuItem.addActionListener(controller);
        menu.add(menuItem);

        menu.insertSeparator(3);
        menuItem = new JMenuItem(rb.getString("Exit"), KeyEvent.VK_X);
        imageURL = GeneralView.class.getResource("images/Exit.png");
        menuItem.setIcon(new ImageIcon(imageURL));
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
                ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("Exit");
        menuItem.addActionListener(controller);
        menu.add(menuItem);

        // Put the menuBar into the Frame
        menuBar.setBackground(Color.white);

        return menuBar;
    }

    public void refresh() {
        rb = RestaurantEngine.translate;
        setJMenuBar(null);
        init();
    }
}
