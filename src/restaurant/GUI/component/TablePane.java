/*
 * Course name: Soft Eng: Process & Tools
 * Course ID: COSC2101
 * Team: Angry Eagles
 */
package restaurant.GUI.component;

/**
 * @author Luan Nguyen Thanh - S3312335
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import restaurant.GUI.GeneralView;
import restaurant.controller.RestaurantController;
import restaurant.model.Location;
import restaurant.model.Table;

public class TablePane extends JPanel {

    /** Controller */
    private RestaurantController controller;
    /** Table list */
    private ArrayList<Table> tableList;
    /** General Components that can be reused */
    private Resizer resizer;
    private JLabel label;
    private JPanel panel;
    private JPanel insidePanel;
    private JPanel table;
    /** Core Components for this createView */
    private TableButton tableButton;
    private TableMap tableMap;
    private JPanel tablePane;
    private JScrollPane tableScrollPane;
    private TableDeleteButton deleteButton;

    public TablePane(RestaurantController controller) {
        this.controller = controller;
        tableList = controller.getEZmodel().getTableList();

        this.setLayout(new BorderLayout());
        setTablePane();
    }

    public void setTableList(ArrayList<Table> tableList) {
        this.tableList = tableList;
    }

    private Table[] getTablesArray() {
        if (tableList.isEmpty()) {
            return null;
        } else {
            return tableList.toArray(new Table[tableList.size()]);
        }
    }

    private void setTableToolBar() {
        tableButton = new TableButton();

        panel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        panel.setBackground(new Color(107, 0, 21));

        panel.add(tableButton);

        this.add(panel, BorderLayout.NORTH);
    }

    private void setTableDelete() {
        deleteButton = new TableDeleteButton(controller);

        panel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        panel.add(deleteButton);

        this.add(panel, BorderLayout.SOUTH);
    }

    private void setTableMap() {
        Table[] tables = getTablesArray();

        // Set the map layout
        tableMap = new TableMap(controller, tables);

        // Add the tables into the map
        if (tables != null) {
            for (int i = 0; i < tables.length; i++) {
                TablePanel tp = new TablePanel(tables[i], controller);
                Location startPoint = tables[i].getStart();
                int w = tables[i].getWidth() > 0
                        ? tables[i].getWidth()
                        : tables[i].widthDef;
                int h = tables[i].getHeight() > 0
                        ? tables[i].getHeight()
                        : tables[i].heightDef;
                resizer = new Resizer(tp);

                tableMap.add(resizer);
                resizer.setBounds(startPoint.getX(), startPoint.getY(), w, h);
            }
        }

        tableScrollPane = new JScrollPane(tableMap);

        this.add(tableScrollPane);
    }

    private void setTablePane() {
        // removeAll();
        setTableToolBar();
        setTableMap();
        //setTableDelete();
        // repaint();
    }

    private JButton createButton(String image, String cmd,
            String toolTip, String altText) {
        // Create the button
        JButton button = new JButton();
        button.setToolTipText(toolTip);
        button.setActionCommand(cmd);
        button.addActionListener(controller);

        // Find the image icon
        URL imageURL = GeneralView.class.getResource("images/" + image + ".png");

        if (imageURL != null) {
            button.setIcon(new ImageIcon(imageURL, altText));
        } else {
            button.setText(altText);
        }

        return button;
    }
}
