import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Tank extends Joueur {
	
	private int ratioResistance;
	
	public Tank(String nom) {
		
		super(nom);
		this.ratioResistance = 2;
	}
	
	public Tank(String nom,int positionX,int positionY) {
		
		super(nom,positionX,positionY);
		this.ratioResistance = 2;
	}

	public void modifResistance(int ajout) {
		
		this.getStat().modifResistance(ratioResistance*ajout);
	}
	
	/* Methode permettant de restaurer une sauvegarde de personnage a partir de son fichier de sauvegarde
	 * 
	 * Renvoi ce même personnage si le fichier de sauvegarde de celui ci existe
	 * 
	 * Sinon renvoi null
	 */
	
	public Tank restaurer() {
		
		ObjectInputStream objInputStream = null;
		Tank persoRestaure = null;
		String idPerso = "" + this.getId();
		String fileName = "save/personnage" + idPerso + ".ser";
		
		try {
			
			FileInputStream fichier = new FileInputStream(fileName);
			objInputStream = new ObjectInputStream(fichier);
			persoRestaure = (Tank) objInputStream.readObject();
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
