package app.media.service;

import app.media.dtos.NewNotificationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notifications", url = "${app.notification.url}")
public interface NotificationService {
    @PostMapping(value = "notification")
    void create(@RequestBody NewNotificationDTO dto);
}
