package it.unisa.diem.Gruppo20.Model;

/**
 * This interface represent an action 
 * that can be performed by calling the execute method.
 * @author Gruppo 20
 */
public interface Command {
    /**
     * Execute this command.
     */
    void execute();
}
