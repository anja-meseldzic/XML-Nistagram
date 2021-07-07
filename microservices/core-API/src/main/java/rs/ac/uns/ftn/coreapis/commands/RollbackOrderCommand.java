package rs.ac.uns.ftn.coreapis.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class RollbackOrderCommand {

    @TargetAggregateIdentifier
    private Long orderId;
    private String status;

    public RollbackOrderCommand() {}

    public RollbackOrderCommand(Long orderId,
                                String status) {
        this.orderId = orderId;
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
