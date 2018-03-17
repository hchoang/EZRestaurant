package restaurant.model;

import java.io.Serializable;
import restaurant.model.exception.GeneralException;

public class Location implements Serializable {

    private int x, y;

    /**
     * Constructor with axis x and y
     * @param x Coordinator x
     * @param y Coordinator y
     */
    public Location(int x, int y) throws GeneralException {
        if (x < 0 || y < 0) {
            throw new GeneralException("Invalid location");
        }
        this.x = x;
        this.y = y;
    }

    /**
     * Accessors to axis x
     *
     * @return axis x
     */
    public int getX() {
        return x;
    }

    /**
     * Mutator to axis x
     *
     * @param x the new axis x
     */
    public void setX(int x) throws GeneralException {
        if (x < 0) {
            throw new GeneralException("Invalid location");
        }
        this.x = x;
    }

    /**
     * Accessors to axis y
     *
     * @return axis y
     */
    public int getY() {
        return y;
    }

    /**
     * Mutator to axis y
     *
     * @param y the new axis y
     */
    public void setY(int y) throws GeneralException {
        if (y < 0) {
            throw new GeneralException("Invalid location");
        }
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Location other = (Location) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.x;
        hash = 83 * hash + this.y;
        return hash;
    }
}
