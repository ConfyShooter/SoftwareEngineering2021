package it.unisa.diem.Gruppo20.Model.Exception;

/**
 *
 * @author Ciro
 */
public class VariableKeyException extends RuntimeException {

    /**
     * Creates a new instance of <code>VariableKeyException</code> without
     * detail message.
     */
    public VariableKeyException() {
    }

    /**
     * Constructs an instance of <code>VariableKeyException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public VariableKeyException(String msg) {
        super(msg);
    }
}
