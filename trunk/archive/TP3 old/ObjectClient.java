import java.io.*;
import java.util.*;

/**@ Classe permettant de representer l'objet ObjectClient @**/
public class ObjectClient implements Serializable
{ 
	private String CliName;
	private double CliCredit;
	
	/**@ Constructeur @**/
	public ObjectClient(String cli, double cre)
	{
		CliName=cli; CliCredit=cre;
	}

	/**@ Description: Permet de recuperer le nom du client @**/
    public String ClientName()
	{
		return CliName; 
	}

	/**@ Description: Permet de recuperer le credit associe @**/
    public double ClientCredit()
	{
		return CliCredit; //retourne le credit associe
	}

	/**@ Description: Permet de modifier le credit du client @**/
	public boolean ModifyCredit(double modif, boolean bAdd)
	{
		if(modif<=0) return(false); //Si montant invalide
		CliCredit += (bAdd)?(modif):(-modif); //Ajoute ou enleve
		return(true); //Retourne succes
	}
} 
