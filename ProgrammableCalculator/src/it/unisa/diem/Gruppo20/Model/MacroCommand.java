package it.unisa.diem.Gruppo20.Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gruppo 20
 */
public class MacroCommand implements Command {
    private List<Command> macros;

    public MacroCommand() {
        macros = new ArrayList<>();
    }
    
    public void add(Command m) {
        macros.add(m);
    }
    
    @Override
    public void execute() {
        for(Command c: macros)
            c.execute();
    }
    
}
