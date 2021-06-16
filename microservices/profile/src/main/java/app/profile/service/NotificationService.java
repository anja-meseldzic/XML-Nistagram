package app.profile.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "notification", url = "${app.notification.url}")
public interface NotificationService {
    @PostMapping("settings/profile/{username}")
    boolean createSettings(@PathVariable("username") String username);
    @PostMapping("settings/follow/{followId}")
    boolean createSettings(@PathVariable("followId") long followId);
    @DeleteMapping("settings/follow/{followId}")
    boolean deleteSettings(@PathVariable("followId") long followId);
}
