/*
 * Course name: Soft Eng: Process & Tools
 * Course ID: COSC2101
 * Team: Angry Eagles
 */
package restaurant.GUI.component;

/**
 * This class will create a item button that can be dragged and dropped on
 * the TablePanel of the restaurant.
 * 
 * @author Luan Nguyen Thanh - S3312335
 */
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.event.*;
import java.text.*;
import javax.swing.*;
import restaurant.controller.RestaurantController;
import restaurant.model.Item;

public class ItemButton extends JButton
        implements Transferable, DragSourceListener, DragGestureListener,
        ActionListener {

    /** Controller for this */
    private RestaurantController controller;
    /** Save the data */
    private Item item;
    /** Mark this as the dragging source */
    private DragSource dragSource;
    /** The TransferHandler to handle the DnD */
    private TransferHandler t;
    /** Store the state of the button */
    private boolean isExpanded;

    // Setting the GUI of the button
    public ItemButton(final Item item) {
        this.item = item;
        this.isExpanded = false;

        init();
        this.addActionListener(this);

        t = new TransferHandler() {

            @Override
            public Transferable createTransferable(JComponent c) {
                return new ItemButton(item);
            }
        };

        // Create a new copy of this button to be dragged
        dragSource = new DragSource();
        dragSource.createDefaultDragGestureRecognizer(this,
                DnDConstants.ACTION_COPY, this);
    }

    public Item getItem() {
        return item;
    }

    private void refresh() {
        this.removeAll();
        
        init();

        this.revalidate();
    }

    /**
     * Initialize the components
     */
    private void init() {
        // Set the button
        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setGroupingSeparator(',');
        df.setDecimalFormatSymbols(dfs);
        String price = df.format(item.getPrice()) + " VND";

        this.setLayout(new BorderLayout(10, 5));
        JLabel itemName = new JLabel(trimString(item.getName()));
        JLabel priceLbl = new JLabel(price);
        this.add(itemName, BorderLayout.WEST);
        this.add(priceLbl, BorderLayout.EAST);
    }
    
    /**
     * Trim the String (item's name) to be shorter if it's size is too long
     * 
     * @param s - the String parse in
     * @return the new String
     */
    private String trimString(String s) {
        if (isExpanded) {
            return s;
        }
        if (s.length() > 15) {
            String new_s = s.substring(0, 14) + "...";
            return new_s;
        }
        
        return s;
    }

    // Handle the DnD part
    /**
     * This will help the DropTarget know how to handle the Transferable
     */
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{new DataFlavor(ItemButton.class, "JButton")};
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
//        this.repaint();
    }

    public void dragGestureRecognized(DragGestureEvent dge) {
        dragSource.startDrag(dge, DragSource.DefaultMoveDrop,
                new ItemButton(item), this);
    }

    /**
     * Expand the button when it is clicked
     * @param e 
     */
    public void actionPerformed(ActionEvent e) {
        isExpanded = !isExpanded;
        refresh();
    }
}
