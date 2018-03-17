/*
 * Course name: Soft Eng: Process & Tools
 * Course ID: COSC2101
 * Team: Angry Eagles
 */
package restaurant.GUI.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import restaurant.GUI.GeneralView;
import restaurant.controller.RestaurantController;
import restaurant.model.facade.RestaurantEngine;

/**
 * Create a button to change language On-The-Fly
 *
 * @author Luan Nguyen Thanh
 */
public class LanguageButton extends JButton implements ActionListener {

    private static final int IN_ENG = 1;
    private static final int IN_VIE = 2;
    private String toolTip;
    private String currentLang;
    private RestaurantController c;
    private static ResourceBundle rb;

    public LanguageButton(RestaurantController c) {
        this.c = c;
        rb = RestaurantEngine.translate;
        toolTip = rb.getString("Vietnamese and English") + " (Ctrl + L)";
        init();

        this.setToolTipText(toolTip);
        this.setBorder(null);
        this.setContentAreaFilled(false);
        this.addActionListener(this);
        this.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW).
                put(KeyStroke.getKeyStroke(
                KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK),
                "language");
        this.getActionMap().put("language", new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                JButton b = (JButton) e.getSource();
                b.doClick();
            }
        });
    }

    private void init() {
        this.currentLang = c.getEZmodel().getLanguage();

        String imageSrc = imageURL();

        URL n_imgURL = GeneralView.class.getResource(imageSrc + ".png");
        URL o_imgURL = GeneralView.class.getResource(imageSrc + "_Over.png");
        URL p_imgURL = GeneralView.class.getResource(imageSrc + "_Press.png");

        if (n_imgURL != null && p_imgURL != null && o_imgURL != null) {
            this.setIcon(new ImageIcon(n_imgURL));
            this.setRolloverIcon(new ImageIcon(o_imgURL));
            this.setPressedIcon(new ImageIcon(p_imgURL));
        } else {
            this.setText(toolTip);
        }
    }

    private String imageURL() {
        String s = "images/";

        s = s.concat(currentLang);

        return s;
    }

    public void actionPerformed(ActionEvent e) {
        if (currentLang.equals(RestaurantEngine.EN_LANGUAGE)) {
            try {
                c.getEZmodel().setLanguage(RestaurantEngine.VI_LANGUAGE,
                        RestaurantEngine.VI_COUNTRY);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(),
                        rb.getString("Error!"), JOptionPane.ERROR_MESSAGE);
            }
        } else if (currentLang.equals(RestaurantEngine.VI_LANGUAGE)) {
            try {
                c.getEZmodel().setLanguage(RestaurantEngine.EN_LANGUAGE,
                        RestaurantEngine.EN_COUNTRY);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(),
                        rb.getString("Error!"), JOptionPane.ERROR_MESSAGE);
            }
        }

        this.init();

        c.getEZview().refresh();
    }
}