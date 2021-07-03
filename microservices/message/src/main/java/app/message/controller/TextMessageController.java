package app.message.controller;

import app.message.model.TextMessage;
import app.message.service.TextMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/txt")
public class TextMessageController {
    private final TextMessageService textMessageService;

    @Autowired
    public TextMessageController(TextMessageService textMessageService) {
        this.textMessageService = textMessageService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody TextMessage message) {
        textMessageService.create(message);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
