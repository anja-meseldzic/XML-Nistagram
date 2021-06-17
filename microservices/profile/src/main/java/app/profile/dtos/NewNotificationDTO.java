package app.profile.dtos;

import app.profile.dtos.NotificationType;

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

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public String getInitiatorUsername() {
        return initiatorUsername;
    }

    public void setInitiatorUsername(String initiatorUsername) {
        this.initiatorUsername = initiatorUsername;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
