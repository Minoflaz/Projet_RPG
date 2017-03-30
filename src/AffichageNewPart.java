
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.*;





public class AffichageNewPart extends JFrame {
		
	Toolkit tk = Toolkit.getDefaultToolkit();
	
	private JTextField fieldNom;
	private int choixClasse;
    
	JTextArea description= new JTextArea();
    
    JPanel fondText= new JPanel();
	
    ImageIcon bouton_new= new ImageIcon("textures/button_new.png");
	ImageIcon bouton_charger= new ImageIcon("textures/button_charger.png");
	ImageIcon bouton_opt= new ImageIcon("textures/button_opt.png");
	ImageIcon bouton_quit= new ImageIcon("textures/button_quit.png");
	
    	public AffichageNewPart(){

    		choixClasse = 0;
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    		this.setTitle("Projet_RPG");
			this.setCursor(new Cursor(12));
			this.add(centre());	
			this.center(tk.getScreenSize().width,tk.getScreenSize().height);
			//this.setExtendedState(JFrame.MAXIMIZED_BOTH);
			this.setVisible(true);
	    }
    	public void center (int w, int h){
    		
    		this.setBounds((tk.getScreenSize().width-w)/2,(tk.getScreenSize().height-h)/2,w,h);
    		
    	}
    	public JLayeredPane centre (){
	        JLayeredPane centre= new JLayeredPane();
	        
	        
	        
	        JPanel fond= new JPanel();//bandeaux noirs
	        int x=tk.getScreenSize().width;
	        int y=tk.getScreenSize().height;
	        fond.setBounds((tk.getScreenSize().width-x)/2,(tk.getScreenSize().height-y)/2,x,y);
	        fond.setBackground(Color.BLACK);
	        centre.add(fond);
	        
	        
	        
	        JLabel choisis= new JLabel();//panneau "choisis ta destinee"
	        int xCh=500;
	        int yCh=150;
	        choisis.setBounds((tk.getScreenSize().width-xCh)/2+(tk.getScreenSize().width-xCh)/5,(tk.getScreenSize().height-yCh)/12,xCh,yCh);
	        choisis.setIcon(new ImageIcon("textures/choisis.png"));
	        
	        
	        
	        
	        JButton go= new JButton();//bouton "go"
	        int xGo=200;
	        int yGo=100;
	        go.setBounds((tk.getScreenSize().width-xGo)/2+(tk.getScreenSize().width-xGo)/3,(tk.getScreenSize().height-yGo)/2+(tk.getScreenSize().height-yGo)/3,xGo,yGo);
	        go.setIcon(new ImageIcon("textures/go.png"));
	        go.addActionListener(new Choix(5));

	        
	        JLabel panelChoix= new JLabel();//fond
	        int w=tk.getScreenSize().width/2+tk.getScreenSize().width/3;
	        int h=tk.getScreenSize().height;
	        panelChoix.setBounds((tk.getScreenSize().width-w)/2,(tk.getScreenSize().height-h)/2,w,h);
	        panelChoix.setIcon(new ImageIcon("textures/fond_menu.png"));
	        
	        
	        
	        JPanel barreSpe= new JPanel(new GridLayout(1,5));//grille des specialisations
	        int xBarre=500;
	        int yBarre=104;
	        barreSpe.setBounds((tk.getScreenSize().width-xBarre)/2+(tk.getScreenSize().width-xBarre)/4,(tk.getScreenSize().height-yBarre)/4,xBarre,yBarre);
	        
	        
	        
	        JPanel barreNom= new JPanel(new GridLayout(1,2));//barre du pseudo
	        int xNom=500;
	        int yNom=40;
	        barreNom.setBounds((tk.getScreenSize().width-xNom)/2,(tk.getScreenSize().height-yNom)/2+(tk.getScreenSize().height-yNom)/3,xNom,yNom);
	        JButton nom=new JButton("Pseudo du perso");
	        nom.setEnabled(false);
	        nom.setFont(new Font("verdana", Font.BOLD, 20));
	        fieldNom= new JTextField();
	        fieldNom.setFont(new Font("verdana", Font.BOLD, 20));
	        fieldNom.setForeground(Color.BLACK);
	        
	        
	        
	        barreNom.add(nom);
	        barreNom.add(fieldNom);
	        
	        
	        
	        JButton fleau= new JButton();
	        fleau.setIcon(new ImageIcon("textures/icone_fleau.png"));
	        fleau.addActionListener(new Choix(1));
	        
	        
	        
	        JButton ingenieur= new JButton();
	        ingenieur.setIcon(new ImageIcon("textures/icone_ingenieur.png"));
	        ingenieur.addActionListener(new Choix(2));
	        
	        
	        
	        JButton tank= new JButton();
	        tank.setIcon(new ImageIcon("textures/icone_tank.png"));
	        tank.addActionListener(new Choix(3));
	        
	        
	        
	        JButton distance= new JButton();
	        distance.setIcon(new ImageIcon("textures/icone_distance.png"));
	        distance.addActionListener(new Choix(4));	        	        	        
	        
	        barreSpe.add(fleau);
	        barreSpe.add(ingenieur);
	        barreSpe.add(tank);
	        barreSpe.add(distance);
	        

	        
	        
	        int xDes=600;//panneau des descriptions
	        int yDes=200;
	        description.setBounds((tk.getScreenSize().width-xDes)/2+(tk.getScreenSize().width-xDes)/8,(tk.getScreenSize().height-yDes)/2,xDes,yDes);
	        description.setEditable(false);
	        description.setFont(new Font("Verdana", Font.BOLD, 19));
	        description.setForeground(Color.BLACK);        
	        //description.setBackground(Color.LIGHT_GRAY);
	        Border roundedBorder = new LineBorder(Color.LIGHT_GRAY, 12, true);
	        description.setBorder(roundedBorder);
	        description.setOpaque(false);
	        description.setVisible(false);
	        
	        
	        
	        int xFT=580;//texte du panneau des descriptions
	        int yFT=180;
	        fondText.setBounds((tk.getScreenSize().width-xFT)/2+(tk.getScreenSize().width-xFT)/8,(tk.getScreenSize().height-yFT)/2,xFT,yFT);
	        fondText.setBackground(Color.WHITE);
	        fondText.setVisible(false);
	        
	        
	        
	        centre.add(barreNom);
	        centre.setLayer(barreNom, 1004);//1
	        centre.add(choisis);
	        centre.setLayer(choisis, 1002);//1
	        centre.add(go);
	        centre.setLayer(go, 1003);//1
	        centre.add(fondText);
	        centre.setLayer(fondText, 999);//4     
	        centre.add(description);
	        centre.setLayer(description, 1001);//2
	        centre.add(panelChoix);
	        centre.setLayer(panelChoix, 998);//5
	        centre.add(barreSpe);
	        centre.setLayer(barreSpe, 1000);//3

	        
	        
	        
	        centre.setBackground(Color.BLACK);
	        
	        
	        
	        setVisible(true);
	        return centre;
	    }
		
