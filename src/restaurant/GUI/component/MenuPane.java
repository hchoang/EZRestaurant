/*
 * Course name: Soft Eng: Process & Tools
 * Course ID: COSC2101
 * Team: Angry Eagles
 */
package restaurant.GUI.component;

/**
 * Create the menu view on the left of the GeneralView
 * 
 * @author Luan Nguyen Thanh & Cat Hoang Huy
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.swing.*;
import restaurant.GUI.GeneralView;
import restaurant.controller.*;
import restaurant.model.Category;
import restaurant.model.Item;
import restaurant.model.facade.RestaurantEngine;

public class MenuPane extends JPanel implements ActionListener {

    /** Check for Manager or Cashier */
    private boolean isManager;
    /** Controller */
    private RestaurantController controller;
    /** Input data from the Controller */
    private ArrayList<Category> categoryList;
    private ArrayList<Item> items;
    private Object[] itemAr;
    /** Layout */
    private GridBagConstraints gbc; // The main constraints
    private GridBagConstraints cons; // This one can be reused
    /** General Components that can be reused */
    private JLabel label;
    private JPanel itemPanel;
    private JPanel panel;
    private JPanel insidePanel;
    private JButton button;
    private JList itemsList;
    /** Core Components for this */
    private JScrollPane menuScrollPane;

     private static ResourceBundle rb;
    public MenuPane(RestaurantController controller,
            ArrayList<Category> categoryList, String type) {
        this.controller = controller;
        this.categoryList = categoryList;
        this.isManager = type.equalsIgnoreCase("MANAGER");
        rb = RestaurantEngine.translate;
        // Layout initializing

        this.setLayout(new BorderLayout());
        this.setBackground(new Color(253, 251, 183));

        setMenuToolBar();
        setMenuItem();
    }

    private void setMenuToolBar() {
        // Set the panel
        insidePanel = new JPanel(new BorderLayout(10, 30));
        insidePanel.setBackground(new Color(253, 251, 183));

        label = new JLabel(new ImageIcon(
                GeneralView.class.getResource("images/MenuLbl.png")));

        insidePanel.add(label, BorderLayout.WEST);

        // Do the second line
        // [+] | [Delete]
        // Exclude the Cashier
        if (isManager) {
            button = createButton("Add", "Add new",
                   rb.getString("Add new category")  + " (Ctrl + N)", "Add");
            button.addActionListener(new AddCategoryListener(controller, this));
            button.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).
                    put(KeyStroke.getKeyStroke(
                    KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK),
                    button.getActionCommand());
            button.getActionMap().put(
                    button.getActionCommand(), new AbstractAction() {

                public void actionPerformed(ActionEvent e) {
                    JButton b = (JButton) e.getSource();
                    b.doClick();
                }
            });
            button.setMargin(new Insets(0, 10, 0, 0));
            insidePanel.add(button, BorderLayout.EAST);
        }

        insidePanel.setSize(new Dimension(insidePanel.getWidth(), 50));

        this.add(insidePanel, BorderLayout.NORTH);
    }

    private void setMenuItem() {
        itemPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));

        panel = new JPanel(new GridBagLayout());
        cons = new GridBagConstraints();
        cons.anchor = GridBagConstraints.LINE_START;
        cons.insets = new Insets(2, 2, 2, 2);
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.gridx = 0;
        cons.gridy = -1;

        if (isManager) {
            for (Category c : categoryList) {
                cons.anchor = GridBagConstraints.LINE_START;
                cons.insets = new Insets(2, 2, 2, 2);

                cons.gridx = 0;
                cons.gridy++;
                JButton collapseBtn = new JButton();
                collapseBtn.setBorder(null);
                collapseBtn.setContentAreaFilled(false);
                collapseBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                collapseBtn.setActionCommand(c.getName());
                collapseBtn.addActionListener(this);
                panel.add(collapseBtn, cons);

                cons.gridx = 1;
                JButton categoryBtn = new JButton(c.getName());
                categoryBtn.setBackground(new Color(153, 200, 247));
                panel.add(categoryBtn, cons);

                JButton addItemBtn = createButton("Add item", "Add Item",
                       rb.getString("Add a new item to this category") , "Add Item");
                addItemBtn.addActionListener(
                        new ManagerListener(this, controller, c));
                cons.gridx = 2;
                panel.add(addItemBtn, cons);

                JButton editBtn = createButton("Edit item", "Edit Category",
                       rb.getString("Edit this category") , "Edit");
                editBtn.addActionListener(
                        new ManagerListener(this, controller, c));
                cons.gridx = 3;
                panel.add(editBtn, cons);

                JButton deleteBtn = createButton("Delete item","Delete Category",
                        rb.getString("Delete this category"), "Delete");
                deleteBtn.addActionListener(
                        new ManagerListener(this, controller, c));
                cons.gridx = 4;
                panel.add(deleteBtn, cons);

                items = c.getItemList();
                if (!c.isCollapse()) {
                    collapseBtn.setIcon(new ImageIcon(
                            GeneralView.class.getResource(
                            "images/Collapse.png")));

                    for (int i = 0; i < items.size(); i++) {
                        cons.anchor = GridBagConstraints.LINE_START;
                        cons.insets = new Insets(3, 30, 3, 3);

                        cons.gridy++;
                        cons.gridx = 1;
                        ItemButton itemBtn = new ItemButton(items.get(i));
                        panel.add(itemBtn, cons);

                        cons.insets = new Insets(0, 0, 0, 0);

                        JButton editItemBtn = createButton("Edit item","Edit Item",
                                rb.getString("Edit this item"), "Edit");
                        editItemBtn.addActionListener(
                                new ManagerListener(this, controller,
                                items.get(i), c));
                        cons.gridx = 2;
                        panel.add(editItemBtn, cons);

                        JButton deleteItemBtn = createButton("Delete item","Delete Item", 
                                rb.getString("Delete this item"), "Delete");
                        deleteItemBtn.addActionListener(
                                new ManagerListener(this, controller,
                                items.get(i), c));
                        cons.gridx = 3;
                        panel.add(deleteItemBtn, cons);
                    }
                } else {
                    collapseBtn.setIcon(new ImageIcon(
                            GeneralView.class.getResource("images/Close.png")));
                }
            }
        } else {
            for (Category c : categoryList) {
                cons.anchor = GridBagConstraints.LINE_START;
                cons.insets = new Insets(2, 2, 2, 2);

                cons.gridx = 0;
                cons.gridy++;
                JButton collapseBtn = new JButton();
                collapseBtn.setBorder(null);
                collapseBtn.setContentAreaFilled(false);
                collapseBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                collapseBtn.setActionCommand(c.getName());
                collapseBtn.addActionListener(this);
                panel.add(collapseBtn, cons);

                cons.gridx++;
                JButton categoryBtn = new JButton(c.getName());
                categoryBtn.setBackground(new Color(153, 200, 247));
                panel.add(categoryBtn, cons);

                items = c.getItemList();
                if (!c.isCollapse()) {
                    collapseBtn.setIcon(new ImageIcon(
                            GeneralView.class.getResource(
                            "images/Collapse.png")));

                    for (int i = 0; i < items.size(); i++) {
                        cons.anchor = GridBagConstraints.LINE_START;
                        cons.gridy++;
                        cons.gridx = 1;
                        cons.insets = new Insets(3, 30, 3, 3);

                        ItemButton itemBtn = new ItemButton(items.get(i));
                        panel.add(itemBtn, cons);
                    }
                } else {
                    collapseBtn.setIcon(new ImageIcon(
                            GeneralView.class.getResource("images/Close.png")));
                }
            }
        }

        panel.setBackground(new Color(253, 251, 183));
        itemPanel.setBackground(new Color(253, 251, 183));

        itemPanel.add(panel);

        menuScrollPane = new JScrollPane(itemPanel);

        menuScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        menuScrollPane.setWheelScrollingEnabled(true);

        this.add(menuScrollPane);
    }

    public void setCategoryList(ArrayList<Category> categoryList) {
        this.categoryList = categoryList;
    }

    /**
     * Create the button base on the inputs
     * 
     * @param image
     * @param cmd
     * @param toolTip
     * @param altText
     * @return 
     */
    private JButton createButton(String image, String cmd,
            String toolTip, String altText) {
        // Create the button
        JButton button = new JButton();
        button.setToolTipText(toolTip);
        button.setActionCommand(cmd);

        // Find the image icon
        URL normalImgURL = GeneralView.class.getResource("images/" + image + ".png");
        URL pressImgURL = GeneralView.class.getResource("images/" + image + "_Press.png");
        URL overImgURL = GeneralView.class.getResource("images/" + image + "_Over.png");

        if (normalImgURL != null
                && pressImgURL != null
                && overImgURL != null) {
            button.setIcon(new ImageIcon(normalImgURL, altText));
            button.setPressedIcon(new ImageIcon(pressImgURL));
            button.setRolloverIcon(new ImageIcon(overImgURL));
        } else {
            button.setText(altText);
        }

        button.setBorder(null);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    public void refresh() {

        this.removeAll();

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.anchor = GridBagConstraints.PAGE_START;
        //this.setBackground(Color.white);
        setMenuToolBar();
        setMenuItem();
        this.revalidate();
    }

    public void actionPerformed(ActionEvent e) {
        try {
            if (controller.getEZmodel().getCategory(e.getActionCommand()).
                    isCollapse()) {
                controller.getEZmodel().getCategory(e.getActionCommand()).
                        setCollapse(false);
            } else {
                controller.getEZmodel().getCategory(e.getActionCommand()).
                        setCollapse(true);
            }
            refresh();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), rb.getString("Error!"),
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
