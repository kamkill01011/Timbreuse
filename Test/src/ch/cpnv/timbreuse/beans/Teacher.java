package ch.cpnv.timbreuse.beans;

import static ch.cpnv.timbreuse.dao.DAOUtility.upperFirstLetter;

/**
 * Objet enseignant
 *
 */
public class Teacher {
	
	private long id;
	private String firstname, lastname, classe, email;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = upperFirstLetter(lastname);
	}
}
