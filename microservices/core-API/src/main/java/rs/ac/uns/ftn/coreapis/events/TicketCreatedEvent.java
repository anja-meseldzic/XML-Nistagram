package rs.ac.uns.ftn.coreapis.events;

import rs.ac.uns.ftn.coreapis.dto.OrderLineItemsDTO;

public class TicketCreatedEvent {

    private String ticketAggregateId;

    public TicketCreatedEvent() {
    }

    public TicketCreatedEvent(String ticketAggregateId) {
        this.ticketAggregateId = ticketAggregateId;
    }

    public String getTicketAggregateId() {
        return ticketAggregateId;
    }

    public void setTicketAggregateId(String ticketAggregateId) {
        this.ticketAggregateId = ticketAggregateId;
    }
}
