
public class Mur implements java.io.Serializable {
	
	private int positionX;
	private int positionY;
	
	public Mur(int x, int y) {
		
		this.positionX = x;
		this.positionY = y;
		
	}
	
	
	public void setPositionX(int x) {
		
		this.positionX = x;
	}
	
	public void setPositionY(int y) {
		
		this.positionY = y;
	}
	
	public void setPosition(int x, int y) {
		
		this.positionX = x;
		this.positionY = y;
	}
	
	public int getPositionX() {
		
		return this.positionX;
	}

	public int getPositionY() {
		
		return this.positionY;
	}
	
	public String toString() {
		
		return "" + positionX + "," + positionY ;
	}
	
}
