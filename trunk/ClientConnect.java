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
        public Vector<String> article;
	
        
        ClientConnect(boolean b){ 
            this.isConnected = b;
            article = new Vector<String>();
        }
        
        public boolean isConnected(){return isConnected;}
        
        public boolean addArticle(String art){
            return article.add(art);
        }
        
        public String getArticle(int i){
            return article.elementAt(i);
        }
        
        
        
        
}
