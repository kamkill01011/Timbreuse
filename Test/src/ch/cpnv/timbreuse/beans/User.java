package ch.cpnv.timbreuse.beans;

public class User {

    private Long      id;
    private String    email;
    private String    lastname;
    private String 	  firstName;
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

    public void setLastname(String name) {
        this.lastname = name;
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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
}