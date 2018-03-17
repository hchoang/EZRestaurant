/*
 * Course name: Soft Eng: Process & Tools
 * Course ID: COSC2101
 * Team: Angry Eagles
 */
package restaurant.GUI.component;

/**
 * This class will create a table button that can be dragged and dropped on
 * the table map of the restaurant.
 * 
 * @author Luan Nguyen Thanh - S3312335
 */
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.util.ResourceBundle;
import javax.swing.*;
import restaurant.GUI.GeneralView;
import restaurant.controller.RestaurantController;
import restaurant.model.facade.RestaurantEngine;

public class TableButton extends JButton
        implements Transferable, DragSourceListener, DragGestureListener {

    /** Controller for this */
    private RestaurantController controller;
    /** Mark this as the dragging source */
    private DragSource dragSource;
    /** */
    private TransferHandler t;
    
     private static ResourceBundle rb;

    // Setting the GUI of the button
    public TableButton() {
        // this.controller = controller;
         rb = RestaurantEngine.translate;
        // Create the button
        this.setToolTipText(rb.getString("Drag this to create new table"));
        this.setActionCommand("Add Table");

        this.setIcon(new ImageIcon(GeneralView.class.
                getResource("images/Table.png")));
        this.setBorder(null);
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        this.setRolloverIcon(new ImageIcon(GeneralView.class.
                getResource("images/Table_Hover.png")));
        this.setMaximumSize(new Dimension(80, 56));
        this.setMinimumSize(new Dimension(80, 56));
        this.setPreferredSize(new Dimension(80, 56));
        this.setCursor(new Cursor(Cursor.MOVE_CURSOR));

        t = new TransferHandler() {

            @Override
            public Transferable createTransferable(JComponent c) {
                return new TableButton();
            }
        };

        // Create a new copy of this button to be dragged
        dragSource = new DragSource();
        dragSource.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY, this);
    }

    // Handle the DnD part
    /**
     * This will help the DropTarget know how to handle the Transferable
     */
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{new DataFlavor(TableButton.class, "JButton")};
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return true;
    }

    public Object getTransferData(DataFlavor flavor) {
        return this;
    }

    public void dragEnter(DragSourceDragEvent dsde) {
    }

    public void dragOver(DragSourceDragEvent dsde) {
    }

    public void dropActionChanged(DragSourceDragEvent dsde) {
    }

    public void dragExit(DragSourceEvent dse) {
    }

    public void dragDropEnd(DragSourceDropEvent dsde) {
        this.repaint();
    }

    public void dragGestureRecognized(DragGestureEvent dge) {
        dragSource.startDrag(dge, DragSource.DefaultMoveDrop,
                new TableButton(), this);
    }
}
