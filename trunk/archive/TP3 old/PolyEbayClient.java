import java.rmi.*;

/**@ Thread que le serveur cree pour communiquer avec un client @**/
public class PolyEbayClient 
{ 
    public ClientRemote pClient; 
    private String pUser; 

	/**@ Constructeur @**/
    public PolyEbayClient( ClientRemote cli, String user ) 
    { 
        this.pClient = cli; //recupere l'instance du client distant
        this.pUser = user; //recupere le nom du client
    }
	
	/**@ Permet de deposer l'objet tableau a afficher @**/
    public boolean PostMessage( String msg ) 
    { 
		try
		{
	        pClient.MessageNotify( msg ); //Notifie le message chez le client
		}
        catch( Exception e)
        {
        	System.out.println("PolyEbay: Exception in PostMessage() " +pUser+"!"); 
        }
        return true; 
    }  

	/**@ Permet de recuperer le nom de l'utilisateur @**/
    public String getUserName() 
    { 
        return pUser; //Retourne le nom du client associe
    } 
} 
