import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;
import java.io.File;
import java.net.URL;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;




/* RESTE A FAIRE : 
 * 
 * SAUVAGARDE TOU CA
 * 
 * SORTIE DE COMBAT QUAND TU MEURS	
 * 
 */






public class AffichageMap extends JFrame {

	Toolkit tk = Toolkit.getDefaultToolkit();
	
	private JButton[][] cases;  //Represente la map sous forme de tableau
	
	private JTextArea box;
	private JTextArea infoPerso;
	private JTextArea force;
	private JTextArea agilite;
	private JTextArea intelligence;
	private JTextArea resistance;
	private JTextArea chatIn;
	private JTextArea chatOut;
	
	private Partie partie;
	
	private Robot robot;
	
	private Stock stock;
	
	private char touche;   // touche pressee
	
	private String text = ""; // Texte ecrit lors des communications a travers le chat
	
	private int iterateuri = 1;  // iterateur i utilise pour parcourir le tableau de bouton
	private int iterateurj = 0;  // iterateur j utilise pour parcourir le tableau de bouton
	private int iterateuriTemp = 1;  // Represente l'iterateur i avant sa modification
	private int iterateurjTemp = 0;  // Represente l'iterateur j avant sa modification	
	private int largeurMap = 15;  //Largeur de la map (peet etre modifie mais pas en dessous de 5 pour l'instant)
	
	private JLayeredPane test;
	private JPanel centre;
	private JPanel map;
	private JPanel testMenu;
	private JPanel inventaire;
	private JPanel chat;
	private ArrayList<JPanel> listChoixStuff;
	private ArrayList<Personnage> persoCombat;
	
	
	ImageIcon icone = new ImageIcon( "textures/skin.png" );//image du personnage
	
	ImageIcon ground1= new ImageIcon("textures/ground1.png");
	ImageIcon ground1_icone= new ImageIcon("textures/ground1_icone.png");
	ImageIcon ground1_icone_zombie = new ImageIcon("textures/ground1_icone_zombie.gif");
	ImageIcon ground1_icone_zombie_head = new ImageIcon("textures/ground1_icone_zombie_head.gif");
	ImageIcon ground1_head= new ImageIcon("textures/ground1_head.png");
	
	ImageIcon ground1_wall_max= new ImageIcon("textures/ground1_wall_max.png");
	ImageIcon ground1_icone_zombie_wall_max= new ImageIcon("textures/ground1_zombie_icone_wall_max.gif");
	ImageIcon ground1_icone_wall_max= new ImageIcon("textures/ground1_icone_wall_max.png");
	
	ImageIcon ground2= new ImageIcon("textures/ground2.png");
	ImageIcon ground2_icone= new ImageIcon("textures/ground2_icone.png");
	ImageIcon ground2_icone_zombie = new ImageIcon("textures/ground2_icone_zombie.gif");
	ImageIcon ground2_icone_zombie_head = new ImageIcon("textures/ground2_icone_zombie_head.gif");
	ImageIcon ground2_head= new ImageIcon("textures/ground2_head.png");
	
	ImageIcon ground2_wall_max= new ImageIcon("textures/ground2_wall_max.png");
	ImageIcon ground2_icone_zombie_wall_max= new ImageIcon("textures/ground2_icone_zombie_wall_max.gif");
	ImageIcon ground2_icone_wall_max= new ImageIcon("textures/ground2_icone_wall_max.png");

	
	ImageIcon wall_beg_head= new ImageIcon("textures/wall_beg_head.png");
	ImageIcon wall_beg_head_zombie= new ImageIcon("textures/wall_beg_head_zombie.gif");
	ImageIcon wall_sui= new ImageIcon("textures/wall_sui.png");
	ImageIcon wall_beg= new ImageIcon("textures/wall_beg.png");
	
	ImageIcon croix_fermer = new ImageIcon("textures/croix_fermer.png");
	
	Dimension dimension= new Dimension(900,900);//dimensions de la map
	Dimension dimensionCentre= new Dimension(1000,1000);//dimensions du conteneur de la map
	
	
	
public AffichageMap (String pseudo,int choix){
		
		init(pseudo,choix);
		
	}
	
	public void init(String pseudo,int choix) {
		
		this.setTitle("Projet_RPG");
		//this.setUndecorated(true);
		//this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setCursor(new Cursor(12));// curseur hand
		this.center(1256,961);//dimensions de la fenetre		
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.stock = new Stock();
		JPanel centre= new Centre(pseudo,choix);
		this.test = new JLayeredPane();
		this.barreMenu();
		test.setVisible(true);
		inventaire = new JPanel(new GridLayout(6,4,0,0));
		chat = initChat();
		test.add(chat,new Integer(99));
		test.add(inventaire,new Integer(100));
		test.add(map,new Integer(101));
		actualiserInventaire();  // Permet de remplir l'inventaire avec les objets que le joueur possede
		test.setLayer(inventaire,new Integer(100));  // Permet de remettre l'inventaire derriere la map quand la partie commence
		inventaire.setBounds(200,200,500,500);   // centre l'inventaire au milieu de la partie
		map.setBounds(0,0,900,900);
		chat.setBounds(200, 200, 400, 400);
		this.add(test,BorderLayout.CENTER);
		this.add(east(),BorderLayout.EAST);
		actualisation();	
		persoCombat = new ArrayList<Personnage>();
		this.setVisible(true);
		
	}
	
	public void initRestaurer(Personnage personnage) {
		
		
		
	}
	
	
	public class Centre extends JPanel{//conteneur de la map
		public Centre(String pseudo,int choix){
			this.setMaximumSize(dimension);//on fixe la dimension de la map
			this.setMinimumSize(dimension);//de meme
			this.setPreferredSize(dimension);//de meme
			this.setBackground(Color.BLACK);
			this.add(map(pseudo,choix));
		}
		String nom="textures/zombie.gif";
		Image image = Toolkit.getDefaultToolkit().getImage(nom);
		/*public void paintComponent(Graphics g) {
			g.setColor(Color.GREEN);
			g.drawLine(0, 0, largeurMap,largeurMap);
		}*/
		
	}
	

	public void initPartie() {
		
		this.partie = new Partie();
		
		
	}
	
	/* Parametres : 
	 * 
	 * Pseudo du joueur
	 * 
	 * Choix de la classe :
	 * 
	 * 0 pour un Fleau
	 * 
	 * 1 pour un Distance
	 * 
	 * 2 pour un Tank
	 * 
	 * 3 pour un ingenieur
	 * 
	 */

