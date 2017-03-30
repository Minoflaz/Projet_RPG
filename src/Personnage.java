import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.Object;



public class Personnage implements java.io.Serializable {
	
	
	private int positionX;
	private int positionY;
	
	private ArrayList<Objet> inventaire;
	private ArrayList<Objet> equipement;

	private long id;   // id unique du personnage permmettant de le demarquer d'un autre
	
	
	private String nom; // Identifiant particulier et unique d'un personnage

	private int sante;
	private int santeMax;
	
	private int degat;
	private int armure;
	private int portee;
	
	private Statistiques stat;  // Statistiques du personnage (Force,Intelligence,Agilite,Resistance)
	private int repartStat;  // Point de rapartition pour augmenter les statistiques du personnage
	
	private int pa;   // points d'action actuels du personnage
	private int paMax;   // points d'action Maximum du personnage
	
	private int pm;  // Points de mouvement actuels du joueur
	private int pmMax; // point de mouvement Maximum du personnage
	
	private boolean enCombat;  // Vrai si le personnage est en combat
	
	private int exp;   // Experience du eprsonnage, se reset quand elle arrive a expMax
	private int expMax;
	private int lvl;   // Niveau du personnage, augmente lorsque son experience atteint expMax
	

	
	// Constructeur par defaut
	
	public Personnage (String nom) { 
		
		this.positionX = 0;
		this.positionY = 0;
		
		this.inventaire = new ArrayList<Objet>();
		this.equipement = new ArrayList<Objet>();

		
		this.id = readId();

		this.nom = nom;
		
		this.sante = 100;
		this.santeMax = 100;
		
		this.degat = 0;
		this.armure = 0;
		this.portee = 0;
		
		this.stat = new Statistiques();
		this.repartStat = 4;
		
		this.pa = 6;
		this.paMax = 6;

		this.pm = 6;
		this.pmMax = 6;
		
		this.enCombat = false;
		
		this.exp = 0;
		this.expMax = 100;
		this.lvl = 1;
	}
	
	// Constructeur avec positions renseignees
	
	public Personnage (String nom,int positionX,int positionY) { 
		
		this.positionX = positionX;
		this.positionY = positionY;
		
		this.inventaire = new ArrayList<Objet>();
		this.equipement = new ArrayList<Objet>();

		
		this.id = readId();

		this.nom = nom;
		
		this.sante = 100;
		this.santeMax = 100;
		
		this.degat = 0;
		this.armure = 0;
		this.portee = 0;
		
		this.stat = new Statistiques();
		this.repartStat = 4;
		
		this.pa = 6;
		this.paMax = 6;

		this.pm = 6;
		this.pmMax = 6;
		
		this.enCombat = false;
		
		this.exp = 0;
		this.expMax = 100;
		this.lvl = 1;
	}
	
	//Constructeur par copie
	
	public Personnage(Personnage personnage) {
		
		this.positionX = personnage.getPositionX();
		this.positionY = personnage.getPositionY();
		
		this.inventaire = new ArrayList<Objet>(personnage.getInventaire());
		this.equipement = new ArrayList<Objet>(personnage.getEquipement());

		
		this.id = readId();

		this.nom = personnage.getNom();
		
		this.sante = personnage.getSante();
		this.santeMax = personnage.getSanteMax();
		
		this.degat = personnage.getDegat();
		this.armure = personnage.getArmure();
		this.portee = personnage.getPortee();
		
		this.stat = new Statistiques(personnage.getStat());
		this.repartStat = personnage.getRepartStat();
		
		this.pa = personnage.getPa();
		this.paMax = personnage.getPaMax();

		this.pm = personnage.getPm();
		this.pmMax = personnage.getPmMax();
		
		this.enCombat = personnage.getEnCombat();
		
		this.exp = personnage.getExp();
		this.expMax = 100;
		this.lvl = 1;
		
	}
	
	
	//Methode permettant de rajouter un "1" dans un fichier texte ou seront sauvegardees les id des personnages
	
