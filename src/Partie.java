import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import utilitaire.Delai;


public class Partie implements java.io.Serializable{
	
	private ArrayList<Personnage> listPersonnage;  // Liste des personnage sur une partie avec le personnage controle par le joueur a l'index 0
	private ArrayList<Mur> listMur;
	private ArrayList<Personnage> combat;  //Liste des personnages en combat, Le personnage dont le tour est actif sera toujours place en premier element de l'arraylist
	
	private Timer timer;  // Timer des tours

	
	private int count;  //Duree des tours en seconde
	private int joueur;   //int pouvant prendre comme valeur 0 ou 1 indiquant l'indice du personnage en train de faire son tour
	private int largeurMap;
	private int posTempX; // Position temporaire du Joueur
	private int posTempY;
	
	private String id;  // Id unique de la partie
	private String mssgCbt="";  // Message d'information sur le combat
	
	public Partie() {
		
		this.listPersonnage = new ArrayList<Personnage>();
		this.listMur = new ArrayList<Mur>();
		this.combat = new ArrayList<Personnage>();
		this.count = 0;
		this.joueur  = 0;
		this.largeurMap = 15;
		this.id = saveId();
	}
	
	public Partie(Partie partie) {
		
		this.listPersonnage = new ArrayList<Personnage>(partie.getListPersonnage());
		this.listMur = new ArrayList<Mur>(partie.getListMur());
		this.combat = new ArrayList<Personnage>(partie.getListCombat());
		this.timer = partie.getTimer();
		this.count = partie.getCount();
		this.joueur  = partie.getJoueur();
		this.largeurMap = partie.getLargeurMap();
		this.posTempX = partie.getPosTempX();
		this.posTempY = partie.getPosTempY();
		this.id = partie.getId();
		this.mssgCbt = partie.getMssgCbt();
								
	}
	// COnstructeur par copie permettant d'initialiser la liste des personnages avec une liste de son choix passee en parametre
	public Partie(Partie partie,ArrayList<Personnage> list) {
		
		this.listPersonnage = new ArrayList<Personnage>(list);
		this.listMur = new ArrayList<Mur>(partie.getListMur());
		this.combat = new ArrayList<Personnage>(partie.getListCombat());
		this.timer = partie.getTimer();
		this.count = partie.getCount();
		this.joueur  = partie.getJoueur();
		this.largeurMap = partie.getLargeurMap();
		this.posTempX = partie.getPosTempX();
		this.posTempY = partie.getPosTempY();
		this.id = partie.getId();
		this.mssgCbt = partie.getMssgCbt();
								
	}
	
	//Sauvegarde une partie dans un fichier portant l'id de celle ci
	