	public JPanel map (String pseudo, int choix){// map de jeu
		
	initPartie();
		
	Random rand = new Random();
		
		cases = new JButton[largeurMap][largeurMap];   // Cree la map de largeur et longueur egales
		map = new JPanel(new GridLayout(largeurMap,largeurMap,0,0));  //Cree un layout de bouton avec comme largeur et longueur "largeurMap"
		map.setMaximumSize(dimension);//on fixe la dimension de la map
		map.setMinimumSize(dimension);//de meme
		map.setPreferredSize(dimension);//de meme
		map.setBackground(Color.BLACK);
		
		for(int i=0;i<largeurMap;i++){  //creation des bouttons et leur ajout au tableau 
			
			for(int j=0;j<largeurMap;j++){
				this.cases[i][j] = new JButton();
			}
		}

		for(int i=0;i<largeurMap;i++){  //initialisation des bouttons et leur ajout au tableau 
			
			for(int j=0;j<largeurMap;j++){
				
				if(i==1 && j==0){
					this.cases[i][j] = new JButton(ground2_icone);//mettre perso
					
					switch(choix) {  // Utilisation du choix et du pseudo pour creer le joueur
						
					case 1 :
						this.partie.addPerso(new Fleau(pseudo,j,i));
						break;
						
					case 2 :
						this.partie.addPerso(new Distance(pseudo,j,i));
						break;
						
					case 3 : 
						this.partie.addPerso(new Tank(pseudo,j,i));
						break;
						
					case 4 : 
						this.partie.addPerso(new Ingenieur(pseudo,j,i));
						break;
					}
					
					
					this.partie.getListPersonnage().get(0).setSante(80);
					this.partie.getListPersonnage().get(0).addObjet(stock.getConsommable());
					this.partie.getListPersonnage().get(0).addObjet(stock.getConsommable());
					this.partie.getListPersonnage().get(0).addObjet(stock.getConsommable());
					this.partie.getListPersonnage().get(0).addObjet(stock.getArme(partie.getListPersonnage().get(0).getLvl()));
					this.partie.getListPersonnage().get(0).equip(new Arme("Scie sauteuse",80,1,1,new Statistiques()));
					this.partie.getListPersonnage().get(0).addObjet(stock.getCasque(partie.getListPersonnage().get(0).getLvl()));
					this.partie.getListPersonnage().get(0).addObjet(stock.getBottes(partie.getListPersonnage().get(0).getLvl()));
					this.partie.getListPersonnage().get(0).addObjet(stock.getBottes(partie.getListPersonnage().get(0).getLvl()));
					
					this.cases[i][j].setBackground(Color.LIGHT_GRAY);//background des cases jouables
					this.cases[i][j].addKeyListener(new Deplacement());//ajout de la methode de deplacement
					this.cases[i][j].setBorderPainted(false);//effaeage des bordures des cases
					map.add(cases[i][j]);// ajout des cases de la map
				}

			
				else{
			
				
					int nombreAleatoire = rand.nextInt(20)+1;// 1 chance sur 20
				
					this.cases[i][j] = new JButton();
					
					int v=0;
					
					ArrayList<Personnage> listZombie = new ArrayList<Personnage>();
				
					if(nombreAleatoire == 20 ){ // une chance sur 20 de faire un mur et pas de mur
						this.cases[i][j].setBackground(Color.GRAY);//couleur d'un mur
						this.cases[i][j].addKeyListener(new Deplacement());
						this.cases[i][j].addKeyListener(new ActionBoutton());
						//partie.addMur(new Mur(j,i));
					}
					
					else {
						if(nombreAleatoire==10 && i<largeurMap-1 && i>(largeurMap/5) && j>(largeurMap/5) && this.cases[i][j].getBackground()!=Color.GRAY && this.cases[i-1][j].getBackground()!=Color.GRAY) {  // generation de zombie aleatoire
							this.cases[i][j].setBackground(Color.LIGHT_GRAY);//sinon on fait une case jouable
							if((i%2==1 && j%2==0) || i%2==0 && j%2==1) {//puis foncee sur 1/2 case								
								
								
								
								
								if(i>0){
									if(!(this.cases[i-1][j].getBackground()==Color.GRAY)){
										this.cases[i-1][j].setIcon(ground1_icone_zombie_head);
										this.cases[i][j].setIcon(ground2_icone_zombie);
									}	
								}	
							}
							
							else {

								
								
								if(i>0){
									if(!(this.cases[i-1][j].getBackground()==Color.GRAY)){
										this.cases[i-1][j].setIcon(ground2_icone_zombie_head);
										this.cases[i][j].setIcon(ground1_icone_zombie);
									}
								}	
							}
							
							listZombie.add(new Monstre("Zombie",j,i));
							listZombie.get(v).equip(new Arme("couteau",9,1,1,new Statistiques(1,1,1,1)));
							
							Random random = new Random();
							int randomNbr = random.nextInt(4);
							
							if(randomNbr==0)
								listZombie.get(v).addObjet(stock.getArme(listZombie.get(v).getLvl()));
							if(randomNbr==1)
								listZombie.get(v).addObjet(stock.getArmure(listZombie.get(v).getLvl()));
							if(randomNbr==2 || randomNbr==3)
								listZombie.get(v).addObjet(stock.getConsommable());
							
							
							this.partie.addPerso(listZombie.get(v));
							v++;
							
							this.cases[i][j].setBackground(Color.LIGHT_GRAY);//background des cases jouables
							this.cases[i][j].addKeyListener(new Deplacement());//ajout de la methode de deplacement
							this.cases[i][j].addKeyListener(new ActionBoutton());
							
						
						}
						
					else {
							this.cases[i][j].setBackground(Color.LIGHT_GRAY);//sinon on fait une case jouable
							this.cases[i][j].setIcon(ground1);//on applique la texture claire
							if((i%2==1 && j%2==0) || i%2==0 && j%2==1){//puis foncee sur 1/2 case
								this.cases[i][j].setIcon(ground2);
							}
							this.cases[i][j].addKeyListener(new Deplacement());
							this.cases[i][j].addKeyListener(new ActionBoutton());
					}	
					}
					this.cases[i][j].setBorderPainted(false);
					map.add(cases[i][j]);
		   		}
			}
		}
		int iSuiv;//ligne de la case cible
		int jSuiv;//colonne de la case cible
		int nbCol; // nb de colonnes qui separe la case courante de la case cible
		int nbLi;// nb de lignes qui separe la case courante de la case cible
		int nombreAleatoire;
		/*
		 * ici on va faire un generateur de murs
		 * la boucle va prendre toutes les cases 
		 * definies aleatoirement comme des murs
		 * va verifier si dans un perimetre de 4 cases
		 * il y a un ou plusieurs autre(s) mur(s)
		 * si tel est le cas, on les fait se rejoindre
		 * entre transformant les cases qui les separent
		 * en mur et avec un chemin aleatoire
		 */
		for(int i=0;i<largeurMap;i++){//pour chaque ligne
			
			for(int j=0;j<largeurMap;j++){//et pour chaque colonne
				
				for(int x=-4;x<5;x++){//dans un perimetre de 4 colonnes
					
					for(int d=-4;d<5;d++){// et de 4 lignes
						
						if(this.cases[i][j].getBackground()==Color.GRAY && i+x<15 && i+x>=0 && j+d<15 && j+d>=0){
							//si la case courante est un mur, et que la case cible est dans la map
							
							if( this.cases[i+x][j+d].getBackground()==Color.GRAY && this.cases[i+x][j+d]!=this.cases[i][j]){
								
								//si la case cible est aussi un mur et qu'elle n'est pas la case courante
								nombreAleatoire = rand.nextInt(2)+1;// on relie les deux cases avec des murs et avec un chemin aleatoire
								iSuiv=i+x;
								jSuiv=j+d;
								nbLi=iSuiv-i;
								nbCol=jSuiv-j;
								if(nombreAleatoire==1){//si 1, alors le chemin demarre par les lignes
									if(iSuiv>i || jSuiv>j){
										for(int ip=0;ip<=nbLi;ip++){//if isuiv >i et jsuiv>j
											//partie.addMur(new Mur(j,i+ip));
											this.cases[i+ip][j].setBackground(Color.BLACK);	
											this.cases[i+ip][j].setBorderPainted(false);
											this.cases[i+ip][j].setEnabled(false);
											this.cases[i+ip][j].setIcon(null);
											
										}
										for(int jp=0;jp<=nbCol;jp++){
											//partie.addMur(new Mur(j+jp,iSuiv));
											this.cases[iSuiv][j+jp].setBackground(Color.BLACK);
											this.cases[iSuiv][j+jp].setBorderPainted(false);
											this.cases[iSuiv][j+jp].setEnabled(false);
											this.cases[iSuiv][j+jp].setIcon(null);
										}
									}
								
								}
								else if(nombreAleatoire==2){//sinon par les colonnes
									if(iSuiv>i || jSuiv>j){
										for(int jp=0;jp<=nbCol;jp++){
											//partie.addMur(new Mur(j+jp,i));
											this.cases[i][j+jp].setBackground(Color.BLACK);
											this.cases[i][j+jp].setBorderPainted(false);
											this.cases[i][j+jp].setEnabled(false);
											this.cases[i][j+jp].setIcon(null);
											
										}
										for(int ip=0;ip<=nbLi;ip++){//if isuiv >i et jsuiv>j
											//partie.addMur(new Mur(jSuiv,i+ip));
											this.cases[i+ip][jSuiv].setBackground(Color.BLACK);
											this.cases[i+ip][jSuiv].setBorderPainted(false);
											this.cases[i+ip][jSuiv].setEnabled(false);
											this.cases[i+ip][jSuiv].setIcon(null);
										}
									}
								}
							}
								

								
							}
						}
					}
				}
			}
		for(int i=0;i<largeurMap;i++){//application des textures de sol
			
			for(int j=0;j<largeurMap;j++){
				if( this.cases[i][j].getBackground()==Color.GRAY){//si la case courante est un sol
					this.cases[i][j].setIcon(ground1);//appliquer premiere texture de sol
					if((i%2==1 && j%2==0) || i%2==0 && j%2==1){
						this.cases[i][j].setIcon(ground2);//appliquer la 2e texture 1 case sur 2
					}
					this.cases[i][j].setText("");
				}
				}
			}
				
				for(int i=0;i<1;i++){//creation du mur superieur, pour la ligne i, appliquer un mur e chaque case et appliquer texture debut de mur
					
					for(int j=0;j<largeurMap;j++){
						//partie.addMur(new Mur(j,i));
						this.cases[i][j].setBackground(Color.BLACK);
						this.cases[i][j].setBorderPainted(false);
						this.cases[i][j].setEnabled(false);
						this.cases[i][j].setDisabledIcon(wall_beg);
					}
				}
				for(int i=0;i<largeurMap;i++){//application des textures de mur
					
					for(int j=0;j<largeurMap;j++){
						if( this.cases[i][j].getBackground()==Color.BLACK && i+1<=largeurMap-1 && this.cases[i+1][j].getBackground()!=Color.BLACK){//si la case du bas n'est pas un mur alors mettre debut de mur							
							this.cases[i][j].setIcon(wall_beg);
							this.cases[i][j].setDisabledIcon(wall_beg);
						}
						if( this.cases[i][j].getBackground()==Color.BLACK && i+1<=largeurMap-1 && this.cases[i+1][j].getBackground()!=Color.BLACK && (this.cases[i+1][j].getIcon()==ground1_icone_zombie || this.cases[i+1][j].getIcon()==ground2_icone_zombie)){//si la case du bas n'est pas un mur alors mettre debut de mur							
							this.cases[i][j].setIcon(wall_beg_head_zombie);
							this.cases[i][j].setDisabledIcon(wall_beg_head_zombie);
						}
						if( this.cases[i][j].getBackground()==Color.BLACK && i+1<=largeurMap-1 && this.cases[i+1][j].getBackground()==Color.BLACK){//si la case du bas est un mur alors mettre mur continu
							this.cases[i][j].setIcon(wall_sui);
							this.cases[i][j].setDisabledIcon(wall_sui);
						}
						if( this.cases[i][j].getBackground()==Color.BLACK && (i-1)>=0 && this.cases[i-1][j].getIcon()==ground1){//si la case du haut est un sol ground1 mettre fin de mur ground1																				
							this.cases[i-1][j].setIcon(ground1_wall_max);
							this.cases[i-1][j].setDisabledIcon(ground1_wall_max);
						}
						/*if( this.cases[i][j].getBackground()==Color.BLACK && (i-1)>=0 && this.cases[i-1][j].getIcon()==ground1_icone_zombie){//si la case du haut est un sol ground1 mettre fin de mur ground1																				
							this.cases[i-1][j].setIcon(ground1_icone_zombie_wall_max);
							this.cases[i-1][j].setDisabledIcon(ground1_icone_zombie_wall_max);
						}*/
						if( this.cases[i][j].getBackground()==Color.BLACK && (i-1)>=0 && this.cases[i-1][j].getIcon()==ground2){//si la case du haut est un sol ground2 mettre fin de mur ground2
							this.cases[i-1][j].setIcon(ground2_wall_max);
							this.cases[i-1][j].setDisabledIcon(ground2_wall_max);
						}
						/*if( this.cases[i][j].getBackground()==Color.BLACK && (i-1)>=0 && this.cases[i-1][j].getIcon()==ground2_icone_zombie){//si la case du haut est un sol ground2 mettre fin de mur ground2
							this.cases[i-1][j].setIcon(ground2_icone_zombie_wall_max);
							this.cases[i-1][j].setDisabledIcon(ground2_icone_zombie_wall_max);
						}*/
						if( this.cases[i][j].getBackground()==Color.BLACK && i==largeurMap-1){//si la case du bas n'est pas un mur alors mettre debut de mur
							this.cases[i][j].setIcon(wall_beg);
							this.cases[i][j].setDisabledIcon(wall_beg);
						}
						/*if( j<largeurMap-1 && j>0  && (this.cases[i][j+1].getIcon()==wall_sui || this.cases[i][j-1].getIcon()==wall_sui) && i>0 && this.cases[i-1][j].getIcon()==ground2_icone_zombie_head){//si la case du haut est un sol ground2 mettre fin de mur ground2
							this.cases[i-1][j].setIcon(ground2_icone_zombie_wall_max);
							this.cases[i-1][j].setDisabledIcon(ground2_icone_zombie_wall_max);
						}
						if( j<largeurMap-1 && j>0  && (this.cases[i][j+1].getIcon()==wall_sui || this.cases[i][j-1].getIcon()==wall_sui) && i>0 && this.cases[i-1][j].getIcon()==ground1_icone_zombie_head){//si la case du haut est un sol ground2 mettre fin de mur ground2
							this.cases[i-1][j].setIcon(ground1_icone_zombie_wall_max);
							this.cases[i-1][j].setDisabledIcon(ground1_icone_zombie_wall_max);
						}*/
						
					}
				}
				this.cases[0][0].setIcon(wall_beg_head);//application de la tete
				this.cases[0][0].setDisabledIcon(wall_beg_head);//de meme
				return map;
				}	

	
	
