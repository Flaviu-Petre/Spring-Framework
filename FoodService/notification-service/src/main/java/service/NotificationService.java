package service;

import dto.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public void processOrderNotification(OrderDTO order) {
        try {
            String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);

            logger.info("=== ORDER NOTIFICATION PROCESSING ===");
            logger.info("Timestamp: {}", timestamp);
            logger.info("Processing order notification for: {}", order);

            String notificationMessage = generateNotificationMessage(order, timestamp);

            sendNotification(order.getOrderName(), notificationMessage);

            logger.info("Order notification sent successfully for Order ID: {}", order.getOrderId());

        } catch (Exception e) {
            logger.error("Failed to process notification for Order ID: {} - Error: {}",
                    order.getOrderId(), e.getMessage(), e);
            throw new RuntimeException("Notification processing failed", e);
        }
    }

    private String generateNotificationMessage(OrderDTO order, String timestamp) {
        return String.format(
                "🍕 Order Confirmation - EatsNow Restaurant\n" +
                        "───────────────────────────────────────\n" +
                        "Dear %s,\n\n" +
                        "Your order has been received and is being processed!\n\n" +
                        "Order Details:\n" +
                        "   • Order ID: %s\n" +
                        "   • Item(s): %s\n" +
                        "   • Delivery Address: %s\n" +
                        "   • Order Time: %s\n\n" +
                        "Status: Order Accepted\n" +
                        "⏱Estimated Delivery: 30-45 minutes\n\n" +
                        "Thank you for choosing EatsNow!\n" +
                        "───────────────────────────────────────",
                order.getOrderName(),
                order.getOrderId(),
                order.getOrderItem(),
                order.getOrderAddress(),
                timestamp
        );
    }

    private void sendNotification(String customerName, String message) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        logger.info("NOTIFICATION SENT TO: {}", customerName);
        logger.info("MESSAGE CONTENT:\n{}", message);

        logger.info("SMS Notification: Sent");
        logger.info("Email Notification: Sent");
        logger.info("Push Notification: Sent");
    }

    public void processOrderStatusUpdate(OrderDTO order, String status) {
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);

        logger.info("=== ORDER STATUS UPDATE ===");
        logger.info("Order ID: {} | Status: {} | Time: {}",
                order.getOrderId(), status, timestamp);

        String statusMessage = generateStatusUpdateMessage(order, status, timestamp);
        sendNotification(order.getOrderName(), statusMessage);
    }

    private String generateStatusUpdateMessage(OrderDTO order, String status, String timestamp) {
        return String.format(
                "%s Order Update - EatsNow\n" +
                        "───────────────────────────\n" +
                        "Dear %s,\n\n" +
                        "Order #%s Status: %s\n" +
                        "Updated at: %s\n\n" +
                        "Thank you for your patience!",
                 order.getOrderName(), order.getOrderId(), status, timestamp
        );
    }
}