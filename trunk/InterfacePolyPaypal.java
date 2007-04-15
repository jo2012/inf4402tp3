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
    
    public double connect(InterfaceClient c) throws RemoteException; 
    public void disconnect(String client) throws RemoteException; 
    public InterfacePolyEbay getEbay() throws RemoteException; 
    public void setEbay(InterfacePolyEbay ebay) throws RemoteException; 
    public boolean checkCredit(String client) throws RemoteException;
    public boolean updateCredit(String client, float d) throws RemoteException;

    
}
