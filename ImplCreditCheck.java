import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
/*
 * ImplCreditCheck.java
 *
 * Created on April 4, 2007, 10:46 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Yann
 */
public class ImplCreditCheck extends UnicastRemoteObject implements InterfaceCreditCheck{
    
    private Hashtable<String, Float> mesClients = new Hashtable<String, Float>();
    private ImplCreditCheck serveurCreditCheck;
    /**
     * Creates a new instance of ImplCreditCheck
     *
     * The ImplCreditCheck can be later used to verify credit of a client
     *
     * @param      void
     */
    public ImplCreditCheck() throws RemoteException {
        //Mise en marche du CreditCheck
        try{
            java.rmi.registry.LocateRegistry.createRegistry(4600);
            System.out.println("Registre cree sur le port 4600 pour CreditCheck");
            java.rmi.registry.Registry reg = java.rmi.registry.LocateRegistry.getRegistry(4600);
            System.out.println("Registre du port 4600 utilise par CreditCheck");
            // Créer et installer le gestionnaire de sécurité.
            /*if (System.getSecurityManager() == null) {
                System.setSecurityManager(new RMISecurityManager());
            }*/
            //serveurCreditCheck = new ImplCreditCheck();
            Naming.rebind("rmi://" + "localhost:4600" + "/" + "CREDITCHECK", this);
	   System.out.println("Serveur CREDITCHECK est pret.");
        } catch (Exception e1) {
            e1.printStackTrace();
            System.out.println("Probleme d'initialisation du serveur CreditCheck.");
        }
        for(int i = 0; i<3; i++)
            mesClients.put("Client"+i,new Float(50.0));
    }
    
    /**
     * Returns the credit of specified client
     *
     *
     * @param      String c - the name of the client
     * @return     float - the value of the client's credit
     */
    public float getClientCredit(String c) throws RemoteException {
        System.out.println("Verification du credit du client " + c + " : " +  mesClients.get(c).floatValue());
        return (mesClients.get(c)).floatValue();
    }
    
    /**
     * Updates the credit of specified client
     *
     *
     * @param      float d - the variation of the client's credit
     * @return     void
     */
    public synchronized float updateClientCredit(String c, float d) throws RemoteException{
        float a, f = mesClients.get(c);
	a = f;
        if(d > f)
	{
		System.out.println("Client " + c + " : mise illegale. Mise de " + d + " avec credit de " + f + "." );
		System.out.println("Client " + c + " : Penalite de 10$." );
		f-=10;
	}
	else
	{
		f -= d;
	}
	mesClients.put(c, new Float(f));
	System.out.println("Mise a jour du credit du client " + c + " : " +  a + " -> " + mesClients.get(c).floatValue());
        return mesClients.get(c).floatValue();
    }
    
    public static void main(String args[]) {
                try {
                    new ImplCreditCheck();
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
    }
}
