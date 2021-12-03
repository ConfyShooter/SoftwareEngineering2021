package it.unisa.diem.Gruppo20.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete class that implements Command. It represent a user-defined operation
 * made by a sequence of Command.
 *
 * @author Gruppo 20
 */
public class UserCommand implements Serializable, Command {

    private final List<String> macroName;
    private final List<Command> macros;

    /**
     * Create a new UserCommand object.
     */
    public UserCommand() {
        macros = new ArrayList<>();
        macroName = new ArrayList<>();
    }

    /**
     * Add m to the sequence of operations of this UserCommand.
     *
     * @param name The name of the operation that the Commanc execute.
     * @param c The command that must be added to the user-define operation.
     */
    public void add(String name, Command c) {
        macroName.add(name.toLowerCase());
        macros.add(c);
    }

    /**
     * Execute this UserCommand.
     */
    @Override
    public void execute() {
        if (macros.isEmpty() || macroName.isEmpty()) {
            throw new RuntimeException("This user-defined operation is trying to use a deleted user-defined operation.");
        }
        for (Command c : macros) {
            c.execute();
        }
    }

    /**
     * Return the names of all operations performed by this UserCommand.
     *
     * @return List of String
     */
    public List<String> getMacroName() {
        return macroName;
    }

    public void reset() {
        macros.clear();
        macroName.clear();
    }

}
