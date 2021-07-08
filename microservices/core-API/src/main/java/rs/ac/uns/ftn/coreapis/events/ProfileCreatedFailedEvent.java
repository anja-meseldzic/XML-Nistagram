package rs.ac.uns.ftn.coreapis.events;

public class ProfileCreatedFailedEvent {
    private String profileAggregateId;
    private String profileUsername;
    private long regularUserId;
    private long userId;
    private String reason;

    public ProfileCreatedFailedEvent() {
    }

    public ProfileCreatedFailedEvent(String profileAggregateId, String profileUsername, long regularUserId, long userId, String reason) {
        this.profileAggregateId = profileAggregateId;
        this.profileUsername = profileUsername;
        this.regularUserId = regularUserId;
        this.userId = userId;
        this.reason = reason;
    }

    public String getProfileUsername() {
        return profileUsername;
    }

    public void setProfileUsername(String profileUsername) {
        this.profileUsername = profileUsername;
    }

    public String getProfileAggregateId() {
        return profileAggregateId;
    }

    public void setProfileAggregateId(String profileAggregateId) {
        this.profileAggregateId = profileAggregateId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public long getRegularUserId() {
        return regularUserId;
    }

    public void setRegularUserId(long regularUserId) {
        this.regularUserId = regularUserId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
