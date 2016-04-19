package ch.cpnv.timbreuse.beans;

public class User {

    private Long      id;
    private String    email;
    private String    lastname;
    private String 	  firstname;
    private Integer   timeDiff;

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
	public Integer getTimeDiff() {
		return timeDiff;
	}
	public void setTimeDiff(Integer timeDiff) {
		this.timeDiff = timeDiff;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
}