	public JPanel east () { // Test d'un affichage du stuff
		
		
		JPanel eastTab = new JPanel(new GridLayout(3,1)); // Panel Est
		JPanel eastSubTab = new JPanel(new BorderLayout());
		JPanel eastSubBut = new JPanel(new BorderLayout());
		JPanel stat = new JPanel(new GridLayout(4,2));  // Panel d'augmentation des stats
		this.box = new JTextArea(25,30);  // Boite de dialogue
		this.infoPerso = new JTextArea();  // Infos personnage
		box.setBorder(BorderFactory.createTitledBorder("Dialogue"));
		box.setVisible(true);
		JPanel east = new JPanel(new GridLayout(5,4));  // Affichage de l'equipement
		JPanel eastSub = new JPanel(new GridLayout(2,2));  //Panel d'infos avec augmentation des stat et actions tel que Attaquer ou Finir tour
		east.setBorder(BorderFactory.createTitledBorder("Equipement"));
		eastSub.setBorder(BorderFactory.createTitledBorder("Infos"));		
		
			JButton blackButton1 = new JButton(); blackButton1.setBackground(Color.BLACK); blackButton1.addActionListener(new Equipement(0));
			JButton blackButton2 = new JButton(); blackButton2.setBackground(Color.BLACK); blackButton2.addActionListener(new Equipement(2));
			JButton blackButton3 = new JButton(); blackButton3.setBackground(Color.BLACK); blackButton3.addActionListener(new Equipement(1));
			JButton blackButton4 = new JButton(); blackButton4.setBackground(Color.BLACK); blackButton4.addActionListener(new Equipement(2));
			JButton blackButton5 = new JButton(); blackButton5.setBackground(Color.BLACK); blackButton5.addActionListener(new Equipement(1));
			JButton blackButton6 = new JButton(); blackButton6.setBackground(Color.BLACK); blackButton6.addActionListener(new Equipement(3));
			JButton blackButton7 = new JButton(); blackButton7.setBackground(Color.BLACK); blackButton7.addActionListener(new Equipement(3));
			JButton blackButton8 = new JButton(); blackButton8.setBackground(Color.BLACK); blackButton8.addActionListener(new Equipement(3));
			JButton blackButton9 = new JButton(); blackButton9.setBackground(Color.BLACK); blackButton9.addActionListener(new Equipement(4));
			JButton blackButton10 = new JButton(); blackButton10.setBackground(Color.BLACK); blackButton10.addActionListener(new Equipement(4));
			
			JButton greyButton1 = new JButton(); greyButton1.setBackground(Color.GRAY);
			JButton greyButton2 = new JButton(); greyButton2.setBackground(Color.GRAY); 
			JButton greyButton3 = new JButton(); greyButton3.setBackground(Color.GRAY);
			JButton greyButton4 = new JButton(); greyButton4.setBackground(Color.GRAY);
			JButton greyButton5 = new JButton(); greyButton5.setBackground(Color.GRAY);
			JButton greyButton6 = new JButton(); greyButton6.setBackground(Color.GRAY); 
			JButton greyButton7 = new JButton(); greyButton7.setBackground(Color.GRAY);
			JButton greyButton8 = new JButton(); greyButton8.setBackground(Color.GRAY);
			JButton greyButton9 = new JButton(); greyButton9.setBackground(Color.GRAY);
			
			JButton darkGreyButton1 = new JButton("Arme"); darkGreyButton1.setBackground(Color.DARK_GRAY); darkGreyButton1.addActionListener(new Equipement(5));
			
			east.add(greyButton1);
			east.add(greyButton6);
			east.add(blackButton1);			
			east.add(greyButton2);
			
			east.add(darkGreyButton1);
			east.add(blackButton2);
			east.add(blackButton3);
			east.add(blackButton4);			
			
			east.add(greyButton7);
			east.add(greyButton3);
			east.add(blackButton5);
			east.add(greyButton4);
			
			east.add(greyButton8);
			east.add(blackButton6);
			east.add(blackButton7);
			east.add(blackButton8);
			
			east.add(greyButton9);
			east.add(blackButton9);
			east.add(greyButton5);
			east.add(blackButton10);
			
			JButton attaquer = new JButton("Attaquer (a)"); attaquer.addActionListener(new Action(4));
			JButton finTour = new JButton("Fin Tour (e)") ; finTour.addActionListener(new Action(5));
			
			JButton vide1 = new JButton(); vide1.setEnabled(false); vide1.setBackground(Color.WHITE); vide1.setBorderPainted(false);
			JButton vide2 = new JButton(); vide2.setEnabled(false); vide2.setBackground(Color.WHITE); vide2.setBorderPainted(false);
			JButton vide3 = new JButton(); vide3.setEnabled(false); vide3.setBackground(Color.WHITE); vide3.setBorderPainted(false);
			JButton vide4 = new JButton(); vide4.setEnabled(false); vide4.setBackground(Color.WHITE); vide4.setBorderPainted(false);
			JButton vide5 = new JButton(); vide5.setEnabled(false); vide5.setBackground(Color.WHITE); vide5.setBorderPainted(false);
			JButton vide6 = new JButton(); vide6.setEnabled(false); vide6.setBackground(Color.WHITE); vide6.setBorderPainted(false);
			JButton vide7 = new JButton(); vide7.setEnabled(false); vide7.setBackground(Color.WHITE); vide7.setBorderPainted(false);
			JButton vide8 = new JButton(); vide8.setEnabled(false); vide8.setBackground(Color.WHITE); vide8.setBorderPainted(false);
			
			JButton forceBut = new JButton("+"); forceBut.addActionListener(new Stat(0));
			JButton agiliteBut = new JButton("+"); agiliteBut.addActionListener(new Stat(1));
			JButton intelligenceBut = new JButton("+"); intelligenceBut.addActionListener(new Stat(2));
			JButton resistanceBut = new JButton("+"); resistanceBut.addActionListener(new Stat(3));
			
			this.force = new JTextArea(); 
			this.agilite = new JTextArea();
			this.intelligence = new JTextArea();
			this.resistance = new JTextArea();
			
			JTextArea forceText = new JTextArea("Force :"); 
			JTextArea agiliteText = new JTextArea("Agilite :");
			JTextArea intelligenceText = new JTextArea("Intell :");
			JTextArea resistanceText = new JTextArea("Resi :");

			stat.add(forceText); stat.add(force);  stat.add(forceBut);
			stat.add(agiliteText); stat.add(agilite);  stat.add(agiliteBut);
			stat.add(intelligenceText); stat.add(intelligence);  stat.add(intelligenceBut);
			stat.add(resistanceText); stat.add(resistance);  stat.add(resistanceBut);
			
			eastSub.add(infoPerso);
			eastSub.add(stat);
			eastSub.add(attaquer);
			eastSub.add(finTour);
			
			
			eastSubTab.add(east,BorderLayout.CENTER);
			eastSubBut.add(eastSub,BorderLayout.CENTER);
			
			
			
			eastTab.add(eastSubTab);
			eastTab.add(eastSubBut);
			eastTab.add(box);
			
	
		
		return eastTab;
	}
			
	
		
	/* Permet de reremplir l'inventaire affiche dynamiquement a chaque modification de celui ci en affichant les objets
	 * 
	 * Un clique sur un objet permet de voir ses informations
	 * 
	 * Un clique sur cet objet permet aussi d'ouvrir le menu de choix :
	 * 
	 * Equiper cet objet si il est equipable ou le consommer si il est consommable
	 * 
	 * Jeter cet objet
	 * 
	 * Annuler les choix
	 * 
	 */
		
	
	public JPanel initChat() {
		
		JPanel panelChat = new JPanel(new GridLayout(2,1,0,0));
		JPanel southChat = new JPanel();
		JPanel players = new JPanel(new GridLayout(2,2,30,20));

		chatIn = new JTextArea(1,25);
		chatIn.setBorder(BorderFactory.createTitledBorder("Chat In"));
		JButton bouttonChat = new JButton("Enter");
		bouttonChat.addActionListener(new ActionChat());
		
		chatOut = new JTextArea(20,20);
		chatOut.setBorder(BorderFactory.createTitledBorder("Chat Out"));
		
		JButton fermerChat = new JButton("X");
		fermerChat.addActionListener(new Menu(1));
		
		players.add(new JLabel("Zombie1"));
		players.add(new JLabel("Zombie2"));
		players.add(new JLabel("Zombie3"));
		players.add(new JLabel("Zombie4"));
		
		players.setBorder(BorderFactory.createTitledBorder("Personnages"));
		
		southChat.add(chatIn);
		southChat.add(bouttonChat);
		southChat.add(players);
		southChat.add(fermerChat);
		
		
		panelChat.add(chatOut);
		panelChat.add(southChat);

		
		return panelChat;
	}
	
	
	
	
	public void actualiserInventaire() {
		
		test.remove(test.getIndexOf(inventaire));			// Permet d'enlever l'inventaire prededent des couches de Panel	
		inventaire = new JPanel(new GridLayout(6,4,0,0));   // Reset l'inventaire
		inventaire.removeAll();
		inventaire.revalidate();
		inventaire.repaint();
		test.add(inventaire, new Integer(101));   // Le replace au dessus de la map
		inventaire.setBounds(200,200,500,500);
		
		JButton fermer = new JButton();  // Permet de fermer l'inventaire en cliquant sur ce bouton
		fermer.addActionListener(new Menu(1));
		fermer.addActionListener(new Menu(2));
		//fermer.setBackground(Color.WHITE);
		fermer.setIcon(croix_fermer);
		inventaire.add(fermer);
		
		ArrayList<JButton> listObjet = new ArrayList<JButton>();
		listObjet.clear();
		
		listChoixStuff = new ArrayList<JPanel>();
		listChoixStuff.clear();
				
		
		if(partie.getListPersonnage().size()>0) {
			
			if(partie.getListPersonnage().get(0).getInventaire().size()>0) {
				
				for(int i=1;i<partie.getListPersonnage().get(0).getInventaire().size()+1;i++) {
					
					listObjet.add(new JButton("obj"));
					listObjet.get(i-1).addActionListener(new GestionInventaire(i-1));				
					inventaire.add(listObjet.get(i-1));	
					
					listChoixStuff.add(new JPanel(new GridLayout(3,1)));
					
					JButton equiper = new JButton("Equiper");
					JButton jeter = new JButton("Jeter");
					JButton annuler = new JButton("Annuler");
					
					equiper.addActionListener(new ChoixObjet(1,1));  // Listener factice permettant la premiere actualisation de celui ci  dans la methode actualiserInventaire()
					jeter.addActionListener(new ChoixObjet(1,1));  // Listener factice permettant la premiere actualisation de celui ci  dans la methode actualiserInventaire()					
					annuler.addActionListener(new Menu(2));
					
					equiper.removeActionListener(equiper.getActionListeners()[0]);  //Enleve le listener qui etait attache au boutton 'Equiper' de choixStuff
					jeter.removeActionListener(jeter.getActionListeners()[0]);  //Enleve le listener qui etait attache au boutton 'Jeter' de choixStuff
					
					equiper.addActionListener(new ChoixObjet(i-1,1));  // Ajout du listener permettant une action sur un objet
					jeter.addActionListener(new ChoixObjet(i-1,2));
					
					listChoixStuff.get(i-1).add(equiper);
					listChoixStuff.get(i-1).add(jeter);
					listChoixStuff.get(i-1).add(annuler);
					
					test.add(listChoixStuff.get(i-1),new Integer(70+(i-1)));
					
				}
				
				if(partie.getListPersonnage().get(0).getInventaire().size()+1<=20) {
					for(int i=partie.getListPersonnage().get(0).getInventaire().size()+1;i<21;i++) {
						
						inventaire.add(new JButton("vide"));
					}
				}	
							
			}
			
		}
		
	}
	
	
	public JPanel testMenu() {
		
		this.testMenu = new JPanel(new GridLayout(2,2,0,0));
		
		JButton test1 = new JButton("test");
		JButton test2 = new JButton("test");
		JButton fermer = new JButton("FERMER");
		JButton test3 = new JButton("test");
		
		fermer.addActionListener(new Menu(1));
		
		testMenu.add(test1);
		testMenu.add(test2);
		testMenu.add(fermer);
		testMenu.add(test3);
		
		return testMenu;
	}
	
	
	
	
	
	
	/* Permet de lancer une action en appuyant sur le bouton adequat
	 * 
	 * PARAMETRE DU CONSTRUCTEUR :
	 * 
	 * 4 pour attaquer
	 * 
	 * 5 pour finir son tour
	 * 
	 * 6 pour check l'inventaire
	 * 
	 */
	private class Action implements ActionListener {
		
		private int action;
		
		public Action(int action) {
			
			this.action = action;
		}
		
