package ch.cpnv.timbreuse.mathTime;

import static ch.cpnv.timbreuse.mathTime.Math.modF;
import static ch.cpnv.timbreuse.mathTime.Math.divF;

/**
 * @author Mathieu.JEE, Kamil.AMRANI
 * Gère les dates 
 *
 */
public final class Date {

	private int day, month, year;

	public Date(int day, int month, int year) {
		if(day<1) {
			throw new IllegalArgumentException("Jour invalide.");
		}
		else if(month<0) {
			throw new IllegalArgumentException("Mois invalide.");
		}
		this.day = day;
		this.month = month;
		this.year = year;
	}

	public Date(java.util.Date date) {
		this.day = date.getDay();
		this.month = date.getMonth();
		this.year = date.getYear();
	}

	/**
	 * Retourne le jour de la date
	 * @return jour entre 1 et 31
	 */
	public int day() {
		return day;
	}

	/**
	 * Retourne le mois de la date
	 * @return mois entre 1 et 12
	 */
	public int month() {
		return month;
	}

	/**
	 * Retourne l'année de la date
	 * @return année en int
	 */
	public int year() {
		return year;
	}

	/**
	 * Retourne la date distante du nombre de jours donnés de la date à laquelle on l'applique.
	 * @param daysDiff
	 * @return 
	 */
	public Date relative(int daysDiff) {
		return fixedToDate(dateToFixed(day, month, year)+daysDiff);
	}

	/**
	 * Modifie la date (Date) vers le format JavaDate
	 * @return date en JavaDate
	 */
	public java.util.Date toJavaDate() {
		return new java.util.Date(year, month, day);
	}

	/* 
	 * Override de la méthode ToString pour la classe Date
	 * @return Retourne la date en string dans un format précis
	 */
	public String toString() {
		return Integer.toString(day)+"-"+Integer.toString(month)+"-"+Integer.toString(year);
	}

	/**
	 * Convertit une dateString en Date
	 * @param dateString "JJ-MM-AAAA"
	 * @return Date
	 */
	public static Date stringToDate(String dateString) {
		if(dateString==null) {
			return null;
		}
		int day = Integer.parseInt(dateString.substring(0, dateString.indexOf("-")));
		int month = Integer.parseInt(dateString.substring(dateString.indexOf("-")+1, dateString.lastIndexOf("-")));
		int year = Integer.parseInt(dateString.substring(dateString.lastIndexOf("-")+1, dateString.length()));
		return new Date(day, month, year);
	}

	/**
	 * Vérifie si l'année passée en paramètre est bissextile
	 * @param y année
	 * @return true, si année bissextile 
	 */
	private static boolean isLeapYear(int y) {
		return ((modF(y, 4)==0) && (modF(y, 100)!=0)) || (modF(y, 400)==0);
	}

	/**
	 * Retourne le nombre de jours dans un mois
	 * @param m mois
	 * @param y année
	 * @return nombre de jours dans un mois
	 */
	private static int daysInMonth(int m, int y) {
		if(m==2 && isLeapYear(y)==true) {
			return 29;
		} else if(m==2 && isLeapYear(y)==false) {
			return 28;
		} else if(m==1||m==3||m==5||m==7||m==9||m==11) {
			return 31;
		} else {
			return 30;
		}
	}

	/**
	 * Vérifie que la date (int) existe bien dans le calendrier.
	 * @param dateInt
	 * @return true, si la date existe 
	 */
	public static boolean isDayValid(int dateInt) {
		Date date = fixedToDate(dateInt);
		if(date.day()>daysInMonth(date.month(), date.year())) {
			return false;
		} return true;
	}
	
	private static int calculC(int m, int y) {
		if(m<=2) { return 0;}
		else if(m>2 && isLeapYear(y)) { return -1;}
		else { return -2;}
	}

	/**
	 * Convertit la date (Date) en entier.
	 * @param d jour
	 * @param m mois
	 * @param y année
	 * @return date en entier
	 */
	private static int dateToFixed(int d, int m, int y) {
		int y0 = y-1;
		int c = calculC(m, y); //calcul de la constante c
		return 365*y0 + divF(y0, 4) - divF(y0, 100) + divF(y0, 400) + divF(((367*m)-362), 12) + c + d;
	}

	private static int calculC2(int n, int y0) {
		int y = y0+1;
		if(n<dateToFixed(1, 3, y)) { return 0;}
		else if((n>=dateToFixed(1, 3, y)) && isLeapYear(y)) { return 1;}
		else { return 2;}
	}

	/**
	 * .Convertit une date (int) en une date (Date).
	 * @param n date int
	 * @return date (Date)
	 */
	public static Date fixedToDate(int n) {
		int d0 = n-1;
		int n400 = divF(d0, 146097);
		int d1 = modF(d0, 146097);
		int n100 = divF(d1, 36524);
		int d2 = modF(d1, 36524);
		int n4 = divF(d2, 1461);
		int d3 = modF(d2, 1461);
		int n1 = divF(d3, 365);
		int y0 = 400*n400 + 100*n100 + 4*n4 + n1;

		int p = n - dateToFixed(1, 1, y0+1);
		int c = calculC2(n, y0); //calcul de la constante c
		int m = divF(((12*(p+c))+373), 367);

		int d = n - dateToFixed(1, m, y0+1) + 1;
		return new Date(d,m,y0+1);
	}

	/**
	 * Convertit la date (Date) en entier.
	 * @return date int
	 */
	public int fixed() {
		return dateToFixed(day, month, year);
	}

	/**
	 * Retourne le jour de la semaine de la date
	 * @return jour de la semaine de la date
	 */
	public String dayOfWeek() {
		int x = Math.modF(dateToFixed(day, month, year)-1,7);
		String result;
		switch(x) {
		case 0: result = "Monday";
		break;
		case 1: result = "Tuesday";
		break;
		case 2: result = "Wednesday";
		break;
		case 3: result = "Thursday";
		break;
		case 4: result = "Friday";
		break;
		case 5: result = "Saturday";
		break;
		case 6: result = "Sunday";
		break;
		default:result = null;
		}
		return result;
	}
}