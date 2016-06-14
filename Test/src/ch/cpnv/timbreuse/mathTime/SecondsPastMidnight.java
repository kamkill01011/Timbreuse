package ch.cpnv.timbreuse.mathTime;

import static java.lang.Math.abs;

/**
 * @author Mathieu.JEE, Kamil.AMRANI
 * Classe qui gère les heures dans le format "SecondsPastMidnight" (nombre de secondes après minuit)
 */
public final class SecondsPastMidnight {
	
	public static final int INFINITE = 200000; 
	
	private SecondsPastMidnight() {  //Constructeur privé par défaut vide => rend la classe non instantiable
		
	}
	
	/**
	 * Convertit une heure du format HH-mm-ss en un entier (nb secondes après minuit)
	 * @param hours heures
	 * @param minutes minutes
	 * @param seconds secondes
	 * @return heure en entier
	 */
	public static int fromHMS(int hours, int minutes, int seconds) {
		if(minutes<=-60 || minutes>=60) {
			throw new IllegalArgumentException("minutes invalides");
		}
		if(seconds<=-60 || seconds>=60) {
			throw new IllegalArgumentException("secondes invalides");
		}
		return (hours*3600)+(minutes*60)+seconds;
	}
	
	/**
	 * Convertit une javaDate en un entier (nb secondes après minuit)
	 * @param date javaDate
	 * @return heure en entier
	 */
	public static int fromJavaDate(java.util.Date date) {
		return fromHMS(date.getHours(), date.getMinutes(), date.getSeconds());
	}
	
	/**
	 * Retourne le nombre d'heures entières dans un nombre de secondes après minuit
	 * @param spm secondes après minuit
	 * @return heures entières dans spm
	 */
	public static int hours(int spm) {
		if(spm<-INFINITE || spm>INFINITE) {
			throw new IllegalArgumentException();
		}
		return Math.divF(spm, 3600);
	}
	
	/**
	 * Retourne le nombre de minutes entières dans un nombre de secondes 
	 * après minuit (soustrait du nombre d'heures entières)
	 * @param spm secondes après minuit
	 * @return minutes entières dans spm
	 */
	public static int minutes(int spm) {
		if(spm<-INFINITE || spm>INFINITE) {
			throw new IllegalArgumentException();
		}
		return Math.divF(Math.modF(spm, 3600), 60);
	}
	
	/**
	 * Retourne le nombre de secondes entières dans un nombre de secondes 
	 * après minuit (soustrait du nombre d'heures et minutes entières)
	 * @param spm secondes après minuit
	 * @return secondes entières dans spm
	 */
	public static int seconds(int spm) {
		if(spm<-INFINITE || spm>INFINITE) {
			throw new IllegalArgumentException();
		}
		return Math.modF(Math.modF(spm, 3600),60);
	}
	
	/**
	 * Convertit et retourne le temps (int) en string (HH-mm-ss)
	 * @param spm secondes après minuit
	 * @return temps en string 
	 */
	public static String toString(int spm) {
		int hours = abs(hours(spm));
		int minutes = abs(minutes(spm));
		int seconds = abs(seconds(spm));
		String sign = "";
		if(spm < 0) sign = "-";
		return sign+String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}

	/**
	 * Convertit et retourne le temps (string) en entier (spm)
	 * @param s temps String
	 * @return temps en secondes après minuit (spm)
	 */
	public static int stringToInt(String s) {
		int sign = 1;
		if(s.startsWith("-")) {
			s = s.substring(1);
			sign = -1;
		}
		int hours = Integer.parseInt(s.substring(0, s.indexOf(":")));
		int minutes = Integer.parseInt(s.substring(s.indexOf(":")+1, s.lastIndexOf(":")));
		int seconds = Integer.parseInt(s.substring(s.lastIndexOf(":")+1, s.length()));
		return sign * fromHMS(hours, minutes, seconds);
	}
}