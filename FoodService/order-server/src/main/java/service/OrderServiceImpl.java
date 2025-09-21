package service;

import entity.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import repository.OrderRepository;
import response.OrderResponse;

import java.util.concurrent.CompletableFuture;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    private static final String ORDER_EVENTS_TOPIC = "order-events";
    private static final String ORDER_STATUS_TOPIC = "order-status";

    private OrderRepository orderRepository;

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void addCustomer(Order order) {
        try {
            Order savedOrder = orderRepository.save(order);
            logger.info("Order saved to database with ID: {}", savedOrder.getOrder_id());

            CompletableFuture<SendResult<String, Object>> future =
                    kafkaTemplate.send(ORDER_EVENTS_TOPIC, savedOrder);

            future.whenComplete((result, throwable) -> {
                if (throwable == null) {
                    logger.info("Successfully published order to Kafka topic [{}] with offset=[{}]",
                            ORDER_EVENTS_TOPIC, result.getRecordMetadata().offset());
                } else {
                    logger.error("Failed to publish order to Kafka topic [{}]: {}",
                            ORDER_EVENTS_TOPIC, throwable.getMessage());
                }
            });

        } catch (Exception e) {
            logger.error("Error saving order or publishing to Kafka: {}", e.getMessage());
            throw new RuntimeException("Failed to process order", e);
        }
    }

    @Override
    public Order getCustomerById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public OrderResponse listAllCustomers(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Order> all = orderRepository.findAll(pageable);

        OrderResponse orderResponse = new OrderResponse();

        orderResponse.setCustomers(all.getContent());
        orderResponse.setPageNo(all.getNumber());
        orderResponse.setPageSize(all.getSize());
        orderResponse.setTotalElements(all.getTotalElements());
        orderResponse.setTotalPages(all.getTotalPages());
        orderResponse.setLast(all.isLast());

        return orderResponse;

    }

    @Override
    public void deleteCustomer(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void updateCustomer(Long id, Order order) {
        Order existingOrder = orderRepository.findById(id).orElse(null);
        if (existingOrder != null) {
            existingOrder.setOrder_name(order.getOrder_name());
            existingOrder.setOrder_item(order.getOrder_item());
            existingOrder.setOrder_adress(order.getOrder_adress());
            orderRepository.save(existingOrder);
            kafkaTemplate.send("ORDER_STATUS_TOPIC", existingOrder);

        }
    }

}
