import java.rmi.*; 
import java.util.*; 

/**@ Classe permettant d'implementer les methodes de l'objet reparti ou distant				 @**/
/**@ Fichier:  PolyPaypalImpl.java															 @**/
/**@ Description: Classe permettant d'implementer les methodes de l'objet reparti ou distant @**/
public class PolyPaypalImpl implements PolyPaypalRemote 
{ 
    private Vector clients;
    private PolyEbayRemote objEbay; //Interface PolyEbay
	private CreditCheckRemote objCredit; //Interface CreditCheck

	/**@ Constructeur @**/
    public PolyPaypalImpl() throws RemoteException 
    { 
		//Cree la liste des clients
		clients = new Vector();
    } 

	/**@ Fonction:  connect 																 @**/
	/**@ Description: Permet aux clients de se connecter au serveur							 @**/
	/**@ Parametres:																		 @**/
	/**@ 			ClientRemote cli: Interface d'acces au client							 @**/
	/**@ 			client client: Nom du client 											 @**/
	/**@ Valeur de retour: (double) Retourne le montant disponible dans le compte du client	 @**/
    public synchronized double connect( ClientRemote cli, String client ) throws RemoteException 
    { 
		//Verifie le credit du client avec le serveur CrediCheck
		double icredit = objCredit.CheckCredit(client);
		
		//Si le client n'a pas suffisament d'argent dans son compte
		if(icredit<=0) return(-1);
		
		//synchronized(clients)
		//{
			System.out.println("PolyPaypal: Connection de "+client+"!");
			clients.add( new PolyPaypalClient( cli, client) ); 
		//}
		return(icredit);
    } 

	/**@ Fonction:  CheckCredit 												 @**/
	/**@ Description: Permet de verifier la disponibilite d'un montant 			 @**/
	/**@ Parametres:												 			 @**/
	/**@ 			double dollard: montant a verifier							 @**/
	/**@ 			String client: Nom du client 								 @**/
	/**@ Valeur de retour: (boolean) Retourne true si le client a assez d'argent @**/
	public synchronized boolean CheckCredit(String client, double dollard) throws RemoteException 
	{
		//Verifie le credit du client avec le serveur CrediCheck
		double icredit = objCredit.CheckCredit(client);
		return (dollard<=icredit);
	}

	/**@ Fonction:  CheckCredit						     								    @**/
	/**@ Description: Permet d'avoir le montant disponible dans le compte d'un client	    @**/
	/**@ Parametres:																		@**/
	/**@ 			String client: Nom du client											@**/
	/**@ Valeur de retour: (double) Retourne le montant disponible dans le compte du client @**/
	public double CheckCredit(String client) throws RemoteException 
	{
		return objCredit.CheckCredit(client);
	}

	/**@ Fonction:  ModifyCredit 												 @**/
	/**@ Description: Permet de modifier le montant du compte d'un client		 @**/
	/**@ Parametres:												 			 @**/
	/**@ 			double dollard: montant a ajouter ou enlever				 @**/
	/**@ 			String client: Nom du client 								 @**/
	/**@			boolean action: true si on ajouter et false on enleve		 @**/
	/**@ Valeur de retour: (boolean) Retourne true si le client a assez d'argent @**/
	public synchronized boolean ModifyCredit(String client, double dollard, boolean action) throws RemoteException 
	{
		return(objCredit.ModifyCredit(client, dollard, action));
	}

	/**@ Fonction:  disconnect   												 @**/
	/**@ Description: Permet aux clients de se deconnecter du server    		 @**/
	/**@ Parametres: Aucun											 			 @**/
	/**@ Valeur de retour: Aucune												 @**/
    public synchronized void disconnect( String client ) 
    { 
    	PolyPaypalClient cli; 

		//Parcours les clients afin de deconnecter le clients qui invoque
        for (int i = 0; i < clients.size(); i++) 
        { 
			//Recupere une copie locale
        	cli = (PolyPaypalClient)clients.elementAt( i ); 
            if ( client.equals( cli.getUserName() ) ) //Si c'est celui recherche
            { 
				try
	        	{
					//synchronized(clients) //Synchronise la liste des utilisateurs ou clients
					//{
						cli.pClient.MessageNotify("PolyPaypal: Vous etes deconnecte!");
						System.out.println("PolyPaypal: Deconnection de "+client+"!");
		                clients.removeElementAt(i); //Le retirer de la liste
					//}
	        	}
				catch(Exception e){}
                return; 
            } 
        } 
    } 

