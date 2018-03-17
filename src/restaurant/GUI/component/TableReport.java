/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * _TableReport.java
 *
 * Created on Dec 16, 2011, 4:51:47 PM
 */
package restaurant.GUI.component;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Vector;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import restaurant.model.exception.GeneralException;
import restaurant.model.facade.RestaurantEngine;
import restaurant.model.facade.RestaurantModel;

/**
 *
 * @author satthuvdh
 */
public class TableReport extends JDialog {

    RestaurantModel model;
    private static ResourceBundle rb;

    public TableReport(RestaurantModel model) {
         rb = RestaurantEngine.translate;
        this.model = model;
        initComponents();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        setAlwaysOnTop(true);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField2 = new javax.swing.JTextField();
        TabPanel = new javax.swing.JTabbedPane();
        timeReportPnl = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        timeReportGenerateBtn = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        timeReportSearchTimeLbl = new javax.swing.JLabel();
        timeReportSearchTimeTfl = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        revenueReportPanl = new javax.swing.JPanel();
        totalRevLBl = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        starDateChooser = new com.toedter.calendar.JDateChooser();
        startLbl = new javax.swing.JLabel();
        endDateChooser = new com.toedter.calendar.JDateChooser();
        endLBl = new javax.swing.JLabel();
        generateRevenueReportBtn = new javax.swing.JButton();
        printBtn = new javax.swing.JButton();
        closeBtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        revenueSearchTimeLbl = new javax.swing.JLabel();
        revenueReportSearchTimeTfl = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        jTextField2.setEditable(false);

        jLabel1.setText(rb.getString("Start"));

        jDateChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser1PropertyChange(evt);
            }
        });

        jLabel2.setText(rb.getString("End"));

        jDateChooser2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser2PropertyChange(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                rb.getString("Date"), rb.getString("Revenue")
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        timeReportGenerateBtn.setText(rb.getString("Generate Report"));
        timeReportGenerateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeReportGenerateBtnActionPerformed(evt);
            }
        });

        jButton3.setText(rb.getString("Print"));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton6.setText(rb.getString("Close"));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        timeReportSearchTimeLbl.setText(rb.getString("Search Time:"));

        jLabel3.setText(rb.getString("seconds"));

        jButton2.setText(rb.getString("Graph"));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout timeReportPnlLayout = new javax.swing.GroupLayout(timeReportPnl);
        timeReportPnl.setLayout(timeReportPnlLayout);
        timeReportPnlLayout.setHorizontalGroup(
            timeReportPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timeReportPnlLayout.createSequentialGroup()
                .addGroup(timeReportPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, timeReportPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, timeReportPnlLayout.createSequentialGroup()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addGroup(timeReportPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                                .addComponent(timeReportGenerateBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                                .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)))
                        .addGroup(timeReportPnlLayout.createSequentialGroup()
                            .addGap(24, 24, 24)
                            .addComponent(jLabel1)
                            .addGap(18, 18, 18)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(152, 152, 152)
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)))
                    .addGroup(timeReportPnlLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(timeReportSearchTimeLbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(timeReportSearchTimeTfl, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)))
                .addContainerGap())
        );
        timeReportPnlLayout.setVerticalGroup(
            timeReportPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(timeReportPnlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(timeReportPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(timeReportPnlLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)
                        .addGap(14, 14, 14))
                    .addGroup(timeReportPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(timeReportPnlLayout.createSequentialGroup()
                            .addGroup(timeReportPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jDateChooser1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))))
                .addGroup(timeReportPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(timeReportPnlLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(timeReportGenerateBtn)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                        .addComponent(jButton6)
                        .addGap(9, 9, 9))
                    .addGroup(timeReportPnlLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(timeReportPnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(timeReportSearchTimeLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(timeReportSearchTimeTfl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        TabPanel.addTab("Time Report", timeReportPnl);

        totalRevLBl.setText(rb.getString("Total Revenue:"));

        starDateChooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                starDateChooserPropertyChange(evt);
            }
        });

        startLbl.setText(rb.getString("Start"));

        endDateChooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                endDateChooserPropertyChange(evt);
            }
        });

        endLBl.setText(rb.getString("End"));

        generateRevenueReportBtn.setText(rb.getString("Generate Report"));
        generateRevenueReportBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateRevenueReportBtnActionPerformed(evt);
            }
        });

        printBtn.setText(rb.getString("Print"));
        printBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printBtnActionPerformed(evt);
            }
        });

        closeBtn.setText(rb.getString("Close"));
        closeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeBtnActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                rb.getString("Date"), rb.getString("Revenue")
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);

        revenueSearchTimeLbl.setText(rb.getString("Search Time:"));

        revenueReportSearchTimeTfl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                revenueReportSearchTimeTflActionPerformed(evt);
            }
        });

        jLabel4.setText(rb.getString("seconds"));

        jButton1.setText(rb.getString("Graph"));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout revenueReportPanlLayout = new javax.swing.GroupLayout(revenueReportPanl);
        revenueReportPanl.setLayout(revenueReportPanlLayout);
        revenueReportPanlLayout.setHorizontalGroup(
            revenueReportPanlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, revenueReportPanlLayout.createSequentialGroup()
                .addGroup(revenueReportPanlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, revenueReportPanlLayout.createSequentialGroup()
                        .addGroup(revenueReportPanlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, revenueReportPanlLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(revenueSearchTimeLbl)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(revenueReportSearchTimeTfl, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(totalRevLBl)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                        .addGroup(revenueReportPanlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(generateRevenueReportBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                            .addComponent(closeBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                            .addComponent(printBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)))
                    .addGroup(revenueReportPanlLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(startLbl)
                        .addGap(18, 18, 18)
                        .addComponent(starDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(122, 122, 122)
                        .addComponent(endLBl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(endDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        revenueReportPanlLayout.setVerticalGroup(
            revenueReportPanlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(revenueReportPanlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(revenueReportPanlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(starDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                    .addComponent(endDateChooser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(startLbl, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(endLBl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE))
                .addGap(11, 11, 11)
                .addGroup(revenueReportPanlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(revenueReportPanlLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(generateRevenueReportBtn)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(printBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(closeBtn)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(revenueReportPanlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(revenueReportPanlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(revenueSearchTimeLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(revenueReportSearchTimeTfl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(revenueReportPanlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(totalRevLBl, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        TabPanel.addTab("Revenue Report", revenueReportPanl);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(TabPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 715, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TabPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void timeReportGenerateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timeReportGenerateBtnActionPerformed
        // TODO add your handling code here:}//GEN-LAST:event_timeReportGenerateBtnActionPerformed
        //RestaurantEngine model = (RestaurantEngine) controller.getEZmodel();
        startDate1 = (GregorianCalendar) jDateChooser1.getCalendar();
        endDate1 = (GregorianCalendar) jDateChooser2.getCalendar();



        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
        int rowNum = tableModel.getRowCount();
        for (int i = 0; i < rowNum; i++) {
            tableModel.removeRow(0);
        }
        try {
            if (startDate1 == null || endDate1 == null) {
                throw new GeneralException(rb.getString("Please Check the Date Again"));
            }
            long start = System.currentTimeMillis();

            report1 = model.timeReport(startDate1, endDate1);
            long end = System.currentTimeMillis();
            Set<String> keys = report1.keySet();
            Iterator iter = keys.iterator();
            while (iter.hasNext()) {
                String key = iter.next().toString();
                Vector<String> row = new Vector<String>();
                row.addElement(key);
                row.add(report1.get(key) + "");
                tableModel.addRow(row);
            }
            long time = end - start;
            timeReportSearchTimeTfl.setText(time / 1000.0 + "");
        } catch (Exception ex) {
            setAlwaysOnTop(false);
            JOptionPane.showMessageDialog(null, ex.getMessage(), rb.getString("Error!"),
                    JOptionPane.ERROR_MESSAGE);
            setAlwaysOnTop(true);
        }
    }
        private void generateRevenueReportBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateRevenueReportBtnActionPerformed
//            RestaurantEngine model = (RestaurantEngine) controller.getEZmodel();
            DecimalFormat df = new DecimalFormat();
            DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            dfs.setGroupingSeparator(',');
            df.setDecimalFormatSymbols(dfs);

            startDate2 = (GregorianCalendar) starDateChooser.getCalendar();
            endDate2 = (GregorianCalendar) endDateChooser.getCalendar();
            long total = 0;
            DefaultTableModel tableModel = (DefaultTableModel) jTable2.getModel();
            int rows = tableModel.getRowCount();
            for (int i = 0; i < rows; i++) {
                tableModel.removeRow(0);
            }
            try {
                if (startDate2 == null || endDate2 == null) {
                    throw new GeneralException(rb.getString("Please Check the Date Again"));
                }
                long start = System.currentTimeMillis();
                report2 = (LinkedHashMap) model.revenueReport(startDate2, endDate2);
                long end = System.currentTimeMillis();
                Set<String> keys = report2.keySet();
                Iterator iter = keys.iterator();
                while (iter.hasNext()) {
                    String key = iter.next().toString();
                    Vector<String> row = new Vector<String>();
                    row.addElement(key);
                    total += report2.get(key);
                    String price = df.format(report2.get(key)) + " VND";
                    row.add(price);
                    tableModel.addRow(row);
                }
                String totalString = df.format(total) + " VND";
                jTextField1.setText(totalString);
                long time = end - start;
                revenueReportSearchTimeTfl.setText(time / 1000.0 + "");
            } catch (Exception ex) {
                setAlwaysOnTop(false);
                JOptionPane.showMessageDialog(null, ex.getMessage(), rb.getString("Error!"),
                        JOptionPane.ERROR_MESSAGE);
                setAlwaysOnTop(true);
            }
        }//GEN-LAST:event_generateRevenueReportBtnActionPerformed

    private void starDateChooserPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_starDateChooserPropertyChange
    }//GEN-LAST:event_starDateChooserPropertyChange

    private void endDateChooserPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_endDateChooserPropertyChange
    }//GEN-LAST:event_endDateChooserPropertyChange

    private void jDateChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser1PropertyChange
    }//GEN-LAST:event_jDateChooser1PropertyChange

    private void jDateChooser2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser2PropertyChange
    }//GEN-LAST:event_jDateChooser2PropertyChange

private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    try {
        this.setAlwaysOnTop(false);
        model.printTimeReport((GregorianCalendar) jDateChooser1.getCalendar(), (GregorianCalendar) jDateChooser2.getCalendar());
        this.setAlwaysOnTop(true);
    } catch (Exception ex) {
        setAlwaysOnTop(false);
        JOptionPane.showMessageDialog(null, ex.getMessage(),rb.getString("Error!"),
                JOptionPane.ERROR_MESSAGE);
        setAlwaysOnTop(true);
    }
}//GEN-LAST:event_jButton3ActionPerformed

private void printBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printBtnActionPerformed
    try {
        this.setAlwaysOnTop(false);
        model.printRevenueReport((GregorianCalendar) starDateChooser.getCalendar(), (GregorianCalendar) endDateChooser.getCalendar());
        this.setAlwaysOnTop(true);
    } catch (Exception ex) {
        setAlwaysOnTop(false);
        JOptionPane.showMessageDialog(null, ex.getMessage(), rb.getString("Error!"),
                JOptionPane.ERROR_MESSAGE);
        setAlwaysOnTop(true);
    }
}//GEN-LAST:event_printBtnActionPerformed

private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
    this.dispose();
}//GEN-LAST:event_jButton6ActionPerformed

private void closeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeBtnActionPerformed
    this.dispose();
}//GEN-LAST:event_closeBtnActionPerformed

    private void revenueReportSearchTimeTflActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_revenueReportSearchTimeTflActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_revenueReportSearchTimeTflActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
            if (report2 == null || report2.size() == 0) {
                throw new GeneralException(rb.getString("Nothing to Generate Graph"));
            }
            this.setAlwaysOnTop(false);
            BarChartHorizontal chart = new BarChartHorizontal(report2);


        } catch (GeneralException ex) {
            setAlwaysOnTop(false);
            JOptionPane.showMessageDialog(null, ex.getMessage(), rb.getString("Error!"),
                    JOptionPane.ERROR_MESSAGE);
            setAlwaysOnTop(true);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            if (report1 == null || report1.size() == 0) {
                throw new GeneralException(rb.getString("Nothing to Generate Graph"));
            }
            setAlwaysOnTop(false);
            BarChartVertical chart = new BarChartVertical(report1);


        } catch (GeneralException ex) {
            setAlwaysOnTop(false);
            JOptionPane.showMessageDialog(null, ex.getMessage(),rb.getString("Error!"),
                    JOptionPane.ERROR_MESSAGE);
            setAlwaysOnTop(true);
        }
    }//GEN-LAST:event_jButton2ActionPerformed
    /**
     * @param args the command line arguments
     */
    private GregorianCalendar startDate1 = null, endDate1 = null, startDate2 = null, endDate2 = null;
    private LinkedHashMap<String, Long> report1, report2;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane TabPanel;
    private javax.swing.JButton closeBtn;
    private com.toedter.calendar.JDateChooser endDateChooser;
    private javax.swing.JLabel endLBl;
    private javax.swing.JButton generateRevenueReportBtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JButton printBtn;
    private javax.swing.JPanel revenueReportPanl;
    private javax.swing.JTextField revenueReportSearchTimeTfl;
    private javax.swing.JLabel revenueSearchTimeLbl;
    private com.toedter.calendar.JDateChooser starDateChooser;
    private javax.swing.JLabel startLbl;
    private javax.swing.JButton timeReportGenerateBtn;
    private javax.swing.JPanel timeReportPnl;
    private javax.swing.JLabel timeReportSearchTimeLbl;
    private javax.swing.JTextField timeReportSearchTimeTfl;
    private javax.swing.JLabel totalRevLBl;
    // End of variables declaration//GEN-END:variables
}
