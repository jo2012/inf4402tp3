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
    
    public float connect(String c) throws RemoteException; 
    public void disconnect(String c) throws RemoteException; 
    public float checkCredit(String c) throws RemoteException;
    public float updateCredit(String c, float d) throws RemoteException;

    
}
