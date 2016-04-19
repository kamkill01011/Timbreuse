package ch.cpnv.timbreuse.beans;

import java.sql.Time;

public class User {

    private Long      id;
    private String    email;
    private String    lastname;
    private String 	  firstname;
    private Time   timeDiff;

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
}