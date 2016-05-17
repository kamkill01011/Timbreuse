package ch.cpnv.timbreuse.beans;

import ch.cpnv.timbreuse.mathTime.SecondsPastMidnight;

/**
 * Objet utilisateur
 *
 */
public class Log {

    private Long		id;
    private String		username,date,status;
    private int 		time;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTime() {
		return SecondsPastMidnight.toString(time);
	}
	public void setTime(int time) {
		this.time = time;
	}
}