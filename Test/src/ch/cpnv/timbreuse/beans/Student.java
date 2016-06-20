package ch.cpnv.timbreuse.beans;

import ch.cpnv.timbreuse.mathTime.Date;
import ch.cpnv.timbreuse.mathTime.SecondsPastMidnight;

import static ch.cpnv.timbreuse.dao.DAOUtility.upperFirstLetter;
import static ch.cpnv.timbreuse.mathTime.Date.stringToDate;

/**
 * Bean élève
 * @author Mathieu.JEE Kamil.AMRANI
 *
 */
public class Student {

    private Long		id;//ID
    private String		email,lastname,firstname,classe,status,tag;//e-mail, nom, prénom, classe, status (ARR, DEP, MED, ...), ID du tag assigné
    private Date		lastCheckDate,startDate;//date du dernier timbrage, date de début de la foration MCT
    private int			lastCheckTime,timeDiff,todayTime,monday,tuesday,wednesday,thursday,friday,saturday,sunday;//heure du dernier timbrage, différence de temps, heures effectuées aujourd'hui, heures à effectuer (pour chaque jour)
    
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
	public String getTodayTime() {
		return SecondsPastMidnight.toString(todayTime);
	}
	public void setTodayTime(int todayTime) {
		this.todayTime = todayTime;
	}
	public String getMonday() {
		return SecondsPastMidnight.toString(monday);
	}
	public void setMonday(int monday) {
		this.monday = monday;
	}
	public String getTuesday() {
		return SecondsPastMidnight.toString(tuesday);
	}
	public void setTuesday(int tuesday) {
		this.tuesday = tuesday;
	}
	public String getWednesday() {
		return SecondsPastMidnight.toString(wednesday);
	}
	public void setWednesday(int wednesday) {
		this.wednesday = wednesday;
	}
	public String getThursday() {
		return SecondsPastMidnight.toString(thursday);
	}
	public void setThursday(int thursday) {
		this.thursday = thursday;
	}
	public String getFriday() {
		return SecondsPastMidnight.toString(friday);
	}
	public void setFriday(int friday) {
		this.friday = friday;
	}
	public String getSaturday() {
		return SecondsPastMidnight.toString(saturday);
	}
	public void setSaturday(int saturday) {
		this.saturday = saturday;
	}
	public String getSunday() {
		return SecondsPastMidnight.toString(sunday);
	}
	public void setSunday(int sunday) {
		this.sunday = sunday;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
}