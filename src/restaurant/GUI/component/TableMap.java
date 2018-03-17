/*
 * Course name: Soft Eng: Process & Tools
 * Course ID: COSC2101
 * Team: Angry Eagles
 */
package restaurant.GUI.component;

/**
 * This class will set up a drop-able JPanel to take a drop from tableButton
 * 
 * @author Luan Nguyen Thanh - S3312335
 */
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.util.ResourceBundle;
import javax.swing.*;
import restaurant.controller.RestaurantController;
import restaurant.model.*;
import restaurant.model.facade.RestaurantEngine;
import restaurant.model.facade.RestaurantModel;

public class TableMap extends JPanel implements DropTargetListener {

    /** Controller */
    private RestaurantController controller;
    /** Tables list */
    private Table[] tables;
    /** Mark this as a DropTarget */
    private DropTarget dt;
    /** Count */
    private int count = 0;
 private static ResourceBundle rb;
 
    public TableMap(RestaurantController controller, Table[] tables) {
        this.controller = controller;
        this.tables = tables;
         rb = RestaurantEngine.translate;
        this.setLayout(new DragLayout());
//        this.setLayout(null);
        this.setBackground(new Color(255, 223, 178));

        dt = new DropTarget(this, this);
        
//        init();
    }

    public void refresh() {
        repaint();
        revalidate();
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
        try {
            Point point = dtde.getLocation();
            Transferable trans = dtde.getTransferable();
            TableButton tb = (TableButton) trans.getTransferData(
                    new DataFlavor(TableButton.class, rb.getString("TableButton")));
            if (tb instanceof TableButton) {
                try {
                    RestaurantModel model = controller.getEZmodel();

                    String tbName = model.generateTable();

                    model.addTable(new Location(point.x, point.y), tbName);
                    Table table = model.getTable(tbName);
                    TablePanel tp = new TablePanel(table, controller);

                    Resizer resizer = new Resizer(tp);
                    resizer.setBounds(point.x, point.y, table.widthDef, 
                            table.heightDef);
                    add(resizer);
                    
                    refresh();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(),
                            rb.getString("Error!"), JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception ex) {
        }
    }
}