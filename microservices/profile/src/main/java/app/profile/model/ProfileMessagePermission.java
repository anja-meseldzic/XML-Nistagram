package app.profile.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ProfileMessagePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstPeer;
    private String secondPeer;

    public ProfileMessagePermission() {
    }

    public ProfileMessagePermission(String firstPeer, String secondPeer) {
        this.firstPeer = firstPeer;
        this.secondPeer = secondPeer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstPeer() {
        return firstPeer;
    }

    public void setFirstPeer(String firstPeer) {
        this.firstPeer = firstPeer;
    }

    public String getSecondPeer() {
        return secondPeer;
    }

    public void setSecondPeer(String secondPeer) {
        this.secondPeer = secondPeer;
    }
}
