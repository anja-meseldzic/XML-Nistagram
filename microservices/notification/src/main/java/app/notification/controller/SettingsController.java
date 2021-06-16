package app.notification.controller;

import app.notification.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "settings")
public class SettingsController {

    private SettingsService settingsService;

    @Autowired
    public SettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @PostMapping(value = "profile/{username}")
    public void create(@PathVariable("username") String username) {
        settingsService.createSettingsPerProfile(username);
    }

    @PostMapping(value = "follow/{followId}")
    public void create(@PathVariable("followId") long followId) {
        settingsService.createSettingsPerFollow(followId);
    }

    @DeleteMapping(value = "follow/{followId}")
    public void delete(@PathVariable("followId") long followId) {
        settingsService.deleteSettingsPerFollow(followId);
    }
}
