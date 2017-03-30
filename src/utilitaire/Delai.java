package utilitaire;

public class Delai {


	public static void delai(int mse){
		try {
			Thread.sleep(mse);
		}
		catch(InterruptedException e){}
	}

}
