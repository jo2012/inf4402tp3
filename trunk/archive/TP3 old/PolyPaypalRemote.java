import java.rmi.*; 

/**@ Interface permettant de representer l'objet distant aux clients @**/
public interface PolyPaypalRemote extends Remote 
{ 
    public double connect( ClientRemote c, String client ) throws RemoteException; 
    public void disconnect( String client ) throws RemoteException; 
    public void PostMessage( String msg ) throws RemoteException; 
    public int ClientCount() throws RemoteException; 
    public boolean GetClient(String client) throws RemoteException;
    public PolyEbayRemote GetEbay() throws RemoteException; 
    public void SetEbay(PolyEbayRemote ebay) throws RemoteException; 
	public boolean CheckCredit(String client, double dollard) throws RemoteException;
	public boolean ModifyCredit(String client, double dollard, boolean action) throws RemoteException;
	public double CheckCredit(String client) throws RemoteException;
	public void SetCreditCheck(CreditCheckRemote credit) throws RemoteException; 
} 
