/*import javax.media.CannotRealizeException;
import javax.media.IncompatibleSourceException;
import javax.media.NoDataSinkException;
import javax.media.NoDataSourceException;
import javax.media.NoPlayerException;*/
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;




import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Random;

class AffichageMenu extends JFrame{
	Toolkit tk = Toolkit.getDefaultToolkit();
	private JTextArea box;
	ImageIcon bouton_new= new ImageIcon("Medias/button_new.png");
	ImageIcon bouton_charger= new ImageIcon("Medias/button_charger.png");
	ImageIcon bouton_opt= new ImageIcon("Medias/button_opt.png");
	ImageIcon bouton_quit= new ImageIcon("Medias/button_quit.png");
	
    	public AffichageMenu(){
			//this.setUndecorated(true);
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    		this.setTitle("Projet_RPG");
			this.setCursor(new Cursor(12));
			this.add(nord(),BorderLayout.NORTH);
			this.add(centre(),BorderLayout.SOUTH);	
			this.setBackground(Color.BLUE);
			this.center(1000,1000);
			//this.setExtendedState(JFrame.MAXIMIZED_BOTH);
			this.setVisible(true);
	    }
    	public void center (int w, int h){
    		
    		this.setBounds((tk.getScreenSize().width-w)/2,(tk.getScreenSize().height-h)/2,w,h);
    		
    	}

    	public JPanel nord (){
    		Dimension dimension=new Dimension(500,500);
	        JPanel nord =new JPanel();
	        JLabel image= new JLabel();	        
	        ImageIcon icon=new ImageIcon("Medias/header_background.png");
	        image.setIcon(icon);
	        nord.add(image);
	        nord.setBackground(Color.BLACK);
			nord.setMaximumSize(dimension);
			nord.setMinimumSize(dimension);
			nord.setPreferredSize(dimension);
			Border border = image.getBorder();
			Border margin = new EmptyBorder(0,0,0,0);
			image.setBorder(new CompoundBorder(border, margin));
	        setVisible(true);
	        return nord;
	    }
    	public JPanel centre (){
    		Dimension dimension=new Dimension(500,500);
	        JPanel centre =new JPanel();
	        centre.setLayout(new OverlayLayout(centre));
	        JLabel image= new JLabel();
	        ImageIcon icon=new ImageIcon("Medias/footer_background.png");
	        image.setIcon(icon);
	        image.setLayout(new OverlayLayout(image));
	        centre.add(image);
	        JPanel choix= new JPanel(new GridLayout(4,1));
	        JButton newPart= new JButton(bouton_new);
	        newPart.setBackground(Color.BLACK);
	        newPart.setBorderPainted(false);
			Border border2 = newPart.getBorder();
			Border margin2 = new EmptyBorder(0,0,0,0);
			choix.setBorder(new CompoundBorder(border2, margin2));
	        JButton charger= new JButton(bouton_charger);
	        charger.setBackground(Color.BLACK);
	        charger.setBorderPainted(false);
	        JButton options= new JButton(bouton_opt);
	        options.setBackground(Color.BLACK);
	        options.setBorderPainted(false);
	        JButton quitter= new JButton(bouton_quit);
	        quitter.setBackground(Color.BLACK);
	        quitter.setBorderPainted(false);
	        quitter.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                AffichageMenu.this.processWindowEvent(
	                        new WindowEvent(
	                                AffichageMenu.this, WindowEvent.WINDOW_CLOSING));
	            }
	        });
	        quitter.setSize(50,200);
	        centre.setBackground(Color.BLACK);
	        choix.setBackground(Color.WHITE);
	        choix.add(newPart);
	        choix.add(charger);
	        choix.add(options);
	        choix.add(quitter);
			Border border1 = choix.getBorder();
			Border margin1 = new EmptyBorder(0,0,0,0);
			choix.setBorder(new CompoundBorder(border1, margin1));
	        image.add(choix);
	        choix.setSize(1000,1000);
			choix.setMaximumSize(dimension);
			choix.setMinimumSize(dimension);
			choix.setPreferredSize(dimension);
	        setVisible(true);
	        return centre;
	    }
    	public static void main(String args[]) /*throws NoDataSourceException, IncompatibleSourceException, CannotRealizeException, NoDataSinkException, NoPlayerException, IOException, InterruptedException*/
	    {
    				//new Intro();
	                new AffichageMenu();
	             //   new Music();
	    }
	}