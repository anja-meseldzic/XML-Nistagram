package app.media.dtos;

public class NewNotificationDTO {
    private NotificationType type;
    private String receiverUsername;
    private String initiatorUsername;
    private String resource;

    public NewNotificationDTO() { }

    public NewNotificationDTO(NotificationType type, String receiverUsername, String initiatorUsername, String resource) {
        this.type = type;
        this.receiverUsername = receiverUsername;
        this.initiatorUsername = initiatorUsername;
        this.resource = resource;
    }

    public NotificationType getType() {
        return type;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public String getInitiatorUsername() {
        return initiatorUsername;
    }

    public String getResource() {
        return resource;
    }
}
