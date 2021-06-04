package app.auth.controller;

import app.auth.model.RegularUser;
import app.auth.util.TokenUtils;
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
    public ResponseEntity<Void> update(@RequestHeader("Authorization") String auth, @RequestBody RegularUser user) {
        if(!TokenUtils.verify(auth, "USER"))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        service.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RegularUser> getRegularUser(@RequestHeader("Authorization") String auth, @PathVariable long id) {
        if(!TokenUtils.verify(auth, "USER", "ADMIN"))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        RegularUser user;
        try {
            user = service.getRegularUser(id);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
