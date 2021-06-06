package app.media.controller;


import java.io.IOException;
import java.util.Iterator;
import java.util.*;
import app.media.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.server.ResponseStatusException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.media.dtos.AlbumDTO;
import app.media.dtos.AllCommentDTO;
import app.media.dtos.AllReactionsDTO;
import app.media.dtos.CommentDTO;
import app.media.dtos.PostDTO;
import app.media.dtos.RatingDTO;
import app.media.dtos.ReactionsNumberDTO;
import app.media.exception.PostDoesNotExistException;
import app.media.exception.ProfileBlockedException;
import app.media.exception.ProfilePrivateException;
import app.media.service.MediaService;

@RestController
@RequestMapping(value = "media")
public class MediaController {
	
	private MediaService mediaService;

    @Autowired
    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

	@GetMapping(value = "/testAuth")
	public ResponseEntity<String> testAuth(@RequestHeader("Authorization") String auth) {
		if(!TokenUtils.verify(auth, "USER"))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}
	
	@PostMapping(value = "allReactions")
	public ResponseEntity<AllReactionsDTO> getReactions(@RequestBody long id,  @RequestHeader("Authorization") String auth)
	{
		if(!TokenUtils.verify(auth, "USER","AGENT"))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		String token = TokenUtils.getToken(auth);
		String myUsername = TokenUtils.getUsernameFromToken(token);
		
		AllReactionsDTO dto;
		try {
			mediaService.checkProfile(id, myUsername);
			dto = mediaService.getAllReactions(id);
		} catch (PostDoesNotExistException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (ProfilePrivateException e) {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile is private");
		} catch (ProfileBlockedException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You blocked this profile");
		}
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@PostMapping(value = "getReactionsNumber")
	public ResponseEntity<ReactionsNumberDTO> getReactionsNumber(@RequestBody long id,  @RequestHeader("Authorization") String auth)
	{
		if(!TokenUtils.verify(auth, "USER","AGENT"))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		String token = TokenUtils.getToken(auth);
		String myUsername = TokenUtils.getUsernameFromToken(token);
		
		ReactionsNumberDTO dto;
		try {
			mediaService.checkProfile(id, myUsername);
			dto = mediaService.getReactionsNumber(id);
		} catch (PostDoesNotExistException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

		} catch (ProfilePrivateException e) {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile is private");
		} catch (ProfileBlockedException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You blocked this profile");
		}
		return new ResponseEntity<>(dto,HttpStatus.OK);	
	}
	@PostMapping(value = "reactOnPost")
	public ResponseEntity<String> reactOnPost(@RequestBody RatingDTO dto, @RequestHeader("Authorization") String auth)
	{
		if(!TokenUtils.verify(auth, "USER","AGENT"))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		String token = TokenUtils.getToken(auth);
		String username = TokenUtils.getUsernameFromToken(token);
		
		try {
			mediaService.reactOnPost(dto, username);
		} catch (PostDoesNotExistException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		return new ResponseEntity<>("ok",HttpStatus.OK);
	}
	
	@PostMapping(value = "allComments")
	public ResponseEntity<List<AllCommentDTO>> getAllComments(@RequestBody long id,  @RequestHeader("Authorization") String auth)
	{
		if(!TokenUtils.verify(auth, "USER","AGENT"))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		String token = TokenUtils.getToken(auth);
		String myUsername = TokenUtils.getUsernameFromToken(token);
		List<AllCommentDTO>  comments;
		try {
			mediaService.checkProfile(id, myUsername);
			comments = mediaService.getAllComments(id);
		} catch (PostDoesNotExistException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (ProfilePrivateException e) {
			 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile is private");
		} catch (ProfileBlockedException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You blocked this profile");
		}
		 
		return new ResponseEntity<>(comments, HttpStatus.OK);
	}
	
	@PostMapping(value = "postComment")
	public ResponseEntity<String> postComment(@RequestBody CommentDTO dto, @RequestHeader("Authorization") String auth)
	{
		if(!TokenUtils.verify(auth, "USER","AGENT"))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		String token = TokenUtils.getToken(auth);
		String username = TokenUtils.getUsernameFromToken(token);
		
		try {
			mediaService.postComment(dto, username);
		} catch (PostDoesNotExistException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		return new ResponseEntity<>("ok",HttpStatus.OK);
	}

    @PostMapping(value="createAlbum")
    public ResponseEntity<Void> uploadFiles(MultipartHttpServletRequest request, @RequestHeader("Authorization") String auth) throws IOException {
    	if(!TokenUtils.verify(auth, "USER","AGENT"))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		String token = TokenUtils.getToken(auth);
		String username = TokenUtils.getUsernameFromToken(token);
    	
    	ObjectMapper mapper = new ObjectMapper();
    	AlbumDTO albumDTO = mapper.readValue(request.getParameter("album"), AlbumDTO.class);
    	
    	List<MultipartFile> files = new ArrayList<MultipartFile>();
    	Iterator<String> iterator = request.getFileNames();
        while (iterator.hasNext()) {
            MultipartFile file = request.getFile(iterator.next());
            files.add(file);
        }

        if(albumDTO.isPostSelected() == true) {
        	mediaService.createAlbumAsPost(files, albumDTO, username);
        } else {
        	mediaService.createAlbumAsStory(files, albumDTO, username);
        }
    	return new ResponseEntity<>(HttpStatus.OK);
    
    }
    @PostMapping(value="createStory",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   	public ResponseEntity<Void> creatStory(@RequestParam(name = "imageFile", required = false) MultipartFile data, @RequestParam(name = "story", required = false) String model,  @RequestHeader("Authorization") String auth) throws JsonMappingException, JsonProcessingException{
    	if(!TokenUtils.verify(auth, "USER","AGENT"))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		String token = TokenUtils.getToken(auth);
		String username = TokenUtils.getUsernameFromToken(token);
    	
    	boolean close = model.equals("true") ? true : false;
		try {
			mediaService.createStory(data, close, username);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value="createPost",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> createPost(@RequestParam(name = "imageFile", required = false) MultipartFile data, @RequestParam(name = "post", required = false) String model, @RequestHeader("Authorization") String auth) throws JsonMappingException, JsonProcessingException{
    	if(!TokenUtils.verify(auth, "USER","AGENT"))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		String token = TokenUtils.getToken(auth);
		String username = TokenUtils.getUsernameFromToken(token);
    	
    	ObjectMapper mapper = new ObjectMapper();
    	PostDTO postDTO = mapper.readValue(model, PostDTO.class);
		try {
			mediaService.createPost(data, postDTO, username);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.OK);
    }

	@GetMapping(value = "content/{contentName:.+}")
	public @ResponseBody ResponseEntity<UrlResource> getContent(@PathVariable(name = "contentName") String fileName) {
		try {
			UrlResource resource = mediaService.getContent(fileName);
			return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
					.contentType(MediaTypeFactory
							.getMediaType(resource)
							.orElse(MediaType.APPLICATION_OCTET_STREAM))
							.body(this.mediaService.getContent(fileName));
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
