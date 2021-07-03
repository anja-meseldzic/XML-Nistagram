package app.auth.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class AgentRegistrationRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "reviewed", nullable = false)
	private boolean reviewed;
	
	@Column(name = "name", nullable = false)
	private String name;
 
	@Column(name = "surname", nullable = false)
    private String surname;
    
	@Column(name = "gender", nullable = false)
    private Gender gender;
    
	@Column(name = "email")
    private String email;
    
	@Column(name = "phoneNumber")
    private String phoneNumber;
    
	@Column(name = "birthDate", nullable = false)
    private LocalDate birthDate;
    
	@Column(name = "website")
    private String website;
    
	@Column(name = "biography")
    private String biography;
	
	@Column(name = "username", nullable = false)
    private String username;
	
    @Column(name = "password", nullable = false)
    private String password;
	
	public AgentRegistrationRequest() {
		super();
	}

	public AgentRegistrationRequest(long id, boolean reviewed, String name, String surname, Gender gender, String email,
			String phoneNumber, LocalDate birthDate, String website, String biography, String username, String password) {
		super();
		this.id = id;
		this.reviewed = reviewed;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.birthDate = birthDate;
		this.website = website;
		this.biography = biography;
		this.username = username;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isReviewed() {
		return reviewed;
	}

	public void setReviewed(boolean reviewed) {
		this.reviewed = reviewed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
