/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.Gruppo20.Model.Exception;

/**
 *
 * @author Team 20
 */
public class UserOperationNameException extends RuntimeException {

    /**
     * Creates a new instance of <code>UserOperationNameException</code> without
     * detail message.
     */
    public UserOperationNameException() {
    }

    /**
     * Constructs an instance of <code>UserOperationNameException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public UserOperationNameException(String msg) {
        super(msg);
    }
}
