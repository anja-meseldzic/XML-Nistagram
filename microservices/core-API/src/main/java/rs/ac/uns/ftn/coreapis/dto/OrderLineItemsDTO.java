package rs.ac.uns.ftn.coreapis.dto;

import java.util.ArrayList;
import java.util.List;

public class OrderLineItemsDTO {

    private List<OrderLineItemDTO> items;

    public OrderLineItemsDTO() {
        this.items = new ArrayList<OrderLineItemDTO>();
    }

    public OrderLineItemsDTO(
            List<OrderLineItemDTO> items
    ) {
        this.items = items;
    }

    public List<OrderLineItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderLineItemDTO> items) {
        this.items = items;
    }

    public void add(OrderLineItemDTO dto) {
        this.items.add(dto);
    }
}
