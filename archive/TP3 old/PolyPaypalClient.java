import java.rmi.*;

/**@ Thread que le serveur cree pour communiquer avec un client @**/
public class PolyPaypalClient 
{ 
    public ClientRemote pClient; 
    private String pUser; 

	/**@ Constructeur @**/
    public PolyPaypalClient( ClientRemote cli, String user) 
    { 
        this.pClient = cli; 
        this.pUser = user; 
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
        	System.out.println("PolyPaypal: Exception in PostMessage() with " +pUser+"!"); 
        }
        return true; 
    } 

	/**@ Permet de recuperer le nom de l'utilisateur @**/
    public String getUserName() 
    { 
        return pUser; 
    } 
} 
