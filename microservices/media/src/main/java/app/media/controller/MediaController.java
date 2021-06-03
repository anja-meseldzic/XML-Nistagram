package app.media.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import app.media.dtos.PostDTO;
import app.media.service.MediaService;



@RestController
@RequestMapping(value = "media")
public class MediaController {
	
	private MediaService mediaService;

    @Autowired
    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }
    
    @PostMapping(value="createPost",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> createPost(@RequestParam(name = "imageFile", required = false) MultipartFile data, @RequestParam(name = "post", required = false) String model) throws JsonMappingException, JsonProcessingException{
	
    	ObjectMapper mapper = new ObjectMapper();
    	PostDTO postDTO = mapper.readValue(model, PostDTO.class);
    	
    	String path = new File("src/main/resources/posts").getAbsolutePath();
		Path filepath = Paths.get(path, data.getOriginalFilename());

		try (OutputStream os = Files.newOutputStream(filepath)) {
		        os.write(data.getBytes());
		    } catch (IOException e2) {
		    	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e2.getMessage());
			}
		
		mediaService.createPost("src/main/resources/posts/" + data.getOriginalFilename(), postDTO);
		
		return new ResponseEntity<>("ok",HttpStatus.OK);
    }
}
