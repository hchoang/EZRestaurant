/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.GUI.component.inputFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.*;
import restaurant.GUI.component.MenuPane;
import restaurant.controller.RestaurantController;
import restaurant.model.Category;
import restaurant.model.facade.RestaurantEngine;

/**
 *
 * @author satthuvdh
 */
public class EditCategoryDialog extends JDialog implements ActionListener {

    private JLabel categoryNameLbl;
    private JTextField categoryNameTFl;
    private GridBagConstraints cons;
    private GridBagLayout layout;
    private Container cont;
    private JButton okBtn, cancelBtn;
    private JPanel buttonPanel;
    private RestaurantController controller;
    private MenuPane menuPane;
    private Category category;
    private static ResourceBundle rb = RestaurantEngine.translate;

    public EditCategoryDialog(RestaurantController controller, MenuPane menuPane, Category category) {
        rb = RestaurantEngine.translate;
        this.menuPane = menuPane;
        this.controller = controller;
        this.category = category;
        this.setTitle(rb.getString("Enter Category's Name"));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.getContentPane().setBackground(new Color(255, 223, 178));

        init();
        setLayout();
        addComponents();

        pack();
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
    }

    private void init() {
        categoryNameLbl = new JLabel(rb.getString("Category-Name"));
        categoryNameTFl = new JTextField(30);
        categoryNameTFl.setText(category.getName());
        okBtn = new JButton(rb.getString("Add"));
        okBtn.addActionListener(this);

        cancelBtn = new JButton(rb.getString("Cancel"));
        cancelBtn.addActionListener(this);
        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(okBtn);
        buttonPanel.add(cancelBtn);
        buttonPanel.setBackground(new Color(255, 223, 178));
    }

    private void setLayout() {
        layout = new GridBagLayout();
        cons = new GridBagConstraints();
        cont = this.getContentPane();
        this.setLayout(layout);
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.insets = new Insets(3, 3, 3, 3);
    }

    private void addComponents() {
        cons.gridx = 1;
        cons.gridy = 0;
        cont.add(categoryNameLbl, cons);

        cons.gridx = 2;
        cont.add(categoryNameTFl, cons);

        cons.gridx = 1;
        cons.gridy = 1;
        cons.gridwidth = 2;
        cont.add(buttonPanel, cons);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals(okBtn.getActionCommand())) {
            try {
                controller.getEZmodel().editCategory(category.getName(),
                        categoryNameTFl.getText());
                menuPane.refresh();
                this.dispose();
            } catch (Exception ex) {
                setAlwaysOnTop(false);
                JOptionPane.showMessageDialog(null, ex.getMessage(), rb.getString("Error!"),
                        JOptionPane.ERROR_MESSAGE);
                setAlwaysOnTop(true);
            }
        } else {
            this.dispose();
        }
    }
}
