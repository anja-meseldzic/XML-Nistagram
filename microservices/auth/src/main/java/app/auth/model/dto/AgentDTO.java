package app.auth.model.dto;

import java.time.LocalDate;

import app.auth.model.Gender;

public class AgentDTO {
	
	private String name;
    private String surname;
    private Gender gender;
    private String email;
    private String phoneNumber;
    private LocalDate birthDate;
    private String website;
    private String biography;
    private String username;
    private String password;
    
    
	public AgentDTO() {
		super();
	}

	public AgentDTO(String name, String surname, Gender gender, String email, String phoneNumber, LocalDate birthDate,
			String website, String biography, String username, String password) {
		super();
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
