package it.unisa.diem.Gruppo20.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete class that implements Command.
 * It represent a user-defined operation made by a sequence of Command.
 * @author Gruppo 20
 */
public class UserCommand implements Command {
    private List<Command> macros;

    /**
     * Create a new UserCommand object.
     */
    public UserCommand() {
        macros = new ArrayList<>();
    }
    
    /**
     * Add m to the sequence of operations of this UserCommand.
     * @param m The command that must be added to the user-define operation.
     */
    public void add(Command m) {
        macros.add(m);
    }
    
    /**
     * Execute this UserCommand.
     */
    @Override
    public void execute() {
        for(Command c: macros)
            c.execute();
    }
    
}
