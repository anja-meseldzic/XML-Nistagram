package app.media.controller;

import app.media.dtos.PostInfoDTO;
import app.media.dtos.SearchResultDTO;
import app.media.exception.PostDoesNotExistException;
import app.media.exception.ProfileBlockedException;
import app.media.exception.ProfilePrivateException;
import app.media.service.PostService;
import app.media.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "post")
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(value = "feed")
    public ResponseEntity<List<PostInfoDTO>> getFeed(@RequestHeader("Authorization") String auth) {
        if(!TokenUtils.verify(auth, "USER") && !TokenUtils.verify(auth, "AGENT"))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        String username = TokenUtils.getUsernameFromToken(auth.substring(7));
        return new ResponseEntity<>(postService.getFeed(username), HttpStatus.OK);
    }

    @GetMapping(value = "profile/{username}")
    public ResponseEntity<List<PostInfoDTO>> getForProfile(@PathVariable("username") String profile, @RequestHeader("Authorization") String auth) {
        try {
            if(TokenUtils.verify(auth, "USER") || TokenUtils.verify(auth, "AGENT")) {
                String username = TokenUtils.getUsernameFromToken(auth.substring(7));
                return new ResponseEntity<>(postService.getForProfile(username, profile), HttpStatus.OK);
            }
            if (auth.equals("Bearer null"))
                return new ResponseEntity<>(postService.getForProfile(null, profile), HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (ProfilePrivateException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile is private");
        } catch (ProfileBlockedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You blocked this profile");
        }
    }

    @GetMapping(value = "search/{criterion}")
    public ResponseEntity<List<SearchResultDTO>> search(@PathVariable("criterion") String criterion, @RequestHeader("Authorization") String auth) {
        if(TokenUtils.verify(auth, "ADMIN"))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        if (auth.equals("Bearer null"))
            return new ResponseEntity<>(postService.search(null, criterion), HttpStatus.OK);
        return new ResponseEntity<>(postService.search(TokenUtils.getUsernameFromToken(auth.substring(7)), criterion), HttpStatus.OK);
    }

    @GetMapping(value = "location/{location}")
    public ResponseEntity<List<PostInfoDTO>> getByLocation(@PathVariable("location") String location, @RequestHeader("Authorization") String auth) {
        if(TokenUtils.verify(auth, "ADMIN"))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        if (auth.equals("Bearer null"))
            return new ResponseEntity<>(postService.getAllWithLocation(null, location), HttpStatus.OK);
        return new ResponseEntity<>(postService.getAllWithLocation(TokenUtils.getUsernameFromToken(auth.substring(7)), location), HttpStatus.OK);
    }

    @GetMapping(value = "hashtag/{hashtag}")
    public ResponseEntity<List<PostInfoDTO>> getByHashtag(@PathVariable("hashtag") String hashtag, @RequestHeader("Authorization") String auth) {
        if(TokenUtils.verify(auth, "ADMIN"))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        if (auth.equals("Bearer null"))
            return new ResponseEntity<>(postService.getAllWithHashtag(null, hashtag), HttpStatus.OK);
        return new ResponseEntity<>(postService.getAllWithHashtag(TokenUtils.getUsernameFromToken(auth.substring(7)), hashtag), HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<PostInfoDTO> get(@PathVariable long id, @RequestHeader("Authorization") String auth) {
        try {
            if(TokenUtils.verify(auth, "ADMIN"))
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            if (auth.equals("Bearer null"))
                return new ResponseEntity<>(postService.get(null, id), HttpStatus.OK);
            return new ResponseEntity<>(postService.get(TokenUtils.getUsernameFromToken(auth.substring(7)), id), HttpStatus.OK);
        } catch (PostDoesNotExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "saveFavourite/{postId}")
    public ResponseEntity<String> saveToFavourites(@PathVariable long postId, @RequestHeader("Authorization") String auth) {
    	if(!TokenUtils.verify(auth, "USER")) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		String token = TokenUtils.getToken(auth);
		String loggedInUsername = TokenUtils.getUsernameFromToken(token);
		
		postService.saveToFavourites(postId, loggedInUsername);
		return new ResponseEntity<>("ok", HttpStatus.OK);
	}
    
    @GetMapping(value = "favourites")
    public ResponseEntity<List<PostInfoDTO>> saveToFavourites(@RequestHeader("Authorization") String auth) {
    	if(!TokenUtils.verify(auth, "USER")) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		String token = TokenUtils.getToken(auth);
		String loggedInUsername = TokenUtils.getUsernameFromToken(token);
		return new ResponseEntity<List<PostInfoDTO>>(postService.getFavouritesForProfile(loggedInUsername), HttpStatus.OK);
	}
}
