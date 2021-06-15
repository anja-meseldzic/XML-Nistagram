package app.profile.controller;

import app.profile.service.AuthService;
import app.profile.service.ProfileInteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping("/interaction")
public class ProfileInteractionController {
    private final ProfileInteractionService service;
    private final AuthService authService;

    @Autowired
    public ProfileInteractionController(ProfileInteractionService service, AuthService authService) {
        this.service = service;
        this.authService = authService;
    }

    @GetMapping(value = "/block-unblock/{blocked}/{blockedBy}")
    public ResponseEntity<Void> updateBlock(@RequestHeader("Authorization") String auth, @PathVariable String blocked, @PathVariable String blockedBy) {
        if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        service.updateBlock(blocked, blockedBy);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/mute-unmute/{muted}/{mutedBy}")
    public ResponseEntity<Void> updateMute(@RequestHeader("Authorization") String auth, @PathVariable String muted, @PathVariable String mutedBy) {
        if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            service.updateMute(muted, mutedBy);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/muted/{username}")
    public ResponseEntity<Collection<String>> getMuted(@RequestHeader("Authorization") String auth, @PathVariable String username) {
        if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(service.getMutedProfiles(username), HttpStatus.OK);
    }

    @GetMapping(value = "/blocked/{username}")
    public ResponseEntity<Collection<String>> getBlocked(@RequestHeader("Authorization") String auth, @PathVariable String username) {
        if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(service.getBlockedProfiles(username), HttpStatus.OK);
    }
}
