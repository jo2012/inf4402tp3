import java.rmi.*; 

/**@ Interface permettant de representer le client au cote du serveur @**/
public interface ClientRemote extends Remote 
{ 
	public String GetUser() throws RemoteException; 
    public void MessageNotify(String msg) throws RemoteException; 
	public PolyPaypalRemote GetPaypal() throws RemoteException; 
	public void MakeUpdate() throws RemoteException; 
} 
