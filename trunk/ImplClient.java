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
    private ClientConnect cc;
    private Article curArticle;
    private InterfacePolyEbay remotePolyEbay;
    private InterfacePolyPaypal remotePolypaypal;
    private Date delaiReseau;
    private float credit;
    private static int port = 1098;
    private int curArtIndex=-1;
    
    public ImplClient(String n) throws RemoteException{
        super();
        monIp ="";
        nom = n;
        try {
            monIp = InetAddress.getLocalHost().getHostAddress();
            monIp+=":"+port;
            port++;
            credit =0;
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }            
    }
    
    public String getLogin(){return nom;}
    public float getCredit(){return credit;}
    public String getTimeServeur(){return cc.getTimeServeur(curArtIndex);}
    public void setLogin(String s){ nom = s ;}
    public void setcurArticle(int i){
        curArticle = cc.getArticle(i);
        curArtIndex = i;
        try {
            remotePolyEbay.addClientArticle(monIp, curArticle.getNom());
        } catch (RemoteException ex) {
            ex.printStackTrace();
            System.err.println("probleme lors du choix de l'article de mise "+curArticle.getNom());
        }
    }
    public Article getcurArticle(){ return curArticle;}
    public Vector<Article> getListArticle(){return cc.getArticles();}
    
    public void faireMise(double montant){
        try {
            remotePolyEbay.miserArticle(monIp, curArticle.getNom(), (float)montant);
        } catch (RemoteException ex) {
            ex.printStackTrace();
            System.err.println("impossible de faire la mise de "+montant);
        }
    }
    
    public void UpdateClient(Article art) throws RemoteException{
        curArticle = art; System.out.println("mise a jour recu");
        cc.UpdateArticle(curArtIndex, art);
        credit = remotePolypaypal.checkCredit(nom);
    }
    
    // Cette fonction permet la connexion au gestionnaire de fichier dont l'adresse IP est 
    // spécifié en paramêtre.
    public boolean connexionEbay(String ipEbay, String ipPaypal) {
        IP_EBAY = ipEbay;
        IP_PAYPAL = ipPaypal;
        demarrerServeurPerso();
        try{
            remotePolypaypal = (InterfacePolyPaypal)Naming.lookup("//" + IP_PAYPAL + ":5000/POLYPAYPAL");
            credit = remotePolypaypal.connect(nom);
            if(credit>0){
            remotePolyEbay = (InterfacePolyEbay)Naming.lookup("//" + IP_EBAY + ":4500/POLYEBAY");
            cc = remotePolyEbay.connectClient(nom,monIp);
            return cc.isConnected();
            }
            else return false;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    // Cette fonction permet la déconnexion au gestionnaire de fichier auquel l'utilisateur està
    // présentement connecté.
    public boolean deconnexionEbay() {
        try{
            remotePolyEbay.disconnectClient(nom,monIp);
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Cette fonction arrête le serveur personnel de l'utilisateur
     public void arreterServeurPerso(){
        try {
            Naming.unbind("rmi://" + monIp + "/" + nom);
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
        /*if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }*/
        try {
            Naming.rebind("rmi://" + monIp + "/" + nom, this);
        } catch (RemoteException e1) {
            e1.printStackTrace();
            System.out.println("Probleme d'initialisation du serveur.");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("Probleme d'initialisation du serveur.");
        }
    }
}
