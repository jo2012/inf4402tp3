import java.util.*;
import java.rmi.*;
import javax.swing.Timer;
import java.rmi.server.UnicastRemoteObject;

/**@ Classe permettant d'implementer le serveur PolyEbay @**/
public class PolyEbayImpl implements PolyEbayRemote 
{ 
	private Timer gtimer; //Gestion du temps
	private Vector clients = new Vector();
	public Vector articles = new Vector();
	
	/**@ Constructeur @**/
	public PolyEbayImpl()
	{
		super();
		//Creation des articles avec les echeances
		articles.add(new ObjectArticle("Java 2", "Livre", 20.35, new GregorianCalendar(2005,12,1,11,50,00)));
		articles.add(new ObjectArticle("Mortal Combat", "DVD", 26.87, new GregorianCalendar(2005,12,1,11,52,00)));
		articles.add(new ObjectArticle("Horloge Biologique", "DVD", 21.5, new GregorianCalendar(2005,12,1,24,27,00)));
		init(); //Initialise les connections
		//Associe la classe de gestion du temps
		gtimer = new Timer(Constant.ONE_SECOND, new PolyEbayTimer(this, 15)); 
		gtimer.start(); //Demarre le timer des echeances
	}

	/**@ Description: Permet aux clients de se connecter au serveur	@**/
    public synchronized boolean connect(ClientRemote cli, String client ) throws RemoteException
	{
		PolyPaypalRemote obj = cli.GetPaypal();
		if(obj==null) return false;
    	if(!obj.GetClient(client))
    		return false;
		synchronized(clients)
		{
			System.out.println("PolyEbay: Connection de "+client+"!");
			clients.add( new PolyEbayClient( cli, client) ); 
		}
		return true;
	}
	
	/**@ Description: Permet aux clients de se deconnecter du serveur	@**/
    public synchronized void disconnect( String client )
	{
		PolyEbayClient cli; 

		//Parcours les clients afin de deconnecter le clients qui invoque
        for (int i = 0; i < clients.size(); i++) 
        { 
			//Recupere une copie locale
        	cli = (PolyEbayClient)clients.elementAt( i ); 
            if ( client.equals( cli.getUserName() ) ) //Si c'est celui recherche
            { 
				try
	        	{
					//synchronized(clients) //Synchronise la liste des utilisateurs ou clients
					//{
						cli.pClient.MessageNotify("PolyEbay: Vous etes deconnecte!");
						System.out.println("PolyEbay: Deconnection de "+client+"!");
		                clients.removeElementAt(i); //Le retirer de la liste
					//}
	        	}
				catch(Exception e){}
                return; 
            } 
        } 
	}

	/**@ Description: Permet de recuperer une instance du client	@**/
    public PolyEbayClient GetClient(String client)
    {
		PolyEbayClient cli; 
		//Parcours les clients afin de deconnecter le clients qui invoque
        for (int i = 0; i < clients.size(); i++) 
        { 
			//Recupere une copie locale
        	cli = (PolyEbayClient)clients.elementAt( i ); 
            if ( client.equals( cli.getUserName() ) ) //Si c'est celui recherche
            { 
            	return(cli); //Retourne le client recherche
            } 
        } 
        return(null);
    }

	/**@ Description: Permet aux clients d'avoir la description des articles @**/
	public synchronized String GetArtDescr(String art)
	{
		//Objet local
		ObjectArticle _article; 
	
		synchronized(articles){
			//Parcours tous les utilisateurs
	        for (int i = 0; i < articles.size(); i++) 
	        { 
				//Recupere l'objet courant de la liste
				_article = (ObjectArticle) articles.elementAt( i ); 
				if(art.equals(_article.ArticleName()))
				{
					//Retourne la description de l'objet recherchee
					return _article.MakeDescr();
				}
	        } 
		}
		return null;
	}
	
