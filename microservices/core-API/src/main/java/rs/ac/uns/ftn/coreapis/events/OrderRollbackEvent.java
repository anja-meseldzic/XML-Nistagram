package rs.ac.uns.ftn.coreapis.events;

public class OrderRollbackEvent {

    private Long orderId;

    public OrderRollbackEvent() {}

    public OrderRollbackEvent(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
