import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
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
public class ImplPolyPaypal extends UnicastRemoteObject implements InterfacePolyPaypal{
    
    /** Creates a new instance of ImplPolyPaypal */
    private Hashtable<String,InterfaceClient> mesClients;
    private InterfacePolyEbay remoteEbay; //Interface PolyEbay
    private InterfaceCreditCheck remoteCreditCheck; //Interface CreditCheck
    private ImplPolyPaypal serveurPolyPaypal;
	static private String ipCreditCheck;
	
    public ImplPolyPaypal() throws RemoteException {
        mesClients = new Hashtable<String,InterfaceClient>();
        
        try{
            java.rmi.registry.LocateRegistry.createRegistry(5000);
            System.out.println("Registre cree sur le port 5000 pour le serveur PolyPaypal");
        } catch(Exception e) {
            e.printStackTrace();
        }
        try {
            java.rmi.registry.Registry reg = java.rmi.registry.LocateRegistry.getRegistry(5000);
            System.out.println("Registre utilise sur le port 5000 pour le serveur PolyPaypal");
        } catch (Exception e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
        }
        
       // Créer et installer le gestionnaire de sécurité.
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
        
        try {
            
            Naming.rebind("rmi://" + "localhost:5000/POLYPAYPAL", this);
            connectCreditCheck();
        } catch (RemoteException e1) {
            e1.printStackTrace();
            System.out.println("Serveur POLYPAYPAL ne peut etre lance.");
            System.out.println("Veuillez reessayer la connection ulterieurement");
            System.exit(0);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("Serveur POLYPAYPAL ne peut etre lance.");
            System.out.println("Veuillez reessayer la connection ulterieurement");
            System.exit(0);
        }
}
/**
 * Fonction permettant à un client de se connecter au serveur PolyPayPal
 *
 * @param InterfaceClient - l'interface du client voulant se connecter
 * @return double - Valeur du credit du client, -1 si le client n'a plus d'argent
 */

public float connect(String c) throws RemoteException {
    float f = remoteCreditCheck.getClientCredit(c);
    if(f>0){
        //mesClients.put(c.toString());
        return remoteCreditCheck.getClientCredit(c);} else
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

public static void main(String args[]) {
                try {
					if(args[0]!=null)
						ipCreditCheck = args[0];
                    new ImplPolyPaypal();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
    }

    private void connectCreditCheck() {
          try {
			  System.out.println("Connection @ " + ipCreditCheck);
			 	remoteCreditCheck = (InterfaceCreditCheck) Naming.lookup("//" + ipCreditCheck + ":4600" + "/" + "CREDITCHECK");
				System.out.println("Connection au serveur CREDITCHECK etablie.");
		  	}
            catch (RemoteException ex) {
                ex.printStackTrace();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            } catch (NotBoundException ex) {
                ex.printStackTrace();
            }
    }
}
