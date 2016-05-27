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
	
	public static int[] updateTime(String timeDiff, String todayTime, String lastCheckTime, int newCheckTime) {
		int[] timeUpdated = new int[2];
		timeUpdated[0] = SecondsPastMidnight.stringToInt(timeDiff) + (newCheckTime - SecondsPastMidnight.stringToInt(lastCheckTime));
		timeUpdated[1] = SecondsPastMidnight.stringToInt(todayTime) + (newCheckTime - SecondsPastMidnight.stringToInt(lastCheckTime));
		return timeUpdated;
	}
	
	public static int checkoutTime(Student student) {
		int checkoutTime = currentTime();
		if(checkoutTime > 82740) {//need to be soft coded (82740 = 22h59, 57600 = 16h)
			if (SecondsPastMidnight.stringToInt(student.getLastCheckTime()) < 57600) checkoutTime = 57600;
			else checkoutTime = SecondsPastMidnight.stringToInt(student.getLastCheckTime())+1;
		}
		return checkoutTime;
	}
	
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
	
	private static void checkoutStudent(Student student, DAOStudent daoStudent, DAOLog daoLog) {
		daoStudent.changeStatus(student, daoLog.addLog(student));
	}
	
	private static void resetTodayTime(Student student, DAOStudent daoStudent) {
		daoStudent.resetTodayTime(student);
	}
	
	private static void setTimediff(Student student, DAOStudent daoStudent, DAOLog daoLog) {
		daoLog.endDayLog(student, "-" + SecondsPastMidnight.toString(daoStudent.setTimeDiff(student)));
	}
}
