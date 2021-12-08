package it.unisa.diem.Gruppo20.Model;

import it.unisa.diem.Gruppo20.Model.Exception.ExecuteException;
import it.unisa.diem.Gruppo20.Model.Exception.ParseException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * This class allow user-defined operations management.
 *
 * @author Team 20
 */
public class Operations {

    private final Calculator c;
    private final Map<String, Command> userOperations;
    private final Map<String, Command> basicOperations;
    private final Set<String> userOpNames;

    /**
     * Create an object of this class, using c for operation execution. An
     * HashMap is used to save user-defined operations.
     *
     * @param c
     */
    public Operations(Calculator c) {
        this.c = c;
        userOperations = new LinkedHashMap<>();
        userOpNames = new LinkedHashSet<>();
        basicOperations = new HashMap<>(175);
        initializeBasicMap();  
    }

    /**
     * Parse a sequence of operations and add a sequence of command to the map.
     *
     * @param s A string formatted like this "nameFun: fun1 fun2 a+bj fun3...".
     * @throws ParseException
     */
    public void parseOperations(String s) throws ParseException {
        int index = s.indexOf(":");
        if (index == -1) {
            throw new ParseException("To make an operation don't check Function box,\n"
                    + " to insert a new user-operation separe name and definition with ':'.");
        }

        String name = s.substring(0, index).trim().toLowerCase();
        checkOperationName(name); //check if is a valind name, can't be a a defined operation like swap, over, +...

        s = s.substring(index + 1).trim();
        if (s.isEmpty()) { //check if is a valind definition, can't be empty
            throw new ParseException("Impossible to insert user-operation: Definition is empty!");
        }

        UserCommand opCommand = (UserCommand) userOperations.get(name); //check if already exists a user-defined operation with same name
        if (opCommand == null) {
            opCommand = new UserCommand(); //if it's a new operation create the Command object
        } else {
            opCommand.reset(); //if already exists perform a overwrite(or edit)
        }

        String[] seq = s.split("\\s+");
        String input;

        for (int i = 0; i < seq.length; i++) {
            input = seq[i];

            char sequence[] = input.toCharArray();
            boolean flag = true; //setting flag

            for (int k = 0; k < sequence.length; k++) { //can use also this input.matches("([0-9]*(\\+|\\-){0,1}(([0-9]+j{1})|(j{1}[0-9]+)){0,1})|[0-9]+(\\+|\\-){0,1}j{1}"); but this not accept j
                if ((sequence[k] >= '0' && sequence[k] <= '9') || input.equalsIgnoreCase("j")) {// in anycase in which the user want to insert a number
                    try {
                        opCommand.add(input, insertNumberCommand(input)); //add a new insert number command to the operation comman
                        flag = false; //because we add a number so don't need to use commandOfOperation method
                    } catch (NumberFormatException ex) {
                    } finally {
                        break;
                    }
                }
            }

            if (flag) { //if flag still true input isn't a number so we must search for right operation command
                opCommand.add(input, commandOfOperation(input));
            }
        }
        input = null; //clean variable for garbage collector
        userOperations.put(name, opCommand);
        userOpNames.add(name);
    }

    /**
     * Return a Command object that represent the operation input.
     *
     * @param input A string labeling an operation.
     * @return Command object.
     * @throws ParseException
     */
    private Command commandOfOperation(String input) throws ParseException {
        input = input.toLowerCase();
        Command command = userOperations.get(input); //checking if it's an already user-defined operation
        if (command != null) {
            return command;
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
            throw new ParseException("Can't parse \"" + input + "\", try to reinsert it.");
        }
    }

    /**
     * Return the Command object that performs the operation with the name
     * passed as a parameter.
     *
     * @param name The operation that must be returned.
     * @return Command object.
     */
    public Command getOperationsCommand(String name) {
        return userOperations.get(name);
    }

    /**
     * Returns the names of all operations performed by the user-defined
     * operation that has the name passed as a parameter.
     *
     * @param name The user-defined operation name.
     * @return List of String.
     */
    public List<String> getOperationsNames(String name) {
        UserCommand command = (UserCommand) userOperations.get(name);
        if (command != null) {
            return command.getCommandName();
        }
        return null;
    }

    /**
     * Execute the user-defined operation that has the name passed as a
     * parameter.
     *
     * @param name The user-defined operation name.
     */
    public void executeOperation(String name) throws ExecuteException {
        Command command = userOperations.get(name);
        if (command != null) {
            command.execute();
        } else {
            throw new ExecuteException("Can't execute this operation, can't find operation with name " + name + ".");
        }
    }

