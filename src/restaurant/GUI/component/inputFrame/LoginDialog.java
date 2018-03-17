package restaurant.GUI.component.inputFrame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import restaurant.GUI.component.LanguageButton;
import restaurant.controller.RestaurantController;
import restaurant.model.facade.RestaurantEngine;
import restaurant.model.facade.RestaurantModel;

/* @author s3312335
 * Nguyen Thanh Luan 
 * 
 */
public class LoginDialog extends JDialog implements KeyListener, MouseListener {

    /** Components needed for this LoginDialog */
    private JPanel btnPn;
    private JButton loginBtn, cancelBtn;
    private JLabel welcomeLbl, usernameLbl, pwdLbl;
    private JTextField usernameTxt, pwdTxt;
    private GridBagConstraints cons;
    private GridBagLayout layout;
    private Container cont;
    private JLabel language;
    /** A model for the Controller to take */
    private RestaurantModel model;
    private RestaurantController controller;
    private static ResourceBundle rb;

    public LoginDialog(RestaurantController controller) {
        //Connect with the Controller
        this.controller = controller;
        try {
            model = new RestaurantEngine();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

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
//        btnPn.add(language);
//        cont.add(btnPn, cons);

        setSize(300, 200);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

        loginBtn.addActionListener(controller);
        cancelBtn.addActionListener(controller);

        usernameTxt.setActionCommand("Log in");
        usernameTxt.addActionListener(controller);
        pwdTxt.setActionCommand("Log in");
        pwdTxt.addActionListener(controller);
    }

    public void setModel() {
        //Get the text from user's input
        String username = usernameTxt.getText(),
                pwd = pwdTxt.getText();
        try {
            model.logIn(username, pwd);
        } catch (Exception ex) {
            setAlwaysOnTop(false);
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            setAlwaysOnTop(true);
//            ex.printStackTrace();
//            Logger.getLogger(LoginDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public RestaurantModel getModel() {
        return model;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
