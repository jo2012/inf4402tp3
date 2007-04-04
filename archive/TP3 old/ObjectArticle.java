import java.util.*; 

/**@ Classe permettant de representer l'objet ObjectArticle associe a un article @**/
public class ObjectArticle
{ 
	private String ArtName;
	private String ArtType;
	private double BasePrix;
	private ObjectArticleMise ArtMise;
	private String str_ech;
	private int int_ech;
	public  boolean bIsValid;

	/**@ Constructeur @**/
	public ObjectArticle(String art, String type, double prix_base, GregorianCalendar ech)
	{
		BasePrix=prix_base; bIsValid=true;
		ArtName=art; ArtType=type;
		ArtMise = new ObjectArticleMise(this); //Cree l'objet des mise associe a cet article
		str_ech = ""+ech.get(Calendar.HOUR_OF_DAY)+":"
					 +ech.get(Calendar.MINUTE)+":"
					 +ech.get(Calendar.SECOND);
		GregorianCalendar t = new GregorianCalendar(); //l'heure courante
		int int_t = t.get(Calendar.HOUR_OF_DAY)*3600
					  +t.get(Calendar.MINUTE)*60
					  +t.get(Calendar.SECOND); //Temps en seconde
		int_ech = ech.get(Calendar.HOUR_OF_DAY)*3600
				  +ech.get(Calendar.MINUTE)*60
				  +ech.get(Calendar.SECOND); //Temps en seconde
		if(int_t>int_ech)
		{
			bIsValid=false;
			System.out.println("PolyEbay: Warning, L'heure de fermeture de l'article <"+art+"> est deja terminee!");
		}
		int_ech -= int_t; //Soustrait l'heure courante et garde le temps restant
	}
	
	/**@ Description: Permet d'avoir le temps restant avant la fermeture de l'enchere @**/
	public int UpdateTimer() //est appele chaque seconde
	{
		int_ech--; //Decremente le temps restant (en seconde)
		return(int_ech);
	}

	/**@ Description: Permet d'avoir le nom de l'article @**/
    public String ArticleName()
	{
		return ArtName; //Retourne la nom de l'article
	}

	/**@ Description: Permet d'avoir le type de l'article @**/
    public String ArticleType()
	{
		return ArtType; //Retourne le type de l'article
	}

	/**@ Description: Permet d'avoir le prix de base d'un article @**/
	public double ArticleBasePrix()
	{
		return BasePrix; //Retourne le montant de base
	}

	/**@ Description: Permet d'avoir une instance de l'objet mise associe a l'article @**/
	public ObjectArticleMise GetObjectMise()
	{
		return ArtMise; //Retourne l'instance de la mise
	}

	/**@ Description: Permet d'ajouter une mise @**/
	public void AddMise(String client, double mise_prix)
	{
		ArtMise.AddMise(client, mise_prix); //Ajoute la mise sur l'article
	}

	/**@ Description: Permet d'avoir la mise d'un client @**/
	public double ClientMise(String cli)
	{
		return ArtMise.ClientMise(cli); //Retourne la mise du client
	}

	/**@ Description: Permet d'avoir la plus haute enchere sous forme de map @**/
	public MapMise HEnchere()
	{
		return ArtMise.HauteEnchere(); //Retourne la plus haute enchere
	}

	/**@ Description: Permet d'avoir la plus haute enchere @**/
	public double HauteEnchere()
	{
		MapMise m=HEnchere();
		if(m==null) return BasePrix; //Retourne le prix de base si aucune mise
		return m.Mise; //Retourne la plus haute enchere
	}
	
	/**@ Description: Permet d'avoir la description de l'article @**/
	public String MakeDescr()
	{
		//Formate et retourne la description de l'article
		return ""+ArtName+": "+ArtType+", prix "
				 +BasePrix+", Fermeture a "+str_ech;
	}

	/**@ Description: Permet de savoir si les encheres sont formees su l'article @**/
	public boolean IsFermeture()
	{
		return(int_ech<=0); //Retourne true si le temps restant est egale ou inferieur a 0
	}

	/**@ Description: Permet d'avoir un taleau des maps @**/
	public MapMise[] GetMiseMap()
	{
		return(ArtMise.GetMiseMap()); //Retoure le tableau des mises
	}
} 
