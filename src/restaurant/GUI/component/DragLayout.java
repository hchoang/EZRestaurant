/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package restaurant.GUI.component;

/**
 *
 * @author Luan Nguyen Thanh
 */
import java.awt.*;
import java.io.Serializable;

/**
 */
public class DragLayout implements LayoutManager, Serializable {

    public DragLayout() {
    }

    /**
     * Adds the specified component with the specified name to the layout.
     * @param name the name of the component
     * @param comp the component to be added
     */
    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    /**
     * Removes the specified component from the layout.
     *
     * @param comp the component to be removed
     */
    @Override
    public void removeLayoutComponent(Component component) {
    }

    /**
     *  Determine the minimum size on the Container
     *
     *  @param   target   the container in which to do the layout
     *  @return  the minimum dimensions needed to lay out the
     *           subcomponents of the specified container
     */
    @Override
    public Dimension minimumLayoutSize(Container parent) {
        synchronized (parent.getTreeLock()) {
            return preferredLayoutSize(parent);
        }
    }

    /**
     *  Determine the preferred size on the Container
     *
     *  @param   parent   the container in which to do the layout
     *  @return  the preferred dimensions to lay out the
     *           subcomponents of the specified container
     */
    @Override
    public Dimension preferredLayoutSize(Container parent) {
        synchronized (parent.getTreeLock()) {
            return getLayoutSize(parent);
        }
    }

    /*
     *  The calculation for minimum/preferred size it the same. The only
     *  difference is the need to use the minimum or preferred size of the
     *  component in the calculation.
     *
     *  @param   parent  the container in which to do the layout
     */
    private Dimension getLayoutSize(Container parent) {
        Insets parentInsets = parent.getInsets();
        int X = parentInsets.left;
        int Y = parentInsets.top;
        int width = 0;
        int height = 0;

        for (Component component : parent.getComponents()) {
            if (component.isVisible()) {
                Resizer r = (Resizer) component;
                int x = r.getX();
                int y = r.getY();
                int w = r.getWidth();
                int h = r.getHeight();

                X = Math.min(X, x);
                Y = Math.min(Y, y);
                width = Math.max(width, x + w);
                height = Math.max(height, y + h);
            }
        }

        if (X < parentInsets.left) {
            width += parentInsets.left - X;
        }

        if (Y < parentInsets.top) {
            height += parentInsets.top - Y;
        }

        width += parentInsets.right;
        height += parentInsets.bottom;
        Dimension d = new Dimension(width, height);

        return d;
    }

    /**
     * Lays out the specified container using this layout.
     *
     * @param     target   the container in which to do the layout
     */
    @Override
    public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()) {
            for (Component component : parent.getComponents()) {
                if (component.isVisible()) {
                    Point p = component.getLocation();
                    Dimension d = component.getPreferredSize();

                    Resizer r = (Resizer) component;
                    int x = r.getX();
                    int y = r.getY();
                    int w = r.getWidth();
                    int h = r.getHeight();

                    component.setBounds(x, y, w, h);
                }
            }
        }
    }

    /**
     * Returns the string representation of this column layout's values.
     * @return   a string representation of this layout
     */
    @Override
    public String toString() {
        return "["
                + getClass().getName()
                + "]";
    }
}