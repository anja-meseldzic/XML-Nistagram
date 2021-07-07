package rs.ac.uns.ftn.coreapis.events;

public class TicketCreatedFailedEvent {

    private String ticketAggregateId;

    private Long orderId;
    private String reason;

    public TicketCreatedFailedEvent() {}

    public TicketCreatedFailedEvent(String ticketAggregateId,
                                    Long orderId, String reason) {
        this.ticketAggregateId = ticketAggregateId;
        this.orderId = orderId;
        this.reason = reason;
    }

    public String getTicketAggregateId() {
        return ticketAggregateId;
    }

    public void setTicketAggregateId(String ticketAggregateId) {
        this.ticketAggregateId = ticketAggregateId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
