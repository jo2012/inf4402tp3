/**
 * Mise.java
 *
 * Created on 9 vril 2007
 *
 * @author BRUN Joel & DEBONNEL Yann
 */
 
import java.util.Date;
import java.text.DateFormat;

public class Mise {
	private static int ids=1;
	private int id;
	private String client;
	private double montant;
	private Date dateMise;
	
	public Mise(String client, double montant){
		this.client = client;
		this.id = ids++;
		this.dateMise = new Date();
		this.montant = montant;
	}
	
	public int getID(){ return id;}
	public String getClient(){ return client;}
	public double getMontant(){ return montant;}
	
	public int print(){		
		DateFormat timeFormat=DateFormat.getTimeInstance();
		String st = montant+"$ par "+client+ " à "+timeFormat.format(dateMise);
		System.out.print(st);
		return st.length();
	}
}
