import java.util.*; 

/**@ Classe permettant de representer l'objet ObjectArticleMise @**/
public class ObjectArticleMise
{ 
	private ObjectArticle objArticle;
	private Vector ClientMise=new Vector();

	/**@ Constructeur @**/
	public ObjectArticleMise(ObjectArticle obj)
	{
		objArticle = obj; //Recupere l'objet article
	}

	/**@ Description: Permet d'ajouter une mise du client @**/
    public void AddMise(String client, double mise_prix)
	{
		MapMise mise; 
		//Ajoute la mise du client dans la liste
		ClientMise.add(new MapMise(client, mise_prix, new GregorianCalendar()));
	}

	/**@ Description: Permet d'avoir la mise d'un client @**/
    public double ClientMise(String cli)
	{
		MapMise mise; 
		
		//Parcours toutes les mises
	    for (int i = 0; i < ClientMise.size(); i++) 
	    { 
			mise = (MapMise) ClientMise.elementAt( i ); 
			if(mise.client.equals(cli)) //Si c'est le client recherche
				return mise.Mise; //Retourne la mise du client
	    }
		return -1; //Aucun client trouve
	}

	/**@ Description: Permet de recuperer toute les maps des articles avec les clients @**/
	public MapMise[] GetMiseMap()
	{
		MapMise[] mise = new MapMise[ClientMise.size()];
		
		//Parcours toute les mappages pour la plus haute enchere
	    for (int i = 0; i < ClientMise.size(); i++) 
	    { 
			//Ajoute le map courant dans le tableau
			mise[i] = (MapMise) ClientMise.elementAt( i ); 
	    }
		return(mise); //Retourne le tableau des maps
	}

	/**@ Description: Permet d'avoir la plus haute enchere sous forme de map @**/
	public MapMise HauteEnchere()
	{
		MapMise mise; double Base=objArticle.ArticleBasePrix(); //Recupere le montant de pase
		
		//Parcours toute les mappages pour la plus haute enchere
	    for (int i = 0; i < ClientMise.size(); i++) 
	    { 
			mise = (MapMise) ClientMise.elementAt( i ); 
			if(mise.Mise > Base) //Si le montant depasse la base
				Base = mise.Mise;
	    }
		//Parcours toute les mappages pour le client qui a la plus haute enchere
	    for (int i = 0; i < ClientMise.size(); i++) 
	    { 
			mise = (MapMise) ClientMise.elementAt( i ); 
			if(mise.Mise==Base) //Si c'est la mise ayant la plus haute enchere
				return mise; //Retourne la mise recherchee
	    }
		return null; //Aucune mise trouvee
	}
} 
