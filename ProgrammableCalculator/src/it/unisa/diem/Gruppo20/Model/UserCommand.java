package it.unisa.diem.Gruppo20.Model;

import it.unisa.diem.Gruppo20.Model.Exception.ExecuteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Concrete class that implements Command. Represents a user-defined operation
 * perofomed by a sequence of Command.
 *
 * @author Team 20
 */
public class UserCommand implements Command {

    private final List<String> commandName;
    private final List<Command> commands;

    /**
     * Creates a new UserCommand object.
     */
    public UserCommand() {
        this.commandName = new ArrayList<>();
        this.commands = new ArrayList<>();
    }

    /**
     * Adds m to the sequence of operations of this UserCommand.
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
     * Executes this UserCommand.
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
     * Returns the names of all operations performed by this UserCommand.
     *
     * @return List of String.
     */
    public List<String> getCommandName() {
        return commandName;
    }

    /**
     * Resets this UserCommand's operations and commands.
     */
    public void reset() {
        commands.clear();
        commandName.clear();
    }

    /**
     * Returns true if this UserCommand is executable, else false.
     *
     * @return A boolean that represent if this command is executable.
     */
    public boolean isExecutable() {
        return !(commands.isEmpty() || commandName.isEmpty());
    }

}
