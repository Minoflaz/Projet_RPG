
public class Statistiques implements java.io.Serializable {
	
	private int force;
	private int agilite;
	private int intelligence;
	private int resistance;
	
	public Statistiques(){
		
		this.force = 0;
		this.agilite = 0;
		this.intelligence = 0;
		this.resistance = 0;
	}
	
	public Statistiques(int force, int agilite, int intelligence, int resistance){
		
		this.force = force;
		this.agilite = agilite;
		this.intelligence = intelligence;
		this.resistance = resistance;
		
	}
	
	public Statistiques(Statistiques stat) {
		
		this.force = stat.getForce();
		this.agilite = stat.getAgilite();
		this.intelligence = stat.getIntelligence();
		this.resistance = stat.getResistance();
		
	}
	
	//Setter
	public void setForce(int force) { this.force = force; }
	public void setAgilite(int agilite) { this.agilite = agilite; }
	public void setIntelligence(int intelligence) { this.intelligence = intelligence; }
	public void setResistance(int resistance) {this.resistance = resistance; }
	
	//Getter
	public int getForce() {return this.force; }
	public int getAgilite() { return this.agilite; }
	public int getIntelligence() { return this.intelligence; }
	public int getResistance() { return this.resistance; }
	
	//Adder
	public void modifForce(int ajout) { this.force += ajout; }
	public void modifAgilite(int ajout) {this.agilite += ajout; }
	public void modifResistance(int ajout) {this.resistance += ajout; }
	public void modifIntelligence(int ajout) {this.intelligence += ajout; }
	
	public void addStat(int force,int agilite, int resistance,int intelligence) {
		
		this.modifForce(force);
		this.modifAgilite(agilite); 
		this.modifResistance(resistance); 
		this.modifIntelligence(intelligence);
	}
	
	

}
