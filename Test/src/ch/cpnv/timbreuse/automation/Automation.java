package ch.cpnv.timbreuse.automation;

import static ch.cpnv.timbreuse.dao.DAOUtility.currentTime;

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
	
	public static int[] updateTime(String timeDiff, String todayTime, String lastCheckTime) {
		int[] timeUpdated = new int[2];
		timeUpdated[0] = SecondsPastMidnight.stringToInt(timeDiff) + (currentTime() - SecondsPastMidnight.stringToInt(lastCheckTime));
		timeUpdated[1] = SecondsPastMidnight.stringToInt(todayTime) + (currentTime() - SecondsPastMidnight.stringToInt(lastCheckTime));
		return timeUpdated;
	}
}
