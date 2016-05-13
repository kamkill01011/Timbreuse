package ch.cpnv.timbreuse.beans;

import static ch.cpnv.timbreuse.dao.DAOUtility.upperFirstLetter;

import org.jasypt.util.text.BasicTextEncryptor;


/**
 * Objet utilisateur
 *
 */
public class User {

    private Long		id;
    private String		lastname,firstname,password,username;
    private int 		permissionLevel;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
	public String getPassword() {
		if(password.length()>8) {
			BasicTextEncryptor cryptor = new BasicTextEncryptor();
			cryptor.setPassword("MonGrainDeSel");
			return cryptor.decrypt(password);
		}
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
}