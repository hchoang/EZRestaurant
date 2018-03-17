package restaurant.GUI.component.inputFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ResourceBundle;
import javax.swing.*;
import restaurant.controller.RestaurantController;
import restaurant.model.facade.RestaurantEngine;
import restaurant.model.facade.RestaurantModel;

/* @author s3312335
 * Nguyen Thanh Luan 
 * 
 */
public class ConfirmUser extends JDialog implements KeyListener, ActionListener {

    /** Components needed for this LoginDialog */
    private JPanel btnPn;
    private JButton loginBtn, cancelBtn;
    private JLabel welcomeLbl, usernameLbl, pwdLbl;
    private JTextField usernameTxt, pwdTxt;
    private GridBagConstraints cons;
    private GridBagLayout layout;
    private Container cont;
    /** A model for the Controller to take */
    private RestaurantModel model;
    private RestaurantController controller;
    private String tableName;
    private OrderDialog od;
    private static ResourceBundle rb;

    public ConfirmUser(RestaurantController controller, String tableName, OrderDialog od) {
        
        //Connect with the Controller
        this.controller = controller;
        this.tableName = tableName;
        this.od = od;

        rb = RestaurantEngine.translate;
//        this.addKeyListener(this);

        //Frame initializing
        setTitle(rb.getString("welcome!PleaseLogin"));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);

        //Create the components
        btnPn = new JPanel(new FlowLayout());
        loginBtn = new JButton(rb.getString("Log in"));
        loginBtn.setActionCommand("Log in");

        cancelBtn = new JButton(rb.getString("SignOut"));
        cancelBtn.setActionCommand("Cancel");
        usernameLbl = new JLabel(rb.getString("UserName"));
        pwdLbl = new JLabel(rb.getString("Password"));
        usernameTxt = new JTextField(10);
        usernameTxt.addKeyListener(this);
        pwdTxt = new JPasswordField(10);
        pwdTxt.addKeyListener(this);

        //Set the layout
        layout = new GridBagLayout();
        cons = new GridBagConstraints();
        cont = this.getContentPane();
        cont.setLayout(layout);
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.insets = new Insets(3, 3, 3, 3);

        cons.gridx = 0;
        cons.gridy = 0;
        cons.gridwidth = 4;

        welcomeLbl = new JLabel(rb.getString("welcomeToEzRestaurant"), JLabel.CENTER);
        welcomeLbl.setFont(new java.awt.Font("Tahoma", 1, 18));

        cont.add(welcomeLbl, cons);

        cons.gridwidth = 1;
        cons.gridheight = 1;
        cons.gridy = 2;
        cont.add(usernameLbl, cons);

        cons.gridwidth = 3;
        cons.gridx = 1;
        cont.add(usernameTxt, cons);

        cons.gridy = 3;
        cont.add(pwdTxt, cons);

        cons.gridx = 0;
        cons.gridwidth = 1;
        cont.add(pwdLbl, cons);

        cons.gridy = 4;
        cons.gridwidth = 4;
        btnPn.add(loginBtn);
        btnPn.add(cancelBtn);
        cont.add(btnPn, cons);

//        cons.gridy = 5;
//        cons.gridwidth = 4;
//        btnPn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//        btnPn.add(new LanguageButton(controller));
//        cont.add(btnPn, cons);

        setSize(300, 200);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

        loginBtn.addActionListener(this);
        cancelBtn.addActionListener(this);

        usernameTxt.setActionCommand("Log in");
        usernameTxt.addActionListener(this);
        pwdTxt.setActionCommand("Log in");
        pwdTxt.addActionListener(this);
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Cancel")) {
            this.dispose();
        } else if (e.getActionCommand().equals("Log in")) {
            String username = usernameTxt.getText(),
                    pwd = pwdTxt.getText();
            try {
                if (!controller.getEZmodel().checkManager(username, pwd)) {
                    this.dispose();
                    JOptionPane.showMessageDialog(null, "Only Manager Account Can Set Special Discount", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    this.dispose();
                    AddDiscount discount = new AddDiscount(controller, true, tableName, od);

                }
            } catch (Exception ex) {
                this.dispose();
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
                
            }

        }
    }
}
