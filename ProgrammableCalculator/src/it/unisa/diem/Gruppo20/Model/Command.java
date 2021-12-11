package it.unisa.diem.Gruppo20.Model;

/**
 * This interface represents an action that can be performed by calling the
 * execute method.
 *
 * @author Team 20
 */
public interface Command {
    /**
     * Execute this command.
     */
    public void execute();
}
