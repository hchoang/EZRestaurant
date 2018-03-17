/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package restaurant.model.exception;

/**
 *
 * @author USER
 */
public class TableLimitException extends Exception{
    public TableLimitException() {
    }

    public TableLimitException(String message) {
        super(message);
    }
}
