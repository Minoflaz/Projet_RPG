
public class Monstre extends PNJ{

	public Monstre(String nom) {
		
		super(nom);
	}
	
	public Monstre(String nom,int x,int y) {
		
		super(nom,x,y);
	}
	
	public String toString() {
		
		return getSante() + "/" + getSanteMax() + " HP\n" + "Lvl : " + getLvl();
	}
}
