/**
 * ImplClient.java
 *
 * Created on 9 vril 2007
 *
 * @author BRUN Joel & DEBONNEL Yann
 */

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;


public class ImplClient extends UnicastRemoteObject implements InterfaceClient {
	private String nom;
	private int idArticle;
	private InterfacePolyEbay remotePolyEbay;
	private InterfacePolyPaypal remotePolypaypal;
	private Date delaiReseau;
	private String monIP;
	private static int port = 1098;
	public ImplClient(String nom) throws RemoteException{
	super();
	}
	
	public void UpdateClient() throws RemoteException{
	
	}
}
