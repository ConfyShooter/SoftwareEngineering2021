package it.unisa.diem.Gruppo20.Model;

import it.unisa.diem.Gruppo20.Model.Exception.ExecuteException;
import it.unisa.diem.Gruppo20.Model.Exception.ParseException;
import it.unisa.diem.Gruppo20.Model.Exception.UserOperationNameException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

    private final Map<String, Command> userOperations;
    private final StandardOperations standardOperations;
    private final Set<String> userOpNames;

    /**
     * Creates an object of this class, using c for operation execution. An
     * HashMap is used to save user-defined operations.
     *
     * @param c
     */
    public Operations(Calculator c) {
        userOperations = new LinkedHashMap<>();
        userOpNames = new LinkedHashSet<>();
        standardOperations = new StandardOperations(c);
    }

    /**
     * Parses a sequence of operations and adds a sequence of command to the
     * map.
     *
     * @param s A string formatted like this "nameOp: op1 op2 a+bj op3...".
     * @throws ParseException
     */
    public void parseOperations(String s) {
        int index = s.indexOf(":");
        if (index == -1) {
            throw new ParseException("To make an operation don't check Function box,\n"
                    + " to insert a new user-operation separe name and definition with ':'.");
        }

        String name = s.substring(0, index).trim().toLowerCase();
        if (standardOperations.isAStandardOperation(name)) {
            throw new UserOperationNameException("You can't assign this name '" + name + "' to an user-defined operation.");
        }

        s = s.substring(index + 1).trim();
        if (s.isEmpty()) { //check if is a valid definition, can't be empty
            throw new ParseException("Impossible to insert user-operation: Definition is empty!");
        }

        UserCommand opCommand = (UserCommand) userOperations.get(name); //check if already exists a user-defined operation with same name
        if (opCommand == null) {
            opCommand = new UserCommand(); //if it's a new operation then create the UserCommand object
        } else {
            opCommand.reset(); //if already exists perform a overwrite(or edit) on the same object
        }

        String[] seq = s.split("\\s+");
        for (String input : seq) { //analyzing list of operation
            input = input.toLowerCase();
            Command comm = userOperations.get(input); //checking if it's an already user-defined operation
            if (comm == null) {
                comm = standardOperations.getCommand(input); //checking if it's a basic operation
            }
            if (comm != null) {
                opCommand.add(input, comm);
            } else {
                opCommand.add(input, standardOperations.insertNumberCommand(input));
            }
        }

        userOperations.put(name, opCommand);
        userOpNames.add(name);
    }

    /**
     * Returns the Command object that performs the operation with the name
     * passed as a parameter.
     *
     * @param name The operation that must be returned.
     * @return Command object or null if the name isn't a valid operation.
     */
    public Command getOperationsCommand(String name) {
        Command c = userOperations.get(name);
        if (c != null) {
            return c;
        } else {
            return standardOperations.getCommand(name);
        }
    }

    /**
     * Returns the list of operations performed by the user-defined
     * operation that has the name passed as a parameter.
     *
     * @param name The user-defined operation name.
     * @return List of String or null if the isn't a valid user-defined
     * operation.
     */
    public List<String> getOperationsNames(String name) {
        UserCommand command = (UserCommand) userOperations.get(name);
        if (command != null) {
            return command.getCommandName();
        }
        return null;
    }

    /**
     * Executes the user-defined operation that has the name passed as a
     * parameter.
     *
     * @param name The user-defined operation name.
     */
    public void executeOperation(String name) {
        UserCommand command = (UserCommand) userOperations.get(name);
        if (command != null) {
            if (!command.isExecutable()) {
                throw new ExecuteException("The implementation of function '" + name + "' has been deleted.");
            }
            command.execute();
            return;
        }

        Command comm = standardOperations.getCommand(name);
        if (comm != null) {
            comm.execute();
        } else {
            throw new ExecuteException("Can't execute this operation, can't find operation with name " + name + ".");
        }
    }

    /**
     * Returns the names of all user-defined operations.
     *
     * @return Set of String.
     */
    public Set<String> userOperationsNames() {
        return userOpNames;
    }

    /**
     * Removes the user-defined operation that has the name passed as a
     * parameter.
     * This method will clean the sequence of operations performed by this user-defined operation,
     * but it will not delete the name by data map, in this way it's possible to redefine it.
     * The user-defined operation name will be deleted from names set.
     * @param name The user-defined operation name.
     */
    public void removeOperations(String name) {
        UserCommand command = (UserCommand) userOperations.get(name);
        if (command != null) {
            command.reset();
        }
        userOpNames.remove(name);
    }

    /**
     * Returns a string of definition of a user-defined operation.
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
     * Saves on a default file the user-defined operations.
     *
     * @param f The file where save the user-defined operations (default =
     * "functions.txt").
     * @throws java.io.IOException
     */
    public void saveOnFile(File f) throws IOException {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(f)))) {
            for (String key : userOperations.keySet()) {
                out.write(operationToString(key) + "\n");
            }
        }
    }

    /**
     * Reloads the previuses user-defined operations from the default file.
     *
     * @param f The file where re-load the user-defined operations (default =
     * "functions.txt").
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
                if (index != -1 && (s.substring(index + 1).isBlank() || (s.length() - 1) == index)) {
                    String name = s.substring(0, index);
                    userOperations.put(name, new UserCommand());
                    userOpNames.add(name);
                } else {
                    parseOperations(s);
                }
            }
        }
    }

}
