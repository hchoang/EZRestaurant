/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.GUI.component;

import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.Set;
import javax.swing.JDialog;
import net.sourceforge.chart2d.*;
import restaurant.model.facade.RestaurantEngine;

/**
 *
 * @author satthuvdh
 */
public class BarChartVertical extends JDialog {

    private LinkedHashMap<String, Long> report;
    private static ResourceBundle rb;
    public BarChartVertical(LinkedHashMap<String, Long> report) {
        rb = RestaurantEngine.translate;
        this.report = report;
        
        Set<String> keyList = report.keySet();
        String[] keys = keyList.toArray(new String[report.size()]);


        //<-- Begin Chart2D configuration -->

        //Configure object properties
        Object2DProperties object2DProps = new Object2DProperties();
        object2DProps.setObjectTitleText(rb.getString("Time Report"));

        //Configure chart properties
        Chart2DProperties chart2DProps = new Chart2DProperties();
        chart2DProps.setChartDataLabelsPrecision(-2);

        //Configure legend properties
        LegendProperties legendProps = new LegendProperties();
        legendProps.setLegendExistence(false);

        //Configure graph chart properties
        GraphChart2DProperties graphChart2DProps = new GraphChart2DProperties();
        graphChart2DProps.setLabelsAxisLabelsTexts(keys);
        graphChart2DProps.setLabelsAxisTitleText(rb.getString("Hours"));
        graphChart2DProps.setNumbersAxisTitleText(rb.getString("Revenue"));
        graphChart2DProps.setChartDatasetCustomizeGreatestValue(true);
        graphChart2DProps.setChartDatasetCustomGreatestValue(1f);
        graphChart2DProps.setChartDatasetCustomizeLeastValue(true);
        graphChart2DProps.setChartDatasetCustomLeastValue(.5f);
        graphChart2DProps.setGraphComponentsColoringByCat(true);
        graphChart2DProps.setGraphComponentsColorsByCat(new MultiColorsProperties());

        //Configure graph properties
        GraphProperties graphProps = new GraphProperties();

        //Configure dataset
        Dataset dataset = new Dataset(1, report.size(), 1);
        for (int i = 0; i < report.size(); i++) {
            long value = report.get(keys[i]);
            dataset.set(0, i, 0, (float) value);
        }

        //Configure graph component colors
        MultiColorsProperties multiColorsProps = new MultiColorsProperties();

        //Configure chart
        LBChart2D chart2D = new LBChart2D();
        chart2D.setObject2DProperties(object2DProps);
        chart2D.setChart2DProperties(chart2DProps);
        chart2D.setLegendProperties(legendProps);
        chart2D.setGraphChart2DProperties(graphChart2DProps);
        chart2D.addGraphProperties(graphProps);
        chart2D.addDataset(dataset);
        chart2D.addMultiColorsProperties(multiColorsProps);

        //Optional validation:  Prints debug messages if invalid only.
        if (!chart2D.validate(false)) {
            chart2D.validate(true);
        }

        //<-- End Chart2D configuration -->

        add(chart2D);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        this.setAlwaysOnTop(true);
        
    }
}
