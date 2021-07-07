package app.profile.controller;

import app.profile.dtos.ProfileInfoDTO;
import app.profile.exception.ProfileNotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import app.profile.model.Profile;
import app.profile.service.AuthService;
import app.profile.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import app.profile.model.dto.FollowRequestDto;
import app.profile.model.dto.FollowerDto;
import app.profile.model.dto.ProfileVerificationRequestDTO;
import app.profile.service.ProfileService;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "profile")
public class ProfileContoller {

	private ProfileService profileService;
	private AuthService authService;
	
	@Autowired
	public ProfileContoller(ProfileService profileService, AuthService authService) {
		this.profileService = profileService;
		this.authService = authService;
	}

	@PostMapping(value = "update")
	public ResponseEntity<Void> update(@RequestHeader("Authorization") String auth, @RequestBody Profile profile) {
		if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT")) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		this.profileService.update(profile);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value = "follow/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> followProfile(@RequestHeader("Authorization") String auth ,@PathVariable String username){
		if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT")) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		String token = TokenUtils.getToken(auth);
		String loggedInUsername = authService.getUsernameFromToken(token);
		int followerCount = profileService.followProfile(username, loggedInUsername); 
		return new ResponseEntity<>(followerCount, HttpStatus.OK);
	}
	
	@GetMapping(value = "unfollow/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> unfollowProfile(@RequestHeader("Authorization") String auth,@PathVariable String username){
		if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT"))  {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		String loggedInUsername = authService.getUsernameFromToken(TokenUtils.getToken(auth));
		int followerCount = profileService.unfollowProfile(username, loggedInUsername); 
		return new ResponseEntity<>(followerCount, HttpStatus.OK);
	}

	
	@GetMapping(value = "followRequest/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<FollowRequestDto>> getFollowRequests(@RequestHeader("Authorization") String auth,@PathVariable String username){
		if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT")) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		Set<FollowRequestDto> requests = profileService.getFollowRequests(username);
		return new ResponseEntity<>(requests, HttpStatus.OK);
	}
	
	@GetMapping(value = "acceptRequest/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<FollowRequestDto>> acceptRequest(@RequestHeader("Authorization") String auth,@PathVariable String username){
		if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT")) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		String loggedInUsername = authService.getUsernameFromToken(TokenUtils.getToken(auth));
		Set<FollowRequestDto> requests = profileService.acceptRequest(username, loggedInUsername);
		return new ResponseEntity<>(requests, HttpStatus.OK);
	}
	
	@GetMapping(value = "deleteRequest/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<FollowRequestDto>> deleteRequest(@RequestHeader("Authorization") String auth,@PathVariable String username){
		if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT")) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		String loggedInUsername = authService.getUsernameFromToken(TokenUtils.getToken(auth));
		Set<FollowRequestDto> requests = profileService.deleteRequest(username, loggedInUsername);
		return new ResponseEntity<>(requests, HttpStatus.OK);
	}
	
	@GetMapping(value = "following/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FollowerDto>> getFollowing(@RequestHeader("Authorization") String auth,@PathVariable String username){
		if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT")) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		List<FollowerDto> following = profileService.getFollowing(username);
		return new ResponseEntity<>(following, HttpStatus.OK);
	}
	
	@GetMapping(value = "followers/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<FollowerDto>> getFollowers(@RequestHeader("Authorization") String auth,@PathVariable String username){
		if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT")) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		List<FollowerDto> followers = profileService.getFollowers(username);
		return new ResponseEntity<>(followers, HttpStatus.OK);
	}

	@PostMapping("/{username}")
	public ResponseEntity<Void> createFromUser(@PathVariable String username) {
		profileService.createFromUser(username);
		return new ResponseEntity<>(HttpStatus.CREATED);

	}

	@GetMapping(value = "{username}")
	public ResponseEntity<ProfileInfoDTO> getInfo(@PathVariable String username, @RequestHeader("Authorization") String auth) {
		try {
			String requestedBy = null;
			if (!auth.equals("Bearer null"))
				requestedBy = authService.getUsernameFromToken(auth.substring(7));
			return new ResponseEntity<>(profileService.getProfile(requestedBy, username), HttpStatus.OK);
		} catch (ProfileNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "one/{username}")
	public ResponseEntity<Profile> get(@RequestHeader("Authorization") String auth, @PathVariable String username) {
		if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT")) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		try {
			Profile profile = profileService.get(username);
			return new ResponseEntity<>(profile, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	//don't put in api gateway
	@GetMapping(value = "ms")
	public List<String> getAll() { return profileService.getAll(); }

	//don't put in api gateway
	@GetMapping(value = "ms/followers/{profile}")
	public List<String> getFollowerss(@PathVariable String profile) {
		return profileService.getFollowerss(profile);
	}

	//don't put in api gateway
	@GetMapping(value = "ms/following/{profile}")
	public List<String> getFollowingg(@PathVariable String profile) {
		return profileService.getFollowingg(profile);
	}

	//don't put in api gateway
	@GetMapping(value = "ms/muted/{profile}")
	public List<String> getMuted(@PathVariable String profile) {
		return profileService.getMuted(profile);
	}

	//don't put in api gateway
	@GetMapping(value = "ms/blocked/{profile}")
	public List<String> getBlocked(@PathVariable String profile) {
		return profileService.getBlocked(profile);
	}

	//don't put in api gateway
	@GetMapping(value = "ms/close/{profile}")
	public List<String> getCloseFriends(@PathVariable String profile) {
		return profileService.getCloseFriends(profile);
	}

	//don't put in api gateway
	@GetMapping(value = "ms/public/{profile}")
	public boolean isPublic(@PathVariable String profile) {
		try {
			return profileService.isPublic(profile);
		} catch (ProfileNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	//don't put in api gateway
		@GetMapping(value = "ms/deactivate/{username}")
		public void deactivateProfile(@PathVariable String username) {
			 profileService.deactivateProfile(username);
		}
		
	//don't put in api gateway
		@GetMapping(value = "ms/getAllInactiveProfiles")
		public List<String> getAllInactiveProfiles() {
	    	return profileService.getAllInactiveProfiles();
	}	

	@PostMapping(value = "addCloseFriend")
	public ResponseEntity<String> addCloseFriend(@RequestBody String usernameOfFriend,  @RequestHeader("Authorization") String auth)
	{
		if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT"))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		String token = TokenUtils.getToken(auth);
		String myUsername = authService.getUsernameFromToken(token);

		String message = profileService.addCloseFriend(myUsername, usernameOfFriend);
		
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
	@GetMapping(value = "getCloseFriends")
	public ResponseEntity<List<String>> getCloseFriendsForProfile(@RequestHeader("Authorization") String auth) {
		if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT"))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		String token = TokenUtils.getToken(auth);
		String myUsername = authService.getUsernameFromToken(token);
		
		List<String> close = profileService.getCloseFriends(myUsername);
		return new ResponseEntity<>(close, HttpStatus.OK);
	}
	@PostMapping(value = "removeCloseFriend")
	public ResponseEntity<String> removeCloseFriend(@RequestBody String usernameOfFriend,  @RequestHeader("Authorization") String auth)
	{
		if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT"))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		String token = TokenUtils.getToken(auth);
		String myUsername = authService.getUsernameFromToken(token);

		String message = profileService.removeCloseFriend(myUsername, usernameOfFriend);
		
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
	@PostMapping(value="verify-profile",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> verifyProfile(@RequestParam(name = "imageFile", required = false) MultipartFile data, @RequestParam(name = "request", required = false) String request, @RequestHeader("Authorization") String auth) throws JsonMappingException, JsonProcessingException{
		if(!authService.verify(auth, "USER") && !authService.verify(auth, "AGENT"))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		String token = TokenUtils.getToken(auth);
		String username = authService.getUsernameFromToken(token);
    	
    	ObjectMapper mapper = new ObjectMapper();
    	ProfileVerificationRequestDTO requestDTO = mapper.readValue(request, ProfileVerificationRequestDTO.class);
    	String message = "";
		try {
			message = profileService.saveRequest(data, requestDTO, username);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return new ResponseEntity<>(message, HttpStatus.OK);
    }
	
	@GetMapping(value = "verification-requests")
	public ResponseEntity<List<ProfileVerificationRequestDTO>> getVerificationRequests(@RequestHeader("Authorization") String auth) {
		if(!authService.verify(auth, "ADMIN"))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	
		List<ProfileVerificationRequestDTO> verificationRequests = profileService.getVerificationRequests();
		return new ResponseEntity<>(verificationRequests, HttpStatus.OK);
	}
	
	@GetMapping(value = "verify/{id}")
	public ResponseEntity<List<ProfileVerificationRequestDTO>> acceptVerification(@RequestHeader("Authorization") String auth,@PathVariable long id) {
		if(!authService.verify(auth, "ADMIN"))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	
		List<ProfileVerificationRequestDTO> verificationRequests = profileService.verify(id);
		return new ResponseEntity<>(verificationRequests, HttpStatus.OK);
	}
	
	@GetMapping(value = "delete-ver-request/{id}")
	public ResponseEntity<List<ProfileVerificationRequestDTO>> deleteVerificationRequest(@RequestHeader("Authorization") String auth,@PathVariable long id) {
		if(!authService.verify(auth, "ADMIN"))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	
		List<ProfileVerificationRequestDTO> verificationRequests = profileService.deleteVerification(id);
		return new ResponseEntity<>(verificationRequests, HttpStatus.OK);
	}
	
	@GetMapping(value = "content/{contentName:.+}")
	public @ResponseBody ResponseEntity<UrlResource> getContent(@PathVariable(name = "contentName") String fileName) {
		try {
			UrlResource resource = profileService.getContent(fileName);
			return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
					.contentType(MediaTypeFactory
							.getMediaType(resource)
							.orElse(MediaType.APPLICATION_OCTET_STREAM))
							.body(this.profileService.getContent(fileName));
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@PostMapping("/activeProfile/{username}")
	public ResponseEntity<Boolean> isProfileActive(@PathVariable String username) {
		boolean isActive = profileService.isProfileActive(username);
		return new ResponseEntity<>(isActive,HttpStatus.OK);

	}
	
	@GetMapping(value = "/influencers")
	public ResponseEntity<ArrayList<String>> getInfluencers(@RequestHeader("Authorization") String auth)
	{
		if(!authService.verify(auth, "AGENT"))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
		String token = TokenUtils.getToken(auth);
		String myUsername = authService.getUsernameFromToken(token);
		
		return new ResponseEntity<ArrayList<String>> (profileService.getInfluencers(myUsername),HttpStatus.OK);
	}
}
