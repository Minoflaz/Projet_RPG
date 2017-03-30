import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


public class Fleau extends Joueur {

	private int ratioForce;
	
	public Fleau(String nom) {
		
		super(nom);
		this.ratioForce = 2;
		
	}
	
	public Fleau(String nom,int positionX,int positionY) {
		
		super(nom,positionX,positionY);
		this.ratioForce = 2;
	}
	
	public Fleau(Fleau fleau) {
		
		super(fleau);
	}
	
	public void modifForce(int ajout) {
		
		this.getStat().modifForce(ratioForce*ajout);
	}
	
	/* Methode permettant de restaurer une sauvegarde de personnage a partir de son fichier de sauvegarde
	 * 
	 * Renvoi ce même personnage si le fichier de sauvegarde de celui ci existe
	 * 
	 * Sinon renvoi null
	 */
	
	public Fleau restaurer() {
		
		ObjectInputStream objInputStream = null;
		Fleau persoRestaure = null;
		String idPerso = "" + this.getId();
		String fileName = "save/personnage" + idPerso + ".ser";
		
		try {
			
			FileInputStream fichier = new FileInputStream(fileName);
			objInputStream = new ObjectInputStream(fichier);
			persoRestaure = (Fleau) objInputStream.readObject();
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
	
	public String toString() {
		
		String str ="";
		
		str += super.toString() + "\n" + this.getClass().getName();
		
		return str;
		
	}
	
	
	public static  void main (String [] args) {
		
		Fleau perso1 = new Fleau("Mino");
		perso1.setSante(80);
		perso1.sauvegarder(); 
		
		Fleau perso2 = new Fleau(perso1.restaurer());
		
		System.out.println(perso1 + "\n");
		System.out.println(perso2);
		
	
		
		
	

	}
	
	
}
