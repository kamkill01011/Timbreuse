package ch.cpnv.timbreuse.automation;

import static ch.cpnv.timbreuse.dao.DAOUtility.currentTime;

import java.util.ArrayList;

import ch.cpnv.timbreuse.beans.Student;
import ch.cpnv.timbreuse.dao.DAOLog;
import ch.cpnv.timbreuse.dao.DAOStudent;
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
	
	public static void endDay(DAOStudent daoStudent, DAOLog daoLog) {
		ArrayList<Student> students = daoStudent.getNotCheckedOutStudents();
		for (int i = 0; i < students.size(); i++) {
			if(students.get(i).getStatus().equals("ARR")) {			
				checkoutAllStudent(students.get(i),daoStudent, daoLog);
			}
			resetTodayTime(students.get(i), daoStudent);
			setTimediff(students.get(i), daoStudent);
		}
		System.out.println("Day ended.");
	}
	
	private static void checkoutAllStudent(Student student, DAOStudent daoStudent, DAOLog daoLog) {
		String newStatus = daoLog.addLog(student);//change time
		daoStudent.changeStatus(student, newStatus);
	}
	
	private static void resetTodayTime(Student student, DAOStudent daoStudent) {
		daoStudent.resetTodayTime(student);
	}
	
	private static void setTimediff(Student student, DAOStudent daoStudent) {
		daoStudent.setTimeDiff(student);
	}
}
