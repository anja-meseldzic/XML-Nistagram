package app.notification.controller;

import app.notification.dto.NewNotificationDTO;
import app.notification.dto.NotificationDTO;
import app.notification.service.AuthService;
import app.notification.service.NotificationService;
import app.notification.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "notification")
public class NotificationController {

    private NotificationService notificationService;
    private AuthService authService;

    @Autowired
    public NotificationController(NotificationService notificationService, AuthService authService) {
        this.notificationService = notificationService;
        this.authService = authService;
    }

    @GetMapping
    public List<NotificationDTO> getAll(@RequestHeader("Authorization") String auth) {
        if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT"))
            return null;
        String token = TokenUtils.getToken(auth);
        String username = authService.getUsernameFromToken(token);
        return notificationService.getAll(username);
    }

    @PostMapping
    public void create(@RequestBody NewNotificationDTO dto) {
        this.notificationService.create(dto);
    }
}
