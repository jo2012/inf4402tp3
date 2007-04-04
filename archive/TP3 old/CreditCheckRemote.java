import java.rmi.*; 

/**@ Interface permettant de representer l'objet CreditCheck a PolyPaypal @**/
public interface CreditCheckRemote extends Remote
{ 
    public double CheckCredit( String client ) throws RemoteException;
	public boolean ModifyCredit( String client, double credit, boolean bAdd) throws RemoteException;
	public int GetLastError() throws RemoteException;
	public int IndexClient( String client ) throws RemoteException;
	public ObjectClient GetClient(int index) throws RemoteException;
	public ObjectClient GetClient( String client ) throws RemoteException;
} 
