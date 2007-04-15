/**
 * InterfacePolyEbay.java
 *
 * Created on 9 vril 2007
 *
 * @author BRUN Joel & DEBONNEL Yann
 */

import java.rmi.*;
import java.util.List;


public interface InterfacePolyEbay extends Remote{

	public ClientConnect connectClient(String nom, String ipClient) throws RemoteException;
	
	public boolean disconnectClient(String nom, String ipClient) throws RemoteException;
	
	public boolean addClientArticle(String ipClient, String article) throws RemoteException;
	
	public void miserArticle(String ipClient, String article, double montant) throws RemoteException;
	
}
