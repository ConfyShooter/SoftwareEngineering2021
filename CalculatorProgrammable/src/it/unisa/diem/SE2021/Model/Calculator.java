package it.unisa.diem.SE2021.Model;

/**
 *
 * @author Gruppo 20
 */
public interface Calculator {
    
    /**
     *
     * @param number
     * @return
     */
    public boolean insert(String number);

    /**
     *
     * @return
     */
    public boolean sum();

    /**
     *
     * @return
     */
    public boolean subtract();

    /**
     *
     * @return
     */
    public boolean multiply();

    /**
     *
     * @return
     */
    public boolean division();

    /**
     *
     * @return
     */
    public boolean sqrt();

    /**
     *
     * @return
     */
    public boolean invertSign();

    /**
     *
     * @return
     */
    public boolean clear();

    /**
     *
     * @return
     */
    public boolean drop();

    /**
     *
     * @return
     */
    public boolean dup();

    /**
     *
     * @return
     */
    public boolean over();

    /**
     *
     * @param c
     * @return
     */
    public boolean saveIntoVariable(char c);

    /**
     *
     * @param c
     * @return
     */
    public boolean saveFromVariable(char c);

    /**
     *
     * @param c
     * @return
     */
    public boolean sumWithVariable(char c);

    /**
     *
     * @param c
     * @return
     */
    public boolean subtractWithVariable(char c);

    /**
     *
     * @return
     */
    public boolean saveVariables();

    /**
     *
     * @return
     */
    public boolean restoreVariables();

    /**
     *
     * @param name
     * @return
     */
    public boolean executeOperation(String name);

    /**
     *
     * @param name
     * @param op
     * @return
     */
    public boolean addOperation(String name,String[] op);
}
