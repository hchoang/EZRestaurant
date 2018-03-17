/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import restaurant.GUI.component.AdminToolBar;

/**
 *
 * @author satthuvdh
 */
public class ListUserTableListener extends MouseAdapter {

    private JTable userTable;
    private AdminToolBar toolBar;

    public ListUserTableListener(JTable userTable, AdminToolBar toolBar) {
        this.userTable = userTable;
        this.toolBar = toolBar;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (userTable.getSelectedRow() >=0) {
            Object selectUser = userTable.getModel().getValueAt(userTable.getSelectedRow(), 1);
            if (selectUser != null) {
                toolBar.setSelectUser((String) selectUser);
                toolBar.setSelectedRow(userTable.getSelectedRow());
            } else {
                toolBar.setSelectUser("");
            }
        }
    }
}
