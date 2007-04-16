/**
 * Mise.java
 *
 * Created on 9 vril 2007
 *
 * @author BRUN Joel & DEBONNEL Yann
 */
 
import java.util.Date;
import java.text.DateFormat;

public class Mise  implements java.io.Serializable {
	private static int ids=1;
	private int id;
	private String client;
	private float montant;
	//private Date dateMise;
        private String dateMise;
	DateFormat timeFormat=DateFormat.getTimeInstance();
        
	public Mise(String client, float montant){
		this.client = client;
		this.id = ids++;
		this.dateMise = timeFormat.format(new Date());
		this.montant = montant;
	}
	
	public int getID(){ return id;}
	public String getClient(){ return client;}
	public float getMontant(){ return montant;}
	
	public int print(){		
		
		String st = montant+"$ par "+client+ " à "+dateMise;//timeFormat.format(dateMise);
		System.out.print(st);
		return st.length();
	}
}
