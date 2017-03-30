
public class Armure extends Objet implements java.io.Serializable {
	
	private int armure;
	
	private int lvl;
	
	private Statistiques stat;
	
	public Armure() {
		
	}
	
	
	public Armure(String nom,int armure,int lvl,Statistiques stat) {
		
		super(nom);
		this.armure = armure;
		this.lvl = lvl;
		this.stat = new Statistiques(stat);
		
	}
	
	public int getArmure() {
		
		return this.armure;
	}
	
	public int getLvl() {
		
		return this.lvl;
	}
	
	public int getForce(){
		
		return this.stat.getForce();
	}
	
	public int getAgilite(){
		
		return this.stat.getAgilite();
	}
	
	public int getResistance(){
	
		return this.stat.getResistance();
	}
	
	public int getIntelligence(){
	
		return this.stat.getIntelligence();
	}
	
	public boolean equals(Object o) {  // Permet de comparer deux armes entre elles si elles ont le meme nom elles sont identiques
		
		if(o instanceof Armure){
			if(((Armure)o).getNom().equals(this.getNom()))
				return true;
		}
		
		else
			return false;
		
		return false;
		
	}
	
	public String toString() {
		
		String str = "";
		
		str += this.getNom() + "\n\n" + "Armure +" + armure  + "\n\nForce +" + stat.getForce() + "\nAgilite +" + stat.getAgilite() + "\nIntell +" + stat.getIntelligence() + "\nRési +" + stat.getResistance() + "\n\nLvl " + lvl;
		
		return str;
	}

}
