/**
 * Course name: Soft Eng: Process & Tools
 * Course ID: COSC2101
 * Team: Angry Eagles
 */
package restaurant.GUI.component.inputFrame;

/**
 * Generate a nice view for the current order of one table
 * 
 * @author Luan, Nguyen Thanh - S3312335
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.net.URL;
import java.util.*;
import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import restaurant.GUI.GeneralView;
import restaurant.GUI.component.TablePanel;
import restaurant.controller.OrderItemListener;
import restaurant.controller.RestaurantController;
import restaurant.model.*;
import restaurant.model.facade.RestaurantEngine;
import restaurant.model.facade.RestaurantModel;

public class OrderDialog extends JDialog {

    /** Controller */
    private RestaurantController controller;
    private OrderItemListener listener;
    private static ResourceBundle rb;
    /**
     * Determine it is in the normal view or kitchen view
     * 
     * True: Is in normal view
     * False: Is in kitchen view
     */
    private boolean isNormal;
    /** The table parse in */
    private Table table;
    private TablePanel tp;
    /** The ResDetailsPanel */
    private ResDetailsPanel rdp;
    private JFormattedTextField discountTxt;

    public OrderDialog(RestaurantController controller, TablePanel tp) {
        this.isNormal = true;
        rb = RestaurantEngine.translate;
        this.controller = controller;
        this.tp = tp;
        this.table = tp.getTable();
        listener = new OrderItemListener(
                this.controller, this.rdp, this.tp, this.table, this);

        this.rdp = new ResDetailsPanel();

        init();

    }

    private void init() {
        
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setAutoRequestFocus(true);

        add(this.rdp);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width / 2) - 265;
        int y = 50;
        setBounds(x, y, 530, 650);
        setResizable(false);
    }

    public void setIsNormal(boolean isNormal) {
        this.isNormal = isNormal;
    }

    public void refresh() {
        rdp.refresh();
        if (isNormal) {
            setPreferredSize(new Dimension(530, 650));
        } else {
            setPreferredSize(new Dimension(530, 580));
        }
    }

    // <editor-fold defaultstate="collapsed" desc="OrderDetailsPanel">
    /**
     * This private class will responsible for generating a panel
     * that display the specific information of the current order.
     * (The items and prices)
     * 
     * @author Luan, Nguyen Thanh - S3312335
     */
    private class OrderDetailsPanel extends JPanel{

        /** Determine whether the order item has the note or not */
        private boolean[] isNoted;
        /** Determine whether the table's order is valid (not null) or not */
        boolean isValid;
        /** Core Components */
        private JLabel tLbl;
        private JLabel headLbl;
        private JToggleButton viewToggleBtn;
        private JScrollPane scrollPane;
        private JTextField itemFld;
        private JFormattedTextField prFld;
        private JPanel flowLayoutPn;
        private JButton printBtn, closeBtn, specialDiBtn, moveTable;
        private ArrayList<JButton> buttonList;
        private ArrayList<JScrollPane> scrollList;
        

        public OrderDetailsPanel() {
            init();
        }

        /**
         * Initialize the layout
         */
        private void init() {
            buttonList = new ArrayList<JButton>();
            scrollList = new ArrayList<JScrollPane>();
            setLayout(new BorderLayout());
            setBackground(new Color(255, 223, 178));

            flowLayoutPn = new JPanel(new FlowLayout(FlowLayout.LEADING));
            flowLayoutPn.setBackground(new Color(255, 223, 178));

            add(tableInfoPn(), BorderLayout.NORTH);
            add(setInnerScrollPane());

            try {
                add(buttonPn(), BorderLayout.SOUTH);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(),
                        rb.getString("Error!"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        /**
         * Refresh the content of this panel
         */
        public void refresh() {
            this.removeAll();
            init();
            this.revalidate();
        }

        /**
         * @return The Panel contains table related information
         */
        private JPanel tableInfoPn() {
            JPanel tablePanel = new JPanel();
            GroupLayout tPnLayout = new GroupLayout(tablePanel);
            tablePanel.setLayout(tPnLayout);
            tablePanel.setBackground(new Color(255, 223, 178));

            tLbl = new JLabel(rb.getString("Table ") + table.getName());
            tLbl.setFont(new Font("Tahoma", Font.BOLD, 18));

            viewToggleBtn = new JToggleButton();

            if (isNormal) {
                viewToggleBtn.setText(rb.getString("Order view"));
                viewToggleBtn.setSelected(false);
            } else {
                viewToggleBtn.setText(rb.getString("Kitchen view"));
                viewToggleBtn.setSelected(true);
            }

            viewToggleBtn.setActionCommand("Toggle");
            viewToggleBtn.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    if (viewToggleBtn.isSelected()) {
                        viewToggleBtn.setText(rb.getString("Kitchen view"));
                        isNormal = false;
                        refresh();
                    } else {
                        viewToggleBtn.setText(rb.getString("Order view"));
                        isNormal = true;
                        refresh();
                    }
                }
            });

            tPnLayout.setHorizontalGroup(tPnLayout.createParallelGroup(
                    GroupLayout.Alignment.LEADING).
                    addGroup(tPnLayout.createSequentialGroup().
                    addContainerGap().addComponent(tLbl).
                    addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                    276, Short.MAX_VALUE).
                    addComponent(viewToggleBtn).addContainerGap()));
            tPnLayout.setVerticalGroup(tPnLayout.createParallelGroup(
                    GroupLayout.Alignment.LEADING).
                    addGroup(GroupLayout.Alignment.TRAILING,
                    tPnLayout.createSequentialGroup().
                    addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).
                    addGroup(tPnLayout.createParallelGroup(
                    GroupLayout.Alignment.BASELINE).addComponent(tLbl).
                    addComponent(viewToggleBtn)).addContainerGap()));

            return tablePanel;
        }

        private JScrollPane setScrollPane() {
            scrollPane = new JScrollPane(setInnerScrollPane());
            return scrollPane;
        }

        /**
         * Put the OrdersList panel inside a scroll pane
         * @return The JScrollPane that holds the OrdersList panel
         */
        private JScrollPane setInnerScrollPane() {
            JScrollPane jsp = new JScrollPane();
            jsp.setViewportView(setOrderList());
            return jsp;
        }

        /**
         * Set the isNoted array of booleans that match with the parsed in
         * order item list size
         * 
         * @param oil - The list of OrderItem
         */
        private void setIsNoted(ArrayList<OrderItem> oil) {
            if (oil != null) {
                int size = oil.size();
                isNoted = new boolean[size];

                for (int i = 0; i < size; i++) {
                    isNoted[i] = !oil.get(i).getNote().equals("");
                }
            }
        }

        /**
         * @return The JPanel that contains the list of order items and
         *         relevant functional buttons
         */
        private JPanel setOrderList() {
            isValid = true;
            ArrayList<OrderItem> oil = null;
            // Get the OrderItems List (oil) from the table
            try {
                oil = table.getCurrentOrder().getOrderItems();
            } catch (NullPointerException ex) {
                isValid = false;
            }
            setIsNoted(oil);

            JPanel inner = new JPanel(new GridBagLayout());
            inner.setBackground(new Color(255, 223, 178));

            GridBagConstraints cons = new GridBagConstraints();
            cons.insets = new Insets(3, 3, 3, 3);

            cons.gridy = 0;

            // Set the view base on the oil
            if (isValid && !oil.isEmpty()) {
                // Set the column names
                cons.gridx = 0;
                headLbl = new JLabel(rb.getString("Item"));
                headLbl.setFont(new Font("Tahoma", 1, 11));
                inner.add(headLbl, cons);

                cons.gridx++;
                headLbl = new JLabel(rb.getString("Amount"));
                headLbl.setFont(new Font("Tahoma", 1, 11));
                inner.add(headLbl, cons);

                if (isNormal) {
                    cons.gridx++;
                    headLbl = new JLabel(rb.getString("Unit price"));
                    headLbl.setFont(new Font("Tahoma", 1, 11));
                    inner.add(headLbl, cons);

                    cons.gridx++;
                    headLbl = new JLabel(rb.getString("Line total"));
                    headLbl.setFont(new Font("Tahoma", 1, 11));
                    inner.add(headLbl, cons);
                }

                // Set the contents
                for (int i = 0; i < oil.size(); i++) {
                    OrderItemListener l = new OrderItemListener(
                            listener, oil.get(i));
                    final OrderItem item = oil.get(i);

                    cons.gridwidth = 1;
                    cons.gridy++;

                    cons.gridx = 0;
                    String itemName = oil.get(i).getItem().getName();
                    itemFld = new JTextField(itemName, 10);
                    itemFld.setEditable(false);
                    itemFld.setBackground(new Color(222, 225, 229));
                    inner.add(itemFld, cons);

                    cons.gridx++;
                    int quantity = oil.get(i).getQuantity();
                    JSpinner quantitySpinner = new JSpinner(
                            new SpinnerNumberModel(quantity, 1, 100, 1));
                    quantitySpinner.addChangeListener(l);
                    inner.add(quantitySpinner, cons);

                    if (isNormal) {
                        cons.gridx++;
                        double unitPrice = oil.get(i).getItem().getPrice();
                        prFld = new JFormattedTextField(
                                new DefaultFormatterFactory(
                                new NumberFormatter()), unitPrice);
                        prFld.setColumns(6);
                        prFld.setHorizontalAlignment(
                                JFormattedTextField.TRAILING);
                        prFld.setEditable(false);
                        prFld.setBackground(new Color(222, 225, 229));
                        inner.add(prFld, cons);

                        cons.gridx++;
                        prFld = new JFormattedTextField(
                                new DefaultFormatterFactory(
                                new NumberFormatter()), unitPrice * quantity);
                        prFld.setColumns(6);
                        prFld.setHorizontalAlignment(
                                JFormattedTextField.TRAILING);
                        prFld.setEditable(false);
                        prFld.setBackground(new Color(222, 225, 229));
                        inner.add(prFld, cons);
                    }

                    cons.gridx++;
                    JButton delBtn = new JButton();

                    URL imgURL = GeneralView.class.getResource(
                            "images/Delete.png");
                    URL overImgURL = GeneralView.class.getResource(
                            "images/Delete_Over.png");
                    URL pressImgURL = GeneralView.class.getResource(
                            "images/Delete_Press.png");

                    if (imgURL != null && overImgURL != null 
                            && pressImgURL != null) {
                        delBtn.setIcon(new ImageIcon(imgURL));
                        delBtn.setRolloverIcon(new ImageIcon(overImgURL));
                        delBtn.setPressedIcon(new ImageIcon(pressImgURL));
                    } else {
                        delBtn.setText(rb.getString("Delete"));
                    }

                    delBtn.setToolTipText(rb.getString("Delete this item"));
                    delBtn.setBorder(null);
                    delBtn.setContentAreaFilled(false);
                    delBtn.setFocusPainted(false);
                    delBtn.setActionCommand("Delete");
                    delBtn.addActionListener(l);

                    buttonList.add(delBtn);
                    inner.add(delBtn, cons);

                    JTextArea noteTxt = new JTextArea();

                    // Set the 'Show note' button
                    cons.gridx++;
                    JButton showNoteBtn = new JButton(
                            rb.getString("Show-note"));
                    showNoteBtn.setActionCommand("Show note");

                    showNoteBtn.setToolTipText(
                            rb.getString("Show the note of this item"));
                    showNoteBtn.setFocusPainted(false);
                    inner.add(showNoteBtn, cons);
                    buttonList.add(showNoteBtn);

                    // Set the note. On the below row.
                    // From Item Name to the last column (not button)
                    cons.gridwidth = cons.gridx - 1;
                    cons.gridy++;
                    cons.gridx = 0;
                    noteTxt.setText(oil.get(i).getNote());
                    int col = isNormal ? 30 : 14;
                    noteTxt.setColumns(col);
                    noteTxt.setRows(3);
                    noteTxt.setLineWrap(true);
                    noteTxt.addFocusListener(l);
                    noteTxt.setVisible(true);

                    JScrollPane noteScroll = new JScrollPane(noteTxt);
                    showNoteBtn.addActionListener(
                            new OrderItemListener(noteScroll));
                    noteScroll.setVisible(false);
                    scrollList.add(noteScroll);
                    inner.add(noteScroll, cons);
                }
            } else {
                cons.gridx = 0;
                headLbl = new JLabel(rb.getString("This table is vacant!"));
                headLbl.setFont(new Font("Tahoma", 1, 14));
                inner.add(headLbl, cons);
            }

            flowLayoutPn.add(inner);

            return flowLayoutPn;
        }

        private JPanel totalPn() throws Exception {
            JPanel totalPn = new JPanel();
            totalPn.setBackground(new Color(255, 223, 178));
            GroupLayout totalPnLayout = new GroupLayout(totalPn);
            totalPn.setLayout(totalPnLayout);

            // Labels
            JLabel subTotalLbl = new JLabel(rb.getString("Sub-total"));
            subTotalLbl.setFont(new Font("Tahoma", Font.BOLD, 13));

            JLabel discountLbl = new JLabel(rb.getString("Discount"));
            discountLbl.setFont(new Font("Tahoma", Font.BOLD, 13));

            JLabel vatLbl = new JLabel(rb.getString("VAT (10%)"));
            vatLbl.setFont(new Font("Tahoma", Font.BOLD, 13));

            JLabel totalLbl = new JLabel(rb.getString("Total"));
            totalLbl.setFont(new Font("Tahoma", Font.BOLD, 13));

            JLabel vnd = new JLabel(" VND  ");

            // Formatted TextFields for price
            JFormattedTextField subTotalTxt = new JFormattedTextField(
                    new DefaultFormatterFactory(
                    new NumberFormatter()),
                    table.getCurrentOrder().calculateOrder());
            subTotalTxt.setEditable(false);
            subTotalTxt.setBackground(new Color(222, 225, 229));
            subTotalLbl.setLabelFor(subTotalTxt);

            discountTxt = new JFormattedTextField(
                    new DefaultFormatterFactory(
                    new NumberFormatter()),
                    table.getCurrentOrder().calculateDiscount());
            discountTxt.setEditable(false);
            discountTxt.setBackground(new Color(222, 225, 229));
            discountLbl.setLabelFor(discountTxt);

            JFormattedTextField vatTxt = new JFormattedTextField(
                    new DefaultFormatterFactory(
                    new NumberFormatter()),
                    table.getCurrentOrder().calculateVAT());
            vatTxt.setEditable(false);
            vatTxt.setBackground(new Color(222, 225, 229));
            vatLbl.setLabelFor(vatTxt);

            JFormattedTextField totalTxt = new JFormattedTextField(
                    new DefaultFormatterFactory(
                    new NumberFormatter()),
                    table.getCurrentOrder().calculateTotal());
            totalTxt.setEditable(false);
            totalTxt.setBackground(new Color(222, 225, 229));
            totalLbl.setLabelFor(totalTxt);

//             Set the layout
//            totalPnLayout.setHorizontalGroup(
//                    totalPnLayout.createParallelGroup(
//                    GroupLayout.Alignment.LEADING).
//                    addGroup(totalPnLayout.createSequentialGroup().
//                    addGroup(totalPnLayout.createParallelGroup(
//                    GroupLayout.Alignment.TRAILING, false).
//                    addComponent(subTotalLbl, GroupLayout.Alignment.LEADING,
//                    GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
//                    Short.MAX_VALUE).
//                    addComponent(totalLbl, GroupLayout.Alignment.LEADING,
//                    GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
//                    Short.MAX_VALUE).
//                    addComponent(vatLbl, GroupLayout.Alignment.LEADING)).
//                    addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).
//                    addGroup(totalPnLayout.createParallelGroup(
//                    GroupLayout.Alignment.LEADING, false).
//                    addComponent(totalTxt).addComponent(vatTxt,
//                    GroupLayout.Alignment.TRAILING, 0, 0, Short.MAX_VALUE).
//                    addComponent(subTotalTxt, GroupLayout.DEFAULT_SIZE,
//                    85, Short.MAX_VALUE)).
//                    addContainerGap(267, Short.MAX_VALUE)));
//            totalPnLayout.setVerticalGroup(
//                    totalPnLayout.createParallelGroup(
//                    GroupLayout.Alignment.LEADING).
//                    addGroup(totalPnLayout.createSequentialGroup().
//                    addContainerGap().addGroup(
//                    totalPnLayout.createParallelGroup(
//                    GroupLayout.Alignment.BASELINE).
//                    addComponent(subTotalTxt, GroupLayout.PREFERRED_SIZE,
//                    GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).
//                    addComponent(subTotalLbl)).
//                    addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).
//                    addGroup(totalPnLayout.createParallelGroup(
//                    GroupLayout.Alignment.LEADING).
//                    addGroup(totalPnLayout.createSequentialGroup().
//                    addComponent(vatTxt, GroupLayout.PREFERRED_SIZE,
//                    GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).
//                    addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).
//                    addGroup(totalPnLayout.createParallelGroup(
//                    GroupLayout.Alignment.BASELINE).addComponent(totalLbl).
//                    addComponent(totalTxt, GroupLayout.PREFERRED_SIZE,
//                    GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))).
//                    addComponent(vatLbl)).addContainerGap(
//                    GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

//             Set the layout - GridBagLayout
            totalPn.setLayout(new GridBagLayout());
            GridBagConstraints cons = new GridBagConstraints();

            cons.insets = new Insets(5, 5, 5, 5);
            cons.anchor = GridBagConstraints.WEST;
            cons.fill = GridBagConstraints.HORIZONTAL;

            cons.gridx = 0;
            cons.gridy = 0;
            totalPn.add(subTotalLbl, cons);

            cons.gridx = 1;
            cons.ipadx = 10;
            totalPn.add(subTotalTxt, cons);

            cons.gridx = 2;
            totalPn.add(vnd, cons);

            cons.gridx = 3;
            cons.ipadx = 150;
            JPanel pn = new JPanel();
            pn.setBackground(new Color(255, 223, 178));
            totalPn.add(pn, cons);

            cons.gridx = 1;
            cons.ipadx = 0;
            cons.gridy++;
            totalPn.add(discountTxt, cons);

            cons.gridx = 0;
            totalPn.add(discountLbl, cons);

            vnd = new JLabel(" VND  ");
            cons.gridx = 2;
            totalPn.add(vnd, cons);

            
            specialDiBtn = new JButton(rb.getString("Special Discount"));
            specialDiBtn.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    specialDiBtnActionListerner();
                }
            });
            cons.gridx = 3;
            totalPn.add(specialDiBtn, cons);

            cons.gridy++;
            cons.gridx = 0;
            totalPn.add(vatLbl, cons);

            cons.gridx = 1;
            totalPn.add(vatTxt, cons);

            vnd = new JLabel(" VND  ");
            cons.gridx = 2;
            totalPn.add(vnd, cons);

            cons.gridx = 1;
            cons.gridy++;
            totalPn.add(totalTxt, cons);

            vnd = new JLabel(" VND  ");
            cons.gridx = 2;
            totalPn.add(vnd, cons);

            cons.gridx = 0;
            totalPn.add(totalLbl, cons);

            int totalPnY = totalPn.getY();

            return totalPn;
        }

        /**
         * @return The Panel contains buttons
         */
        private JPanel buttonPn() throws Exception {
            JSeparator separator = new JSeparator();
            JPanel totalPn = null;
            if (isValid) {
                totalPn = totalPn();
            }
            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(new Color(255, 223, 178));

            String print = rb.getString("Print ") + (isNormal
                    ? rb.getString("bill") : rb.getString("order"));
            String cmd = "Print " + (isNormal ? "bill" : "order");

            printBtn = new JButton(print);
            printBtn.setActionCommand(cmd);
            printBtn.addActionListener(listener);
            closeBtn = new JButton(rb.getString("Close"));
            closeBtn.setActionCommand("Close");
            closeBtn.addActionListener(listener);

            GroupLayout buttonPanelLayout = new GroupLayout(buttonPanel);
            buttonPanel.setLayout(buttonPanelLayout);

            if (isValid && isNormal) {
                buttonPanelLayout.setHorizontalGroup(
                        buttonPanelLayout.createParallelGroup(
                        GroupLayout.Alignment.LEADING).
                        addGroup(buttonPanelLayout.createSequentialGroup().
                        addContainerGap().addComponent(printBtn).
                        addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                        272, Short.MAX_VALUE).
                        addComponent(closeBtn, GroupLayout.PREFERRED_SIZE,
                        69, GroupLayout.PREFERRED_SIZE).addContainerGap()).
                        addComponent(separator, GroupLayout.Alignment.TRAILING,
                        GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE).
                        addGroup(GroupLayout.Alignment.TRAILING,
                        buttonPanelLayout.createSequentialGroup().
                        addContainerGap().addComponent(totalPn,
                        GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE).addContainerGap()));
                buttonPanelLayout.setVerticalGroup(
                        buttonPanelLayout.createParallelGroup(
                        GroupLayout.Alignment.LEADING).addGroup(
                        GroupLayout.Alignment.TRAILING,
                        buttonPanelLayout.createSequentialGroup().
                        addContainerGap(
                        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).
                        addComponent(totalPn, GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).
                        addGap(18, 18, 18).addComponent(separator,
                        GroupLayout.PREFERRED_SIZE, 10,
                        GroupLayout.PREFERRED_SIZE).addPreferredGap(
                        LayoutStyle.ComponentPlacement.UNRELATED).
                        addGroup(buttonPanelLayout.createParallelGroup(
                        GroupLayout.Alignment.BASELINE).addComponent(printBtn).
                        addComponent(closeBtn)).addContainerGap()));
            } else {
                buttonPanelLayout.setHorizontalGroup(
                        buttonPanelLayout.createParallelGroup(
                        GroupLayout.Alignment.LEADING).
                        addGroup(buttonPanelLayout.createSequentialGroup().
                        addContainerGap().addComponent(printBtn).
                        addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                        272, Short.MAX_VALUE).addComponent(closeBtn,
                        GroupLayout.PREFERRED_SIZE, 69,
                        GroupLayout.PREFERRED_SIZE).addContainerGap()));
                buttonPanelLayout.setVerticalGroup(
                        buttonPanelLayout.createParallelGroup(
                        GroupLayout.Alignment.LEADING).addGroup(
                        GroupLayout.Alignment.TRAILING,
                        buttonPanelLayout.createSequentialGroup().
                        addContainerGap(GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE).addGroup(
                        buttonPanelLayout.createParallelGroup(
                        GroupLayout.Alignment.BASELINE).
                        addComponent(printBtn).addComponent(closeBtn)).
                        addContainerGap()));
            }

