package ch.cpnv.timbreuse.mathTime;

import static java.lang.Math.floor;

/**
 * @author Mathieu.JEE, Kamil.AMRANI
 * Classe qui redéfinit certaines méthodes de java.lang.Math 
 * pour ce projet
 */
public final class Math {
	
	public Math() {	//constructeur par défaut vide pour être sûr que la classe ne peut pas être instanciée
		
	}
	
	/**
	 * Retourne le quotient de la division par défaut de n par d
	 * @param n, dividende
	 * @param d, diviseur
	 * @return quotient de la div de n par d
	 */
	public static int divF(int n, int d) {
		return (int)floor(n/d);
	}
	
	/**
	 * Retourne le reste de la division par défaut de n par d
	 * @param n, dividende
	 * @param d, diviseur
	 * @return reste de la div de n par d
	 */
	public static int modF(int n, int d) {
		return (int)(n - (d*(divF(n, d))));
	}
}