		public void actionPerformed(ActionEvent e) {
			
			int play=0;
			int tailleAvt=partie.getListCombat().size();  // Permet de voir si le combat se termine en comprant les tailles pre et post attaque
			int x = 0;
			int y= 0;
			
			if(partie.getListCombat().size()>0) {  // Permet d'enregistrer avant une attaque potentielle la position de l'ennemi
				
				x= partie.getListCombat().get(1).getPositionX();
				y= partie.getListCombat().get(1).getPositionY();
			}				
			
			Iterator iteratorPersoPdtCbt = persoCombat.iterator();
			
			switch(this.action) {
			
				case 4 : // Attaquer
					
					play = partie.choix(4);
					
					if(partie.getListCombat().size()<tailleAvt) {  // Si le combat se termine alors les personnages hors du combat reapparaissent
						
						while(iteratorPersoPdtCbt.hasNext()) {
							
							Personnage persoPdtCbt = (Personnage) iteratorPersoPdtCbt.next();
							
							if(cases[persoPdtCbt.getPositionY()][persoPdtCbt.getPositionX()].getIcon()==ground1) 
								cases[persoPdtCbt.getPositionY()][persoPdtCbt.getPositionX()].setIcon(ground1_icone_zombie);
							
							else if(cases[persoPdtCbt.getPositionY()][persoPdtCbt.getPositionX()].getIcon()==ground2) 
								cases[persoPdtCbt.getPositionY()][persoPdtCbt.getPositionX()].setIcon(ground2_icone_zombie);
							
							if(persoPdtCbt.getPositionY()>0) {
								
								if(cases[persoPdtCbt.getPositionY()-1][persoPdtCbt.getPositionX()].getIcon()==wall_beg) 
									cases[persoPdtCbt.getPositionY()-1][persoPdtCbt.getPositionX()].setIcon(wall_beg_head_zombie);
								
								if(cases[persoPdtCbt.getPositionY()-1][persoPdtCbt.getPositionX()].getIcon()==ground1) 
									cases[persoPdtCbt.getPositionY()-1][persoPdtCbt.getPositionX()].setIcon(ground1_icone_zombie_head);
								
								if(cases[persoPdtCbt.getPositionY()-1][persoPdtCbt.getPositionX()].getIcon()==ground2) 
									cases[persoPdtCbt.getPositionY()-1][persoPdtCbt.getPositionX()].setIcon(ground2_icone_zombie_head);
								
								
							}
							iteratorPersoPdtCbt.remove();	
						}
						
						if(cases[y][x].getIcon() == ground1_icone_zombie)
							cases[y][x].setIcon(ground1);
						
						else if(cases[y][x].getIcon() == ground2_icone_zombie)
							cases[y][x].setIcon(ground2);
						
						if(cases[y][x].getIcon() == ground1_icone_zombie_wall_max)
							cases[y][x].setIcon(ground1_wall_max);
						
						else if(cases[y][x].getIcon() == ground2_icone_zombie_wall_max)
							cases[y][x].setIcon(ground2_wall_max);
							
						
						if(y>0) {
							
							if(cases[y-1][x].getIcon() == ground1_icone_zombie_head)
								cases[y-1][x].setIcon(ground1);
							
							else if(cases[y-1][x].getIcon() == ground2_icone_zombie_head)
								cases[y-1][x].setIcon(ground2);																		
							
							if(cases[y-1][x].getIcon() == wall_beg_head_zombie)
								cases[y-1][x].setIcon(wall_beg);
																
						}
						
						
						actualiserInventaire();
					}
					
					if(play==8)
						box.setText(partie.getMssgCbt());					
					
					if(play==7)
						box.setText("Vous n'etes pas en combat");									
					
					if(play==9)
						box.setText("Pas assez de Pa !");
					
					if(play==10)
						box.setText("L'ennemi est trop loin !");


					break;
				
				case 5 : // Finir son tour
					
					 partie.choix(5);
					
					
					
					break;
					
				case 6 : 
					
					
					break;	
			
			
			}
			
		}
		
		
	}	
	
	
	public class ActionChat implements ActionListener {
		
		public void actionPerformed(ActionEvent e){
			
			text += partie.getListPersonnage().get(0).getNom() + " : " + chatIn.getText() + "\n";
			text += "Zombie : Bonjour je suis Pierre le zombie\n";
			
			chatOut.setText(text);
			
			chatIn.setText(" ");
		}
		
		
	}
	
	
	
	/* Permet de lancer une action en appuyant sur le bouton adequat
	 * 
	 * PARAMETRE DU CONSTRUCTEUR :
	 * 
	 * 4 pour attaquer
	 * 
	 * 5 pour finir son tour
	 * 
	 * 6 pour check l'inventaire
	 * 
	 */
	private class ActionBoutton implements KeyListener {
				
		
		public void keyPressed(KeyEvent e) {
			
			int play=0;
			int tailleAvt=partie.getListCombat().size();  // Permet de voir si le combat se termine en comprant les tailles pre et post attaque
			int x = 0;
			int y= 0;
			
			if(partie.getListCombat().size()>0) {  // Permet d'enregistrer avant une attaque potentielle la position de l'ennemi
				
				x= partie.getListCombat().get(1).getPositionX();
				y= partie.getListCombat().get(1).getPositionY();
			}				
			
			Iterator iteratorPersoPdtCbt = persoCombat.iterator();
			
			char key = e.getKeyChar();
			
			switch(key) {
			
				case 'a' : // Attaquer
					
					play = partie.choix(4);
					
					if(partie.getListCombat().size()<tailleAvt) {  // Si le combat se termine alors les personnages hors du combat reapparaissent
						
						while(iteratorPersoPdtCbt.hasNext()) {
							
							Personnage persoPdtCbt = (Personnage) iteratorPersoPdtCbt.next();
							
							if(cases[persoPdtCbt.getPositionY()][persoPdtCbt.getPositionX()].getIcon()==ground1) 
								cases[persoPdtCbt.getPositionY()][persoPdtCbt.getPositionX()].setIcon(ground1_icone_zombie);
							
							else if(cases[persoPdtCbt.getPositionY()][persoPdtCbt.getPositionX()].getIcon()==ground2) 
								cases[persoPdtCbt.getPositionY()][persoPdtCbt.getPositionX()].setIcon(ground2_icone_zombie);
							
							if(persoPdtCbt.getPositionY()>0) {
								
								if(cases[persoPdtCbt.getPositionY()-1][persoPdtCbt.getPositionX()].getIcon()==wall_beg) 
									cases[persoPdtCbt.getPositionY()-1][persoPdtCbt.getPositionX()].setIcon(wall_beg_head_zombie);
								
								if(cases[persoPdtCbt.getPositionY()-1][persoPdtCbt.getPositionX()].getIcon()==ground1) 
									cases[persoPdtCbt.getPositionY()-1][persoPdtCbt.getPositionX()].setIcon(ground1_icone_zombie_head);
								
								if(cases[persoPdtCbt.getPositionY()-1][persoPdtCbt.getPositionX()].getIcon()==ground2) 
									cases[persoPdtCbt.getPositionY()-1][persoPdtCbt.getPositionX()].setIcon(ground2_icone_zombie_head);
								
								
							}
							iteratorPersoPdtCbt.remove();	
						}
						
						if(cases[y][x].getIcon() == ground1_icone_zombie)
							cases[y][x].setIcon(ground1);
						
						else if(cases[y][x].getIcon() == ground2_icone_zombie)
							cases[y][x].setIcon(ground2);
						
						if(cases[y][x].getIcon() == ground1_icone_zombie_wall_max)
							cases[y][x].setIcon(ground1_wall_max);
						
						else if(cases[y][x].getIcon() == ground2_icone_zombie_wall_max)
							cases[y][x].setIcon(ground2_wall_max);
							
						
						if(y>0) {
							
							if(cases[y-1][x].getIcon() == ground1_icone_zombie_head)
								cases[y-1][x].setIcon(ground1);
							
							else if(cases[y-1][x].getIcon() == ground2_icone_zombie_head)
								cases[y-1][x].setIcon(ground2);																		
							
							if(cases[y-1][x].getIcon() == wall_beg_head_zombie)
								cases[y-1][x].setIcon(wall_beg);
																
						}
						
						
						actualiserInventaire();
					}
					
					if(play==8)
						box.setText(partie.getMssgCbt());					
					
					if(play==7)
						box.setText("Vous n'etes pas en combat");									
					
					if(play==9)
						box.setText("Pas assez de Pa !");
					
					if(play==10)
						box.setText("L'ennemi est trop loin !");


					break;
				
				case 'e' : // Finir son tour
					
					 partie.choix(5);
					
					
					
					break;
					
				
			}
			
		}
		
		public void keyTyped (KeyEvent ev) {}
		
		public void keyReleased (KeyEvent evt){}
		
	}	
	
	
	private class Stat implements ActionListener {
		
		private int stat;
		
		public Stat(int stat) {
			
			this.stat = stat;
		}
		
		public void actionPerformed(ActionEvent e) {		
			
			if(partie.getListPersonnage().size()>0) {
			
				switch(this.stat) {
				
					case 0 : // Augmenter force
						
						partie.getListPersonnage().get(0).repartirStat(0);
						
			            break;
					
					case 1 : // Augmenter agilite
						
						partie.getListPersonnage().get(0).repartirStat(1);
			            
			            break;
						
					case 2 : // Augmenter intelligence
						
						partie.getListPersonnage().get(0).repartirStat(2);
						
						break;	
						
					case 3 : // Augmenter resistance
						
						partie.getListPersonnage().get(0).repartirStat(3);
															
						break;
				
				
				}
			}
		}
		
		
	}
	
	private class Equipement implements ActionListener { // Permet de voir son stuff et reset la map
		
		Random rand = new Random();
		
		private int emplacement;
		 
		public Equipement(int emplacement) { this.emplacement = emplacement; }
		
		public void actionPerformed(ActionEvent e) {
			
			switch(this.emplacement){
			
				case 0 : // Affiche le casque si il y en a un equippe
					
					if(partie.getListPersonnage().size()>0) {
						if(partie.getListPersonnage().get(0).getCasque() != null) 
							box.setText("" + partie.getListPersonnage().get(0).getCasque());	
						
						else					
							box.setText("Aucun casque equippe !");
					}
					
					else					
						box.setText("Aucun casque equippe !");
					
					break;
			
				case 1 :  // Affiche le torse si il y en a un equippe
					
					if(partie.getListPersonnage().size()>0) {
						if(partie.getListPersonnage().get(0).getTorse() != null) 
							box.setText("" + partie.getListPersonnage().get(0).getTorse());		
						
						else					
							box.setText("Aucun torse equippe !");
					}
					
					else					
						box.setText("Aucun torse equippe !");
					
					break;
					
				case 2 :  // Affiche les gants si il y en a equippes
	
					if(partie.getListPersonnage().size()>0) {
						if(partie.getListPersonnage().get(0).getGants() != null) 
							box.setText("" + partie.getListPersonnage().get(0).getGants());	
						
						else					
							box.setText("Aucun gants equippes !");	
							
					}
					
					else					
						box.setText("Aucun gants equippes !");
					
					break;
					
				case 3 :  // Affiche le pantalon si il y en a un equippe
	
					if(partie.getListPersonnage().size()>0) {
						if(partie.getListPersonnage().get(0).getPantalon() != null) 
							box.setText("" + partie.getListPersonnage().get(0).getPantalon());	
						
						else					
							box.setText("Aucun pantalon equippe !");
					}
					
					else					
						box.setText("Aucun pantalon equippe !");
					
					break;
			
				case 4 :  // Affiche les bottes si il y en a equippees
					
					if(partie.getListPersonnage().size()>0) {
						if(partie.getListPersonnage().get(0).getBottes() != null) 
							box.setText("" + partie.getListPersonnage().get(0).getBottes());	
						
						else					
							box.setText("Aucunes bottes equippees !");
					}
					
					else					
						box.setText("Aucunes bottes equippees !");
					
					break;	
				
				case 5 : // Affiche l'arme si il y en a une equippee
					
					if(partie.getListPersonnage().size()>0) {
						if(partie.getListPersonnage().get(0).getArme() != null) 
							box.setText("" + partie.getListPersonnage().get(0).getArme());	
						
						else					
							box.setText("Aucune arme equippee !");
					}
					
					else					
						box.setText("Aucune arme equippee !");
					
					break;	
			}
			
		}
		
		
	}	
	
