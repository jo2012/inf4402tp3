/**
 * ImplPolyEbay.java
 *
 * Created on 9 vril 2007
 *
 * @author BRUN Joel & DEBONNEL Yann
 */




public class ImplPolyEbay extends UnicastRemoteObject implements InterfacePolyEbay {
	private List<Article> articles;
	private HashTable<Article, Hashtable<String client, String ip>> clients;
	private InterfacePolyPaypal remotePolyPaypal;
	private HashTable<String ip, InterfaceClient> remoteClients;
	
	public ImplPolyEbay(){
		super();
		articles = new List<Article>();
	}
	
	public List<Article> connectClient(Object String nom, Object String ipClient) throws RemoteException {
		
	}
	
	public boolean disconnectClient(Object String nom, Object String ipClient) throws RemoteException {
		
	}
	
	public boolean addClient(Object String ipClient, Object int idArticle) throws RemoteException {
		
	}
	
	public void miserArticle(Object int idArticle) throws RemoteException {
		
	}
	
	public boolean addArticle(String nom, double prix, String time_fin){
		boolean isAdded=false;
		for(Iterator ite = articles.iterator(); ite.hasNext() && isAdded==true;){
			if( ite.next().getNom().compareTo(nom)==0)
				isAdded=true;
		}
		
		if()
		Article unArt = new Article(nom, prix, time_fin);
		articles.add(unArt);
		return !isAdded;		
	}
}
