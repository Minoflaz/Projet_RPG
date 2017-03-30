import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


public class Distance extends Joueur {
	
	private int ratioAgilite;
	
	public Distance(String nom) {
		
		super(nom);
		this.ratioAgilite = 2;
	}
	
	public Distance(String nom,int positionX,int positionY) {
		
		super(nom,positionX,positionY);
		this.ratioAgilite = 2;
	}
	
	public void modifAgilite(int ajout) {
		
		this.getStat().modifAgilite(ratioAgilite*ajout);
	}
	
	/* Methode permettant de restaurer une sauvegarde de personnage a partir de son fichier de sauvegarde
	 * 
	 * Renvoi ce même personnage si le fichier de sauvegarde de celui ci existe
	 * 
	 * Sinon renvoi null
	 */
	
	public Distance restaurer() {
		
		ObjectInputStream objInputStream = null;
		Distance persoRestaure = null;
		String idPerso = "" + this.getId();
		String fileName = "save/personnage" + idPerso + ".ser";
		
		try {
			
			FileInputStream fichier = new FileInputStream(fileName);
			objInputStream = new ObjectInputStream(fichier);
			persoRestaure = (Distance) objInputStream.readObject();
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
