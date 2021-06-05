package app.media.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

import javax.servlet.http.HttpSession;

import app.media.util.TokenUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.server.ResponseStatusException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.media.dtos.AlbumDTO;
import app.media.dtos.CommentDTO;
import app.media.dtos.PostDTO;
import app.media.exception.PostDoesNotExistException;
import app.media.service.MediaService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


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
	
	@PostMapping(value = "postComment")
	public ResponseEntity<String> postComment(@RequestBody CommentDTO dto)
	{
		try {
			mediaService.postComment(dto);
		} catch (PostDoesNotExistException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		return new ResponseEntity<>("ok",HttpStatus.OK);
	}

    @PostMapping(value="createAlbum")
    public ResponseEntity<Void> uploadFiles(MultipartHttpServletRequest request) throws IOException {
    	ObjectMapper mapper = new ObjectMapper();
    	AlbumDTO albumDTO = mapper.readValue(request.getParameter("album"), AlbumDTO.class);
    	
    	List<MultipartFile> files = new ArrayList<MultipartFile>();
    	Iterator<String> iterator = request.getFileNames();
        while (iterator.hasNext()) {
            MultipartFile file = request.getFile(iterator.next());
            files.add(file);
        }

        if(albumDTO.isPostSelected() == true) {
        	mediaService.createAlbumAsPost(files, albumDTO);
        } else {
        	mediaService.createAlbumAsStory(files, albumDTO);
        }
    	return new ResponseEntity<>(HttpStatus.OK);
    
    }
    @PostMapping(value="createStory",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   	public ResponseEntity<Void> creatStory(@RequestParam(name = "imageFile", required = false) MultipartFile data, @RequestParam(name = "story", required = false) String model) throws JsonMappingException, JsonProcessingException{
    	boolean close = model.equals("true") ? true : false;
		try {
			mediaService.createStory(data, close);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value="createPost",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Void> createPost(@RequestParam(name = "imageFile", required = false) MultipartFile data, @RequestParam(name = "post", required = false) String model) throws JsonMappingException, JsonProcessingException{
    	ObjectMapper mapper = new ObjectMapper();
    	PostDTO postDTO = mapper.readValue(model, PostDTO.class);
		try {
			mediaService.createPost(data, postDTO);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.OK);
    }

	@GetMapping(
			value = "content/{contentName:.+}",
			produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, "video/mp4"}
	)
	public @ResponseBody byte[] getContent(@PathVariable(name = "contentName") String fileName) {
		try {
			return this.mediaService.getContent(fileName);
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
