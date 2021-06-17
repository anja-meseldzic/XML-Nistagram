package app.notification.service;

import app.notification.dto.NewNotificationDTO;
import app.notification.dto.NotificationDTO;

import java.util.List;

public interface NotificationService {

    List<NotificationDTO> getAll(String requestedBy);

    void create(NewNotificationDTO dto);
}
