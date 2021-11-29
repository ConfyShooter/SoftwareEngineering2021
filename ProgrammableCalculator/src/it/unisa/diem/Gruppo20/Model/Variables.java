package it.unisa.diem.Gruppo20.Model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Gruppo20
 */
public class Variables {
    private Map<Character, Complex> data;
    private Deque<Map<Character, Complex>> backupsStack;

    public Variables(Map<Character, Complex> data, Deque<Map<Character, Complex>> backupsStack) {
        this.data = data;
        this.backupsStack = backupsStack;
    }

    public Variables() {
        this(new HashMap<>(), new ArrayDeque<>());
    }

    public Map<Character, Complex> getCurrentValues() {
        return data;
    }
    
    public Complex getVariable(char c) {
        return null;
    }
    
    public Complex setVariable(char c, Complex number) {
        return null;
    }
    
    public Complex sumVariable(char c, Complex number) {
        return null;
    }
    
    public Complex subVariable(char c, Complex number) {
        return null;
    }
    
    public void backup() {
        ;
    }
    
    public void restore() {
        ;
    }
    
}
