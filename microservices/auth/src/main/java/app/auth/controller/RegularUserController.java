package app.auth.controller;

import app.auth.exception.UserNotFoundException;
import app.auth.model.RegularUser;
import app.auth.model.dto.UserInfoDTO;
import app.auth.util.TokenUtils;
import app.auth.service.RegularUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        try {
            service.register(user);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Void> update(@RequestHeader("Authorization") String auth, @RequestBody RegularUser user) {
        if(!TokenUtils.verify(auth, "USER", "AGENT"))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        service.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RegularUser> getRegularUser(@RequestHeader("Authorization") String auth, @PathVariable long id) {
        if(!TokenUtils.verify(auth, "USER", "ADMIN", "AGENT"))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        RegularUser user;
        try {
            user = service.getRegularUser(id);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //don't put in api gateway
    @GetMapping(value = "ms/{username}")
    public UserInfoDTO get(@PathVariable String username) {
        try {
            return service.get(username);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
