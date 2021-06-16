package app.notification.model;

import javax.persistence.*;

@Entity
public class NotificationSettingsPerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "profile")
    private String profile;
    @Column(name = "notifyOnFollow")
    private boolean notifyOnFollw;
    @Column(name = "notifyOnAcceptedFollowRequest")
    private boolean notifyOnAcceptedFollowRequest;

    public NotificationSettingsPerProfile(String profile, boolean notifyOnFollw, boolean notifyOnAcceptedFollowRequest) {
        this.profile = profile;
        this.notifyOnFollw = notifyOnFollw;
        this.notifyOnAcceptedFollowRequest = notifyOnAcceptedFollowRequest;
    }

    public NotificationSettingsPerProfile() {

    }

    public NotificationSettingsPerProfile(String profile) {
        this.profile = profile;
        this.notifyOnFollw = true;
        this.notifyOnAcceptedFollowRequest = true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) { this.id = id; }

    public String getProfile() {
        return profile;
    }

    public boolean isNotifyOnFollw() {
        return notifyOnFollw;
    }

    public boolean isNotifyOnAcceptedFollowRequest() {
        return notifyOnAcceptedFollowRequest;
    }
}