	public void saveId(){  
		
		try {
			
			Writer fW = new FileWriter("src/idPersonnage.txt",true);
			fW.write("" + 1);
			fW.close();
		} 
		catch (IOException e) {
	
			e.printStackTrace();
		}
	}
	
	//Methode permettant de lire les char dans le fichier contenant des char "1" en renvoyant leur valeur sous forme de int
	
	public int readId() {
		
		saveId();
		
		int idSent = 0;
		int idRead;
		
		try {
			
			Reader fR = new FileReader("src/idPersonnage.txt");
			while((idRead = fR.read())!= -1) {
				idSent += idRead;
			}
			
		} 
		catch (IOException e) {
	
			e.printStackTrace();
		}
		
		return idSent;
		
	}
	
	//Sauvegarde un personnage dans un fichier portant l'id de celui ci
	
		public void sauvegarder() {
			
			ObjectOutputStream objOutputStream = null;
			String idPersonnage = "" + this.getId();
			String fileName = "save/personnage" + idPersonnage + ".ser";
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
		
		
		/* Methode permettant de restaurer une sauvegarde de personnage a partir de son fichier de sauvegarde
		 * 
		 * Renvoi ce même personnage si le fichier de sauvegarde de celui ci existe
		 * 
		 * Sinon renvoi null
		 */
		
		public Personnage restaurer() {
			
			ObjectInputStream objInputStream = null;
			Personnage persoRestaure = null;
			String idPerso = "" + this.getId();
			String fileName = "save/personnage" + idPerso + ".ser";
			
			try {
				
				FileInputStream fichier = new FileInputStream(fileName);
				objInputStream = new ObjectInputStream(fichier);
				persoRestaure = (Personnage) objInputStream.readObject();
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
			
			return persoRestaure;
		}
	

	public void addObjet(Objet objet) {
		
		this.inventaire.add(objet);
	}

	
	
	/* Methode permettant d'equipper un objet equipable donc arme ou armure
	 * 
	 * Retourne : 
	 * 
	 * 0 si l'objet n'etait pas equippable
	 * 
	 * 1 si l'objet s'est equippe correctement
	 * 
	 * 2 si le personnage n'avait pas le niveau requis pour equipper l'objet
	 * 
	 */

	public int equip(Objet objet) {  
		
		Iterator iteratorE =  equipement.iterator(); 
		
		if(objet instanceof Arme || objet instanceof Armure) {  // Tant que l'objet est equipable
			
			if(objet instanceof Arme) {  // Si c'est une arme
				
				Arme arme = (Arme) objet;
				
				if(arme.getLvl()>=this.lvl) {   //Si le niveau du personnage est plus haut ou egal au niveau requis de l'objet equippable
		
					while(iteratorE.hasNext()) {
						
						 Object a =  iteratorE.next();
						 
						 if (a instanceof Arme) {  // Si l'emplacement de l'equipement que l'on veut equiper est occupe alors il est transfere dans l'inventaire
							 
							 inventaire.add((Arme)a);  // Place l'equipement qui occupait l'emplacement de l'objet que le joueur cherche a equipper dans l'inventaire
							 modifStat(-(((Arme)a).getForce()),-(((Arme)a).getAgilite()),-(((Arme)a).getIntelligence()),-(((Arme)a).getResistance())); // Retire les stats de l'equipement enleve
							 modifDegat(-((Arme)a).getDegat()); // Retire la valeur d'armure de l'equipement enleve
							 modifPortee(-((Arme)a).getPortee());
							 iteratorE.remove(); // Enlève l'equipement
						 }
						
					}
					
					equipement.add(objet);
					jeterObjetInventaire(objet);
						
					this.stat.addStat(arme.getForce(),arme.getAgilite(),arme.getResistance(),arme.getIntelligence());  // Ajoute au personnage les bonus de statistiques de l'Arme
					
					modifDegat(arme.getDegat());
					
					if(arme.getPortee()==1)
						this.portee += arme.getPortee();
					if(arme.getPortee()>1)
						this.portee += arme.getPortee();
					
					return 1;
				}
				
				else
					return 2;
			}
			
			else {    // Si c'est une pièce armure
				
				Armure armure = (Armure) objet;
				
				if(armure.getLvl()>=this.lvl) {   //Si le niveau du personnage est plus haut ou egal au niveau requis de l'objet equippable
		
					while(iteratorE.hasNext()) {
						
						 Object a = iteratorE.next();
						 
						 if (a.getClass().equals(objet.getClass())) {  // Si l'emplacement de l'equipement que l'on veut equiper est occupe alors l'equipement deja equippe est transfere dans l'inventaire
							 
							 inventaire.add((Armure)a);  // Place l'equipement qui occupait l'emplacement de l'objet que le joueur cherche a equipper dans l'inventaire
							 modifStat(-((Armure)a).getForce(),-((Armure)a).getAgilite(),-((Armure)a).getIntelligence(),-((Armure)a).getResistance()); // Retire les stats de l'equipement enleve
							 modifArmure(-((Armure)a).getArmure()); // Retire la valeur d'armure de l'equipement enleve
							 iteratorE.remove();  // Enlève l'equipement
						 }
						
					}
					
					equipement.add(objet);
					inventaire.remove(objet);
						
					this.stat.addStat(armure.getForce(),armure.getAgilite(),armure.getResistance(),armure.getIntelligence());  // Ajoute au personnage les bonus de statistiques de l'Armure
					this.armure += armure.getArmure();
					
					return 1;
				}
				
				else
					return 2;
											
			}
				
		}
		
		else 
			return 3;
		
	}

	public Objet jeterObjetInventaire(Objet objet) {
		
		if(inventaire.contains(objet)) 
			this.inventaire.remove(objet);
			
		return objet;
	}

	public Objet desequip(Objet objet) {
			
		if(equipement.contains(objet))	{
			
			if(objet instanceof Arme) {
				
			
				Arme stuffArme = (Arme) objet;
				
				this.equipement.remove(equipement.indexOf(stuffArme));
				this.inventaire.add(stuffArme);
				this.modifStat(stuffArme.getForce(), stuffArme.getAgilite(), stuffArme.getIntelligence(), stuffArme.getResistance());
				this.degat -= stuffArme.getDegat();
				this.portee -= stuffArme.getPortee();
			
			}
			
			if(objet instanceof Armure) {
				
				
				Armure stuffArmure = (Armure) objet;
				
				this.equipement.remove(equipement.indexOf(stuffArmure));
				this.inventaire.add(stuffArmure);
				this.modifStat(stuffArmure.getForce(), stuffArmure.getAgilite(), stuffArmure.getIntelligence(), stuffArmure.getResistance());
				this.armure -= stuffArmure.getArmure();
			
			}
			
		}	
			return objet;
	}
	
	/* Consomme l'objet en paramètre si c'est un consommable
	 * 
	 * RETOURNE :
	 * 
	 * 0 si l'objet n'est pas un consommable
	 * 
	 * 1 si l'objet est un consommable et qu'il a ete consomme
	 * 
	 * 2 si les pa pour consommer en combat etaient insuffisants
	 * 
	 * 
	 */
	
	public int consommer (Objet objet) {
		
		
		if(objet instanceof Consommable) {
			
			if(inventaire.contains(objet)) {
				
				if(getEnCombat()) {
					
					if(pa>=2) {
				
						Consommable conso = (Consommable) objet;
						
						sante += conso.getSoin();
								
						if(sante>santeMax)
							sante = santeMax;
							
						inventaire.remove(objet);
						
						pa -= 2;
						
						return 1;
					}
					
					else
						return 2;
				}
				
				else {
					
					Consommable conso = (Consommable) objet;
					
					sante += conso.getSoin();
							
					if(sante>santeMax)
						sante = santeMax;
						
					inventaire.remove(objet);					
					
					return 1;
					
				}
					
			}
		}
		
		return 0;
		
	}
	
	

	//Setter et getter
	
	public ArrayList<Objet> getInventaire() {
		
		return this.inventaire;
	}
	
	public ArrayList<Objet> getEquipement() {
		
		return this.equipement;
	}
	
	public int getPositionX(){
		
		return this.positionX;
	}
	
	public int getPositionY(){
		
		return this.positionY;
	}
	
	public void setPositionX(int posX) {
		
		this.positionX = posX;
	}
	
	public void setPositionY(int posY) {
		
		this.positionY = posY;
	}
	
	public void modifPositionY(int modif) {
		
		this.positionY += modif;
	}
	
	public long getId() {
		
		return this.id;
	}
	
	public void setId(int id) {
		
		this.id = id;
	}
	
	public String getNom() {
		
		return this.nom;
	}
	
	public void setNom(String nom) {
		
		this.nom = nom;
	}
	
	public int getSante() {
		
		return this.sante;
	}
	
	public void setSante(int sante) {
		
		this.sante = sante;
	}

	public void modifSante(int sante) {  //Ajoute l'entier si la valeur de celui ci est positive et en enleve l'entier si il est negatif
		
		this.sante += sante;
	}
	
	public void setSanteMax(int santeMax) {
		
		this.santeMax = santeMax;
	}
	
	public void modifSanteMax(int modif) {
		
		this.santeMax += modif;
	}
	
	public int getSanteMax() {
		
		return this.santeMax;
	}
	
	public int getDegat() {
		
		return this.degat;
	}

	public void setDegat(int degat) {
		
		this.degat = degat;
	}
	
	public void modifDegat(int degat) {
		
		this.degat += degat;
	}
	
	public void setArmure(int armure) {
		
		this.armure = armure;
	}
	
	public int getArmure() {
		
		return this.armure;
	}
	
	public void modifArmure(int armure) {
		
		this.armure += armure;
	}
	
	public void setPortee(int portee) {
		
		this.portee = portee;
	}
	
	public void modifPortee(int modif) {
		
		this.portee += modif;
	}
	
	public int getPortee() {
		
		return this.portee;
	}
	
	public Statistiques getStat() {
		
		return this.stat;
	}
	
	public int getForce() {
		
		return this.stat.getForce();
	}
	
	public int getAgilite() {
		
		return this.stat.getAgilite();
	}

	public int getIntelligence() {
	
	return this.stat.getIntelligence();
	}

	public int getResistance() {
	
	return this.stat.getResistance();
	}
	
	
	public void modifForce(int ajout) {
		
		this.stat.modifForce(ajout);
	}
	
	public void modifAgilite(int ajout) {
		
		this.stat.modifAgilite(ajout);
	}
	
	public void modifResistance(int ajout) {
		
		this.stat.modifResistance(ajout);
	}
	
	public void modifIntelligence(int ajout) {
		
		this.stat.modifIntelligence(ajout);
	}
	
	public void modifStat(int force, int agilite, int intelligence, int resistance) {
		
		modifForce(force);
		modifAgilite(agilite);
		modifIntelligence(intelligence);
		modifResistance(resistance);
	}
	
	public int getRepartStat () {
		
		return this.repartStat;
	}
	
	public void setRepartStat (int repartStat) {
		
		this.repartStat = repartStat;
	}
	
	
	public int getPa() {
		
		return this.pa;
	}

	public void setPa(int pa) {
		
		this.pa = pa;
	}

	public void modifPa(int pa) {  //Ajoute l'entier si la valeur de celui ci est positive et en enleve l'entier si il est negatif
		
		this.pa += pa;
	}
	
	public void setPaMax(int paMax) {
		
		this.paMax = paMax;
	}
	
	public void modifPaMax(int modif) {
		
		this.paMax += modif;
	}
	
	public int getPaMax() {
		
		return this.paMax;
	}
	
	public int getPm() {
		
		return this.pm;
	}

	public void setPm(int pm) {
		
		this.pm = pm;
	}
	
	public void downPm() {
		
		this.pm--;
	}
	
	public void addPm() {
		
		this.pm++;
	}
	
	public void setPmMax(int pmMax) {
		
		this.pmMax = pmMax;
	}
	
	public void modifPmMax(int modif) {
		
		this.pmMax += modif;
	}
	
	public int getPmMax() {
		
		return this.pmMax;
	}
	
	public void entreCombat() {
		
		this.enCombat = true;
	}
	
	public void sortCombat() {
		
		this.enCombat = false;
	}
	
	public boolean getEnCombat() {
		
		return this.enCombat;
	}
	
	public void setExp(int exp) {
		
		this.exp = exp;
	}
	
	public int getExp() {
		
		return this.exp;
	}
	
	public void setExpMax(int expMax) {
		
		this.expMax = expMax;
	}
	
	public int getExpMax() {
		
		return this.expMax;
	}
	
	public void addExp(int exp) {
		
		this.exp += exp; 
		
		if(exp >= expMax) {
			this.exp = (this.exp-expMax);
			lvlUp();

		}	
		
	}
	
	public void setLvl(int lvl) {
		
		this.lvl = lvl;
	}
	
	public void lvlUp() {
		
		this.lvl ++;
		this.expMax+=100;
		this.repartStat +=4;
		this.santeMax +=10;
		this.sante = this.santeMax;
	}
	
	/* Methode peremettant d'ajouter une unite a la statistique choisie tant que le joueur a des points de repartition
	 * 
	 * VALEURS DE PARAMETRE :
	 * 
	 * 0 pour augmenter la force
	 * 
	 * 1 pour augmenter l'agilite
	 * 
	 * 2 pour augmenter l'intelligence
	 * 
	 * 3 pour augmenter la resistance
	 * 
	 */
	
	public void repartirStat(int choix) {
		
		if(repartStat>0) {
		
			switch(choix) {
			
				case 0 : 
					
					this.modifForce(1);
					break;
				
				case 1 : 
					
					this.modifAgilite(1);
					break;
					
				case 2 : 
					
					this.modifIntelligence(1);
					break;
					
				case 3 : 
					
					this.modifResistance(1);
					break;
					
			}
			
			this.repartStat--;
		}	
	}


	public int getLvl() {
		
		return this.lvl;
	}
	
	
	
	
	
	//Deplacements
	

	
	
	public void deplacerHaut() {
		
	
			positionY --;
	}
	
	public void deplacerBas() {
		
		
			positionY ++;
	}
	
	public void deplacerGauche() {
		
		
			positionX--;					
	}
	
	public void deplacerDroite(){
		
		
			positionX++;
			
	}
	
	
	
	
	/*
	 *  Methode permettant de se deplacer sur la map 
	 * 
	 * Valeurs du parametre choix : 
	 *  
	 * 0 pour se deplacer en haut
	 * 
	 * 1 pour se deplacer en bas
	 * 
	 * 2 pour se deplacer a gauche
	 * 
	 * 3 pour se deplacer a droite
	 * 
	 */
	
	
	public void deplacer(int choix) {
		
		switch(choix) {
			
			case 0:
				
				deplacerHaut();				
				break;
				
			case 1:
				
				deplacerBas();				
				break;
				
			case 2:
				
				deplacerGauche();
				
				break;
				
			case 3:
				
				deplacerDroite();
				
				break;		
		
		}
	}
	
	public void commencerTour(){
		
		this.pa = this.paMax;
		this.pm = this.pmMax;
	}
	
	//Verifie si l'equipement du personnage contient un Casque. Renvoie celui ci ou null si l'equipement n'en contient pas
	
	public Casque getCasque() {
		
		if(equipement.size()>0) {
		
			Iterator iteratorVerifCasque = equipement.iterator();
			
			while(iteratorVerifCasque.hasNext()) {
				
				Object o = iteratorVerifCasque.next();
				
				if(o instanceof Casque)
					return (Casque)o;
				
			}
		}	
		
		return null;
		
	}
	
	//Verifie si l'equipement du personnage contient un Torse. Renvoie celui ci ou null si l'equipement n'en contient pas
	
	public Torse getTorse() {
		
		if(equipement.size()>0) {
		
			Iterator iteratorVerifTorse = equipement.iterator();
			
			while(iteratorVerifTorse.hasNext()) {
				
				Object o = iteratorVerifTorse.next();
				
				if(o instanceof Torse)
					return (Torse)o;
				
			}
		}	
		
		return null;
		
	}
	
	//Verifie si l'equipement du personnage contient un Pantalon. Renvoie celui ci ou null si l'equipement n'en contient pas
	
	public Pantalon getPantalon() {
		
		if(equipement.size()>0) {
		
			Iterator iteratorVerifPantalon = equipement.iterator();
			
			while(iteratorVerifPantalon.hasNext()) {
				
				Object o = iteratorVerifPantalon.next();
				
				if(o instanceof Pantalon)
					return (Pantalon)o;
				
			}
		}	
		
		return null;
		
	}
	
	//Verifie si l'equipement du personnage contient des Gants. Renvoie ceux ci ou null si l'equipement n'en contient pas

	public Gants getGants() {
		
		if(equipement.size()>0) {
		
			Iterator iteratorVerifGants = equipement.iterator();
			
			while(iteratorVerifGants.hasNext()) {
				
				Object o = iteratorVerifGants.next();
				
				if(o instanceof Gants)
					return (Gants)o;
				
			}
		}	
		
		return null;
		
	}
	
	//Verifie si l'equipement du personnage contient des Bottes. Renvoie celles ci ou null si l'equipement n'en contient pas
	
	public Bottes getBottes() {
		
		if(equipement.size()>0) {
		
			Iterator iteratorVerifBottes = equipement.iterator();
			
			while(iteratorVerifBottes.hasNext()) {
				
				Object o = iteratorVerifBottes.next();
				
				if(o instanceof Bottes)
					return (Bottes)o;
				
			}
		}	
		
		return null;
		
	}
	
	//Verifie si l'equipement du personnage contient une arme. Renvoie celle ci ou null si l'equipement n'en contient pas
	
	public Arme getArme() {
		
		if(equipement.size()>0) {
			
			Iterator iteratorVerifArme = equipement.iterator();
		
			while(iteratorVerifArme.hasNext()) {
				
				Object o = iteratorVerifArme.next();
				
				if(o instanceof Arme)
					return (Arme)o;
				
			}
		}
		
		return null;
		
	}

	
	public String toString() {
		
		String str = "";
		
		str += nom + " : \n" + "\nPosition X : " + positionX + "\n" + "Position Y : " + positionY + "\nID : " + id + "\n" + sante + "/" + santeMax + " HP\n" + "Degat : " + degat + "\nForce : " + stat.getForce() + "\nAgilite : " + stat.getAgilite() + "\nIntelligence : " + stat.getIntelligence() + "\nResistance : " + stat.getResistance() + "\n" + pa + "/" + paMax + " PA\n" + pm + "/" + pmMax + " PM\n" + exp + "/" + expMax + " XP"  + "\nLvl : " + lvl + "\n" + "enCombat : " + enCombat + "\n";
		
		return str;
	}
	
	public String afficherInfo() {  // Sorte de toString mais plus concis
		
		String str = "";
		
		str +=  sante + "/" + santeMax + " HP\n" + "Degat : " + degat + "\nArmure : " + armure + "\n" + pa + "/" + paMax + " PA\n" + pm + "/" + pmMax + " PM\n" + exp + "/" + expMax + " XP"  + "\nLvl : " + lvl + "\nStat a repartir : " + repartStat;
		
		return str;
	}
	
	public boolean equals(Object perso) {
		
		if(perso instanceof Personnage) {
			
			Personnage perso1 = (Personnage) perso;
			
			if(this.id == perso1.getId())
				return true;
			else
				return false;
			
		}
		
		else 
			return false;
		
	}

	
	public static  void main (String [] args) {
		
		Personnage perso1 = new Personnage("Mino");
		perso1.setSante(80);

		
		System.out.println(perso1.getCasque());
		
		
		
		perso1.equip(new Casque("Frontalite",8,1,new Statistiques()));
		
		System.out.println(perso1.getCasque());
		
	
		
		
	

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
