package app.notification.controller;

import app.notification.model.NotificationSettingsPerFollow;
import app.notification.model.NotificationSettingsPerProfile;
import app.notification.service.AuthService;
import app.notification.service.SettingsService;
import app.notification.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "settings")
public class SettingsController {

    private SettingsService settingsService;
    private AuthService authService;

    @Autowired
    public SettingsController(SettingsService settingsService, AuthService authService) {
        this.settingsService = settingsService;
        this.authService = authService;
    }

    @GetMapping(value = "profile")
    public NotificationSettingsPerProfile getForProfile(@RequestHeader("Authorization") String auth) {
        if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT"))
            return null;
        String token = TokenUtils.getToken(auth);
        String username = authService.getUsernameFromToken(token);
        return settingsService.get(username);
    }

    @GetMapping(value = "follow")
    public List<NotificationSettingsPerFollow> getForFollow(@RequestHeader("Authorization") String auth) {
        if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT"))
            return null;
        String token = TokenUtils.getToken(auth);
        String username = authService.getUsernameFromToken(token);
        return settingsService.getByProfile(username);
    }

    @PutMapping(value = "profile")
    public void update(@RequestBody NotificationSettingsPerProfile settings, @RequestHeader("Authorization") String auth) {
        if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT"))
            return;
        String token = TokenUtils.getToken(auth);
        String username = authService.getUsernameFromToken(token);
        settingsService.update(settings, username);
    }

    @PutMapping(value = "follow")
    public void update(@RequestBody NotificationSettingsPerFollow settings, @RequestHeader("Authorization") String auth) {
        if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT"))
            return;
        String token = TokenUtils.getToken(auth);
        String username = authService.getUsernameFromToken(token);
        settingsService.update(settings, username);
    }

    @PostMapping(value = "profile/{username}")
    public void create(@PathVariable("username") String username) {
        settingsService.createSettingsPerProfile(username);
    }

    @PostMapping(value = "follow/{followId}/{profile}")
    public void create(@PathVariable("followId") long followId, @PathVariable("profile") String profile) {
        settingsService.createSettingsPerFollow(followId, profile);
    }

    @DeleteMapping(value = "follow/{followId}")
    public void delete(@PathVariable("followId") long followId) {
        settingsService.deleteSettingsPerFollow(followId);
    }
}
