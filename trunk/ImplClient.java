/**
 * ImplClient.java
 *
 * Created on 9 vril 2007
 *
 * @author BRUN Joel & DEBONNEL Yann
 */

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.Vector;


public class ImplClient extends UnicastRemoteObject implements InterfaceClient {
    private String nom;
    private String monIp;
    private String IP_EBAY;    
    private String IP_PAYPAL;
    private Vector<Article> Articles;
    private Article curArticle;
    private InterfacePolyEbay remotePolyEbay;
    private InterfacePolyPaypal remotePolypaypal;
    private Date delaiReseau;
    private String monIP;
    private static int port = 1098;
    
    public ImplClient(String nom) throws RemoteException{
        super();
        monIp ="";
        try {
            monIP = InetAddress.getLocalHost().getHostAddress();
            monIP+=":"+port;
            port++;
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
            
    }
    
    public String getLogin(){return nom;}
    public void setLogin(String s){ nom = s ;}
    
    public void UpdateClient() throws RemoteException{
        
    }
    
    // Cette fonction arrête le serveur personnel de l'utilisateur
     public void arreterServeur(){
        try {
            Naming.unbind("rmi://" + monIP + "/" + nom);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (NotBoundException ex) {
            ex.printStackTrace();
        }
    }
    
    // Cette fonction démarre le serveur personnel de l'utilisateur.
    public void demarrerServeurPerso() {
        try{
            java.rmi.registry.LocateRegistry.createRegistry(1098);
            System.out.println("Registre cree sur le port 1098");
        } catch(Exception e) {
            e.printStackTrace();
        }
        try {
            java.rmi.registry.Registry reg = java.rmi.registry.LocateRegistry.getRegistry(1098);
            System.out.println("Registre du port 1098 utilise");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Créer et installer le gestionnaire de sécurité.
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
        try {
            Naming.rebind("rmi://" + monIP + "/" + nom, this);
        } catch (RemoteException e1) {
            e1.printStackTrace();
            System.out.println("Probleme d'initialisation du serveur.");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("Probleme d'initialisation du serveur.");
        }
    }
}
