package it.unisa.diem.Gruppo20.Model;

import it.unisa.diem.Gruppo20.Model.Exception.ParseException;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author Team 20
 */
public class BasicOperations {
    
    private final Calculator c;
    private final Map<String, Command> basicOpMap;
    private static final int mapCapacity = 175;

    /**
     * Create an object of this class, using c for operation execution. An
     * HashMap is used to save basic calculator operations.
     *
     * @param c
     */
    public BasicOperations(Calculator c) {
        this.c = c;
        basicOpMap = new HashMap<>(mapCapacity);
        initializeBasicMap();  
    }
    
    /**
     *  Check if the key label a basic operation defined in the data map.
     * @param key The basic operation key.
     * @return true if it's a basic operation, otherwise false;
     */
    public boolean isABasicOperation(String key) {
        return basicOpMap.containsKey(key);
    }
    
    /**
     * Return the Command object that performs the basic operation labeled with the input
     * passed as a parameter.
     *
     * @param input The operation that must be returned.
     * @return Command object.
     */
    public Command getCommand(String input) {
        Command comm = basicOpMap.get(input);
        if(comm != null)
            return comm;
        
        if (input.matches(">[a-z]{1}")) {
            return pushVariableCommand(input.charAt(1));
        } else if (input.matches("<[a-z]{1}")) {
            return pullVariableCommand(input.charAt(1));
        } else if (input.matches("\\+[a-z]{1}")) {
            return sumVariableCommand(input.charAt(1));
        } else if (input.matches("\\-[a-z]{1}")) {
            return subtractVariableCommand(input.charAt(1));
        } else {
            //throw new ParseException("Can't parse \"" + input + "\", try to reinsert it.");
            return null;
        }
    }
    
    /**
     *  Return a Command object that performs an insert of the Complex number passed as a parameter. 
     * @param input a String that represent the Complex number.
     * @return a Command object.
     * @throws ParseException if the input string is not a valid number.
     */
    public Command insertNumberCommand(String input) {
        try {
            Complex number = c.parseNumber(input);
            return () -> c.insertNumber(number);
        } catch (NumberFormatException ex) {
            throw new ParseException("Can't parse \"" + input + "\", try to reinsert it.");
        }
    }

    private Command sumCommand() {
        return c::sum;
    }

    private Command subtractCommand() {
        return c::subtract;
    }

    private Command multiplyCommand() {
        return c::multiply;
    }

    private Command divisionCommand() {
        return c::division;
    }

    private Command sqrtCommand() {
        return c::sqrt;
    }

    private Command invertSignCommand() {
        return c::invertSign;
    }

    private Command clearCommand() {
        return c::clear;
    }

    private Command dropCommand() {
        return c::drop;
    }

    private Command dupCommand() {
        return c::dup;
    }

    private Command swapCommand() {
        return c::swap;
    }

    private Command overCommand() {
        return c::over;
    }

    private Command pushVariableCommand(char ch) {
        return () -> c.pushVariable(ch);
    }

    private Command pullVariableCommand(char ch) {
        return () -> c.pullVariable(ch);
    }

    private Command sumVariableCommand(char ch) {
        return () -> c.sumVariable(ch);
    }

    private Command subtractVariableCommand(char ch) {
        return () -> c.subtractVariable(ch);
    }

    private Command saveVariablesCommand() {
        return c::saveVariables;
    }

    private Command restoreVariablesCommand() {
        return c::restoreVariables;
    }
    
    private Command modCommand() {
        return c::mod;
    }
    
    private Command argCommand() {
        return c::arg;
    }
    
    private Command cosCommand() {
        return c::cos;
    }
    
    private Command arcCosCommand() {
        return c::arcCos;
    }
            
    private Command sinCommand() {
        return c::sin;
    }
    
    private Command arcSinCommand() {
        return c::arcSin;
    }
    
    private Command tanCommand() {
        return c::tan;
    }
    
    private Command arcTanCommand() {
        return c::arcTan;
    }
    
    private Command powCommand() {
        return c::pow;
    }
    
    private Command expCommand() {
        return c::exp;
    }
    
    private Command logCommand() {
        return c::log;
    }

    private void initializeBasicMap() {
        basicOpMap.put("+", sumCommand());
        basicOpMap.put("-", subtractCommand());
        basicOpMap.put("*", multiplyCommand());
        basicOpMap.put("/", divisionCommand());
        basicOpMap.put("+-", invertSignCommand());
        basicOpMap.put("sqrt", sqrtCommand());
        basicOpMap.put("clear", clearCommand());
        basicOpMap.put("dup", dupCommand());
        basicOpMap.put("drop", dropCommand());
        basicOpMap.put("swap", swapCommand());
        basicOpMap.put("over", overCommand());
        basicOpMap.put("mod", modCommand());
        basicOpMap.put("arg", argCommand());
        basicOpMap.put("cos", cosCommand());
        basicOpMap.put("sin", sinCommand());
        basicOpMap.put("tan", tanCommand());
        basicOpMap.put("acos", arcCosCommand());
        basicOpMap.put("asin", arcSinCommand());
        basicOpMap.put("atan", arcTanCommand());
        basicOpMap.put("pow", powCommand());
        basicOpMap.put("exp", expCommand());
        basicOpMap.put("log", logCommand());
        basicOpMap.put("save", saveVariablesCommand());
        basicOpMap.put("restore", restoreVariablesCommand());
        char current = 'a';
        while(current <= 'z') {
            basicOpMap.put(">" + String.valueOf(current), pushVariableCommand(current));
            basicOpMap.put("<" + String.valueOf(current), pushVariableCommand(current));
            basicOpMap.put("+" + String.valueOf(current), pushVariableCommand(current));
            basicOpMap.put("-" + String.valueOf(current), pushVariableCommand(current));
            current += 1;
        }
    }
}
