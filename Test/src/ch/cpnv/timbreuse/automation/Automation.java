package ch.cpnv.timbreuse.automation;

import static ch.cpnv.timbreuse.dao.DAOUtility.currentDate;
import static ch.cpnv.timbreuse.dao.DAOUtility.currentTime;

import java.util.ArrayList;

import ch.cpnv.timbreuse.beans.Holyday;
import ch.cpnv.timbreuse.beans.Student;
import ch.cpnv.timbreuse.dao.DAOHolyday;
import ch.cpnv.timbreuse.dao.DAOLog;
import ch.cpnv.timbreuse.dao.DAOStudent;
import ch.cpnv.timbreuse.mathTime.Date;
import ch.cpnv.timbreuse.mathTime.SecondsPastMidnight;

/**
 * Gére tout ce qui doit s'exécuter autmatiquement notement à la fin d'une journée
 * @author Mathieu.JEE Kamil.AMRANI
 *
 */
public final class Automation {

	private Automation() {
	}
	
	/**
	 * Retourne la différence de temps journalière
	 * @param toDoTime Temps à faire pour la journée
	 * @param doneTime Temps déjà fait aujourd'hui
	 * @return daily timeDiff La différence de temps
	 */
	public static int timeDiffDay(int toDoTime, int doneTime) {
		return doneTime - toDoTime;
	}
	
	/**
	 * Retourn un tableau avec les nouvelles valeurs de timeDiff et todayTime après avoir timbré "DEP"
	 * @param timeDiff Ancienne différence de temps
	 * @param todayTime Ancien temps effectué aujourd'hui
	 * @param lastCheckTime Heure du dernier timbrage
	 * @param newCheckTime Heure du nouveau timbrage
	 * @return Les nouvelles valeurs de timeDiff et todayTime
	 */
	public static int[] updateTime(String timeDiff, String todayTime, String lastCheckTime, int newCheckTime) {
		int[] timeUpdated = new int[2];
		timeUpdated[0] = SecondsPastMidnight.stringToInt(timeDiff) + (newCheckTime - SecondsPastMidnight.stringToInt(lastCheckTime));
		timeUpdated[1] = SecondsPastMidnight.stringToInt(todayTime) + (newCheckTime - SecondsPastMidnight.stringToInt(lastCheckTime));
		return timeUpdated;
	}
	
	/**
	 * Retourn le de timbrage corrigée
	 * @param student L'élève qui timbre
	 * @return Heure de timbrage
	 */
	public static int checkoutTime(Student student) {
		int checkoutTime = currentTime();
		if(checkoutTime > 82740) {//need to be soft coded (82740 = 22h59, 57600 = 16h)
			if (SecondsPastMidnight.stringToInt(student.getLastCheckTime()) < 57600) checkoutTime = 57600;
			else checkoutTime = SecondsPastMidnight.stringToInt(student.getLastCheckTime())+1;
		}
		return checkoutTime;
	}
	
	/**
	 * Gére la fin de la journée; vérifie si c'est un jour de vacance, timbre les élèves qui sont marqués comme "ARR", reset de todayTime et soustrait les heures à faire
	 * @param daoStudent DAO pour modifier les élèves
	 * @param daoLog DAO pour ajouter des logs
	 * @param daoHolyday DAO pour récupérer les vacances
	 */
	public static void endDay(DAOStudent daoStudent, DAOLog daoLog, DAOHolyday daoHolyday) {
		ArrayList<Holyday> holydays = daoHolyday.getAllHolydays();
		for (int i = 0; i < holydays.size(); i++) {
			if(Date.stringToDate(holydays.get(i).getDate()).equals(Date.stringToDate(currentDate()))) {
				System.out.println("Holyday, day ended.");
				return;
			}
		}
		ArrayList<Student> students = daoStudent.getNotCheckedOutStudents();
		for (int i = 0; i < students.size(); i++) {
			if(students.get(i).getStatus().equals("ARR")) checkoutStudent(students.get(i),daoStudent, daoLog);
			resetTodayTime(students.get(i), daoStudent);
			if(!students.get(i).getStatus().equals("MED")) setTimediff(students.get(i), daoStudent, daoLog);
		}
		System.out.println("Day ended.");
	}
	
	/**
	 * Timber un élève
	 * @param student L'élève à timbrer
	 * @param daoStudent DAO pour modifier l'élève
	 * @param daoLog DAO pour ajouter des logs
	 */
	public static void checkoutStudent(Student student, DAOStudent daoStudent, DAOLog daoLog) {
		daoStudent.changeStatus(student, daoLog.addLog(student));
	}
	
	/**
	 * Remet todayTime à zéro
	 * @param student L'élève à modifier
	 * @param daoStudent DAO pour modifier l'élève
	 */
	public static void resetTodayTime(Student student, DAOStudent daoStudent) {
		daoStudent.resetTodayTime(student);
	}
	
	/**
	 * Soustrait les heures à faire à un élève
	 * @param student L'élève à modifier
	 * @param daoStudent DAO pour modifier l'élève
	 * @param daoLogDAO pour ajouter des logs
	 */
	public static void setTimediff(Student student, DAOStudent daoStudent, DAOLog daoLog) {
		daoLog.endDayLog(student, "-" + SecondsPastMidnight.toString(daoStudent.setTimeDiff(student)));
	}
}
