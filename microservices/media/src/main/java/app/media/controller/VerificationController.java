package app.media.controller;

import app.media.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verify")
public class VerificationController {
    private final VerificationService verificationService;

    @Autowired
    public VerificationController(VerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @GetMapping("/post/{id}/{username}")
    public ResponseEntity<Boolean> verifyPostLink(@PathVariable Long id, @PathVariable String username) {
        return new ResponseEntity<>(verificationService.verifyPostLink(id, username), HttpStatus.OK);
    }

    @GetMapping("/story/{author}/{username}")
    public ResponseEntity<Boolean> verifyStoryLink(@PathVariable String author, @PathVariable String username) {
        return new ResponseEntity<>(verificationService.verifyStoryLink(author, username), HttpStatus.OK);
    }
}