	//Listener a instancier a 0 pour afficher l'inventaire et 1 pour afficher la map
	
	
	private class Menu implements ActionListener {
		
		private int menu;
		
		public Menu(int menu) {
			
			this.menu = menu;
		}
		
		public void actionPerformed(ActionEvent e) {
			
			switch(this.menu) {			
			
				case 0 : // Afficher l'inventaire
					
					test.setLayer(inventaire,new Integer(101));
					test.setLayer(map,new Integer(100));
					test.setLayer(chat,new Integer(99));
					box.setText("inventaire");
					break;
				
				case 1 : // Afficher la map
			
					test.setLayer(inventaire,new Integer(100));
					test.setLayer(map,new Integer(101));
					test.setLayer(chat, new Integer(99));
					box.setText("Map");
					robot.mouseMove(MouseInfo.getPointerInfo().getLocation().x,MouseInfo.getPointerInfo().getLocation().y); // Simule un clique pour reprendre les deplacements
					robot.mousePress(InputEvent.BUTTON1_MASK);
		            robot.mouseRelease(InputEvent.BUTTON1_MASK);
					break;
				
				case 2 :
					
					if(listChoixStuff.size()>0) {  // actualise l'affichage des choix objet
						
						for(int i=0;i<listChoixStuff.size();i++) {
							test.setLayer(listChoixStuff.get(i),new Integer(70+i));
						}
					}
					
					break;
					
				
					
			}
			
		}
		
		
	}
	
	
	
	
	public class GestionInventaire implements ActionListener {
		
		private int obj;
		
		public GestionInventaire(int obj) {
			
			this.obj = obj;
		}
		
		public void actionPerformed(ActionEvent e){
			
			 
			
			box.setText("" + partie.getListPersonnage().get(0).getInventaire().get(obj)); // Donne les info concernant l'objet
			listChoixStuff.get(obj).setBounds((int)((JButton)e.getSource()).getLocation().getX()+300,(int)((JButton)e.getSource()).getLocation().getY()+270,100,100); // Place le panel de choix concernant l' objet selectionne
			for(int i=0;i<listChoixStuff.size();i++) {
				if(i==obj)  // Ne laisse que le bon choix sur objet en premier plan
					test.setLayer(listChoixStuff.get(obj),new Integer(102));
				else
					test.setLayer(listChoixStuff.get(i),new Integer(70+i));
			}	
		}
		
		
		
	}
	
	public class ChoixObjet implements ActionListener {
		
		private int choix;
		private int indice;
		
		public ChoixObjet(int indice, int choix) {
			
			this.indice = indice;
			this.choix = choix;
		}
		
		public void actionPerformed(ActionEvent e) {
			
			
			switch(choix) {
			
			case 1 : // Permet d'equiper un equipement ou de consommer un consommable en fonction du type de l'objet selectionne
				
				
					if(partie.getListCombat().size()==0) {
						partie.getListPersonnage().get(0).consommer(partie.getListPersonnage().get(0).getInventaire().get(indice));
						partie.getListPersonnage().get(0).equip(partie.getListPersonnage().get(0).getInventaire().get(indice));				
						box.setText("Equipe/Consomme");
					}
					
					if(listChoixStuff.size()>0) {  // Cache toute les choixStuff sur les objets
						
						for(int i=0;i<listChoixStuff.size();i++) {
							test.setLayer(listChoixStuff.get(i),new Integer(70+i));
						}
					}
					
					actualiserInventaire();
					
				
				break;
				
			case 2 : // Jete un objet
				
				partie.getListPersonnage().get(0).jeterObjetInventaire(partie.getListPersonnage().get(0).getInventaire().get(indice));
				if(partie.getListCombat().size()==0)
					box.setText("Jete");
				
				if(listChoixStuff.size()>0) {  // Cache toute les choixStuff sur les objets
					
					for(int i=0;i<listChoixStuff.size();i++) {
						test.setLayer(listChoixStuff.get(i),new Integer(70+i));
					}
				}
				
				actualiserInventaire(); 
				
				
				break;
			
			case 3 :
				
				if(listChoixStuff.size()>0) {  // Cache toute les choixStuff sur les objets
					
					for(int i=0;i<listChoixStuff.size();i++) {
						test.setLayer(listChoixStuff.get(i),new Integer(70+i));
					}
				}
				break;
			}
			
			
			
		}
				
	}
	
	
	public class Deplacement implements KeyListener {   // Listener du deplacement et actions sur la map
		
