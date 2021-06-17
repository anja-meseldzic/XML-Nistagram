package app.profile.controller;

import app.profile.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "follow")
public class FollowController {

    private FollowService followService;

    @Autowired
    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @GetMapping(value = "followedBy/{followId}")
    public String getFollowedBy(@PathVariable("followId") long followId) {
        return followService.getFollowedBy(followId);
    }
}