	/**@ Description: Permet aux clients d'avoir la liste des articles @**/
    public synchronized String[] GetArticles()
	{
    	//Objet local
		ObjectArticle _article;  String[] art;
		
    	synchronized(articles){ //Synchronise les articles
    		art = new String[articles.size()];
    		//Parcours tous les articles
            for (int i = 0; i < articles.size(); i++) 
            { 
				//Recupere l'article courant de la liste et le met dans le tableau
    			_article = (ObjectArticle) articles.elementAt( i ); 
    			art[i] = _article.ArticleName();
            }
    	}
    	return(art); //Retourne la tableau des articles
	}

	/**@ Description: Permet d'avoir le nombre de clients connectes sur PolyEbay @**/
    public synchronized int ClientCount()
	{
    	synchronized(clients){
    		return(clients.size());
        }
	}

	/**@ Description: Permet aux clients de miser sur un article @**/
	public synchronized boolean MiserArticle( String cli, String art, double dollard )
	{
		//Objet local
		ObjectArticle _article; 
	
		synchronized(articles){
			//Parcours tous les articles
	        for (int i = 0; i < articles.size(); i++) 
	        { 
				//Recupere l'article courant de la liste
				_article = (ObjectArticle) articles.elementAt( i ); 
				if(art.equals(_article.ArticleName()))
				{
					if(dollard < _article.ArticleBasePrix())
					{
						//Avertissement du client d'avoir mise une valeur inferieur au prix de base
						PostMessage(cli, "PolyEbay: Mise inferieur au prix de base pour l'article <"+art+">!");
						PostMessage(cli, "PolyEbay: ***Mise rejettee***");
						return false;
					}
					_article.AddMise(cli, dollard); //Ajoute la mise du client
					MapMise mise = HEnchere(art); //Recupere la plus haute enchere
					if(mise != null)
					{
						//Rappel de la plus haute enchere a tous les utilisateurs
						PostMessage("PolyEbay: Haute enchere de l'article <"+art+"> = "+mise.Mise+" a "+mise.time);
					}
					ParseNotify(); //Affiche immediatement le tableau
					return true;
				}
	        } 
		}
		return false;
	}

	/**@ Description: Permet aux clients d'avoir la plus haute enchere @**/
	public synchronized double HauteEnchere( String art )
	{
		//Objet local
		ObjectArticle _article; 
	
		synchronized(articles){
			//Parcours tous les utilisateurs
	        for (int i = 0; i < articles.size(); i++) 
	        { 
				_article = (ObjectArticle) articles.elementAt( i ); 
				if(art.equals(_article.ArticleName())) //Si c'est l'article recherche
				{
					//Rectoune la plus haute enchere de l'article demande
					return(_article.HauteEnchere());
				}
	        } 
		}
		return -1; //Si aucun article trouve
	}

	/**@ Description: Permet d'avoir la plus haute enchere sous forme de map entre le client et l'enchere @**/
	public MapMise HEnchere( String art )
	{
		//Objet local
		ObjectArticle _article; 
	
		synchronized(articles){ //Synchronose la liste des clients
			//Parcours tous les utilisateurs
	        for (int i = 0; i < articles.size(); i++) 
	        { 
				_article = (ObjectArticle) articles.elementAt( i ); 
				if(art.equals(_article.ArticleName()))
				{
					//Retourne le map de la plus enchere
					return(_article.HEnchere());
				}
	        } 
		}
		return null; //Aucun article trouve
	}

	/**@ Description: Permet de mettre a jour les information chez 1 ou plusieurs client @**/
	public void UpdateClients(PolyEbayClient client, boolean bOne) throws RemoteException
	{
		if(bOne)
		{
			PolyEbayClient cli; 
			//Parcours tous les utilisateurs
		    for (int i=0; i<clients.size(); i++) 
		    { 
		    	//Met a jour toutes les instances du client
		        cli = (PolyEbayClient) clients.elementAt( i ); 
		        if(cli.getUserName().equals(client.getUserName()))
		        {
					//Met a jour le client 
		        	cli.pClient.MakeUpdate();
		        }
		    } 
		}
		else
		{
			PolyEbayClient cli; 
			//Parcours tous les utilisateurs
		    for (int i=0; i<clients.size(); i++) 
		    { 
		        cli = (PolyEbayClient) clients.elementAt( i ); 
		        synchronized(cli)//Synchronise le thread du client
		        {
					//Met a jour le client courant
		        	cli.pClient.MakeUpdate();
		        }
		    } 
		}
	}

