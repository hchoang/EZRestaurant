/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.GUI.component.inputFrame;

import java.awt.*;
import java.awt.event.*;
import java.util.ResourceBundle;
import javax.swing.*;
import restaurant.GUI.component.MenuPane;
import restaurant.controller.RestaurantController;
import restaurant.model.Category;
import restaurant.model.exception.GeneralException;
import restaurant.model.facade.RestaurantEngine;

/**
 *
 * @author satthuvdh
 */
public class AddItemDialog extends JDialog implements ActionListener {

    private RestaurantController controller;
    private JTextField nameTextField, categoryTextField;
    private JSpinner price;
    private JButton acceptBtn, cancelBtn;
    private JLabel itemNameLbl, priceLbl, categoryLbl, vndLbl;
    private JPanel btnPn;
    private JSeparator separator;
    private GridBagConstraints cons;
    private GridBagLayout layout;
    private Container cont;
    private MenuPane menuPane;
    private Category category;
    private static ResourceBundle rb;

    public AddItemDialog(RestaurantController controller, MenuPane menuPane,
            Category category) {
        rb = RestaurantEngine.translate;
        this.menuPane = menuPane;
        this.controller = controller;
        this.category = category;
        setTitle(rb.getString("Add-New-Item"));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

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

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if (cmd.equals(rb.getString("Cancel"))) {
            this.dispose();
        } else {
            try {
                if (nameTextField.getText() == null
                        || nameTextField.getText().equals("")) {
                    throw new GeneralException(rb.getString("ITEM'S NAME ERROR!"));
                }
                long pr = (new Double((Double) price.getValue())).longValue();
                controller.getEZmodel().addItem(nameTextField.getText(),
                        pr, categoryTextField.getText());
                category.setCollapse(true);
                menuPane.refresh();
                this.dispose();
            } catch (Exception ex) {
                setAlwaysOnTop(false);
                JOptionPane.showMessageDialog(null, ex.getMessage(), rb.getString("Error!"),
                        JOptionPane.ERROR_MESSAGE);
                setAlwaysOnTop(true);
            }
        }
    }

    private void init() {
        btnPn = new JPanel(new FlowLayout());
        acceptBtn = new JButton(rb.getString("OK"));
        cancelBtn = new JButton(rb.getString("Cancel"));

        itemNameLbl = new JLabel(rb.getString("Item-Name"));
        priceLbl = new JLabel(rb.getString("Price"));
        categoryLbl = new JLabel(rb.getString("Category"));
        vndLbl = new JLabel("VND");
        nameTextField = new JTextField(30);
        nameTextField.addActionListener(this);

        separator = new JSeparator(SwingConstants.VERTICAL);

        categoryTextField = new JTextField(category.getName());
        categoryTextField.setEditable(false);
        categoryTextField.setBackground(new Color(222, 225, 229));

        btnPn.add(acceptBtn);
        btnPn.add(cancelBtn);

        btnPn.setBackground(new Color(255, 223, 178));
        long defaultValue = 500;
        long minimumValue = 500;
        long maximumValue = 10000000;
        long stepSize = 500;
        SpinnerModel spinnerModel = new SpinnerNumberModel(
                defaultValue, minimumValue, maximumValue, stepSize);
        price = new JSpinner(spinnerModel);

        acceptBtn.addActionListener(this);
        cancelBtn.addActionListener(this);


    }

    private void setLayout() {
        layout = new GridBagLayout();
        cons = new GridBagConstraints();
        cont = this.getContentPane();
        cont.setLayout(layout);
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.insets = new Insets(3, 3, 3, 3);
    }

    private void addComponents() {
        cons.gridx = 0;

        cons.gridwidth = 1;
        cons.gridy = 1;
        cont.add(itemNameLbl, cons);

        cons.gridwidth = 5;
        cons.gridx = 1;
        cont.add(nameTextField, cons);

        cons.gridy = 2;
        cons.gridwidth = 1;
        cont.add(price, cons);

        cons.gridx = 0;
        cont.add(priceLbl, cons);

        cons.gridx = 2;
        cont.add(vndLbl, cons);

        cons.gridx = 3;
        cont.add(separator, cons);

        cons.gridx = 4;
        cont.add(categoryLbl, cons);

        cons.gridx = 5;
        cont.add(categoryTextField, cons);

        cons.gridx = 0;
        cons.gridy = 4;
        cons.gridwidth = 6;
        cont.add(btnPn, cons);
    }
}
