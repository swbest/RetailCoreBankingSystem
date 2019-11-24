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
public class DepositAccountExistException extends Exception {

    public DepositAccountExistException() {}
    
    public DepositAccountExistException(String msg) {
        super(msg);
    }
}