		public void keyPressed(KeyEvent e){    // Lorsque l'on touche une touche du clavier
		    
			int tailleAvt=partie.getListCombat().size();
			Iterator iteListPerso = partie.getListPersonnage().iterator();
			
			touche = e.getKeyChar();  //Capture la touche du clavier appuyee
			
			switch(touche) {
																				
				case 'z' :
					
					deplacementHaut();
					
					if(partie.getListCombat().size()>tailleAvt) { // actualise la map a l'entree en combat en enlevant tout les personnages ne combattant pas de la map
						
						
						while(iteListPerso.hasNext()) {
							
							Personnage perso = (Personnage) iteListPerso.next();
							
							if(!(perso.equals(partie.getListCombat().get(0))) && !(perso.equals(partie.getListCombat().get(1)))){
								
								persoCombat.add(perso);
								
								if(cases[perso.getPositionY()][perso.getPositionX()].getIcon() == ground1_icone_zombie)
									cases[perso.getPositionY()][perso.getPositionX()].setIcon(ground1);
								
								else if(cases[perso.getPositionY()][perso.getPositionX()].getIcon() == ground2_icone_zombie)
									cases[perso.getPositionY()][perso.getPositionX()].setIcon(ground2);
								
								if(cases[perso.getPositionY()][perso.getPositionX()].getIcon() == ground1_icone_zombie_wall_max)
									cases[perso.getPositionY()][perso.getPositionX()].setIcon(ground1_wall_max);
								
								else if(cases[perso.getPositionY()][perso.getPositionX()].getIcon() == ground2_icone_zombie_wall_max)
									cases[perso.getPositionY()][perso.getPositionX()].setIcon(ground2_wall_max);
									
								
								if(perso.getPositionY()>0) {
									
									if(cases[perso.getPositionY()-1][perso.getPositionX()].getIcon() == ground1_icone_zombie_head)
										cases[perso.getPositionY()-1][perso.getPositionX()].setIcon(ground1);
									
									else if(cases[perso.getPositionY()-1][perso.getPositionX()].getIcon() == ground2_icone_zombie_head)
										cases[perso.getPositionY()-1][perso.getPositionX()].setIcon(ground2);																		
									
									if(cases[perso.getPositionY()-1][perso.getPositionX()].getIcon() == wall_beg_head_zombie)
										cases[perso.getPositionY()-1][perso.getPositionX()].setIcon(wall_beg);
																		
								}
							}
							
						}
						
						if(partie.getListCombat().get(1).getPositionY()>0 && partie.getListCombat().get(1).getPositionX()>0) { // Disparition de la tete du joueur qui vient d'entrer en combat
							
							if(cases[partie.getListCombat().get(1).getPositionY()-1][partie.getListCombat().get(1).getPositionX()-1].getIcon()==ground1_head)
								cases[partie.getListCombat().get(1).getPositionY()-1][partie.getListCombat().get(1).getPositionX()-1].setIcon(ground1);
							
							if(cases[partie.getListCombat().get(1).getPositionY()-1][partie.getListCombat().get(1).getPositionX()-1].getIcon()==ground2_head)
								cases[partie.getListCombat().get(1).getPositionY()-1][partie.getListCombat().get(1).getPositionX()-1].setIcon(ground2);
							
							if(cases[partie.getListCombat().get(1).getPositionY()-1][partie.getListCombat().get(1).getPositionX()-1].getIcon()==wall_beg_head)
								cases[partie.getListCombat().get(1).getPositionY()-1][partie.getListCombat().get(1).getPositionX()-1].setIcon(wall_beg);
							
							if(cases[partie.getListCombat().get(1).getPositionY()-1][partie.getListCombat().get(1).getPositionX()-1].getIcon()==wall_beg_head)
								cases[partie.getListCombat().get(1).getPositionY()-1][partie.getListCombat().get(1).getPositionX()-1].setIcon(wall_beg);
						}	
					}
					
					break;	
				
				case 's' : 
					
					deplacementBas();
					
					if(partie.getListCombat().size()>tailleAvt) { // actualise la map a l'entree en combat en enlevant tout les personnages ne combattant pas de la map
						
						
						while(iteListPerso.hasNext()) {
							
							Personnage perso = (Personnage) iteListPerso.next();
							
							if(!(perso.equals(partie.getListCombat().get(0))) && !(perso.equals(partie.getListCombat().get(1)))){
								
								persoCombat.add(perso);
								
								if(cases[perso.getPositionY()][perso.getPositionX()].getIcon() == ground1_icone_zombie)
									cases[perso.getPositionY()][perso.getPositionX()].setIcon(ground1);
								
								else if(cases[perso.getPositionY()][perso.getPositionX()].getIcon() == ground2_icone_zombie)
									cases[perso.getPositionY()][perso.getPositionX()].setIcon(ground2);
								
								if(cases[perso.getPositionY()][perso.getPositionX()].getIcon() == ground1_icone_zombie_wall_max)
									cases[perso.getPositionY()][perso.getPositionX()].setIcon(ground1_wall_max);
								
								else if(cases[perso.getPositionY()][perso.getPositionX()].getIcon() == ground2_icone_zombie_wall_max)
									cases[perso.getPositionY()][perso.getPositionX()].setIcon(ground2_wall_max);
									
								
								if(perso.getPositionY()>0) {
									
									if(cases[perso.getPositionY()-1][perso.getPositionX()].getIcon() == ground1_icone_zombie_head)
										cases[perso.getPositionY()-1][perso.getPositionX()].setIcon(ground1);
									
									else if(cases[perso.getPositionY()-1][perso.getPositionX()].getIcon() == ground2_icone_zombie_head)
										cases[perso.getPositionY()-1][perso.getPositionX()].setIcon(ground2);																		
									
									if(cases[perso.getPositionY()-1][perso.getPositionX()].getIcon() == wall_beg_head_zombie)
										cases[perso.getPositionY()-1][perso.getPositionX()].setIcon(wall_beg);
																		
								}
							}
							
						}
						
						if(partie.getListCombat().get(1).getPositionY()>0 && partie.getListCombat().get(1).getPositionX()>0) { // Disparition de la tete du joueur qui vient d'entrer en combat
							
							if(cases[partie.getListCombat().get(1).getPositionY()-1][partie.getListCombat().get(1).getPositionX()-1].getIcon()==ground1_head)
								cases[partie.getListCombat().get(1).getPositionY()-1][partie.getListCombat().get(1).getPositionX()-1].setIcon(ground1);
							
							if(cases[partie.getListCombat().get(1).getPositionY()-1][partie.getListCombat().get(1).getPositionX()-1].getIcon()==ground2_head)
								cases[partie.getListCombat().get(1).getPositionY()-1][partie.getListCombat().get(1).getPositionX()-1].setIcon(ground2);
							
							if(cases[partie.getListCombat().get(1).getPositionY()-1][partie.getListCombat().get(1).getPositionX()-1].getIcon()==wall_beg_head)
								cases[partie.getListCombat().get(1).getPositionY()-1][partie.getListCombat().get(1).getPositionX()-1].setIcon(wall_beg);
							
							if(cases[partie.getListCombat().get(1).getPositionY()-1][partie.getListCombat().get(1).getPositionX()-1].getIcon()==wall_beg_head)
								cases[partie.getListCombat().get(1).getPositionY()-1][partie.getListCombat().get(1).getPositionX()-1].setIcon(wall_beg);
						}	
					}
				
					break;
					
				case 'q':
					
					deplacementGauche();
					
					if(partie.getListCombat().size()>tailleAvt) { // actualise la map a l'entree en combat en enlevant tout les personnages ne combattant pas de la map
						
						
						while(iteListPerso.hasNext()) {
							
							Personnage perso = (Personnage) iteListPerso.next();
							
							if(!(perso.equals(partie.getListCombat().get(0))) && !(perso.equals(partie.getListCombat().get(1)))){
								
								persoCombat.add(perso);
								
								if(cases[perso.getPositionY()][perso.getPositionX()].getIcon() == ground1_icone_zombie)
									cases[perso.getPositionY()][perso.getPositionX()].setIcon(ground1);
								
								else if(cases[perso.getPositionY()][perso.getPositionX()].getIcon() == ground2_icone_zombie)
									cases[perso.getPositionY()][perso.getPositionX()].setIcon(ground2);
								
								if(cases[perso.getPositionY()][perso.getPositionX()].getIcon() == ground1_icone_zombie_wall_max)
									cases[perso.getPositionY()][perso.getPositionX()].setIcon(ground1_wall_max);
								
								else if(cases[perso.getPositionY()][perso.getPositionX()].getIcon() == ground2_icone_zombie_wall_max)
									cases[perso.getPositionY()][perso.getPositionX()].setIcon(ground2_wall_max);
									
								
								if(perso.getPositionY()>0) {
									
									if(cases[perso.getPositionY()-1][perso.getPositionX()].getIcon() == ground1_icone_zombie_head)
										cases[perso.getPositionY()-1][perso.getPositionX()].setIcon(ground1);
									
									else if(cases[perso.getPositionY()-1][perso.getPositionX()].getIcon() == ground2_icone_zombie_head)
										cases[perso.getPositionY()-1][perso.getPositionX()].setIcon(ground2);																		
									
									if(cases[perso.getPositionY()-1][perso.getPositionX()].getIcon() == wall_beg_head_zombie)
										cases[perso.getPositionY()-1][perso.getPositionX()].setIcon(wall_beg);
																		
								}
							}
							
						}
						
						if(partie.getListCombat().get(1).getPositionY()>0 && partie.getListCombat().get(1).getPositionX()< largeurMap-1) { // Disparition de la tete du joueur qui vient d'entrer en combat
							
							if(cases[partie.getListCombat().get(1).getPositionY()-1][partie.getListCombat().get(1).getPositionX()+1].getIcon()==ground1_head)
								cases[partie.getListCombat().get(1).getPositionY()-1][partie.getListCombat().get(1).getPositionX()+1].setIcon(ground1);
							
							if(cases[partie.getListCombat().get(1).getPositionY()-1][partie.getListCombat().get(1).getPositionX()+1].getIcon()==ground2_head)
								cases[partie.getListCombat().get(1).getPositionY()-1][partie.getListCombat().get(1).getPositionX()+1].setIcon(ground2);
							
							if(cases[partie.getListCombat().get(1).getPositionY()-1][partie.getListCombat().get(1).getPositionX()+1].getIcon()==wall_beg_head)
								cases[partie.getListCombat().get(1).getPositionY()-1][partie.getListCombat().get(1).getPositionX()+1].setIcon(wall_beg);
							
							if(cases[partie.getListCombat().get(1).getPositionY()-1][partie.getListCombat().get(1).getPositionX()+1].getIcon()==wall_beg_head)
								cases[partie.getListCombat().get(1).getPositionY()-1][partie.getListCombat().get(1).getPositionX()+1].setIcon(wall_beg);
						}	
					}
					
					break;	
					
				case 'd':
					
					deplacementDroit();
					
					if(partie.getListCombat().size()>tailleAvt) { // actualise la map a l'entree en combat en enlevant tout les personnages ne combattant pas de la map
						
						
						while(iteListPerso.hasNext()) {
							
							Personnage perso = (Personnage) iteListPerso.next();
							
							if(!(perso.equals(partie.getListCombat().get(0))) && !(perso.equals(partie.getListCombat().get(1)))){
								
								persoCombat.add(perso);
								
								if(cases[perso.getPositionY()][perso.getPositionX()].getIcon() == ground1_icone_zombie)
									cases[perso.getPositionY()][perso.getPositionX()].setIcon(ground1);
								
								else if(cases[perso.getPositionY()][perso.getPositionX()].getIcon() == ground2_icone_zombie)
									cases[perso.getPositionY()][perso.getPositionX()].setIcon(ground2);
								
								if(cases[perso.getPositionY()][perso.getPositionX()].getIcon() == ground1_icone_zombie_wall_max)
									cases[perso.getPositionY()][perso.getPositionX()].setIcon(ground1_wall_max);
								
								else if(cases[perso.getPositionY()][perso.getPositionX()].getIcon() == ground2_icone_zombie_wall_max)
									cases[perso.getPositionY()][perso.getPositionX()].setIcon(ground2_wall_max);
									
								
								if(perso.getPositionY()>0) {
									
									if(cases[perso.getPositionY()-1][perso.getPositionX()].getIcon() == ground1_icone_zombie_head)
										cases[perso.getPositionY()-1][perso.getPositionX()].setIcon(ground1);
									
									else if(cases[perso.getPositionY()-1][perso.getPositionX()].getIcon() == ground2_icone_zombie_head)
										cases[perso.getPositionY()-1][perso.getPositionX()].setIcon(ground2);																		
									
									if(cases[perso.getPositionY()-1][perso.getPositionX()].getIcon() == wall_beg_head_zombie)
										cases[perso.getPositionY()-1][perso.getPositionX()].setIcon(wall_beg);
																		
								}
							}
							
						}
						
						if(partie.getListCombat().get(1).getPositionY()>0 && partie.getListCombat().get(1).getPositionX()>0) { // Disparition de la tete du joueur qui vient d'entrer en combat
							
							if(cases[partie.getListCombat().get(1).getPositionY()-1][partie.getListCombat().get(1).getPositionX()-1].getIcon()==ground1_head)
								cases[partie.getListCombat().get(1).getPositionY()-1][partie.getListCombat().get(1).getPositionX()-1].setIcon(ground1);
							
							if(cases[partie.getListCombat().get(1).getPositionY()-1][partie.getListCombat().get(1).getPositionX()-1].getIcon()==ground2_head)
								cases[partie.getListCombat().get(1).getPositionY()-1][partie.getListCombat().get(1).getPositionX()-1].setIcon(ground2);
							
							if(cases[partie.getListCombat().get(1).getPositionY()-1][partie.getListCombat().get(1).getPositionX()-1].getIcon()==wall_beg_head)
								cases[partie.getListCombat().get(1).getPositionY()-1][partie.getListCombat().get(1).getPositionX()-1].setIcon(wall_beg);
							
							if(cases[partie.getListCombat().get(1).getPositionY()-1][partie.getListCombat().get(1).getPositionX()-1].getIcon()==wall_beg_head)
								cases[partie.getListCombat().get(1).getPositionY()-1][partie.getListCombat().get(1).getPositionX()-1].setIcon(wall_beg);
						}	
					}
					
					break;
					
				case '&' :  // Place l'inventaire en premier plan
					
					test.setLayer(inventaire,new Integer(101));
					test.setLayer(map,new Integer(100));
					test.setLayer(chat,new Integer(99));
					box.setText("Inventaire");
					break;
					
				case 'c' : 	
					
					test.setLayer(inventaire, new Integer(99));
					test.setLayer(map, new Integer(100));
					test.setLayer(chat, new Integer(101));
					box.setText("Chat");
					break;
			}
			
			
		}
		
		public void keyTyped (KeyEvent ev) {}
		
