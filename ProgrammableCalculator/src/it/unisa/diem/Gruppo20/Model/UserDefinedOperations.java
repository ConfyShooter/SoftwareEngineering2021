package it.unisa.diem.Gruppo20.Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * This class allow user-defined operations management.
 *
 * @author Gruppo20
 */
public class UserDefinedOperations {

    private final Calculator c;
    private final Map<String, Command> operations;

    /**
     * Create an object of this class, using c for operation execution. An
     * HashMap is used to save user-defined operations.
     *
     * @param c
     */
    public UserDefinedOperations(Calculator c) {
        this.c = c;
        operations = new LinkedHashMap<>();
    }

    /**
     * Parse a sequence of operations and add a suquence of command to the map.
     *
     * @param s A string formatted like this "nameFun: fun1 fun2 a+bj fun3..."
     * @throws RuntimeException
     */
    public void parseOperations(String s) throws RuntimeException {
        int index = s.indexOf(":");
        if (index == -1)
            throw new RuntimeException("To make an operation don't check Function box,\n"
                    + " to insert a new user-operation separe name and definition with ':'.");
        String name = s.substring(0, index).trim().toLowerCase();

        s = s.substring(index + 1).trim();

        UserCommand opCommand = (UserCommand) operations.get(name);
        if (opCommand == null) {
            opCommand = new UserCommand();
        } else {
            opCommand.reset();
        }

        String[] seq = s.split("\\s+");

        for (int i = 0; i < seq.length; i++) {
            String input = seq[i];

            /*if(input.matches("([0-9]*(\\+|\\-){0,1}(([0-9]+j{1})|(j{1}[0-9]+)){0,1})|[0-9]+(\\+|\\-){0,1}j{1}") || input.equalsIgnoreCase("j"))
                opCommand.add(input, insertNumberCommand(c.parseNumber(input)));
            else
                opCommand.add(input, commandOfOperation(input*/
            char sequence[] = input.toCharArray();
            boolean flag = true;

            for (int k = 0; k < sequence.length; k++) { //can use also this input.matches("([0-9]*(\\+|\\-){0,1}(([0-9]+j{1})|(j{1}[0-9]+)){0,1})|[0-9]+(\\+|\\-){0,1}j{1}"); but this not accept j
                if ((sequence[k] >= '0' && sequence[k] <= '9') || input.equalsIgnoreCase("j")) {// in anycase in which the user want to insert a number
                    try {
                        opCommand.add(input, insertNumberCommand(c.parseNumber(input)));
                        flag = false;
                    } catch (NumberFormatException ex) {
                    } finally {
                        break;
                    }
                }
            }

            if (flag) {
                opCommand.add(input, commandOfOperation(input));
            }
        }

        operations.put(name, opCommand);
    }

    /**
     * Return a Command object that represent the operation input.
     *
     * @param input a string labeling an operation
     * @return Command object
     * @throws RuntimeException
     */
    private Command commandOfOperation(String input) throws RuntimeException {
        input = input.toLowerCase();
        Command c = operations.get(input);
        if (c != null) {
            return c;
        }

        switch (input) {
            case "+":
                return sumCommand();
            case "-":
                return subtractCommand();
            case "*":
                return multiplyCommand();
            case "/":
                return divisionCommand();
            case "+-":
                return invertSignCommand();
            case "sqrt":
                return sqrtCommand();
            case "clear":
                return clearCommand();
            case "drop":
                return dropCommand();
            case "dup":
                return dupCommand();
            case "swap":
                return swapCommand();
            case "over":
                return overCommand();
            case "save":
                return saveVariablesCommand();
            case "restore":
                return restoreVariablesCommand();
        }

        if (input.matches(">[a-z]{1}")) {
            return pushVariableCommand(input.charAt(1));
        } else if (input.matches("<[a-z]{1}")) {
            return pullVariableCommand(input.charAt(1));
        } else if (input.matches("\\+[a-z]{1}")) {
            return sumVariableCommand(input.charAt(1));
        } else if (input.matches("\\-[a-z]{1}")) {
            return subtractVariableCommand(input.charAt(1));
        } else {
            throw new RuntimeException("Can't parse \"" + input + "\", try to reinsert it.");
        }

    }

    /**
     * Return the Command object that performs the operation with the name
     * passed as a parameter.
     *
     * @param name The operation that must be returned.
     * @return Command object
     */
    public Command getOperationsCommand(String name) {
        return operations.get(name);
    }

    /**
     * Returns the names of all operations performed by the user-defined
     * operation that has the name passed as a parameter.
     *
     * @param name The user-defined operation name
     * @return List of String
     */
    public List<String> getOperationsNames(String name) {
        UserCommand c = (UserCommand) operations.get(name);
        if (c != null) {
            return c.getMacroName();
        }
        return null;
    }

    /**
     * Execute the user-defined operation that has the name passed as a
     * parameter.
     *
     * @param name The user-defined operation name
     */
    public void executeOperation(String name) throws RuntimeException {
        Command c = operations.get(name);
        if (c != null) {
            c.execute();
        } else {
            throw new RuntimeException("Can't execute this operation, can't find operation with name " + name + ".");
        }
    }

    /**
     * Return the names of all user-defined operations.
     *
     * @return Set of String
     */
    public Set<String> userOperationsNames() {
        return operations.keySet();
    }

    /**
     * Remove the user-defined operation that has the name passaed as a
     * parameter.
     *
     * @param name The user-defined operation name
     */
    public void removeOperations(String name) {
        UserCommand c = (UserCommand) operations.get(name);
        if (c != null) {
            c.reset();
            c = null;
        }

        operations.remove(name);
    }

// NON SO SE NECESSARIA - UTILE SOLO ALLA STAMPA SU FILE
    public String operationsNameToString(String name) {
        UserCommand comm = (UserCommand) operations.get(name);
        String str = "";

        if (comm != null) {
            for (String op : comm.getMacroName()) {
                str += " " + op;
            }
        }
        return str + "\n";
    }

    /**
     * Save on a default file the user-defined operations.
     *
     * @param f The file where save the user-defined operations (default =
     * "functions.txt").
     * @throws java.io.IOException
     */
    public void saveOnFile(File f) throws IOException {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f)))) {
            for (String func : operations.keySet()) {
                out.write(func + ":" + operationsNameToString(func));
                //out.write(func + ": " + getOperationsNames(func) + "\n");
            }
        }
    }

    /**
     * Reload the previuses user-defined operations from the default file.
     *
     * @param f The file where re-load the user-defined operations (default =
     * "functions.txt")
     * @throws java.io.IOException
     */
    public void loadFromFile(File f) throws IOException {
        operations.clear(); // overwrite the operations inserted before
        try (Scanner in = new Scanner(new BufferedReader(new FileReader(f)))) {
            in.useDelimiter("\n+|\n\r+");
            in.useLocale(Locale.US);
            while (in.hasNext()) {
                parseOperations(in.next());
            }
        }
    }

    private Command insertNumberCommand(Complex number) {
        return () -> c.insertNumber(number);
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
}
