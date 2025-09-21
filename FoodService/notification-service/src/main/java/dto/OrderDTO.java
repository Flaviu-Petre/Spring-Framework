package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderDTO {

    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("order_name")
    private String orderName;

    @JsonProperty("order_item")
    private String orderItem;

    @JsonProperty("order_adress") // Note: keeping original typo for compatibility
    private String orderAddress;

    // Default constructor for JSON deserialization
    public OrderDTO() {}

    public OrderDTO(Long orderId, String orderName, String orderItem, String orderAddress) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.orderItem = orderItem;
        this.orderAddress = orderAddress;
    }

    // Getters and Setters
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(String orderItem) {
        this.orderItem = orderItem;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId=" + orderId +
                ", orderName='" + orderName + '\'' +
                ", orderItem='" + orderItem + '\'' +
                ", orderAddress='" + orderAddress + '\'' +
                '}';
    }
}