package rs.ac.uns.ftn.coreapis.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import rs.ac.uns.ftn.coreapis.dto.OrderLineItemsDTO;

public class CreateOrderCommand {

    @TargetAggregateIdentifier
    private Long orderId;
    private Long restaurantId;
    private OrderLineItemsDTO orderLineItemsDTO;

    public CreateOrderCommand() {}

    public CreateOrderCommand(Long orderId,
                              Long restaurantId,
                              OrderLineItemsDTO orderLineItemsDTO) {
        this.orderId = orderId;
        this.restaurantId = restaurantId;
        this.orderLineItemsDTO = orderLineItemsDTO;
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
