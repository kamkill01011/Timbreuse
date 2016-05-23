package ch.cpnv.timbreuse.automation;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import ch.cpnv.timbreuse.dao.DAOHolyday;
import ch.cpnv.timbreuse.dao.DAOLog;
import ch.cpnv.timbreuse.dao.DAOStudent;

import static ch.cpnv.timbreuse.automation.Automation.endDay;

public class EndOfDay {
	Timer timer;
	private DAOStudent daoStudent;
	private DAOLog daoLog;
	private DAOHolyday daoHolyday;
	
	public EndOfDay(DAOStudent daoStudent, DAOLog daoLog, DAOHolyday daoHolyday) {
		this.daoStudent = daoStudent;
		this.daoLog = daoLog;
		this.daoHolyday = daoHolyday;
	}
	
	public void init() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.HOUR_OF_DAY, 22);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);

    timer = new Timer();
    timer.schedule(new EndOfDayTask(), calendar.getTime());
    
    System.out.println("End of day init...");
	}
	
	class EndOfDayTask extends TimerTask {

		@Override
		public void run() {
			endDay(daoStudent, daoLog, daoHolyday);
		}
	}
}
