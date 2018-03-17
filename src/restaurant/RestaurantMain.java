/*
 * Course name: Soft Eng: Process & Tools
 * Course ID: COSC2101
 * Team: Angry Eagles
 */
package restaurant;

import com.sun.java.swing.Painter;
import com.sun.java.swing.plaf.nimbus.AbstractRegionPainter;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import restaurant.controller.RestaurantController;

public class RestaurantMain {

    private static RestaurantController controller;
    private static UIDefaults d = new UIDefaults();

    public static void main(String[] args) {
        
        try {
            // Changing the L&F of Nimbus
            UIManager.put("control", new Color(247, 248, 251));
            UIManager.put("menu", new Color(247, 248, 251));
            //UIManager.put("FormattedTextField[Disabled].backgroundPainter", setDisabledTextField());
            UIManager.put("MenuBar[Enabled].backgroundPainter", setMenuSelectPainter());

            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            try {
                // Set System L&F
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());
            } catch (UnsupportedLookAndFeelException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (InstantiationException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (IllegalAccessException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        controller = new RestaurantController();
    }

    private static Painter setMenuSelectPainter() {
        Painter menuSelection = new Painter() {

            public void paint(Graphics2D g, Object object, int width, int height) {
                g.setColor(new Color(247, 248, 251));
                g.fillRect(0, 0, width, height);
            }
        };
        
        return menuSelection;
    }

    private static AbstractRegionPainter setDisabledTextField() {
        AbstractRegionPainter painter = new AbstractRegionPainter() {

            @Override
            protected PaintContext getPaintContext() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            protected void doPaint(Graphics2D g, JComponent c, int width, int height, Object[] extendedCacheKeys) {
                if (c instanceof JButton) {
                    c.setBackground(new Color(214,217,223));
                }
            }
        };
        
        return painter;
    }
}
