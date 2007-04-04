import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.rmi.*; 

/**@ Classe permettant de representer l'interface graphique du client @**/
public class FrameClient extends JFrame implements ActionListener 
{
	private JButton bMiser_art;
	private JTextField iSoeMise, iCreditClient, iNbrClient;
	private JLabel art_info, lserver_msg, label_sp, label_sp3, lNbrClient,
				label_sp2, lMise, lArticle, lCredit, lDescription, label_sp4, label_sp5;
	private JMenuBar bar;
	private JMenu connection, exit;
	private JMenuItem connect_PolyPayPal, connect_PolyEbay, exit_fermer;
	private JPanel panel_articles, panel_msg;
	private String Articles_Choix = "(Choisir un article)";
	private String ArtDest = "Nom article";
	private JComboBox ListArticle;
	private DefaultComboBoxModel ComboModel;
	private JTextArea lMsgPane;
	
	//Remote
	private PolyEbayRemote polyEbay;
	PolyPaypalRemote polyPaypal;
	ClientImpl objClient;
	String clientUser;
	boolean IsPolyPaypal, IsPolyEbay;

	/**@ Constructeur @**/
	public FrameClient(String client, PolyPaypalRemote Paypal, PolyEbayRemote Ebay, ClientImpl cli)
	{
		super(client);
		clientUser = client;
		
		polyEbay = Ebay; 
		polyPaypal = Paypal;
		objClient = cli;
		IsPolyPaypal=IsPolyEbay=false;
		
		setSize(500, 400);
		setBackground(new Color(245,233,200));
		setResizable(false);
	}

	/**@ Description: Permet dinitialise ou de modifier la liste des articles @**/
	public void ChooseArticle(String[] art, boolean bFirst)
	{
		if(art==null) return; //Si aucun article
		if(!bFirst) //Efface tous les elements presents
		{
			for(int j=1; j<ComboModel.getSize(); j++)
			{
				//ComboModel.removeElementAt(j);
				ListArticle.removeItemAt(j);
			}
		}
		
		//Parcours tous les utilisateurs
	    for (int i = 1; i < art.length+1; i++) 
	    { 
			//Ajoute les articles dans le ComboBox
	    	ComboModel.insertElementAt(art[i-1], i);
	    }
	}

	/**@ Description: Permet d'afficher un message @**/
	public void MsgOut(String Msg)
	{
		if(lMsgPane.getLineCount()>=5)
			lMsgPane.setText("");
		lMsgPane.append(Msg+"\n");
		System.out.println(Msg);
	}

	/**@ Description: Permet d'effectuer les connections sur les serveur PolyEbay et PolyPaypal @**/
	public boolean ConnectServer(int lserver)
	{
		try
		{
			switch(lserver) //Verifie le serveur sur qui on veut effectuer la connection
			{
				case Constant.POLYPAYPAL_SERVER: //Si c'est le serveur PolyPaypal
				{
					if(polyPaypal==null) //Aucune instance n'a ete cree auparavant
					{
						MsgOut("Impossible de se connecter sur PolyPaypal!");
						return false; //Retourne echec
					}
					MsgOut("Connection sur PolyPaypal...");
					//Effectue la connection sur le serveur et recupere le solde du compte
					double icredit = polyPaypal.connect(objClient, clientUser);
					if(icredit <= 0) 
					{
						MsgOut("Impossible de se connecter sur PolyPaypal!");
						MsgOut("Verifiez si vous disposez d'argent dans votre compte!");
						return false;
					}
					iCreditClient.setText(""+icredit+"$"); //Affiche le montant sur l'interface
					MsgOut("Vous etes connecte sur PolyPaypal");
					MsgOut("Votre solde est de: "+icredit);
					IsPolyPaypal=true; //Siganle que la connection est effectuee avec succes sur PolyPaypal
					return IsPolyPaypal;  //Retourne succes
				}
				case Constant.POLYEBAY_SERVER:  //Si c'est le serveur PolyEbay
				{
					if(!IsPolyPaypal) //Si aucune connection avec le serveur PolyPaypal
					{
						//Notify le client
						MsgOut("Impossible de se connecter sur PolyEbay!");
						MsgOut("Ouvrez d'abord une connection sur PolyPaypal!");
						return false; //Retourne echec
					}
					if(polyEbay==null) //Aucune instance de PolyEbay n'a ete cree auparavant
					{
						MsgOut("Impossible de se connecter sur PolyEbay!");
						return false;
					}
					MsgOut("Connection sur PolyEbay...");
					//Effectue la connection sur PolyEbay
					if(polyEbay.connect(objClient, clientUser)) //Si Succes de connection
					{
						MsgOut("Vous etes connecte sur PolyEbay");
						int cli_count = polyEbay.ClientCount(); //Recupere le nombre de client connectes
						MsgOut("<"+cli_count+"> client(s) sont presentement connectes!");
						iNbrClient.setText(""+cli_count); //met a jour l'identifiant de la connection
						return UpdateArticles(true); //met a jour l'interface
					}
					else MsgOut("Impossible de se connecter sur PolyEbay!");
					return false;
				}
				default: return false;
			}
		}
        catch ( RemoteException e ) 
        { 
			MsgOut("***RemoteException in ConnectServer()***"); 
            return false;
        }
	}

