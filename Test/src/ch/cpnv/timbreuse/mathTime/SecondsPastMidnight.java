package ch.cpnv.timbreuse.mathTime;

import static java.lang.Math.abs;

public final class SecondsPastMidnight {
	public static final int INFINITE = 200000; 
	
	private SecondsPastMidnight() {  //Constructeur privé par défaut vide => rend la classe non instantiable
	
	}
	
	public static int fromHMS(int hours, int minutes, int seconds) {
		if(minutes<=-60 || minutes>=60) {
			throw new IllegalArgumentException("minutes invalides");
		}
		if(seconds<=-60 || seconds>=60) {
			throw new IllegalArgumentException("secondes invalides");
		}
		if(hours<=-30 || hours>=30) {
			throw new IllegalArgumentException("heure invalide");
		}
		return (hours*3600)+(minutes*60)+seconds;
	}
	
	public static int fromJavaDate(java.util.Date date) {
		return fromHMS(date.getHours(), date.getMinutes(), date.getSeconds());
	}
	
	public static int hours(int spm) {
		if(spm<-INFINITE || spm>INFINITE) {
			throw new IllegalArgumentException();
		}
		return Math.divF(spm, 3600);
	}
	
	public static int minutes(int spm) {
		if(spm<-INFINITE || spm>INFINITE) {
			throw new IllegalArgumentException();
		}
		return Math.divF(Math.modF(spm, 3600), 60);
	}
	
	public static int seconds(int spm) {
		if(spm<-INFINITE || spm>INFINITE) {
			throw new IllegalArgumentException();
		}
		return Math.modF(Math.modF(spm, 3600),60);
	}
	
	public static String toString(int spm) {
		int hours = abs(hours(spm));
		int minutes = abs(minutes(spm));
		int seconds = abs(seconds(spm));
		String sign = "";
		if(spm < 0) sign = "-";
		return sign+String.format("%02d:%02d:%02d", hours, minutes, seconds);
	}



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
