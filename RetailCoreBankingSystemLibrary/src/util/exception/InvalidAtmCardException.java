/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.exception;

/**
 *
 * @author dtjldamien
 */
public class InvalidAtmCardException extends Exception {
    
    public InvalidAtmCardException() {}
    
    public InvalidAtmCardException(String msg) {
        super(msg);
    }
}
