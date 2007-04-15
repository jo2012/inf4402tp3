/**
 * ImplClient.java
 *
 * Created on 9 vril 2007
 *
 * @author BRUN Joel & DEBONNEL Yann
 */

import java.rmi.server.UnicastRemoteObject;


public class ImplClient extends UnicastRemoteObject implements InterfaceClient {
	private String nom;
	private int idArticle;
	private InterfacePolyEbay remotePolyEbay;
	private InterfacePolyPaypal remotePolypaypal;
	private Time delaiReseau;
	private String monIP;
	private static int port = 1098;
	public void ImplClient(Object String nom) {
	
	}
	
	public void UpdateClient() {
	
	}
}