	public void sauvegarder() {
		
		ObjectOutputStream objOutputStream = null;
		String idPartie = this.id;
		String fileName = "save/partie" + idPartie + ".ser";
		File file = new File(fileName);
		
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
		
			FileOutputStream fichier = new FileOutputStream(fileName);
			objOutputStream = new ObjectOutputStream(fichier);
			objOutputStream.writeObject(this);
			objOutputStream.flush();
		}
		catch (final java.io.IOException e) {	
			e.printStackTrace();
		} 
		finally {

			try {

				if (objOutputStream != null) {
					objOutputStream.flush();
					objOutputStream.close();
				}
			} 
			catch (final IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	
	/* Methode permettant de restaurer une sauvegarde de partir a partie de son fichier de sauvegarde
	 * 
	 * Renvoi cette meme partie si le fichier de sauvegarde de celle ci existe
	 * 
	 * Sinon renvoi null
	 */
	
	public Partie restaurer() {
		
		ObjectInputStream objInputStream = null;
		Partie partieRestaure = null;
		String idPartie = this.id;
		String fileName = "save/partie" + idPartie + ".ser";
		
		try {
			
			FileInputStream fichier = new FileInputStream(fileName);
			objInputStream = new ObjectInputStream(fichier);
			partieRestaure = (Partie) objInputStream.readObject();
		} 
		catch (final java.io.IOException e) {
			e.printStackTrace();
		} 
		catch (final ClassNotFoundException e) {
			e.printStackTrace();
		} 
		finally {
			
			try {
				if (objInputStream != null) {
					objInputStream.close();
				}
			} 
			catch (final IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return partieRestaure;
	}
	
	public String saveId(){  
		
		String id = readId();
		int idNbr = Integer.parseInt(id); 
		int idNbrPlus = idNbr+1;
		
		try {
			
			Writer fW = new FileWriter("src/idPartie.txt");
			fW.write("" + idNbrPlus);
			fW.close();
		} 
		catch (IOException e) {
	
			e.printStackTrace();
		}
		
		return "" + idNbrPlus;
	}
	
	public String readId() {
		
		String id = "";
		
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader("src/idPartie.txt"));
			
			id = bufferedReader.readLine();
		    
			
			bufferedReader.close();
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return id;
		
		
	}
	 

	public ArrayList<Personnage> getListPersonnage() {
		
		return this.listPersonnage;
	}
	
	public ArrayList<Personnage> getListCombat() {
		
		return this.combat;
	}
	
	public ArrayList<Mur> getListMur() {
		
		return this.listMur;
	}
	
	public Timer getTimer() {
		
		return this.timer;
	}
	
	public int getCount() {
		
		return this.count;
	}	
	
	public int getJoueur() {
		
		return this.joueur;
	}
	
	public int getLargeurMap() {
		
		return this.largeurMap;
	}
	
	public void setLargeurMap(int largeurMap) {
		
		this.largeurMap = largeurMap;
	}
	
	public int getPosTempX() {
		
		return this.posTempX;
	}
	
	public int getPosTempY() {
		
		return this.posTempY;
	}
	
	public String getId() {
		
		return this.id;
	}
	
	public String getMssgCbt() {
		
		return this.mssgCbt;
	}
	
	
	public boolean personnageDeplacableHaut(Personnage personnage) {
		
		if(listPersonnage.contains(personnage)) {
			
			int indice = listPersonnage.indexOf(personnage);
		
			if(listPersonnage.get(indice).getPositionY()>0) {
				
				Iterator iteratorHaut = listMur.iterator();
				
				while(iteratorHaut.hasNext()) {
					
					Mur mur = (Mur) iteratorHaut.next();
					
					if((listPersonnage.get(indice).getPositionY()-1) == mur.getPositionY()) 
						return false;
					
					
				}
				
				return true;
				
			}
			
			else
				return false;
		}
		
		else 
			return false;
	}
	
	public boolean personnageDeplacableBas(Personnage personnage) {
		
		if(listPersonnage.contains(personnage)) {
			
			int indice = listPersonnage.indexOf(personnage);
			
			if(listPersonnage.get(indice).getPositionY()<(largeurMap-1)) {
				
				Iterator iteratorBas = listMur.iterator();
				
				while(iteratorBas.hasNext()) {
					
					Mur mur = (Mur) iteratorBas.next();
					
					if((listPersonnage.get(indice).getPositionY()+1) == mur.getPositionY()) 
						return false;
					
					
				}
				
				return true;
				
			}
			
			else
				return false;
		}
		
		else
			return false;
			
	}

	public boolean personnageDeplacableGauche(Personnage personnage) {
		
		if(listPersonnage.contains(personnage)) {
			
			int indice = listPersonnage.indexOf(personnage);
		
			if(listPersonnage.get(indice).getPositionX()>0) {
				
				Iterator iteratorGauche = listMur.iterator();
				
				while(iteratorGauche.hasNext()) {
					
					Mur mur = (Mur) iteratorGauche.next();
					
					if((listPersonnage.get(indice).getPositionX()-1) == mur.getPositionX()) 
						return false;
					
					
				}
				
				return true;
				
			}
		
			else
				return false;			
		}
		
		else
			return false;
		
	}

	public boolean personnageDeplacableDroite(Personnage personnage) {
		
		if(listPersonnage.contains(personnage)) {
			
			int indice = listPersonnage.indexOf(personnage);
		
			if(listPersonnage.get(indice).getPositionX()<(largeurMap-1)) {
				
				Iterator iteratorDroite = listMur.iterator();
				
				while(iteratorDroite.hasNext()) {
					
					Mur mur = (Mur) iteratorDroite.next();
					
					if((listPersonnage.get(indice).getPositionX()+1) == mur.getPositionX()) 
						return false;
					
					
				}
				
				return true;
				
			}
			
			else
				return false;
		}
		
		else
			return false;
		
	}
	
	public boolean personnageDeplacable(Personnage personnage, int direction) {
		
		if(listPersonnage.contains(personnage)) {
		
			int indice = listPersonnage.indexOf(personnage);
			
			if(direction==0)
					return personnageDeplacableHaut(listPersonnage.get(indice));

			else if(direction==1)
					return personnageDeplacableBas(listPersonnage.get(indice));
					
			else if(direction==2)
					return personnageDeplacableGauche(listPersonnage.get(indice));
					
			else if(direction==3)
					return personnageDeplacableDroite(listPersonnage.get(indice));
						
			
			
		}
		
		
		
		return false;
		
	}
	
	
	
	
	
	/*
	 *  Methode permettant de se deplacer sur la map avec le personnage en parametre
	 * 
	 * Valeurs du parametre choix : 
	 *  
	 * 0 pour se deplacer en haut
	 * 
	 * 1 pour se deplacer en bas
	 * 
	 * 2 pour se deplacer e gauche
	 * 
	 * 3 pour se deplacer e droite
	 * 
	 */
	public void deplacerPersonnageHorsCombat(Personnage personnage,int direction) {  
		
		if(listPersonnage.contains(personnage)) {
			int i = listPersonnage.indexOf(personnage);  // Les index i seront nommes avec un chiffre s'incrementant a chaque nouvelle utilisation pour eviter de les confondre
			if(personnageDeplacable(listPersonnage.get(i), direction)) {
				listPersonnage.get(i).deplacer(direction);
				ennemyCombattable(personnage);  // Entre en combat au contact d'un ennemi
			}	
						
		}	
	}
	
	/*
	 *  Methode permettant de se deplacer sur la map avec le personnage en parametre et ceci en combat
	 * 
	 * Valeurs du parametre choix : 
	 *  
	 * 0 pour se deplacer en haut
	 * 
	 * 1 pour se deplacer en bas
	 * 
	 * 2 pour se deplacer e gauche
	 * 
	 * 3 pour se deplacer e droite
	 * 
	 * Valeurs de renvoi : 
	 * 
	 * 0 si le deplacement c'est bien effectue
	 * 
	 * 1 si le personnage n'avait pas assez de pm pour bouger
	 * 
	 * 2 si le personnage n'etait pas dans la partie
	 * 
	 */
	public int deplacerPersonnageEnCombat(Personnage personnage,int direction) {
		
		if(combat.contains(personnage)) {
			
			int i2 = combat.indexOf(personnage);
			Iterator iteratorL2 = combat.iterator();
			
			while(iteratorL2.hasNext()) {
				
				Personnage perso3 = (Personnage) iteratorL2.next();
				
				switch(direction) {   // Switch avec a chaque case des controleurs permettant d'assurer que deux personnages en combat ne se retrouve pas sur la meme case de la map
					
					case 0 : 
						
						if(personnageDeplacable(listPersonnage.get(i2), direction)) {  // Si le personnage est deplacable en haut le deplace
							
							if((!(listPersonnage.get(i2).equals(perso3)))){  // Si il n'ya pas n'ennemi sur la case vise du deplacement
								
								if(listPersonnage.get(i2).getPositionX() == perso3.getPositionX()){
									
									if(((listPersonnage.get(i2).getPositionY())-1) != perso3.getPositionY()){
										
										if(listPersonnage.get(i2).getPm()>0){  // Si le joueur a assez de pm pour bouger
											
											listPersonnage.get(i2).deplacer(direction);
											listPersonnage.get(i2).downPm();
											return 0;
										}
										
										else
											return 1;
										}
							
									
									}
									else {
									
										if(listPersonnage.get(i2).getPm()>0){  // Si le joueur a assez de pm pour bouger
											
											listPersonnage.get(i2).deplacer(direction);
											listPersonnage.get(i2).downPm();
											return 0;
										}
									
										else
											return 1;
									}
							}
						}	
						break;	
					
					case 1 : 
						
						if(personnageDeplacable(listPersonnage.get(i2), direction)) {  // Si le personnage est deplacable en bas le deplace
							
							if(!(listPersonnage.get(i2).equals(perso3))) {
								
								if(listPersonnage.get(i2).getPositionX() == perso3.getPositionX()) {
									
									if(((listPersonnage.get(i2).getPositionY())+1) != perso3.getPositionY()) {
									
										if(listPersonnage.get(i2).getPm()>0){  // Si le joueur a assez de pm pour bouger
											
											listPersonnage.get(i2).deplacer(direction);
											listPersonnage.get(i2).downPm();
											return 0;
										}
										
										else
											return 1;
										}
							
									
									}
									else {
									
										if(listPersonnage.get(i2).getPm()>0){  // Si le joueur a assez de pm pour bouger
											
											listPersonnage.get(i2).deplacer(direction);
											listPersonnage.get(i2).downPm();
											return 0;
										}
									
										else
											return 1;
									}
							}
						}	
						break;
						
					case 2 : 
						
						if(personnageDeplacable(listPersonnage.get(i2), direction)) {  // Si le personnage est deplacable a gauche le deplace
							
							if(!(listPersonnage.get(i2).equals(perso3))) {
								
								if(listPersonnage.get(i2).getPositionY() == perso3.getPositionY()) {
									
									if(((listPersonnage.get(i2).getPositionX())-1) != perso3.getPositionX()) {
									
										if(listPersonnage.get(i2).getPm()>0){  // Si le joueur a assez de pm pour bouger
											
											listPersonnage.get(i2).deplacer(direction);
											listPersonnage.get(i2).downPm();
											return 0;
										}
										
										else
											return 1;
										}
							
									
									}
									else {
									
										if(listPersonnage.get(i2).getPm()>0){  // Si le joueur a assez de pm pour bouger
											
											listPersonnage.get(i2).deplacer(direction);
											listPersonnage.get(i2).downPm();
											return 0;
										}
									
										else
											return 1;
									}
							}
						}	
						break;
						
					case 3 :  
						
						if(personnageDeplacable(listPersonnage.get(i2), direction)) {  // Si le personnage est deplacable a droite le deplace
							
							if(!(listPersonnage.get(i2).equals(perso3))) {
								
								if(listPersonnage.get(i2).getPositionY() == perso3.getPositionY()) {
									
									if(((listPersonnage.get(i2).getPositionX())+1) != perso3.getPositionX()) {
									
										if(listPersonnage.get(i2).getPm()>0){  // Si le joueur a assez de pm pour bouger
											
											listPersonnage.get(i2).deplacer(direction);
											listPersonnage.get(i2).downPm();
											return 0;
										}
										
										else
											return 1;
										}
							
									
									}
									else {
									
										if(listPersonnage.get(i2).getPm()>0){  // Si le joueur a assez de pm pour bouger
											
											listPersonnage.get(i2).deplacer(direction);
											listPersonnage.get(i2).downPm();
											return 0;
										}
									
										else
											return 1;
									}
							}
						}	
						break;
				
				}
				

					
			}
			

		}
		
		return 2;
	}
	
	
	public void deplacerPersonnage(Personnage personnage, int direction) {
		
		if(listPersonnage.contains(personnage)) {
		
			int i3 = listPersonnage.indexOf(personnage);
			
			if(listPersonnage.get(i3).getEnCombat())   // Si le personnage est en combat le deplace
				deplacerPersonnageEnCombat(personnage,direction);
			else                                       // Si le personnage n'est pas en combat le deplace
				deplacerPersonnageHorsCombat(personnage,direction);
				
		}
	}
	
	
	public void ennemyCombattable (Personnage perso1){  // Regarde si un ennemi est sur la case du personnage en parametre avant d'etre en combat
		

		
		Iterator iteratorL = listPersonnage.iterator();
		
		if(listPersonnage.contains(perso1)) {
			
			int i = listPersonnage.indexOf(perso1);
		
			while(iteratorL.hasNext()){
				
				Personnage perso2 = (Personnage) iteratorL.next();
				
				if((!(listPersonnage.get(i).equals(perso2))) && (listPersonnage.get(i).getPositionX() == perso2.getPositionX()) && (listPersonnage.get(i).getPositionY() == perso2.getPositionY())) {  // Si les deux personnages sont differents mais a la meme place
					
					
					this.posTempX = listPersonnage.get(0).getPositionX();
					this.posTempY = listPersonnage.get(0).getPositionY();
					perso1.entreCombat();
					perso2.entreCombat();
					combat.add(perso1); // Ajoute les deux personnage au combat
					combat.add(perso2);
					placementCombatTop(combat.get(0)); 
					timeTour();
					joueur=0;
				}	
	
			}
		}
		
				
	}
	
	
	public void timeTour() {
		
		count = 0;
		
		timer = new Timer();
		
		
		
			timer.schedule(new TimerTask() {
				
				  @Override
				  public void run() {
					 
					  if(count==30) {
						  
						 finTour();
						 
						  
						 
						
					  }
					 
					  count++;
					  
					  if(combat.size()==0) {
						  
						  timer.cancel();
						  timer.purge();
						  return;
						  
					  }
					  
					  
					  
					
					   
					  
				  }
				  
			}, 0,1000);
		
		
		
	}
	
	
	
	
	//Place un personnage en haut de map en verifiant que la case oe il doit etre n'est pas deja occupee par un mur
	
	public void placementCombatTop(Personnage personnage) {
		
		personnage.setPositionX(0);
		personnage.setPositionY(1);
		
	}
	
	//Place un personnage en bas de map en verifiant que la case oe il doit etre n'est pas deja occupee par un mur
	
	public int placementCombatBot(Personnage personnage) {
		

		
		for(int i=largeurMap-1;i>largeurMap/2;i--) {
			for(int j=largeurMap-1;j>=0;j--) {
				
				for(int k=0;k<listMur.size();k++) {
					if(listMur.get(k).getPositionX()!=j && listMur.get(k).getPositionY()!=i) {
						personnage.setPositionX(j);
						personnage.setPositionY(i);
						return 1;
					}
				}
				
			}
			
		}
			
			
			
			
		return 1;
		
	}
	
	
	/*Methode utilisable en combat retournant : 
	 * 
	 * true si l'ennemi est attaquable en tenant compte de la portee du perso1
	 * 
	 * false si l'ennemi n'est pas attaquable en tenant compte de la portee du perso1
	 * 
	 * false si les deux personnages ne sont pas dans la liste de personnage
	 */
	
	public boolean attaquePossible(Personnage perso1, Personnage perso2) {
		
		if(listPersonnage.contains(perso1) && listPersonnage.contains(perso2)) {
			
			if( (perso2.getPositionX() >= (perso1.getPositionX() - perso1.getPortee())) && (perso2.getPositionX() <= (perso1.getPositionX()+perso1.getPortee())) && (perso2.getPositionY() >= (perso1.getPositionY()-perso1.getPortee())) && (perso2.getPositionY() <= (perso1.getPositionY()+perso1.getPortee())) ) {
				
				return true;
			}
			
			else
				return false;
			
			
			
		}
		
		else 
			return false;
		
	}
	
	/* Fonction regissant une phase d'attaque d'un joueur envers un autre joueur (perso1 attaque perso2)
	 * 
	 * VALEURS DE RETOUR :
	 * 
	 * 1 si l'attaque c'est bien deroulee
	 * 
	 * 2 si les pa pour attaquer etaient insuffisants
	 * 
	 * 3 si l'attaque n'etait pas possible 
	 * 
	 */
	
	public int attaquer(Personnage perso1, Personnage perso2) {
		
		Random rand = new Random();
		
		int santeAvt = perso2.getSante();
		int degatsInfliges;
		
		if(attaquePossible(perso1,perso2)) {
			
			if(perso1.getPa()>=4) {
			
				int nombreAlea = (rand.nextInt(20)+1); 
			
				if(perso1.getPortee()==1){
				
					if(nombreAlea!=2 || nombreAlea!=1)  // Attaque normale
						perso2.modifSante(-((perso1.getDegat()+(perso1.getForce()/4))-((perso2.getArmure()*(10/100))+(perso2.getResistance()*(10/100)))));  
					
					else if(nombreAlea==2)  // Coup critique
						perso2.modifSante(-(perso1.getDegat() + (perso1.getForce()*(1/4)) + ((perso1.getDegat() + (perso1.getForce()*(1/4)))/2)-((perso2.getArmure()*(10/100))+(perso2.getResistance()*(10/100)))));  
					
					else if(nombreAlea==1)  // Echec d'attaque (Demi attaque)
						perso2.modifSante(-(((perso1.getDegat() + (perso1.getForce()*(1/4)))/2) - ((perso2.getArmure()*(10/100))+(perso2.getResistance()*(10/100)))));
				}	
				
				if(perso1.getPortee()>1){
					
					if(nombreAlea!=2 || nombreAlea!=1)  // Attaque normale
						perso2.modifSante(-(perso1.getDegat()+(perso1.getAgilite()*(1/4))-((perso2.getArmure()*(10/100))+(perso2.getResistance()*(10/100)))));  
					
					else if(nombreAlea==2)  // Coup critique
						perso2.modifSante(-(perso1.getDegat() + (perso1.getAgilite()*(1/4)) + ((perso1.getDegat() + (perso1.getAgilite()*(1/4)))/2)-((perso2.getArmure()*(10/100))+(perso2.getResistance()*(10/100)))));  
					
					else if(nombreAlea==1)  // Echec d'attaque (Demi attaque)
						perso2.modifSante(-(((perso1.getDegat() + (perso1.getAgilite()*(1/4)))/2) - ((perso2.getArmure()*(10/100))+(perso2.getResistance()*(10/100)))));								
				}
				
				degatsInfliges = santeAvt - perso2.getSante();
				
				if(joueur==0)
					mssgCbt = "Zombie a perdu " + degatsInfliges + " HP !\n" + "Il ne lui reste que "  + perso2.getSante() + " HP !";
				else
					mssgCbt = "Vous avez perdu " + degatsInfliges + " HP !\n" + "Il ne vous reste que "  + perso2.getSante() + " HP !";
				
				perso1.modifPa(-4);
				return 1;
			}
			
			else
				return 2;
		}
		else 
			return 3;
		
	}
	
	//Methode de fin de tour, permet de passer du tour d'un joueur a l'autre lors du combat
	
	public void finTour() {	
		
		if(joueur==0) {	
			combat.get(1).commencerTour();
			joueur=1;
			tourIa();
			checkCombatTerminee();
			Delai.delai(1000);
		}	
		
		else if(joueur==1) {
			combat.get(0).commencerTour();
			joueur=0;
		}
		
		count=0;
		timer.purge();
		
	}
	
	// Methode permettant de verifier si un personnage du combat est mort
	public boolean checkCombatTerminee() {
		
		int lvlAvt;
		
		if(combat.get(0).getSante()<=0 || combat.get(1).getSante()<=0) {
			
			if(combat.get(1).getSante()<=0) {  // Si l'ennemi du joueur meurt
				
				lvlAvt = combat.get(0).getLvl();
				
				combat.get(0).addExp(combat.get(1).getLvl()*20);   // Gagne de l'xp
				
				if(lvlAvt<combat.get(0).getLvl())
					mssgCbt += "LVL UP !\n" + "Vous avez 4 ptns a depenser !\n"; 
				
				Iterator iteratorCbtTermine = combat.get(1).getInventaire().iterator();
				
				mssgCbt = "Vous avez gagne : \n\n";
				
				while(iteratorCbtTermine.hasNext()) {
					
					Objet obj = (Objet) iteratorCbtTermine.next();
					
					mssgCbt += obj.getNom() + "\n";
					
					combat.get(0).addObjet(obj);  // Le joueur gagne les objets de l'inventaire de lennemi
					iteratorCbtTermine.remove();
					
				}
				
				timer.cancel();
				timer.purge();
				
				combat.get(0).sortCombat();
				combat.get(1).sortCombat();
				
				listPersonnage.remove(combat.get(1));
				
				combat.remove(0);
				combat.remove(0);
				
				//listPersonnage.get(0).setPositionX(posTempX);
				//listPersonnage.get(0).setPositionY(posTempY);
				
				listPersonnage.get(0).setSante(listPersonnage.get(0).getSanteMax());
				listPersonnage.get(0).setPa(listPersonnage.get(0).getPaMax());
				listPersonnage.get(0).setPm(listPersonnage.get(0).getPmMax());
								
				
				return true;
			}
			
			else {
				
				combat.get(0).sortCombat();
				combat.get(1).sortCombat();
				
				combat.remove(0);
				combat.remove(0);
				
			}
				
				
			
		}
		return false;
	}
	
	
	/* Methode permettant de jouer avec un personnage sur une partie
	 * 
	 * PARAMETRES DE LA METHODE :
	 * 
	 * 0 pour un deplacement en haut
	 * 
	 * 1 pour un deplacement en bas
	 * 
	 * 2 pour un deplacement a gauche
	 * 
	 * 3 pour un deplacement a droite
	 * 
	 * 4 pour attaquer pendant son tour de jeux
	 * 
	 * 5 pour finir son tour
	 * 
	 * VALEURS DE RETOUR : 
	 * 
	 * 0 pour un deplacement en haut effectue
	 * 
	 * 1 pour un deplacement en bas effectue
	 * 
	 * 2 pour un deplacement a gauche effectue
	 * 
	 * 3 pour un deplacement a droite effectue
	 * 
	 * 4 si le joueur a attaque pendant son tour
	 * 
	 * 5 si le joueur a fini son tour
	 * 
	 * 6 si le joueur a tente de faire une action de combat alors que ce n'etait pas son tour
	 * 
	 * 7 si le joueur a tente de faire une action de combat alors qu'il etait pas en combat
	 * 
	 * 8 si le joueur a bien attaque
	 * 
	 * 9 si le joueur a voulu attaquer mais n'avait pas assez de pa
	 * 
	 * 10 si le joueur n'avait pas la portee d'attaquer
	 * 
	 * 11 si rien ne s'est passe e l'appel de la methode
	 * 
	 */
	public int choix(int choix) {
		
		int retour = 9;
		
		if(combat.size()!=0) {
			if(joueur==0) {
		
			
				switch(choix) {
				
				    // Au cas oe le personnage veut se deplacer
					case 0 : //haut  
					case 1 : //bas
					case 2 : //gauche
					case 3 : //droite
						
						deplacerPersonnageEnCombat(combat.get(joueur), choix);
						retour = choix;
						break;
						
					case 4 :   // attaque l'autre personnage de la partie
						
						if(joueur==0)  													
							retour = 7 + attaquer(combat.get(joueur), combat.get(1));
							
						checkCombatTerminee();  //Controle si le combat est termine
							
						break;
						
					case 5 : 
									
						finTour();
						retour = choix;
						break;
				}
			}
			
			else {
				
				switch(choix) {
				
					case 0 : 
					case 1 : 
					case 2 : 
					case 3 : 
					case 4 :
					case 5 :
						
						retour = 6;
						break;
					
				}
			}
					
			
		}
		
		else {
			
			switch(choix) {
			
			// Au cas oe le personnage veut se deplacer
			case 0 : //haut
			case 1 : //bas
			case 2 : //gauche
			case 3 : //droite
				
				deplacerPersonnageHorsCombat(listPersonnage.get(0), choix);
				retour = choix;
				break;
			
			case 4 : 	//Si le joueur veut attaquer ou finir son tour alors qu'il ne le peut pas
			case 5 :
				
				retour = 7;
				break;
			
				
			}	
			
		}
		
		return retour;
	}
	
	//Methode regissant le tour de l'Ia qui va basiquement attendre, attaquer un personnage a portee et attendre une seconde phase
	
	public void tourIa(){
		
	/*	TourIA tIA = new TourIA();
		Thread threadIA = new Thread(tIA);
		
		threadIA.start();*/
		
		Delai.delai(3000);
		attaquer(combat.get(1),combat.get(0));
		//tIA.setFlag(false);
		
		
		finTour();
		checkCombatTerminee();
		
	}
	
	
			
	
	
	public void addPerso(Personnage personnage) {
		
		listPersonnage.add(personnage);
	}
	
	public void addMur(Mur mur) {
		
		listMur.add(mur);
	}
	
	
	
	//Tests unitaire
	
	public static void main (String []args) {
		
		Partie partie = new Partie();
		
		partie.sauvegarder();
		
		System.out.println(partie.getId() + "\n");
		
		partie.setLargeurMap(17);
		Personnage personnage1 = new Personnage("mino");
		Personnage personnage2 = new Personnage("flaz");
		partie.addPerso(personnage1);
		partie.addPerso(personnage2);
		
		partie.sauvegarder();
		
		Partie partie2 = new Partie(partie.restaurer());
		
		for(int i=0;i<partie2.getListPersonnage().size();i++){
			System.out.println("" + partie2.getListPersonnage().get(i) + "\n");
		}
		
		
		/*
		Mur mur1 = new Mur(10,12);
		Mur mur2 = new Mur(11,11);
		Mur mur3 = new Mur(9,11);
		partie.addMur(mur1);
		partie.addMur(mur2);
		partie.addMur(mur3);
		
		Personnage personnage1 = new Personnage("mino");
		Personnage personnage2 = new Personnage("flaz");
		personnage1.setPositionX(10);
		personnage1.setPositionY(11);
		personnage2.setPositionX(10);
		personnage2.setPositionY(10);
		
		Objet epee = new Arme("rapiere", 15, 1, 1,new Statistiques());
		Objet hache = new Arme("hache", 15, 1, 1,new Statistiques());

		
		System.out.println(personnage1);
		System.out.println(personnage2);
		
		
		partie.addPerso(personnage1);
		partie.addPerso(personnage2);
		partie.getListPersonnage().get(0).equipStuff(epee);
		partie.getListPersonnage().get(1).equipStuff(hache);
										
		
		partie.choix(1);
		
		System.out.println(partie.getListPersonnage().get(0));
		System.out.println(partie.getListPersonnage().get(1));
		
		partie.choix(2);
		
		System.out.println(partie.getListPersonnage().get(0));
		System.out.println(partie.getListPersonnage().get(1));
		
		partie.choix(3);
		
		System.out.println(partie.getListPersonnage().get(0));
		System.out.println(partie.getListPersonnage().get(1));
		
		
		partie.choix(0);
		
		System.out.println(partie.getListPersonnage().get(0));
		System.out.println(partie.getListPersonnage().get(1));
		*/
		


		
		
		
	}
	
	
	
	
	
	
	
	
	
	
}
