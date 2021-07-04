package app.message.controller;

import app.message.model.Message;
import app.message.service.MediaMessageService;
import app.message.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;
    private final MediaMessageService mediaMessageService;

    @Autowired
    public MessageController(MessageService messageService, MediaMessageService mediaMessageService) {
        this.messageService = messageService;
        this.mediaMessageService = mediaMessageService;
    }

    @GetMapping("/{firstPeer}/{secondPeer}")
    public ResponseEntity<Collection<Message>> getByUser(@PathVariable String firstPeer, @PathVariable String secondPeer) {
        return new ResponseEntity<>(messageService.getUserMessages(firstPeer, secondPeer), HttpStatus.OK);
    }

    @PostMapping("/share")
    public ResponseEntity<Void> createShareMessage(@RequestBody Message message) {
        messageService.create(message);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> create(@RequestParam(name = "imageFile", required = false) MultipartFile data, @RequestParam(name = "post", required = false) String model, @RequestHeader("Authorization") String auth) throws JsonProcessingException {
        //TODO AUTHORIZATION

        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(model, Message.class);

        try {
            return new ResponseEntity<>(mediaMessageService.createMultimediaMessage(data, message), HttpStatus.CREATED);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }


    @GetMapping(value = "content/{contentName:.+}")
    public @ResponseBody
    ResponseEntity<UrlResource> getContent(@PathVariable(name = "contentName") String fileName) {
        try {
            UrlResource resource = mediaMessageService.getContent(fileName);
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .contentType(MediaTypeFactory
                            .getMediaType(resource)
                            .orElse(MediaType.APPLICATION_OCTET_STREAM))
                    .body(mediaMessageService.getContent(fileName));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> seeMessage(@PathVariable Long id) {
        try {
            mediaMessageService.seeMessage(id);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
