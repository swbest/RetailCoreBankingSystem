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
public class AtmCardNumberExistException extends Exception {
    
    public AtmCardNumberExistException() {}
    
    public AtmCardNumberExistException(String msg) {
        super(msg);
    }
}
