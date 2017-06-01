package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private long id;
	
	@Column(name="Login")
	private String login;
	
	@Column(name="Password")
	private String password;
	
	@Column(name="Mail")
	private String mail;
	
	public User(){}

	public User(String login, String password, String mail) {
		this.login = login;
		this.password = password;
		this.mail = mail;
	}
	
	
}