    	private class Choix implements ActionListener {

    		private int choix;
    		
    		public Choix(int choix) {
    			this.choix = choix;
    		}
			public void actionPerformed(ActionEvent arg0) {
				
			
				switch(choix) {
				
				case 1 :
					description.setVisible(true);
					description.setText("Le fleau  c'est un petit  peu le rocky de  la  survie sa force \nest  son  atout,  il  fonce  dans  le  tas  sans  se  poser  de  \nquestion, d'ailleurs, tel  est  son  defaut  son  intelligence \nlaisse a desirer... \n\n\nAvantage: 1 point de caracteristique = 2 force\nMalus: 2 points de caracteristique=1 intelligence");
					fondText.setVisible(true);
					description.setFont(new Font("Verdana", Font.BOLD, 17));
					choixClasse = choix;
					
					break;
					
				case 2 :
					description.setVisible(true);
					description.setText("Le distance  c'est l'adepte  des armes a grande  portee,  un \nlâche en quoi, mais ca lui permet de survivre et d'être bien \nbadass avec  son arc  ou  son  arbalète et  son  carquois de \nflèches bien taillees. Le problème ? sa resistance, au corps \na  corps  c'est  un  chewing-gum  sur  un  trottoir  parisien \n\nAvantage: 1 point de caracteristique = 2 agilite\nMalus: 2 points de caracteristique=1 resistance ");
					fondText.setVisible(true);
					description.setFont(new Font("Verdana", Font.BOLD, 17));
					choixClasse = choix;
					
					break;	
					
				case 3 :
					description.setVisible(true);
					description.setText("Le tank,  aka  l'encaisseur de  coups,  si  vous  cherchez  un \nbouclier pour vous defendre contre votre beau-père c'est le \ntype  parfait  En  ces  temps  post-apocalyptiques  c'est  un \navantage  consequent  c'est  dommage  que  son  agilite en \nprenne un sacre coup !\n\nAvantage: 1 point de caracteristique = 2 resistance\nMalus: 2 points de caracteristique=1 agilite ");
					fondText.setVisible(true);
					description.setFont(new Font("Verdana", Font.BOLD, 17));
					choixClasse = choix;
					
					break;
					
					
				case 4 :
					description.setFont(new Font("Verdana", Font.BOLD, 17));
					description.setVisible(true);
					description.setText("L'ingenieur bin... C'est la tronche du groupe quoi ! Il utilise \nsa  matière  grise  pour  elaborer  des  pièges  et  trouve  la \nnourriture plus facilement  que les autres, seulement, il n'a \nrien dans  les  bras, il sera  donc très vulnerable  au corps a \ncorps!\n\nAvantage: 1 point de caracteristique = 2 intelligence\nMalus: 2 points de caracteristique=1 force");
					fondText.setVisible(true);
					choixClasse = choix;
					
					break;
									
				case 5 :
					
					String nom = fieldNom.getText();
					
					if(choixClasse!=0) {
						
						AffichageMap lol = new AffichageMap(nom,choixClasse);
						setVisible(false);
					}
					
					else {
						description.setFont(new Font("Verdana", Font.BOLD, 17));
						description.setVisible(true);
						description.setText("Vous n'avez pas choisi de classe !");
						fondText.setVisible(true);
					} 
				
			} 
			}
    	}
	
	public static void main(String[] args) {
		
		AffichageNewPart lol = new AffichageNewPart();
		
		
		
		
		
	}
}
