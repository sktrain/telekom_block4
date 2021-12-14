package sk.train;

public class Arithmetic {
	
	public static double sum (double a, double b) {
		return a+ b;
	}
	
	public static int fakultaet(int n) {
		int erg = 1;
		for (int i = 2; i <= n ; ++i) {
			erg = erg * i;
		}
		return erg;
	}
	

}