		public void keyReleased (KeyEvent evt){}
		
		
		
	}
	
	
	
	
	public class AttaqueClick implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			
		}
		
	}
	
	
	public String totoString (int lol){
		
		String str = "";
		str+=lol;
		
		return str;
	}
	
	public void actualisationInfoStat() {
		
		if(partie.getListPersonnage().size()>0){
			this.infoPerso.setText("" + partie.getListPersonnage().get(0).afficherInfo());
			this.force.setText("" + partie.getListPersonnage().get(0).getForce());
			this.agilite.setText("" + partie.getListPersonnage().get(0).getAgilite());
			this.intelligence.setText("" + partie.getListPersonnage().get(0).getIntelligence());
			this.resistance.setText("" + partie.getListPersonnage().get(0).getResistance());
		}	
									
		
	}
	
	public void actualisation() {
		
		Timer timer = new Timer();
									
		timer.schedule(new TimerTask() {  
			
			@Override
			  public void run() {
				
				actualisationInfoStat();
																	
				
		}}, 0,1);
	}
	
	
	/*public class AffichageInfo implements Runnable {
		private boolean flag;
		
		public AffichageInfo(){
			this.flag=true;
		}

		public void setFlag(boolean flag) {
			this.flag = flag;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			while (flag){
				
				box.setText(partie.getMssgCbt());
				
				
			}
			
		}

	}*/
	
	
	/*public void actualiserMap(){
		
		for(int i=0;i<largeurMap;i++) {
			
			for(int j=0;j<largeurMap;j++) {
				
				for(int k=0;k<partie.getListPersonnage().size();k++) {
					
					if(partie.getListPersonnage().get(k).getPositionX()==j && partie.getListPersonnage().get(k).getPositionY()==i) {
						
						if(partie.getListPersonnage().get(k) instanceof Fleau || partie.getListPersonnage().get(k) instanceof Distance || partie.getListPersonnage().get(k) instanceof Ingenieur || partie.getListPersonnage().get(k) instanceof Tank) {
							
							if(cases[i][j].getIcon()==ground1)
								cases[i][j].setIcon(ground1_icone);
							
							else if(cases[i][i].getIcon()==ground2)// pareil qu'au dessus mais pour ground2
								cases[i][i].setIcon(ground2_icone);
								
						}
						
						else if(partie.getListPersonnage().get(k) instanceof Monstre) {
							
							if(cases[i][j].getIcon()==ground1)
								cases[i][j].setIcon(ground1_icone_zombie);
							
							else if(cases[i][j].getIcon()==ground2)// pareil qu'au dessus mais pour ground2
								cases[i][j].setIcon(ground2_icone_zombie);
														
							
						}
							
						
					}
					
				}
				
			}
		}
		
	}*/
	
	
	
	
	
	public void deplacementDroit(){
		if(iterateurj != largeurMap-1 &&(!(cases[iterateuri][iterateurj+1].getBackground()==Color.BLACK)) && (!(cases[iterateuri][iterateurj+1].getIcon()==ground1_icone_zombie_head)) && (!(cases[iterateuri][iterateurj+1].getIcon()==ground2_icone_zombie_head)) && (!(cases[iterateuri-1][iterateurj+1].getIcon()==ground1_icone_zombie)) && (!(cases[iterateuri-1][iterateurj+1].getIcon()==ground2_icone_zombie)))	{ // si la prochaine case n'est pas un mur et si la case est en bout droit de la map ne fait rien sinon deplace a droite
			
			iterateuriTemp = iterateuri;
			iterateurjTemp = iterateurj;
			
			partie.choix(3);
			
			
			
			iterateuri = partie.getListPersonnage().get(0).getPositionY();
			iterateurj = partie.getListPersonnage().get(0).getPositionX();		
			
			if(cases[iterateuri][iterateurj].getIcon()==ground1){// pareil qu'au dessus mais pour ground2
				cases[iterateuri][iterateurj].setIcon(ground1_icone);//on deplace le perso avec sol ground1
				if(cases[iterateuri-1][iterateurj].getDisabledIcon()==wall_beg){//si la case du dessus est un mur on place la tete en consequence
					cases[iterateuri-1][iterateurj].setIcon(wall_beg_head);
					cases[iterateuri-1][iterateurj].setDisabledIcon(wall_beg_head);

				}
				else{//sinon on place la tete avec ground2
					cases[iterateuri-1][iterateurj].setIcon(ground2_head);
					if(iterateurj>0)
						cases[iterateuri-1][iterateurj-1].setIcon(ground1);
				}		
				if(iterateurj>0) {
					if(cases[iterateuri-1][iterateurj-1].getDisabledIcon()==wall_beg_head){
							cases[iterateuri-1][iterateurj-1].setIcon(wall_beg);
							cases[iterateuri-1][iterateurj-1].setDisabledIcon(wall_beg);
						}
						else{
							cases[iterateuri-1][iterateurj-1].setIcon(ground1);
						}
				}	
				if(cases[iterateuriTemp][iterateurjTemp].getIcon()==ground2_icone_wall_max){  //update de l'ancienne case
				cases[iterateuriTemp][iterateurjTemp].setIcon(ground2_wall_max);
				}
				else{
				cases[iterateuriTemp][iterateurjTemp].setIcon(ground2);//update de l'ancienne case
				}

			}
			if(cases[iterateuri][iterateurj].getIcon()==ground2){// pareil qu'au dessus mais pour ground2
				cases[iterateuri][iterateurj].setIcon(ground2_icone);//on deplace le perso avec sol ground1
				if(cases[iterateuri-1][iterateurj].getDisabledIcon()==wall_beg){//si la case du dessus est un mur on place la tete en consequence
					cases[iterateuri-1][iterateurj].setIcon(wall_beg_head);
					cases[iterateuri-1][iterateurj].setDisabledIcon(wall_beg_head);
				}
				else{//sinon on place la tete avec ground2
					cases[iterateuri-1][iterateurj].setIcon(ground1_head);
					if(iterateurj>0)
						cases[iterateuri-1][iterateurj-1].setIcon(ground2);
				}
				if(cases[iterateuriTemp][iterateurjTemp].getIcon()==ground1_icone_wall_max){
				cases[iterateuriTemp][iterateurjTemp].setIcon(ground1_wall_max);//update de l'ancienne case
				}
				else{
				cases[iterateuriTemp][iterateurjTemp].setIcon(ground1);//update de l'ancienne case
				}
				if(iterateurj>0) {
					if(cases[iterateuri-1][iterateurj-1].getDisabledIcon()==wall_beg_head){
						cases[iterateuri-1][iterateurj-1].setIcon(wall_beg);
						cases[iterateuri-1][iterateurj-1].setDisabledIcon(wall_beg);
					}
					else{
						cases[iterateuri-1][iterateurj-1].setIcon(ground2);
					}
				}	

			}
			if(cases[iterateuri][iterateurj].getIcon()==ground1_wall_max){// pareil qu'au dessus mais pour ground2
				cases[iterateuri][iterateurj].setIcon(ground1_icone_wall_max);//on deplace le perso avec sol ground1
				if(cases[iterateuri-1][iterateurj].getDisabledIcon()==wall_beg){//si la case du dessus est un mur on place la tete en consequence
					cases[iterateuri-1][iterateurj].setIcon(wall_beg_head);
					cases[iterateuri-1][iterateurj].setDisabledIcon(wall_beg_head);
					if(iterateurj>0) {
						if(cases[iterateuri-1][iterateurj-1].getDisabledIcon()==wall_beg_head){
							cases[iterateuri-1][iterateurj-1].setIcon(wall_beg);
							cases[iterateuri-1][iterateurj-1].setDisabledIcon(wall_beg);
						}
						else{
							cases[iterateuri-1][iterateurj-1].setIcon(ground1);
						}
					}	
				}
				else{//sinon on place la tete avec ground2
					cases[iterateuri-1][iterateurj].setIcon(ground2_head);
					cases[iterateuri-1][iterateurj-1].setIcon(ground1);
					if(iterateurj>0) {
						if(cases[iterateuri-1][iterateurj-1].getDisabledIcon()==wall_beg_head){
							cases[iterateuri-1][iterateurj-1].setIcon(wall_beg);
							cases[iterateuri-1][iterateurj-1].setDisabledIcon(wall_beg);
						}
						else{
							cases[iterateuri-1][iterateurj-1].setIcon(ground1);
						}
					}	
				}
				if(cases[iterateuriTemp][iterateurjTemp].getIcon()==ground2_icone_wall_max){
				cases[iterateuriTemp][iterateurjTemp].setIcon(ground2_wall_max);//update de l'ancienne case
				}
				else{
				cases[iterateuriTemp][iterateurjTemp].setIcon(ground2);//update de l'ancienne case
				}

			}


							
		if(cases[iterateuri][iterateurj].getIcon()==ground2_wall_max){// pareil qu'au dessus mais pour ground2
			cases[iterateuri][iterateurj].setIcon(ground2_icone_wall_max);//on deplace le perso avec sol ground1
			if(cases[iterateuri-1][iterateurj].getDisabledIcon()==wall_beg){//si la case du dessus est un mur on place la tete en consequence
				cases[iterateuri-1][iterateurj].setIcon(wall_beg_head);
				cases[iterateuri-1][iterateurj].setDisabledIcon(wall_beg_head);
				if(iterateurj>0) {
					if(cases[iterateuri-1][iterateurj-1].getDisabledIcon()==wall_beg_head){
						cases[iterateuri-1][iterateurj-1].setIcon(wall_beg);
						cases[iterateuri-1][iterateurj-1].setDisabledIcon(wall_beg);
					}
					else{
						cases[iterateuri-1][iterateurj-1].setIcon(ground2);
					}
				}	
			}
			else{//sinon on place la tete avec ground2
				cases[iterateuri-1][iterateurj].setIcon(ground1_head);
				if(iterateurj>0) {
					cases[iterateuri-1][iterateurj-1].setIcon(ground2);
					if(cases[iterateuri-1][iterateurj-1].getDisabledIcon()==wall_beg_head){
						cases[iterateuri-1][iterateurj-1].setIcon(wall_beg);
						cases[iterateuri-1][iterateurj-1].setDisabledIcon(wall_beg);
					}
					else{
						cases[iterateuri-1][iterateurj-1].setIcon(ground2);
					}
				}	
			}
			if(cases[iterateuriTemp][iterateurjTemp].getIcon()==ground1_icone_wall_max){
			cases[iterateuriTemp][iterateurjTemp].setIcon(ground1_wall_max);//update de l'ancienne case
			}
			else{
			cases[iterateuriTemp][iterateurjTemp].setIcon(ground1);//update de l'ancienne case
			}

		}

		}
		
	}
	public void deplacementGauche(){

		
		if(iterateurj !=0 && (!(cases[iterateuri][iterateurj-1].getBackground()==Color.BLACK)) && (!(cases[iterateuri][iterateurj-1].getIcon()==ground1_icone_zombie_head)) && (!(cases[iterateuri][iterateurj-1].getIcon()==ground2_icone_zombie_head))&& (!(cases[iterateuri-1][iterateurj-1].getIcon()==ground1_icone_zombie)) && (!(cases[iterateuri-1][iterateurj-1].getIcon()==ground2_icone_zombie))) {  // si la case est en bout gauche de la map ne fait rien sinon deplace a gauche
			
			iterateuriTemp = iterateuri;
			iterateurjTemp = iterateurj;
			
			partie.choix(2);
			
		
			
			iterateuri = partie.getListPersonnage().get(0).getPositionY();
			iterateurj = partie.getListPersonnage().get(0).getPositionX();	
			
			if(cases[iterateuri][iterateurj].getIcon()==ground1){//si case destination est ground 1
				cases[iterateuri][iterateurj].setIcon(ground1_icone);
				if(cases[iterateuri-1][iterateurj].getDisabledIcon()==wall_beg){//si la case du dessus est un mur on place la tete en consequence
					cases[iterateuri-1][iterateurj].setIcon(wall_beg_head);
					cases[iterateuri-1][iterateurj].setDisabledIcon(wall_beg_head);
					if(cases[iterateuri-1][iterateurj+1].getDisabledIcon()==wall_beg_head){
						cases[iterateuri-1][iterateurj+1].setDisabledIcon(wall_beg);//update de l'ancienne case
					}
					else{
						cases[iterateuri-1][iterateurj+1].setIcon(ground1);//update de l'ancienne case
					}
				}
				else{//sinon on place la tete avec ground2
					cases[iterateuri-1][iterateurj].setIcon(ground2_head);
					cases[iterateuri-1][iterateurj+1].setIcon(ground1);
					if(cases[iterateuri-1][iterateurj+1].getDisabledIcon()==wall_beg_head){
						cases[iterateuri-1][iterateurj+1].setDisabledIcon(wall_beg);//update de l'ancienne case
					}
					else{
						cases[iterateuri-1][iterateurj+1].setIcon(ground1);//update de l'ancienne case
					}
				}
				if(cases[iterateuriTemp][iterateurjTemp].getIcon()==ground2_icone_wall_max){
					cases[iterateuriTemp][iterateurjTemp].setIcon(ground2_wall_max);//update de l'ancienne case
				}
				else{
					cases[iterateuriTemp][iterateurjTemp].setIcon(ground2);//update de l'ancienne case
				}
			}
			if(cases[iterateuri][iterateurj].getIcon()==ground2){//si case destination est ground 1
				cases[iterateuri][iterateurj].setIcon(ground2_icone);
				if(cases[iterateuri-1][iterateurj].getDisabledIcon()==wall_beg){//si la case du dessus est un mur on place la tete en consequence
					cases[iterateuri-1][iterateurj].setIcon(wall_beg_head);
					cases[iterateuri-1][iterateurj].setDisabledIcon(wall_beg_head);
					if(cases[iterateuri-1][iterateurj+1].getDisabledIcon()==wall_beg_head){
						cases[iterateuri-1][iterateurj+1].setDisabledIcon(wall_beg);//update de l'ancienne case
					}
					else{
						cases[iterateuri-1][iterateurj+1].setIcon(ground2);//update de l'ancienne case
					}
									}
					else{//sinon on place la tete avec ground2
						cases[iterateuri-1][iterateurj].setIcon(ground1_head);
						cases[iterateuri-1][iterateurj+1].setIcon(ground2);
						if(cases[iterateuri-1][iterateurj+1].getDisabledIcon()==wall_beg_head){
							cases[iterateuri-1][iterateurj+1].setDisabledIcon(wall_beg);//update de l'ancienne case
						}
						else{
							cases[iterateuri-1][iterateurj+1].setIcon(ground2);//update de l'ancienne case
						}
					}
					if(cases[iterateuriTemp][iterateurjTemp].getIcon()==ground1_icone_wall_max){
						cases[iterateuriTemp][iterateurjTemp].setIcon(ground1_wall_max);//update de l'ancienne case
					}
					else{
						cases[iterateuriTemp][iterateurjTemp].setIcon(ground1);//update de l'ancienne case
					}
			}

			if(cases[iterateuri][iterateurj].getIcon()==ground1_wall_max){// pareil qu'au dessus mais pour ground2
				cases[iterateuri][iterateurj].setIcon(ground1_icone_wall_max);//on deplace le perso avec sol ground1
				if(cases[iterateuri-1][iterateurj].getDisabledIcon()==wall_beg){//si la case du dessus est un mur on place la tete en consequence
					cases[iterateuri-1][iterateurj].setIcon(wall_beg_head);
					cases[iterateuri-1][iterateurj].setDisabledIcon(wall_beg_head);
					if(cases[iterateuri-1][iterateurj+1].getDisabledIcon()==wall_beg_head){
						cases[iterateuri-1][iterateurj+1].setIcon(wall_beg);
						cases[iterateuri-1][iterateurj+1].setDisabledIcon(wall_beg);
					}
					else{
						cases[iterateuri-1][iterateurj+1].setIcon(ground1);
					}
				}
				else{//sinon on place la tete avec ground2
					cases[iterateuri-1][iterateurj].setIcon(ground2_head);
					cases[iterateuri-1][iterateurj+1].setIcon(ground1);
					if(cases[iterateuri-1][iterateurj+1].getDisabledIcon()==wall_beg_head){
						cases[iterateuri-1][iterateurj+1].setIcon(wall_beg);
						cases[iterateuri-1][iterateurj+1].setDisabledIcon(wall_beg);
					}
					else{
						cases[iterateuri-1][iterateurj+1].setIcon(ground1);
					}
				}
				if(cases[iterateuriTemp][iterateurjTemp].getIcon()==ground2_icone_wall_max){
				cases[iterateuriTemp][iterateurjTemp].setIcon(ground2_wall_max);//update de l'ancienne case
				}
				else{
				cases[iterateuriTemp][iterateurjTemp].setIcon(ground2);//update de l'ancienne case
				}

			}


							
		if(cases[iterateuri][iterateurj].getIcon()==ground2_wall_max){// pareil qu'au dessus mais pour ground2
			cases[iterateuri][iterateurj].setIcon(ground2_icone_wall_max);//on deplace le perso avec sol ground1
			if(cases[iterateuri-1][iterateurj].getIcon()==wall_beg){//si la case du dessus est un mur on place la tete en consequence
				cases[iterateuri-1][iterateurj].setIcon(wall_beg_head);
				cases[iterateuri-1][iterateurj].setDisabledIcon(wall_beg_head);
				if(cases[iterateuri-1][iterateurj+1].getDisabledIcon()==wall_beg_head){
					cases[iterateuri-1][iterateurj+1].setIcon(wall_beg);
					cases[iterateuri-1][iterateurj+1].setDisabledIcon(wall_beg);
				}
				else{
					cases[iterateuri-1][iterateurj+1].setIcon(ground2);
				}
			}
			else{//sinon on place la tete avec ground2
				cases[iterateuri-1][iterateurj].setIcon(ground1_head);
				cases[iterateuri-1][iterateurj+1].setIcon(ground2);
				if(cases[iterateuri-1][iterateurj+1].getDisabledIcon()==wall_beg_head){
					cases[iterateuri-1][iterateurj+1].setIcon(wall_beg);
					cases[iterateuri-1][iterateurj+1].setDisabledIcon(wall_beg);
				}
				else{
					cases[iterateuri-1][iterateurj+1].setIcon(ground2);
				}
			}
			if(cases[iterateuriTemp][iterateurjTemp].getIcon()==ground1_icone_wall_max){
			cases[iterateuriTemp][iterateurjTemp].setIcon(ground1_wall_max);//update de l'ancienne case
			}
			else{
			cases[iterateuriTemp][iterateurjTemp].setIcon(ground1);//update de l'ancienne case
			}

		}
		}
		
	}
	public void deplacementHaut(){

		
		if(iterateuri>1 && (!(cases[iterateuri-1][iterateurj].getBackground()==Color.BLACK)) && (!(cases[iterateuri-1][iterateurj].getIcon()==ground1_icone_zombie)) && (!(cases[iterateuri-1][iterateurj].getIcon()==ground2_icone_zombie)) && (!(cases[iterateuri-2][iterateurj].getIcon()==ground1_icone_zombie)) && (!(cases[iterateuri-2][iterateurj].getIcon()==ground2_icone_zombie))) { // si la case est en bout haut de la map ne fait rien sinon deplace en haut
			
			iterateuriTemp = iterateuri;
			iterateurjTemp = iterateurj;
			
			partie.choix(0);
			
			
			iterateuri = partie.getListPersonnage().get(0).getPositionY();
			iterateurj = partie.getListPersonnage().get(0).getPositionX();	
			
			if(cases[iterateuri][iterateurj].getIcon()==ground1_head){//si la case du dessus est un ground1
				cases[iterateuri][iterateurj].setIcon(ground1_icone);//on monte
				if(cases[iterateuri-1][iterateurj].getIcon()==ground2){//placement de la tete en fonction de mur/sol
					cases[iterateuri-1][iterateurj].setIcon(ground2_head);
				}
				if(cases[iterateuri-1][iterateurj].getDisabledIcon()==wall_beg){
					cases[iterateuri-1][iterateurj].setIcon(wall_beg_head);
					cases[iterateuri-1][iterateurj].setDisabledIcon(wall_beg_head);
				}
				if(cases[iterateuriTemp][iterateurjTemp].getIcon()==ground2_icone_wall_max){
					cases[iterateuriTemp][iterateurjTemp].setIcon(ground2_wall_max);
				}
				else{
					cases[iterateuriTemp][iterateurjTemp].setIcon(ground2);
				}
			}
			if(cases[iterateuri][iterateurj].getIcon()==ground2_head){//pareil mais pour sol ground2
				cases[iterateuri][iterateurj].setIcon(ground2_icone);
				if(cases[iterateuri-1][iterateurj].getIcon()==ground1){
				cases[iterateuri-1][iterateurj].setIcon(ground1_head);
				}
				if(cases[iterateuri-1][iterateurj].getDisabledIcon()==wall_beg){
					cases[iterateuri-1][iterateurj].setIcon(wall_beg_head);
					cases[iterateuri-1][iterateurj].setDisabledIcon(wall_beg_head);
				}
				if(cases[iterateuriTemp][iterateurjTemp].getIcon()==ground1_icone_wall_max){
					cases[iterateuriTemp][iterateurjTemp].setIcon(ground1_wall_max);
				}
				else{
					cases[iterateuriTemp][iterateurjTemp].setIcon(ground1);
				}
			}
		}
		
		
	}
	public void deplacementBas(){

		
		if(iterateuri!= largeurMap-1 && (!(cases[iterateuri+1][iterateurj].getBackground()==Color.BLACK)) && (!(cases[iterateuri+1][iterateurj].getIcon()==ground1_icone_zombie)) && (!(cases[iterateuri+1][iterateurj].getIcon()==ground2_icone_zombie)) && (!(cases[iterateuri+1][iterateurj].getIcon()==ground1_icone_zombie_head)) && (!(cases[iterateuri+1][iterateurj].getIcon()==ground2_icone_zombie_head))) {  // si la case est en bout bas de la map ne fait rien sinon deplace en bas
			
			iterateuriTemp = iterateuri;
			iterateurjTemp = iterateurj;
			
			partie.choix(1);
			
			
			iterateuri = partie.getListPersonnage().get(0).getPositionY();
			iterateurj = partie.getListPersonnage().get(0).getPositionX();
			
			if(cases[iterateuri][iterateurj].getIcon()==ground1){
				cases[iterateuri][iterateurj].setIcon(ground1_icone);
				cases[iterateuri-1][iterateurj].setIcon(ground2_head);
				if(cases[iterateuriTemp-1][iterateurjTemp].getDisabledIcon()==wall_beg_head){
					cases[iterateuriTemp-1][iterateurjTemp].setDisabledIcon(wall_beg);
				}
				else{
					cases[iterateuriTemp-1][iterateurjTemp].setIcon(ground1);
				}
			}
			if(cases[iterateuri][iterateurj].getIcon()==ground2){
				cases[iterateuri][iterateurj].setIcon(ground2_icone);
				cases[iterateuri-1][iterateurj].setIcon(ground1_head);
				if(cases[iterateuriTemp-1][iterateurjTemp].getDisabledIcon()==wall_beg_head){
					cases[iterateuriTemp-1][iterateurjTemp].setDisabledIcon(wall_beg);
				}
				else{
					cases[iterateuriTemp-1][iterateurjTemp].setIcon(ground2);
				}
			}		
			if(cases[iterateuri][iterateurj].getIcon()==ground1_wall_max){
				cases[iterateuri][iterateurj].setIcon(ground1_icone_wall_max);
				cases[iterateuri-1][iterateurj].setIcon(ground2_head);
				if(cases[iterateuriTemp-1][iterateurjTemp].getDisabledIcon()==wall_beg_head){
					cases[iterateuriTemp-1][iterateurjTemp].setDisabledIcon(wall_beg);
				}
				else{
					cases[iterateuriTemp-1][iterateurjTemp].setIcon(ground1);
				}
			}
			if(cases[iterateuri][iterateurj].getIcon()==ground2_wall_max){
				cases[iterateuri][iterateurj].setIcon(ground2_icone_wall_max);
				cases[iterateuri-1][iterateurj].setIcon(ground1_head);
				if(cases[iterateuriTemp-1][iterateurjTemp].getDisabledIcon()==wall_beg_head){
					cases[iterateuriTemp-1][iterateurjTemp].setDisabledIcon(wall_beg);
				}
				else{
					cases[iterateuriTemp-1][iterateurjTemp].setIcon(ground2);
				}
			}	
		}
		
	}
	
	
	public void center (int w, int h){
		
		this.setBounds((tk.getScreenSize().width-w)/2,(tk.getScreenSize().height-h)/2,w,h);
		
	}
	
	public void barreMenu () {  // Barre de menu permettant de reset la map
		
		JMenuBar barre = new JMenuBar();
		this.setJMenuBar(barre);
		
		JMenu menu1 = new JMenu("Game");
		barre.add(menu1);
								
		JMenuItem menu1Item1 = new JMenuItem("Menu");
		//menu1Item1.addActionListener(new Menu(0));	
		JMenuItem menu1Item2 = new JMenuItem("Map");
		//menu1Item2.addActionListener(new Menu(1));
		menu1.add(menu1Item1);
		menu1.add(menu1Item2);
		
	}
	
	
	public void afficherMur() {
		
		String str = "";
		
		for(int i=0;i<partie.getListMur().size();i++) {
			str += partie.getListMur().get(i);
		}
		
		box.setText(str);
		
	}
	
	public static void main(String[] args){

		AffichageMap lol = new AffichageMap("Mino",1);
		



	    }
	}

		
	
	
		
	
	
