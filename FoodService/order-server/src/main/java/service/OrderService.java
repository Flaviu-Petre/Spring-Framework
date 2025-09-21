package service;

import entity.Order;
import response.OrderResponse;

public interface OrderService {
    void addCustomer(Order order);
    Order getCustomerById(Long id);
    void deleteCustomer(Long id);
    void updateCustomer(Long id, Order order);
    OrderResponse listAllCustomers(int pageNo, int pageSize, String sortBy, String sortDir);
}
