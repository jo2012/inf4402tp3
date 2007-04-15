/**
 * InterfaceClient.java
 *
 * Created on 9 vril 2007
 *
 * @author BRUN Joel & DEBONNEL Yann
 */


import java.rmi.*;

public interface InterfaceClient extends Remote{
    
    public void UpdateClient(Article Art) throws RemoteException;
}
