package it.unisa.diem.Gruppo20.Model;

import it.unisa.diem.Gruppo20.Model.Exception.ExecuteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete class that implements Command. It represent a user-defined operation
 * made by a sequence of Command.
 *
 * @author Team 20
 */
public class UserCommand implements Command {

    private final List<String> commandName;
    private final List<Command> commands;

    /**
     * Create a new UserCommand object.
     */
    public UserCommand() {
        this.commandName = new ArrayList<>();
        this.commands = new ArrayList<>();
    }

    /**
     * Add m to the sequence of operations of this UserCommand.
     *
     * @param name The name of the operation that the Command execute.
     * @param c The command that must be added to the user-defined operation.
     */
    public void add(String name, Command c) {
        if (name == null || c == null) {
            throw new NullPointerException();
        }
        commandName.add(name.toLowerCase());
        commands.add(c);
    }

    /**
     * Execute this UserCommand.
     *
     * @throws ExecuteException if the user-defined operation calls another
     * user-defined operation that was deleted from function list.
     */
    @Override
    public void execute() throws ExecuteException {
        if (commands.isEmpty() || commandName.isEmpty()) {
            throw new ExecuteException("This user-defined operation is trying to use a deleted user-defined operation.");
        }
        for (Command c : commands) {
            c.execute();
        }
    }

    /**
     * Return the names of all operations performed by this UserCommand.
     *
     * @return List of String.
     */
    public List<String> getCommandName() {
        return commandName;
    }

    /**
     * Reset this UserCommand's operations and commands.
     */
    public void reset() {
        commands.clear();
        commandName.clear();
    }

}
