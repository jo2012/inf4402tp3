/**
 * InterfacePolyEbay.java
 *
 * Created on 9 vril 2007
 *
 * @author BRUN Joel & DEBONNEL Yann
 */

import java.rmi.*;


public interface InterfacePolyEbay extends Remote{

	public List<Article> connectClient(Object String nom, Object String ipClient) throws RemoteException;
	
	public boolean disconnectClient(Object String nom, Object String ipClient) throws RemoteException;
	
	public boolean addClient(Object String ipClient, Object int idArticle) throws RemoteException;
	
	public void miserArticle(Object int idArticle) throws RemoteException;
	
}
