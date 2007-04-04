import java.rmi.*; 
import java.util.*; 

/**@ Interface permettant de representer l'objet PolyEbay @**/
public interface PolyEbayRemote extends Remote 
{ 
    public boolean connect( ClientRemote cli, String client ) throws RemoteException; 
    public void disconnect( String client ) throws RemoteException; 
    public String[] GetArticles() throws RemoteException; 
    public int ClientCount() throws RemoteException; 
	public boolean MiserArticle( String cli, String art, double dollard ) throws RemoteException; 
	public double HauteEnchere( String art ) throws RemoteException; 
	public void PostMessage(String msg) throws RemoteException; 
	public String GetArtDescr(String art) throws RemoteException;
} 
