import java.rmi.*;
/*
 * InterfaceCrediCheck.java
 *
 * Created on April 4, 2007, 10:47 AM
 *
 Interface de la classe ImplCreditCheck
 *
 */

public interface InterfaceCreditCheck extends Remote{
        public float getClientCredit(String c) throws RemoteException;
        public void updateClientCredit(String c, float d) throws RemoteException;
}