	/**@ Fonction:  GetClient	 												 @**/
	/**@ Description: Permet de verifier l'existance d'un client        		 @**/
	/**@ Parametres:												 			 @**/
	/**@ 			String client: Nom du client 								 @**/
	/**@ Valeur de retour: (boolean) Retourne true si le client existe			 @**/
    public synchronized boolean GetClient(String client)
    {
    	PolyPaypalClient cli; 
        for (int i = 0; i < clients.size(); i++) 
        { 
        	try
        	{
        		cli = (PolyPaypalClient) clients.elementAt( i ); 
	            if ( client.equals( cli.getUserName() ) ) 
	            { 
					//Retourne sa presence
	                return(true);
	            } 
        	}
        	catch(Exception e)
        	{
        		System.out.println("PolyPaypal: Exception in GetClient() for "+client+"!");
        		return false;
        	}
        }
        return false;
    }

	/**@ Fonction:  GetEbay		 												 	@**/
	/**@ Description: Permet de recuperer l'instance du serveur PolyEbay		 	@**/
	/**@ Parametres: Aucun											 			 	@**/
	/**@ Valeur de retour: (PolyEbayRemote) Retourne l'instance du Serveur PolyEbay	@**/
    public synchronized PolyEbayRemote GetEbay()
    {
    	return(objEbay);
    }

	/**@ Fonction:  SetEbay														 @**/
	/**@ Description: Permet d'avoir l'instance du serveur PolyEbay				 @**/
	/**@ Parametres:												 			 @**/
	/**@ 			PolyEbayRemote ebay: l'instance du Serveur PolyEbay			 @**/
	/**@ Valeur de retour: Aucune												 @**/
    public synchronized void SetEbay(PolyEbayRemote ebay)
    {
    	objEbay=ebay;
    }

	/**@ Fonction:  SetCreditCheck												 @**/
	/**@ Description: Permet d'avoir l'instance du serveur CreditCheck			 @**/
	/**@ Parametres:												 			 @**/
	/**@ 			CreditCheckRemote credit: l'instance du Serveur CreditCheck	 @**/
	/**@ Valeur de retour: Aucune												 @**/
	public void SetCreditCheck(CreditCheckRemote credit)
	{
		objCredit=credit;
	}

	/**@ Fonction:  ClientCount 												 @**/
	/**@ Description: Permet d'avoir le nombre de clients connectes				 @**/
	/**@ Parametres: Aucun											 			 @**/
	/**@ Valeur de retour: (int) Retourne le nombre de clients					 @**/
    public synchronized int ClientCount() 
    { 
		//Nombre de client connectes
        return clients.size(); 
    } 

	/**@ Fonction:  PostMessage 												 @**/
	/**@ Description: Permet de poster un message aux utilisateurs				 @**/
	/**@ Parametres:												 			 @**/
	/**@ 			String msg: Message a envoyer								 @**/
	/**@ Valeur de retour: Aucune												 @**/
    public synchronized void PostMessage(String msg) 
    { 
		//Objet local
    	PolyPaypalClient cli; 
	
		//Parcours tous les utilisateurs
        for (int i = 0; i < clients.size(); i++) 
        { 
        	cli = (PolyPaypalClient) clients.elementAt( i ); 
        	synchronized(cli)//Synchronise le thread du client
            {
        		if ( ! cli.PostMessage( msg ) ) 
        			clients.removeElementAt( i ); //retire PolyPaypalClient 
            }
        } 
    } 
} 
