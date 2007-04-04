import java.awt.event.*;

/**@ Classe permettant de gerer le timer du serveur @**/
/** PolyEbay (les echeances des articles) @**/
public class PolyEbayTimer implements ActionListener
{
	private PolyEbayImpl objEbay;
	private int update_tab=0, update_data=0; 
	PolyEbayTimer(PolyEbayImpl ebay, int update)
	{
		//Recupere l'instance de PolyEbay et le temps de mise a jour
		objEbay=ebay; update_tab=update;
	}
	public void actionPerformed(ActionEvent e)
	{
		ObjectArticle _article; update_data++;
		//synchronized(objEbay.articles)
		//{
			//Parcours tous les articles et met a jour les echeances
	        for (int i = 0; i < objEbay.articles.size(); i++) 
	        { 
				//Recupere un article de la liste
				_article = (ObjectArticle) objEbay.articles.elementAt( i ); 
				if(_article.bIsValid && _article.UpdateTimer()<=0)
				{
					_article.bIsValid=false; //Cet article n'est plus valide
					String art=_article.ArticleName(); //Recupere le nom de l
					objEbay.PostMessage("PolyEbay: Echeance de l'article <"+art+">");
					objEbay.PerformVerification(art, i); //Verifie le gagnant et son solde
					continue;
				}
	        } 
			//Si temps de mise a jour du tableau
		    if(update_data>=update_tab)
		    {
		    	objEbay.ParseNotify(); //On affiche le tableau
		    	update_data=0; //On reinitialise la temps
		    }
		//}
	}
}
