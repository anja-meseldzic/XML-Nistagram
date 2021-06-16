package app.notification.model;

public class NotificationSettingsPerProfile {

    private long id;
    private String profile;
    private boolean notifyOnFollw;
    private boolean notifyOnAcceptedFollowRequest;

    public NotificationSettingsPerProfile(String profile, boolean notifyOnFollw, boolean notifyOnAcceptedFollowRequest) {
        this.profile = profile;
        this.notifyOnFollw = notifyOnFollw;
        this.notifyOnAcceptedFollowRequest = notifyOnAcceptedFollowRequest;
    }

    public long getId() {
        return id;
    }

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
