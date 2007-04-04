import java.util.*; 

/**@ Classe permettant de representer l'objet MapMise entre un client et une mise @**/
public class MapMise
{
	public String client;
	public double Mise;
	public String time;

	/**@ Constructeur @**/
	public MapMise(String cli, double m, GregorianCalendar t)
	{
		client=cli; //Recupere le nom du client
		Mise=m; //Recupere la mise
		//Recupere et formate le temps d'echeance de l'article
		time = ""+t.get(Calendar.HOUR_OF_DAY)+":"
						 +t.get(Calendar.MINUTE)+":"
						 +t.get(Calendar.SECOND);
	}

	/**@ Description: Permet de modifier le montant de mise @**/
	public void ModifyMap(double m, GregorianCalendar t)
	{
		Mise=m; //Recupere la mise
		//Recupere et formate le temps d'echeance de l'article
		time = ""+t.get(Calendar.HOUR_OF_DAY)+":"
				 +t.get(Calendar.MINUTE)+":"
				 +t.get(Calendar.SECOND);
	}
} 
