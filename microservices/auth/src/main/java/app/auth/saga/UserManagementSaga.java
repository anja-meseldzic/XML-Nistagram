package app.auth.saga;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
public class UserManagementSaga {
    private final transient CommandGateway commandGateway;

    @Autowired
    public UserManagementSaga(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }
}
