package bg.sofia.uni.fmi.mjt.eventbus.events;

public class OrderPayload implements Payload<OrderPayload> {

    private final String orderId;
    private final String customerName;
    private final double totalAmount;

    // Конструктор
    public OrderPayload(String orderId, String customerName, double totalAmount) {
        if (orderId == null || customerName == null) {
            throw new IllegalArgumentException("Order ID and customer name cannot be null.");
        }
        if (totalAmount <= 0) {
            throw new IllegalArgumentException("Total amount must be greater than zero.");
        }
        this.orderId = orderId;
        this.customerName = customerName;
        this.totalAmount = totalAmount;
    }

    // Имплементация на метода getSize
    @Override
    public int getSize() {
        // Примерен начин за изчисляване на размера на payload
        return orderId.length() + customerName.length() + Double.BYTES;
    }

    // Имплементация на метода getPayload
    @Override
    public OrderPayload getPayload() {
        return this;
    }

    // Гетъри за полетата
    public String getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    // Текстово представяне на обекта
    @Override
    public String toString() {
        return "OrderPayload{" +
            "orderId='" + orderId + '\'' +
            ", customerName='" + customerName + '\'' +
            ", totalAmount=" + totalAmount +
            '}';
    }
}
