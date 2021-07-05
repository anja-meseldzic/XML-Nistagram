package app.campaign.controller;

import app.campaign.model.AgeGroup;
import app.campaign.model.Gender;
import app.campaign.repository.AgeGroupRepository;
import app.campaign.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "target-group")
public class TargetGroupController {

    private AuthService authService;
    private AgeGroupRepository ageGroupRepository;

    @Autowired
    public TargetGroupController(AuthService authService, AgeGroupRepository ageGroupRepository) {
        this.authService = authService;
        this.ageGroupRepository = ageGroupRepository;
    }

    @GetMapping(value = "age")
    public List<AgeGroup> getAgeGroups(@RequestHeader("Authorization") String auth) {
        if(!authService.verify(auth, "AGENT"))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
        return ageGroupRepository.findAll();
    }

    @GetMapping(value = "age/{id}")
    public AgeGroup getAgeGroup(@PathVariable("id") long id, @RequestHeader("Authorization") String auth) {
        if(!authService.verify(auth, "AGENT"))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
        return ageGroupRepository.findAll().stream().filter(a -> a.getId() == id).findFirst().orElse(null);
    }

    @GetMapping(value = "gender")
    public List<String> getGenders(@RequestHeader("Authorization") String auth) {
        if(!authService.verify(auth, "AGENT"))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unauthorized");
        return Arrays.stream(Gender.values())
                .map(g -> g.toString())
                .collect(Collectors.toList());
    }
}
