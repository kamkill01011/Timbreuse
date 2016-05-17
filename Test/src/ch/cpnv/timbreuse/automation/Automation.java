package ch.cpnv.timbreuse.automation;

import ch.cpnv.timbreuse.mathTime.SecondsPastMidnight;

public final class Automation {

	private Automation() {
		
	}
	
	/**
	 * Retourne la différence de temps journalière
	 * @param toDoTime
	 * @param doneTime
	 * @return daily timeDiff
	 */
	public static int timeDiffDay(int toDoTime, int doneTime) {
		return doneTime - toDoTime;
	}
	
	/**
	 * Retourne la différence de temps avec son signe
	 * @param timeDiff
	 * @return timeDiff avec le signe + ou -
	 */
	public static String signedTimeDiff(int timeDiff) {
		if(timeDiff>0) {
			return "+"+SecondsPastMidnight.toString(timeDiff);
		} else if(timeDiff<0) {
			return "-"+SecondsPastMidnight.toString(timeDiff);
		} return SecondsPastMidnight.toString(timeDiff);
	}
}
