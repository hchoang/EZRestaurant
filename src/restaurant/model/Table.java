package restaurant.model;

import java.awt.*;
import java.awt.print.*;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import restaurant.model.exception.GeneralException;

public class Table implements Serializable, Printable {

    public final int widthDef = 100, heightDef = 75;
    private Location posStart;
    private Location posEnd;
    private int width, height;
    //posStart is  the corner left-upside of the table
    //posEnd is the corner right-downside of the table
    private Order currentOrder;
    private String name;
    private int paperSize;
    private boolean print; // True is print bill, False is print order;
    private int[] pageBreaks;
    private ArrayList<String> textLines;
    private ArrayList<String> resInfo;
    private double normal;

    /**
     * Constructor with the start position
     *
     * @param posStart
     */
    public Table(Location posStart, String name) throws GeneralException {
        this.posStart = posStart;
        posEnd = new Location(posStart.getX() + widthDef,
                posStart.getY() + heightDef);
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) throws GeneralException {
        this.height = height;
        posEnd = new Location(posStart.getX() + width,
                posStart.getY() + height);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) throws GeneralException {
        this.width = width;
        posEnd = new Location(posStart.getX() + width,
                posStart.getY() + height);
    }

    /**
     * Accessors to the name of the table
     *
     * @return name of the table
     */
    public String getName() {
        return name;
    }

    /**
     * Mutator to the name of the table
     *
     * @param name the name of the table
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Accessors to the current order of the table
     *
     * @return
     * <code>Order</code> in current of the table
     */
    public Order getCurrentOrder() {
        return currentOrder;
    }

    /**
     * Mutator to the current order of the table
     *
     * @param currentOrder the current order of the table
     */
    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    /**
     * Accessors to the start point of the table
     *
     * @return
     * <code>Location</code> of the start point
     */
    public Location getStart() {
        return posStart;
    }

    /**
     * Mutator to the start point of the table
     *
     * @param posStart the new location of the start point
     */
    public void setStart(Location posStart) {
        this.posStart = posStart;
    }

    /**
     * Accessors to the end point of the table
     *
     * @return
     * <code>Location</code> of the end point
     */
    public Location getEnd() {
        return posEnd;
    }

    /**
     * Mutator to the end point of the table
     *
     * @param posEnd the new location of the end point
     */
    public void setEnd(Location posEnd) {
        this.posEnd = posEnd;
    }

    /**
     * Check other table is overlap this table or not
     *
     * @param table the other table
     * @return true if not overlapping, otherwise false
     */
    public boolean checkTable(Table table) {

        //Check whether 4 corners of the other table is in this table or not

        if (table.getStart().getX() >= posStart.getX()
                && table.getStart().getX() <= posEnd.getX()
                && table.getEnd().getY() <= posEnd.getY()
                && table.getEnd().getY() >= posStart.getY()) {
            return false;
        }

        if (table.getEnd().getX() >= posStart.getX()
                && table.getEnd().getX() <= posEnd.getX()
                && table.getStart().getY() <= posEnd.getY()
                && table.getStart().getY() >= posStart.getY()) {
            return false;
        }

        if (table.getStart().getX() >= posStart.getX()
                && table.getStart().getX() <= posEnd.getX()
                && table.getStart().getY() <= posEnd.getY()
                && table.getStart().getY() >= posStart.getY()) {
            return false;
        }

        if (table.getEnd().getX() >= posStart.getX()
                && table.getEnd().getX() <= posEnd.getX()
                && table.getEnd().getY() >= posEnd.getY()
                && table.getEnd().getY() <= posStart.getY()) {
            return false;
        }
        return true;
    }

    /**
     * Print the order
     *
     * @throws PrinterException when we have problem with printer
     */
    public void printerKitchenOrder(double normal, int paperSize) throws PrinterException, GeneralException {
        this.normal = normal;
        this.paperSize = paperSize;
        PageFormat format = new PageFormat();
        Paper paper = new Paper();
        PrinterJob printJob = PrinterJob.getPrinterJob();
            
            double paperWidth = ((double)paperSize)/25.4;
            double paperHeight = 100;
            double leftMargin = 0;
            double rightMargin = 0;
            double topMargin = 0;
            double bottomMargin = 0.01;
            paper.setSize(((double)paperSize/25.4) * 72.0, paperHeight * 72.0);
            paper.setImageableArea(leftMargin * 72.0, topMargin * 72.0,
                    (paperWidth - leftMargin - rightMargin) * 72.0,
                    (paperHeight - topMargin - bottomMargin) * 72.0);

            format.setPaper(paper);

            PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
            aset.add(OrientationRequested.PORTRAIT);

            format = printJob.validatePage(format);
            printJob.setPrintable(this, format);
        if (printJob.printDialog()) {
            print = false;
            printJob.print();
        }
    }

