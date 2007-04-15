import java.rmi.*;
/*
 * InterfacePolyPaypal.java
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
public interface InterfacePolyPaypal extends Remote{
    
    public float connect(InterfaceClient c) throws RemoteException; 
    public void disconnect(String client) throws RemoteException; 
    public void checkCredit(String c) throws RemoteException;
    public void updateCredit(String c, float d) throws RemoteException;

    
}
