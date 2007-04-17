/**
 * ImplPolyEbay.java
 *
 * Created on 9 vril 2007
 *
 * @author BRUN Joel & DEBONNEL Yann
 */

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Timer;
import java.util.Vector;
import java.util.Calendar;
import java.util.GregorianCalendar;



public class ImplPolyEbay extends UnicastRemoteObject implements InterfacePolyEbay, Runnable {
    private Vector<Article> articles;
    private Vector<String> clients;
    private Hashtable<String, String> clientsArticle; // ip article
    private Hashtable<String, String>  clientsIp; // ip client
    private Hashtable<String, InterfaceClient> remoteClients; // ip remote
    private InterfacePolyPaypal remotePolyPaypal;
    private boolean IsPolyPayPal = false;
    private Timer timer;
    private Thread t_listener;
    
    private String month[] = {"janvier","fevrier","mars","avril","mai",
    "juin","juillet","aout","septembre","octobre","novembre","decembre"};
    
    private boolean isThreadActive;
    
    
    public ImplPolyEbay() throws RemoteException{
        super();
        articles = new Vector<Article>();
        clientsArticle = new Hashtable<String, String>();
        clientsIp = new Hashtable<String, String>();
        remoteClients = new Hashtable<String, InterfaceClient>();
        clients = new Vector<String>();
        isThreadActive = false;
    }
    
    public synchronized ClientConnect connectClient(String nom, String ipClient) throws RemoteException {
        ClientConnect temp = null;
        //if(clientsIp.containsKey(ipClient)==true || remotePolyPaypal.connect(nom)==-1)
        if(remotePolyPaypal.connect(nom)==-1)
            temp = new ClientConnect(false);
        else{
            InterfaceClient rC;
            try {
                rC = (InterfaceClient) Naming.lookup("//" + ipClient + "/" + nom);
                remoteClients.put(ipClient, rC);
                System.out.println("Connection du client "+nom+ " @ " + ipClient);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            } catch (NotBoundException ex) {
                ex.printStackTrace();
            }
            
            temp = new ClientConnect(true);
            
            
            if(clientsIp.containsValue(nom)==true){
                Vector<String> ip = new Vector<String>();
                
                for( Enumeration e = clientsIp.keys(); e.hasMoreElements()==true;){
                    String unIp = (String) e.nextElement();
                    if(clientsIp.get(unIp).compareTo(nom)==0){
                        ip.add(unIp);
                    }
                }
                
                for(int i=0; i<articles.size() && clientsArticle.isEmpty()==false; i++){
                    boolean isUse=false;
                    Article UnArt = articles.elementAt(i);
                    for(int j=0; j<ip.size() && isUse==false; j++){
                        if(UnArt.getNom().compareTo(clientsArticle.get(ip.elementAt(j)))==0)
                            isUse=true;
                    }
                    if(isUse==false) temp.addArticle(UnArt);
                }
            } else{
                for(int i=0; i<articles.size(); i++){
                    temp.addArticle(articles.elementAt(i));
                }
            }
            clientsIp.put(ipClient, nom);
            if(!clients.contains(nom)) clients.add(nom);
        }
        return temp;
    }
    
    public synchronized boolean disconnectClient(String nom, String ipClient) throws RemoteException {
        if(clientsIp.get(ipClient).compareTo(nom)==0){
            clientsArticle.remove(ipClient);
            clientsIp.remove(ipClient);
            remoteClients.remove(ipClient);
            
            return true;
        } else return false;
    }
    
    public synchronized boolean addClientArticle(String ipClient, String article) throws RemoteException {
        if(clientsIp.containsKey(ipClient) && !clientsArticle.containsKey(ipClient)){
            clientsArticle.put(ipClient, article);
            return true;
        } else return false;
    }
    
    public synchronized void miserArticle(String ipClient, String article, float montant) throws RemoteException {
        boolean isArt = false;
        Article unArt ;
        if(clientsArticle.get(ipClient).compareTo(article)==0){
            for(Iterator ite = articles.iterator(); ite.hasNext() && !isArt ; ){
                unArt = (Article) ite.next();
                if(unArt.getNom().compareTo(article)==0){
                    isArt = true;
                    if(unArt.addMise(clientsIp.get(ipClient),montant)==true){
                        System.out.println("mise effectuer avec succes: "+article+" "+ montant);
                        for(Enumeration e = remoteClients.keys(); e.hasMoreElements(); ){
                            String unIp = (String) e.nextElement();
                            if(clientsArticle.get(unIp).compareTo(article)==0){
                                remoteClients.get(unIp).UpdateClient(unArt);
                            }
                        }
                    } else
                        System.out.println("mise erreur : "+article+" "+ montant);
                }
            }
            printInfo();
        }
    }
    
    
    
    public boolean addArticle(String nom, double prix, String time_fin){
        boolean isAdded=false;
        for(Iterator ite = articles.iterator(); ite.hasNext() && isAdded==true;){
            Article unArt = (Article) ite.next();
            if( unArt.getNom().compareTo(nom)==0)
                isAdded=true;
        }
        
        if(isAdded==false){
            Article unArt = new Article(nom, prix, time_fin);
            articles.add(unArt);
            if(!isThreadActive){ isThreadActive=true;  t_listener=new Thread(this); t_listener.start();}
        }
        /*////////////////////// test
        System.out.println("--------------------------");
        System.out.println("articles ajoutes : ");
        for(Iterator ite = articles.iterator(); ite.hasNext();){
            Article unArt = (Article) ite.next();
            System.out.println(unArt.getNom());
        }
        System.out.println("--------------------------");
        //////////////////// fin test*/
        //printInfo();
        return !isAdded;
    }
    
