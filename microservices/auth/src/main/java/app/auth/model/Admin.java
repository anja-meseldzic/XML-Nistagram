package app.auth.model;

import app.auth.exception.InvalidRoleException;

import javax.persistence.*;

@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "admin_generator")
    @SequenceGenerator(name="admin_generator", sequenceName = "admin_seq", allocationSize=50, initialValue = 100)
    private long id;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

    public Admin() {

    }

    public Admin(User user) throws InvalidRoleException {
        if(user.getRole() != Role.ADMIN)
            throw new InvalidRoleException();
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }
}
