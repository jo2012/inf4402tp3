import java.rmi.*; 

/**@ Classe permettant de lancer le client @**/
public class Client{
	
	/**@ Fonction principale du programme @**/
	public static void main(String[] args)
	{
		if(args.length < 1) //verifie les paramettres et notify le client
		{
			System.out.println("");
			System.out.println("***********************************");
			System.out.println("* Usage: java RmiClient <username>*");
			System.out.println("* <username>=client1,client2, ... *");
			System.out.println("***********************************");
			System.out.println("");
			System.exit(-1);
		}

		//Recupere le nom de l'usager
		String User=args[0];

		try
		{
			//Cree l'objet client
			ClientImpl lClient = new ClientImpl(User);

			//Initialise les objets et demarre
			if(!lClient.init())
			{
				System.out.println("Erreur fatale lors de l'initialisation du tableau");
				System.exit(-1); //ferme le programme pour une autre tentative
			}
		}
        catch ( RemoteException e ) 
        { 
            System.out.println("Remote error in Client "+User+"!"); 
            System.exit(0); //Ferme le programme
        }
	}
}