    /**
     * Return the names of all user-defined operations.
     *
     * @return Set of String.
     */
    public Set<String> userOperationsNames() {
        return userOpNames;
    }

    /**
     * Remove the user-defined operation that has the name passed as a
     * parameter.
     *
     * @param name The user-defined operation name.
     */
    public void removeOperations(String name) {
        UserCommand command = (UserCommand) userOperations.get(name);
        if (command != null) {
            command.reset();
            command = null;
        }
        userOpNames.remove(name);
    }

    /**
     * Return a string of definition of a user-defined operation.
     *
     * @param name The name of the user-defined operation whose we want to
     * print.
     * @return A string of name and all the commands separated by two points.
     */
    public String operationToString(String name) {
        UserCommand comm = (UserCommand) userOperations.get(name);
        String str = name + ":";

        if (comm != null) {
            for (String op : comm.getCommandName()) {
                str += " " + op;
            }
        }
        return str;
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
            for (String key: userOperations.keySet()) {
                out.write(operationToString(key) + "\n");
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
        userOperations.clear(); // overwrite the operations inserted before
        userOpNames.clear();
        try (Scanner in = new Scanner(new BufferedReader(new FileReader(f)))) {
            in.useDelimiter("\n+|\n\r+");
            in.useLocale(Locale.US);
            String s;
            while (in.hasNext()) {
                s = in.next();
                int index = s.indexOf(":");
                if(index != -1 && (s.substring(index +1).isBlank() || (s.length()-1) == index)) {
                    String name = s.substring(0, index);
                    userOperations.put(name, new UserCommand());
                    userOpNames.add(name);
                }
                else
                    parseOperations(s);
            }
        }
    }

    private Command insertNumberCommand(String input) {
        return () -> c.insertNumber(input);
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
    
    private Command phaseCommand() {
        return c::phase;
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

    private void checkOperationName(String name) throws ParseException {
        if (name.equals("+")
                || name.equals("-")
                || name.equals("*")
                || name.equals("/")
                || name.equals("+-")
                || name.equals("sqrt")
                || name.equals("clear")
                || name.equals("drop")
                || name.equals("dup")
                || name.equals("over")
                || name.equals("swap")
                || name.equals("save")
                || name.equals("restore")
                || name.matches(">[a-z]{0,1}")
                || name.matches("<[a-z]{0,1}")
                || name.matches("\\+[a-z]{1}")
                || name.matches("\\-[a-z]{1}")) {
            throw new ParseException("You can't assign this name '" + name + "' to an user-defined operation.");
        }
    }

    private void initializeBasicMap() {
        basicOperations.put("+", sumCommand());
        basicOperations.put("-", subtractCommand());
        basicOperations.put("*", multiplyCommand());
        basicOperations.put("/", divisionCommand());
        basicOperations.put("+-", invertSignCommand());
        basicOperations.put("sqrt", sqrtCommand());
        basicOperations.put("clear", clearCommand());
        basicOperations.put("dup", dupCommand());
        basicOperations.put("drop", dropCommand());
        basicOperations.put("swap", swapCommand());
        basicOperations.put("over", overCommand());
        basicOperations.put("mod", modCommand());
        basicOperations.put("arg", phaseCommand());
        basicOperations.put("cos", cosCommand());
        basicOperations.put("sin", sinCommand());
        basicOperations.put("tan", tanCommand());
        basicOperations.put("acos", arcCosCommand());
        basicOperations.put("asin", arcSinCommand());
        basicOperations.put("atan", arcTanCommand());
        basicOperations.put("pow", powCommand());
        basicOperations.put("exp", expCommand());
        basicOperations.put("log", logCommand());
        //basicOperations.put("?", insertNumberCommand("0"));
        //basicOperations.put(">?", pushVariableCommand('a'));
        //basicOperations.put("<?", pullVariableCommand('b'));
        //basicOperations.put("+?", sumVariableCommand('c'));
        //basicOperations.put("-?", subtractVariableCommand('d'));
        char current = 'a';
        while(current <= 'z') {
            basicOperations.put(">" + String.valueOf(current), pushVariableCommand(current));
            basicOperations.put("<" + String.valueOf(current), pushVariableCommand(current));
            basicOperations.put("+" + String.valueOf(current), pushVariableCommand(current));
            basicOperations.put("-" + String.valueOf(current), pushVariableCommand(current));
            current += 1;
        }
    }
}