	/**@ Description: Permet de mettre a jour les controles de l'interface du client @**/
	public boolean UpdateArticles(boolean bFirst)
	{
		try
		{
			if(polyEbay==null) return false; //Si aucune connection avec PolyEbay
			if(polyPaypal==null) return false; //Si aucune connection avec PolyPaypal
			if(!bFirst) //Si c'est pas la premiere fois
			{
				//recupere le solde du compte du client
				double dollard = polyPaypal.CheckCredit(clientUser);
				iCreditClient.setText(""+dollard+"$"); //Affiche sur l'interface
			}
			ChooseArticle(polyEbay.GetArticles(), bFirst); //Met a jour la liste des articles
			return true; //Retourne succes
		}
        catch ( RemoteException e ) 
        { 
			MsgOut("***RemoteException in UpdateArticles()***"); 
			return false;
        }
	}

	/**@ Description: Permet de recuperer l'instance du serveur PolyPaypal associee @**/
	public PolyPaypalRemote GetPaypal()
	{
		return(polyPaypal); //Retourne l'instance de PolyPaypal
	}

	/**@ Description: Permet de se deconnecter des serveurs @**/
	public void DisconnectServer()
	{
		try
		{
			MsgOut("Fermeture du systeme...");
			if(polyPaypal!=null) //Si connection avec PolyPaypal
				polyPaypal.disconnect(clientUser); //Deconnecte le client chez PolyPaypal
			if(polyEbay!=null) //Si connection avec PolyPaypal
				polyEbay.disconnect(clientUser); //Deconnecte le client chez PolyEbay
		}
		catch(RemoteException e)
        { 
			MsgOut("RemoteException in DisconnectServer()!"); 
        }
		System.exit(0);
	}

	/**@ Description: Permet d'initialiser les contriles de l'interface client @**/
	public void InitFrame()
	{
		panel_articles = new JPanel();
		panel_articles.setLayout(new GridLayout (9, 2, 10, 10));
		panel_msg = new JPanel();
		panel_msg.setLayout(new GridLayout (1, 1));
		ComboModel = new DefaultComboBoxModel();
		ListArticle = new JComboBox(ComboModel);
		ComboModel.addElement(Articles_Choix);
		ListArticle.addActionListener(this);
		
		label_sp = new JLabel();
		label_sp2 = new JLabel();
		label_sp3 = new JLabel();
		label_sp4 = new JLabel();
		label_sp5 = new JLabel();
		lMise = new JLabel("Somme à miser: ", SwingConstants.RIGHT); 
		lArticle = new JLabel("Liste des articles: ", SwingConstants.RIGHT);
		lCredit = new JLabel("Votre balance de credit: ", SwingConstants.RIGHT);
		lDescription = new JLabel("Description de l'article: ", SwingConstants.RIGHT);
		art_info = new JLabel("--", SwingConstants.CENTER);
		lserver_msg = new JLabel("[Messages de sortie]", SwingConstants.LEFT);
		lNbrClient = new JLabel("Numero de connection: ", SwingConstants.RIGHT);
		
		iSoeMise = new JTextField(); 
		iCreditClient = new JTextField();
		iNbrClient = new JTextField();
		iNbrClient.setEditable(false);
		iCreditClient.setEditable(false);
		iSoeMise.setEnabled(false); 
		
		iCreditClient.setText("--");
		iNbrClient.setText("--");
		iSoeMise.setHorizontalAlignment(SwingConstants.CENTER);
		iCreditClient.setHorizontalAlignment(SwingConstants.CENTER);
		iNbrClient.setHorizontalAlignment(SwingConstants.CENTER);
		bMiser_art = new JButton("--");
		iSoeMise.addActionListener(this); 
		
		lMsgPane = new JTextArea(5, 1);
		lMsgPane.setEditable(false);
		lMsgPane.setBackground(new Color(0, 0, 0));
		lMsgPane.setForeground(new Color(210, 210, 210));
		lMsgPane.setFont(new Font(null, Font.BOLD|Font.ITALIC, 12));
		panel_msg.add(lMsgPane);
		
		panel_articles.add(lArticle);
		panel_articles.add(ListArticle);
		panel_articles.add(lDescription);
		panel_articles.add(art_info);
		panel_articles.add(label_sp4);
		panel_articles.add(label_sp5);
		panel_articles.add(lNbrClient);
		panel_articles.add(iNbrClient);
		panel_articles.add(lMise); 
		panel_articles.add(iSoeMise); 
		panel_articles.add(lCredit);
		panel_articles.add(iCreditClient);
		panel_articles.add(label_sp);
		panel_articles.add(bMiser_art);
		panel_articles.add(label_sp2);
		panel_articles.add(label_sp3);
		panel_articles.add(lserver_msg);
		
		bMiser_art.addActionListener(this);
		bMiser_art.setEnabled(false);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(panel_articles, BorderLayout.CENTER );
		getContentPane().add(panel_msg, BorderLayout.SOUTH);

		connect_PolyPayPal = new JMenuItem("PolyPayPal");
		connect_PolyPayPal.addActionListener(this);
		connect_PolyEbay = new JMenuItem("PolyEbay");
		connect_PolyEbay.addActionListener(this);
		exit_fermer = new JMenuItem("Fermer");
		exit_fermer.addActionListener(this);

		connection = new JMenu("Connecter");
		exit = new JMenu("Exit");
		connection.add(connect_PolyPayPal);
		connection.addSeparator();
		connection.add(connect_PolyEbay);
		exit.add(exit_fermer);

		bar = new JMenuBar();
		bar.add(connection);
		bar.add(exit);
		setJMenuBar(bar);
		addWindowListener(new FermeFenetre(this));
	}

