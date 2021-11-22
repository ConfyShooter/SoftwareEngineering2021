package it.unisa.diem.SE2021.Model;

/**
 *
 * @author Gruppo 20
 */
public interface Calculator {
    
    public boolean insert(String number);
    public boolean sum();
    public boolean subtract();
    public boolean multiply();
    public boolean division();
    public boolean sqrt();
    public boolean invertSign();
    public boolean clear();
    public boolean drop();
    public boolean dup();
    public boolean over();
    public boolean saveIntoVariable(char c);
    public boolean saveFromVariable(char c);
    public boolean sumWithVariable(char c);
    public boolean subtractWithVariable(char c);
    public boolean saveVariables();
    public boolean restoreVariables();
    public boolean executeOperation(String name);
    public boolean addOperation(String name,String[] op);
}
