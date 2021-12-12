package it.unisa.diem.Gruppo20.Model;

import it.unisa.diem.Gruppo20.Model.Exception.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * This class manages the Standard calculator's Operations using the Command
 * pattern to execute insert numbers, arithmetic, trigonometric, trascendental
 * and on variable ones.
 *
 *
 * @author Team 20
 */
public class StandardOperations {

    private final Calculator c;
    private final Map<String, Command> standardOpMap;
    private static final int mapCapacity = 64;

    /**
     * Creates an object of this class, using c to perform the operation. An
     * HashMap is used to save basic and advanced calculator operations.
     *
     * @param c
     */
    public StandardOperations(Calculator c) {
        this.c = c;
        standardOpMap = new HashMap<>(mapCapacity);
        initializeMap();
    }

    /**
     * Checks if the key label a basic operation defined in the data map.
     *
     * @param key The basic operation key.
     * @return true if it's a basic operation, otherwise false.
     */
    public boolean isAStandardOperation(String key) {
        return standardOpMap.containsKey(key)
                || key.matches(">[a-z]{1}")
                || key.matches("<[a-z]{1}")
                || key.matches("\\+[a-z]{1}")
                || key.matches("\\-[a-z]{1}");
    }

    /**
     * Returns the Command object that performs the basic operation labeled with
     * the input passed as a parameter.
     *
     * @param input The operation that must be returned.
     * @return Command object.
     */
    public Command getCommand(String input) {
        Command comm = standardOpMap.get(input);
        if (comm != null) {
            return comm;
        }

        if (input.matches(">[a-z]{1}")) {
            comm = pushVariableCommand(input.charAt(1));
        } else if (input.matches("<[a-z]{1}")) {
            comm = pullVariableCommand(input.charAt(1));
        } else if (input.matches("\\+[a-z]{1}")) {
            comm = sumVariableCommand(input.charAt(1));
        } else if (input.matches("\\-[a-z]{1}")) {
            comm = subtractVariableCommand(input.charAt(1));
        } else {
            return null;
        }

        standardOpMap.put(input, comm);
        return comm;
    }

    /**
     * Returns a Command object that performs an insert of the Complex number
     * passed as a parameter.
     *
     * @param input A String that represent the Complex number.
     * @return A Command object.
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

    private void initializeMap() {
        standardOpMap.put("+", sumCommand());
        standardOpMap.put("-", subtractCommand());
        standardOpMap.put("*", multiplyCommand());
        standardOpMap.put("/", divisionCommand());
        standardOpMap.put("+-", invertSignCommand());
        standardOpMap.put("sqrt", sqrtCommand());
        standardOpMap.put("clear", clearCommand());
        standardOpMap.put("dup", dupCommand());
        standardOpMap.put("drop", dropCommand());
        standardOpMap.put("swap", swapCommand());
        standardOpMap.put("over", overCommand());
        standardOpMap.put("mod", modCommand());
        standardOpMap.put("arg", argCommand());
        standardOpMap.put("cos", cosCommand());
        standardOpMap.put("sin", sinCommand());
        standardOpMap.put("tan", tanCommand());
        standardOpMap.put("acos", arcCosCommand());
        standardOpMap.put("asin", arcSinCommand());
        standardOpMap.put("atan", arcTanCommand());
        standardOpMap.put("pow", powCommand());
        standardOpMap.put("exp", expCommand());
        standardOpMap.put("log", logCommand());
        standardOpMap.put("save", saveVariablesCommand());
        standardOpMap.put("restore", restoreVariablesCommand());
    }
}
