package app.notification.dto;

public class NotificationDTO {
    private String text;
    private String resource;
    private String type;

    public NotificationDTO(String text, String resource, String type) {
        this.text = text;
        this.resource = resource;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public String getResource() {
        return resource;
    }

    public String getType() {
        return type;
    }
}
