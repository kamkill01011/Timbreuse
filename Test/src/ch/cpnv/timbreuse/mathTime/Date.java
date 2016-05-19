package ch.cpnv.timbreuse.mathTime;

import static ch.cpnv.timbreuse.mathTime.Math.modF;
import static ch.cpnv.timbreuse.mathTime.Math.divF;

public final class Date {
	
	private int day, month, year;
	
	public Date(int day, int month, int year) {
		if(day<1 || day>daysInMonth(month, year)) {
			throw new IllegalArgumentException("Jour invalide.");
		}
		else if(month<1 || month>12) {
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
	 * Retourne la date distance du nombre de jours donnés de la date à laquelle on l'applique.
	 * @param daysDiff
	 * @return 
	 */
	public Date relative(int daysDiff) {
		return fixedToDate(dateToFixed(day, month, year)+daysDiff);
	}
	
	public java.util.Date toJavaDate() {
		return new java.util.Date(year, month, day);
	}
	
	public String toString() {
		return Integer.toString(day)+"-"+Integer.toString(month)+"-"+Integer.toString(year);
	}
	
	public boolean equals(Object that) {
		return false;
	}
	
	public int compareTo(Date that) {
		return 0;
	}
	
	public static Date stringToDate(String dateString) {
		if(dateString==null) {
			return null;
		}
		int day = Integer.parseInt(dateString.substring(0, dateString.indexOf("-")));
		int month = Integer.parseInt(dateString.substring(dateString.indexOf("-")+1, dateString.lastIndexOf("-")));
		int year = Integer.parseInt(dateString.substring(dateString.lastIndexOf("-")+1, dateString.length()));
		return new Date(day, month, year);
	}
	
	private static boolean isLeapYear(int y) {
		return ((modF(y, 4)==0) && (modF(y, 100)!=0)) || (modF(y, 400)==0);
	}
	
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
	
	private static int calculC(int m, int y) {
		if(m<=2) { return 0;}
		else if(m>2 && isLeapYear(y)) { return -1;}
		else { return -2;}
	}
	
	private static int dateToFixed(int d, int m, int y) {
		int y0 = y-1;
		int c = calculC(m, y);
		return 365*y0 + divF(y0, 4) - divF(y0, 100) + divF(y0, 400) + divF(((367*m)-362), 12) + c + d;
	}
	
	private static int calculC2(int n, int y0) {
		int y = y0+1;
		if(n<dateToFixed(1, 3, y)) { return 0;}
		else if((n>=dateToFixed(1, 3, y)) && isLeapYear(y)) { return 1;}
		else { return 2;}
	}
	
	private static Date fixedToDate(int n) {
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
		int c = calculC2(n, y0);
		int m = divF(((12*(p+c))+373), 367);
		
		int d = n - dateToFixed(1, m, y0+1) + 1;
		return new Date(d,m,y0+1);
	}
	
	public int fixed() {
		return dateToFixed(day, month, year);
	}
	
	/**Retourne le jour de la semaine de la date
	 * @return jour de la semaine de la date
	 */
	public int dayOfWeek() {
		return Math.modF(dateToFixed(day, month, year)-1,7);
	}
}






