 INF4402 - TP2
---------------

 DEVELOPPMENT D'UN MARCHE D'ENCHERES ELECTRONIQUE
------------------------------------------------

 DONNEES DU REPERTOIRE COURANT
------------------------------

	/class
	Client.java
	ClientImpl.java
	ClientRemote.java
	Constant.java
	CreditCheckImpl.java
	CreditCheckRemote.java
	FrameClient.java
	make.bat
	makefile
	MapMise.java
	ObjectArticle.java
	ObjectArticleMise.java
	ObjectClient.java
	PolyEbayClient.java
	PolyEbayImpl.java
	PolyEbayRemote.java
	PolyEbayTimer.java
	PolyPaypalClient.java
	PolyPaypalImpl.java
	PolyPaypalRemote.java
	PolyServer.java
	readme.txt
	traces.txt



 COMPILATION (DANS LE REPERTOIRE COURANT OU SE TROUVE LES .JAVA)
---------------------------------------------------------------

Sous Linux:
	#> make pour compiler et make clean pour netoyer les .class

Sous Windows
	#> make //execute le fichier make.bat

NB: les .class generes sont dans le repertoire ./class




 CREATION ET ÉCHÉANCE DES ARTICLES
----------------------------------
La creation des articles se fait dans le constructeur de la classe d'implémentation
PolyEbayImpl. Dans ce constructeur, nous avons crée 3 articles sous forme d'objets.
Le temps de la fermeture des enchères est sous forme d'objet java GregorianCalendar
qui prend en paramètre respectievement l'année, le mois, le jour, l'heure, les minutes
et les secondes. les paramètres année, mois et jour ne sont pas pris en compte et peuvent 
avoir n'importe quelle valeur. Par contre les 3 derniers à savoir heure, minute et seconde
sont très importantes et fixe l'heure de fermeture de l'article.
Lorsque une heure est déjà passé, vous serez averti par un warning du serveur lors de son 
lancement comme quoi une échéance est passée et cet article sera mis dans un état
non évolutif (aucune mise à jour).



 EXECUTION - SOUS LA PLATE-FORME WINDOWS (RECOMMENDEE)
------------------------------------------------------
NB: Nous avons eu des problemes de memoire avec la machine virtuelle
java sous linux (probablement un probleme de securite), ce qui nous limitait
a 2 clients.
Par consequenet, tous nos tests ont ete realises sous la plate-forme window
c'est donc sous windows que nous aimerions que vous testez notre programme.
Merci.


Serveur:
	#> cd ./class
	#> start rmiregistry
	#> java PolyServer  //Demarre tous les serveurs (PolyEbay, PolyPaypal et CreditCheck)

Clients:
	#> java Client <nom du client> //sous la forme client1, client2, client3, ...




 DESCRIPTION DE L'INTERFACE CLIENT
----------------------------------

Menu Connecter:
---------------
	1. PolyPaypal, permet d'ouvrir une session sur Polypaypal et le succes de
cette operation active les boutons appropries sur l'interface (balance de credit)

	2. PolyEbay, permet d'ouvrir une session sur PolyEbay et le succes de cette
operation active tous les autres boutons et recupere la liste des articles du serveur


Menu Exit:
----------
	1. Fermer, permet de fermer la fenetre d'interface et par consequent 
ferme toutes les connections avec les serveurs


Liste des articles:
-------------------
	ComboBox permettant d'afficher la liste des articles au client afin 
qu'il fasse un choix et obtient la description avant de se decider de miser
ou pas. Initialement vide et rempli apres connection sur PolyEbay


Description de l'article:
-------------------------
	Chaine static permettant d'afficher au client la description de l'article
selection dans la liste des articles


Numero de connection:
---------------------
	EditBox (read only) permettant d'afficher au client son numero de connection
par rapport au nombre de connection enregistrees sur le serveur


Somme a miser:
--------------
	EditBox permettant au client de saisir le montant a miser sur l'article
selectionne. N'est editable que lorsqu'un article est selectionne


Votre balance de credit:
------------------------
	EditBox (read only) permettant d'afficher au client son solde courant
disponible sur PolyPaypal et valide par CreditCheck


Mise sur <Nom Article>:
-----------------------
	Bouton permettant au client de miser le montant saisie sur l'article
selectionne. N'est actif que lorsqu'un article est selectionne


Messages de sortie:
-------------------
	TextArea permettant d'afficher toutes les notifications du serveur au client
fonctionne comme une console non editable.




	
--------------------------------------------------------------------------------		
Pour toute precision suplementaire vous pouvez toujours nous
envoyer un mail.
/Merci