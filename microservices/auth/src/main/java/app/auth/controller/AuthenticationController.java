package app.auth.controller;

import app.auth.dto.TargetGroup;
import app.auth.model.dto.LoginDTO;
import app.auth.service.AuthenticationService;
import app.auth.service.RegularUserService;
import app.auth.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final RegularUserService userService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, RegularUserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        String token;
        try {
            token = authenticationService.login(loginDTO.getUsername(), loginDTO.getPassword());
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping(value = "{token}/{role}")
    public ResponseEntity<Boolean> verify(@PathVariable("token") String token, @PathVariable("role") String role) {
        return new ResponseEntity<>(TokenUtils.verify(token, role), HttpStatus.OK);
    }

    @GetMapping(value = "{token}")
    public ResponseEntity<String> getUsernameFromToken(@PathVariable("token") String token) {
        return new ResponseEntity<>(TokenUtils.getUsernameFromToken(token), HttpStatus.OK);
    }

    @GetMapping("target-group")
    List<String> getTargetGroup(@RequestBody TargetGroup targetGroup) {
        return userService.getByTargetGroup(targetGroup);
    }
}
