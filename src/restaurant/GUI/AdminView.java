/*
 * Course name: Soft Eng: Process & Tools
 * Course ID: COSC2101
 * Team: Angry Eagles
 */
package restaurant.GUI;

import java.awt.*;
import javax.swing.*;
import restaurant.GUI.component.*;
import restaurant.controller.*;

/**
 * 
 * @author satthuvdh
 */
public class AdminView extends RestaurantView {

    private AdminToolBar toolBar;
    private JTable userDisplay;
    private JScrollPane userDisplaySPane;
    private GridBagConstraints cons;
    private GridBagLayout layout;
    private Container cont;

    public AdminView(RestaurantController controller, String username,
            String userFullname, String type) {
        super(controller, username, userFullname, type);
        this.setLayout(new BorderLayout());

        init();

//        pack();
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private void init() {
        TableDisplayUser model = new TableDisplayUser(
                controller.getEZmodel().getAccounts());
        userDisplay = new JTable(model);

        userDisplaySPane = new JScrollPane(userDisplay);
        userDisplay.setFillsViewportHeight(true);
        userDisplaySPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        userDisplaySPane.setPreferredSize(new Dimension(800, 600));

        userDisplay.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        toolBar = new AdminToolBar(controller, model);

        userDisplay.addMouseListener(new ListUserTableListener(userDisplay,
                toolBar));

        this.add(toolBar, BorderLayout.NORTH);
        this.add(userDisplaySPane);
        
        pack();
    }

    @Override
    public void refresh() {
        super.refresh();
        getContentPane().removeAll();
        init();
        revalidate();
    }
}
