package app.auth.model.aggregates;

import app.auth.service.RegularUserService;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import rs.ac.uns.ftn.coreapis.commands.CreateUserCommand;
import rs.ac.uns.ftn.coreapis.commands.RollbackUserCommand;
import rs.ac.uns.ftn.coreapis.events.UserCreatedEvent;
import rs.ac.uns.ftn.coreapis.events.UserRollbackEvent;

@Aggregate
public class UserAggregate {
    @AggregateIdentifier
    private String username;
    private long regularUserId;
    private long userId;

    public UserAggregate() {}

    @CommandHandler
    public UserAggregate(CreateUserCommand createUserCommand) {
        AggregateLifecycle.apply(
                new UserCreatedEvent(createUserCommand.getUsername(),
                        createUserCommand.getRegularUserId(),
                        createUserCommand.getUserId()));
    }

    @EventSourcingHandler
    public void on(UserCreatedEvent userCreatedEvent) {
        this.username = userCreatedEvent.getUsername();
        this.regularUserId = userCreatedEvent.getRegularUserId();
        this.userId = userCreatedEvent.getUserId();
    }

    @CommandHandler
    public void on(RollbackUserCommand rollbackUserCommand, RegularUserService regularUserService) {
        regularUserService.remove(rollbackUserCommand.getRegularUserId());
        AggregateLifecycle.apply(
                new UserRollbackEvent(rollbackUserCommand.getUsername(),
                        rollbackUserCommand.getRegularUserId(),
                        rollbackUserCommand.getUserId()));
    }


}
