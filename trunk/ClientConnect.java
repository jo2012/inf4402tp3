/**
 * ClientConnect.java
 *
 * Created on 9 vril 2007
 *
 * @author BRUN Joel & DEBONNEL Yann
 */

import java.util.Vector;


public class ClientConnect implements java.io.Serializable{
	public boolean isConnected;
        public Vector<Article> articles;
	
        
        ClientConnect(boolean b){ 
            this.isConnected = b;
            articles = new Vector<Article>();
        }
        
        public boolean isConnected(){return isConnected;}
        
        public boolean addArticle(Article art){
            return articles.add(art);
        }
        
        public Article getArticle(int i){
            if(isConnected) return articles.elementAt(i);
            else return null;
        }
        
        public Vector<Article> getArticles(){
           if(isConnected) return articles;
           else return null;
        }
        
        
        
        
}
