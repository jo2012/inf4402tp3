/**
 * Article.java
 *
 * Created on 9 vril 2007
 *
 * uthor BRUN Joel & DEBONNEL Yann
 */

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Vector;

public class Article {
	public int id;
	private static int ids = 1;
	private String nom;
	private double prixBase;
	public double bestMise;
	private Vector<Mise> mises;
	private GregorianCalendar timeFin;
        private String stTimeFin;
        private boolean isTimeout;
        
        private String month[] = {"janvier","fevrier","mars","avril","mai",
        "juin","juillet","aout","septembre","octobre","novembre","decembre"};
	
	private DateFormat dateTimeFormat=DateFormat.getDateTimeInstance();
	private	DateFormat timeFormat= DateFormat.getTimeInstance();
	
	public Article(String nom, double prix, String time_fin){
		this.id=ids++;
		this.nom = nom;
		this.prixBase = prix;
		this.bestMise = prix;
		this.mises = new Vector<Mise>();

                this.timeFin = new GregorianCalendar();
                timeFin.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time_fin.substring(0,2)));
                timeFin.set(Calendar.MINUTE, Integer.parseInt(time_fin.substring(3,5)));                
                //timeFin.set(Calendar.SECOND, Integer.parseInt(time_fin.substring(3,5)));
                timeFin.set(Calendar.SECOND,0);
                
                
                String stToday = timeFin.get(Calendar.DAY_OF_MONTH)+" "+
                month[timeFin.get(Calendar.MONTH)]+" "+
                timeFin.get(Calendar.YEAR)+", ";
                stTimeFin="";
                int a = timeFin.get(Calendar.HOUR_OF_DAY);
                if(a<10) stTimeFin+="0"+a+":";         else stTimeFin+=a+":";
                 a = timeFin.get(Calendar.MINUTE);
                if(a<10) stTimeFin+="0"+a+":";         else stTimeFin+=a+":";
                 a = timeFin.get(Calendar.SECOND);
                if(a<10) stTimeFin+="0"+a;             else stTimeFin+=a;
                
                System.out.println(" fin: "+stToday+", "+stTimeFin);
                if(timeFin.before(new GregorianCalendar()))isTimeout = false;
                else isTimeout = true;
                System.out.println(" fin: "+isTimeout);
	}
	
	public synchronized boolean addMise(String client, double montant){
		if(montant>bestMise){
			bestMise = montant;
			Mise uneMise = new Mise(client, montant);
			mises.add(uneMise);
			return true;
		}
		else return false;
	}
	
	public String getNom(){ return nom;}
        public double getPrix(){ return prixBase;}        
        public String getDateFin(){ return stTimeFin;}
        //public String getTimeRemaining(){ };
	
	public void print(){
		int i=1;String st;
		if(mises.size()==0) return;
                Iterator ite = mises.iterator();
		for (; ite.hasNext() && i<mises.size(); ){
			st="|mise "+ i +": ";
			System.out.print(st);
                        Mise uneMise = (Mise) ite.next();
			int len = 50-st.length() - uneMise.print();
			st="";
			for (int j=len; j>0; j--) {st+=" ";}
			st+="|";
			System.out.println(st);
			i++;
		}
		st="|derniere mise ";
                Mise uneMise = (Mise) ite.next();
		int len = 50-st.length() - uneMise.print();
		for (int j=len; j>0; j--) {st+=" ";}
		st+="|";
		System.out.println(st);
		
	}
}

