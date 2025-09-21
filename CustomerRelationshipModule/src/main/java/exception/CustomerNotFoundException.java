package exception;

public class CustomerNotFoundException extends RuntimeException {

    private long customerId;

    public CustomerNotFoundException(long customerId) {
        super("Customer not found with id: " + customerId);
        this.customerId = customerId;
    }

    public CustomerNotFoundException(String message) {
        super(message);
    }

    public CustomerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
}