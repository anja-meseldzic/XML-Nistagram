package app.profile.controller;

import app.profile.service.AuthService;
import app.profile.service.FollowService;
import app.profile.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;
    private final AuthService authService;
    private final FollowService followService;

    @Autowired
    public MessageController(MessageService messageService, AuthService authService, FollowService followService) {
        this.messageService = messageService;
        this.authService = authService;
        this.followService = followService;
    }

    @GetMapping("{sender}/{receiver}")
    public ResponseEntity<Boolean> verify(@PathVariable String sender, @PathVariable String receiver, @RequestHeader("Authorization") String auth) {
        if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(messageService.verifyMessage(sender, receiver), HttpStatus.OK);
    }

    @GetMapping("/follower/followee")
    public ResponseEntity<Boolean> doesFollow(@PathVariable String follower, @PathVariable String followee, @RequestHeader("Authorization") String auth) {
        if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(followService.doesFollow(follower, followee), HttpStatus.OK);
    }
}
