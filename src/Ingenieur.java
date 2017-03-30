import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


public class Ingenieur extends Joueur {
	
	private int ratioIntelligence;
	
	public Ingenieur(String nom) {
		
		super(nom);
		this.ratioIntelligence = 2;
		
	}
	
	public Ingenieur(String nom,int positionX,int positionY) {
		
		super(nom,positionX,positionY);
		this.ratioIntelligence = 2;
	}
	
	public void modifIntelligence(int ajout) {
		
		this.getStat().modifIntelligence(ratioIntelligence*ajout);
	}
	
	/* Methode permettant de restaurer une sauvegarde de personnage a partir de son fichier de sauvegarde
	 * 
	 * Renvoi ce même personnage si le fichier de sauvegarde de celui ci existe
	 * 
	 * Sinon renvoi null
	 */
	
	public Ingenieur restaurer() {
		
		ObjectInputStream objInputStream = null;
		Ingenieur persoRestaure = null;
		String idPerso = "" + this.getId();
		String fileName = "save/personnage" + idPerso + ".ser";
		
		try {
			
			FileInputStream fichier = new FileInputStream(fileName);
			objInputStream = new ObjectInputStream(fichier);
			persoRestaure = (Ingenieur) objInputStream.readObject();
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
	
}
