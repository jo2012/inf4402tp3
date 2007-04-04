import java.rmi.*;
//import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**@ Programme pour lancer les serveurs @**/
public class PolyServer {

	/**@ Fonction principale du programme @**/
    public static void main(String args[]) 
    { 
        try 
        {
			//Activation de la securite de RMI
			//System.setSecurityManager( new RMISecurityManager() ); //Signal un acces refuse!

			//Exporte le serveur PolyPaypal
			System.out.println ("PolyPaypal Server started...");
			PolyPaypalImpl obj = new PolyPaypalImpl();
			PolyPaypalRemote stub = (PolyPaypalRemote) UnicastRemoteObject.exportObject(obj, 0);
			
			//Exporte le serveur PolyEbay
			PolyEbayImpl objEbay = new PolyEbayImpl();
			stub.SetEbay(objEbay);

			//Exporte le serveur CreditCheck
			CreditCheckImpl objCredit = new CreditCheckImpl();
			stub.SetCreditCheck(objCredit);
			
			//Recupere le registre et insert l'objet dans le registre de rmi
			Registry registry = LocateRegistry.getRegistry();
	    	registry.bind("PolyPaypalRemote", stub);
	    	
			//Le serveur PolyPaypal est en attente de requettes
			System.out.println ("PolyPaypal Server ready!");
			System.out.println ();
        } 
        catch (RemoteException e) 
        { 
            System.out.println("General PolyPaypal Server Error: " + e); 
            System.exit(0); 
        } 
	    catch (Exception e)
	    {
	    	System.out.println(e.getMessage());
			System.exit(-1);
	    }
    }
}
