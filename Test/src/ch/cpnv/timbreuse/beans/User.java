package ch.cpnv.timbreuse.beans;

import java.sql.Date;
import java.sql.Time;

import org.joda.time.DateTime;


public class User {

    private Long		id;
    private String		email,lastname,firstname,classe,password,username,status;
    private Date		lastCheck,startDate;
    private Time		timeDiff,todayTime,monday,tuesday,wednesday,thursday,friday,saturday,sunday;
    private int 		permissionLevel;
    
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
	public Time getTimeDiff() {
		return timeDiff;
	}
	public void setTimeDiff(Time timeDiff) {
		this.timeDiff = timeDiff;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPermissionLevel() {
		return permissionLevel;
	}
	public void setPermissionLevel(int permissionLevel) {
		this.permissionLevel = permissionLevel;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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