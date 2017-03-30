
public class Arme extends Objet implements java.io.Serializable{
		
	
	private int degat;  
	
	private int lvl;
	
	private Statistiques stat;
	
	private int portee;   
	
	
	public Arme(){}
	
	public Arme(String nom, int degat, int lvl, int portee, Statistiques stat) {
		
		super(nom);
		this.degat = degat;
		this.lvl = lvl;
		this.portee = portee;
		this.stat = new Statistiques(stat);
	}
	
	public boolean equals(Object o) {  // Permet de comparer deux armes entre elles si elles ont le meme nom elles sont identiques
		
		if(o instanceof Arme){
			if(((Arme)o).getNom().equals(this.getNom()) /*&& this.degat == ((Arme)o).getDegat() && this.lvl == ((Arme)o).getDegat() && this.getForce() == ((Arme)o).getForce() && this.getAgilite() == ((Arme)o).getAgilite() && this.getIntelligence() == ((Arme)o).getIntelligence() && this.getResistance() == ((Arme)o).getResistance()*/ )
				return true;
		}
		
		else
			return false;
		
		return false;
		
	}
	
			
	
	public int getPortee() {
		
		return this.portee;
	}

	public int getDegat() {
		
		return this.degat;
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
	
	public String toString() {
		
		String str = "";
		
		str += this.getNom() + "\n\n" + degat + " dmg\n" + "Portée : " + portee + "\n\nForce +" + stat.getForce() + "\nAgilite +" + stat.getAgilite() + "\nIntell +" + stat.getIntelligence() + "\nRési +" + stat.getResistance() + "\n\nLvl " + lvl;
		
		return str;
	}
	
	/*
	public static void main(String []args) {  // Test de la classe
		
		
		Objet epee = new Arme("rapiere",15,false);
		Objet epee2 = new Arme("rapiere",15,false);
		Objet arc = new Arme("arbatus",13,true);
		
		System.out.println(epee.equals(epee2));
		
		
	}*/
	
}



