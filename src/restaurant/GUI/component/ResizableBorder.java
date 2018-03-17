/*
 * Course name: Soft Eng: Process & Tools
 * Course ID: COSC2101
 * Team: Angry Eagles
 */
package restaurant.GUI.component;

/**
 * This class is created to draw a border around component,
 * allow the component to be resized.
 * 
 * @author Luan Nguyen Thanh - S3312335
 * @reference ZetCode.com
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class ResizableBorder implements Border {

    private int distance = 8;
    
    /** Create the grip points */
    int locations[] = {
        SwingConstants.NORTH, SwingConstants.SOUTH, SwingConstants.WEST,
        SwingConstants.EAST, SwingConstants.NORTH_WEST,
        SwingConstants.NORTH_EAST, SwingConstants.SOUTH_WEST,
        SwingConstants.SOUTH_EAST
    };
    /** Match the cursor with the grip */
    int cursors[] = {
        Cursor.N_RESIZE_CURSOR, Cursor.S_RESIZE_CURSOR, Cursor.W_RESIZE_CURSOR,
        Cursor.E_RESIZE_CURSOR, Cursor.NW_RESIZE_CURSOR, Cursor.NE_RESIZE_CURSOR,
        Cursor.SW_RESIZE_CURSOR, Cursor.SE_RESIZE_CURSOR
    };

    public ResizableBorder(int distance) {
        this.distance = distance;
    }

    public Insets getBorderInsets(Component component) {
        return new Insets(distance, distance, distance, distance);
    }

    public boolean isBorderOpaque() {
        return true;
    }

    public void paintBorder(Component component, Graphics g, int x, int y,
            int w, int h) {
        if (component.hasFocus()) {
            g.setColor(Color.black);
            g.drawRect(x + distance / 2, y + distance / 2,
                    w - distance, h - distance);

            for (int i = 0; i < locations.length; i++) {
                Rectangle rect = getRectangle(x, y, w, h, locations[i]);
                g.setColor(Color.WHITE);
                g.fillRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
                g.setColor(Color.BLACK);
                g.drawRect(rect.x, rect.y, rect.width - 1, rect.height - 1);
            }
        } else {
            g.setColor(new Color(255, 223, 178));
            g.fillRect(x, y, w, h);
            g.setColor(Color.black);
            g.drawRect(x + distance / 2, y + distance / 2,
                    w - distance, h - distance);
        }
    }

    private Rectangle getRectangle(int x, int y, int w, int h, int location) {
        switch (location) {
            case SwingConstants.NORTH:
                return new Rectangle(x + w / 2 - distance / 2, y, distance, distance);
            case SwingConstants.SOUTH:
                return new Rectangle(x + w / 2 - distance / 2, y + h - distance, distance,
                        distance);
            case SwingConstants.WEST:
                return new Rectangle(x, y + h / 2 - distance / 2, distance, distance);
            case SwingConstants.EAST:
                return new Rectangle(x + w - distance, y + h / 2 - distance / 2, distance,
                        distance);
            case SwingConstants.NORTH_WEST:
                return new Rectangle(x, y, distance, distance);
            case SwingConstants.NORTH_EAST:
                return new Rectangle(x + w - distance, y, distance, distance);
            case SwingConstants.SOUTH_WEST:
                return new Rectangle(x, y + h - distance, distance, distance);
            case SwingConstants.SOUTH_EAST:
                return new Rectangle(x + w - distance, y + h - distance, distance, distance);
        }
        return null;
    }

    public int getCursor(MouseEvent me) {
        Component c = me.getComponent();
        int w = c.getWidth();
        int h = c.getHeight();

        for (int i = 0; i < locations.length; i++) {
            Rectangle rect = getRectangle(0, 0, w, h, locations[i]);
            if (rect.contains(me.getPoint())) {
                return cursors[i];
            }
        }

        return Cursor.MOVE_CURSOR;
    }
}