	/**@ Description: Permet la verification des informations telles que le solde du compte d'un client	@**/
	public boolean PerformVerification( String art, int index)
	{
		try
		{
			gtimer.stop(); //Arret d'abord le timer
			MapMise mise = HEnchere(art); //Recupere le map de la plus haute enchere
			if(mise==null)return false;
			PolyEbayClient cli=GetClient(mise.client); //recupere l'instance du client
			if(cli==null) return(false);
			PolyPaypalRemote obj = cli.pClient.GetPaypal(); //Recupere une instance de PolyPaypal associe
			if(obj==null) return false;
			if(!obj.CheckCredit(mise.client, mise.Mise)) //Verifie le credit du vainqueur
			{
				//Envoie un avertissement au client concerne
				cli.pClient.MessageNotify("PolyEbay: Vous avez mise un montant superieur a votre solde chez PolyPaypal!");
				cli.pClient.MessageNotify("PolyEbay: Une penalite de 2.0$ est portee a votre compte et ne recidivez pas");
				obj.ModifyCredit(mise.client, 2.0, false); //Applique la penalite de 2$
				UpdateClients(cli, true); //Mise a jour du client conserne
			}
			else
			{
				cli.pClient.MessageNotify("PolyEbay: Felicitation, l'article <"
				+art+">, prix "+mise.Mise+"$ vous sera envoye par la poste!!!"); //Felicite le client vainqueur
				obj.ModifyCredit(mise.client, mise.Mise, false); //Soustrait le montant au compte du client
				articles.removeElementAt(index); //Retrait de l'article
				UpdateClients(null, false); //Mise a jour de tous les clients
			}
			gtimer.restart(); return(true); //Redemarre le timer et retourne succes
		}
		catch(RemoteException ex)
		{
			System.out.print("PolyEbay: RemoteException in PerformVerification()");
			return false;
		}
	}

	/**@ Permet de poster un message aux utilisateurs @**/
    public synchronized void PostMessage(String msg) 
    { 
		//Objet local
		PolyEbayClient cli; 
		
		//Parcours tous les utilisateurs
	    for (int i = 0; i < clients.size(); i++) 
	    { 
	        cli = (PolyEbayClient) clients.elementAt( i ); 
	        synchronized(cli)//Synchronise l'objet des client
	        {
	        	if ( ! cli.PostMessage( msg ) ) 
				{
					synchronized(clients){
						clients.removeElementAt( i ); //retire PolyEbayClient 
					}
				}
	        }
	    } 
    } 

	/**@ Permet de poster un message a un utilisateur @**/
    public synchronized void PostMessage(String client, String msg) 
    { 
		//Objet local
		PolyEbayClient cli; 
	
		//Parcours tous les utilisateurs
	    for (int i = 0; i < clients.size(); i++) 
	    { 
	        cli = (PolyEbayClient) clients.elementAt( i ); 
			if(client.equals(cli.getUserName()))
			{
				//Envoie le message au client demande
				cli.PostMessage( msg );
				return; //Retourne
			}
	    } 
    } 

	/**@ Description: Permet d'avoir formater les noms des clients	@**/
    public String GetClientsName() 
    { 
		//Objet local
		PolyEbayClient cli; 
		String client="";
	
		//Parcours tous les utilisateurs
	    for (int i = 0; i < clients.size(); i++) 
	    { 
	        cli = (PolyEbayClient) clients.elementAt( i ); 
			//Ajoute le nom du client courant
			client += (cli.getUserName()+(( i==clients.size()-1)?" ":", "));
	    } 
		return client; //retourne le format souhaite
    } 

