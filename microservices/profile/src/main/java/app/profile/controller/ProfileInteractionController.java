package app.profile.controller;

import app.profile.service.ProfileInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/interaction")
public class ProfileInteractionController {
    private final ProfileInteractionService service;

    @Autowired
    public ProfileInteractionController(ProfileInteractionService service) {
        this.service = service;
    }

    @GetMapping(value = "/block/{blocked}/{blockedBy}")
    public ResponseEntity<Void> block(@PathVariable String blocked, @PathVariable String blockedBy) {
        service.blockProfile(blocked, blockedBy);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/unblock/{blocked}/{blockedBy}")
    public ResponseEntity<Void> unblock(@PathVariable String blocked, @PathVariable String blockedBy) {
        try {
            service.unblockProfile(blocked, blockedBy);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/mute/{muted}/{mutedBy}")
    public ResponseEntity<Void> mute(@PathVariable String muted, @PathVariable String mutedBy) {
        service.muteProfile(muted, mutedBy);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/unmute/{muted}/{mutedBy}")
    public ResponseEntity<Void> unmute(@PathVariable String muted, @PathVariable String mutedBy) {
        try {
            service.unmuteProfile(muted, mutedBy);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
