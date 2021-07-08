package app.auth.model;

import app.auth.exception.InvalidRoleException;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class RegularUser {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reg_user_generator")
	@SequenceGenerator(name = "reg_user_generator", sequenceName = "reg_user_seq", allocationSize = 50, initialValue = 100)
	private long id;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private User user;
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
	@Column(name = "apitoken")
	private String apiToken;

	public RegularUser() {

	}

	public RegularUser(User user, String name, String surname, Gender gender, String email, String phoneNumber,
			LocalDate birthDate, String website, String biography, String apikey) throws InvalidRoleException {
		if (user.getRole() != Role.USER && user.getRole() != Role.AGENT)
			throw new InvalidRoleException();
		this.user = user;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.birthDate = birthDate;
		this.website = website;
		this.biography = biography;
		this.apiToken = apikey;
	}

	public long getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public Gender getGender() {
		return gender;
	}

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public String getWebsite() {
		return website;
	}

	public String getBiography() {
		return biography;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public String getApiToken() {
		return apiToken;
	}

	public void setApiToken(String apiToken) {
		this.apiToken = apiToken;
	}
	
}
