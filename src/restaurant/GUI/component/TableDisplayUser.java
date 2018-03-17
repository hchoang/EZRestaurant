/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.GUI.component;

import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.swing.table.DefaultTableModel;
import restaurant.model.Admin;
import restaurant.model.Manager;
import restaurant.model.User;
import restaurant.model.facade.RestaurantEngine;

/**
 *
 * @author satthuvdh
 */
public class TableDisplayUser extends DefaultTableModel {

    private ArrayList<User> userList;
    private static ResourceBundle rb;


    public TableDisplayUser(ArrayList<User> userList) {
        this.userList = userList;
        rb = RestaurantEngine.translate;
        init();
        addRows(userList);
         
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    private void init() {
        addColumn(rb.getString("User-Type"));
        addColumn(rb.getString("UserName"));
        addColumn(rb.getString("FullName"));
        addColumn(rb.getString("Age"));
        addColumn(rb.getString("Gender"));
        addColumn(rb.getString("Email"));
        addColumn(rb.getString("Active"));
    }

    public void addRows(ArrayList<User> userList) {
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            String type = (user instanceof Admin) ? rb.getString("ADMIN")  
                    : ((user instanceof Manager) ? rb.getString("MANAGER")  : rb.getString("CASHIER"));
            String gender = (user.isGender()) ? rb.getString("MALE")  : rb.getString("FEMALE");
            String active = (user.isActive()) ? rb.getString("ACTIVE")   : rb.getString("INACTIVE");
            insertRow(i, new Object[]{type, user.getUsername(), user.getName(),
                        user.getAge(), gender, user.getEmail(), active});
        }
    }
    
    public void removeAllRows(){
        int rows = this.getRowCount();
        for (int i = 0; i < rows; i++) {
            this.removeRow(0);
        }
    }
}