//             Set the layout


            return buttonPanel;
        }

        

        

        
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="ResDetailsPanel inner class">
    /**
     * This private class will responsible for generating a panel
     * that display the information of the restaurant
     * and general information about the order.
     * Including:
     * - Restaurant name, address, phone;
     * - The user who works on this order, timestamp.
     * 
     * @author Luan, Nguyen Thanh - S3312335
     */
    private class ResDetailsPanel extends JPanel implements Printable {

        private RestaurantModel model;
        // General use components
        private String detail;
        private JLabel detailLbl;
        private JPanel panel;
        private GridBagConstraints cons;
        /** The OrderDetailsPanel */
        private OrderDetailsPanel odp;

        public void setOdp(OrderDetailsPanel odp) {
            this.odp = odp;
        }

        public ResDetailsPanel() {
            model = controller.getEZmodel();
            odp = new OrderDetailsPanel();
            init();
        }

        

        /**
         * Initial the components
         */
        private void init() {
            setLayout(new BorderLayout());
            setBackground(new Color(107, 0, 21));

            add(initResPanel(), BorderLayout.NORTH);
            add(odp);

            pack();
        }

        /**
         * Refresh the content of this panel
         */
        public void refresh() {

            odp.refresh();


        }

        public int print(Graphics g, PageFormat pf, int page) throws
                PrinterException {

            if (page > 0) { /* We have only one page, and 'page' is zero-based */
                return NO_SUCH_PAGE;
            }


            /* User (0,0) is typically outside the imageable area, so we must
             * translate by the X and Y values in the PageFormat to avoid clipping
             */
            Graphics2D g2d = (Graphics2D) g;
            g2d.translate(pf.getImageableX(), pf.getImageableY());

            /* Now print the window and its visible contents */
            this.printAll(g);

            /* tell the caller that this page is part of the printed document */
            return PAGE_EXISTS;
        }

        /**
         * Create the left side: Restaurant details
         * @return The JPanel that holds the restaurant details
         */
        private JPanel initResPanel() {
            // Initiate the layout
            JPanel namePanel = new JPanel();
            GroupLayout NamePanelLayout = new GroupLayout(namePanel);
            namePanel.setLayout(NamePanelLayout);
            namePanel.setBackground(new Color(107, 0, 21));

            // Initiate the needed components
            detail = model.getRestaurantName();
            JLabel resName = new JLabel(detail);
            resName.setForeground(Color.white);
            resName.setFont(new Font("Tahoma", 1, 12));

            detail = model.getAddress();
            JLabel add = new JLabel(detail);
            add.setForeground(Color.white);

            detail = model.getPhone();
            JLabel phone = new JLabel(detail);
            phone.setForeground(Color.white);

            User current = controller.getEZmodel().getCurrent();
            detail = current.getName() + " (" + current.getUsername() + ")";
            JLabel currentUser = new JLabel(detail);
            currentUser.setForeground(Color.white);
            currentUser.setFont(new Font("Tahoma", 1, 12));

            Calendar c = new GregorianCalendar();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            String m = (minute > 9) ? minute + "" : "0" + minute;
            int second = c.get(Calendar.SECOND);
            detail = rb.getString("Edited at ") + hour + ":" + m + ":"
                    + second;
            JLabel createdTime = new JLabel(detail);
            createdTime.setForeground(Color.white);

            int date = c.get(Calendar.DAY_OF_MONTH);
            int year = c.get(Calendar.YEAR);
            String[] months = {rb.getString("Jan"), rb.getString("Feb"),
                rb.getString("Mar"), rb.getString("Apr"), rb.getString("May"),
                rb.getString("Jun"), rb.getString("Jul"), rb.getString("Aug"),
                rb.getString("Sep"), rb.getString("Oct"), rb.getString("Nov"),
                rb.getString("Dec")};
            String month = months[c.get(Calendar.MONTH)];
            detail = date + " " + month + ", " + year;
            JLabel createdDate = new JLabel(detail);
            createdDate.setForeground(Color.white);

            // Set the layout
            NamePanelLayout.setHorizontalGroup(
                    NamePanelLayout.createParallelGroup(
                    GroupLayout.Alignment.LEADING).
                    addGroup(NamePanelLayout.createSequentialGroup().
                    addContainerGap().addGroup(
                    NamePanelLayout.createParallelGroup(
                    GroupLayout.Alignment.LEADING).
                    addGroup(NamePanelLayout.createSequentialGroup().
                    addComponent(resName).addPreferredGap(
                    LayoutStyle.ComponentPlacement.RELATED, 124,
                    Short.MAX_VALUE).
                    addComponent(currentUser)).addGroup(
                    NamePanelLayout.createSequentialGroup().addComponent(add).
                    addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                    223, Short.MAX_VALUE).
                    addComponent(createdTime)).addGroup(
                    NamePanelLayout.createSequentialGroup().addComponent(phone).
                    addPreferredGap(
                    LayoutStyle.ComponentPlacement.RELATED, 251,
                    Short.MAX_VALUE).addComponent(createdDate))).
                    addContainerGap()));
            NamePanelLayout.setVerticalGroup(
                    NamePanelLayout.createParallelGroup(
                    GroupLayout.Alignment.LEADING).
                    addGroup(NamePanelLayout.createSequentialGroup().
                    addContainerGap().addGroup(
                    NamePanelLayout.createParallelGroup(
                    GroupLayout.Alignment.BASELINE).
                    addComponent(resName).addComponent(currentUser)).
                    addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).
                    addGroup(NamePanelLayout.createParallelGroup(
                    GroupLayout.Alignment.BASELINE).
                    addComponent(add).addComponent(createdTime)).
                    addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).
                    addGroup(NamePanelLayout.createParallelGroup(
                    GroupLayout.Alignment.BASELINE).
                    addComponent(phone).addComponent(createdDate)).
                    addContainerGap(GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE)));

            namePanel.setSize(400, 150);
            return namePanel;
        }
    }// </editor-fold>

    public void specialDiBtnActionListerner() {
        if (!(controller.getEZmodel().getCurrent() instanceof Manager)) {
            ConfirmUser cf = new ConfirmUser(controller, table.getName(), this);
        } else {
            AddDiscount discount = new AddDiscount(controller, true,
                    table.getName(), this);
        }
    }
}
