package ch.cpnv.timbreuse.automation;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import ch.cpnv.timbreuse.dao.DAOHolyday;
import ch.cpnv.timbreuse.dao.DAOLog;
import ch.cpnv.timbreuse.dao.DAOStudent;

import static ch.cpnv.timbreuse.automation.Automation.endDay;

/**
 * Execute une TimerTask à la fin de chaque journée (à 23:00:00)
 * @author Mathieu.JEE Kamil.AMRANI
 *
 */
public class EndOfDay {
	private Timer timer;
	private Calendar calendar;
	private DAOStudent daoStudent;
	private DAOLog daoLog;
	private DAOHolyday daoHolyday;
	
	public EndOfDay(DAOStudent daoStudent, DAOLog daoLog, DAOHolyday daoHolyday) {
		this.daoStudent = daoStudent;
		this.daoLog = daoLog;
		this.daoHolyday = daoHolyday;
	}
	
	/**
	 * Initialisation du Timer
	 */
	public void init() {
		calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		
		timer = new Timer();
		timer.scheduleAtFixedRate(new EndOfDayTask(), calendar.getTime(), 1000 * 60 * 60 * 24);
		
		System.out.println("End of day init...");
	}
	
	/**
	 * TimerTask qui s'effectue à la fin de chaque journée
	 * @author Mathieu.JEE Kamil.AMRANI
	 *
	 */
	class EndOfDayTask extends TimerTask {

		@Override
		public void run() {
			endDay(daoStudent, daoLog, daoHolyday);
		}
	}
}
