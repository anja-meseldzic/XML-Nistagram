package app.media.controller;

import app.media.dtos.StoryInfoDTO;
import app.media.exception.ProfileBlockedException;
import app.media.exception.ProfilePrivateException;
import app.media.service.AuthService;
import app.media.service.StoryService;
import app.media.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "story")
public class StoryController {

    private StoryService storyService;
    private AuthService authService;

    @Autowired
    public StoryController(StoryService storyService, AuthService authService) {
        this.storyService = storyService;
        this.authService = authService;
    }

    @GetMapping(value = "feed")
    public ResponseEntity<List<StoryInfoDTO>> getFeed(@RequestHeader("Authorization") String auth) {
        if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT"))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        String username = authService.getUsernameFromToken(TokenUtils.getToken(auth));
        return new ResponseEntity<>(storyService.getFeed(username), HttpStatus.OK);
    }

    @GetMapping(value = "profile/{username}")
    public ResponseEntity<List<StoryInfoDTO>> getForProfile(@PathVariable("username") String profile, @RequestHeader("Authorization") String auth) {
        try {
            if(authService.verify(auth, "USER") || authService.verify(auth, "AGENT")) {
                String username = authService.getUsernameFromToken(TokenUtils.getToken(auth));
                return new ResponseEntity<>(storyService.getForProfile(username, profile), HttpStatus.OK);
            }
            else if (auth.equals("Bearer null"))
                return new ResponseEntity<>(storyService.getForProfile(null, profile), HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (ProfilePrivateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile is private");
        } catch (ProfileBlockedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You blocked this profile");
        }
    }
    @GetMapping(value = "allStories")
    public ResponseEntity<List<StoryInfoDTO>> getAllStories(@RequestHeader("Authorization") String auth) {
        if(authService.verify(auth, "USER") || authService.verify(auth, "AGENT")) {
            String username = authService.getUsernameFromToken(TokenUtils.getToken(auth));
            return new ResponseEntity<>(storyService.getAllUserStories(username), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    
    @GetMapping(value = "storyHighlights")
    public ResponseEntity<List<StoryInfoDTO>> getAllHighlights(@RequestHeader("Authorization") String auth) {
        if(authService.verify(auth, "USER") || authService.verify(auth, "AGENT")) {
            String username = authService.getUsernameFromToken(TokenUtils.getToken(auth));
            return new ResponseEntity<>(storyService.getStoryHighlights(username), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    
    @PostMapping(value = "saveToHighlights")
    public ResponseEntity<String> addToHighlights(@RequestBody StoryInfoDTO dto ,@RequestHeader("Authorization") String auth) {
        if(authService.verify(auth, "USER") || authService.verify(auth, "AGENT")) {
            storyService.addToStoryHighlights(dto);
            return new ResponseEntity<>("Successfully saved to story highlights", HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping(value = "camp/{id}")
    public String getLink(@PathVariable("id") long id) {
        return storyService.getLink(id);
    }

    @PostMapping(value = "visit/{id}/{username}")
    public void saveLinkClick(@PathVariable("id") long id, @PathVariable("username") String username) {
        storyService.saveLinkClick(id, username);
    }
}
