package app.media.controller;

import app.media.dtos.StoryInfoDTO;
import app.media.exception.ProfileBlockedException;
import app.media.exception.ProfilePrivateException;
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

    @Autowired
    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping(value = "feed")
    public ResponseEntity<List<StoryInfoDTO>> getFeed(@RequestHeader("Authorization") String auth) {
        if(!TokenUtils.verify(auth, "USER") && !TokenUtils.verify(auth, "AGENT"))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        String username = TokenUtils.getUsernameFromToken(auth.substring(7));
        return new ResponseEntity<>(storyService.getFeed(username), HttpStatus.OK);
    }

    @GetMapping(value = "profile/{username}")
    public ResponseEntity<List<StoryInfoDTO>> getForProfile(@PathVariable("username") String profile, @RequestHeader("Authorization") String auth) {
        try {
            if(TokenUtils.verify(auth, "USER") || TokenUtils.verify(auth, "AGENT")) {
                String username = TokenUtils.getUsernameFromToken(auth.substring(7));
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
    public ResponseEntity<List<StoryInfoDTO>> getForProfile(@RequestHeader("Authorization") String auth) {
  
            if(TokenUtils.verify(auth, "USER") || TokenUtils.verify(auth, "AGENT")) {
                String username = TokenUtils.getUsernameFromToken(auth.substring(7));
                return new ResponseEntity<>(storyService.getAllUserStories(username), HttpStatus.OK);
            }
            else
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
      
    }
}
