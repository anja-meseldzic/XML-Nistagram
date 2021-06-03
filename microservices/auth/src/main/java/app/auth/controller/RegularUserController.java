package app.auth.controller;

import app.auth.model.RegularUser;
import app.auth.service.RegularUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/update")
    public ResponseEntity<Void> update(@RequestBody RegularUser user) {
        service.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RegularUser> getRegularUser(@PathVariable long id) {
        RegularUser user;
        try {
            user = service.getRegularUser(id);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
