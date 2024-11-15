package bg.sofia.uni.fmi.mjt.eventbus.events;

public class NotificationPayload implements Payload<NotificationPayload> {

    private final String message;
    private final String recipient;

    // Конструктор
    public NotificationPayload(String message, String recipient) {
        if (message == null || recipient == null) {
            throw new IllegalArgumentException("Message and recipient cannot be null!");
        }
        this.message = message;
        this.recipient = recipient;
    }

    // Имплементация на метода getSize
    @Override
    public int getSize() {
        // Връща размера на полезния товар (например общата дължина на съобщението и получателя)
        return message.length() + recipient.length();
    }

    // Имплементация на метода getPayload
    @Override
    public NotificationPayload getPayload() {
        return this;
    }

    // Гетъри за полетата
    public String getMessage() {
        return message;
    }

    public String getRecipient() {
        return recipient;
    }

    // Текстово представяне на полезния товар
    @Override
    public String toString() {
        return "NotificationPayload{" +
            "message='" + message + '\'' +
            ", recipient='" + recipient + '\'' +
            '}';
    }
}
