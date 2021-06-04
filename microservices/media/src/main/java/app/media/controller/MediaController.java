package app.media.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import app.media.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.server.ResponseStatusException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.media.dtos.AlbumDTO;
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

	@GetMapping(value = "/testAuth")
	public ResponseEntity<String> testAuth(@RequestHeader("Authorization") String auth) {
		if(!TokenUtils.verify(auth, "USER"))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}
	
    @PostMapping(value="createAlbum")
    public ResponseEntity<String> uploadFiles(MultipartHttpServletRequest request) throws IOException {
    	ObjectMapper mapper = new ObjectMapper();
    	AlbumDTO albumDTO = mapper.readValue(request.getParameter("album"), AlbumDTO.class);
    	
    	Set<String> fileNames = new HashSet<String>();
    	String path = new File("src/main/resources/albums").getAbsolutePath();
    	Iterator<String> iterator = request.getFileNames();
        MultipartFile multipartFile = null;
        while (iterator.hasNext()) {
            multipartFile = request.getFile(iterator.next());
            fileNames.add("src/main/resources/albums/" + multipartFile.getOriginalFilename());
            Path filepath = Paths.get(path, multipartFile.getOriginalFilename());
            try (OutputStream os = Files.newOutputStream(filepath)) {
		        os.write(multipartFile.getBytes());
		    } catch (IOException e2) {
		    	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e2.getMessage());
			}
        }
        if(albumDTO.isPostSelected() == true) {
        	mediaService.createAlbumAsPost(fileNames, albumDTO);
        } else {
        	mediaService.createAlbumAsStory(fileNames, albumDTO);
        }
    	return new ResponseEntity<>("ok",HttpStatus.OK);
    
    }
    @PostMapping(value="createStory",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
   	public ResponseEntity<String> creatStory(@RequestParam(name = "imageFile", required = false) MultipartFile data, @RequestParam(name = "story", required = false) String model) throws JsonMappingException, JsonProcessingException{
    	boolean close = model.equals("true") ? true : false; 
    	String path = new File("src/main/resources/stories").getAbsolutePath();
		Path filepath = Paths.get(path, data.getOriginalFilename());

		try (OutputStream os = Files.newOutputStream(filepath)) {
		        os.write(data.getBytes());
		    } catch (IOException e2) {
		    	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e2.getMessage());
			}
		mediaService.createStory("src/main/resources/stories/" + data.getOriginalFilename(), close);
		
    	return new ResponseEntity<>("ok",HttpStatus.OK);
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
