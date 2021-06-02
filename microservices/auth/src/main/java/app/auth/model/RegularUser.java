package app.auth.model;

import app.auth.exception.InvalidRoleException;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class RegularUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

    public RegularUser() {

    }

    public RegularUser(User user, String name, String surname, Gender gender, String email, String phoneNumber, LocalDate birthDate, String website, String biography) throws InvalidRoleException {
        if(user.getRole() != Role.USER && user.getRole() != Role.AGENT)
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
}
