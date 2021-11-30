package it.unisa.diem.Gruppo20.Model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Gruppo20
 */
public class UserDefinedOperations {
    private final Calculator c;
    private final Map<String, Command> operations;

    public UserDefinedOperations(Calculator c) {
        this.c = c;
        operations = new HashMap<>();
    }
    
    private void parseFunctions(String s) {
        int index = s.indexOf(":");
        String name = s.substring(0, index);
        s = s.substring(index);
        
        String[] seq = s.split("\\s+");
        UserCommand m = new UserCommand();
        
        for (int i=0; i < seq.length; i++) {
            m.add(sumCommand());
        }
            
    }
    
    private Command sumCommand() {
        return c::sum;
    }
        
    private Command subtractCommand() {
        return c::subtract;
    }
    
    
    
}
