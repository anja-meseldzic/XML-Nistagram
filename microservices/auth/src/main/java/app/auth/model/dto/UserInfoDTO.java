package app.auth.model.dto;

import java.time.LocalDate;

public class UserInfoDTO {

    private String fullName;
    private String  gender;
    private String email;
    private LocalDate birthDate;
    private String website;
    private String bio;

    public UserInfoDTO() {

    }

    public UserInfoDTO(String fullName, String gender, String email, LocalDate birthDate, String website, String bio) {
        this.fullName = fullName;
        this.gender = gender;
        this.email = email;
        this.birthDate = birthDate;
        this.website = website;
        this.bio = bio;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
