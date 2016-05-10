package ch.cpnv.timbreuse.beans;

import ch.cpnv.timbreuse.mathTime.Date;
import ch.cpnv.timbreuse.mathTime.SecondsPastMidnight;

import static ch.cpnv.timbreuse.dao.DAOUtility.upperFirstLetter;
import static ch.cpnv.timbreuse.mathTime.Date.stringToDate;


/**
 * Objet élève
 *
 */
public class Student {

    private Long		id;
    private String		email,lastname,firstname,classe,status;
    private Date		lastCheckDate,startDate;
    private int			lastCheckTime,timeDiff,todayTime,monday,tuesday,wednesday,thursday,friday,saturday,sunday;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
    	return email;
    }
    public void setLastname(String lastname) {
        this.lastname = upperFirstLetter(lastname);
    }
    public String getLastname() {
        return lastname;
    }
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = upperFirstLetter(firstname);
	}
	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = stringToDate(startDate);
	}
	public Date getLastCheckDate() {
		return lastCheckDate;
	}
	public void setLastCheckDate(String lastCheckDate) {
		this.lastCheckDate = stringToDate(lastCheckDate);
	}
	public String getLastCheckTime() {
		return SecondsPastMidnight.toString(lastCheckTime);
	}
	public void setLastCheckTime(int lastCheckTime) {
		this.lastCheckTime = lastCheckTime;
	}
	public String getTimeDiff() {
		return SecondsPastMidnight.toString(timeDiff);
	}
	public void setTimeDiff(int timeDiff) {
		this.timeDiff = timeDiff;
	}
	public int getTodayTime() {
		return todayTime;
	}
	public void setTodayTime(int todayTime) {
		this.todayTime = todayTime;
	}
	public int getMonday() {
		return monday;
	}
	public void setMonday(int monday) {
		this.monday = monday;
	}
	public int getTuesday() {
		return tuesday;
	}
	public void setTuesday(int tuesday) {
		this.tuesday = tuesday;
	}
	public int getWednesday() {
		return wednesday;
	}
	public void setWednesday(int wednesday) {
		this.wednesday = wednesday;
	}
	public int getThursday() {
		return thursday;
	}
	public void setThursday(int thursday) {
		this.thursday = thursday;
	}
	public int getFriday() {
		return friday;
	}
	public void setFriday(int friday) {
		this.friday = friday;
	}
	public int getSaturday() {
		return saturday;
	}
	public void setSaturday(int saturday) {
		this.saturday = saturday;
	}
	public int getSunday() {
		return sunday;
	}
	public void setSunday(int sunday) {
		this.sunday = sunday;
	}
}