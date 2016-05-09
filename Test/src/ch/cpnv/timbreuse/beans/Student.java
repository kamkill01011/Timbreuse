package ch.cpnv.timbreuse.beans;

import java.sql.Date;
import java.sql.Time;

import org.joda.time.LocalTime;


/**
 * Objet élève
 *
 */
public class Student {

    private Long		id;
    private String		email,lastname,firstname,classe,status;
    private Date		lastCheck,startDate;
    private Time		todayTime,monday,tuesday,wednesday,thursday,friday,saturday,sunday;
    private LocalTime 	timeDiff;
    
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
        this.lastname = lastname;
    }
    public String getLastname() {
        return lastname;
    }
	public LocalTime getTimeDiff() {
		return timeDiff;
	}
	public void setTimeDiff(Time timeDiff) {
		//this.timeDiff = new LocalTime(timeDiff.toLocalTime());
		System.out.println(timeDiff.toString());
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public Time getTodayTime() {
		return todayTime;
	}
	public void setTodayTime(Time todayTime) {
		this.todayTime = todayTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getLastCheck() {
		return lastCheck;
	}
	public void setLastCheck(Date lastCheck) {
		this.lastCheck = lastCheck;
	}
	public Time getSaturday() {
		return saturday;
	}
	public void setSaturday(Time saturday) {
		this.saturday = saturday;
	}
	public Time getSunday() {
		return sunday;
	}
	public void setSunday(Time sunday) {
		this.sunday = sunday;
	}
	public Time getWednesday() {
		return wednesday;
	}
	public void setWednesday(Time wednesday) {
		this.wednesday = wednesday;
	}
	public Time getThursday() {
		return thursday;
	}
	public void setThursday(Time thursday) {
		this.thursday = thursday;
	}
	public Time getFriday() {
		return friday;
	}
	public void setFriday(Time friday) {
		this.friday = friday;
	}
	public Time getMonday() {
		return monday;
	}
	public void setMonday(Time monday) {
		this.monday = monday;
	}
	public Time getTuesday() {
		return tuesday;
	}
	public void setTuesday(Time tuesday) {
		this.tuesday = tuesday;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
}