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
public class DepositAccountNotFoundException extends Exception {
    
    public DepositAccountNotFoundException() {}
    
    public DepositAccountNotFoundException(String msg) {
        super(msg);
    }    
}
