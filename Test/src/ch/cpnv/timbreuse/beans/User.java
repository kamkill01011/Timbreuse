package ch.cpnv.timbreuse.beans;

import java.sql.Time;
import static ch.cpnv.timbreuse.dao.DAOUtility.upperWithoutAccent;

public class User {

    private Long      id;
    private String    email;
    private String    lastname;
    private String 	  firstname;
    private Time      timeDiff;
    private String    classe;
    private String password;

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
        return firstname+"."+upperWithoutAccent(lastname)+"@cpnv.ch";
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
}