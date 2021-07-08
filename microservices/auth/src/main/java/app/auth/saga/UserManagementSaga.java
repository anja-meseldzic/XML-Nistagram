package app.auth.saga;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import rs.ac.uns.ftn.coreapis.commands.CreateProfileCommand;
import rs.ac.uns.ftn.coreapis.commands.RollbackUserCommand;
import rs.ac.uns.ftn.coreapis.events.ProfileCreatedEvent;
import rs.ac.uns.ftn.coreapis.events.ProfileCreatedFailedEvent;
import rs.ac.uns.ftn.coreapis.events.UserCreatedEvent;
import rs.ac.uns.ftn.coreapis.events.UserRollbackEvent;

import javax.inject.Inject;
import java.util.UUID;

@Saga
public class UserManagementSaga {
    @Inject
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "username")
    public void handle(UserCreatedEvent userCreatedEvent) {
        System.out.println("SAGA invoked");
        String profileAggregateId = UUID.randomUUID().toString();
        SagaLifecycle.associateWith("profileAggregateId", profileAggregateId);
        commandGateway.send(new CreateProfileCommand(profileAggregateId,
                userCreatedEvent.getRegularUserId(),
                userCreatedEvent.getUserId(),
                userCreatedEvent.getUsername()));
    }

    @SagaEventHandler(associationProperty = "profileAggregateId")
    public void handle(ProfileCreatedEvent profileCreatedEvent) {
        System.out.println("Saga finishing, both user and its profile are created!");
        SagaLifecycle.end();
    }

    @SagaEventHandler(associationProperty = "profileAggregateId")
    public void handle(ProfileCreatedFailedEvent profileCreatedFailedEvent) {
        System.out.println("Saga declined, starting compensation transaction!");
        commandGateway.send(new RollbackUserCommand(profileCreatedFailedEvent.getProfileUsername(),
                profileCreatedFailedEvent.getRegularUserId(),
                profileCreatedFailedEvent.getUserId()));
    }

    @SagaEventHandler(associationProperty = "username")
    public void handle(UserRollbackEvent userRollbackEvent) {
        System.out.println("Saga finishing!");
        SagaLifecycle.end();
    }
}