	/**@ Description: Permet de mettre a jour la longueur d'une chaine du tableau que le serveur affiche @**/
	public String UpdateLength(String src)
	{
		String lSpace=" "; String loc=src;
		int _l = loc.length();
		if(_l < 66) //Longueur max d'une ligne du tableau = 66
		{
			for(int j=0; j<(66-_l); j++)
				loc += lSpace; //Ajoute les espaces jusqu'a 66
		} loc+="|"; 
		return loc; //Retourne la nouvelle chaine
	}

	/**@ Description: Permet au serveur d'afficher le tableau @**/
	public synchronized void ParseNotify()
	{
		Calendar cal = new GregorianCalendar(); //Recupere l'heure courante
		//Formate l'entete du tableau
		System.out.println("-------------------------------------------------------------------");
		String lEntete = "|     Serveur PolyEbay, "+cal.get(Calendar.DAY_OF_MONTH)+" "
													+cal.get(Calendar.DAY_OF_WEEK) +" "
													+cal.get(Calendar.YEAR)+", "+
													+cal.get(Calendar.HOUR_OF_DAY)+":"
													+cal.get(Calendar.MINUTE)+":"
													+cal.get(Calendar.SECOND)+"      ";
		System.out.println(UpdateLength(lEntete));// Met a jour la longueur et affiche l'entete
		System.out.println("|-----------------------------------------------------------------|");
		
		String item1 = "| Connexions: PolyPaypal, "+GetClientsName(); //Affiche les noms des clients
		String item2 = "| Nombre de clients connectes = "+clients.size(); //Affiche le nombre de clients
		String item3 = "| Nom(s) : "+GetClientsName();
		System.out.println(UpdateLength(item1));  //Met a jour les longueurs et affiche
		System.out.println("|                                                                 |");
		System.out.println(UpdateLength(item2));
		System.out.println(UpdateLength(item3));
		String _line = "|--------------------                                             |";
		
		ObjectArticle _article;
    	//Parcours tous les articles
        for (int i = 0; i < articles.size(); i++) //Pour chaque article
        { 
			System.out.println(_line); //Affiche une ligne
    		_article = (ObjectArticle) articles.elementAt( i ); 
			String lname_des = "| "+GetArtDescr(_article.ArticleName()); //Formate la description de l'article
			System.out.println(UpdateLength(lname_des)); //Met a jour la longueur et affiche
			
			MapMise[] mises = _article.GetMiseMap(); //Recupere les mises sur cet article
			for(int j=0; j<mises.length-1; j++) //Pour chaque mise
			{
				//Formate les mises de l'article
				String mj = "| mise "+(j+1)+": "+mises[j].Mise+" par <"+mises[j].client+"> a "+mises[j].time;
				System.out.println(UpdateLength(mj)); //Met a jour la longueur et affiche
			}
			if(mises.length>0)
			{
				//Formate la derniere mise 
				String ml = "| derniere mise "+mises[mises.length-1].Mise
											+" par <"+mises[mises.length-1].client
											+"> a "+mises[mises.length-1].time;
				System.out.println(UpdateLength(ml)); //Met a jour la longueur et affiche
			}
    	}
		System.out.println("|                                                                 |");
		System.out.println("|                                                                 |");
		System.out.println("-------------------------------------------------------------------");
	}
	
	/**@ Permet d'initialiser et d'exporter le serveur PolyEbay @**/
    public boolean init()
    {
        try 
        {
			//Activation de la securite de RMI
			//System.setSecurityManager( new RMISecurityManager() ); //Signal un acces refuse!
      
        	//Cree l'objet distribue et l'exporte
        	System.out.println ("PolyEbay Server started...");
        	UnicastRemoteObject.exportObject( this ); //Exporte le serveur PolyEbay
			System.out.println ("PolyEbay Server ready!");
			return true; //Retourne succes
        } 
        catch (RemoteException e) 
        { 
            System.out.println("General Server PolyEbay Error: " + e); 
            System.exit(0); return false;
        } 
    }
} 
