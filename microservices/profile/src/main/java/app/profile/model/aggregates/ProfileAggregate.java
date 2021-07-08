package app.profile.model.aggregates;

import app.profile.service.ProfileService;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import rs.ac.uns.ftn.coreapis.commands.CreateProfileCommand;
import rs.ac.uns.ftn.coreapis.events.ProfileCreatedEvent;
import rs.ac.uns.ftn.coreapis.events.ProfileCreatedFailedEvent;

@Aggregate
public class ProfileAggregate {
    @AggregateIdentifier
    private String profileAggregateId;

    public ProfileAggregate() {}

    @CommandHandler
    public ProfileAggregate(CreateProfileCommand createProfileCommand, ProfileService profileService) {
        try {
            profileService.createFromUser(createProfileCommand.getProfileUsername());
            AggregateLifecycle.apply(new ProfileCreatedEvent(createProfileCommand.getProfileAggregateId()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            AggregateLifecycle.apply(new ProfileCreatedFailedEvent(createProfileCommand.getProfileAggregateId(),
                    createProfileCommand.getProfileUsername(),
                    createProfileCommand.getRegularUserId(),
                    createProfileCommand.getUserId(),
                    e.getMessage()));
        }
    }

    @EventSourcingHandler
    public void on(ProfileCreatedEvent profileCreatedEvent) {
        this.profileAggregateId = profileCreatedEvent.getProfileAggregateId();
    }

    @EventSourcingHandler
    public void on(ProfileCreatedFailedEvent profileCreatedFailedEvent) {
        this.profileAggregateId = profileCreatedFailedEvent.getProfileAggregateId();
    }
}
