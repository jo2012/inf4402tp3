import java.rmi.RemoteException;
import java.util.Hashtable;
/*
 * ImplCreditCheck.java
 *
 * Created on April 4, 2007, 10:46 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Yann
 */
public class ImplCreditCheck implements InterfaceCrediCheck{
    
    private Hashtable<String, Float> mesClients = new Hashtable<String, Float>();
     /**
     * Creates a new instance of ImplCreditCheck
     *
     * The ImplCreditCheck can be later used to verify credit of a client
     *
     * @param      void
     */
    public ImplCreditCheck() {
        for(int i = 0; i<3; i++)
            mesClients.put("Client"+i,new Float(50.0));
    }

    /**
     * Returns the credit of specified client
     *
     * 
     * @param      String c - the name of the client
     * @return     float - the value of the client's credit
     */
    public float getClientCredit(String c) throws RemoteException {
        return mesClients.get(c).floatValue();
    }

    /**
     * Updates the credit of specified client
     *
     * 
     * @param      float d - the variation of the client's credit
     * @return     void
     */
    public void updateClientCredit(String c, float d) throws RemoteException {
        float f = mesClients.get(c);
        f+=d;
        mesClients.put(c, new Float(f));
    }
}
