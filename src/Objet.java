
public class Objet implements java.io.Serializable {
	
	private String nom;
	
	public Objet() {}

	public Objet(String nom) {
		
		this.nom = nom;
	}


	public String getNom() {
		
		return this.nom;
	}
	
	public String toString() {
		
		
		return this.nom;
	}
	
}
