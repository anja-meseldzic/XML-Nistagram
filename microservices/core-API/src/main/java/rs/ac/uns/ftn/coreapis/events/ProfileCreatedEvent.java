package rs.ac.uns.ftn.coreapis.events;

public class ProfileCreatedEvent {
    private String profileAggregateId;

    public ProfileCreatedEvent() {
    }

    public ProfileCreatedEvent(String profileAggregateId) {
        this.profileAggregateId = profileAggregateId;
    }

    public String getProfileAggregateId() {
        return profileAggregateId;
    }

    public void setProfileAggregateId(String profileAggregateId) {
        this.profileAggregateId = profileAggregateId;
    }
}