    public void printInfo(){
        String connected = "";
        if(!clients.isEmpty())
            connected = clients.toString();
        int nb = clients.size();
        
        GregorianCalendar today = new GregorianCalendar();
        String stToday = today.get(Calendar.DAY_OF_MONTH)+" "+
                month[today.get(Calendar.MONTH)]+" "+
                today.get(Calendar.YEAR)+", "+today.get(Calendar.HOUR_OF_DAY)+":"+
                today.get(Calendar.MINUTE)+":"+today.get(Calendar.SECOND);
        System.out.println(" -------------------------------------------------------------");
        System.out.println("|     Serveur PolyEbay, "+stToday+"   |");
        System.out.println(" -------------------------------------------------------------");
        if(IsPolyPayPal)  System.out.println("|connexions: PolyPayPal, "+connected+"             |");
        else System.out.println("|connexions: "+connected+"             |");
        System.out.println("| nombre de clients connectes: "+nb+"                              |");
        System.out.println("| nom(s): "+connected+"                             |");
        for(int i=0; i<articles.size();i++){
            Article unArt = articles.elementAt(i);
            System.out.println(" -------------------------------------------------------------");
            System.out.println("| Article "+(i+1)+": "+unArt.getNom()+" prix "+unArt.getPrix()
            +", fermeture a "+unArt.getDateFin()+"    |");
            unArt.print();
        }
        System.out.println("|                                                            |");
        System.out.println(" -------------------------------------------------------------");
    }
    
    public void arreterServeur(){
        try {
            Naming.unbind("//localhost:4500/POLYEBAY");
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (NotBoundException ex) {
            ex.printStackTrace();
        }
    }
    
    public void demarrerServeur(String IP_PAYPAL) {
        try {
            remotePolyPaypal = (InterfacePolyPaypal)Naming.lookup("//" + IP_PAYPAL + ":5000/POLYPAYPAL");
            System.out.println("Connection au serveur PAYPAL etablie.");
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (RemoteException ex) {
            ex.printStackTrace();
        } catch (NotBoundException ex) {
            ex.printStackTrace();
        }
        
        try{
            java.rmi.registry.LocateRegistry.createRegistry(4500);
            System.out.println("Registre cree sur le port 4500");
        } catch(Exception e) {
            e.printStackTrace();
        }
        try {
            java.rmi.registry.Registry reg = java.rmi.registry.LocateRegistry.getRegistry(4500);
            System.out.println("Registre utilise sur le port 4500");
        } catch (Exception e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
        }
        
        // Create and install a security manager
        /*if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }*/
        
        try {
            Naming.rebind("rmi://" + "localhost:4500/POLYEBAY", this);
            System.out.println("Serveur PolyEBAY fonctionnel @ localhost : 4500");
        } catch (RemoteException e1) {
            e1.printStackTrace();
            System.out.println("Systeme de partage de fichier n'est pas disponible en ce moment");
            System.out.println("Veuillez reessayer la connection ulterieurement");
            System.exit(0);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("Systeme de partage de fichier n'est pas disponible en ce moment");
            System.out.println("Veuillez reessayer la connection ulterieurement");
            System.exit(0);
        }
    }
    
    public static void main(String args[]) throws RemoteException{
        ImplPolyEbay polyEbay;
        polyEbay = new ImplPolyEbay();
        polyEbay.demarrerServeur("localhost");
        polyEbay.addArticle("livre java 1", 19.99, "20:56:00");
        polyEbay.addArticle("livre c++ 1", 29.99, "21:00:00");
        polyEbay.addArticle("livre philo 1", 40.99, "21:05:00");
        
        InputStreamReader in = new InputStreamReader(System.in);
        String In;
        char[] input = new char[20];
        while(true){
            System.out.println("\n-----------------------------------------------");
            System.out.println("Pour arreter le systeme appuyer sur : q ou Q\n");
            try {
                int size = in.read(input, 0, 20);
                In = String.valueOf(input);
                In= In.substring(0,1);
                if(In.equalsIgnoreCase("q") == true){
                    //polyEbay.arreterServeur();
                    System.exit(0);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                Thread.sleep(5000);
                polyEbay.printInfo();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            
        }
        
    }
    
    public void run() {
        while(isThreadActive){
            for(int i=0; i<articles.size();i++){
                Article art = articles.elementAt(i);
                art.setTimeRemaining();
                if(art.IsTimeOut()){
                    if(art.getLeader().compareTo("")!=0){
                        try {
                            remotePolyPaypal.updateCredit(art.getLeader(), art.getbestMise());
                            
                            for(Enumeration e1 = clientsArticle.keys(); e1.hasMoreElements();){
                                String ip = (String) e1.nextElement();
                                if(clientsArticle.get(ip).compareTo(art.getNom())==0){
                                    remoteClients.get(ip).UpdateClient(art);
                                }
                            } }catch (RemoteException ex) {
                                ex.printStackTrace();
                            }
                    }
                    articles.remove(i);
                    i--;
                }
            }
            if(articles.size()==0) {
                isThreadActive=false; t_listener = null;
            } else{
                try {
                    t_listener.sleep(60000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
