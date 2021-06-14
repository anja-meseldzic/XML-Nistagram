package app.media.controller;

import app.media.dtos.CollectionDTO;
import app.media.dtos.CollectionInfoDTO;
import app.media.dtos.PostInfoDTO;
import app.media.dtos.SearchResultDTO;
import app.media.exception.PostDoesNotExistException;
import app.media.exception.ProfileBlockedException;
import app.media.exception.ProfilePrivateException;
import app.media.service.AuthService;
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
    private AuthService authService;

    @Autowired
    public PostController(PostService postService, AuthService authService) {
        this.postService = postService;
        this.authService = authService;
    }
    
    @GetMapping(value = "dislikedContent")
    public ResponseEntity<List<PostInfoDTO>> getDislikedContent(@RequestHeader("Authorization") String auth) {
        if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT"))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        String username = authService.getUsernameFromToken(TokenUtils.getToken(auth));
        return new ResponseEntity<>(postService.getDislikedContent(username), HttpStatus.OK);
    }
    
    @GetMapping(value = "likedContent")
    public ResponseEntity<List<PostInfoDTO>> getLikedContent(@RequestHeader("Authorization") String auth) {
        if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT"))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        String username = authService.getUsernameFromToken(TokenUtils.getToken(auth));
        return new ResponseEntity<>(postService.getLikedContent(username), HttpStatus.OK);
    }
    
    @GetMapping(value = "feed")
    public ResponseEntity<List<PostInfoDTO>> getFeed(@RequestHeader("Authorization") String auth) {
        if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT"))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        String username = authService.getUsernameFromToken(TokenUtils.getToken(auth));
        return new ResponseEntity<>(postService.getFeed(username), HttpStatus.OK);
    }

    @GetMapping(value = "profile/{username}")
    public ResponseEntity<List<PostInfoDTO>> getForProfile(@PathVariable("username") String profile, @RequestHeader("Authorization") String auth) {
        try {
            if(authService.verify(auth, "USER") || authService.verify(auth, "AGENT")){
                String username = authService.getUsernameFromToken(TokenUtils.getToken(auth));
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
        if(authService.verify(auth, "ADMIN"))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        if (auth.equals("Bearer null"))
            return new ResponseEntity<>(postService.search(null, criterion), HttpStatus.OK);
        return new ResponseEntity<>(postService.search(authService.getUsernameFromToken(TokenUtils.getToken(auth)), criterion), HttpStatus.OK);
    }

    @GetMapping(value = "location/{location}")
    public ResponseEntity<List<PostInfoDTO>> getByLocation(@PathVariable("location") String location, @RequestHeader("Authorization") String auth) {
        if(authService.verify(auth, "ADMIN"))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        if (auth.equals("Bearer null"))
            return new ResponseEntity<>(postService.getAllWithLocation(null, location), HttpStatus.OK);
        return new ResponseEntity<>(postService.getAllWithLocation(authService.getUsernameFromToken(TokenUtils.getToken(auth)), location), HttpStatus.OK);
    }

    @GetMapping(value = "hashtag/{hashtag}")
    public ResponseEntity<List<PostInfoDTO>> getByHashtag(@PathVariable("hashtag") String hashtag, @RequestHeader("Authorization") String auth) {
        if(authService.verify(auth, "ADMIN"))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        if (auth.equals("Bearer null"))
            return new ResponseEntity<>(postService.getAllWithHashtag(null, hashtag), HttpStatus.OK);
        return new ResponseEntity<>(postService.getAllWithHashtag(authService.getUsernameFromToken(TokenUtils.getToken(auth)), hashtag), HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<PostInfoDTO> get(@PathVariable long id, @RequestHeader("Authorization") String auth) {
        try {
            if(authService.verify(auth, "ADMIN"))
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            if (auth.equals("Bearer null"))
                return new ResponseEntity<>(postService.get(null, id), HttpStatus.OK);
            return new ResponseEntity<>(postService.get(authService.getUsernameFromToken(TokenUtils.getToken(auth)), id), HttpStatus.OK);
        } catch (PostDoesNotExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping(value = "saveFavourite/{postId}")
    public ResponseEntity<String> saveToFavourites(@PathVariable long postId, @RequestHeader("Authorization") String auth) {
    	if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT")) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		String token = TokenUtils.getToken(auth);
		String loggedInUsername = authService.getUsernameFromToken(token);
		
		postService.saveToFavourites(postId, loggedInUsername);
		return new ResponseEntity<>("ok", HttpStatus.OK);
	}
    
    @GetMapping(value = "favourites")
    public ResponseEntity<List<PostInfoDTO>> getFavourites(@RequestHeader("Authorization") String auth) {
    	if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT")) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		String token = TokenUtils.getToken(auth);
		String loggedInUsername = authService.getUsernameFromToken(token);
		return new ResponseEntity<List<PostInfoDTO>>(postService.getFavouritesForProfile(loggedInUsername), HttpStatus.OK);
	}
    
    @PostMapping(value = "addToCollection")
    public ResponseEntity<String> addToCollection(@RequestBody CollectionDTO dto, @RequestHeader("Authorization") String auth) {
    	if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT")) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		String token = TokenUtils.getToken(auth);
		String loggedInUsername = authService.getUsernameFromToken(token);
		postService.addFavouritesToCollection(loggedInUsername, dto);
		return new ResponseEntity<>("You have added post to collection " + dto.getName(), HttpStatus.OK);
	}
    
    @GetMapping(value = "collections")
    public ResponseEntity<List<CollectionInfoDTO>> getAllCollections(@RequestHeader("Authorization") String auth) {
    	if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT")) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		String token = TokenUtils.getToken(auth);
		String loggedInUsername = authService.getUsernameFromToken(token);
		return new ResponseEntity<List<CollectionInfoDTO>>(postService.getCollectionsForProfile(loggedInUsername), HttpStatus.OK);
	}
}
