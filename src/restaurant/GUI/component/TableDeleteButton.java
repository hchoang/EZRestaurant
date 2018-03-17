/**
 * Luan, Nguyen Thanh © 2011
 * RMIT University, Vietnam
 * Bachelor of IT
 */
package restaurant.GUI.component;

import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import restaurant.GUI.GeneralView;
import restaurant.controller.RestaurantController;
import restaurant.model.facade.RestaurantEngine;

public class TableDeleteButton extends JButton implements DropTargetListener {
    
    /** Controller */
    private RestaurantController controller;
    private static ResourceBundle rb;


    public TableDeleteButton(RestaurantController controller) {
        this.controller = controller;
         rb = RestaurantEngine.translate;
        this.setIcon(new ImageIcon(GeneralView.class.getResource("images/Recycle.png")));
        this.setRolloverIcon(new ImageIcon(GeneralView.class.getResource("images/Recycle_Over.png")));
        this.setPressedIcon(new ImageIcon(GeneralView.class.getResource("images/Recycle.png")));
        this.setToolTipText(rb.getString("Drag table here to delete"));
        this.setBorder(null);
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
    }

    public void dragEnter(DropTargetDragEvent dtde) {
    }

    public void dragOver(DropTargetDragEvent dtde) {
    }

    public void dropActionChanged(DropTargetDragEvent dtde) {
    }

    public void dragExit(DropTargetEvent dte) {
    }

    public void drop(DropTargetDropEvent dtde) {
    }
}
