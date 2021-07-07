package rs.ac.uns.ftn.coreapis.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class CreateProfileCommand {
    @TargetAggregateIdentifier
    private long profileId;
    private String username;

    public CreateProfileCommand() {
    }

    public CreateProfileCommand(long profileId, String username) {
        this.profileId = profileId;
        this.username = username;
    }

    public long getProfileId() {
        return profileId;
    }

    public void setProfileId(long profileId) {
        this.profileId = profileId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
