package it.unisa.diem.Gruppo20.Model.Exception;

/**
 *
 * @author Ciro
 */
public class ParseException extends RuntimeException {

    /**
     * Creates a new instance of <code>ParseException</code> without detail
     * message.
     */
    public ParseException() {
    }

    /**
     * Constructs an instance of <code>ParseException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public ParseException(String msg) {
        super(msg);
    }
}
