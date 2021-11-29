package it.unisa.diem.Gruppo20.Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gruppo 20
 */
public class Macro {
    private List<Command> macros;

    public Macro() {
        macros = new ArrayList<>();
    }
    
    public void add(Command m) {
        macros.add(m);
    }
    
    public void executeMacros() {
        for(Command c: macros)
            c.execute();
    }
    
}
