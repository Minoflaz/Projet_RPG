import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;


public class Joueur extends Personnage {

			
	public Joueur(String nom) {
		
		super(nom);
		
	}
	
	public Joueur(String nom,int positionX,int positionY) {
		
		super(nom,positionX,positionY);
		
	}
	
	public Joueur(Joueur joueur) {
		
		super(joueur);
	}
	
	
	
	
}
