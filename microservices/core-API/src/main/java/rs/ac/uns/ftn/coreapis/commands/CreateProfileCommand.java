package rs.ac.uns.ftn.coreapis.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class CreateProfileCommand {
    @TargetAggregateIdentifier
    private String profileAggregateId;
    private long regularUserId;
    private long userId;
    private String profileUsername;

    public CreateProfileCommand() {
    }

    public CreateProfileCommand(String profileAggregateId, long regularUserId, long userId, String profileUsername) {
        this.profileAggregateId = profileAggregateId;
        this.regularUserId = regularUserId;
        this.userId = userId;
        this.profileUsername = profileUsername;
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
