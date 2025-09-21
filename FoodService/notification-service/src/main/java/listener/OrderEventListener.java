package listener;

import dto.OrderDTO;
import service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    private static final Logger logger = LoggerFactory.getLogger(OrderEventListener.class);

    @Autowired
    private NotificationService notificationService;
    
    @KafkaListener(
            topics = "order-events",
            groupId = "notification-service-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleOrderEvents(
            @Payload OrderDTO order,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset,
            Acknowledgment acknowledgment) {

        try {
            logger.info(" KAFKA MESSAGE RECEIVED");
            logger.info("Topic: {} | Partition: {} | Offset: {}", topic, partition, offset);
            logger.info("Order Data: {}", order);
            
            if (order == null || order.getOrderId() == null) {
                logger.warn("  Received invalid order data: {}", order);
                acknowledgment.acknowledge(); 
                return;
            }
            
            notificationService.processOrderNotification(order);
            
            acknowledgment.acknowledge();

            logger.info(" Successfully processed and acknowledged order: {}", order.getOrderId());

        } catch (Exception e) {
            logger.error(" Error processing order event: {} - Error: {}",
                    order != null ? order.getOrderId() : "unknown", e.getMessage(), e);
            
            acknowledgment.acknowledge();
        }
    }

    @KafkaListener(
            topics = "order-status",
            groupId = "notification-service-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleOrderStatusUpdates(
            @Payload OrderDTO order,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(value = "status", required = false) String status,
            Acknowledgment acknowledgment) {

        try {
            logger.info(" ORDER STATUS UPDATE RECEIVED");
            logger.info("Topic: {} | Order ID: {} | Status: {}", topic, order.getOrderId(), status);

            if (order == null || order.getOrderId() == null) {
                logger.warn("  Received invalid order status update: {}", order);
                acknowledgment.acknowledge();
                return;
            }

            String orderStatus = status != null ? status : "Processing";
            notificationService.processOrderStatusUpdate(order, orderStatus);

            acknowledgment.acknowledge();
            logger.info(" Successfully processed status update for order: {}", order.getOrderId());

        } catch (Exception e) {
            logger.error(" Error processing order status update: {} - Error: {}",
                    order != null ? order.getOrderId() : "unknown", e.getMessage(), e);
            acknowledgment.acknowledge();
        }
    }
}