    /**
     * Print the bill
     *
     * @throws PrinterException when we have problem with printer
     */
    public void printerBill(ArrayList<String> resInfo, double normal, int paperSize) throws PrinterException, GeneralException {
        this.normal = normal;
        this.paperSize = paperSize;
        PageFormat format = new PageFormat();
        Paper paper = new Paper();

        PrinterJob printJob = PrinterJob.getPrinterJob();
        
            
        
        double paperWidth = paperSize/25.4;
        double paperHeight = 100;
        double leftMargin = 0;
        double rightMargin = 0;
        double topMargin = 0;
        double bottomMargin = 0.01;

        paper.setSize(paperWidth * 72.0, paperHeight * 72.0);
        paper.setImageableArea(leftMargin * 72.0, topMargin * 72.0,
                (paperWidth - leftMargin - rightMargin) * 72.0,
                (paperHeight - topMargin - bottomMargin) * 72.0);

        format.setPaper(paper);

        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        aset.add(OrientationRequested.PORTRAIT);

        format = printJob.validatePage(format);
        printJob.setPrintable(this, format);
        
        if (printJob.printDialog()) {
            this.resInfo = resInfo;
            print = true;
            printJob.print();
            this.setCurrentOrder(null);
        } else {
            throw new GeneralException("Print bill is canceled. Can not check out the order");
        }
    }

    /**
     * Create the page to print
     *
     */
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
            throws PrinterException {

        if (print) {
            return printBill(graphics, pageFormat, pageIndex);
        } else {
            return printOrder(graphics, pageFormat, pageIndex);
        }

    }

    public int printOrder(Graphics graphics, PageFormat pageFormat, int pageIndex) {

        Font font = new Font("Arial", Font.PLAIN, 10);
        FontMetrics metrics = graphics.getFontMetrics(font);
        graphics.setColor(Color.black);
        graphics.setFont(font);
        int lineHeight = metrics.getHeight() + 15;


        textLines = new ArrayList<String>();
        GregorianCalendar time = new GregorianCalendar();
        String timeString = time.get(GregorianCalendar.HOUR) + ":"
                + time.get(GregorianCalendar.MINUTE) + ", "
                + time.get(GregorianCalendar.DATE) + "/"
                + (time.get(GregorianCalendar.MONTH) + 1) + "/"
                + time.get(GregorianCalendar.YEAR);
        textLines.add("Table : " + name + " at " + timeString);
        textLines.add("List of item:");
        ArrayList<OrderItem> items = currentOrder.getOrderItems();
        for (int i = 0; i < items.size(); i++) {
            OrderItem item = items.get(i);
            textLines.add(item.getItem().getName()
                    + ". Quantity: " + item.getQuantity()
                    + ". Note: " + item.getNote());
        }
        textLines.add("-------------------------------------------------------------------------------------");
       

        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        int y = 0;
        for (int line = 0; line < textLines.size(); line++) {
            y += lineHeight;
            graphics.drawString(textLines.get(line), 20, y);
        }

        return PAGE_EXISTS;
    }

    public int printBill(Graphics graphics, PageFormat pageFormat, int pageIndex) {

        Font font = new Font("Arial", Font.PLAIN, 10);
        FontMetrics metrics = graphics.getFontMetrics(font);
        graphics.setColor(Color.black);
        graphics.setFont(font);
        int lineHeight = metrics.getHeight() + 15;



        textLines = new ArrayList<String>();
        textLines.add("Restaurant name: " + resInfo.get(0));
        textLines.add("Restaurant address: " + resInfo.get(1));
        textLines.add("Restaurant phone:" + resInfo.get(2));
        GregorianCalendar time = new GregorianCalendar();
        String timeString = time.get(GregorianCalendar.HOUR) + ":"
                + time.get(GregorianCalendar.MINUTE) + ", "
                + time.get(GregorianCalendar.DATE) + "/"
                + (time.get(GregorianCalendar.MONTH) + 1) + "/"
                + time.get(GregorianCalendar.YEAR);
        textLines.add("Table : " + name + " at " + timeString);
        textLines.add("List of item:");
        ArrayList<OrderItem> items = currentOrder.getOrderItems();
        DecimalFormat df = new DecimalFormat();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setGroupingSeparator(',');
        df.setDecimalFormatSymbols(dfs);

        for (int i = 0; i < items.size(); i++) {
            OrderItem item = items.get(i);
            String price = df.format((int) item.getItem().getPrice()) + " VND";
            String total = df.format((int) item.calculatePrice()) + " VND";
            textLines.add(item.getItem().getName()
                    + " (price: " + price + ") * " + item.getQuantity()
                    + " = " + total);
        }
        textLines.add("-------------------------------------------------------------------------------------");
        textLines.add("Sub-total: " + df.format((int) currentOrder.calculateOrder()) + " VND");
        textLines.add("VAT (10%): " + df.format((int) currentOrder.calculateVAT()) + " VND");
        textLines.add("Total: " + df.format((int) currentOrder.calculateTotal()) + " VND");

        
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        int y = 0;
        
        for (int line = 0; line < textLines.size(); line++) {
            y += lineHeight;
            graphics.drawString(textLines.get(line), 20, y);
        }

        return PAGE_EXISTS;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Table other = (Table) obj;
        if (!Objects.equals(this.posStart, other.posStart)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.posStart);
        hash = 31 * hash + Objects.hashCode(this.name);
        return hash;
    }
}
