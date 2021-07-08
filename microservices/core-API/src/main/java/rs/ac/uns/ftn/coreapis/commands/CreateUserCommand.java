package rs.ac.uns.ftn.coreapis.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class CreateUserCommand {
    @TargetAggregateIdentifier
    private String username;
    private long regularUserId;
    private long userId;

    public CreateUserCommand() {}

    public CreateUserCommand(String username, long regularUserId, long userId) {
        this.username = username;
        this.regularUserId = regularUserId;
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