	/**@ Description: Permet de miser sur un article @**/
	public boolean MiseArticle()
	{
		try
		{
			if(polyEbay==null) //Aucune connection avec PolyEbay
			{
				MsgOut("Veuillez vous connecter sur PolyEbay avant de miser!");
				return false; //Retourne echec
			}
			if(iSoeMise.getText().length()==0) //Si aucun montant
			{
				MsgOut("Veuillez fournir une somme valide!");
				return false; //Retourne echec
			}
			double _mise = Double.parseDouble(iSoeMise.getText());
			MsgOut("Vous avez mise "+_mise+"$ sur l'article <"+ArtDest+">");
			if(!polyEbay.MiserArticle(clientUser, ArtDest, _mise))//Effectue la mise sur PolyEbay
			{
				return false; //Si echec retourne false
			}
			return true; //Retourne succes
		}
		catch(RemoteException e)
        { 
			MsgOut("RemoteException in MiseArticle()!"); 
			return false;
        }
	}

	/**@ Description: Permet de traiter l'article seclectione @**/
	public boolean SelectArticle()
	{
		if(ListArticle.getSelectedIndex()==0 || polyEbay==null) //Si aucune selection ou connection
		{
			bMiser_art.setEnabled(false);
			iSoeMise.setEnabled(false);
			return false; //Retourne echec
		}
		bMiser_art.setEnabled(true);
		iSoeMise.setEnabled(true);
		ArtDest = ListArticle.getSelectedItem().toString(); //Recupere le nom de l'article
		MsgOut("Vous avez choisi: "+ArtDest);
		try
		{
			String des = polyEbay.GetArtDescr(ArtDest); //Recupere la description de l'article selectionne
			art_info.setText(des);
			bMiser_art.setText("Miser sur "+ArtDest); //Notify le client sur la mise a effectuer
			MsgOut(des); return true; //Retourne succes
		}
		catch(RemoteException ex)
		{ 
			System.out.println("RemoteException in SelectArticle()!");
			return false;
		}
	}

	/**@ Description: Permet de traiter les actions du client effectuees sur l'interface @**/
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == bMiser_art){ //Si c'est l'option miser sur un article
			if(!MiseArticle()){ //Effectuer la mise
				MsgOut("Impossible de miser sur l'article <"+ArtDest+">");
			}
		}
		else if(e.getSource() == connect_PolyPayPal){ //Si c'est une connection 
			if(ConnectServer(Constant.POLYPAYPAL_SERVER)) //Effectue la connection sur PolyPaypal
				connect_PolyPayPal.setEnabled(false); //Si succes desactive le menu
		}
		else if(e.getSource() == connect_PolyEbay){ //Si c'est une connection
			if(ConnectServer(Constant.POLYEBAY_SERVER)) //Effectue la connection sur PolyEbay
				connect_PolyEbay.setEnabled(false); //Si succes desactive le menu
		}
		else if(e.getSource() == exit_fermer){ //Si le bouton exit
			DisconnectServer(); //Deconnecter les serveurs
		}
		else if(e.getSource() == ListArticle){ //Si selection d'un article
			SelectArticle(); //Traite la selection
		}
	}

	/**@ Classe permettant de gere la fermeture du programme @**/
	public class FermeFenetre extends WindowAdapter
	{
		private FrameClient Wfm;
		public FermeFenetre(FrameClient frm)
		{
			Wfm = frm; //Recupere une instance de la fenetre
		}

		/**@ Description: Permet de gerer l'evennement de fermeture de la fenetre @**/
		public void windowClosing(WindowEvent w){
			Wfm.DisconnectServer(); //Effectue la deconnection
		}
	 }
}