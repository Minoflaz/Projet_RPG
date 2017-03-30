import java.util.ArrayList;
import java.util.Random;


public class Stock {
	
	private ArrayList<ArrayList<Arme>> armes;  // ArrayList contenant d'autres ArrayList classees par niveau (La premiere ArrayList aura les armes de lvl 1 etc)
	private ArrayList<ArrayList<Armure>> armures;
	private ArrayList<Consommable> consommables;
	
	
	public Stock() {
		
		armes = stockArme();
		armures = stockArmure();
		consommables = stockConsommable();
	}
	
	
	public ArrayList<ArrayList<Arme>> stockArme() {
		
		int degat = 7;  // les degats minimum commencent a 7
		int plageStat = 4;  // Les stats min commencent a 4
		

		ArrayList<ArrayList<Arme>> touteArmes = new ArrayList<ArrayList<Arme>>();  // Arraylist d'arraylist d'arme retournee
		
		
		String rapiere = "rapiere";  // Les noms sont declares en variables de type String car ils pourront dans l'avenir être manipules (par exemple  : rapiere de feu)
		String couteau = "couteau";
		String baton = "baton";
		String dague = "dague";
		String epee = "epee" ;
		String hache = "hache";
		String lance = "lance";
		String fronde = "fronde";
		String katana = "katana";
		String machette = "machette";
		String sabreLaser = "Sabre Laser";
		
		String arc = "arc";
		String fusil = "fusil";
		String arbalete = "arbalete";
		String lanceRoquette = "lance roquette";
		String flechettes = "flechettes";
		String grenade = "grenade";
		String revolver = "revolver";
		String sniper = "sniper";
		String shuriken = "shuriken";
		String pistoletLaser = "Pistolet Laser";
		String lanceFlamme = "lance flamme";
		
		for(int i=1;i<101;i++) { // Attribution aleatoire des degats et statistiques de l'arme
			
			Random rand = new Random();
			
			ArrayList<Arme> creerArmes = new ArrayList<Arme>();
			
			creerArmes.add(new Arme(rapiere,(rand.nextInt((degat+(5*i)))+degat+(5*i)-5),i,1,new Statistiques(rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4))));
			creerArmes.add(new Arme(couteau,(rand.nextInt((degat+(5*i)))+degat+(5*i)-5),i,1,new Statistiques(rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4))));
			creerArmes.add(new Arme(baton,(rand.nextInt((degat+(5*i)))+degat+(5*i)-5),i,1,new Statistiques(rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4))));
			creerArmes.add(new Arme(dague,(rand.nextInt((degat+(5*i)))+degat+(5*i)-5),i,1,new Statistiques(rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4))));
			creerArmes.add(new Arme(epee,(rand.nextInt((degat+(5*i)))+degat+(5*i)-5),i,1,new Statistiques(rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4))));
			creerArmes.add(new Arme(hache,(rand.nextInt((degat+(5*i)))+degat+(5*i)-5),i,1,new Statistiques(rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4))));
			creerArmes.add(new Arme(lance,(rand.nextInt((degat+(5*i)))+degat+(5*i)-5),i,1,new Statistiques(rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4))));
			creerArmes.add(new Arme(fronde,(rand.nextInt((degat+(5*i)))+degat+(5*i)-5),i,1,new Statistiques(rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4))));
			creerArmes.add(new Arme(katana,(rand.nextInt((degat+(5*i)))+degat+(5*i)-5),i,1,new Statistiques(rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4))));
			creerArmes.add(new Arme(machette,(rand.nextInt((degat+(5*i)))+degat+(5*i)-5),i,1,new Statistiques(rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4))));
			creerArmes.add(new Arme(sabreLaser,(rand.nextInt((degat+(5*i)))+degat+(5*i)-5),i,1,new Statistiques(rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4))));
			
			creerArmes.add(new Arme(arc,(rand.nextInt(degat+5*i)+degat+(5*i)-5),i,2,new Statistiques(rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4))));
			creerArmes.add(new Arme(fusil,(rand.nextInt(degat+5*i)+degat+(5*i)-5),i,2,new Statistiques(rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4))));
			creerArmes.add(new Arme(arbalete,(rand.nextInt(degat+5*i)+degat+(5*i)-5),i,2,new Statistiques(rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4))));
			creerArmes.add(new Arme(lanceRoquette,(rand.nextInt(degat+5*i)+degat+(5*i)-5),i,2,new Statistiques(rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4))));
			creerArmes.add(new Arme(flechettes,(rand.nextInt(degat+5*i)+degat+(5*i)-5),i,2,new Statistiques(rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4))));
			creerArmes.add(new Arme(grenade,(rand.nextInt(degat+5*i)+degat+(5*i)-5),i,2,new Statistiques(rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4))));
			creerArmes.add(new Arme(revolver,(rand.nextInt(degat+5*i)+degat+(5*i)-5),i,2,new Statistiques(rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4))));
			creerArmes.add(new Arme(sniper,(rand.nextInt(degat+5*i)+degat+(5*i)-5),i,2,new Statistiques(rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4))));
			creerArmes.add(new Arme(shuriken,(rand.nextInt(degat+5*i)+degat+(5*i)-5),i,2,new Statistiques(rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4))));
			creerArmes.add(new Arme(pistoletLaser,(rand.nextInt(degat+5*i)+degat+(5*i)-5),i,2,new Statistiques(rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4))));
			creerArmes.add(new Arme(lanceFlamme,(rand.nextInt(degat+5*i)+degat+(5*i)-5),i,2,new Statistiques(rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4))));
			
			touteArmes.add(creerArmes);
			
			
			plageStat +=5;
			
		}	
		
		return touteArmes;
	}
	
	public Arme getArme(int lvl) {
		
		Random rand = new Random();
		
		return (armes.get(lvl-1)).get((rand.nextInt((armes.get(lvl-1)).size())));  // Retourne une arme aletoirement choisie parmi les armes de niveau lvl -> parametre de la methode
	}
	
	
	/* Charge le stock d'armure et retourne une arraylist d'arrayList
	 * 
	 * La premiere arraylist aura les Armures de lvl 1 rangees dans l'ordre suivant :
	 * 
	 * Indice 0 : Casque
	 * 
	 * Indice 1 : Plastron
	 * 
	 * Indice 2 : Gants
	 * 
	 * Indice 3 : Pantalon
	 * 
	 * Indice 4 : Bottes
	 * 
	 */
	
	public ArrayList<ArrayList<Armure>> stockArmure() {
		
		int armure = 3;  // L'armure min est de 3
		int plageStat = 4;  // Les stats min commencent a 4
		
		ArrayList<ArrayList<Armure>> touteArmures = new ArrayList<ArrayList<Armure>>();  // ArrayList d'arraylist d'armure retournee
		
		
		for(int i=1;i<101;i++) {
			
			Random rand = new Random();
			
			ArrayList<Armure> creerArmures = new ArrayList<Armure>();
			
			creerArmures.add(new Casque("Casque",(rand.nextInt((armure+(3*i)))+armure+(3*i)-3),i,new Statistiques(rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4))));
			creerArmures.add(new Torse("Torse",(rand.nextInt((armure+(3*i)))+armure+(3*i)-3),i,new Statistiques(rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4))));
			creerArmures.add(new Gants("Gants",(rand.nextInt((armure+(3*i)))+armure+(3*i)-3),i,new Statistiques(rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4))));
			creerArmures.add(new Pantalon("Pantalon",(rand.nextInt((armure+(3*i)))+armure+(3*i)-3),i,new Statistiques(rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4))));
			creerArmures.add(new Bottes("Bottes",(rand.nextInt((armure+(3*i)))+armure+(3*i)-3),i,new Statistiques(rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4),rand.nextInt(plageStat+5)+(plageStat-4))));
			
			touteArmures.add(creerArmures);
			
			plageStat +=5;
		}
		
		
		
		return touteArmures;
	}
	
	public Casque getCasque(int lvl) {
		
		return (Casque)(armures.get(lvl-1)).get(0);  // Retourne un casque aletoirement choisi parmi les casques de niveau lvl -> parametre de la methode
	}
	
	public Torse getTorse(int lvl) {
		
		return (Torse)(armures.get(lvl-1)).get(1);  // Retourne un plastron aletoirement choisi parmi les plastrons de niveau lvl -> parametre de la methode
	}


	public Gants getGants(int lvl) {
	
		return (Gants)(armures.get(lvl-1)).get(2);  // Retourne des gants aletoirement choisis parmi les gants de niveau lvl -> parametre de la methode
	}


	public Pantalon getPantalon(int lvl) {
	
		return (Pantalon)(armures.get(lvl-1)).get(3);  // Retourne un pantalon aletoirement choisi parmi les pantalons de niveau lvl -> parametre de la methode
	}


	public Bottes getBottes(int lvl) {
	
		return (Bottes)(armures.get(lvl-1)).get(4);  // Retourne des bottes aletoirement choisies parmi les bottes de niveau lvl -> parametre de la methode
	}
	
	public Armure getArmure(int lvl) {
		
		Random rand = new Random();
		
		return (armures.get(lvl-1)).get(rand.nextInt(5));
	}
	
	
	
	
	public ArrayList<Consommable> stockConsommable() {
		
		ArrayList<Consommable> toutConsommables = new ArrayList<Consommable>();
		
		String barreCereale = "Barre de cereale";
		String bouteilleEau = "Bouteille d'eau";
		String paquetChips = "Paquet de chips";
		
		String paquetCereale = "Paquet de cereale";
		String soda = "soda";
		String frites = "Frites";
		
		String cookie = "Cookie";
		String boissonEnergisante = "Boisson energisante";
		String burger = "burger";
		
		toutConsommables.add(new Consommable(barreCereale,1)); // Consommables de rang 1
		toutConsommables.add(new Consommable(bouteilleEau,1));
		toutConsommables.add(new Consommable(paquetChips,1));
		
		toutConsommables.add(new Consommable(paquetCereale,2)); // Consommables de rang 2
		toutConsommables.add(new Consommable(soda,2));
		toutConsommables.add(new Consommable(frites,2));
		
		toutConsommables.add(new Consommable(cookie,3));  // Consommables de rang 3
		toutConsommables.add(new Consommable(boissonEnergisante,3));
		toutConsommables.add(new Consommable(burger,3));
	
	
		return toutConsommables;
	}
	
	public Consommable getConsommable() {
		
		Random rand = new Random();
		
		return consommables.get(rand.nextInt(consommables.size()));  // Retourne un consommable aletoirement choisi parmi les consommables 
	}
	

	public static void main(String[] args) {
		
		Stock stock = new Stock();
		
		Consommable conso = stock.getConsommable();
		
		System.out.println(conso);
		
	}
	
}
