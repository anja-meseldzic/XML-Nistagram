package rs.ac.uns.ftn.coreapis.events;

import rs.ac.uns.ftn.coreapis.dto.OrderLineItemsDTO;

public class OrderCreatedEvent {

    private Long orderId;
    private Long restaurantId;
    private OrderLineItemsDTO orderLineItemsDTO;

    public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(Long orderId,
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
