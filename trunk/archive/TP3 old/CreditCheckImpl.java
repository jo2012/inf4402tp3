import java.rmi.*; 
import java.util.*; 
import java.rmi.server.UnicastRemoteObject;

/**@ Classe permettant d'implementer le serveur CreditCheck @**/
public class CreditCheckImpl implements CreditCheckRemote
{ 
	private int lastError;
	private Vector listClient;

	/**@ Constructeur @**/
	public CreditCheckImpl()
	{
		listClient = new Vector(); //Cree la liste des clients
		for(int i=1; i<Constant.CLIENT_COUNT+1; i++) //Cree tous les clients
		{
			ObjectClient cli = new ObjectClient("client"+i, 50.00);
			listClient.add(cli); //Ajoute le client dans la liste
		}
		init(); //Initialise les connections
		lastError = Constant.NO_ERROR; //Aucune erreur
	}

	/**@ Description: Permet de recuperer le solde d'un client @**/
    public synchronized double CheckCredit( String client )
	{ 
		if(listClient==null) //Si aucun client
		{
			lastError = Constant.NO_CLIENTS;
			return(-1); //Retourne echec
		}
		int _index = IndexClient(client); //Recupere l'index du client
		if(_index==Constant.CLIENT_NOT_FOUND)
			return(-1);
		return(GetClient(_index).ClientCredit()); //Recupere le solde associe a l'index
	}

	/**@ Description: Permet de modifier le solde du compte d'un client @**/
	public synchronized boolean ModifyCredit( String client, double credit, boolean bAdd)
	{
		if(listClient==null) //Si aucun client
		{
			lastError = Constant.NO_CLIENTS;
			return(false); //retourne echec
		}
		int _index = IndexClient(client); //recupere l'index du client
		if(_index==Constant.CLIENT_NOT_FOUND)
			return(false); //Echec si aucun client
		return(GetClient(_index).ModifyCredit(credit, bAdd)); //Modifie le solde du client associe a l'index
	}

	/**@ Description: Permet de recuperer la derniere erreur @**/
	public synchronized int GetLastError()
	{
		return(lastError); //Retourne la derniere erreur
	}

	/**@ Description: Permet de recupere l'index d'un client dans la liste @**/
	public synchronized int IndexClient( String client )
	{
		//Parcours tous les clients
		for(int i=0; i<listClient.size(); i++)
		{
			ObjectClient cli = (ObjectClient)listClient.elementAt(i);
			if(client.equals(cli.ClientName())) //Si c'est le client recherche
				return(i); //Retourne son index
		}
		lastError = Constant.CLIENT_NOT_FOUND; //Retourne aucun client
		return(lastError);
	}

	/**@ Description: Permet de recuperer une instance de l'objet client associer a index @**/
	public synchronized ObjectClient GetClient(int index)
	{
		if(index<0) //Si index invalide
		{
			lastError=Constant.INVALID_ARGS;
			return(null); //Retourne echec
		}
		return (ObjectClient)listClient.elementAt(index); //Retourne l'intance recherchee
	}

	/**@ Description: Permet de recuperer une instance de l'objet client associer a son nom @**/
	public synchronized ObjectClient GetClient( String client )
	{
		int _index = IndexClient(client); //Recupere l'index
		return GetClient(_index);//Retourne l'instance associee
	}

	/**@ Permet d'initialiser et d'exporter le serveur CreditCheck @**/
    public boolean init()
    {
        try 
        {
			//Activation de la securite de RMI
			//System.setSecurityManager( new RMISecurityManager() ); //Signal un acces refuse!
      
        	//Cree l'objet distribue et l'exporte
        	System.out.println ("CreditCheck Server started...");
        	UnicastRemoteObject.exportObject( this ); //Exporte le serveur CreditCheck
			System.out.println ("CreditCheck Server ready!");
			return true; //Retourne succes
        } 
        catch (RemoteException e) 
        { 
            System.out.println("General CreditCheck Server Error: " + e); 
            System.exit(0); return false; //Retourne echec
        } 
    }
} 
