package app.auth.controller;

import app.auth.model.RegularUser;
import app.auth.service.RegularUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/regulars")
public class RegularUserController {
    private final RegularUserService service;

    @Autowired
    public RegularUserController(RegularUserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody RegularUser user) {
        service.register(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
