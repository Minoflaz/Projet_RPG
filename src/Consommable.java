import java.util.Random;


public class Consommable extends Objet {
	
	private int soin;
	
	public Consommable(String nom,int niveau) {
		
		super(nom);
		
		switch(niveau) {
		
			case 1 :
				
				this.soin = 10;
				break;
				
			case 2 :
				
				this.soin = 20;
				break;
				
			case 3 :
				
				this.soin = 30;
				break;
		
		}
		
	}
	
	public void setSoin(int soin) {
		
		this.soin = soin;
	}
	
	public int getSoin() {
		
		return this.soin;
	}
	
	public String toString () {
		
		String str = "";
		
		str+= this.getNom() + " : \n\n+" + soin + "HP" ;
		
		return str;
	}
	
	
	
}
