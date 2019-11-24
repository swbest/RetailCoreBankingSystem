package automatedtellermachineclient;

import ejb.session.stateless.AtmCardSessionBeanRemote;
import javax.ejb.EJB;
import util.exception.AtmCardIncorrectPinException;
import util.exception.AtmCardNotFoundException;

/**
 *
 * @author sw_be
 */
public class Main {

    @EJB
    private static AtmCardSessionBeanRemote atmCardSessionBeanRemote;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws AtmCardNotFoundException, AtmCardIncorrectPinException {
        // TODO code application logic here
        MainApp mainApp = new MainApp(atmCardSessionBeanRemote);
        mainApp.runApp();
    }
}
