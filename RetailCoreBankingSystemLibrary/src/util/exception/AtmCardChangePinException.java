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
public class AtmCardChangePinException extends Exception {

    public AtmCardChangePinException() {
    }

    public AtmCardChangePinException(String msg) {
        super(msg);
    }
}
