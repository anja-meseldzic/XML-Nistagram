package rs.ac.uns.ftn.coreapis.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import rs.ac.uns.ftn.coreapis.dto.OrderLineItemsDTO;

public class CreateTicketCommand {

    @TargetAggregateIdentifier
    private String ticketAggregateId;

    private Long orderId;
    private Long restaurantId;
    private OrderLineItemsDTO orderLineItemsDTO;

    public CreateTicketCommand() {
    }

    public CreateTicketCommand(String ticketAggregateId,
                               Long orderId,
                               Long restaurantId,
                               OrderLineItemsDTO orderLineItemsDTO) {
        this.ticketAggregateId = ticketAggregateId;
        this.orderId = orderId;
        this.restaurantId = restaurantId;
        this.orderLineItemsDTO = orderLineItemsDTO;
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

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public OrderLineItemsDTO getOrderLineItemsDTO() {
        return orderLineItemsDTO;
    }

    public void setOrderLineItemsDTO(OrderLineItemsDTO orderLineItemsDTO) {
        this.orderLineItemsDTO = orderLineItemsDTO;
    }
}
