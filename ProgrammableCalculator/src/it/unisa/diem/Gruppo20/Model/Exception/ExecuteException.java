package it.unisa.diem.Gruppo20.Model.Exception;

/**
 *
 * @author Team 20
 */
public class ExecuteException extends RuntimeException {

    /**
     * Creates a new instance of <code>ExecuteException</code> without detail
     * message.
     */
    public ExecuteException() {
    }

    /**
     * Constructs an instance of <code>ExecuteException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ExecuteException(String msg) {
        super(msg);
    }
}
