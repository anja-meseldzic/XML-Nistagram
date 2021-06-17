package app.profile.service;

import app.profile.dtos.NewNotificationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notifications", url = "${app.notification.url}")
public interface NotificationService {
    @PostMapping(value="settings/profile/{username}")
    void createSettings(@PathVariable("username") String username);
    @PostMapping(value="settings/follow/{followId}/{profile}")
    void createSettings(@PathVariable("followId") long followId, @PathVariable("profile") String profile);
    @DeleteMapping(value="settings/follow/{followId}")
    void deleteSettings(@PathVariable("followId") long followId);
    @PostMapping(value = "notification")
    void create(@RequestBody NewNotificationDTO dto);
}
