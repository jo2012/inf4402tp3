import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.util.Hashtable;
/*
 * ImplPolyPaypal.java
 *
 * Created on April 4, 2007, 10:44 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Yann
 */
public class ImplPolyPaypal implements InterfacePolyPaypal{
    
    /** Creates a new instance of ImplPolyPaypal */
    private Hashtable<String,InterfaceClient> mesClients;
    private InterfacePolyEbay remoteEbay; //Interface PolyEbay
    private InterfaceCreditCheck remoteCreditCheck; //Interface CreditCheck
    private ImplPolyPaypal serveurPolyPaypal;
    
    public ImplPolyPaypal() {
        mesClients = new Hashtable<String,InterfaceClient>();
        
        //Démarrage du serveur PoyPayPal
        try{
            java.rmi.registry.LocateRegistry.createRegistry(1098);
            System.out.println("Registre cree sur le port 1098 pour le serveur PolyPayPal");
            java.rmi.registry.Registry reg = java.rmi.registry.LocateRegistry.getRegistry(1098);
            System.out.println("Registre du port 1098 utilise pour le serveur PolyPayPal");
       
            // Créer et installer le gestionnaire de sécurité.
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new RMISecurityManager());
                 }
        serveurPolyPaypal = new ImplPolyPaypal();
        Naming.rebind("rmi://" + "localhost" + "/" + "PolyPayPal", serveurPolyPaypal);
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Probleme d'initialisation du serveur PolyPaypal");
    }
    
    //Connection au CreditCheck
    try{
        remoteCreditCheck = (InterfaceCreditCheck)Naming.lookup("//" + "localhost" + ":4500/CreditCheck");
    } catch(Exception e) {
        System.out.println("Problème de connexion initiale au CreditCheck");
        e.printStackTrace();
    }
}
/**
 * Fonction permettant à un client de se connecter au serveur PolyPayPal
 *
 * @param InterfaceClient - l'interface du client voulant se connecter
 * @return double - Valeur du credit du client, -1 si le client n'a plus d'argent
 */

public float connect(String c) throws RemoteException {
    float f = remoteCreditCheck.getClientCredit(c.toString());
    if(f>0){
        //mesClients.put(c.toString());
        return remoteCreditCheck.getClientCredit(c.toString());} else
            return -1;
}

public void disconnect(String c) throws RemoteException {
}

public InterfacePolyEbay getEbay() throws RemoteException {
    return remoteEbay;
}

public void setEbay(InterfacePolyEbay ebay) throws RemoteException {
    remoteEbay = ebay;
}

public float checkCredit(String c) throws RemoteException {
    return(remoteCreditCheck.getClientCredit(c));
}

public float updateCredit(String c, float d) throws RemoteException {
    return remoteCreditCheck.updateClientCredit(c,d);
}
    }
