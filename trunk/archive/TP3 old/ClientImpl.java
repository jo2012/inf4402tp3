import java.rmi.*; 
import java.rmi.server.*;
//import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**@ Classe permettant d'implementer le client distant @**/
public class ClientImpl implements ClientRemote{
	
	private String pUser;
	private FrameClient objFrame;
	private PolyEbayRemote objEbay;
	private PolyPaypalRemote objPaypal;
	
	//Constructeur
    public ClientImpl(String user)
    { 
        this.pUser = user; //recupere le nom du client
    } 
	
	/**@ Permet de recuperer le nom du client @**/
	public synchronized String GetUser()
	{
		return this.pUser; //retourne le nom du client
	}
    
	/**@ Permet d'initialiser et d'obtenir les references sur l'objet distant @**/
    public boolean init() throws RemoteException 
    { 
        try 
        { 
			//Gestion de securite de RMI
			//System.setSecurityManager( new RMISecurityManager() );  //Signal un acces refuse!

        	//Exporte le client distant
        	System.out.println( "Client started..." ); 
        	UnicastRemoteObject.exportObject( this ); 

			//Recupere le registre de rmi
        	System.out.println( "Client LocateRegistry..." ); 
			Registry registry = LocateRegistry.getRegistry(null);

			//recupere une reference de l'objet distant
			objPaypal = (PolyPaypalRemote) registry.lookup("PolyPaypalRemote");
			objEbay = objPaypal.GetEbay();
            
			//Cree le thread devant gerer la communication avec le server
            objFrame = new FrameClient(this.pUser, objPaypal, objEbay, this);
            objFrame.InitFrame(); //Initialise l'interface client
            objFrame.setVisible(true); //Rend l'interface visite au client
            return true; //retourne succes
        } 
        catch ( NotBoundException e ) 
        { 
            System.out.println("Service RMI non disponible."); 
            System.exit(0); 
        }
        catch ( Exception e ) 
        { 
            System.out.println("Impossible de localiser le serveur: " + e); 
            System.exit(0); 
        } 
        return false;
    }

	/**@ Description: Permet de mettre a jour les articles chez le client @**/
    public synchronized void MakeUpdate()
    {
    	if(objFrame==null) return; //Si aucune interface
    	objFrame.UpdateArticles(false); //Met a jour les articles
    }

	/**@ Description: Permet de recuperer l'instance de PolyPaypal associee @**/
	public synchronized PolyPaypalRemote GetPaypal()
	{
		if(objFrame==null) return null; //Si aucune interface client
		return(objFrame.GetPaypal()); //Retourne l'instance associee
	}

	/**@ Permet au server d'afficher une notification @**/
	public synchronized void MessageNotify( String msg )
	{
		if(objFrame==null) return; //Si aucune interface client
		objFrame.MsgOut(msg); //Affiche le message chez le client
	}
}
