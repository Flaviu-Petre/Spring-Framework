package entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "customers")
public class Order {

    //region Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long order_id;

    @Column(name = "cust_name", nullable = false)
    private String order_name;

    @Column(name = "cust_item", nullable = false)
    private String order_item;

    @Column(name = "cust_adress", nullable = false)
    private String order_adress;
    //endregion

    //region Getters

    public Long getOrder_id() {
        return order_id;
    }

    public String getOrder_name() {
        return order_name;
    }

    public String getOrder_item() {
        return order_item;
    }

    public String getOrder_adress() {
        return order_adress;
    }

    //endregion

    //region Setters

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public void setOrder_item(String order_item) {
        this.order_item = order_item;
    }

    public void setOrder_adress(String order_adress) {
        this.order_adress = order_adress;
    }


    //endregion

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (!order_id.equals(order.order_id)) return false;
        if (!order_name.equals(order.order_name)) return false;
        if (!order_item.equals(order.order_item)) return false;
        return order_adress.equals(order.order_adress);
    }

    @Override
    public String toString() {
        return "UpdatedOrder{" +
                "orderId=" + order_id +
                ", name='" + order_name + '\'' +
                ", item='" + order_item + '\'' +
                ", adress=" + order_adress + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(order_id, order_name, order_item, order_adress);
    }

}
