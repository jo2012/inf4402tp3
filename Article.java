/**
 * Article.java
 *
 * Created on 9 vril 2007
 *
 * uthor BRUN Joel & DEBONNEL Yann
 */



public class Article {
	public int id;
	private static int ids = 1;
	private String nom;
	private double prixBase;
	public double bestMise;
	private List<Mise> mises;
	private Date timeFin;
	private Date timer;
	
	private DateFormat dateTimeFormat=DateFormat.getDateTimeInstance();
	private	DateFormat timeFormat= DateFormat.getTimeInstance();
	
	public Article(String nom, double prix, String time_fin){
		this.id=ids++;
		this.nom = nom;
		this.prixBase = prix;
		this.bestMise = prix;
		this.mises = new List<Mise>();

		String today = dateTimeFormat.format(new Date(););
		time_fin = today.substring(0,today.length()-8)+time_fin;
		try{
			this.timeFin = dateTimeFormat.parse(time_fin);
			System.out.println("time fin:" +this.timeFin);
		}catch (ParseException e){
			e.printStackTrace();
		}
		
		this.timer = (time_fin = new Date);
		System.out.println("timer :" +this.timer);
		
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
	
	public void print(){
		int i=1;String st;
		if(mises.size()==0) return;
		for (Iterator ite = mises.iterator(); ite.hasNext() && i<mises.size(); ){
			st="|mise "+ i +": ";
			print(st);
			int len = 50-st.length() - ite.next().print();
			st="";
			for (int j=len; j>0; j--) {st+=" ";}
			st+="|";
			println(st);
			i++;
		}
		st="|derniere mise ";
		int len = 50-st.length() - ite.next().print();
		for (int j=len; j>0; j--) {st+=" ";}
		st+="|";
		println(st);
		
	}
}

